package mod.slashblade.reforged.generated.recipe;

import mod.slashblade.reforged.content.recipe.IRecipeInputItem;
import mod.slashblade.reforged.content.recipe.SlashBladeRecipe;
import mod.slashblade.reforged.generated.group.SlashBladeItemStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

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
                            'L', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Blocks.LAPIS_BLOCK)),
                            'C', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Blocks.COAL_BLOCK)),
                            'I', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Blocks.DRAGON_EGG)), //TODO 宝珠
                            'B', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Items.BLAZE_ROD)),
                            'G', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Items.GOLD_INGOT)),
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Items.STRING)),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.SHARPNESS_WHITE.get())
                    ),
                    '#',
                    SlashBladeItemStacks.SLASH_BLADE.get()
            );
}
