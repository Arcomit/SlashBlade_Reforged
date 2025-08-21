package mod.slashblade.reforged.core.animation.event;

import mod.slashblade.reforged.SlashbladeMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 15:08
 * @Description: 动画预加载
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class PreloadedAnimationHandler {

    @SubscribeEvent
    public static void onPreload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new AnimationManager());
    }
}
