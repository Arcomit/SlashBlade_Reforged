package mod.slashblade.reforged.content.action;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 10:57
 * @Description: TODO
 */
@Getter
public class BasicAction {

    private ResourceLocation animation;

    public BasicAction(ResourceLocation animation) {
        this.animation = animation;
    }


}
