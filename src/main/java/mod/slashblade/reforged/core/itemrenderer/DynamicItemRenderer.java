package mod.slashblade.reforged.core.itemrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderHandEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 19:58
 * @Description: TODO
 */
public interface DynamicItemRenderer {

    void renderByItem(ItemStack stack, ItemDisplayContext mode, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);

}
