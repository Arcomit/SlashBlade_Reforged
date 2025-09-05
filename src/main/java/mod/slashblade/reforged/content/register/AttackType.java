package mod.slashblade.reforged.content.register;

import lombok.AllArgsConstructor;
import mod.slashblade.reforged.utils.ICreateDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/**
 * @Author: til
 * @Description: 攻击类型
 */
@AllArgsConstructor
public class AttackType implements ICreateDamageSource {

    final ICreateDamageSource iCreateDamageSource;

    public AttackType() {
        this.iCreateDamageSource = (attacker, target) -> null;
    }

    @Override
    public DamageSource createDamageSource(LivingEntity attacker, Entity target) {
        return iCreateDamageSource.createDamageSource(attacker, target);
    }

}
