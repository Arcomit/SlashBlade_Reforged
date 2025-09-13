package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.Action;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 13:09
 * @Description: 集中管理所有模组行动的注册（且能够从中获取调用）
 */
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
                    .animation("Default_Walk_FristPerson", 0.0f, 0.25f)
                    .fristPersonAnimation("Default_Walk_FristPerson", 0.0f, 0.25f)
                    .isLoop(true)
    );

    public static final DeferredHolder<Action, Action> SPRINTING_ACTION = ACTIONS.register(
            "sprinting",
            () -> new Action()
                    .animation("Default_Sprint_FristPerson", 0.0f, 0.25f)
                    .fristPersonAnimation("Default_Sprint_FristPerson", 0.0f, 0.25f)
                    .isLoop(true)
    );

    public static final DeferredHolder<Action, Action> TEST = ACTIONS.register(
            "test",
            () -> new Action()
                    .animation("Combo_A4_FristPerson", 0.0f, 3.58f)
                    .fristPersonAnimation("Combo_A4_FristPerson", 0.0f, 3.58f)
                    .isLoop(true)
    );

    public static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
