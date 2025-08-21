package mod.slashblade.reforged.content.client.renderer.item;

import com.maydaymemory.mae.basic.BoneTransform;
import com.maydaymemory.mae.basic.Pose;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import lombok.experimental.ExtensionMethod;
import mod.slashblade.reforged.content.client.camera.CameraAnimationHandler;
import mod.slashblade.reforged.content.client.renderer.SbRenderTypes;
import mod.slashblade.reforged.content.init.SbDataComponents;
import mod.slashblade.reforged.core.animation.event.AnimationManager;
import mod.slashblade.reforged.core.obj.ObjGroupIndexProvider;
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
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 17:43
 * @Description: 拔刀剑物品的渲染（不含第三人称）
 */
@ExtensionMethod(ItemDisplayContextExtension.class)
public class SlashBladeItemRenderer implements DynamicItemRenderer {

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transform, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        if (transform.firstPerson() || transform.thirdPerson()) {
            return;
        }
        System.out.println(stack.get(SbDataComponents.PUT_AWAY_ACTION));
        poseStack.pushPose();

        WriteVerticesInfo.setPoseStack(poseStack);
        WriteVerticesInfo.setLightMap(packedLight);
        WriteVerticesInfo.setOverlayMap(packedOverlay);

        ObjModel model = ObjModelManager.get(DefaultResources.DEFAULT_MODEL);

        RenderType renderType = SbRenderTypes.getBlend(DefaultResources.DEFAULT_TEXTURE);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        //poseStack.scale(0.01f,0.01f,0.01f);

        model.writeVerticesOnly(vertexConsumer, "blade");
        model.writeVerticesOnly(vertexConsumer, "sheath");


        WriteVerticesInfo.resetPoseStack();
        WriteVerticesInfo.resetLightMap();
        WriteVerticesInfo.resetOverlayMap();

        poseStack.popPose();
    }

    @Override
    public void renderFristPerson(ItemStack stack, RenderHandEvent event, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
        event.setCanceled(true);
        Minecraft mc = Minecraft.getInstance();
        poseStack.pushPose();
        if (mc.getCameraEntity() instanceof LocalPlayer player) {
            // 抵消视角左右移动的晃动
            float interpolatedXBobbing = Mth.lerp(partialTick, player.xBobO, player.xBob);
            float interpolatedYBobbing = Mth.lerp(partialTick, player.yBobO, player.yBob);
            poseStack.mulPose(Axis.YN.rotationDegrees((player.getViewYRot(partialTick) - interpolatedYBobbing ) * 0.1F));
            poseStack.mulPose(Axis.XN.rotationDegrees((player.getViewXRot(partialTick) - interpolatedXBobbing ) * 0.1F));

            //抵消走路时的晃动
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
            // 抵消视角旋转，固定物品位置
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            Quaternionf inverseRot = new Quaternionf(camera.rotation()).conjugate();
            poseStack.mulPose(inverseRot);
            // 重新应用Yaw使左右移动时跟随视角旋转
            Quaternionf Quaternionf = new Quaternionf();
            float yaw = camera.getYRot() - CameraAnimationHandler.y;
            float ptich = 0;
            float roll = 0;
            Quaternionf.rotationYXZ((float) Math.PI - yaw * (float) (Math.PI / 180.0), -ptich * (float) (Math.PI / 180.0), -roll * (float) (Math.PI / 180.0));
            poseStack.mulPose(Quaternionf);
        }else{

            // 抵消摄像机额外旋转
            float extraY = CameraAnimationHandler.y;
            float extraX = CameraAnimationHandler.x;
            float extraZ = -CameraAnimationHandler.z;

            Quaternionf extraRot = new Quaternionf().rotationXYZ(
                    extraX * ((float) Math.PI / 180.0f),
                    extraY * ((float) Math.PI / 180.0f),
                    extraZ * ((float) Math.PI / 180.0f)
            );

            poseStack.mulPose(new Quaternionf(extraRot));
        }

        poseStack.translate(0, -1.125, -0.375);
        //poseStack.translate(1, -0.5, -1.03125);

        WriteVerticesInfo.setPoseStack(poseStack);
        WriteVerticesInfo.setLightMap(packedLight);
        WriteVerticesInfo.setOverlayMap(OverlayTexture.NO_OVERLAY);

        ObjModel model = ObjModelManager.get(DefaultResources.DEFAULT_MODEL);

        RenderType renderType = SbRenderTypes.getBlend(DefaultResources.DEFAULT_TEXTURE);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        model.writeVerticesOnly(vertexConsumer, "blade");
        model.writeVerticesOnly(vertexConsumer, "sheath");

        WriteVerticesInfo.resetPoseStack();
        WriteVerticesInfo.resetLightMap();
        WriteVerticesInfo.resetOverlayMap();

        poseStack.popPose();
    }
}
