package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.capability.ILockTarget;
import mod.slashblade.reforged.content.capability.IInputCapability;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.content.event.key.KeyInputEvent;
import mod.slashblade.reforged.content.event.key.KeyUpdateEvent;
import mod.slashblade.reforged.content.init.SbAttachmentTypes;
import mod.slashblade.reforged.content.init.SbCapabilities;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.Comparator;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class KeyHelper {

    @SubscribeEvent
    public static void onSummoningSummondSword(KeyInputEvent event) {
        if (event.getKeyInput() != KeyInput.SUMMONING_SUMMOND_SWORD) {
            return;
        }

        if (event.getKeyType() != KeyInputEvent.KeyType.DOWN) {
            return;
        }

        LivingEntity livingEntity = event.getLivingEntity();

        if (livingEntity.level().isClientSide()) {
            return;
        }

        ItemStack mainHandItem = livingEntity.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }

        //TODO 写入配置
        if (!canBeReleased(mainHandItem, slashBladeLogic, livingEntity, 2, true)) {
            return;
        }

        Level level = event.getLivingEntity().level();
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        int enchantmentLevel = mainHandItem.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.MULTISHOT));
        enchantmentLevel = Math.min(enchantmentLevel, 5); // TODO 写入配置
        int count = 1 + enchantmentLevel; // TODO 写入配置 每级增加的数量

        int piercingLevel = mainHandItem.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.PIERCING));
        int piercing = (int) (piercingLevel * 1f); // TODO 写入配置 每级增加穿透的数量

        for(int i = 0; i < count; i++) {
            SummondSwordEntity summondSwordEntity = new SummondSwordEntity(SbEntityType.SUMMOND_SWORD_ENTITY.get(), livingEntity.level(), livingEntity);
            slashBladeStyle.decorate(summondSwordEntity);
            summondSwordEntity.setDamage(SbConfig.COMMON.ordinaryAttack.get());
            summondSwordEntity.setMaxLifeTime(100);
            summondSwordEntity.lookAt(SwordsmanHelper.getAttackPos(livingEntity, slashBladeLogic), false);
            summondSwordEntity.setMaxPierce(piercing);
            livingEntity.level().addFreshEntity(summondSwordEntity);
            livingEntity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.2F, 1.45F);
        }

    }


    @SubscribeEvent
    public static void onSwordSkills(KeyInputEvent event) {
        if (event.getKeyInput() != KeyInput.SUMMONING_SUMMOND_SWORD) {
            return;
        }

        if (event.getKeyType() != KeyInputEvent.KeyType.HOLD) {
            return;
        }

        if (event.getLivingEntity().level().isClientSide()) {
            return;
        }

        IInputCapability playerInputCapability = event.getPlayerInputCapability();

        if (!playerInputCapability.isDown(KeyInput.SNEAK)) {
            return;
        }

        LivingEntity livingEntity = event.getLivingEntity();

        ItemStack mainHandItem = livingEntity.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }

        //TODO 写入配置
        if (!canBeReleased(mainHandItem, slashBladeLogic, livingEntity, 20, true)) {
            return;
        }

        Level level = event.getLivingEntity().level();
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        int enchantmentLevel = mainHandItem.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.MULTISHOT));
        enchantmentLevel = Math.min(enchantmentLevel, 5); // TODO 写入配置

        int piercingLevel = mainHandItem.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.PIERCING));
        int piercing = (int) (piercingLevel * 1f); // TODO 写入配置 每级增加穿透的数量

        livingEntity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.2F, 1.45F);

        Vec3 attackPos = SwordsmanHelper.getAttackPos(livingEntity, slashBladeLogic);

        // 剑雨
        if (playerInputCapability.isDown(KeyInput.FORWARD) && playerInputCapability.isDown(KeyInput.BACK)) {

            Vec3 offset = new Vec3(
                    SbConfig.COMMON.heavyRainOffsetX.get(),
                    SbConfig.COMMON.heavyRainOffsetY.get(),
                    SbConfig.COMMON.heavyRainOffsetZ.get()
            );
            RandomSource random = event.getLivingEntity().getRandom();

            int amount = SbConfig.COMMON.heavyRainAttackNumber.get() + 8 * enchantmentLevel; // TODO 写入配置

            for(int i = 0; i < amount; i++) {
                Vec3 pos = attackPos.add(
                        -offset.x() / 2 + random.nextDouble() * offset.x(),
                        (-offset.y() / 2 + random.nextDouble() * offset.y()) + SbConfig.COMMON.heavyRainYOffset.get(),
                        -offset.z() / 2 + random.nextDouble() * offset.z()
                );


                Vec3 singleAttackPos = attackPos.add(
                        -offset.x() / 2 + random.nextDouble() * offset.x(),
                        -offset.y() / 2 + random.nextDouble() * offset.y(),
                        -offset.z() / 2 + random.nextDouble() * offset.z()
                );
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        livingEntity.level(),
                        livingEntity
                );

                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setDamage(SbConfig.COMMON.heavyRainAttack.get());
                summondSwordEntity.setStartDelay(random.nextInt(10));
                summondSwordEntity.setBreakDelay(10);
                summondSwordEntity.setPos(pos.x(), pos.y(), pos.z());
                summondSwordEntity.lookAt(
                        singleAttackPos,
                        false
                );
                summondSwordEntity.setMaxPierce(piercing);
                summondSwordEntity.setRoll(random.nextInt(360));
                livingEntity.level().addFreshEntity(summondSwordEntity);
            }

            return;
        }

        // 猛烈
        if (playerInputCapability.isDown(KeyInput.FORWARD)) {
            int amount = SbConfig.COMMON.blisteringAttackNumber.get() + 4 * enchantmentLevel; // TODO 写入配置
            for(int i = 0; i < amount; i++) {
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        livingEntity.level(),
                        livingEntity
                );
                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setDamage(SbConfig.COMMON.stormSwordAttack.get());
                summondSwordEntity.setMaxLifeTime(100);
                summondSwordEntity.setStartDelay((i / 2) * 2);
                Vec3 pos = livingEntity.getEyePosition(1.0f)
                        .add(
                                VectorHelper.getVectorForRotation(
                                                0.0f,
                                                livingEntity.getViewYRot(0) + 90)
                                        .scale(
                                                i % 2 == 0
                                                        ? 1
                                                        : -1
                                        )
                        );
                summondSwordEntity.setMaxPierce(piercing);
                summondSwordEntity.setPos(pos.x(), pos.y() + ofBlisteringOffset(i, amount), pos.z());
                summondSwordEntity.lookAt(attackPos, false);
                livingEntity.level().addFreshEntity(summondSwordEntity);
            }
            return;
        }

        // 风暴
        if (playerInputCapability.isDown(KeyInput.BACK)) {

            int amount = SbConfig.COMMON.stormSwordAttackNumber.get() + 4 * enchantmentLevel; // TODO 写入配置
            double stepping = Math.PI * 2 / amount;

            for(int i = 0; i < amount; i++) {
                double offsetX = Math.sin(stepping * i);
                double offsetZ = Math.cos(stepping * i);
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        livingEntity.level(),
                        livingEntity
                );
                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setStartDelay(5);
                summondSwordEntity.setDamage(SbConfig.COMMON.stormSwordAttack.get());

                summondSwordEntity.setPos(
                        attackPos.x() + offsetX * 5,
                        attackPos.y(),
                        attackPos.z() + offsetZ * 5
                );
                summondSwordEntity.setMaxPierce(piercing);
                summondSwordEntity.lookAt(attackPos, false);

                livingEntity.level().addFreshEntity(summondSwordEntity);
            }
            return;
        }

        // 螺旋
        {
            int amount = SbConfig.COMMON.spiralSwordAttackNumber.get() + 4 * enchantmentLevel; // TODO 写入配置
            double stepping = Math.PI * 2 / amount;

            Vec3 pos = livingEntity.getPosition(1);

            for(int i = 0; i < amount; i++) {
                double offsetX = Math.sin(stepping * i);
                double offsetZ = Math.cos(stepping * i);
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        livingEntity.level(),
                        livingEntity
                );
                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setDamage(SbConfig.COMMON.spiralSwordAttack.get());
                summondSwordEntity.setStartDelay(5);
                summondSwordEntity.setPos(
                        pos.x() + offsetX * 3,
                        pos.y(),
                        pos.z() + offsetZ * 3
                );
                summondSwordEntity.setMaxPierce(piercing);
                float yaw = livingEntity.getYRot();
                yaw = yaw / 180;
                yaw += (float) (stepping * i);
                Vec3 lookAtPosRotated = new Vec3(Math.sin(yaw), 0, Math.cos(yaw));
                summondSwordEntity.lookAt(lookAtPosRotated, true);
                livingEntity.level().addFreshEntity(summondSwordEntity);
            }
        }

    }

    public static boolean canBeReleased(ItemStack itemStack, SlashBladeLogic slashBladeLogic, LivingEntity attacker, int proudSoulConsumption, boolean consumption) {
        Level level = attacker.level();
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        int powerLevel = itemStack.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.POWER));
        if (powerLevel <= 0) {
            return false;
        }

        if (slashBladeLogic.getProudSoul() < proudSoulConsumption) {
            return false;
        }

        if (consumption) {
            int enchantmentLevel = itemStack.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.INFINITY));
            if (enchantmentLevel <= 0) {
                itemStack.update(
                        SbDataComponentTypes.SLASH_BLADE_LOGIC,
                        SlashBladeLogic.DEF,
                        s -> s.toBuilder()
                                .proudSoul(s.getProudSoul() - proudSoulConsumption)
                                .build()
                );
            }
        }

        return true;
    }


    public static float ofBlisteringOffset(int i, int maxAmount) {
        float maxOffset = 0.5f;
        float minOffset = -0.25f;
        float ratio = (float) i / (maxAmount - 1);
        return maxOffset - ratio * (maxOffset - minOffset);
    }

    @SubscribeEvent
    public static void onTeleportation(KeyInputEvent event) {

        if (event.getLivingEntity().level().isClientSide()) {
            return;
        }

        if (event.getKeyType() != KeyInputEvent.KeyType.DOWN) {
            return;
        }

        LivingEntity livingEntity = event.getLivingEntity();

        ItemStack mainHandItem = livingEntity.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }

        livingEntity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.2F, 1.45F);

        IInputCapability playerInputCapability = event.getPlayerInputCapability();
        if (
                !playerInputCapability.isDown(KeyInput.FORWARD)
                        || !playerInputCapability.isDown(KeyInput.SNEAK)
                        || !playerInputCapability.isDown(KeyInput.SPECIAL_OPERATION)
        ) {
            return;
        }

        SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                livingEntity.level(),
                livingEntity
        );

        slashBladeStyle.decorate(summondSwordEntity);
        summondSwordEntity.setDamage(SbConfig.COMMON.teleportationAttack.get());
        summondSwordEntity.setStartDelay(0);

        summondSwordEntity.attackActionCallbackPoint.register(e -> {
            if (e instanceof LivingEntity living) {
                doTeleport(livingEntity, living);
            }
        });

        ILockTarget lockTarget = livingEntity.getCapability(SbCapabilities.LOCK_TARGET);

        if (lockTarget != null && lockTarget.getTargetEntity() != null) {
            Entity targetEntity = lockTarget.getTargetEntity();
            Vec3 entityPosition = EntityHelper.getEntityPosition(targetEntity);
            summondSwordEntity.setPos(entityPosition);
            summondSwordEntity.onHitEntity(targetEntity, SummondSwordEntity.SummondAttackType.HIT);
        }

        livingEntity.level().addFreshEntity(summondSwordEntity);

    }

    protected static void doTeleport(Entity entityIn, LivingEntity target) {
        if (!(entityIn.level() instanceof ServerLevel)) return;

        if (entityIn instanceof Player player) {
            player.playSound(SoundEvents.ENDERMAN_TELEPORT, 0.75F, 1.25F);

            // Note: The old capability system has been removed in 1.21
            // This part needs to be updated to use the new data component system
            // Commenting out for now as it references non-existent classes
            /*
            player.getMainHandItem().getCapability(ItemSlashBlade.BLADESTATE)
                    .ifPresent(state -> state.updateComboSeq(player, state.getComboRootAir()));
            */

            //Untouchable.setUntouchable(player, 10);
        }

        ServerLevel worldIn = (ServerLevel) entityIn.level();

        Vec3 teleportPos = target.position().add(0, target.getBbHeight() / 2.0, 0).add(entityIn.getLookAngle().scale(-2.0));

        double x = teleportPos.x();
        double y = teleportPos.y();
        double z = teleportPos.z();
        float yaw = entityIn.getYRot();
        float pitch = entityIn.getXRot();

        // In 1.21, the SPlayerPositionLookPacket.Flags is not used the same way
        // We use a simpler approach for teleportation
        BlockPos blockpos = BlockPos.containing(x, y, z);

        if (entityIn instanceof ServerPlayer serverPlayer) {
            ChunkPos chunkpos = new ChunkPos(blockpos);
            worldIn.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, entityIn.getId());
            entityIn.stopRiding();

            if (serverPlayer.isSleeping()) {
                serverPlayer.stopSleepInBed(true, true);
            }

            if (worldIn == entityIn.level()) {
                // For same-dimension teleportation
                serverPlayer.teleportTo(x, y, z);
                serverPlayer.setYRot(yaw);
                serverPlayer.setXRot(pitch);
            } else {
                // For cross-dimension teleportation
                serverPlayer.teleportTo(worldIn, x, y, z, yaw, pitch);
            }

            entityIn.setYHeadRot(yaw);
        } else {
            float f1 = Mth.wrapDegrees(yaw);
            float f = Mth.wrapDegrees(pitch);
            f = Mth.clamp(f, -90.0F, 90.0F);
            if (worldIn == entityIn.level()) {
                entityIn.setPos(x, y, z);
                entityIn.setYRot(f1);
                entityIn.setXRot(f);
                entityIn.setYHeadRot(f1);
            } else {
                entityIn.unRide();
                Entity entity = entityIn;
                entityIn = entityIn.getType().create(worldIn);
                if (entityIn == null) {
                    return;
                }

                // Note: copyDataFromOld method may not exist in 1.21
                // This would need to be handled differently in the new version
                entityIn.setPos(x, y, z);
                entityIn.setYRot(f1);
                entityIn.setXRot(f);
                entityIn.setYHeadRot(f1);
                worldIn.addFreshEntity(entityIn);
            }
        }

        if (!(entityIn instanceof LivingEntity) || !((LivingEntity) entityIn).isFallFlying()) {
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
            entityIn.setOnGround(false);
        }

        if (entityIn instanceof PathfinderMob) {
            ((PathfinderMob) entityIn).getNavigation().stop();
        }


    }

    @SubscribeEvent
    public static void onAttackTest(KeyInputEvent keyInputEvent) {
        if (keyInputEvent.getKeyInput() != KeyInput.LEFT_CLICK && keyInputEvent.getKeyInput() != KeyInput.RIGHT_CLICK) {
            return;
        }

        if (keyInputEvent.getKeyType() != KeyInputEvent.KeyType.DOWN) {
            return;
        }

        LivingEntity livingEntity = keyInputEvent.getLivingEntity();

        if (livingEntity.level().isClientSide()) {
            return;
        }

        ItemStack mainHandItem = livingEntity.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = mainHandItem.get(SbDataComponentTypes.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }

        AttackHelper.doSlash(livingEntity, livingEntity.getRandom().nextInt(360), Vec3.ZERO, 0.5, 1, null);

    }


    @SubscribeEvent
    protected static void onLockOn(KeyUpdateEvent event) {
        LivingEntity livingEntity = event.getLivingEntity();

        if (livingEntity.level().isClientSide()) {
            return;
        }

        ItemStack itemStack = livingEntity.getMainHandItem();

        SlashBladeLogic slashBladeLogic = itemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return;
        }

        ILockTarget lockTarget = livingEntity.getCapability(SbCapabilities.LOCK_TARGET);

        if (lockTarget == null) {
            return;
        }

        if (!event.getDownMap().get(KeyInput.SNEAK)) {
            lockTarget.setTargetEntity(null);
            return;
        }


        Entity targetEntity = lockTarget.getTargetEntity();
        if (targetEntity != null && !targetEntity.isAlive()) {
            targetEntity = null;
        }

        if (targetEntity == null) {
            HitResult selector = TargetSelectorHelper.selector(livingEntity, 64);
            if (selector.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHitResult = ((EntityHitResult) selector);
                targetEntity = entityHitResult.getEntity();
            }
        }

        if (targetEntity == null) {
            targetEntity = EntityHelper.getTargettableEntitiesWithinAABB(livingEntity.level(), livingEntity, new Vec3(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()), 32)
                    .stream().
                    min(Comparator.comparingDouble(e -> e.distanceTo(livingEntity)))
                    .orElse(null);
        }

        lockTarget.setTargetEntity(targetEntity);

        livingEntity.syncData(SbAttachmentTypes.LOCK_TARGET);
    }


}


