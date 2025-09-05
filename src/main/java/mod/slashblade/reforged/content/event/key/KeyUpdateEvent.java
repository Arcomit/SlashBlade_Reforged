package mod.slashblade.reforged.content.event.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.capabilitie.IPlayerInputCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.EnumMap;

@Getter
public class KeyUpdateEvent extends KeyEvent {

    final EnumMap<KeyInput, Boolean> downMap;

    public KeyUpdateEvent(LivingEntity livingEntity, IPlayerInputCapability playerInputCapability, EnumMap<KeyInput, Boolean> downMap) {
        super(livingEntity, playerInputCapability);
        this.downMap = downMap;
    }
}
