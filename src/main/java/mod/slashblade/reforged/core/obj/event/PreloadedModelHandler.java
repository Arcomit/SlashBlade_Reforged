package mod.slashblade.reforged.core.obj.event;

import mod.slashblade.reforged.SlashbladeMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 11:54
 * @Description: 用于在启动游戏，重载材质包时预加载模型
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class PreloadedModelHandler {

    @SubscribeEvent
    public static void onPreload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ObjModelManager());
    }
}
