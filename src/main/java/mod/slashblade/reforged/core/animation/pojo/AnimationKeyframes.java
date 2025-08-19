package mod.slashblade.reforged.core.animation.pojo;

import it.unimi.dsi.fastutil.doubles.Double2ObjectRBTreeMap;
import lombok.Getter;
import org.joml.Vector3f;

import javax.annotation.Nullable;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-14 17:32
 * @Description: TODO
 */
@Getter
public class AnimationKeyframes {
    private final Double2ObjectRBTreeMap<Keyframe> keyframes;

    public AnimationKeyframes(Double2ObjectRBTreeMap<Keyframe> keyframes) {
        this.keyframes = keyframes;
    }

    @Getter
    public static class Keyframe {
        private final Vector3f pre;
        private final Vector3f post;
        private final Vector3f data;
        private final String lerpMode;

        public Keyframe (@Nullable Vector3f pre, @Nullable Vector3f post,
                         @Nullable Vector3f data, @Nullable String lerpMode) {
            this.pre      = pre;
            this.post     = post;
            this.data     = data;
            this.lerpMode = lerpMode;
        }
    }
}
