package mod.slashblade.reforged.core.animation.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mod.slashblade.reforged.core.animation.pojo.AnimationKeyframes;
import mod.slashblade.reforged.core.animation.pojo.SoundEffectKeyframes;
import mod.slashblade.reforged.core.animation.pojo.serialize.AnimationKeyframesSerializer;
import mod.slashblade.reforged.core.animation.pojo.serialize.SoundEffectKeyframesSerializer;
import mod.slashblade.reforged.core.animation.pojo.serialize.Vector3fSerializer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-14 17:51
 * @Description: TODO
 */
public class GsonUtil {
    public static final Gson CLIENT_GSON = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(Vector3f.class, new Vector3fSerializer())
            .registerTypeAdapter(AnimationKeyframes.class, new AnimationKeyframesSerializer())
            .registerTypeAdapter(SoundEffectKeyframes.class, new SoundEffectKeyframesSerializer())
            .create();
}
