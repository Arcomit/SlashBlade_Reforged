package mod.slashblade.reforged.content.animation.state;

import com.maydaymemory.mae.basic.Pose;
import com.maydaymemory.mae.control.blend.EasingBlendCurve;
import com.maydaymemory.mae.control.blend.IBlendCurve;
import com.maydaymemory.mae.control.statemachine.IAnimationState;
import com.maydaymemory.mae.control.statemachine.IAnimationTransition;
import com.maydaymemory.mae.control.statemachine.TransferOutStrategy;
import com.maydaymemory.mae.util.Easing;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationContext;

import java.util.List;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-01 13:41
 * @Description: 攻击的动画状态
 */
public class Attacking implements IAnimationState<SlashBladeAnimationContext> {

    public static final Attacking INSTANCE = new Attacking();

    @Override
    public Iterable<IAnimationTransition<SlashBladeAnimationContext>> transitions() {
        return List.of(
                new Standing  .Transition() // 可切换到站立状态
        );
    }

    @Override
    public void onEnter(SlashBladeAnimationContext context, IAnimationState<SlashBladeAnimationContext> fromState) {

    }

    @Override
    public void onExit(SlashBladeAnimationContext context, IAnimationTransition<SlashBladeAnimationContext> triggeredTransition) {

    }

    @Override
    public void onUpdate(SlashBladeAnimationContext context) {

    }

    @Override
    public Pose evaluatePose(SlashBladeAnimationContext context) {
        return null;
    }

    public static class Transition implements IAnimationTransition<SlashBladeAnimationContext> {

        @Override
        public IAnimationState<SlashBladeAnimationContext> targetState() {
            return Attacking.INSTANCE;
        }

        @Override
        public IBlendCurve curve() {
            return new EasingBlendCurve(Easing.LINEAR);
        }

        @Override
        public float duration() {
            return 0.0f;
        }

        @Override
        public TransferOutStrategy transferOutStrategy() {
            return null;
        }

        @Override
        public boolean canTrigger(SlashBladeAnimationContext context) {
            return false;
        }

        @Override
        public void afterTrigger(SlashBladeAnimationContext context) {

        }

        @Override
        public Pose getInterpolatedPose(SlashBladeAnimationContext context, Pose fromPose, Pose toPose, float alpha) {
            return null;
        }
    }
}
