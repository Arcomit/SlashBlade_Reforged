package mod.slashblade.reforged.content.event.key;

import lombok.Getter;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.capabilitie.IInputCapability;
import net.minecraft.world.entity.LivingEntity;

/***
 * @author til
 * @Description 按键状态更改时触发，双端触发
 */
@Getter
public class KeyInputEvent extends KeyEvent {

    final KeyInput keyInput;
    final KeyType keyType;

    public KeyInputEvent(LivingEntity livingEntity, IInputCapability playerInputCapability, KeyInput keyInput, KeyType keyType) {
        super(livingEntity, playerInputCapability);
        this.keyInput = keyInput;
        this.keyType = keyType;
    }

    /***
     * 规定一个按键的按键过程
     * 按下大于1秒视为HOLD，取消将额外触发HOLD_UP
     */
    public enum KeyType {
        DOWN,
        HOLD,
        HOLD_UP,
        UP
    }
}
