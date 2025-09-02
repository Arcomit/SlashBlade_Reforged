package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.BasicAction;
import mod.slashblade.reforged.content.register.AttackType;
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

    public static final ResourceKey<Registry<BasicAction>> ACTION_REGISTRY_KEY = ResourceKey.createRegistryKey(SlashbladeMod.prefix("action"));
    public static final Registry<BasicAction>              ACTION_REGISTRY     = new RegistryBuilder<>(ACTION_REGISTRY_KEY)
            .sync      (true)
            .defaultKey(DefaultResources.DEFAULT_ACTION)
            .create    ();

    public static final ResourceKey<Registry<AttackType>> ATTACK_TYPE_KEY = ResourceKey.createRegistryKey(SlashbladeMod.prefix("attack_type"));
    public static final Registry<AttackType> ATTACK_TYPE_REGISTRY = new RegistryBuilder<>(ATTACK_TYPE_KEY)
            .sync(true)
            .create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(ACTION_REGISTRY);
        event.register(ATTACK_TYPE_REGISTRY);
    }
}
