package mod.slashblade.reforged.content.event.key;

import mod.slashblade.reforged.content.data.capabilitie.IPlayerInputCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class KeyEvent extends PlayerEvent {

    final IPlayerInputCapability playerInputCapability;

    public KeyEvent(Player player, IPlayerInputCapability playerInputCapability) {
        super(player);
        this.playerInputCapability = playerInputCapability;
    }
}
