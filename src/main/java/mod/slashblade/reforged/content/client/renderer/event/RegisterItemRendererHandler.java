package mod.slashblade.reforged.content.client.renderer.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.client.renderer.item.SlashBladeItemRenderer;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRendererRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 18:24
 * @Description: 注册物品渲染器处理类
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class RegisterItemRendererHandler {

    @SubscribeEvent
    public static void registerDynamicItemRenderer(FMLClientSetupEvent event) {
        DynamicItemRendererRegistry.INSTANCE.register(SbItems.SLASH_BLADE.get(), new SlashBladeItemRenderer());
    }
}
