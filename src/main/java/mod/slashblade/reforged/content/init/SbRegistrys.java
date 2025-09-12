package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.Action;
import mod.slashblade.reforged.content.recipe.IRecipeInputItemSerializer;
import mod.slashblade.reforged.content.register.AttackType;
import mod.slashblade.reforged.content.register.SpecialAttack;
import mod.slashblade.reforged.content.register.SpecialEffect;
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
    public static final Registry<Action> ACTION_REGISTRY = new RegistryBuilder<>(ACTION_REGISTRY_KEY)
            .sync(true)
            .defaultKey(SlashbladeMod.prefix("idle"))
            .create();

    public static final ResourceKey<Registry<AttackType>> ATTACK_TYPE_KEY = ResourceKey.createRegistryKey(SlashbladeMod.prefix("attack_type"));
    public static final Registry<AttackType> ATTACK_TYPE_REGISTRY = new RegistryBuilder<>(ATTACK_TYPE_KEY)
            .sync(true)
            .create();


    public static final ResourceKey<Registry<IRecipeInputItemSerializer<?>>> RECIPE_INPUT_ITEM_SERIALIZER_KEY = ResourceKey.createRegistryKey(SlashbladeMod.prefix("recipe_input_item_serializer"));
    public static final Registry<IRecipeInputItemSerializer<?>> RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY = new RegistryBuilder<>(RECIPE_INPUT_ITEM_SERIALIZER_KEY)
            .sync(true)
            .create();

    public static final ResourceKey<Registry<SpecialAttack>> SPECIAL_ATTACK = ResourceKey.createRegistryKey(SlashbladeMod.prefix("special_attack"));
    public static final Registry<SpecialAttack> SPECIAL_ATTACK_REGISTRY = new RegistryBuilder<>(SPECIAL_ATTACK)
            .sync(true)
            .create();

    public static final ResourceKey<Registry<SpecialEffect>> SPECIAL_EFFECT = ResourceKey.createRegistryKey(SlashbladeMod.prefix("special_effect"));
    public static final Registry<SpecialEffect> SPECIAL_EFFECT_REGISTRY = new RegistryBuilder<>(SPECIAL_EFFECT)
            .sync(true)
            .create();


    // 注册注册表
    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(ACTION_REGISTRY);
        event.register(ATTACK_TYPE_REGISTRY);
        event.register(RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY);
        event.register(SPECIAL_ATTACK_REGISTRY);
        event.register(SPECIAL_EFFECT_REGISTRY);
    }
}
