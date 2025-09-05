package mod.slashblade.reforged.content.event.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mod.slashblade.reforged.content.data.capabilitie.IPlayerInputCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Getter
@AllArgsConstructor
public class KeyEvent extends Event {

    final LivingEntity livingEntity;
    final IPlayerInputCapability playerInputCapability;


}
