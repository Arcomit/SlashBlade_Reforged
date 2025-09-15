package mod.slashblade.reforged.content.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.slashblade.reforged.utils.PoseStackAutoCloser;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class BladeItemEntityRenderer extends ItemEntityRenderer {
    public BladeItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    /*@Override
    public void render(ItemEntity itemIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        this.shadowRadius = 0;

        if(!itemIn.getItem().isEmpty()){
            renderBlade(itemIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }else{
            partialTicks = (float)(itemIn.bobOffs * 20.0 - (double)itemIn.getAge());
            super.render(itemIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    private void renderBlade(ItemEntity itemIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(matrixStackIn)) {
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(entityYaw));

            ItemStack current = itemIn.getItem();


            EnumSet<SwordType> types = SwordType.from(current);
            ResourceLocation modelLocation =
                    current.getCapability(ItemSlashBlade.BLADESTATE)
                            .map((state) -> state.getModel().orElseGet(() -> BladeModelManager.resourceDefaultModel))
                            .orElseGet(()->{
                                if(current.hasTag() && current.getTag().contains("Model"))
                                    return new ResourceLocation(current.getTag().getString("Model"));
                                else
                                    return  BladeModelManager.resourceDefaultModel;
                            });
            ResourceLocation textureLocation =
                    current.getCapability(ItemSlashBlade.BLADESTATE)
                            .map((state) -> state.getTexture().orElseGet(() -> BladeModelManager.resourceDefaultTexture))
                            .orElseGet(()->{
                                if(current.hasTag() && current.getTag().contains("Texture"))
                                    return new ResourceLocation(current.getTag().getString("Texture"));
                                else
                                    return  BladeModelManager.resourceDefaultTexture;
                            });
            WavefrontObject model = BladeModelManager.getInstance().getModel(modelLocation);

            float scale = 0.00625f;

            try (PoseStackAutoCloser PSAC2 = PoseStackAutoCloser.pushMatrix(matrixStackIn)){

                float heightOffset;
                float xOffset = 0;
                String renderTarget;
                if (types.contains(SwordType.EdgeFragment)) {
                    heightOffset = 225;
                    xOffset = 200;
                    renderTarget = "blade_fragment";
                }else if (types.contains(SwordType.Broken)) {
                    heightOffset = 100;
                    xOffset = 30;
                    renderTarget = "blade_damaged";
                }else {
                    heightOffset = 225;
                    xOffset = 120;
                    renderTarget = "blade";
                }


                if(itemIn.isInWater()){

                    matrixStackIn.translate(0, 0.025f, 0);
                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(itemIn.bobOffs));

                    matrixStackIn.scale(scale, scale, scale);

                    matrixStackIn.mulPose(Axis.XP.rotationDegrees(90));

                }else if(!itemIn.onGround())
                {
                    matrixStackIn.scale(scale, scale, scale);

                    float speed = -81f;
                    matrixStackIn.mulPose(Axis.ZP.rotationDegrees(speed * (itemIn.tickCount + partialTicks)));
                    matrixStackIn.translate(xOffset, 0 , 0);
                }else{
                    matrixStackIn.scale(scale, scale, scale);

                    matrixStackIn.mulPose(Axis.ZP.rotationDegrees(60 + (float)Math.toDegrees(itemIn.bobOffs / 6.0)));
                    matrixStackIn.translate(heightOffset, 0 , 0);
                }



                BladeRenderState.renderOverrided(current, model, renderTarget, textureLocation, matrixStackIn, bufferIn, packedLightIn);
                BladeRenderState.renderOverridedLuminous(current, model, renderTarget + "_luminous", textureLocation, matrixStackIn, bufferIn, packedLightIn);
            }

            if((itemIn.isInWater() || itemIn.onGround()) && !types.contains(SwordType.NoScabbard)) {

                try (PoseStackAutoCloser PSAC2 = PoseStackAutoCloser.pushMatrix(matrixStackIn)){

                    matrixStackIn.translate(0, 0.025f, 0);

                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(itemIn.bobOffs));

                    if(!itemIn.isInWater()){
                        matrixStackIn.translate(0.75, 0, -0.4);
                    }

                    matrixStackIn.scale(scale, scale, scale);

                    matrixStackIn.mulPose(Axis.XP.rotationDegrees(90));

                    String renderTarget = "sheath";

                    BladeRenderState.renderOverrided(current, model, renderTarget, textureLocation, matrixStackIn, bufferIn, packedLightIn);
                    BladeRenderState.renderOverridedLuminous(current, model, renderTarget + "_luminous", textureLocation, matrixStackIn, bufferIn, packedLightIn);
                }
            }

        }
    }*/

}
