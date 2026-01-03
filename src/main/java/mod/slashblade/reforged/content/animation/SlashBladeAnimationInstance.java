package mod.slashblade.reforged.content.animation;

import com.maydaymemory.mae.control.statemachine.AnimationStateMachine;
import mod.slashblade.reforged.content.animation.state.Standing;
import mod.slashblade.reforged.content.init.SbAttachmentTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-27 17:03
 * @Description: 拔刀剑动画实例(与实体绑定，无持久化实现)
 */
public class SlashBladeAnimationInstance {

    public AnimationStateMachine<SlashBladeAnimationContext> stateMachine;

    @OnlyIn(Dist.CLIENT)
    public void renderTick() {
        stateMachine.tick();
    }

    public void tick() {
        stateMachine.tick();
    }

    public static SlashBladeAnimationInstance get(LivingEntity livingEntity){
        SlashBladeAnimationInstance instance = livingEntity.getData(SbAttachmentTypes.ANIMATION_INSTANCE);
        if (instance.stateMachine == null){
            instance.stateMachine = new AnimationStateMachine<>(Standing.INSTANCE, new SlashBladeAnimationContext(livingEntity), new NotCountingPausedNanoTimeSupplier());
        }
        return instance;
    }
}

