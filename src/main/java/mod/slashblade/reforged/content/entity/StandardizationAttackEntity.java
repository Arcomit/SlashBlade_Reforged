package mod.slashblade.reforged.content.entity;

import mod.slashblade.reforged.content.init.SbEntityDataSerializers;
import mod.slashblade.reforged.utils.CallbackPoint;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import mod.slashblade.reforged.utils.helper.MathHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * @Author: til
 * @Description: 所有攻击实体的基类
 */
public abstract class StandardizationAttackEntity extends Entity {


    /**
     * 发射实体的ID
     */
    protected static final EntityDataAccessor<Integer> SHOOTER_ENTITY_ID = SynchedEntityData.defineId(StandardizationAttackEntity.class, EntityDataSerializers.INT);

    /**
     * 模型资源位置
     */
    protected static final EntityDataAccessor<ResourceLocation> MODEL = SynchedEntityData.defineId(StandardizationAttackEntity.class, SbEntityDataSerializers.RESOURCE_LOCATION.get());

    /**
     * 纹理资源位置
     */
    protected static final EntityDataAccessor<ResourceLocation> TEXTURE = SynchedEntityData.defineId(StandardizationAttackEntity.class, SbEntityDataSerializers.RESOURCE_LOCATION.get());

    /**
     * 最大存活时间
     */
    protected static final EntityDataAccessor<Integer> MAX_LIFE_TIEM = SynchedEntityData.defineId(StandardizationAttackEntity.class, EntityDataSerializers.INT);

    /**
     * 颜色
     */
    protected static final EntityDataAccessor<Color> COLOR = SynchedEntityData.defineId(StandardizationAttackEntity.class, SbEntityDataSerializers.COLOR.get());

    /**
     * 伤害值
     */
    protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(StandardizationAttackEntity.class, EntityDataSerializers.FLOAT);

    /**
     * 旋转角度
     */
    protected static final EntityDataAccessor<Float> ROLL = SynchedEntityData.defineId(StandardizationAttackEntity.class, EntityDataSerializers.FLOAT);

    /**
     * 大小
     */
    protected static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(StandardizationAttackEntity.class, EntityDataSerializers.FLOAT);

    /**
     * 是否静音
     */
    protected static final EntityDataAccessor<Boolean> MUTE = SynchedEntityData.defineId(StandardizationAttackEntity.class, EntityDataSerializers.BOOLEAN);

    boolean completeSetup = false;

    public final CallbackPoint<ISetup> setupCallbackPoint = new CallbackPoint<>();
    public final CallbackPoint<IEnd> endCallbackPoint = new CallbackPoint<>();

    public StandardizationAttackEntity(EntityType<?> entityTypeIn, Level worldIn, LivingEntity shooting) {
        super(entityTypeIn, worldIn);
        if (!worldIn.isClientSide && shooting == null) {
            //必须有shooting
            this.discard();
        }
        setShooter(shooting);
        setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        builder.define(SHOOTER_ENTITY_ID, -1);
        builder.define(MODEL, ResourceLocationConstants.DEFAULT);
        builder.define(TEXTURE, ResourceLocationConstants.DEFAULT);
        builder.define(MAX_LIFE_TIEM, 100);
        builder.define(COLOR, new Color(0x3333FF));
        builder.define(DAMAGE, 1f);
        builder.define(ROLL, 0f);
        builder.define(SIZE, 1f);
        builder.define(MUTE, false);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    public void tick() {
        super.tick();

        if (!completeSetup) {
            completeSetup = true;
            setUp();
        }

        if (level().isClientSide()) {
            return;
        }

        if (getMaxLifeTime() < tickCount) {
            discard();
        }

    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        endCallbackPoint.call(IEnd::end);
    }

    public void setUp() {
        setupCallbackPoint.call(ISetup::setup);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = 128.0D * getViewScale();
        return distance < d0 * d0;
    }

    @Nullable
    protected LivingEntity shooter;

    @Nullable
    public LivingEntity getShooter() {
        int id = entityData.get(SHOOTER_ENTITY_ID);

        if (shooter != null && shooter.getId() != id) {
            shooter = null;
        }

        if (shooter == null) {
            if (id > 0) {
                Entity entity = level().getEntity(id);
                if (entity instanceof LivingEntity) {
                    shooter = (LivingEntity) entity;
                }
            }
        }

        return shooter;
    }

    public void setShooter(@Nullable LivingEntity shooter) {
        entityData.set(
                SHOOTER_ENTITY_ID,
                shooter != null
                        ? shooter.getId()
                        : -1
        );
        this.shooter = shooter;
    }

    public ResourceLocation getModel() {
        return entityData.get(MODEL);
    }

    public void setModel(ResourceLocation model) {
        entityData.set(MODEL, model);
    }

    public ResourceLocation getTexture() {
        return entityData.get(TEXTURE);
    }

    public void setTexture(ResourceLocation texture) {
        entityData.set(TEXTURE, texture);
    }

    public int getMaxLifeTime() {
        return entityData.get(MAX_LIFE_TIEM);
    }

    public void setMaxLifeTime(int maxLife) {
        entityData.set(MAX_LIFE_TIEM, maxLife);
    }

    public Color getColor() {
        return entityData.get(COLOR);
    }

    public void setColor(Color color) {
        entityData.set(COLOR, color);
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        entityData.set(DAMAGE, damage);
    }

    public float getRoll() {
        return entityData.get(ROLL);
    }

    public void setRoll(float roll) {
        entityData.set(ROLL, roll);
    }

    public float getSize() {
        return entityData.get(SIZE);
    }

    public void setSize(float size) {
        entityData.set(SIZE, size);
    }

    public boolean isMute() {
        return entityData.get(MUTE);
    }

    public void setMute(boolean mute) {
        entityData.set(MUTE, mute);
    }

    public void setRot(float yRot, float xRot, boolean prevSynchronous) {
        setYRot(yRot % 360.0F);
        setXRot(xRot % 360.0F);

        if (prevSynchronous) {
            xRotO = xRot;
            yRotO = yRot;
        }
    }

    public Vec3 getPos() {
        return new Vec3(getX(), getY(), getZ());
    }

    public void lookAt(Vec3 target, boolean isDistance) {
        lookAt(target, isDistance, true);
    }

    public void lookAt(Vec3 target, boolean isDistance, boolean prevSynchronous) {
        Vec3 distance = isDistance
                ? target
                : target.subtract(getPos());

        distance = distance.normalize();
        double d0 = distance.x;
        double d1 = distance.y;
        double d2 = distance.z;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);

        //attackPitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(d1, d3) * (double) (180F / (float) Math.PI))));
        //attackYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F);

        //rotationPitch = MathHelper.wrapDegrees((float) ((MathHelper.atan2(d1, d3)) * (double) (180F / (float) Math.PI)));
        //rotationYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(d0, d2) * (double) (180F / (float) Math.PI)));


        float rotationPitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(d1, d3) * (double) (180F / (float) Math.PI))));
        float rotationYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F);

        setRot(rotationYaw, rotationPitch, prevSynchronous);
    }

    public void updateMotion(float seep) {
        float fYawDtoR = (getYRot() / 180F) * (float) Math.PI;
        float fPitDtoR = (getXRot() / 180F) * (float) Math.PI;
        float motionX = -MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * seep;
        float motionY = -MathHelper.sin(fPitDtoR) * seep;
        float motionZ = MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * seep;
        setDeltaMovement(motionX, motionY, motionZ);
    }


    public interface ISetup {
        void setup();
    }

    public interface IEnd {
        void end();
    }

}
