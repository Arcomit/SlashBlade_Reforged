package mod.slashblade.reforged.content.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityNameRenderer<E extends Entity> extends EntityRenderer<E> {
    public EntityNameRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull E p_entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        this.renderNameTag(p_entity, p_entity.getName(), poseStack, bufferSource, packedLight, partialTick);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull E entity) {
        return ResourceLocationConstants.DEFAULT;
    }
}
