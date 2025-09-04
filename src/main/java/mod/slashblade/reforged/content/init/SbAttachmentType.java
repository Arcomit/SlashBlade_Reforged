package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.PlayerInputCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class SbAttachmentType {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SlashbladeMod.MODID);

    public static final Supplier<AttachmentType<PlayerInputCapability>> PLAYER_INPUT_CAPABILITY = ATTACHMENT_TYPES.register(
            "player_input_capability",
            () -> AttachmentType.serializable(
                    s -> {
                        if (s instanceof Player player) {
                            return new PlayerInputCapability(player);
                        } else {
                            return null;
                        }
                    }
            ).build()
    );

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }

}
