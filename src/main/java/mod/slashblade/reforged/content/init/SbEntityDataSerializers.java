package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.utils.constant.ByteBufCodecConstants;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.awt.*;
import java.util.function.Supplier;

/**
 * @Author: til
 * @Description: EntityDataSerializer 常量类
 */
public class SbEntityDataSerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, SlashbladeMod.MODID);

    public static final Supplier<EntityDataSerializer<ResourceLocation>> RESOURCE_LOCATION = ENTITY_DATA_SERIALIZERS.register("resource_location", () -> EntityDataSerializer.forValueType(ResourceLocation.STREAM_CODEC));
    public static final Supplier<EntityDataSerializer<SummondSwordEntity.ActionType>> SUMMOND_SWORD_ENTITY_ACTION_TYPE = ENTITY_DATA_SERIALIZERS.register("summond_sword_entity_action_type", () -> EntityDataSerializer.forValueType(ByteBufCodecConstants.SUMMOND_SWORD_ENTITY_ACTION_TYPE));
    public static final Supplier<EntityDataSerializer<Color>> COLOR = ENTITY_DATA_SERIALIZERS.register("color", () -> EntityDataSerializer.forValueType(ByteBufCodecConstants.COLOR));

    public static void register(IEventBus bus) {
        ENTITY_DATA_SERIALIZERS.register(bus);
    }

}
