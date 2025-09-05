package mod.slashblade.reforged.core.animation.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import mod.slashblade.reforged.core.animation.pojo.exclusion.ClientOnly;

import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-14 17:14
 * @Description: 动画数据类
 */
@Getter
public class AnimationPOJO {

    @SerializedName("loop"            )
    private boolean                    loop;

    @SerializedName("animation_length")
    private double                     animationLength;

    @ClientOnly
    @SerializedName("bones"           )
    private Map<String, AnimationBone> bones;

    @SerializedName("sound_effects"   )
    private SoundEffectKeyframes       soundEffects;
}
