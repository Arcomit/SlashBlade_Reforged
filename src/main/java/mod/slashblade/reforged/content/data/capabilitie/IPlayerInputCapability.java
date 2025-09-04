package mod.slashblade.reforged.content.data.capabilitie;

import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.network.KeyInputPack;
import mod.slashblade.reforged.content.event.key.KeyInputEvent;
import mod.slashblade.reforged.content.event.key.KeyUpdateEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

import java.util.EnumMap;

public interface IPlayerInputCapability {


    void acceptNewInput(KeyInputPack keyInputPack);

    boolean isDown(KeyInput keyInput);

    /***
     * 长按
     */
    boolean isLongHold(KeyInput keyInput);

    class PlayerInputCapability implements IPlayerInputCapability {
        final Player player;

        final EnumMap<KeyInput, Boolean> downMap;
        final EnumMap<KeyInput, Integer> holdMap;

        public PlayerInputCapability(Player player) {
            this.player = player;

            downMap = new EnumMap<>(KeyInput.class);
            holdMap = new EnumMap<>(KeyInput.class);
            for(KeyInput value : KeyInput.values()) {
                downMap.put(value, false);
                holdMap.put(value, 0);
            }
        }

        @Override
        public void acceptNewInput(KeyInputPack keyInputPack) {
            EnumMap<KeyInput, Boolean> newDown = keyInputPack.getIsDown();
            NeoForge.EVENT_BUS.post(new KeyUpdateEvent(player, this, newDown));

            for(KeyInput key : KeyInput.values()) {
                if (downMap.get(key) && !newDown.get(key)) {
                    if (isLongHold(key)) {
                        NeoForge.EVENT_BUS.post(new KeyInputEvent(player, this, key, KeyInputEvent.KeyType.HOLD_UP));
                    }
                    NeoForge.EVENT_BUS.post(new KeyInputEvent(player, this, key, KeyInputEvent.KeyType.UP));
                    holdMap.put(key, 0);
                    continue;
                }
                if (!downMap.get(key) && newDown.get(key)) {
                    NeoForge.EVENT_BUS.post(new KeyInputEvent(player, this, key, KeyInputEvent.KeyType.DOWN));

                    continue;
                }
                holdMap.put(key, holdMap.get(key) + 1);
                if (isLongHold(key)) {
                    NeoForge.EVENT_BUS.post(new KeyInputEvent(player, this, key, KeyInputEvent.KeyType.HOLD));
                }
                downMap.put(key, newDown.get(key));
            }

        }

        @Override
        public boolean isDown(KeyInput keyInput) {
            return downMap.get(keyInput);
        }

        @Override
        public boolean isLongHold(KeyInput keyInput) {
            return isDown(keyInput) && holdMap.get(keyInput) > 20;
        }
    }
}
