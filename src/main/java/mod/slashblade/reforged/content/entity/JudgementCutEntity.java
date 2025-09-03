package mod.slashblade.reforged.content.entity;

import lombok.Getter;
import mod.slashblade.reforged.content.init.SbAttackTypes;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import mod.slashblade.reforged.utils.helper.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: til
 * @Description: 次次次次次元斩
 */
public class JudgementCutEntity extends ContinuousDamageEntity {

    @Getter
    protected int seed;

    public JudgementCutEntity(EntityType<?> entityTypeIn, Level worldIn, LivingEntity shooting) {
        super(entityTypeIn, worldIn, shooting);

        this.setMaxLifeTime(10);
        this.seed = this.getRandom().nextInt(360);

        attackTypeModelList = List.of(SbAttackTypes.JUDGEMENT_CUT_ATTACK_TYPE.get());
        setRepeatedAttack(false);

        setModel(ResourceLocationConstants.DEFAULT_JUDGEMENT_CUT_MODEL);
        setTexture(ResourceLocationConstants.DEFAULT_JUDGEMENT_CUT_TEXTURE);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (level().isClientSide()) {
            ParticleHelper.spawnParticle(level(), ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 16, 0.5, 0.5, 0.5, 0.25f);
        }
    }

}
