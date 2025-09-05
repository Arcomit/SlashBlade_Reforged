package mod.slashblade.reforged.content.entity;

import mod.slashblade.reforged.content.init.SbAttackTypes;
import mod.slashblade.reforged.utils.helper.MathHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: til
 * @Description: 剑气
 */
public class DriveEntity extends SlashEffectEntity {

    /***
     * 速度
     */
    protected static final EntityDataAccessor<Float> SEEP = SynchedEntityData.defineId(DriveEntity.class, EntityDataSerializers.FLOAT);

    /***
     * 穿透墙体
     */
    protected static final EntityDataAccessor<Boolean> PARAMETER = SynchedEntityData.defineId(DriveEntity.class, EntityDataSerializers.BOOLEAN);

    public DriveEntity(EntityType<?> entityTypeIn, Level worldIn, LivingEntity shooting) {
        super(entityTypeIn, worldIn, shooting);

        setMaxLifeTime(40);

        if (shooting != null) {
            setPos(shooting.getX(), shooting.getY(), shooting.getZ());
            setRot(shooting.getYRot(), shooting.getXRot());
            updateMotion(1);
            lookAt(getDeltaMovement(), true, true);
        }

        setAttackInterval(1);

        attackTypeModelList = List.of(SbAttackTypes.DRIVE_SWORD_ATTACK_TYPE.get());

    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SEEP, 4f);
        builder.define(PARAMETER, false);
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 positionVec = getPos();
        Vec3 deltaMovement = getDeltaMovement();
        Vec3 movedVec = positionVec.add(deltaMovement.x, deltaMovement.y, deltaMovement.z);

        double mx = movedVec.x;
        double my = movedVec.y;
        double mz = movedVec.z;
        setPos(mx, my, mz);

        if (!level().isClientSide() && !isParameter()) {

            BlockHitResult hitResult = level().clip(
                    new ClipContext(
                            positionVec,
                            movedVec,
                            ClipContext.Block.COLLIDER,
                            ClipContext.Fluid.NONE,
                            this
                    )
            );

            if (hitResult.getType() == HitResult.Type.BLOCK && tickCount > 5) {
                this.discard();
            }

        }

    }

    public float getSeep() {
        return this.entityData.get(SEEP);
    }

    public void setSeep(float seep) {
        this.entityData.set(SEEP, seep);
    }

    public boolean isParameter() {
        return this.entityData.get(PARAMETER);
    }

    public void setParameter(boolean parameter) {
        this.entityData.set(PARAMETER, parameter);
    }

}
