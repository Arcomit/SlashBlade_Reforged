package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * @Author: til
 * @Description: 剑客处理
 */
public class SwordsmanHelper {


    public static Vec3 getAttackPos(LivingEntity livingEntity) {

        if (livingEntity == null) {
            return Vec3.ZERO;
        }

        return getAttackPos(livingEntity, livingEntity.getMainHandItem().get(SbDataComponentTypes.SLASH_BLADE_LOGIC));
    }

    public static Vec3 getAttackPos(LivingEntity livingEntity, SlashBladeLogic slashBladeLogic) {

        if (livingEntity == null || slashBladeLogic == null) {
            return Vec3.ZERO;
        }

        Entity targetEntity = slashBladeLogic.getTargetEntity(livingEntity.level());

        if (targetEntity != null) {
            return EntityHelper.getEntityPosition(targetEntity);
        }

        HitResult selector = TargetSelectorHelper.selector(livingEntity, 64);

        if (selector instanceof EntityHitResult entityHitResult) {
            return EntityHelper.getEntityPosition(entityHitResult.getEntity());
        }

        return selector.getLocation();
    }

}
