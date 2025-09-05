package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.Action;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 12:57
 * @Description: 集中管理所有模组注册表的注册（且能够从中获取调用）
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class SbRegistrys {

    // 动作注册表
    public static final ResourceKey<Registry<Action>> ACTION_REGISTRY_KEY = ResourceKey.createRegistryKey(SlashbladeMod.prefix("action"));
    public static final Registry<Action>              ACTION_REGISTRY     = new RegistryBuilder<>(ACTION_REGISTRY_KEY)
            .sync      (true)
            .defaultKey(SlashbladeMod.prefix("idle"))
            .create    ();

    // 注册注册表
    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(ACTION_REGISTRY);
    }
}
