package mod.slashblade.reforged.generated.recipe;

import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.content.recipe.IRecipeInputItem;
import mod.slashblade.reforged.content.recipe.SlashBladeRecipe;
import mod.slashblade.reforged.generated.group.SlashBladeItemStacks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SlashBladeRecipes {



    public static final Supplier<SlashBladeRecipe> SLASH_BLADE_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            " BI",
                            "L#C",
                            "SG "
                    ),
                    Map.of(
                            'L', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_LAPIS),
                            'C', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_COAL),
                            'I', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(SbItems.PROUD_SOUL_SPHERE.get())),
                            'B', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.RODS_BLAZE),
                            'G', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.INGOTS_GOLD),
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.SHARPNESS_WHITE.get())
                    ),
                    '#',
                    SlashBladeItemStacks.SLASH_BLADE.get()
            );
}
