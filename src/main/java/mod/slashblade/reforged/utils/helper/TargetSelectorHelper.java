package mod.slashblade.reforged.utils.helper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: til
 * @Description:
 */
public class TargetSelectorHelper {

    /***
     * 搜寻目标
     */
    public static HitResult selector(Entity entity, float range, Entity... otherExclude) {
        Vec3 start = entity.getEyePosition(1.0F);
        Vec3 dir = entity.getLookAngle();
        Vec3 end = start.add(dir.scale(range));
        boolean hasOtherExclude = otherExclude != null && otherExclude.length > 0;
        List<Entity> otherEntityList = hasOtherExclude
                ? Arrays.asList(otherExclude)
                : null;

        BlockHitResult blockHitResult = entity.level().clip(
                new ClipContext(
                        start,
                        end,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        entity
                )
        );

        double entityReach = range;
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            end = blockHitResult.getLocation();
            entityReach = start.distanceTo(end);
        }

        // 使用ProjectileUtil进行实体射线追踪
        AABB area = entity.getBoundingBox().expandTowards(dir.scale(entityReach)).inflate(1.0D);

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                entity.level(),
                entity,
                start,
                end,
                area,
                target -> {
                    if (target == entity) {
                        return false;
                    }
                    if (hasOtherExclude && otherEntityList.contains(target)) {
                        return false;
                    }
                    return EntityPredicateHelper.canTarget(entity, target);
                }
        );

        if (entityHitResult != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
            return entityHitResult;
        }

        return blockHitResult;
    }


}
