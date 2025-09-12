package mod.slashblade.reforged.content.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.data.capabilitie.IInputCapability;
import mod.slashblade.reforged.content.data.capabilitie.ILockTarget;
import mod.slashblade.reforged.content.init.SbCapabilities;
import mod.slashblade.reforged.content.init.SbCreativeModeTab;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.core.obj.ObjModel;
import mod.slashblade.reforged.core.obj.event.ObjModelManager;
import mod.slashblade.reforged.utils.PoseStackAutoCloser;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import mod.slashblade.reforged.utils.constant.R;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.awt.*;
import java.util.Optional;

@EventBusSubscriber(modid = SlashbladeMod.MODID, value = Dist.CLIENT)
public class LockOnCircleRender {


    static final ResourceLocation modelLoc = R.Special.Lockon.lockon$obj;
    static final ResourceLocation textureLoc = R.Special.Lockon.lockon$png;

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        ILockTarget lockTarget = player.getCapability(SbCapabilities.LOCK_TARGET);

        if (lockTarget == null) {
            return;
        }

        Entity targetEntity = lockTarget.getTargetEntity();
        if (targetEntity == null) {
            return;
        }

        if (!targetEntity.equals(event.getEntity())) {
            return;
        }

        LivingEntity livingEntity = event.getEntity();
        if (!targetEntity.equals(livingEntity)) {
            return;
        }

        IInputCapability iInputCapability = player.getCapability(SbCapabilities.INPUT_CAPABILITY);
        if (iInputCapability == null) {
            return;
        }

        if (!iInputCapability.isDown(KeyInput.SNEAK)) {
            return;
        }

        ItemStack stack = player.getMainHandItem();

        SlashBladeLogic slashBladeLogic = stack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = stack.get(SbDataComponentTypes.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }

        Color effectColor = slashBladeStyle.getColor();

        if (!livingEntity.isAlive()) {
            return;
        }

        float health = livingEntity.getHealth() / livingEntity.getMaxHealth();

        Color col = new Color(effectColor.getRGB() & 0xFFFFFF | 0xAA000000, true);

        PoseStack poseStack = event.getPoseStack();

        MultiBufferSource buffer = event.getMultiBufferSource();

        try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(poseStack)) {


            float f = livingEntity.getBbHeight() * 0.5f;
            float partialTicks = event.getPartialTick();
            poseStack.translate(0.0D, f, 0.0D);

            Camera mainCamera = Minecraft.getInstance().gameRenderer.getMainCamera();

            Vec3 offset = mainCamera
                    .getPosition()
                    .subtract(
                            livingEntity.getPosition(partialTicks)
                                    .add(0, f, 0)
                    );
            offset = offset.scale(0.5f);
            poseStack.translate(offset.x(), offset.y(), offset.z());


            poseStack.mulPose(mainCamera.rotation());
            //poseStack.scale(-0.025F, -0.025F, 0.025F);

            float scale = 0.0025f;
            poseStack.scale(scale, -scale, scale);

            ObjModel model = ObjModelManager.get(modelLoc);

            RenderType renderType = SbRenderTypes.getBlend(textureLoc);

            WriteVerticesInfo.setPoseStack(poseStack);
            WriteVerticesInfo.setLightMap(LightTexture.FULL_BRIGHT);
            WriteVerticesInfo.setOverlayMap(OverlayTexture.NO_OVERLAY);
            WriteVerticesInfo.setColor(col);


            VertexConsumer vertexConsumer = buffer.getBuffer(renderType);


            final String base = "lockonBase";
            final String mask = "lockonHealthMask";
            final String value = "lockonHealth";
            model.writeVerticesOnly(vertexConsumer, base);

            try (PoseStackAutoCloser PSAC2 = PoseStackAutoCloser.pushMatrix(poseStack)) {
                poseStack.translate(0, 0, health * 10.0f);
                WriteVerticesInfo.setColor(new Color(0x20000000, true));
                model.writeVerticesOnly(vertexConsumer, mask);
            }

            WriteVerticesInfo.setColor(col);
            model.writeVerticesOnly(vertexConsumer, value);

            WriteVerticesInfo.resetColor();
            WriteVerticesInfo.resetPoseStack();
            WriteVerticesInfo.resetLightMap();
            WriteVerticesInfo.resetOverlayMap();

        }

    }


    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void onInputChange(RenderFrameEvent.Pre event) {
        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        ILockTarget lockTarget = player.getCapability(SbCapabilities.LOCK_TARGET);

        if (lockTarget == null) {
            return;
        }

        Entity targetEntity = lockTarget.getTargetEntity();

        if (targetEntity == null) {
            return;
        }

        if (!targetEntity.isAlive()) {
            return;
        }

        IInputCapability iInputCapability = player.getCapability(SbCapabilities.INPUT_CAPABILITY);
        if (iInputCapability == null) {
            return;
        }

        if (!iInputCapability.isDown(KeyInput.SNEAK)) {
            return;
        }

        float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();

        float oldYawHead = player.yHeadRot;
        float oldYawOffset = player.yBodyRot;
        float oldPitch = player.getXRot();
        float oldYaw = player.getYRot();

        float prevYawHead = player.yHeadRotO;
        float prevYawOffset = player.yBodyRotO;
        float prevYaw = player.yRotO;
        float prevPitch = player.xRotO;

        player.lookAt(EntityAnchorArgument.Anchor.EYES, targetEntity.position().add(0,targetEntity.getEyeHeight() / 2.0,0));

        float step = 0.125f * partialTicks;

        step *= Math.min(1.0f ,Math.abs(Mth.wrapDegrees(oldYaw - player.yHeadRot) * 0.5f));

        player.setXRot(Mth.rotLerp(step,oldPitch ,player.getXRot()));
        player.setYRot(Mth.rotLerp(step, oldYaw , player.getYRot()));
        player.setYHeadRot(Mth.rotLerp(step, oldYawHead , player.getYHeadRot()));

        player.yBodyRot = oldYawOffset;

        player.yBodyRotO = prevYawOffset;
        player.yHeadRotO = prevYawHead;
        player.yRotO = prevYaw;
        player.xRotO = prevPitch;
    }
}
