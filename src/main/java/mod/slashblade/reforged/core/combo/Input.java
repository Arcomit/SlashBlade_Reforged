package mod.slashblade.reforged.core.combo;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-20 14:51
 * @Description: TODO
 */
public class Input {
    public enum InputType {
        FORWARD, BACK, LEFT, RIGHT,
        LEFT_CLICK, RIGHT_CLICK,
        SNEAK, JUMP,
        SUMMONING_SUMMOND_SWORD,
        SPECIAL_OPERATION,
    }

    public enum InputState {
        PRESSED, RELEASED,
        HOLDING, HOLD_RELEASED
    }
}
