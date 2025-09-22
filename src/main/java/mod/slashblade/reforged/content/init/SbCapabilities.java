package mod.slashblade.reforged.content.init;


import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.capability.ILockTarget;
import mod.slashblade.reforged.content.capability.IInputCapability;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * @Author: til
 * @Description: 能力注入类
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class SbCapabilities {

    public static final EntityCapability<IInputCapability, Void> INPUT_CAPABILITY =
            EntityCapability.createVoid(
                    SlashbladeMod.prefix("input_capability"),
                    IInputCapability.class
            );

    public static final EntityCapability<ILockTarget, Void> LOCK_TARGET =
            EntityCapability.createVoid(
                    SlashbladeMod.prefix("lock_target"),
                    ILockTarget.class
            );


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(INPUT_CAPABILITY, EntityType.PLAYER, (e, c) -> e.getData(SbAttachmentTypes.INPUT_CAPABILITY));
        event.registerEntity(LOCK_TARGET, EntityType.PLAYER, (e, c) -> e.getData(SbAttachmentTypes.LOCK_TARGET));
    }


    public static void register(IEventBus bus) {
    }

}
