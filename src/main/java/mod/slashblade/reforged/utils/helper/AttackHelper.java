package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.entity.SlashEffectEntity;
import mod.slashblade.reforged.content.event.SlashBladeAttackEvent;
import mod.slashblade.reforged.content.event.SlashBladeDoSlashEvent;
import mod.slashblade.reforged.content.event.SlashBladeDurabilityLoss;
import mod.slashblade.reforged.content.init.SbAttackTypes;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbEntityType;
import mod.slashblade.reforged.content.register.AttackType;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Vector3d;

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

    public static void doSlash(
            LivingEntity attacker,
            float roll,
            Vec3 centerOffset,
            double damage,
            float basicsRange,
            @Nullable Consumer<SlashEffectEntity> advanceOperation
    ) {
        if (damage == 0) {
            return;
        }

        ItemStack mainHandItem = attacker.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }

        if (!slashBladeLogic.canUse()) {
            return;
        }


        Vec3 pos = attacker.getPosition(1)
                .add(0.0D, (double) attacker.getEyeHeight() * 0.75D, 0.0D)
                .add(attacker.getLookAngle().scale(0.3f));

        pos = pos.add(VectorHelper.getVectorForRotation(-90.0F, attacker.getViewYRot(0)).scale(centerOffset.y))
                .add(VectorHelper.getVectorForRotation(0, attacker.getViewYRot(0) + 90).scale(centerOffset.z))
                .add(attacker.getLookAngle().scale(centerOffset.z));

        SlashEffectEntity jc = new SlashEffectEntity(
                SbEntityType.SLASH_EFFECT_ENTITY.get(),
                attacker.level(),
                attacker
        );

        slashBladeStyle.decorate(jc);

        jc.setPos(pos.x(), pos.y(), pos.z());
        jc.setRoll(roll);
        jc.setDamage(damage);
        jc.setSize(slashBladeLogic.getAttackDistance() * basicsRange);

        if (advanceOperation != null) {
            advanceOperation.accept(jc);
        }

        NeoForge.EVENT_BUS.post(new SlashBladeDoSlashEvent(mainHandItem, slashBladeLogic, attacker, jc));

        attacker.level().addFreshEntity(jc);
    }

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
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

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
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return;
        }

        if (!slashBladeLogic.canUse()) {
            return;
        }

        SlashBladeAttackEvent slashBladeAttackEvent = new SlashBladeAttackEvent(mainHandItem, slashBladeLogic, attacker, target, modifiedRatio, attackTypeList);
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

            if (list.isEmpty()) {
                list = List.of(
                        attacker.damageSources().source(
                                attacker instanceof Player
                                        ? DamageTypes.PLAYER_ATTACK
                                        : DamageTypes.MOB_ATTACK,
                                attacker
                        )
                );
            }

            List<DamageSource> finalList = list;
            list.forEach(damageSource -> target.hurt(damageSource, (float) attribute.getValue() / finalList.size()));

        } finally {
            attribute.removeModifier(am.id());
        }

        if (bypassesCooldown) {
            target.invulnerableTime = 0;
        }
    }

    public static void durabilityLoss(LivingEntity user, ItemStack itemStack, SlashBladeLogic slashBladeLogic, double loss) {

        if (loss <= 0) {
            return;
        }
        SlashBladeDurabilityLoss slashBladeDurabilityLoss = new SlashBladeDurabilityLoss(itemStack, slashBladeLogic, user, loss);
        NeoForge.EVENT_BUS.post(slashBladeDurabilityLoss);

        double durabilityReductionRate = Math.max(slashBladeDurabilityLoss.getDurabilityLevel() * SbConfig.COMMON.durabilityReductionRate.get(), 0);

        // -Sigmoid函数
        double modifiedRatio = 1 / (durabilityReductionRate + Math.pow(Math.E, -durabilityReductionRate));

        loss = slashBladeDurabilityLoss.getBasicLoss() * modifiedRatio;

        if (loss <= 0) {
            return;
        }

        slashBladeLogic.setDurable(slashBladeLogic.getDurable() - loss);

        if (slashBladeLogic.getDurable() <= 0) {
            slashBladeLogic.setDurable(0);
            //TODO 刀损坏
        }

        itemStack.set(SbDataComponentTypes.SLASH_BLADE_LOGIC, slashBladeLogic);


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
            event.addModifiedRatioAmplifier(SbConfig.COMMON.thousandKillAttackBonus.get());
        }
        if (slashBladeLogic.getKillCount() > 10000) {
            event.addModifiedRatioAmplifier(SbConfig.COMMON.tenThousandKillAttackBonus.get());
        }
        if (slashBladeLogic.getRefine() > 1000) {
            event.addModifiedRatioAmplifier(SbConfig.COMMON.thousandRefineAttackBonus.get());
        }
        if (slashBladeLogic.getRefine() > 10000) {
            event.addModifiedRatioAmplifier(SbConfig.COMMON.tenThousandRefineAttackBonus.get());
        }
    }

    @SubscribeEvent
    public static void durabilitySettlement(SlashBladeAttackEvent event) {
        if (!event.getAttackTypeList().contains(SbAttackTypes.SLASH_BLADE_ATTACK_TYPE.get())) {
            return;
        }

        durabilityLoss(event.getUser(), event.getItem(), event.getSlashBladeLogic(), event.getBasicsModifiedRatio() * SbConfig.COMMON.durabilityLoss.get());
    }


}
