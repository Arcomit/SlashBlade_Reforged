package mod.slashblade.reforged.content.register;

import lombok.AllArgsConstructor;
import mod.slashblade.reforged.content.event.AttackEvent;
import mod.slashblade.reforged.utils.ICreateDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * @Author: til
 * @Description: 攻击类型
 */
@AllArgsConstructor
public class AttackType implements ICreateDamageSource {

    final ICreateDamageSource iCreateDamageSource;

    public AttackType() {
        this.iCreateDamageSource = (attacker, target) -> new AttackEvent.DamageSourceInfo(
                attacker.damageSources().source(
                        attacker instanceof Player
                                ? DamageTypes.PLAYER_ATTACK
                                : DamageTypes.MOB_ATTACK,
                        attacker
                ),
                1
        );
    }

    @Override
    public AttackEvent.DamageSourceInfo createDamageSource(LivingEntity attacker, Entity target) {
        return iCreateDamageSource.createDamageSource(attacker, target);
    }

}
