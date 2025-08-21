package mod.slashblade.reforged.core.animation.event;

import com.google.common.collect.Maps;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.core.animation.AnimationAsset;
import mod.slashblade.reforged.core.animation.pojo.AnimationFile;
import mod.slashblade.reforged.core.animation.pojo.AnimationPOJO;
import mod.slashblade.reforged.core.animation.utils.GsonUtil;
import mod.slashblade.reforged.core.obj.ModelParseException;
import mod.slashblade.reforged.core.obj.ObjGroupIndexProvider;
import mod.slashblade.reforged.core.obj.ObjModel;
import mod.slashblade.reforged.core.obj.ObjReader;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 15:08
 * @Description: 动作管理器
 */
@OnlyIn(Dist.CLIENT)
public class AnimationManager implements PreparableReloadListener {

    private static final Map<String, AnimationAsset> ANIMATIONS        = new HashMap<>();
    private static final String                      FILE_DIR          = "slashblade/animations";
    private static final String                      FILE_TYPES        = ".json";

    public static final  ObjGroupIndexProvider       INDEX_PROVIDER    = new ObjGroupIndexProvider();

    @Override
    public CompletableFuture<Void> reload(
            PreparationBarrier preparationBarrier,
            ResourceManager    resourceManager,
            ProfilerFiller     preparationsProfiler,
            ProfilerFiller     reloadProfiler,
            Executor           backgroundExecutor,
            Executor           gameExecutor
    ) {
        return CompletableFuture.runAsync(
                        () -> {
                            loadResources(resourceManager);
                        }
                        , backgroundExecutor
                )
                .thenCompose(preparationBarrier::wait);
    }

    private void loadResources(ResourceManager resourceManager) {
        ANIMATIONS.clear();

        Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                FILE_DIR, resLoc -> resLoc.getPath().toLowerCase(Locale.ROOT).endsWith(FILE_TYPES)
        );

        resources.forEach((resourceLocation, resource) -> {
            try {

                InputStream inputStream = resource.open();

                AnimationFile file = GsonUtil.CLIENT_GSON.fromJson(new InputStreamReader(inputStream), AnimationFile.class);

                List<AnimationAsset> animations = AnimationAsset.createAnimation(file, INDEX_PROVIDER);

                for (AnimationAsset animation : animations) {

                    ANIMATIONS.put(animation.getName(), animation);
                    System.out.println("Loaded animation: " + animation.getName());

                }

            } catch (IOException e) {

                throw new RuntimeException("Failed to load animation: " + resourceLocation, e);

            }
        });
    }

    public static AnimationAsset get(String animationName) {

        if (animationName != null){

            return ANIMATIONS.computeIfAbsent(animationName, m -> ANIMATIONS.get(DefaultResources.DEFAULT_ANIMATION));

        }

        return ANIMATIONS.get(DefaultResources.DEFAULT_ANIMATION);
    }
}