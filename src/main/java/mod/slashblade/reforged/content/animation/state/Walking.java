package mod.slashblade.reforged.content.animation.state;

import com.maydaymemory.mae.basic.ArrayPoseBuilder;
import com.maydaymemory.mae.basic.DummyPose;
import com.maydaymemory.mae.basic.Pose;
import com.maydaymemory.mae.basic.ZYXBoneTransformFactory;
import com.maydaymemory.mae.control.blend.EasingBlendCurve;
import com.maydaymemory.mae.control.blend.IBlendCurve;
import com.maydaymemory.mae.control.montage.AnimationMontageRunner;
import com.maydaymemory.mae.control.statemachine.IAnimationState;
import com.maydaymemory.mae.control.statemachine.IAnimationTransition;
import com.maydaymemory.mae.control.statemachine.TransferOutStrategy;
import com.maydaymemory.mae.util.Easing;
import mod.slashblade.reforged.content.action.Action;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationContext;
import mod.slashblade.reforged.content.animation.NotCountingPausedNanoTimeSupplier;
import mod.slashblade.reforged.content.init.SbActions;

import java.util.List;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-01 13:42
 * @Description: 行走中的动画状态
 */
public class Walking implements IAnimationState<SlashBladeAnimationContext> {

    public static final Walking INSTANCE = new Walking();

    @Override
    public Iterable<IAnimationTransition<SlashBladeAnimationContext>> transitions() {
        return List.of(
                new Standing  .Transition(),// 可切换到站立状态
                new Sprinting .Transition(),// 可切换到疾跑状态
                new Inspecting.Transition(),// 可切换到视检状态
                new Attacking .Transition() // 可切换到攻击状态
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
        AnimationMontageRunner<SlashBladeAnimationContext> runner = context.animationMontageRunner;
        if (runner == null) {
            return DummyPose.INSTANCE;
        }
        return runner.getPose();
    }

    public static class Transition implements IAnimationTransition<SlashBladeAnimationContext> {

        @Override
        public IAnimationState<SlashBladeAnimationContext> targetState() {
            return Walking.INSTANCE;
        }

        @Override
        public IBlendCurve curve() {
            return new EasingBlendCurve(Easing.LINEAR);
        }

        @Override
        public float duration() {
            return 0.3f;
        }

        @Override
        public TransferOutStrategy transferOutStrategy() {
            return TransferOutStrategy.TO_STATE;
        }

        @Override
        public boolean canTrigger(SlashBladeAnimationContext context) {
            float walkDelta = context.livingEntity.walkDist - context.livingEntity.walkDistO;
            if (walkDelta > 0.05F && !(context.livingEntity.isSprinting())) {
                return true;
            }
            return false;
        }

        @Override
        public void afterTrigger(SlashBladeAnimationContext context) {
            Action action = SbActions.WALKING_ACTION.get();
            context.animationMontageRunner = new AnimationMontageRunner<>(action.getActionMontage(), context, new ZYXBoneTransformFactory(), ArrayPoseBuilder::new, new NotCountingPausedNanoTimeSupplier());
            context.animationMontageRunner.start("action");
        }

        @Override
        public Pose getInterpolatedPose(SlashBladeAnimationContext context, Pose fromPose, Pose toPose, float alpha) {
            return SlashBladeAnimationContext.BLENDER.blend(fromPose, toPose, alpha);
        }
    }
}
