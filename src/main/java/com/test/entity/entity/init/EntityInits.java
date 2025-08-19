package com.test.entity.entity.init;

import mod.slashblade.reforged.SlashbladeMod;
import com.test.entity.entity.TestEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-09 01:22
 * @Description: TODO
 */
public class EntityInits {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, SlashbladeMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<TestEntity>> BLACK_DICK =
            ENTITIES.register("black_dick", () ->
                    EntityType.Builder.<TestEntity>of(TestEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("black_dick"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
