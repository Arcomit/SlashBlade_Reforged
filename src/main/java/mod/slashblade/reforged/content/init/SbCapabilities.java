package mod.slashblade.reforged.content.init;


import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.capabilitie.ILockTarget;
import mod.slashblade.reforged.content.data.capabilitie.IInputCapability;
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

    /*public static final ItemCapability<ISlashBladeMaterial, Void> SLASH_BLADE_MATERIAL =
            ItemCapability.createVoid(
                    SlashbladeMod.prefix("slash_blade_material"),
                    ISlashBladeMaterial.class
            );*/

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(INPUT_CAPABILITY, EntityType.PLAYER, (e, c) -> e.getData(SbAttachmentTypes.INPUT_CAPABILITY));
        event.registerEntity(LOCK_TARGET, EntityType.PLAYER, (e, c) -> e.getData(SbAttachmentTypes.LOCK_TARGET));

        /*event.registerItem(SLASH_BLADE_MATERIAL,
                (i, c) -> i.getItem() instanceof ISlashBladeMaterial
                        ? (ISlashBladeMaterial) i.getItem()
                        : null,
                SbItems.PROUD_SOUL.get(),
                SbItems.PROUD_SOUL_INGOT.get(),
                SbItems.PROUD_SOUL_TINY.get(),
                SbItems.PROUD_SOUL_SPHERE.get(),
                SbItems.PROUD_SOUL_CRYSTAL.get(),
                SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()
        );*/
    }


    public static void register(IEventBus bus) {
    }

}
