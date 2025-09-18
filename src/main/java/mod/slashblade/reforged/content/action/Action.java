package mod.slashblade.reforged.content.action;

import com.maydaymemory.mae.basic.ArrayClipChannel;
import com.maydaymemory.mae.basic.Keyframe;
import com.maydaymemory.mae.control.montage.*;
import lombok.Getter;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationContext;
import mod.slashblade.reforged.core.animation.event.AnimationManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 10:57
 * @Description: 合成蒙太奇储存
 */
public class Action {

    private AnimationMontage<SlashBladeAnimationContext> actionMontage;

    //第一人称蒙太奇
    private AnimationMontage<SlashBladeAnimationContext> firstPersonMontage;

    private final ArrayList<Keyframe<IAnimationNotify<SlashBladeAnimationContext>>> actionNotifies = new ArrayList<>();

    // 动作的动画资源名称
    private String animation = "Default_Idle_Universal";
    private float start = 0.0f;
    private float end = 0.25f;

    // 第一人称动画
    private String fristPersonAnimation = null;
    private float fristPersonStart = 0.0f;
    private float fristPersonEnd = 0.0f;

    // 取消动作所需的时间（单位：秒）
    private float  cancelAfterTimeS = 0;

    // 动作的速度
    private float  speed = 1.0f;
    // 动作的优先级（优先级大的动作可以无视时间取消低的动作）
    private int    priority = 0;
    private boolean isLoop = false;

    private boolean isInit = false;

    public Action animation(String animationName, float start, float end) {
        this.animation = animationName;
        this.start = start;
        this.end = end;
        return this;
    }

    public Action fristPersonAnimation(String animationName, float start, float end) {
        this.fristPersonAnimation = animationName;
        this.fristPersonStart = start;
        this.fristPersonEnd = end;
        return this;
    }

    public Action cancelTimeS(float timeS) {
        this.cancelAfterTimeS = timeS;
        return this;
    }

    public Action speed(float speed) {
        this.speed = speed;
        return this;
    }

    public Action priority(int priority) {
        this.priority = priority;
        return this;
    }

    public Action isLoop(boolean isLoop) {
        this.isLoop = isLoop;
        return this;
    }

    public Action addNotify(float timeS,Consumer<SlashBladeAnimationContext> notify) {
        this.actionNotifies.add(new AnimationNotifyKeyframe<>(timeS, notify::accept));
        return this;
    }

    public AnimationMontage<SlashBladeAnimationContext> getActionMontage() {
        init();
        return actionMontage;
    }

    public AnimationMontage<SlashBladeAnimationContext> getFirstPersonMontage() {
        init();
        return firstPersonMontage;
    }

    // 初始化动作
    public void init() {
        if (isInit) return;

        actionMontage = new AnimationMontage<SlashBladeAnimationContext>();
        firstPersonMontage = new AnimationMontage<SlashBladeAnimationContext>();

        ArrayList<Keyframe<AnimationSegment>> commonSegments = new ArrayList<>();
        commonSegments.add(AnimationManager.constructSegmentKeyframe(animation, 0.0f, start, end));
        AnimationMontageTrack drawTrack = new AnimationMontageTrack(commonSegments);

        Map<String, AnimationMontageSection> commonMontageSections = new HashMap<>();
        if (isLoop) {
            commonMontageSections.put("action", new AnimationMontageSection("action", 0.0f, end - start, "action"));
        }else {
            commonMontageSections.put("action", new AnimationMontageSection("action", 0.0f, end - start, null));
        }
        actionMontage.setTracks(List.of(drawTrack));
        actionMontage.setSections(commonMontageSections);

        // 没有设置第一人称动画时，则与第三人称共用动画
        if (fristPersonAnimation != null) {
            ArrayList<Keyframe<AnimationSegment>> firstPersonSegments = new ArrayList<>();
            firstPersonSegments.add(AnimationManager.constructSegmentKeyframe(fristPersonAnimation, 0.0f, fristPersonStart, fristPersonEnd));
            AnimationMontageTrack firstPersonTrack = new AnimationMontageTrack(firstPersonSegments);

            Map<String, AnimationMontageSection> firstPersonSections = new HashMap<>();
            if (isLoop) {
                firstPersonSections.put("action", new AnimationMontageSection("action", 0.0f, fristPersonEnd - fristPersonStart, "action"));
            }else {
                firstPersonSections.put("action", new AnimationMontageSection("action", 0.0f, fristPersonEnd - fristPersonStart, null));
            }

            firstPersonMontage.setTracks(List.of(firstPersonTrack));
            firstPersonMontage.setSections(firstPersonSections);
        }else {
            firstPersonMontage.setTracks(List.of(drawTrack));
            firstPersonMontage.setSections(commonMontageSections);
        }

        actionMontage.setNotifyChannels(List.of(new ArrayClipChannel<>(actionNotifies)));

        isInit = true;
    }

    public void resetInit() {
       isInit = false;
    }


}
