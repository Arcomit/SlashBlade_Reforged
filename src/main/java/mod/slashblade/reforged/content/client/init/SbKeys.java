package mod.slashblade.reforged.content.client.init;

import com.mojang.blaze3d.platform.InputConstants;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.network.SpecialOperationPack;
import mod.slashblade.reforged.content.data.network.SummoningSummondSwordPack;
import mod.slashblade.reforged.content.init.SbRegisterPayloads;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = SlashbladeMod.MODID, value = Dist.CLIENT)
public class SbKeys {


    public static final Lazy<KeyMapping> SUMMONING_SUMMOND_SWORD = Lazy.of(
            () -> new KeyMapping(
                    SlashbladeMod.prefix("summoning_summond_sword").toString(),
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_MOUSE_BUTTON_3,
                    SlashbladeMod.MODID
            )
    );
    public static final Lazy<KeyMapping> SPECIAL_OPERATION = Lazy.of(
            () -> new KeyMapping(
                    SlashbladeMod.prefix("special_operation").toString(),
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    SlashbladeMod.MODID
            )
    );


    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SUMMONING_SUMMOND_SWORD.get());
        event.register(SPECIAL_OPERATION.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (SUMMONING_SUMMOND_SWORD.get().consumeClick()) {
            PacketDistributor.sendToServer(SummoningSummondSwordPack.INSTANCE);
        }
        while (SPECIAL_OPERATION.get().consumeClick()) {
            PacketDistributor.sendToServer(SpecialOperationPack.INSTANCE);
        }
    }
}
