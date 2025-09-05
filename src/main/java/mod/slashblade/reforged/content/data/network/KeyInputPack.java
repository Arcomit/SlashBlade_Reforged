package mod.slashblade.reforged.content.data.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.KeyInput;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashSet;

@Data
@AllArgsConstructor
public class KeyInputPack implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KeyInputPack> TYPE = new CustomPacketPayload.Type<>(SlashbladeMod.prefix("key_input_pack"));

    EnumMap<KeyInput, Boolean> isDown;

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
