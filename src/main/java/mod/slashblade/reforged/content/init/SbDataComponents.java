package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-19 16:24
 * @Description: 集中管理所有模组数据组件的注册（且能够从中获取调用）
 */
public class SbDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SlashbladeMod.MODID);

    public static final Supplier<DataComponentType<ResourceLocation>> DRAW_ACTION =
            DATA_COMPONENTS.register("draw_action", () ->
                    DataComponentType.<ResourceLocation>builder()
                            .persistent(ResourceLocation.CODEC)
                            .networkSynchronized(ResourceLocation.STREAM_CODEC)
                            .build()
            );

    public static final Supplier<DataComponentType<ResourceLocation>> WALK_ACTION =
            DATA_COMPONENTS.register("walk_action", () ->
                    DataComponentType.<ResourceLocation>builder()
                            .persistent(ResourceLocation.CODEC)
                            .networkSynchronized(ResourceLocation.STREAM_CODEC)
                            .build()
            );

    public static final Supplier<DataComponentType<ResourceLocation>> SPRINT_ACTION =
            DATA_COMPONENTS.register("sprint_action", () ->
                    DataComponentType.<ResourceLocation>builder()
                            .persistent(ResourceLocation.CODEC)
                            .networkSynchronized(ResourceLocation.STREAM_CODEC)
                            .build()
            );

    public static final Supplier<DataComponentType<ResourceLocation>> WALK_UNSHEATHE_ACTION =
            DATA_COMPONENTS.register("walk_unsheathe_action", () ->
                    DataComponentType.<ResourceLocation>builder()
                            .persistent(ResourceLocation.CODEC)
                            .networkSynchronized(ResourceLocation.STREAM_CODEC)
                            .build()
            );

    public static final Supplier<DataComponentType<ResourceLocation>> SPRINT_UNSHEATHE_ACTION =
            DATA_COMPONENTS.register("sprint_unsheathe_action", () ->
                    DataComponentType.<ResourceLocation>builder()
                            .persistent(ResourceLocation.CODEC)
                            .networkSynchronized(ResourceLocation.STREAM_CODEC)
                            .build()
            );

    public static final Supplier<DataComponentType<ResourceLocation>> SHEATH_ACTION =
            DATA_COMPONENTS.register("sheath_action", () ->
                    DataComponentType.<ResourceLocation>builder()
                            .persistent(ResourceLocation.CODEC)
                            .networkSynchronized(ResourceLocation.STREAM_CODEC)
                            .build()
            );

    public static final Supplier<DataComponentType<ResourceLocation>> PUT_AWAY_ACTION =
            DATA_COMPONENTS.register("put_away_action", () ->
                    DataComponentType.<ResourceLocation>builder()
                            .persistent(ResourceLocation.CODEC)
                            .networkSynchronized(ResourceLocation.STREAM_CODEC)
                            .build()
            );

    public static void register(IEventBus bus) {
        DATA_COMPONENTS.register(bus);
    }
}
