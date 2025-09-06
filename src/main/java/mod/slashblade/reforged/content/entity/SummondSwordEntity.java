package mod.slashblade.reforged.content.entity;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import mod.slashblade.reforged.content.init.SbAttackTypes;
import mod.slashblade.reforged.content.init.SbEntityDataSerializers;
import mod.slashblade.reforged.utils.CallbackPoint;
import mod.slashblade.reforged.utils.helper.AttackHelper;
import mod.slashblade.reforged.utils.helper.EntityPredicateHelper;
import mod.slashblade.reforged.utils.helper.MathHelper;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: til
 * @Description: 幻影剑
 */
public class SummondSwordEntity extends StandardizationAttackEntity {

    /**
     * 命中实体的ID
     */
    protected static final EntityDataAccessor<Integer> HIT_ENTITY_ID = SynchedEntityData.defineId(SummondSwordEntity.class, EntityDataSerializers.INT);

    /**
     * 最大穿透数
     */
    protected static final EntityDataAccessor<Integer> MAX_PIERCE = SynchedEntityData.defineId(SummondSwordEntity.class, EntityDataSerializers.INT);

    /**
     * 破碎延迟
     */
    protected static final EntityDataAccessor<Integer> BREAK_DELAY = SynchedEntityData.defineId(SummondSwordEntity.class, EntityDataSerializers.INT);

    /**
     * 开始延迟时间
     */
    protected static final EntityDataAccessor<Integer> START_DELAY = SynchedEntityData.defineId(SummondSwordEntity.class, EntityDataSerializers.INT);

    /**
     * 渗透值
     */
    protected static final EntityDataAccessor<Float> SEEP = SynchedEntityData.defineId(SummondSwordEntity.class, EntityDataSerializers.FLOAT);

    /**
     * 是否忽略方块碰撞
     */
    protected static final EntityDataAccessor<Boolean> IGNORING_BLOCK = SynchedEntityData.defineId(SummondSwordEntity.class, EntityDataSerializers.BOOLEAN);

    /**
     * 当前的行动类型
     */
    protected static final EntityDataAccessor<ActionType> ACTION_TYPE = SynchedEntityData.defineId(SummondSwordEntity.class, SbEntityDataSerializers.SUMMOND_SWORD_ENTITY_ACTION_TYPE.get());

    /***
     * 表示已经攻击的目标数量
     */
    @javax.annotation.Nullable
    protected IntOpenHashSet pierce;

    @OnlyIn(Dist.CLIENT)
    protected boolean recordAttackPos;

    @OnlyIn(Dist.CLIENT)
    protected double hitX;
    @OnlyIn(Dist.CLIENT)
    protected double hitY;
    @OnlyIn(Dist.CLIENT)
    protected double hitZ;
    @OnlyIn(Dist.CLIENT)
    protected float hitYaw;
    @OnlyIn(Dist.CLIENT)
    protected float hitPitch;

    protected BlockState inBlockState;

    public final CallbackPoint<IAttackAction> attackActionCallbackPoint = new CallbackPoint<>();
    public final CallbackPoint<IAttackEndAction> attackEndActionCallbackPoint = new CallbackPoint<>();
    public final CallbackPoint<IAttackBlock> attackBlockCallbackPoint = new CallbackPoint<>();

