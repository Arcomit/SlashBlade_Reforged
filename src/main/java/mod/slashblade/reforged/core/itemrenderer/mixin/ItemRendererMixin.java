package mod.slashblade.reforged.core.itemrenderer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRendererRegistry;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 20:07
 * @Description: MixinItemRenderer用于方便的自定义物品渲染，不需要写花里胡哨的一大堆（比如用不上的BakedModel）
 */
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Inject(method = "render",
            at = @At(
                    value  = "INVOKE",
                    target = "Lnet/minecraft/client/resources/model/BakedModel;isCustomRenderer()Z",
                    shift  = At.Shift.BEFORE
            ),
            require     = 1,
            cancellable = true
    )
    private void onRenderByItem(
            ItemStack          itemStack,
            ItemDisplayContext displayContext,
            boolean            leftHand,
            PoseStack          poseStack,
            MultiBufferSource  bufferSource,
            int                combinedLight,
            int                combinedOverlay,
            BakedModel         p_model,
            CallbackInfo       ci
    ) {
        @Nullable
        DynamicItemRenderer renderer = DynamicItemRendererRegistry.INSTANCE.get(itemStack.getItem());

        if (renderer != null) {
            renderer.renderByItem(itemStack, displayContext, poseStack, bufferSource, combinedLight, combinedOverlay);
            poseStack.popPose();

            ci.cancel();
        }
    }
}
