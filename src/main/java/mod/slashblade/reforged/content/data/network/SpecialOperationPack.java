package mod.slashblade.reforged.content.data.network;

import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: til
 * @Description: 使用特殊行动的标记类
 */
public class SpecialOperationPack implements CustomPacketPayload {
    public static final SpecialOperationPack INSTANCE = new SpecialOperationPack();
    public static final CustomPacketPayload.Type<SpecialOperationPack> TYPE = new CustomPacketPayload.Type<>(SlashbladeMod.prefix("special_operation_pack"));

    private SpecialOperationPack() {
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
