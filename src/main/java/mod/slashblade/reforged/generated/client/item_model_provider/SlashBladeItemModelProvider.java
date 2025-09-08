package mod.slashblade.reforged.generated.client.item_model_provider;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.utils.tuple.Tuple2;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * SlashBlade模组的物品模型生成器
 */
public class SlashBladeItemModelProvider extends ItemModelProvider {


    public SlashBladeItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generateProudSoulItemBuilder(BuiltInRegistries.ITEM.getKey(SbItems.PROUD_SOUL.get()).getPath());
        generateProudSoulItemBuilder(BuiltInRegistries.ITEM.getKey(SbItems.PROUD_SOUL_INGOT.get()).getPath());
        generateProudSoulItemBuilder(BuiltInRegistries.ITEM.getKey(SbItems.PROUD_SOUL_TINY.get()).getPath());
        generateProudSoulItemBuilder(BuiltInRegistries.ITEM.getKey(SbItems.PROUD_SOUL_SPHERE.get()).getPath());
        generateProudSoulItemBuilder(BuiltInRegistries.ITEM.getKey(SbItems.PROUD_SOUL_CRYSTAL.get()).getPath());
        generateProudSoulItemBuilder(BuiltInRegistries.ITEM.getKey(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()).getPath());
    }


    @SuppressWarnings("UnusedReturnValue")
    public ItemModelBuilder generateProudSoulItemBuilder(String item) {

        return getBuilder(item)
                //.parent(new ModelFile.UncheckedModelFile(mcLoc("prefab/" + item)))
                .texture("layer0", SlashbladeMod.prefix("soul"))
                .guiLight(BlockModel.GuiLight.FRONT)
                .customLoader(ObjModelBuilder::begin)
                .modelLocation(SlashbladeMod.prefix("models/prefab/" + item + ".obj"))
                .flipV(true)
                .end()

                .transforms()

                .transform(ItemDisplayContext.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 3, 0)
                .scale(0.5f, 0.5f, 0.5f)
                .end()

                .transform(ItemDisplayContext.GUI)
                .rotation(10, 0, 0)
                .translation(0, 0, 0)
                .scale(0.9f, 0.9f, 0.9f)
                .end()

                .transform(ItemDisplayContext.HEAD)
                .rotation(0, 180, 0)
                .translation(0, 13, 7)
                .scale(1, 1, 1)
                .end()

                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, 0, 0)
                .translation(0, 3, 1)
                .scale(0.55f, 0.55f, 0.55f)
                .end()

                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, -90, 25)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end()

                .transform(ItemDisplayContext.FIXED)
                .rotation(-90, 0, 0)
                .translation(0, 0, -10)
                .scale(1, 1, 1)
                .end()

                .end();


    }

}
