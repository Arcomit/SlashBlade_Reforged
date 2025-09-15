package mod.slashblade.reforged.content.mixin;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.utils.Util;
import mod.slashblade.reforged.utils.tuple.Tuple2;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Detainted;
import javax.annotation.Nullable;
import java.awt.image.SampleModel;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("UnusedMixin")
@Deprecated
@Mixin(PatchedDataComponentMap.class)
public abstract class PatchedDataComponentMapAccessor {

    @Accessor
    public abstract DataComponentMap getPrototype();

    @Accessor
    public abstract Reference2ObjectMap<DataComponentType<?>, Optional<?>> getPatch();

    @Accessor
    public abstract  void setPatch(Reference2ObjectMap<DataComponentType<?>, Optional<?>> patch);

    @Accessor
    public abstract   boolean isCopyOnWrite();

    @Accessor
    public abstract  void setCopyOnWrite(boolean copyOnWrite);

    /**
     * @author til
     * @reason
     */
    @Overwrite
    public void ensureMapOwnership() {
        if (!isCopyOnWrite()) {
            return;
        }

        setCopyOnWrite(false);

        Reference2ObjectMap<DataComponentType<?>, Optional<?>> patch = getPatch();
        patch = new Reference2ObjectArrayMap<>(
                patch.entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> {
                                            Optional<?> value = e.getValue();
                                            if (value.isEmpty()) {
                                                return Optional.empty();
                                            }
                                            Object o = value.get();
                                            if (!(o instanceof Cloneable)) {
                                                return value;
                                            }

                                            Method clone;
                                            try {
                                                clone = o.getClass().getMethod("clone");
                                                o = clone.invoke(o);
                                            } catch (Exception ex) {
                                                SlashbladeMod.LOGGER.error("clone {} error", o.getClass(), ex);
                                            }

                                            return Optional.of(o);
                                        }
                                )
                        )
        );
        setPatch(patch);
    }

    /**
     * @author til
     * @reason
     */
    @Nullable
    @Overwrite
    public <T> T get(DataComponentType<? extends T> component) {


        Optional<?> optional = getPatch().get(component);
        Object o;
        //noinspection OptionalAssignedToNull
        if (optional != null)  {
            o = optional.orElse(null);
        }
        else {
            o = getPrototype().get(component);
        }

        if (isCopyOnWrite() && o instanceof Cloneable) {
            ensureMapOwnership();
            return get(component);
        }

        return Util.forcedConversion(o);

    }
}
