package mod.slashblade.reforged.core.obj.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.core.obj.ModelParseException;
import mod.slashblade.reforged.core.obj.ObjModel;
import mod.slashblade.reforged.core.obj.ObjReader;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 11:54
 * @Description: Obj模型管理，用于加载和缓存，获取Obj模型。
 */
@OnlyIn(Dist.CLIENT)
public class ObjModelManager implements PreparableReloadListener {

    private static final Map<ResourceLocation, ObjModel> MODELS        = new HashMap<>();
    private static final String                          MODEL_DIR     = "model";
    private static final String                          FILE_TYPES    = ".obj";

    private static       ObjModel                        DEFAULT_MODEL = null;

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
        MODELS.clear();
        System.out.println("NB");

        try {

            DEFAULT_MODEL = new ObjReader(DefaultResources.DEFAULT_MODEL).getModel();

        } catch (IOException | ModelParseException e) {

            throw new RuntimeException("Failed to load default model: " + DefaultResources.DEFAULT_MODEL, e);

        }

        Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                MODEL_DIR, resLoc -> resLoc.getPath().toLowerCase(Locale.ROOT).endsWith(FILE_TYPES)
        );
        resources.forEach((resourceLocation, resource) -> {
            ObjModel model;

            try {

                model = new ObjReader(resource).getModel();

            } catch (IOException | ModelParseException e) {

                model = DEFAULT_MODEL;

                SlashbladeMod.LOGGER.warn("Failed to load model: {}", resourceLocation, e);

            }

            MODELS.put(resourceLocation, model);
        });
    }

    public static ObjModel get(ResourceLocation resourceLocation) {
        if (resourceLocation != null){
            ObjModel model = MODELS.computeIfAbsent(resourceLocation, m -> {
                try {

                    return new ObjReader(resourceLocation).getModel();

                } catch (IOException | ModelParseException e) {

                    SlashbladeMod.LOGGER.warn("Failed to load model: {}", resourceLocation, e);

                    return DEFAULT_MODEL;

                }
            });

            return model;
        }

        return DEFAULT_MODEL;
    }
}
