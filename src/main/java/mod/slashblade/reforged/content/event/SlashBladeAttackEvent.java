package mod.slashblade.reforged.content.event;

import lombok.*;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.register.AttackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class SlashBladeAttackEvent extends SlashBladeEvent {

    LivingEntity attacker;
    Entity target;

    /***
     * 基础攻击倍率
     */
    double basicsModifiedRatio;

    /***
     * 当前的攻击倍率
     */
    @Setter
    double modifiedRatio;

    /***
     * modifiedRatio放大器
     */
    @Setter
    double modifiedRatioAmplifier = 1;


    final List<AttackType> attackTypeList;

    public SlashBladeAttackEvent(ItemStack item, SlashBladeLogic slashBladeLogic, LivingEntity attacker, Entity target, double modifiedRatio, List<AttackType> attackTypeList) {
        super(item, slashBladeLogic, attacker);
        this.attacker = attacker;
        this.target = target;
        this.basicsModifiedRatio = modifiedRatio;
        this.modifiedRatio = modifiedRatio;
        this.attackTypeList = attackTypeList;
    }

    public double getUltimatelyModifiedRatio() {
        return modifiedRatio * modifiedRatioAmplifier;
    }

    public void addModifiedRatio(double amplifier) {
        modifiedRatio += amplifier;
    }

    public void addModifiedRatioAmplifier(double amplifier) {
        modifiedRatioAmplifier += amplifier;
    }
}
