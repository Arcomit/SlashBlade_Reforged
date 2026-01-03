package mod.slashblade.reforged.content.animation.state;

import com.maydaymemory.mae.basic.*;
import com.maydaymemory.mae.control.blend.EasingBlendCurve;
import com.maydaymemory.mae.control.blend.IBlendCurve;
import com.maydaymemory.mae.control.montage.*;
import com.maydaymemory.mae.control.statemachine.IAnimationState;
import com.maydaymemory.mae.control.statemachine.IAnimationTransition;
import com.maydaymemory.mae.control.statemachine.TransferOutStrategy;
import com.maydaymemory.mae.util.Easing;
import mod.slashblade.reforged.content.action.Action;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationContext;
import mod.slashblade.reforged.content.animation.NotCountingPausedNanoTimeSupplier;
import mod.slashblade.reforged.content.init.SbActions;
import mod.slashblade.reforged.core.animation.event.AnimationManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-01 13:41
 * @Description: 站立不动的动画状态
 */
public class Standing implements IAnimationState<SlashBladeAnimationContext> {

    public static final Standing INSTANCE = new Standing();

    @Override
    public Iterable<IAnimationTransition<SlashBladeAnimationContext>> transitions() {
        return List.of(
                new Walking   .Transition(),// 可切换到走路状态
                new Sprinting .Transition(),// 可切换到疾跑状态
                new Inspecting.Transition(),// 可切换到视检状态
                new Attacking .Transition() // 可切换到攻击状态
        );
    }

    @Override
    public void onEnter(SlashBladeAnimationContext context, IAnimationState<SlashBladeAnimationContext> fromState) {
        //context.animationMontageRunner.setSpeed(0.05F);
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
            return Standing.INSTANCE;
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
            if (walkDelta <= 0.05F) {
                return true;
            }
            return false;
        }

        @Override
        public void afterTrigger(SlashBladeAnimationContext context) {
            // Action action = SbActions.IDLE_ACTION.get();
//            Action action = SbActions.TEST.get();
//            context.animationMontageRunner = new AnimationMontageRunner<>(action.getActionMontage(), context, new ZYXBoneTransformFactory(), ArrayPoseBuilder::new, new NotCountingPausedNanoTimeSupplier());
//            context.animationMontageRunner.start("action");


            AnimationMontage<SlashBladeAnimationContext> drawMontage = new AnimationMontage<>();

            // 将两段动画合并成一个片段
            ArrayList<Keyframe<AnimationSegment>> drawSegments = new ArrayList<>();
//            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A1_FristPerson", 0.0f, 0.0f, 0.33f));
//            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A2_FristPerson", 0.33f, 0.0f, 0.33f));
//            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A3_FristPerson", 0.66f, 0.0f, 0.50f));
//            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A4_FristPerson", 1.16f, 0.0f, 3.58f));
//            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A1_FristPerson", 0.0f, 0.0f, 0.33f));
//            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A2_FristPerson", 0.33f, 0.0f, 0.33f));
//            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_C_FristPerson", 0.66f, 0.0f, 2.92f));
            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A1_FristPerson", 0.0f, 0.0f, 0.33f));
            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A2_FristPerson", 0.33f, 0.0f, 0.33f));
            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A3_FristPerson", 0.66f, 0.0f, 0.50f));
            drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A4_EX_FristPerson", 1.16f, 0.0f, 3.56f));
            //drawSegments.add(AnimationManager.constructSegmentKeyframe("Combo_A5_FristPerson", 2.16f, 0.0f, 5.38f));



            AnimationMontageTrack drawTrack = new AnimationMontageTrack(drawSegments);
            drawMontage.setTracks(List.of(drawTrack));

            Map<String, AnimationMontageSection> drawMontageSections = new HashMap<>();
            //drawMontageSections.put("draw", new AnimationMontageSection("draw", 0.0f, 1.16f + 3.58f, "draw"));
            drawMontageSections.put("draw", new AnimationMontageSection("draw", 0.0f, 1.16f + 3.56f, "draw"));
            drawMontage.setSections(drawMontageSections);

            context.animationMontageRunner = new AnimationMontageRunner<>(drawMontage, context, new ZYXBoneTransformFactory(), ArrayPoseBuilder::new, new NotCountingPausedNanoTimeSupplier());
            context.animationMontageRunner.start("draw");
        }

        @Override
        public Pose getInterpolatedPose(SlashBladeAnimationContext context, Pose fromPose, Pose toPose, float alpha) {
            return SlashBladeAnimationContext.BLENDER.blend(fromPose, toPose, alpha);
        }
    }
}
