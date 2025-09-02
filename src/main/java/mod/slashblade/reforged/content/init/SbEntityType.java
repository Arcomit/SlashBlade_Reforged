package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.utils.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.guieffect.qual.UIType;

import java.util.function.Supplier;

public class SbEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SlashbladeMod.MODID);

    public static final Supplier<EntityType<SummondSwordEntity>> SUMMOND_SWORD_ENTITY = ENTITY_TYPE_REGISTER.register(
            "summond_sword_entity",
            () -> EntityType.Builder.<SummondSwordEntity>of(
                            (e, l) -> new SummondSwordEntity(e, l, null),
                            MobCategory.MISC
                    )
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(20)
                    .fireImmune()
                    .noSave()
                    .build("summond_sword_entity")
    );

    public static void register(IEventBus bus) {
        bus.register(ENTITY_TYPE_REGISTER);
    }
}
