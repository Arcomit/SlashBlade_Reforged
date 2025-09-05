package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-19 16:24
 * @Description: 集中管理所有模组数据组件的注册（且能够从中获取调用）
 */
public class SbDataComponentTypes {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SlashbladeMod.MODID);

    public static final Supplier<DataComponentType<ResourceLocation>> MODEL_LOCATION = DATA_COMPONENTS.register(
            "model_location",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> TEXTURE_LOCATION = DATA_COMPONENTS.register(
            "texture_location",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> DRAW_ACTION = DATA_COMPONENTS.register(
            "draw_action",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> WALK_ACTION = DATA_COMPONENTS.register(
            "walk_action",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> SPRINT_ACTION = DATA_COMPONENTS.register(
            "sprint_action",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> WALK_UNSHEATHE_ACTION = DATA_COMPONENTS.register(
            "walk_unsheathe_action",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> SPRINT_UNSHEATHE_ACTION = DATA_COMPONENTS.register(
            "sprint_unsheathe_action",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> SHEATH_ACTION = DATA_COMPONENTS.register(
            "sheath_action",
            () ->  DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> PUT_AWAY_ACTION = DATA_COMPONENTS.register(
            "put_away_action",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build()
    );


    // todo: 测试用例
//    public static final Codec<SlashBladeAnimationGraph> BASIC_CODEC = RecordCodecBuilder.create(instance ->
//            instance.group(
//                    Codec.INT.fieldOf("test1").forGetter(SlashBladeAnimationGraph::getTest),
//                    Codec.BOOL.fieldOf("test2").forGetter(SlashBladeAnimationGraph::isTest2)
//            ).apply(instance, SlashBladeAnimationGraph::new)
//    );
//    public static final StreamCodec<ByteBuf, SlashBladeAnimationGraph> BASIC_STREAM_CODEC = StreamCodec.composite(
//            ByteBufCodecs.INT, SlashBladeAnimationGraph::getTest,
//            ByteBufCodecs.BOOL, SlashBladeAnimationGraph::isTest2,
//            SlashBladeAnimationGraph::new
//    );
//
//
//    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SlashBladeAnimationGraph>> BASIC_EXAMPLE = DATA_COMPONENTS.registerComponentType(
//            "basic",
//            builder -> builder
//                    // The codec to read/write the data to disk
//                    .persistent(BASIC_CODEC)
//                    // The codec to read/write the data across the network
//                    .networkSynchronized(BASIC_STREAM_CODEC)
//    );

    public static void register(IEventBus bus) {
        DATA_COMPONENTS.register(bus);
    }
}
