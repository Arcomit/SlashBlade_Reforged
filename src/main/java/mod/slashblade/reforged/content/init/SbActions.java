package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.BasicAction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 13:09
 * @Description: 集中管理所有模组行动的注册（且能够从中获取调用）
 */
public class SbActions {

    public static final DeferredRegister<BasicAction> ACTIONS = DeferredRegister.create(SbRegistrys.ACTION_REGISTRY, SlashbladeMod.MODID);

    //

    public static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
