package mod.slashblade.reforged.content.entity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import mod.slashblade.reforged.content.register.AttackType;
import mod.slashblade.reforged.utils.CallbackPoint;
import mod.slashblade.reforged.utils.helper.AttackHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ContinuousDamageEntity extends StandardizationAttackEntity {

    /***
     * 攻击间隔
     */
    protected static final EntityDataAccessor<Integer> ATTACK_INTERVAL = SynchedEntityData.defineId(ContinuousDamageEntity.class, EntityDataSerializers.INT);

    /***
     * 是否重复攻击
     */
    protected static final EntityDataAccessor<Boolean> REPEATED_ATTACK = SynchedEntityData.defineId(ContinuousDamageEntity.class, EntityDataSerializers.BOOLEAN);

    /***
     * 参数单位范围，索敌范围为 SIZE * PARAMETER_RANGE
     */
    protected static final EntityDataAccessor<Float> PARAMETER_RANGE = SynchedEntityData.defineId(ContinuousDamageEntity.class, EntityDataSerializers.FLOAT);

    /***
     * 所有以攻击的实体id
     */
    @Nullable
    protected Set<Entity> alreadyHits;

    public final CallbackPoint<IAttackAction> attackActionCallbackPoint = new CallbackPoint<>();

    public ContinuousDamageEntity(EntityType<?> entityTypeIn, Level worldIn, LivingEntity shooting) {
        super(entityTypeIn, worldIn, shooting);
        this.setNoGravity(true);
    }


    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_INTERVAL, 2);
        builder.define(REPEATED_ATTACK, false);
        builder.define(PARAMETER_RANGE, 1.0f);
    }


    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            if (tickCount % getAttackInterval() == 0) {
                onAttackTime();
            }
        }
    }

    public void onAttackTime() {

        LivingEntity shooter = getShooter();
        if (shooter == null) {
            return;
        }

        if (alreadyHits == null) {
            alreadyHits = new HashSet<>(List.of(this, shooter));
        }

        AttackHelper.areaAttack(
                        shooter,
                        getPos(),
                        e -> {},
                        getSize() * getParameterRange(),
                        getDamage(),
                        true,
                        alreadyHits,
                        getAttackTypes()
                )
                .stream()
                .peek(
                        e -> {
                            if (!isRepeatedAttack()) {
                                alreadyHits.add(e);
                            }
                        }
                )
                .forEach(e -> attackActionCallbackPoint.call(r -> r.attack(e)));

    }

    public int getAttackInterval() {
        return this.entityData.get(ATTACK_INTERVAL);
    }

    public void setAttackInterval(int interval) {
        this.entityData.set(ATTACK_INTERVAL, interval);
    }

    public boolean isRepeatedAttack() {
        return this.entityData.get(REPEATED_ATTACK);
    }

    public void setRepeatedAttack(boolean repeatedAttack) {
        this.entityData.set(REPEATED_ATTACK, repeatedAttack);
    }

    public float getParameterRange() {
        return this.entityData.get(PARAMETER_RANGE);
    }

    public void setParameterRange(float parameterRange) {
        this.entityData.set(PARAMETER_RANGE, parameterRange);
    }

    public abstract List<AttackType> getAttackTypes();

    /***
     * 攻击到实体时
     */
    public interface IAttackAction {
        void attack(Entity hitEntity);
    }
}
