package mod.slashblade.reforged.content.animation;

import com.maydaymemory.mae.basic.ArrayPoseBuilder;
import com.maydaymemory.mae.basic.Keyframe;
import com.maydaymemory.mae.basic.Pose;
import com.maydaymemory.mae.basic.ZYXBoneTransformFactory;
import com.maydaymemory.mae.blend.InterpolatorBlender;
import com.maydaymemory.mae.blend.SimpleInterpolatorBlender;
import com.maydaymemory.mae.control.Tickable;
import com.maydaymemory.mae.control.misc.AnimationVelocityEstimatorNode;
import com.maydaymemory.mae.control.misc.RealtimeVelocityEstimatorNode;
import com.maydaymemory.mae.control.montage.AnimationMontageRunner;
import mod.slashblade.reforged.core.animation.AnimationAsset;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-28 17:29
 * @Description: TODO
 */
public class SlashBladeAnimationContext implements Tickable {

    public static final InterpolatorBlender BLENDER = new SimpleInterpolatorBlender(new ZYXBoneTransformFactory(), ArrayPoseBuilder::new);

    public LivingEntity livingEntity;

    public AnimationMontageRunner<SlashBladeAnimationContext> animationMontageRunner;

    public SlashBladeAnimationContext(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    @Override
    public void tick() {
        if (animationMontageRunner != null) {
            animationMontageRunner.tick();
            Level level = livingEntity.level();
            if (level != null && !level.isClientSide) {
                @SuppressWarnings("unchecked")
                Iterable<Keyframe<ResourceLocation>> sounds = animationMontageRunner.clip(AnimationAsset.SOUND_CHANNEL_NAME);
                if (sounds != null) {
                    for (Keyframe<ResourceLocation> keyframe : sounds) {
                        BlockPos pos = livingEntity.getOnPos();
                        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(keyframe.getValue());
                        level.playSound(null, pos, soundEvent, SoundSource.BLOCKS);
                    }
                }
            }
        }
    }
}
