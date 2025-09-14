package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.register.SpecialAttack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SlashBladeSpecialAttacks {
    public static final DeferredRegister<SpecialAttack> SPECIAL_ATTACK = DeferredRegister.create(SbRegistrys.SPECIAL_ATTACK, SlashbladeMod.MODID);

    public static void register(IEventBus bus) {
        SPECIAL_ATTACK.register(bus);
    }
}
