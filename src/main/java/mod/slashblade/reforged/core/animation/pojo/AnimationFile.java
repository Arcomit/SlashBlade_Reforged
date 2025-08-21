package mod.slashblade.reforged.core.animation.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-14 17:14
 * @Description: 动画文件
 */
@Getter
public class AnimationFile {

    @SerializedName("format_version")
    private String                     format_version;

    @SerializedName("animations"    )
    private Map<String, AnimationPOJO> animations;
}
