package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.content.config.SbConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

/**
 * @Author: til
 * @Description: 实体间是否能攻击对方的判断
 */
public class EntityPredicateHelper {
    public static boolean canTarget(@Nullable Entity attacker, Entity target) {
        boolean allowInvulnerable = SbConfig.COMMON.allowInvulnerable.get();
        boolean isLivingEntity = SbConfig.COMMON.isLivingEntity.get();
        boolean friendlyFire = SbConfig.COMMON.friendlyFire.get();
        boolean skipAttackChecks = SbConfig.COMMON.skipAttackChecks.get();


        if (target == null) {
            return false;
        }
        if (attacker == target) {
            return false;
        }
        if (target.isSpectator()) {
            return false;
        }
        if (!target.isAlive()) {
            return false;
        }

        if (!allowInvulnerable && target.isInvulnerable()) {
            return false;
        }

        if (attacker == null) {
            return true;
        }

        if (isLivingEntity && !(target instanceof LivingEntity)) {
            return false;
        }

        if (!skipAttackChecks && attacker instanceof LivingEntity livingAttacker && target instanceof LivingEntity livingTarget) {
            if (!livingAttacker.canAttack(livingTarget)) {
                return false;
            }
        }

        if (!friendlyFire && attacker.isAlliedTo(target)) {
            return false;
        }

        return true;

    }
}
