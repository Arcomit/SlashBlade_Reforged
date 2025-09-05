package mod.slashblade.reforged.content.client.renderer.item;

import com.maydaymemory.mae.basic.Pose;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import lombok.experimental.ExtensionMethod;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationInstance;
import mod.slashblade.reforged.content.client.camera.CameraAnimationHandler;
import mod.slashblade.reforged.content.client.renderer.SbRenderTypes;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.core.animation.AnimationAsset;
import mod.slashblade.reforged.core.animation.event.AnimationManager;
import mod.slashblade.reforged.utils.PoseStackAutoCloser;
import mod.slashblade.reforged.utils.extension.ItemDisplayContextExtension;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRenderer;
import mod.slashblade.reforged.core.obj.ObjModel;
import mod.slashblade.reforged.core.obj.event.ObjModelManager;
import mod.slashblade.reforged.utils.DefaultResources;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 17:43
 * @Description: 拔刀剑物品的渲染（不含第三人称）
 */
@ExtensionMethod(ItemDisplayContextExtension.class)
public class SlashBladeItemRenderer implements DynamicItemRenderer {

    // 物品栏渲染
    @Override
    public void renderByItem(
            ItemStack          stack,
            ItemDisplayContext transform,
            PoseStack          poseStack,
            MultiBufferSource  bufferSource,
            int                packedLight,
            int                packedOverlay
    ) {
        if (transform.firstPerson() || transform.thirdPerson()) {
            return;
        }

        ObjModel model = ObjModelManager.get(DefaultResources.DEFAULT_MODEL);

        AnimationAsset animation = AnimationManager.get(DefaultResources.DEFAULT_ANIMATION);
        if (animation == null) return;
        Pose pose = animation.evaluate(0f);
        model.applyPose(pose);

        try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(poseStack)) {
            poseStack.translate(0.5f, 0.5f, 0.5f);

            if (transform == ItemDisplayContext.GROUND) {
                poseStack.translate(0, 0.15f, 0);
                poseStack.scale(0.5f, 0.5f, 0.5f);
            } else if (transform == ItemDisplayContext.FIXED) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
                poseStack.scale(0.95f, 0.95f, 0.95f);
            } else if (transform == ItemDisplayContext.GUI) {
                poseStack.scale(0.8f, 0.8f, 0.8f);
            }

            WriteVerticesInfo.setPoseStack(poseStack);
            WriteVerticesInfo.setLightMap(packedLight);
            WriteVerticesInfo.setOverlayMap(packedOverlay);

            RenderType renderType = SbRenderTypes.getBlend(DefaultResources.DEFAULT_TEXTURE);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

            //poseStack.scale(0.01f,0.01f,0.01f);

            model.writeVerticesOnly(vertexConsumer, "item_blade");

