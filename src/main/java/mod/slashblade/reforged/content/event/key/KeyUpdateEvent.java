package mod.slashblade.reforged.content.event.key;

import lombok.Getter;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.capabilitie.IInputCapability;
import net.minecraft.world.entity.LivingEntity;

import java.util.EnumMap;

/***
 * @Author: til
 * @Description 每tick触发，双端触发
 * @CreateTime: 2025-09-02 18:16
 */
@Getter
public class KeyUpdateEvent extends KeyEvent {

    final EnumMap<KeyInput, Boolean> downMap;

    public KeyUpdateEvent(LivingEntity livingEntity, IInputCapability playerInputCapability, EnumMap<KeyInput, Boolean> downMap) {
        super(livingEntity, playerInputCapability);
        this.downMap = downMap;
    }
}
