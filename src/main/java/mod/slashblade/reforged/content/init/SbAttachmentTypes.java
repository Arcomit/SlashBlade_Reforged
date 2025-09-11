package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationInstance;
import mod.slashblade.reforged.content.data.LockTarget;
import mod.slashblade.reforged.content.data.InputCapability;
import mod.slashblade.reforged.utils.constant.ByteBufCodecConstants;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-02 18:16
 * @Description: TODO
 */
public class SbAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SlashbladeMod.MODID);


    public static final Supplier<AttachmentType<SlashBladeAnimationInstance>> ANIMATION_INSTANCE = ATTACHMENT_TYPES.register(
            "animation_instance", () -> AttachmentType.builder(SlashBladeAnimationInstance::new).build()
    );

    public static final Supplier<AttachmentType<LockTarget>> LOCK_TARGET = ATTACHMENT_TYPES.register(
            "lock_target", () -> AttachmentType.builder(LockTarget::new).sync(ByteBufCodecConstants.LOCK_TARGET).build()
    );

    public static final Supplier<AttachmentType<InputCapability>> INPUT_CAPABILITY = ATTACHMENT_TYPES.register(
            "input_capability",
            () -> AttachmentType.builder(
                    s -> {
                        if (s instanceof LivingEntity livingEntity) {
                            return new InputCapability(livingEntity);
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
