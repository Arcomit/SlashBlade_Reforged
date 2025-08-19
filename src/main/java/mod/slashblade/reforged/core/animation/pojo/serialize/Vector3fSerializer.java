package mod.slashblade.reforged.core.animation.pojo.serialize;

import com.google.gson.*;
import net.minecraft.util.GsonHelper;
import org.joml.Vector3f;

import java.lang.reflect.Type;

public class Vector3fSerializer implements JsonDeserializer<Vector3f>, JsonSerializer<Vector3f> {
    @Override
    public Vector3f deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            JsonElement xElement = array.get(0);
            JsonElement yElement = array.get(1);
            JsonElement zElement = array.get(2);
            float x = GsonHelper.convertToFloat(xElement, "(array i=0)");
            float y = GsonHelper.convertToFloat(yElement, "(array i=1)");
            float z = GsonHelper.convertToFloat(zElement, "(array i=2)");
            return new Vector3f(x, y, z);
        } else {
            throw new JsonSyntaxException("Expected " + json + " to be a Vector3f because it's not an array");
        }
    }

    @Override
    public JsonElement serialize(Vector3f src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        array.add(new JsonPrimitive(src.x()));
        array.add(new JsonPrimitive(src.y()));
        array.add(new JsonPrimitive(src.z()));
        return array;
    }
}