package mod.slashblade.reforged.core.animation.pojo;

import it.unimi.dsi.fastutil.doubles.Double2ObjectRBTreeMap;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-14 17:31
 * @Description: 音效关键帧
 */
@Getter
public class SoundEffectKeyframes {

    private final Double2ObjectRBTreeMap<ResourceLocation> keyframes;

    public SoundEffectKeyframes(Double2ObjectRBTreeMap<ResourceLocation> keyframes) {
        this.keyframes = keyframes;
    }
}
