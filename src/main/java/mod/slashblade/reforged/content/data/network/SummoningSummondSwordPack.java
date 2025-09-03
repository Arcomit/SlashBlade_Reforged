package mod.slashblade.reforged.content.data.network;

import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: til
 * @Description: 召唤幻影剑的标记类
 */
public class SummoningSummondSwordPack implements CustomPacketPayload {
    public static final SummoningSummondSwordPack INSTANCE = new SummoningSummondSwordPack();

    public static final CustomPacketPayload.Type<SummoningSummondSwordPack> TYPE = new CustomPacketPayload.Type<>(SlashbladeMod.prefix("summoning_summond_sword"));

    private SummoningSummondSwordPack() {
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
