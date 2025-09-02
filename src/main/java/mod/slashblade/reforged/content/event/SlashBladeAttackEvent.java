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
    float modifiedRatio;


    final List<AttackType> attackTypeList;

    public SlashBladeAttackEvent(ItemStack item, SlashBladeLogic slashBladeLogic, float modifiedRatio, List<AttackType> attackTypeList) {
        super(item, slashBladeLogic);
        this.attackTypeList = attackTypeList;
        this.modifiedRatio = modifiedRatio;
    }

}
