package mod.slashblade.reforged.content.serialize;

import com.google.gson.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;

public class RegistrySerializer<R> implements JsonDeserializer<R>, JsonSerializer<R> {
    Registry<R> registry;

    public RegistrySerializer(Registry<R> registry) {
        this.registry = registry;
    }

    @Override
    public R deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResourceLocation resourceLocation = context.deserialize(json, ResourceLocation.class);
        return registry.get(resourceLocation);
    }

    @Override
    public JsonElement serialize(R src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(registry.getKey(src), ResourceLocation.class);
    }
}
