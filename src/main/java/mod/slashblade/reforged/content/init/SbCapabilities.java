package mod.slashblade.reforged.content.init;


import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.capabilitie.IPlayerInputCapability;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * @Author: til
 * @Description: 能力注入类
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class SbCapabilities {
    public static final EntityCapability<IPlayerInputCapability, Void> PLAYER_INPUT_CAPABILITY =
            EntityCapability.createVoid(
                    SlashbladeMod.prefix("player_input_capability"),
                    IPlayerInputCapability.class
            );

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(PLAYER_INPUT_CAPABILITY, EntityType.PLAYER, (e, c) -> new IPlayerInputCapability.PlayerInputCapability(e));
    }


    public static void register(IEventBus bus) {
    }

}
