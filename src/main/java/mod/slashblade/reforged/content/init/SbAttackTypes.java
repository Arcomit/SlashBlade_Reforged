package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.register.AttackType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @Author: til
 * @Description: 能力注入类
 */
public class SbAttackTypes {
    public static final DeferredRegister<AttackType> ATTACK_TYPE = DeferredRegister.create(SbRegistrys.ATTACK_TYPE_REGISTRY, SlashbladeMod.MODID);

    /***
     * 幻影剑攻击类型
     */
    public static final Supplier<AttackType> SUMMOND_SWORD_ATTACK_TYPE = ATTACK_TYPE.register("summond_sword_attack_type", () -> new AttackType());

    /***
     * 幻影刃攻击类型
     */
    public static final Supplier<AttackType> DRIVE_SWORD_ATTACK_TYPE = ATTACK_TYPE.register("drive_sword_attack_type", () -> new AttackType());

    /***
     * 次元斩攻击类型
     */
    public static final Supplier<AttackType> JUDGEMENT_CUT_ATTACK_TYPE = ATTACK_TYPE.register("judgement_cut_attack_type", () -> new AttackType());

    /***
     * 闪电攻击类型
     */
    public static final Supplier<AttackType> LIGHTNING_ATTACK_TYPE = ATTACK_TYPE.register("lightning_attack_type", () -> new AttackType());

    /***
     * 劈砍攻击类型
     */
    public static final Supplier<AttackType> SLASH_BLADE_ATTACK_TYPE = ATTACK_TYPE.register("slash_blade_attack_type", () -> new AttackType());


    public static void register(IEventBus bus) {
        ATTACK_TYPE.register(bus);
    }
}
