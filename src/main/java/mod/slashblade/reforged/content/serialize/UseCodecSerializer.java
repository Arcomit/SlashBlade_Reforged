package mod.slashblade.reforged.content.serialize;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import lombok.AllArgsConstructor;

import java.lang.reflect.Type;

@AllArgsConstructor
public class UseCodecSerializer<D> implements JsonDeserializer<D>, JsonSerializer<D> {
    Codec<D> codec;

    @Override
    public D deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return codec.parse(JsonOps.INSTANCE, json).getOrThrow();
    }

    @Override
    public JsonElement serialize(D src, Type typeOfSrc, JsonSerializationContext context) {
        return codec.encodeStart(JsonOps.INSTANCE, src).getOrThrow();
    }
}
