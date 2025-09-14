package mod.slashblade.reforged.content.client.renderer.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.client.renderer.item.SlashBladeItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-12 15:56
 * @Description: 第一人称渲染处理类
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class FirstPersonRenderHandler {

    @SubscribeEvent
    public static void firstPersonRender(RenderHandEvent event) {
        ItemStack itemStack = event.getItemStack();

        if (itemStack.isEmpty()) return;

        @Nullable
        BlockEntityWithoutLevelRenderer renderer = IClientItemExtensions.of(itemStack).getCustomRenderer();
        if (renderer != null && renderer instanceof SlashBladeItemRenderer bladeRenderer) {
            bladeRenderer.renderFristPerson(
                    itemStack,
                    event,
                    event.getPoseStack        (),
                    event.getMultiBufferSource(),
                    event.getPackedLight      (),
                    event.getPartialTick      ()
            );
        }
    }
}
