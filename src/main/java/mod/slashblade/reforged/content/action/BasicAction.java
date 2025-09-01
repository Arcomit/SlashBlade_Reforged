package mod.slashblade.reforged.content.action;

import com.maydaymemory.mae.basic.Keyframe;
import com.maydaymemory.mae.control.montage.AnimationMontage;
import com.maydaymemory.mae.control.montage.AnimationMontageSection;
import com.maydaymemory.mae.control.montage.AnimationMontageTrack;
import com.maydaymemory.mae.control.montage.AnimationSegment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationInstance;
import mod.slashblade.reforged.core.animation.event.AnimationManager;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 10:57
 * @Description: 合成蒙太奇储存
 */
@Getter
public class BasicAction {

    private AnimationMontage<SlashBladeAnimationInstance> actionMontage = new AnimationMontage<>();

    public static BasicAction.Builder builder() {
        return new BasicAction.Builder();
    }

    public static class Builder {

        // 动作的动画资源名称
        private String animation = "default_idle_universal";
        private float start = 0.0f;
        private float end = 0.2f;

        // 取消动作所需的时间（单位：秒）
        private float  cancelAfterTimeS = 0;

        // 动作播放完还没取消自动播放的下一个动画
        private String nextAnimation = "default_idle_universal";
        private float nextStart = 0.0f;
        private float nextEnd = 0.2f;

        // 动作的速度
        private float  speed = 1.0f;
        // 动作的优先级
        private int    priority = 0;
        private boolean isLoop = false;

        public BasicAction build() {
            BasicAction action = new BasicAction();
            AnimationMontage<SlashBladeAnimationInstance> montage = action.getActionMontage();

            ArrayList<Keyframe<AnimationSegment>> drawSegments = new ArrayList<>();
            drawSegments.add(AnimationManager.constructSegmentKeyframe(animation, 0.0f, start, end));
            drawSegments.add(AnimationManager.constructSegmentKeyframe(nextAnimation, end, nextStart, nextEnd));
            AnimationMontageTrack drawTrack = new AnimationMontageTrack(drawSegments);
            montage.setTracks(List.of(drawTrack));

            Map<String, AnimationMontageSection> drawMontageSections = new HashMap<>();
            drawMontageSections.put("first", new AnimationMontageSection("first", 0.0f, end - start, "next"));
            if (isLoop) {
                drawMontageSections.put("next", new AnimationMontageSection("next", end - start, nextEnd - nextStart, "first"));
            }else {
                drawMontageSections.put("next", new AnimationMontageSection("next", end - start, nextEnd - nextStart, null));
            }
            montage.setSections(drawMontageSections);

            return action;
        }

        public Builder animation(String animationNmae, int start, int end) {
            this.animation = animationNmae;
            this.start = start;
            this.end = end;
            return this;
        }

        public Builder cancelTimeS(float timeS) {
            this.cancelAfterTimeS = timeS;
            return this;
        }

        public Builder nextAnimation(String animationNmae, int start, int end) {
            this.nextAnimation = animationNmae;
            this.nextStart = start;
            this.nextEnd = end;
            return this;
        }

        public Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder isLoop(boolean isLoop) {
            this.isLoop = isLoop;
            return this;
        }
    }
}
