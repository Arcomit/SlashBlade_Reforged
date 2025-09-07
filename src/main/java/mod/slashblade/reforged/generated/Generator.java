package mod.slashblade.reforged.generated;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.generated.group.ItemStackDataPackGenerator;
import mod.slashblade.reforged.generated.recipe.SlashBladeRecipeProvider;
import mod.slashblade.reforged.generated.recipe.SlashBladeRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 主数据生成器，负责协调所有数据生成器的注册和执行
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class Generator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeServer(),
                new ItemStackDataPackGenerator(
                        event.getGenerator().getPackOutput(),
                        event.getLookupProvider(),
                        List.of(ItemStackDataPackGenerator.class)
                )
        );
        event.getGenerator().addProvider(
                event.includeServer(),
                new SlashBladeRecipeProvider(
                        event.getGenerator().getPackOutput(),
                        event.getLookupProvider(),
                        List.of(SlashBladeRecipes.class),
                        SlashbladeMod.MODID
                )
        );
    }


}
