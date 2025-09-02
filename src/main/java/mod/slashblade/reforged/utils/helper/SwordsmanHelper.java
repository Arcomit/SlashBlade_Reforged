package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * @Author: t
 * il
 * @Description: 剑客处理
 */
public class SwordsmanHelper {

    
    public static Vec3 getAttackPos(LivingEntity livingEntity) {
        SlashBladeLogic slashBladeLogic = livingEntity.getMainHandItem().get(SbDataComponents.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return Vec3.ZERO;
        }

        Entity targetEntity = slashBladeLogic.getTargetEntity(livingEntity.level());

        if (targetEntity != null) {
            return EntityHelper.getEntityPosition(targetEntity);
        }

        return TargetSelectorHelper.selector(livingEntity, 64).getLocation();
    }


}
