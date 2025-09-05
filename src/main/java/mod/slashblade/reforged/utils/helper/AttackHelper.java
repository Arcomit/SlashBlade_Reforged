package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.event.SlashBladeAttackEvent;
import mod.slashblade.reforged.content.init.SbDataComponents;
import mod.slashblade.reforged.content.register.AttackType;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @Author: til
 * @Description: 通用攻击处理逻辑
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class AttackHelper {

    /***
     * 访问攻击
     */
    public static List<Entity> areaAttack(
            LivingEntity attacker,
            Vec3 pos,
            Consumer<Entity> beforeHit,
            float range,
            double modifiedRatio,
            boolean bypassesCooldown,
            Set<Entity> exclude,
            List<AttackType> attackTypeList
    ) {


        if (modifiedRatio == 0) {
            return List.of();
        }

        ItemStack mainHandItem = attacker.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponents.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return List.of();
        }

        if (!slashBladeLogic.canUse()) {
            return List.of();
        }


        return EntityHelper.getTargettableEntitiesWithinAABB(attacker.level(), attacker, pos, range).stream()
                .filter(e -> !exclude.contains(e))
                .peek(beforeHit)
                .peek(e -> doAttack(attacker, e, modifiedRatio, bypassesCooldown, attackTypeList))
                .toList();

    }


    /***
     * 简单单次攻击
     */
    public static void doAttack(LivingEntity attacker, Entity target, double modifiedRatio, boolean bypassesCooldown, List<AttackType> attackTypeList) {
        if (modifiedRatio == 0) {
            return;
        }

        ItemStack mainHandItem = attacker.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponents.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return;
        }

        if (!slashBladeLogic.canUse()) {
            return;
        }

        SlashBladeAttackEvent slashBladeAttackEvent = new SlashBladeAttackEvent(attacker, target, mainHandItem, slashBladeLogic, modifiedRatio, attackTypeList);
        NeoForge.EVENT_BUS.post(slashBladeAttackEvent);

        if (bypassesCooldown) {
            target.invulnerableTime = 0;
        }

        AttributeModifier am = new AttributeModifier(
                ResourceLocationConstants.SLASH_BLADE_ATTACK_MULTIPLIED_TOTAL,
                (slashBladeAttackEvent.getUltimatelyModifiedRatio()) - 1,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        AttributeInstance attribute = attacker.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attribute == null) {
            return;
        }

        try {
            attribute.addTransientModifier(am);

            /*if (attacker instanceof Player player) {
                player.attack(target);
            } else {
                DamageSource damageSource = attacker.damageSources().mobAttack(attacker);
                target.hurt(damageSource, (float) attribute.getValue());
            }*/

            List<DamageSource> list = attackTypeList.stream()
                    .map(a -> a.createDamageSource(attacker, target))
                    .filter(Objects::nonNull)
                    .toList();


            list.forEach(damageSource -> target.hurt(damageSource, (float) attribute.getValue() / list.size()));

        } finally {
            attribute.removeModifier(am.id());
        }

        if (bypassesCooldown) {
            target.invulnerableTime = 0;
        }

        //TODO ArrowReflector.doReflect(target, attacker);
        //TODO TNTExtinguisher.doExtinguishing(target, attacker);
    }

    /***
     * 默认倍率添加
     */
    @SubscribeEvent
    public static void onSlashBladeAttackEvent(SlashBladeAttackEvent event) {

        SlashBladeLogic slashBladeLogic = event.getSlashBladeLogic();

        // TODO 评分表加成

        event.addModifiedRatio(SbConfig.COMMON.refineAttackBonus.get() * slashBladeLogic.getRefine());

        if (slashBladeLogic.getKillCount() > 1000) {
            event.addModifiedRatioAmplifier(SbConfig.COMMON.thousandKillReward.get());
        }
        if (slashBladeLogic.getKillCount() > 10000) {
            event.addModifiedRatioAmplifier(SbConfig.COMMON.tenThousandKillReward.get());
        }
        if (slashBladeLogic.getRefine() > 1000) {
            event.addModifiedRatioAmplifier(SbConfig.COMMON.thousandRefineReward.get());
        }
        if (slashBladeLogic.getRefine() > 10000) {
            event.addModifiedRatioAmplifier(SbConfig.COMMON.tenThousandRefineReward.get());
        }
    }
}
