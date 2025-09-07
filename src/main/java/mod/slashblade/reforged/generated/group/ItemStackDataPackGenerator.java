package mod.slashblade.reforged.generated.group;

import com.mojang.serialization.JsonOps;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.utils.tuple.Tuple2;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * 用于生成自定义ItemStack数据包的生成器
 */
public class ItemStackDataPackGenerator implements DataProvider {

    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    List<Class<?>> classes;

    public ItemStackDataPackGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, List<Class<?>> classes) {
        this.output = output;
        this.registries = registries;
        this.classes = classes;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {

        return registries.thenCompose(
                v -> CompletableFuture.allOf(
                        classes.stream()
                                .flatMap(c -> Arrays.stream(SlashBladeItemStacks.class.getDeclaredFields()))
                                .filter(field -> Supplier.class.isAssignableFrom(field.getType()))
                                .filter(field -> Modifier.isStatic(field.getModifiers()))
                                .filter(field -> Modifier.isFinal(field.getModifiers()))
                                .peek(field -> field.setAccessible(true))
                                .map(
                                        field -> {
                                            try {
                                                return new Tuple2<>(field, field.get(null));
                                            } catch (IllegalAccessException e) {
                                                SlashbladeMod.LOGGER.warn("", e); // TODO 日志输出
                                                return null;
                                            }
                                        }
                                )
                                .filter(Objects::nonNull)
                                .filter(o -> o.getB() instanceof Supplier<?>)
                                .map(o -> new Tuple2<>(o.getA(), ((Supplier<?>) o.getB()).get()))
                                .filter(o -> o.getB() instanceof ItemStack)
                                .map(o -> new Tuple2<>(o.getA(), (ItemStack) o.getB()))
                                .map(
                                        t -> DataProvider.saveStable(cache, ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, t.getB()).getOrThrow(), output.getOutputFolder()
                                                .resolve("data")
                                                .resolve(SlashbladeMod.MODID)
                                                .resolve("group_items")
                                                .resolve(t.getA().getName().toLowerCase() + ".json"))
                                )
                                .toArray(CompletableFuture[]::new)
                )
        );


    }


    @Override
    public @NotNull String getName() {
        return "SlashBlade ItemStack DataPack Generator";
    }
}
