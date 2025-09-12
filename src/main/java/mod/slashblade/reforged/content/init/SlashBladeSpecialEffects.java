package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.register.SpecialAttack;
import mod.slashblade.reforged.content.register.SpecialEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SlashBladeSpecialEffects {
    public static final DeferredRegister<SpecialEffect> SPECIAL_EFFECT = DeferredRegister.create(SbRegistrys.SPECIAL_EFFECT, SlashbladeMod.MODID);

    public static void register(IEventBus bus) {
        SPECIAL_EFFECT.register(bus);
    }
}
