package mod.slashblade.reforged.core.itemrenderer.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRenderer;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRendererRegistry;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import org.jetbrains.annotations.Nullable;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-12 15:56
 * @Description: 动态物品渲染器的第一人称渲染事件处理类
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class FirstPersonRenderHandler {

    @SubscribeEvent
    public static void firstPersonRender(RenderHandEvent event) {
        ItemStack stack = event.getItemStack();

        @Nullable
        DynamicItemRenderer renderer = DynamicItemRendererRegistry.INSTANCE.get(stack.getItem());
        if (renderer != null) {
            renderer.renderFristPerson(
                    stack,
                    event,
                    event.getPoseStack        (),
                    event.getMultiBufferSource(),
                    event.getPackedLight      (),
                    event.getPartialTick      ()
            );
        }
    }
}
