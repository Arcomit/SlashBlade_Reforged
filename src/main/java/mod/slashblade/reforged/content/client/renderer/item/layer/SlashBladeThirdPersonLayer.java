package mod.slashblade.reforged.content.client.renderer.item.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.slashblade.reforged.content.client.renderer.item.SlashBladeItemRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-19 10:28
 * @Description: 第三人称拔刀剑物品渲染层
 */
public class SlashBladeThirdPersonLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T,M> {

    public SlashBladeThirdPersonLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack         poseStack,
                       @NotNull MultiBufferSource bufferSource,
                       int                        packedLight,
                       @NotNull LivingEntity      livingEntity,
                       float                      limbSwing,
                       float                      limbSwingAmount,
                       float                      partialTick,
                       float                      ageInTicks,
                       float                      netHeadYaw,
                       float                      headPitch)
    {
        ItemStack itemStack = livingEntity.getMainHandItem();
        if (itemStack.isEmpty()) return;

        @Nullable
        BlockEntityWithoutLevelRenderer renderer = IClientItemExtensions.of(itemStack).getCustomRenderer();
        if (renderer != null && renderer instanceof SlashBladeItemRenderer bladeRenderer) {
            bladeRenderer.renderThirdPerson(
                    poseStack,
                    bufferSource,
                    packedLight,
                    livingEntity,
                    itemStack,
                    limbSwing,
                    limbSwingAmount,
                    partialTick,
                    ageInTicks,
                    netHeadYaw,
                    headPitch);
        }
    }
}
