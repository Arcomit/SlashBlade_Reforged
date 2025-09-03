package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Random;
import org.joml.Vector3d;

/**
 * @Author: til
 * @Description: 粒子效果处理类
 */
public class ParticleHelper {
    private static final RandomSource random = RandomSource.create();

    public static void spawnParticle(Level level, ParticleOptions type, double posX, double posY, double posZ, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        for(int i = 0; i < particleCount; i++) {
            double d1 = random.nextGaussian() * xOffset;
            double d3 = random.nextGaussian() * yOffset;
            double d5 = random.nextGaussian() * zOffset;
            double d6 = random.nextGaussian() * speed;
            double d7 = random.nextGaussian() * speed;
            double d8 = random.nextGaussian() * speed;

            try {
                level.addParticle(
                        type,
                        false,
                        posX + d1,
                        posY + d3,
                        posZ + d5,
                        d6,
                        d7,
                        d8
                );
            } catch (Throwable throwable) {
                SlashbladeMod.LOGGER.warn("Could not spawn particle effect {}", type);
                return;
            }
        }


    }

    public static void spawnBlockParticle(Entity user, Vec3 targetPos, Vec3 normal, float fallFactor) {
        Vec3 blockPos = targetPos.add(normal.normalize().scale(0.5F));
        int x = MathHelper.floor(blockPos.x());
        int y = MathHelper.floor(blockPos.y());
        int z = MathHelper.floor(blockPos.z());
        BlockPos pos = new BlockPos(x, y, z);
        BlockState state = user.level().getBlockState(pos);
        float f = (float) MathHelper.ceil(fallFactor);
        if (state.isAir()) {
            return;
        }
        double d0 = Math.min(0.2F + f / 15.0F, (double) 2.5F);
        int i = (int) ((double) 150.0F * d0);
        BlockParticleOption blockParticleOption = new BlockParticleOption(ParticleTypes.BLOCK, state);
        spawnParticle(user.level(), blockParticleOption, targetPos.x, targetPos.y, targetPos.z, i, 0, 0, 0, 0.15F);
    }

}
