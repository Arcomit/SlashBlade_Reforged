package mod.slashblade.reforged.core.animation.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.core.obj.event.ObjModelManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 15:08
 * @Description: 动画预加载
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class PreloadedAnimationHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPreloadClient(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new AnimationManager());
    }


    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public static void onPreloadServer(AddReloadListenerEvent event) {
        event.addListener(new AnimationManager());
    }
}
