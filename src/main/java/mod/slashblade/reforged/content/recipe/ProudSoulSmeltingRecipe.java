package mod.slashblade.reforged.content.recipe;

import mod.slashblade.reforged.content.init.SbRecipeSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ProudSoulSmeltingRecipe extends SmeltingRecipe {

    public ProudSoulSmeltingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }


    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        ItemStack item = input.item();
        ItemStack output = super.assemble(input, registries);
        EnchantmentHelper.setEnchantments(output, item.getTagEnchantments());
        return output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SbRecipeSerializer.PROUD_SOUL_SMELTING_RECIPE.get();
    }

}