            WriteVerticesInfo.resetPoseStack();
            WriteVerticesInfo.resetLightMap();
            WriteVerticesInfo.resetOverlayMap();
        }

        model.resetPose();
    }

    // 第一人称渲染
    public void renderFristPerson(
            ItemStack         stack,
            RenderHandEvent   event,
            PoseStack         poseStack,
            MultiBufferSource bufferSource,
            int               packedLight,
            float             partialTick
    ) {
        event.setCanceled(true);
        Minecraft mc = Minecraft.getInstance();

        ObjModel model = ObjModelManager.get(DefaultResources.DEFAULT_MODEL);

        if (!(mc.getCameraEntity() instanceof LocalPlayer)) return;
        LocalPlayer playerTest = (LocalPlayer) mc.getCameraEntity();
        SlashBladeAnimationInstance instance = SlashBladeAnimationInstance.get(playerTest);
        instance.renderTick();

        Pose pose = instance.stateMachine.getPose();
//        AnimationAsset animation = AnimationManager.get(DefaultResources.DEFAULT_ANIMATION);
//        if (animation == null) return;
//        Pose pose = animation.evaluate(0f);
        model.applyPose(pose);

        try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(poseStack)) {
            if (mc.getCameraEntity() instanceof LocalPlayer player) {
                // 抵消移动视角时的晃动
                float interpolatedXBobbing = Mth.lerp(partialTick, player.xBobO, player.xBob);
                float interpolatedYBobbing = Mth.lerp(partialTick, player.yBobO, player.yBob);
                poseStack.mulPose(Axis.YN.rotationDegrees((player.getViewYRot(partialTick) - interpolatedYBobbing ) * 0.1F));
                poseStack.mulPose(Axis.XN.rotationDegrees((player.getViewXRot(partialTick) - interpolatedXBobbing ) * 0.1F));

                // 抵消走路时的晃动
                if (mc.options.bobView().get()){
                    float walkDelta = player.walkDist - player.walkDistO;
                    float phase = -(player.walkDist + walkDelta  * partialTick);
                    float bobbingAmount = Mth.lerp(partialTick, player.oBob, player.bob);
                    poseStack.mulPose(Axis.XN.rotationDegrees(Math.abs(Mth.cos(phase * (float)Math.PI - 0.2F) * bobbingAmount) * 5.0F));
                    poseStack.mulPose(Axis.ZN.rotationDegrees(Mth.sin(phase * (float)Math.PI) * bobbingAmount * 3.0F));
                    poseStack.translate(-Mth.sin(phase * (float)Math.PI) * bobbingAmount * 0.5F, Math.abs(Mth.cos(phase * (float)Math.PI) * bobbingAmount), 0.0F);
                }
            }

            if (true){
                // 抵消上下移动摄像机视角时的跟随旋转（类似拔刀剑2，重锋的视角）
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                Quaternionf inverseRot = new Quaternionf(camera.rotation()).conjugate();
                poseStack.mulPose(inverseRot);
                Quaternionf Quaternionf = new Quaternionf();
                // 抵消摄像机Yaw额外旋转
                float yaw = camera.getYRot() - CameraAnimationHandler.yRotation;
                float ptich = 0;
                float roll = 0;
                Quaternionf.rotationYXZ((float) Math.PI - yaw * (float) (Math.PI / 180.0), -ptich * (float) (Math.PI / 180.0), -roll * (float) (Math.PI / 180.0));
                poseStack.mulPose(Quaternionf);
            }else{
                // 固定视角，渲染跟随摄像机视角移动（类似拔刀剑1的视角）
                // 抵消摄像机额外旋转
                float extraY = CameraAnimationHandler.yRotation;
                float extraX = CameraAnimationHandler.xRotation;
                float extraZ = -CameraAnimationHandler.zRotation;
                Quaternionf extraRot = new Quaternionf().rotationXYZ(
                        extraX * ((float) Math.PI / 180.0f),
                        extraY * ((float) Math.PI / 180.0f),
                        extraZ * ((float) Math.PI / 180.0f)
                );
                poseStack.mulPose(new Quaternionf(extraRot));
            }

            // 摄像机是无法移动的，但我们反方向移动物品可以达到相对摄像机移动的效果
            poseStack.translate(-CameraAnimationHandler.posX, -CameraAnimationHandler.posY, -CameraAnimationHandler.posZ);

            WriteVerticesInfo.setPoseStack(poseStack);
            WriteVerticesInfo.setLightMap(packedLight);
            WriteVerticesInfo.setOverlayMap(OverlayTexture.NO_OVERLAY);

            RenderType renderType = SbRenderTypes.getBlend(DefaultResources.DEFAULT_TEXTURE);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

            model.writeVerticesOnly(vertexConsumer, "blade");
            model.writeVerticesOnly(vertexConsumer, "sheath");

            WriteVerticesInfo.resetPoseStack();
            WriteVerticesInfo.resetLightMap();
            WriteVerticesInfo.resetOverlayMap();
        }

        model.resetPose();
    }

    // 第三人称渲染
    public void renderThirdPerson(
                       @NotNull PoseStack         poseStack,
                       @NotNull MultiBufferSource bufferSource,
                       int                        packedLight,
                       @NotNull LivingEntity      livingEntity,
                       @NotNull ItemStack         itemStack,
                       float                      limbSwing,
                       float                      limbSwingAmount,
                       float                      partialTick,
                       float                      ageInTicks,
                       float                      netHeadYaw,
                       float                      headPitch)
    {
//        SlashBladeAnimationGraph graph = itemStack.get(SbDataComponents.BASIC_EXAMPLE);
//        graph.setTest(20);
//        graph.setTest2(false);
//        itemStack.set(SbDataComponents.BASIC_EXAMPLE, graph);
//
//        System.out.println(itemStack.get(SbDataComponents.BASIC_EXAMPLE).getTest());
//        System.out.println(itemStack.get(SbDataComponents.BASIC_EXAMPLE).isTest2());

        ResourceLocation test = itemStack.get(SbDataComponentTypes.DRAW_ACTION);
        System.out.println(test);

        ObjModel model = ObjModelManager.get(DefaultResources.DEFAULT_MODEL);

        AnimationAsset animation = AnimationManager.get(DefaultResources.DEFAULT_ANIMATION);
        if (animation == null) return;
        Pose pose = animation.evaluate(0f);
        model.applyPose(pose);

        try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(poseStack)) {
            // 调整位置，使其于blockbench初始位置一致
            poseStack.translate(0, 1.5f, 0);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            WriteVerticesInfo.setPoseStack(poseStack);
            WriteVerticesInfo.setLightMap(packedLight);
            WriteVerticesInfo.setOverlayMap(OverlayTexture.NO_OVERLAY);

            RenderType renderType = SbRenderTypes.getBlend(DefaultResources.DEFAULT_TEXTURE);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

            //poseStack.scale(0.01f,0.01f,0.01f);

            model.writeVerticesOnly(vertexConsumer, "blade");
            model.writeVerticesOnly(vertexConsumer, "sheath");


            WriteVerticesInfo.resetPoseStack();
            WriteVerticesInfo.resetLightMap();
            WriteVerticesInfo.resetOverlayMap();
        }

        model.resetPose();
    }
}
