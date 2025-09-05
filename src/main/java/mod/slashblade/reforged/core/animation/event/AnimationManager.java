package mod.slashblade.reforged.core.animation.event;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.maydaymemory.mae.control.montage.AnimationSegment;
import com.maydaymemory.mae.control.montage.AnimationSegmentKeyframe;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.action.Action;
import mod.slashblade.reforged.content.init.SbRegistrys;
import mod.slashblade.reforged.core.animation.AnimationAsset;
import mod.slashblade.reforged.core.animation.pojo.AnimationFile;
import mod.slashblade.reforged.core.animation.pojo.AnimationPOJO;
import mod.slashblade.reforged.core.animation.utils.GsonUtil;
import mod.slashblade.reforged.core.obj.ModelParseException;
import mod.slashblade.reforged.core.obj.ObjGroupIndexProvider;
import mod.slashblade.reforged.core.obj.ObjModel;
import mod.slashblade.reforged.core.obj.ObjReader;
import mod.slashblade.reforged.core.obj.event.ObjModelManager;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 15:08
 * @Description: 动画管理器
 */
public class AnimationManager implements PreparableReloadListener {

    private static final    String                      FILE_DIR          = "slashblade/animations";
    private static final    String                      FILE_TYPES        = ".json";

    private static final    Lock                        LOCK              = new ReentrantLock();
    private static final    Condition                   PREPARED          = LOCK.newCondition();
    private static volatile Map<String, AnimationAsset> animationsCache;

    public  static final    ObjGroupIndexProvider       INDEX_PROVIDER    = new ObjGroupIndexProvider();

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
        SbRegistrys.ACTION_REGISTRY.forEach(Action::resetInit);

        LOCK.lock();
        animationsCache = null;
        LOCK.unlock();

        Map<String, AnimationAsset> cache = Maps.newHashMap();

        Gson gson = FMLLoader.getDist() == Dist.CLIENT ? GsonUtil.CLIENT_GSON : GsonUtil.SERVER_GSON;

        Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                FILE_DIR, resLoc -> resLoc.getPath().toLowerCase(Locale.ROOT).endsWith(FILE_TYPES)
        );

        resources.forEach((resourceLocation, resource) -> {
            try {

                InputStream inputStream = resource.open();

                AnimationFile file = gson.fromJson(new InputStreamReader(inputStream), AnimationFile.class);

                List<AnimationAsset> animations = AnimationAsset.createAnimation(file, INDEX_PROVIDER);

                for (AnimationAsset animation : animations) {

                    cache.put(animation.getName(), animation);
                    System.out.println("Loaded animation: " + animation.getName());

                }

            } catch (IOException e) {

                throw new RuntimeException("Failed to load animation: " + resourceLocation, e);

            }
        });

        // 通知所有等待线程 animationsCache 已准备
        LOCK.lock();
        try {
            animationsCache = cache;
            PREPARED.signalAll();
        } finally {
            LOCK.unlock();
        }
    }

    public static AnimationAsset get(String animationName) {
        LOCK.lock();
        try {
            while (animationsCache == null) {
                PREPARED.await(); // 等待 animationsCache 准备好
            }
            return animationsCache.computeIfAbsent(animationName,
                    aniName -> animationsCache.get(DefaultResources.DEFAULT_ANIMATION)
            );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    public static AnimationSegmentKeyframe constructSegmentKeyframe(String animationName, float keyframeTime, float startTimeS, float endTimeS) {
        return new AnimationSegmentKeyframe(keyframeTime, new AnimationSegment(AnimationManager.get(animationName), startTimeS, endTimeS));
    }
}