package mod.slashblade.reforged.content.init;

import lombok.extern.slf4j.Slf4j;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.Action;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 13:09
 * @Description: 集中管理所有模组行动的注册（且能够从中获取调用）
 */
@Slf4j
public class SbActions {

    public static final DeferredRegister<Action> ACTIONS = DeferredRegister.create(SbRegistrys.ACTION_REGISTRY, SlashbladeMod.MODID);

    public static final DeferredHolder<Action, Action> IDLE_ACTION = ACTIONS.register(
            "idle",
            () -> new Action()
                    .isLoop(true)
    );

    public static final DeferredHolder<Action, Action> WALKING_ACTION = ACTIONS.register(
            "walking",
            () -> new Action()
                    .animation("Default_Walk_Universal", 0.0f, 0.25f)
                    .isLoop(true)
    );

    public static final DeferredHolder<Action, Action> SPRINTING_ACTION = ACTIONS.register(
            "sprinting",
            () -> new Action()
                    .animation("Default_Sprint_Universal", 0.0f, 0.25f)
                    .isLoop(true)
    );

    public static final DeferredHolder<Action, Action> TEST = ACTIONS.register(
            "test",
            () -> new Action()
                    .animation("Combo_A1_ThirdPerson", 0.0f, 1.33f)
                    .fristPersonAnimation("Combo_A1_ThirdPerson", 0.0f, 1.33f)
                    .addNotify(0.358f, (context) -> {
                        //System.out.println(context.livingEntity);
                    })
                    .isLoop(true)
    );

    public static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
