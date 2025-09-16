package mod.slashblade.reforged.content.recipe;

import com.google.common.collect.Sets;
import lombok.Getter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.content.init.SbRecipeSerializer;
import mod.slashblade.reforged.utils.helper.SlashBladeHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class ProudSoulShapedRecipe implements CraftingRecipe {

    @Getter
    List<String> pattern;
    @Getter
    Map<Character, IRecipeInputItem> key;
    @Getter
    List<Character> inheritanceKey;
    @Getter
    ItemStack result;

    @Getter
    int recipeWidth;
    @Getter
    int recipeHeight;


    NonNullList<IRecipeInputItem> recipeInputItemPackList;
    NonNullList<Ingredient> ingredientNonNullList;
    NonNullList<Boolean> inheritanceList;

    public ProudSoulShapedRecipe(List<String> pattern, Map<Character, IRecipeInputItem> key, List<Character> inheritanceKey, ItemStack result) {
        this.pattern = pattern;
        this.key = key;
        this.inheritanceKey = inheritanceKey;
        this.result = result;

        recipeHeight = getPattern().size();

        if (recipeHeight == 0) {
            throw new IllegalArgumentException("recipe input height is 0");
        }

        recipeWidth = pattern.stream().max(Comparator.comparingInt(String::length)).orElse("").length();

        if (recipeWidth == 0) {
            throw new IllegalArgumentException("recipe input width is 0");
        }

        recipeInputItemPackList = NonNullList.withSize(recipeWidth * recipeHeight, IRecipeInputItem.EMPTY);
        inheritanceList = NonNullList.withSize(recipeWidth * recipeHeight, false);

        Set<Character> set = Sets.newHashSet(key.keySet());

        if (set.contains(' ')) {
            throw new IllegalArgumentException("recipe input key cannot be used ' '");
        }

        for(int i = 0; i < recipeHeight; ++i) {
            for(int j = 0; j < pattern.get(i).length(); ++j) {
                char s = pattern.get(i).charAt(j);
                if (s == ' ') {
                    continue;
                }

                IRecipeInputItem iRecipeInputItem = key.get(s);
                if (iRecipeInputItem == null) {
                    throw new IllegalArgumentException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                recipeInputItemPackList.set(j + recipeWidth * i, iRecipeInputItem);

                if (inheritanceKey.contains(s)) {
                    inheritanceList.set(j + recipeWidth * i, true);
                }
            }
        }

        if (!set.isEmpty()) {
            throw new IllegalArgumentException("Key defines symbols that aren't used in pattern: " + set);
        }

        this.ingredientNonNullList = NonNullList.of(Ingredient.EMPTY, recipeInputItemPackList.stream().map(IRecipeInputItem::toIngredient).toArray(Ingredient[]::new));

    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return getResult();
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        Map<Holder<Enchantment>, Integer> map = new HashMap<>();
        for(int i = 0; i <= input.width() - this.getRecipeWidth(); ++i) {
            for(int j = 0; j <= input.height() - this.getRecipeHeight(); ++j) {
                if (this.checkMatch(input, i, j, map)) {
                    return true;
                }
                map.clear();
            }
        }
        return false;
    }

    protected boolean checkMatch(CraftingInput craftingInventory, int width, int height, Map<Holder<Enchantment>, Integer> map) {

        for(int i = 0; i < craftingInventory.width(); ++i) {
            for(int j = 0; j < craftingInventory.height(); ++j) {
                int k = i - width;
                int l = j - height;

                int id = k + l * this.getRecipeWidth();
                if (k < 0 || l < 0 || k >= this.getRecipeWidth() || l >= this.getRecipeHeight()) {
                    return false;
                }

                ItemStack itemStack = craftingInventory.getItem(i + j * craftingInventory.width());
                if (!recipeInputItemPackList.get(id).test(itemStack)) {
                    return false;
                }

                if (inheritanceList.get(id)) {
                    if (map == null) {
                        map = new HashMap<>();
                    }

                    ItemEnchantments tagEnchantments = itemStack.getTagEnchantments();
                    Map<Holder<Enchantment>, Integer> finalMap = map;
                    tagEnchantments.entrySet().forEach(holderEntry -> finalMap.put(holderEntry.getKey(), Math.max(finalMap.computeIfAbsent(holderEntry.getKey(), m -> 1), holderEntry.getIntValue())));

                }
            }
        }

        return map.isEmpty() || map.size() == 1;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider registries) {
        ItemStack target = getResult().copy();

        Map<Holder<Enchantment>, Integer> map = new HashMap<>();

        for(int i = 0; i <= input.width() - this.getRecipeWidth(); ++i) {
            for(int j = 0; j <= input.height() - this.getRecipeHeight(); ++j) {
                if (!checkMatch(input, i, j, map)) {
                    map.clear();
                }

                ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
                map.forEach(mutable::set);
                EnchantmentHelper.setEnchantments(target, mutable.toImmutable());
            }
        }
        return target;

    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SbRecipeSerializer.PROUD_SOUL_SHAPED_RECIPE.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredientNonNullList;
    }

    @Override
    public @NotNull String getGroup() {
        return getResult().getDescriptionId();
    }

}
