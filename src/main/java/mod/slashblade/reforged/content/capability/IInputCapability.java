package mod.slashblade.reforged.content.capability;

import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.network.KeyInputPack;

public interface IInputCapability {


    void acceptNewInput( KeyInputPack keyInputPack);

    boolean isDown(KeyInput keyInput);

    /***
     * 长按
     */
    boolean isLongHold(KeyInput keyInput);

}
