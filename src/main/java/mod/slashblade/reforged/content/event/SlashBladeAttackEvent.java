package mod.slashblade.reforged.content.event;

import lombok.*;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.register.AttackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@Getter
public class SlashBladeAttackEvent extends SlashBladeEvent {

    LivingEntity attacker;
    Entity target;


    /***
     * 当前的攻击倍率
     */
    @Setter
    double modifiedRatio;

    /***
     * 伤害放大器
     */
    double modifiedRatioAmplifier = 1;

    /***
     * 机制放大器（第二乘区）
     */
    double mechanismModifiedRatioAmplifier = 1;

    final List<AttackType> attackTypeList;

    public SlashBladeAttackEvent(ItemStack item, SlashBladeLogic slashBladeLogic, LivingEntity attacker, Entity target, double modifiedRatio, List<AttackType> attackTypeList) {
        super(item, slashBladeLogic, attacker);
        this.attacker = attacker;
        this.target = target;
        this.modifiedRatio = modifiedRatio;
        this.attackTypeList = attackTypeList;
    }

    public double getUltimatelyModifiedRatio() {
        return modifiedRatio * modifiedRatioAmplifier * mechanismModifiedRatioAmplifier;
    }

    public void addModifiedRatioAmplifier(double amplifier) {
        modifiedRatio += amplifier;
    }

    public void addMechanismModifiedRatioAmplifier(double amplifier) {
        mechanismModifiedRatioAmplifier += amplifier;
    }
}
