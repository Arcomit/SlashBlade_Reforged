package mod.slashblade.reforged.core.animation.pojo.serialize;

import com.google.gson.*;
import mod.slashblade.reforged.core.animation.pojo.AnimationKeyframes;

import java.lang.reflect.Type;

public class BooleanSerializer implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return false;
        }


        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = (JsonPrimitive) json;

            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            }


            if (primitive.isNumber()) {
                return primitive.getAsDouble() != 0;
            }

            if (primitive.isString()) {
                return "true".equals(primitive.getAsString());
            }

        }

        return true;
    }

    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src);
    }
}
