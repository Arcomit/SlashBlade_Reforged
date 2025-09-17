package mod.slashblade.reforged.content.entity;


import com.mojang.datafixers.kinds.IdF;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import lombok.extern.slf4j.Slf4j;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeMaterial;
import mod.slashblade.reforged.content.data.context.SlashBladeContext;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.content.init.SbRegistrys;
import mod.slashblade.reforged.content.register.SpecialEffect;
import mod.slashblade.reforged.utils.tuple.Tuple2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class BladeStandEntity extends ItemFrame {
    public static final RandomSource random = RandomSource.create();

    protected static final EntityDataAccessor<ItemStack> CURRENT_TYPE = SynchedEntityData.defineId(BladeStandEntity.class, EntityDataSerializers.ITEM_STACK);

    public BladeStandEntity(EntityType<? extends ItemFrame> entityType, Level level, ItemStack currentType) {
        super(entityType, level);
        if (currentType != null) {
            setCurrentType(currentType);
        }
    }

    public BladeStandEntity(EntityType<? extends ItemFrame> entityType, Level level, ItemStack itemStack, BlockPos placePos, Direction dir) {
        this(entityType, level, itemStack);

        pos = placePos;
        setDirection(dir);
    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CURRENT_TYPE, new ItemStack(SbItems.BLADESTAND_1.get()));
    }

    public ItemStack getCurrentType() {
        return this.entityData.get(CURRENT_TYPE);
    }

    public void setCurrentType(ItemStack currentType) {
        this.entityData.set(CURRENT_TYPE, currentType);
    }

    @Override
    protected @NotNull ItemStack getFrameItemStack() {
        return getCurrentType();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (level().isClientSide()) {
            return false;
        }

        if (this.isInvulnerableTo(source)) {
            return false;
        }

        if (!source.is(DamageTypeTags.IS_PLAYER_ATTACK)) {
            return false;
        }

        Entity entity = source.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) {
            return false;
        }

        ItemStack inputItemStack = livingEntity.getMainHandItem();
        ItemStack placedItemStack = getItem();

        // 删除实体
        if (inputItemStack.isEmpty() && placedItemStack.isEmpty() && livingEntity.isShiftKeyDown()) {
            this.discard();
            ItemEntity itemEntity = new ItemEntity(
                    level(), getX(), getY(), getZ(), getCurrentType().copy()
            );
            level().addFreshEntity(itemEntity);
            return true;
        }

        if (inputItemStack.isEmpty() || inputItemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC) != null) {
            setItem(inputItemStack.copy());
            livingEntity.setItemInHand(InteractionHand.MAIN_HAND, placedItemStack.copy());
            return true;
        }

        if (placedItemStack.isEmpty()) {
            return false;
        }

        // 复制SA
        if (tryCopySa(inputItemStack, placedItemStack, livingEntity)) {
            return true;
        }

        // 制作 耀偏方三八面体
        if (tryMakeTrapezohedron(inputItemStack, placedItemStack, livingEntity)) {
            return true;
        }

        // 提取SE
        if (tryExtractSe(inputItemStack, placedItemStack, livingEntity)) {
            return true;
        }

        // 提升SE
        if (tryUpgradeSe(inputItemStack, placedItemStack, livingEntity)) {
            return true;
        }

        // 提升附魔
        if (tryUpgradeEnchantment(inputItemStack, placedItemStack, livingEntity)) {
            return true;
        }


        return false;
    }

    public boolean tryCopySa(ItemStack inputItemStack, ItemStack placedItemStack, LivingEntity livingEntity) {
        Level level = livingEntity.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        if (!inputItemStack.is(SbItems.PROUD_SOUL_SPHERE.get())) {
            return false;
        }

        SlashBladeLogic slashBladeLogic = inputItemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return false;
        }

        if (slashBladeLogic.getSa() == null) {
            return false;
        }

        ItemEnchantments tagEnchantments = inputItemStack.getTagEnchantments();
        if (tagEnchantments.isEmpty()) {
            return false;
        }
        Optional<Object2IntMap.Entry<Holder<Enchantment>>> any = tagEnchantments.entrySet().stream().findAny();
        if (any.isEmpty()) {
            return false;
        }

        Holder<Enchantment> key = any.get().getKey();
        int maxLevel = key.value().getMaxLevel();

        int held = placedItemStack.getEnchantmentLevel(key);

        if (held < maxLevel) {
            return false;
        }

        float successRate = 0.5f; //TODO 配置字段
        boolean success = level().random.nextFloat() < successRate;

        if (success) {
            ItemStack output = new ItemStack(SbItems.PROUD_SOUL_SPHERE.get());
            output.set(SbDataComponentTypes.SA, slashBladeLogic.getSa());
            ItemEntity itemEntity = new ItemEntity(
                    serverLevel, getX(), getY(), getZ(), output
            );
            serverLevel.addFreshEntity(itemEntity);
        }

        generatedEffect(success, serverLevel);

        inputItemStack.consume(1, livingEntity);
        return true;

    }

    public boolean tryMakeTrapezohedron(ItemStack inputItemStack, ItemStack placedItemStack, LivingEntity livingEntity) {
        Level level = livingEntity.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        if (!inputItemStack.is(SbItems.PROUD_SOUL_CRYSTAL.get())) {
            return false;
        }

        if (isOnFire()) {
            return false;
        }

        SlashBladeLogic slashBladeLogic = placedItemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return false;
        }

        int consumptionProudSoul = 5000; //TODO 配置字段
        if (slashBladeLogic.getProudSoul() < consumptionProudSoul) {
            return false;
        }

        placedItemStack.update(
                SbDataComponentTypes.SLASH_BLADE_LOGIC,
                SlashBladeLogic.DEF,
                s -> s.toBuilder()
                        .proudSoul(s.getProudSoul() - consumptionProudSoul)
                        .build()
        );

        ItemStack itemStack = new ItemStack(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get());
        EnchantmentHelper.setEnchantments(itemStack, inputItemStack.getTagEnchantments());

        ItemEntity itemEntity = new ItemEntity(
                serverLevel, getX(), getY(), getZ(), itemStack
        );
        serverLevel.addFreshEntity(itemEntity);

        generatedEffect(true, serverLevel);

        inputItemStack.consume(1, livingEntity);

        return true;
    }

    public boolean tryExtractSe(ItemStack inputItemStack, ItemStack placedItemStack, LivingEntity livingEntity) {
        Level level = livingEntity.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        if (!inputItemStack.is(SbItems.PROUD_SOUL_CRYSTAL.get())) {
            return false;
        }

        if (inputItemStack.getTagEnchantments().isEmpty()) {
            return false;
        }

        if (inputItemStack.get(SbDataComponentTypes.SE) != null) {
            return false;
        }

        SlashBladeLogic slashBladeLogic = placedItemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return false;
        }

        Optional<Map.Entry<SpecialEffect, Integer>> first = slashBladeLogic.getSe().entrySet()
                .stream()
                .filter(e -> e.getKey().isReplicable())
                .min(Comparator.comparingInt(e -> SbRegistrys.SPECIAL_EFFECT_REGISTRY.getId(e.getKey())));

        if (first.isEmpty()) {
            return false;
        }

        Map.Entry<SpecialEffect, Integer> specialEffectIntegerEntry = first.get();

        placedItemStack.update(
                SbDataComponentTypes.SLASH_BLADE_LOGIC,
                SlashBladeLogic.DEF,
                s -> s.toBuilder()
                        .se(
                                s.getSe()
                                        .entrySet()
                                        .stream()
                                        .map(
                                                e -> new Tuple2<>(
                                                        e.getKey(),
                                                        e.getKey().equals(specialEffectIntegerEntry.getKey())
                                                                ? e.getValue() - 1
                                                                : e.getValue()
                                                )
                                        )
                                        .collect(Collectors.toMap(Tuple2::getA, Tuple2::getB))
                        )
                        .build()
        );

        ItemStack itemStack = new ItemStack(SbItems.PROUD_SOUL_CRYSTAL.get());
        itemStack.set(SbDataComponentTypes.SE, specialEffectIntegerEntry.getKey());
        ItemEntity itemEntity = new ItemEntity(
                serverLevel, getX(), getY(), getZ(), itemStack
        );
        serverLevel.addFreshEntity(itemEntity);

        generatedEffect(true, serverLevel);

        inputItemStack.consume(1, livingEntity);

        return true;
    }

    public boolean tryUpgradeSe(ItemStack inputItemStack, ItemStack placedItemStack, LivingEntity livingEntity) {
        Level level = livingEntity.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        if (!inputItemStack.is(SbItems.PROUD_SOUL_CRYSTAL.get())) {
            return false;
        }

        SpecialEffect se = inputItemStack.get(SbDataComponentTypes.SE);
        if (se == null) {
            return false;
        }

        SlashBladeLogic slashBladeLogic = placedItemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return false;
        }

        if (slashBladeLogic.getSe().getOrDefault(se, 0) >= se.getMaxLevel()) {
            return false;
        }

        int maxLevel = slashBladeLogic.getSe().values().stream().mapToInt(i -> i).sum();

        double successReductionRate = maxLevel * 0.2; // TODO 配置字段

        // -Sigmoid+1
        double successRate = 1 / (successReductionRate + Math.pow(Math.E, -successReductionRate));

        boolean success = random.nextDouble() < successRate;

        if (success) {
            placedItemStack.update(
                    SbDataComponentTypes.SLASH_BLADE_LOGIC,
                    SlashBladeLogic.DEF,
                    s -> {
                        Map<SpecialEffect, Integer> map = new HashMap<>(s.getSe());
                        map.put(se, map.getOrDefault(se, 0) + 1);
                        return s.toBuilder()
                                .se(map)
                                .build();
                    }
            );
        }

        generatedEffect(success, serverLevel);

        inputItemStack.consume(1, livingEntity);

        return true;
    }

    public boolean tryUpgradeEnchantment(ItemStack inputItemStack, ItemStack placedItemStack, LivingEntity livingEntity) {
        Level level = livingEntity.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        SlashBladeMaterial slashBladeMaterial = inputItemStack.get(SbDataComponentTypes.SLASH_BLADE_MATERIAL);
        if (slashBladeMaterial == null) {
            return false;
        }

        ItemEnchantments tagEnchantments = inputItemStack.getTagEnchantments();
        if (tagEnchantments.isEmpty()) {
            return false;
        }

        Optional<Object2IntMap.Entry<Holder<Enchantment>>> first = tagEnchantments.entrySet().stream().findFirst();
        if (first.isEmpty()) {
            return false;
        }

        Holder<Enchantment> enchantment = first.get().getKey();

        ItemEnchantments placedTagEnchantments = placedItemStack.getTagEnchantments();

        if (placedTagEnchantments.getLevel(enchantment) >= enchantment.value().getMaxLevel()) {
            return false;
        }

        int maxLevel = placedTagEnchantments.keySet().stream().mapToInt(placedTagEnchantments::getLevel).sum();

        double successReductionRate = maxLevel * 1d * (1d / slashBladeMaterial.getEnchantedSuccessRate()); // TODO 配置字段

        // -Sigmoid+1
        double successRate = 1 / (successReductionRate + Math.pow(Math.E, -successReductionRate));

        boolean success = random.nextDouble() < successRate;

        if (success) {
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(placedTagEnchantments);
            mutable.set(enchantment, mutable.getLevel(enchantment) + 1);
            EnchantmentHelper.setEnchantments(placedItemStack, mutable.toImmutable());
        }

        generatedEffect(success, serverLevel);

        inputItemStack.consume(1, livingEntity);

        return true;
    }

    public void generatedEffect(boolean success, ServerLevel serverLevel) {

        playSound(SoundEvents.GLASS_BREAK, 1f, 1f);

        serverLevel.sendParticles(
                success
                        ? ParticleTypes.CRIT
                        : ParticleTypes.CLOUD,
                this.getX(), this.getY(), this.getZ(), 16, 0.5, 0.5, 0.5, 0.25f
        );

    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        if (level().isClientSide()) {
            return InteractionResult.PASS;
        }

        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        ItemStack inputItemStack = player.getMainHandItem();
        ItemStack placedItemStack = getItem();

        if (inputItemStack.isEmpty() && placedItemStack.isEmpty()) {
            this.setRotation(this.getRotation() + 1);
            this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        if (inputItemStack.isEmpty() || inputItemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC) != null) {
            setItem(inputItemStack.copy());
            player.setItemInHand(InteractionHand.MAIN_HAND, placedItemStack.copy());

            this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);
            return InteractionResult.CONSUME;
        }

        if (inputItemStack.isEmpty() && placedItemStack.isEmpty() && player.isShiftKeyDown()) {
            this.discard();
            ItemEntity itemEntity = new ItemEntity(
                    level(), getX(), getY(), getZ(), getCurrentType().copy()
            );
            level().addFreshEntity(itemEntity);
            return InteractionResult.SUCCESS;
        }

        this.setRotation(this.getRotation() + 1);
        this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);

        return InteractionResult.SUCCESS;
    }
}
