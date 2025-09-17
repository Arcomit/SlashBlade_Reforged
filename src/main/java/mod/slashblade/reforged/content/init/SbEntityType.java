package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

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
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(20)
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
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(false)
                    .setUpdateInterval(20)
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
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(false)
                    .setUpdateInterval(20)
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
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(false)
                    .setUpdateInterval(20)
                    .fireImmune()
                    .noSave()
                    .build("judgement_cut_entity")
    );

    public static final Supplier<EntityType<BladeStandEntity>> BLADE_STAND_ENTITY = ENTITY_TYPE_REGISTER.register(
            "blade_stand_entity",
            () -> EntityType.Builder.<BladeStandEntity>of(
                            (e, l) -> new BladeStandEntity(e, l, null),
                            MobCategory.MISC
                    )
                    .sized(0.5f, 0.5f)
                    .setUpdateInterval(20)
                    .build("blade_stand_entity")
    );

    public static void register(IEventBus bus) {
        ENTITY_TYPE_REGISTER.register(bus);
    }
}
