package mod.slashblade.reforged.content.entity;

import lombok.Getter;
import mod.slashblade.reforged.content.init.SbAttackTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

/**
 * @Author: til
 * @Description: 闪电 （假的）
 */
public class LightningEntity extends ContinuousDamageEntity {
    @Getter
    protected long boltVertex;

    public LightningEntity(EntityType<?> entityTypeIn, Level worldIn, LivingEntity shooting) {
        super(entityTypeIn, worldIn, shooting);
        setMaxLifeTime(this.getRandom().nextInt(15) + 5);
        boltVertex = this.getRandom().nextLong();
        setParameterRange(3);
        attackTypeModelList = List.of(SbAttackTypes.LIGHTNING_ATTACK_TYPE.get());
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount % 5 == 0) {
            this.boltVertex = this.getRandom().nextLong();
        }

        if (level().isClientSide()) {
            this.level().setThunderLevel(2);
        }

    }

    @Override
    public void setUp() {
        super.setUp();
        if (!isMute()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + this.getRandom().nextFloat() * 0.2F);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 2.0F, 0.5F + this.getRandom().nextFloat() * 0.2F);
        }
    }

}
