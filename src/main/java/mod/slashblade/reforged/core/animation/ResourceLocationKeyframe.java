package mod.slashblade.reforged.core.animation;

import com.maydaymemory.mae.basic.BaseKeyframe;
import net.minecraft.resources.ResourceLocation;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 18:28
 * @Description: TODO
 */
public class ResourceLocationKeyframe extends BaseKeyframe<ResourceLocation> {
    private final ResourceLocation resourceLocation;

    public ResourceLocationKeyframe(float timeS, ResourceLocation resourceLocation) {
        super(timeS);
        this.resourceLocation = resourceLocation;
    }

    @Override
    public ResourceLocation getValue() {
        return resourceLocation;
    }
}
