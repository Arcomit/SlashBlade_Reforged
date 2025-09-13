package mod.slashblade.reforged.content.client.renderer.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.client.renderer.item.SlashBladeItemRenderer;
import mod.slashblade.reforged.content.init.SbItems;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 18:24
 * @Description: 注册物品渲染器处理类
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class RegisterItemRendererHandler {
    private static final SlashBladeItemRenderer SLASH_BLADE_ITEM_RENDERER = new SlashBladeItemRenderer();

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return SLASH_BLADE_ITEM_RENDERER;
                    }
                },
                SbItems.SLASH_BLADE
        );
    }
}
