package mod.slashblade.reforged.content.data.capabilitie;

import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.network.KeyInputPack;
import mod.slashblade.reforged.content.event.key.KeyInputEvent;
import mod.slashblade.reforged.content.event.key.KeyUpdateEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

import java.util.EnumMap;

public interface IPlayerInputCapability {


    void acceptNewInput( KeyInputPack keyInputPack);

    boolean isDown(KeyInput keyInput);

    /***
     * 长按
     */
    boolean isLongHold(KeyInput keyInput);

}
