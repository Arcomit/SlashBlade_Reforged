package mod.slashblade.reforged.utils.constant;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
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
        return Codec.STRING
                .<D>comapFlatMap(s -> DataResult.success(GsonUtil.GSON.fromJson(s, type)), GsonUtil.GSON::toJson)
                .stable();
    }

    public static <D> Codec<D> onlyNew(Supplier<D> create) {
        return Codec.STRING
                .<D>comapFlatMap(s -> DataResult.success(create.get()), v -> "")
                .stable();
    }

}
