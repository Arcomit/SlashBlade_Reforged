package mod.slashblade.reforged.content.event;

import lombok.Getter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.register.AttackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class AreaAttackEvent extends SlashBladeEvent {

    LivingEntity attacker;
    List<Entity> target;
    double modifiedRatio;
    final List<AttackType> attackTypeList;

    public AreaAttackEvent(ItemStack item, SlashBladeLogic slashBladeLogic, @Nullable LivingEntity attacker,  double modifiedRatio, List<Entity> target, List<AttackType> attackTypeList) {
        super(item, slashBladeLogic, attacker);
        this.attackTypeList = attackTypeList;
        this.modifiedRatio = modifiedRatio;
        this.target = target;
        this.attacker = attacker;
    }
}
