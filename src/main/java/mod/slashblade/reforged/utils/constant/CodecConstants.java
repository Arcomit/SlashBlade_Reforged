package mod.slashblade.reforged.utils.constant;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import mod.slashblade.reforged.content.data.PlayerInputCapability;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.core.animation.utils.GsonUtil;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;
import java.util.function.Supplier;

public class CodecConstants {


    public static final Codec<SlashBladeLogic> SLASH_BLADE_LOGIC = ofJson(SlashBladeLogic.class);
    public static final Codec<SlashBladeStyle> SLASH_BLADE_STYLE = ofJson(SlashBladeStyle.class);


    public static <D> Codec<D> ofJson(Type type) {
        return new PrimitiveCodec<D>() {
            @Override
            public <T> DataResult<D> read(DynamicOps<T> ops, T input) {
                JsonElement jsonElement = ops.convertTo(JsonOps.INSTANCE, input);
                return DataResult.success(GsonUtil.SERVER_GSON.fromJson(jsonElement, type));
            }

            @Override
            public <T> T write(DynamicOps<T> ops, D value) {
                JsonElement jsonElement = GsonUtil.SERVER_GSON.toJsonTree(value);
                return JsonOps.INSTANCE.convertTo(ops, jsonElement);
            }
        };
    }


}
