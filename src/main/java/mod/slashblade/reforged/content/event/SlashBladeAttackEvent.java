package mod.slashblade.reforged.content.event;

import lombok.*;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.register.AttackType;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Getter
public class SlashBladeAttackEvent extends SlashBladeEvent {

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

    public SlashBladeAttackEvent(ItemStack item, SlashBladeLogic slashBladeLogic, double modifiedRatio, List<AttackType> attackTypeList) {
        super(item, slashBladeLogic);
        this.attackTypeList = attackTypeList;
        this.modifiedRatio = modifiedRatio;
    }

    public double getUltimatelyModifiedRatio() {
        return modifiedRatio * modifiedRatioAmplifier;
    }

    public void  addModifiedRatio(double amplifier) {
        modifiedRatio += amplifier;
    }

    public void addModifiedRatioAmplifier(double amplifier) {
        modifiedRatioAmplifier += amplifier;
    }
}
