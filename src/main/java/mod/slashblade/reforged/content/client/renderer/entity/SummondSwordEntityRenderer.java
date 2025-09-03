package mod.slashblade.reforged.content.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: til
 */
public class SummondSwordEntityRenderer<E extends SummondSwordEntity> extends EntityRenderer<E> {

    public SummondSwordEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull E p_entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(E entity) {
        return entity.getTexture();
    }
}
