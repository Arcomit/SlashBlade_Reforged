package com.test.entity.entity;

import com.test.entity.entity.init.EntityInits;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-09 01:22
 * @Description: TODO
 */
public class TestEntity extends ThrowableProjectile {
    public TestEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public TestEntity(LivingEntity shooter, Level level) {
        super(EntityInits.BLACK_DICK.get(), shooter, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        // 添加命中效果（例如爆炸、粒子效果等）
        if (!this.level().isClientSide) {
            //this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.NONE);
            //this.discard();
        }
    }



    @Override
    public void tick() {
        super.tick();
        // 添加飞行粒子效果
        if (this.level().isClientSide) {
            for (int i = 0; i < 2; i++) {
                level().addParticle(ParticleTypes.SMOKE,
                        this.getX() + (random.nextDouble() - 0.5) * 0.2,
                        this.getY() + 0.1,
                        this.getZ() + (random.nextDouble() - 0.5) * 0.2,
                        0, 0, 0);
            }
        }
    }
}