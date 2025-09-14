package mod.slashblade.reforged.generated;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.generated.group.ItemStackDataPackGenerator;
import mod.slashblade.reforged.generated.loot.SlashBladeLootTableProvider;
import mod.slashblade.reforged.generated.recipe.SlashBladeRecipeProvider;
import mod.slashblade.reforged.generated.recipe.SlashBladeRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 主数据生成器，负责协调所有数据生成器的注册和执行
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class Generator {

    protected static HolderLookup.Provider provider;

    public static HolderLookup.Provider grtProvider() {
        return provider;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onGatherData(GatherDataEvent event) {
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider()
                .thenApply(p -> {
                            provider = p;
                            return p;
                        }
                );

        event.getGenerator().addProvider(
                event.includeClient(),
                new ItemStackDataPackGenerator(
                        event.getGenerator().getPackOutput(),
                        lookupProvider,
                        List.of(ItemStackDataPackGenerator.class)
                )
        );
        event.getGenerator().addProvider(
                event.includeClient(),
                new SlashBladeRecipeProvider(
                        event.getGenerator().getPackOutput(),
                        lookupProvider,
                        List.of(SlashBladeRecipes.class),
                        SlashbladeMod.MODID
                )
        );

        // 注册战利品表生成器
        event.getGenerator().addProvider(
                event.includeServer(),
                new SlashBladeLootTableProvider(
                        event.getGenerator().getPackOutput(),
                        lookupProvider
                )
        );

    }


}
