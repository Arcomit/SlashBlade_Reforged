package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.BasicAction;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 13:09
 * @Description: 集中管理所有模组行动的注册（且能够从中获取调用）
 */
public class SbActions {

    public static final DeferredRegister<BasicAction> ACTIONS = DeferredRegister.create(SbRegistrys.ACTION_REGISTRY, SlashbladeMod.MODID);

    public static final Supplier<BasicAction> DEFAULT_ACTION = ACTIONS.register(
            DefaultResources.DEFAULT_ACTION.getPath(),
            () -> BasicAction.builder().build()
    );

    public static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
