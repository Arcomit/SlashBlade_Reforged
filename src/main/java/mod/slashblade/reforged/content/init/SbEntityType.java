package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.entity.DriveEntity;
import mod.slashblade.reforged.content.entity.JudgementCutEntity;
import mod.slashblade.reforged.content.entity.LightningEntity;
import mod.slashblade.reforged.content.entity.SlashEffectEntity;
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

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, SlashbladeMod.MODID);

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

    public static final Supplier<EntityType<DriveEntity>> DRIVE_ENTITY = ENTITY_TYPE_REGISTER.register(
            "drive_entity",
            () -> EntityType.Builder.<DriveEntity>of(
                            (e, l) -> new DriveEntity(e, l, null),
                            MobCategory.MISC
                    )
                    .sized(1.0f, 1.0f)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .fireImmune()
                    .noSave()
                    .build("drive_entity")
    );

    public static final Supplier<EntityType<LightningEntity>> LIGHTNING_ENTITY = ENTITY_TYPE_REGISTER.register(
            "lightning_entity",
            () -> EntityType.Builder.<LightningEntity>of(
                            (e, l) -> new LightningEntity(e, l, null),
                            MobCategory.MISC
                    )
                    .sized(0.1f, 0.1f)
                    .setShouldReceiveVelocityUpdates(false)
                    .setUpdateInterval(1)
                    .fireImmune()
                    .noSave()
                    .build("lightning_entity")
    );

    public static final Supplier<EntityType<SlashEffectEntity>> SLASH_EFFECT_ENTITY = ENTITY_TYPE_REGISTER.register(
            "slash_effect_entity",
            () -> EntityType.Builder.<SlashEffectEntity>of(
                            (e, l) -> new SlashEffectEntity(e, l, null),
                            MobCategory.MISC
                    )
                    .sized(1.0f, 1.0f)
                    .setShouldReceiveVelocityUpdates(false)
                    .setUpdateInterval(1)
                    .fireImmune()
                    .noSave()
                    .build("slash_effect_entity")
    );

    public static final Supplier<EntityType<JudgementCutEntity>> JUDGEMENT_CUT_ENTITY = ENTITY_TYPE_REGISTER.register(
            "judgement_cut_entity",
            () -> EntityType.Builder.<JudgementCutEntity>of(
                            (e, l) -> new JudgementCutEntity(e, l, null),
                            MobCategory.MISC
                    )
                    .sized(1.0f, 1.0f)
                    .setShouldReceiveVelocityUpdates(false)
                    .setUpdateInterval(1)
                    .fireImmune()
                    .noSave()
                    .build("judgement_cut_entity")
    );


    public static void register(IEventBus bus) {
        ENTITY_TYPE_REGISTER.register(bus);
    }
}
