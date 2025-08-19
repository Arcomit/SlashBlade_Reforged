package mod.slashblade.reforged.content.client.renderer.item.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.slashblade.reforged.content.client.renderer.SBRenderTypes;
import mod.slashblade.reforged.core.obj.ObjModel;
import mod.slashblade.reforged.core.obj.event.ObjModelManager;
import mod.slashblade.reforged.utils.DefaultResources;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.breeze.Breeze;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-19 10:28
 * @Description: TODO
 */
public class SlashBladeThirdPersonLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T,M> {

    public SlashBladeThirdPersonLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack         poseStack,
                       @NotNull MultiBufferSource bufferSource,
                       int                        packedLight,
                       @NotNull T                 livingEntity,
                       float                      limbSwing,
                       float                      limbSwingAmount,
                       float                      partialTick,
                       float                      ageInTicks,
                       float                      netHeadYaw,
                       float                      headPitch)
    {
        Minecraft mc = Minecraft.getInstance();
        poseStack.pushPose();
        poseStack.translate(0, 1.5f, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        WriteVerticesInfo.setPoseStack(poseStack);
        WriteVerticesInfo.setLightMap(packedLight);
        WriteVerticesInfo.setOverlayMap(OverlayTexture.NO_OVERLAY);

        ObjModel model = ObjModelManager.get(DefaultResources.DEFAULT_MODEL);

        RenderType renderType = SBRenderTypes.getSlashBladeBlend(DefaultResources.DEFAULT_TEXTURE);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        //poseStack.scale(0.01f,0.01f,0.01f);

        model.writeVerticesOnly(vertexConsumer, "blade");


        WriteVerticesInfo.resetPoseStack();
        WriteVerticesInfo.resetLightMap();
        WriteVerticesInfo.resetOverlayMap();

        poseStack.popPose();
    }
}