    public SummondSwordEntity(EntityType<? extends SummondSwordEntity> entityTypeIn, Level worldIn, LivingEntity shooting) {
        super(entityTypeIn, worldIn, shooting);
        setModel(ResourceLocationConstants.DEFAULT_SUMMOND_MODEL);
        setTexture(ResourceLocationConstants.DEFAULT_SUMMOND_TEXTURE);

        //设定初始角度等信息
        if (shooting != null) {
            setShooter(shooting);
            float dist = 2.0f;
            double ran = (getRandom().nextFloat() - 0.5) * 2.0;
            double yaw = Math.toRadians(-shooting.getYRot() + 90);
            double x = ran * Math.sin(yaw);
            double y = 1.0 - Math.abs(ran);
            double z = ran * Math.cos(yaw);
            x *= dist;
            y *= dist;
            z *= dist;
            setPos(shooting.getX() + x, shooting.getY() + y, shooting.getZ() + z);
            setRot(shooting.getYRot(), shooting.getXRot());
            updateMotion(1);
            lookAt(getDeltaMovement(), true, true);
        }


    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HIT_ENTITY_ID, -1);
        builder.define(MAX_PIERCE, 1);
        builder.define(BREAK_DELAY, 20);
        builder.define(START_DELAY, 0);
        builder.define(SEEP, 3.25f);
        builder.define(IGNORING_BLOCK, false);
        builder.define(ACTION_TYPE, ActionType.PREPARE);
    }

    protected SoundEvent hitEntitySound = SoundEvents.TRIDENT_HIT;
    protected SoundEvent hitGroundSound = SoundEvents.TRIDENT_HIT_GROUND;
    protected SoundEvent breakSound = SoundEvents.GLASS_BREAK;

    @Override
    public void tick() {
        super.tick();

        setOldPosAndRot();


        switch (getActionType()) {
            case PREPARE -> {
                if (tickCount > getStartDelay()) {
                    setActionType(ActionType.FLYING);
                }
            }
            case FLYING -> {

                //process inAir
                Vec3 positionVec = getPos();
                Vec3 deltaMovement = getDeltaMovement();
                Vec3 movedVec = positionVec.add(deltaMovement.x, deltaMovement.y, deltaMovement.z);

                double mx = movedVec.x;
                double my = movedVec.y;
                double mz = movedVec.z;
                setPos(mx, my, mz);

                if (!isIgnoringBlock()) {
                    BlockHitResult hitResult = level().clip(
                            new ClipContext(
                                    positionVec,
                                    movedVec,
                                    ClipContext.Block.COLLIDER,
                                    ClipContext.Fluid.NONE,
                                    this
                            )
                    );

                    if (hitResult.getType() == HitResult.Type.BLOCK) {
                        onHitBlock(hitResult);
                        return;
                    }

                }


                EntityHitResult entityHitResult = getRayTrace(positionVec, movedVec);

                if (entityHitResult != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
                    Entity target = entityHitResult.getEntity();
                    onHitEntity(target, SummondAttackType.HIT);
                    return;
                }


                if (isInWater()) {
                    for(int j = 0; j < 4; ++j) {
                        level().addParticle(ParticleTypes.BUBBLE, getX() - mx * 0.25D, getY() - my * 0.25D, getZ() - mz * 0.25D, mx, my, mz);
                    }
                }

            }
            case HIT_ENTITY -> {

                Entity hits = getHitEntity();

                if (hits == null || !hits.isAlive()) {
                    discard();
                    return;
                }

                if (!recordAttackPos) {
                    recordAttackPos = true;
                    hitYaw = getYRot() - hits.getYRot();
                    hitPitch = getXRot() - hits.getXRot();
                    hitX = getX() - hits.getX();
                    hitY = getY() - hits.getY();
                    hitZ = getZ() - hits.getZ();
                }


                double posX = hits.getX() + (hitX * Math.cos(Math.toRadians(hits.getYRot())) - hitZ * Math.sin(Math.toRadians(hits.getYRot())));
                double posY = hits.getY() + hitY;
                double posZ = hits.getZ() + (hitX * Math.sin(Math.toRadians(hits.getYRot())) + hitZ * Math.cos(Math.toRadians(hits.getYRot())));

                setPos(posX, posY, posZ);
                setRot(hits.getYRot() + hitYaw, hits.getXRot() + hitPitch, false);

            }
            case HIT_GROUND -> {

                // 提升性能不做处理

                /*if (level().isClientSide()) {
                    return;
                }

                BlockPos blockpos = new BlockPos((int) getX(), (int) getY(), (int) getZ());
                BlockState blockstate = level().getBlockState(blockpos);

                if (inBlockState != blockstate && level().noCollision(getBoundingBox().inflate(0.06D))) {
                    discard();
                }*/

            }
        }
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        inBlockState = level().getBlockState(blockHitResult.getBlockPos());
        setActionType(ActionType.HIT_GROUND);
        lerpMotion(0, 0, 0);
        Vec3 vec3d = blockHitResult.getLocation().subtract(getPos());
        Vec3 vec3d1 = getPos().subtract(vec3d.normalize().scale(0.05F));
        setPos(vec3d1.x, vec3d1.y, vec3d1.z);
        if (!isMute()) {
            playSound(hitGroundSound, 1.0F, 2.2F / (getRandom().nextFloat() * 0.2F + 0.9F));
        }
        setMaxLifeTime(tickCount + getBreakDelay());
        setMaxPierce(0);
        attackBlockCallbackPoint.call(a -> a.attackBlock(inBlockState, blockHitResult.getBlockPos()));
    }

    protected void onHitEntity(Entity targetEntity, SummondAttackType summondAttackType) {
        if (pierce != null && pierce.contains(targetEntity.getId())) {
            return;
        }
        if (!level().isClientSide()) {
            doAttackEntity(targetEntity, summondAttackType);
        }
        if (!level().isClientSide() && summondAttackType != SummondAttackType.BROKEN) {
            if (pierce == null || getMaxPierce() == pierce.size()) {
                setHitEntity(targetEntity);
                setActionType(ActionType.HIT_ENTITY);
                setMaxLifeTime(tickCount + getBreakDelay());
            }
        }
        if (!isMute()) {
            playSound(hitEntitySound, 1.0F, 1.2F / (getRandom().nextFloat() * 0.2F + 0.9F));
        }

    }

    public void doAttackEntity(Entity target, SummondAttackType summondAttackType) {
        AttackHelper.doAttack(getShooter(), target, getDamage(), true, List.of(SbAttackTypes.SUMMOND_SWORD_ATTACK_TYPE.get()));
        target.setDeltaMovement(0, 0.1, 0);
        switch (summondAttackType) {
            case HIT -> attackActionCallbackPoint.call(a -> a.attack(target));
            case BROKEN -> attackEndActionCallbackPoint.call(a -> a.attack(target));
        }

        if (target instanceof LivingEntity targetLivingEntity) {
            targetLivingEntity.hurtTime = 0;

            // TODO StunManager.setStun(targetLivingEntity);

            // TODO EnchantmentHelper.applyThornEnchantments(targetLivingEntity, getShooter());
            // TODO EnchantmentHelper.applyArthropodEnchantments(getShooter(), targetLivingEntity);

            //arrowHit(targetLivingEntity);


            //getShooter().playSound(getHitEntityPlayerSound(), 0.18F, 0.45F);

            if (pierce != null && pierce.size() < getMaxPierce()) {
                pierce.add(target.getId());
            }
        }
    }

    @Nullable
    protected EntityHitResult getRayTrace(Vec3 startVec, Vec3 endVec) {
        return ProjectileUtil.getEntityHitResult(
                level(),
                this,
                startVec,
                endVec,
                getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D),
                entity -> EntityPredicateHelper.canTarget(getShooter(), entity)
        );
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);

        if (!isMute()) {
            playSound(breakSound, 1.0F, 1.2F / (getRandom().nextFloat() * 0.2F + 0.9F));
        }

        if (!level().isClientSide()) {
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CRIT, getX(), getY(), getZ(), 16, 0.5, 0.5, 0.5, 0.25f);
            }
        }

        if (getHitEntity() != null) {
            onHitEntity(getHitEntity(), SummondAttackType.BROKEN);
        }
    }

    @Nullable
    protected Entity hitEntity;

    @Nullable
    public Entity getHitEntity() {
        int id = entityData.get(HIT_ENTITY_ID);

        if (hitEntity != null && hitEntity.getId() != id) {
            hitEntity = null;
        }

        if (hitEntity == null) {
            if (id > 0) {
                Entity entity = level().getEntity(id);
                if (entity != null) {
                    hitEntity = entity;
                }
            }
        }

        return hitEntity;
    }

    public void setHitEntity(@Nullable Entity hitEntity) {
        entityData.set(
                HIT_ENTITY_ID,
                hitEntity != null
                        ? hitEntity.getId()
                        : -1
        );
        this.hitEntity = hitEntity;
    }

    public int getMaxPierce() {
        return entityData.get(MAX_PIERCE);
    }

    public void setMaxPierce(int maxPierce) {
        entityData.set(MAX_PIERCE, maxPierce);
        if (maxPierce > 0 && pierce == null) {
            pierce = new IntOpenHashSet(maxPierce);
        }
    }

    public int getBreakDelay() {
        return entityData.get(BREAK_DELAY);
    }

    public void setBreakDelay(int breakDelay) {
        entityData.set(BREAK_DELAY, breakDelay);
    }

    public int getStartDelay() {
        return entityData.get(START_DELAY);
    }

    public void setStartDelay(int startDelay) {
        entityData.set(START_DELAY, startDelay);
    }

    public float getSeep() {
        return entityData.get(SEEP);
    }

    public void setSeep(float seep) {
        entityData.set(SEEP, seep);
        if (!level().isClientSide) {
            updateMotion(seep);
        }
    }

    public boolean isIgnoringBlock() {
        return entityData.get(IGNORING_BLOCK);
    }

    public void setIgnoringBlock(boolean ignoringBlock) {
        entityData.set(IGNORING_BLOCK, ignoringBlock);
    }

    public ActionType getActionType() {
        return entityData.get(ACTION_TYPE);
    }

    public void setActionType(ActionType actionType) {
        entityData.set(ACTION_TYPE, actionType);
    }

    @Override
    public void lookAt(Vec3 target, boolean isDistance, boolean prevSynchronous) {
        super.lookAt(target, isDistance, prevSynchronous);
        updateMotion(getSeep());
    }

    /**
     * 召唤剑实体的动作类型枚举
     */
    public enum ActionType {
        /**
         * 准备状态
         */
        PREPARE,

        /**
         * 飞行状态
         */
        FLYING,

        /**
         * 命中实体状态
         */
        HIT_ENTITY,

        /**
         * 命中地面状态
         */
        HIT_GROUND,
    }

    public enum SummondAttackType {
        HIT,
        BROKEN
    }

    /***
     * 攻击到实体时
     */
    public interface IAttackAction {
        void attack(Entity hitEntity);
    }

    /***
     * 破碎时再次造成伤害时
     */
    public interface IAttackEndAction {
        void attack(Entity hitEntity);
    }

    /***
     * 击中地面
     */
    public interface IAttackBlock {
        void attackBlock(BlockState blockState, BlockPos blockPos);

    }
}
