package mod.slashblade.reforged.core.animation;

import com.maydaymemory.mae.basic.*;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import mod.slashblade.reforged.core.animation.pojo.*;
import mod.slashblade.reforged.core.obj.ObjGroupIndexProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 15:13
 * @Description: TODO
 */
public class Animation extends BasicAnimation {
    private static final float DEGREE_TO_ANGLE = (float) (Math.PI / 180);
    public static final String SOUND_CHANNEL_NAME = "sound_effects";

    private float specifiedEndTimeS = -1;

    public Animation(String name) {
        super(name, new ZYXBoneTransformFactory(), ArrayPoseBuilder::new);
    }

    public void setSpecifiedEndTimeS(float specifiedEndTimeS) {
        this.specifiedEndTimeS = specifiedEndTimeS;
    }

    public float getSpecifiedEndTimeS() {
        return specifiedEndTimeS;
    }

    public static Animation createAnimation(String name, AnimationPOJO pojo, ObjGroupIndexProvider indexProvider) {
        Animation animation = new Animation(name);
        if (pojo.getBones() != null) {
            for (Map.Entry<String, AnimationBone> entry : pojo.getBones().entrySet()) {
                int boneIndex = indexProvider.getIndex(entry.getKey());
                AnimationBone bone = entry.getValue();
                if (boneIndex >= 0) {
                    // 这里导出成基岩版模型之后位移 x 轴会逆转（yz 平面对称变成左手系等效位移），现在我们逆转回来
                    ArrayInterpolatableChannel<Vector3fc> translationChannel = parseChannel(bone.getPosition(), -1, 1, 1);
                    // 这里导出成基岩版模型之后旋转会 z 轴对称变成左手系等效旋转，现在我们逆转回来
                    ArrayInterpolatableChannel<Rotation> rotationChannel = parseRotationChannel(bone.getRotation(), -1, -1, 1);
                    ArrayInterpolatableChannel<Vector3fc> scaleChannel = parseChannel(bone.getScale(), 1, 1, 1);
                    animation.setTranslationChannel(boneIndex, translationChannel);
                    animation.setRotationChannel(boneIndex, rotationChannel);
                    animation.setScaleChannel(boneIndex, scaleChannel);
                }
            }
        }
        SoundEffectKeyframes soundEffects = pojo.getSoundEffects();
        if (soundEffects != null) {
            ArrayList<Keyframe<ResourceLocation>> keyframes = new ArrayList<>();
            for (Double2ObjectMap.Entry<ResourceLocation> entry : soundEffects.getKeyframes().double2ObjectEntrySet()) {
                keyframes.add(new ResourceLocationKeyframe((float) entry.getDoubleKey(), entry.getValue()));
            }
            animation.setClipChannel(SOUND_CHANNEL_NAME, new ArrayClipChannel<>(keyframes));
        }
        float animationLength = (float) pojo.getAnimationLength();
        animation.setSpecifiedEndTimeS(animationLength);
        return animation;
    }

    public static List<Animation> createAnimation(AnimationFile pojo, ObjGroupIndexProvider indexProvider) {
        List<Animation> animations = new ArrayList<>();
        if (pojo.getAnimations() != null) {
            for (Map.Entry<String, AnimationPOJO> entry : pojo.getAnimations().entrySet()) {
                animations.add(createAnimation(entry.getKey(), entry.getValue(), indexProvider));
            }
        }
        return animations;
    }

    private static ArrayInterpolatableChannel<Rotation> parseRotationChannel(AnimationKeyframes keyframes,
                                                                             float x, float y, float z) {
        if (keyframes == null) {
            return null;
        }
        ArrayList<InterpolatableKeyframe<Rotation>> array = new ArrayList<>();
        keyframes.getKeyframes().forEach((timeS, keyframe) -> {
            array.add(parseRotationKeyframe((float) (double)timeS, keyframe, x, y, z));
        });
        return new ArrayInterpolatableChannel<>(array);
    }

    private static RotationKeyframe parseRotationKeyframe(float timeS, AnimationKeyframes.Keyframe keyframe,
                                                          float x, float y, float z) {
        Interpolator<Vector3fc> interpolator;
        Vector3f pre, post;
        if (keyframe.getData() != null) {
            pre = post = keyframe.getData();
        } else {
            pre = keyframe.getPre() == null ? keyframe.getPost() : keyframe.getPre();
            post = keyframe.getPost() == null ? keyframe.getPre() : keyframe.getPost();
        }
        if (pre == post) {
            pre.mul(x, y, z).mul(DEGREE_TO_ANGLE);
        } else {
            pre.mul(x, y, z).mul(DEGREE_TO_ANGLE);
            post.mul(x, y, z).mul(DEGREE_TO_ANGLE);
        }
        if ("catmullrom".equals(keyframe.getLerpMode())) {
            interpolator = Vector3fCubicSplineInterpolator.INSTANCE;
        } else {
            interpolator = Vector3fLinearInterpolator.INSTANCE;
        }
        return new RotationKeyframe(
                timeS,
                new Rotation(pre),
                new Rotation(post),
                new EulerAnglesRotationInterpolator(interpolator));
    }

    private static ArrayInterpolatableChannel<Vector3fc> parseChannel(AnimationKeyframes keyframes,
                                                                      float x, float y, float z) {
        if (keyframes == null) {
            return null;
        }
        ArrayList<InterpolatableKeyframe<Vector3fc>> array = new ArrayList<>();
        keyframes.getKeyframes().forEach((timeS, keyframe) -> {
            array.add(parseKeyframe((float)(double)timeS, keyframe, x, y, z));
        });
        return new ArrayInterpolatableChannel<>(array);
    }


    private static Vector3fKeyframe parseKeyframe(float timeS, AnimationKeyframes.Keyframe keyframe,
                                                  float x, float y, float z) {
        Interpolator<Vector3fc> interpolator;
        Vector3f pre, post;
        if (keyframe.getData() != null) {
            pre = post = keyframe.getData();
        } else {
            pre = keyframe.getPre() == null ? keyframe.getPost() : keyframe.getPre();
            post = keyframe.getPost() == null ? keyframe.getPre() : keyframe.getPost();
        }
        if (pre == post) {
            pre.mul(x, y, z);
        } else {
            pre.mul(x, y, z);
            post.mul(x, y, z);
        }
        if ("catmullrom".equals(keyframe.getLerpMode())) {
            interpolator = Vector3fCubicSplineInterpolator.INSTANCE;
        } else {
            interpolator = Vector3fLinearInterpolator.INSTANCE;
        }
        return new Vector3fKeyframe(timeS, pre, post, interpolator);
    }
}