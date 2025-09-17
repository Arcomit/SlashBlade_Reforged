package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.entity.SlashEffectEntity;
import mod.slashblade.reforged.content.event.*;
import mod.slashblade.reforged.content.init.SbAttackTypes;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbEntityType;
import mod.slashblade.reforged.content.register.AttackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        NeoForge.EVENT_BUS.post(new SlashEvent(mainHandItem, slashBladeLogic, attacker, jc));

        attacker.level().addFreshEntity(jc);
    }

    /***
     * 范围攻击
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


        List<Entity> list = EntityHelper.getTargettableEntitiesWithinAABB(attacker.level(), attacker, pos, range).stream()
                .filter(e -> !exclude.contains(e))
                .peek(beforeHit)
                .peek(e -> doAttack(attacker, e, modifiedRatio, attackTypeList))
                .toList();
        if (!list.isEmpty()) {
            NeoForge.EVENT_BUS.post(new AreaAttackEvent(mainHandItem, slashBladeLogic, attacker, modifiedRatio, list, attackTypeList));
        }
        return list;

    }


    /***
     * 简单单次攻击
     */
    public static void doAttack(LivingEntity attacker, Entity target, double modifiedRatio, List<AttackType> attackTypeList) {
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

        AttackEvent attackEvent = new AttackEvent(
                mainHandItem, slashBladeLogic, attacker, target, modifiedRatio, attackTypeList,
                attackTypeList.stream()
                        .map(a -> a.createDamageSource(attacker, target))
                        .filter(Objects::nonNull).collect(Collectors.toList())
        );
        NeoForge.EVENT_BUS.post(attackEvent);

        target.invulnerableTime = 0;


        AttributeInstance attribute = attacker.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attribute == null) {
            return;
        }

        double harm = attribute.getValue() * attackEvent.getUltimatelyModifiedRatio();


        List<AttackEvent.DamageSourceInfo> list = attackEvent.getDamageSourceInfoList();
        if (list.isEmpty()) {
            return;
        }

        list.forEach(info -> {
                    target.invulnerableTime = 0;
                    target.hurt(info.damageSource(), (float) (harm * info.damage()));
                }
        );


    }

    public static void durabilityLoss(LivingEntity user, ItemStack itemStack, SlashBladeLogic slashBladeLogic, double loss) {

        if (loss <= 0) {
            return;
        }

        // 创造模式不消耗耐久
        if (user instanceof Player player && player.getAbilities().instabuild) {
            return;
        }

        DurabilityLossEvent slashBladeDurabilityLossEvent = new DurabilityLossEvent(itemStack, slashBladeLogic, user, loss);
        NeoForge.EVENT_BUS.post(slashBladeDurabilityLossEvent);

        double durabilityReductionRate = Math.max(slashBladeDurabilityLossEvent.getDurabilityLevel() * SbConfig.COMMON.durabilityReductionRate.get(), 0);

        // -Sigmoid+1
        double modifiedRatio = 1 / (durabilityReductionRate + Math.pow(Math.E, -durabilityReductionRate));

        loss = slashBladeDurabilityLossEvent.getBasicLoss() * modifiedRatio;

        if (loss <= 0) {
            return;
        }

        AtomicBoolean lost = new AtomicBoolean(false);

        double finalLoss = loss;
        itemStack.update(
                SbDataComponentTypes.SLASH_BLADE_LOGIC,
                SlashBladeLogic.DEF,
                s -> {
                    SlashBladeLogic.SlashBladeLogicBuilder builder = s.toBuilder();
                    double to = s.getDurable() - finalLoss;

                    if (to <= 0) {
                        to = 0;
                        builder.broken(true);

                        //user.level().broadcastEntityEvent(user, entityEventForEquipmentBreak(EquipmentSlot.MAINHAND));
                        user.level().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BREAK, SoundSource.MASTER, 1, 1);
                        NeoForge.EVENT_BUS.post(new BrokenEvent(itemStack, slashBladeLogic, user, builder));

                        if (s.isFragile()) {
                            lost.set(true);
                        }

                    }

                    builder.durable(to);

                    return builder.build();
                }
        );


        if (lost.get()) {
            user.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

    }


    /***
     * 默认倍率添加
     */
    @SubscribeEvent
    public static void onSlashBladeAttackEvent(AttackEvent event) {

        SlashBladeLogic slashBladeLogic = event.getSlashBladeLogic();

        // TODO 评分表加成

        event.addModifiedRatioAmplifier(SbConfig.COMMON.refineAttackBonus.get() * slashBladeLogic.getRefine());

        if (slashBladeLogic.getKill() > 1000) {
            event.addMechanismModifiedRatioAmplifier(SbConfig.COMMON.thousandKillAttackBonus.get());
        }
        if (slashBladeLogic.getKill() > 10000) {
            event.addMechanismModifiedRatioAmplifier(SbConfig.COMMON.tenThousandKillAttackBonus.get());
        }
        if (slashBladeLogic.getRefine() > 1000) {
            event.addMechanismModifiedRatioAmplifier(SbConfig.COMMON.thousandRefineAttackBonus.get());
        }
        if (slashBladeLogic.getRefine() > 10000) {
            event.addMechanismModifiedRatioAmplifier(SbConfig.COMMON.tenThousandRefineAttackBonus.get());
        }
    }

    @SubscribeEvent
    public static void durabilitySettlement(AreaAttackEvent event) {
        if (!event.getAttackTypeList().contains(SbAttackTypes.SLASH_BLADE_ATTACK_TYPE.get())) {
            return;
        }

        durabilityLoss(event.getUser(), event.getItem(), event.getSlashBladeLogic(), event.getModifiedRatio() * SbConfig.COMMON.durabilityLoss.get() * event.getTarget().size());
    }

}
