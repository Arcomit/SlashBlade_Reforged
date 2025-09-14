package mod.slashblade.reforged.content.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.slashblade.reforged.content.entity.BladeStandEntity;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.utils.PoseStackAutoCloser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BladeStandEntityRenderer<E extends BladeStandEntity> extends ItemFrameRenderer<E> {
    private final net.minecraft.client.renderer.entity.ItemRenderer itemRenderer;

    public BladeStandEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(@NotNull E entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {

        if (entity.getCurrentType().isEmpty()) {
            return;
        }

        try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(poseStack)) {
            BlockPos blockpos = entity.getPos();
            Vec3 vec = Vec3.upFromBottomCenterOf(blockpos, 0.75).subtract(entity.position());
            poseStack.translate(vec.x, vec.y, vec.z);
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));

            try (PoseStackAutoCloser PSAC2 = PoseStackAutoCloser.pushMatrix(poseStack)) {

                int i = entity.getRotation();
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) i * 360.0F / 8.0F));


                poseStack.scale(2, 2, 2);
                Item type = entity.getCurrentType().getItem();
                if (type.equals(SbItems.BLADESTAND_1.get())) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
                } else if (type.equals(SbItems.BLADESTAND_2.get())) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
                } else if (type.equals(SbItems.BLADESTAND_V.get())) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
                } else if (type.equals(SbItems.BLADESTAND_S.get())) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
                } else if (type.equals(SbItems.BLADESTAND_1W.get())) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(180f));
                    poseStack.translate(0, 0, -0.15f);
                } else if (type.equals(SbItems.BLADESTAND_2W.get())) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(180f));
                    poseStack.translate(0, 0, -0.15f);
                }

                //stand render
                poseStack.pushPose();
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.translate(0, 0, 0.44);
                this.renderItem(entity, entity.getCurrentType(), poseStack, buffer, packedLight);
                poseStack.popPose();

                if (type.equals(SbItems.BLADESTAND_1W.get()) || type.equals(SbItems.BLADESTAND_2W.get())) {
                    poseStack.translate(0, 0, -0.19f);
                } else if (type.equals(SbItems.BLADESTAND_1)) {
                }
                //blade render
                poseStack.mulPose(Axis.YP.rotationDegrees(-180f));
                this.renderItem(entity, entity.getItem(), poseStack, buffer, packedLight);
            }
        }
    }

    private void renderItem(BladeStandEntity entity, ItemStack itemstack, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (!itemstack.isEmpty()) {
            BakedModel ibakedmodel = this.itemRenderer.getModel(itemstack, entity.level(), null, 0);
            this.itemRenderer.render(itemstack, ItemDisplayContext.FIXED, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        }
    }
}
