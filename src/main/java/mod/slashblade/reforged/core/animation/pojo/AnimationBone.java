package mod.slashblade.reforged.core.animation.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-14 17:31
 * @Description: TODO
 */
@Getter
public class AnimationBone {
    @SerializedName("position")
    private AnimationKeyframes position;

    @SerializedName("rotation")
    private AnimationKeyframes rotation;

    @SerializedName("scale"   )
    private AnimationKeyframes scale;
}
