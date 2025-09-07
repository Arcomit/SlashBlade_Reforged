package mod.slashblade.reforged.core.animation.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import mod.slashblade.reforged.content.recipe.IRecipeInputItem;
import mod.slashblade.reforged.content.serialize.BooleanSerializer;
import mod.slashblade.reforged.content.serialize.UseCodecSerializer;
import mod.slashblade.reforged.core.animation.pojo.AnimationKeyframes;
import mod.slashblade.reforged.core.animation.pojo.SoundEffectKeyframes;
import mod.slashblade.reforged.core.animation.pojo.exclusion.ServerExclusionStrategy;
import mod.slashblade.reforged.core.animation.pojo.serialize.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.joml.Vector3f;

import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-14 17:51
 * @Description: 用于将动画json文件转换为java对象
 */
public class GsonUtil {

    public static final Gson CLIENT_GSON = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(Vector3f.class, new Vector3fSerializer())
            .registerTypeAdapter(AnimationKeyframes.class, new AnimationKeyframesSerializer())
            .registerTypeAdapter(SoundEffectKeyframes.class, new SoundEffectKeyframesSerializer())
            .registerTypeAdapter(Boolean.class, new BooleanSerializer())
            .registerTypeAdapter(boolean.class, new BooleanSerializer())
            .create();

    public static final Gson SERVER_GSON = new GsonBuilder()
            .addDeserializationExclusionStrategy(new ServerExclusionStrategy())
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(SoundEffectKeyframes.class, new SoundEffectKeyframesSerializer())
            .registerTypeAdapter(Boolean.class, new BooleanSerializer())
            .registerTypeAdapter(boolean.class, new BooleanSerializer())
            .registerTypeAdapter(Ingredient.class, new UseCodecSerializer<>(Ingredient.CODEC))
            .registerTypeAdapter(ItemStack.class, new UseCodecSerializer<>(ItemStack.CODEC))
            .create();
}
