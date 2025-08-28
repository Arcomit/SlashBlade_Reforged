package mod.slashblade.reforged.content.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-20 10:57
 * @Description: TODO
 */
@Getter @AllArgsConstructor
public class BasicAction {

    private String animation;

    // 动作的速度
    private float  speed = 1.0f;

    // 取消动作所需的Ticks
    private int    cancelAfterTicks;

    private String timeoutAnimation;

    private int    priority;
}
