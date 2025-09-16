package mod.slashblade.reforged.content.recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import mod.slashblade.reforged.content.init.SbRecipeSerializer;
import mod.slashblade.reforged.utils.tuple.Tuple2;
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

@Getter
public class ProudSoulShapelessRecipe extends CustomRecipe {

    final String group;
    final ItemStack result;
    final NonNullList<EnchantmentTagIngredient> tagIngredients;
    final NonNullList<Ingredient> ingredients;

    public ProudSoulShapelessRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<EnchantmentTagIngredient> ingredients) {
        super(category);
        this.group = group;
        this.result = result;
        this.tagIngredients = ingredients;
        this.ingredients = NonNullList.of(Ingredient.EMPTY, ingredients.stream().map(t -> t.getIngredient().toIngredient()).toArray(Ingredient[]::new));
    }

    public Tuple2<Set<Integer>, Map<Holder<Enchantment>, Integer>> check(@NotNull CraftingInput input) {
        Set<Integer> matchIds = new HashSet<>();

        Map<Holder<Enchantment>, Integer> map = new HashMap<>();

        for(EnchantmentTagIngredient model : tagIngredients) {
            for(int j = 0; j < input.size(); j++) {
                if (matchIds.contains(j)) {
                    continue;
                }

                ItemStack inputItem = input.getItem(j);
                if (!model.getIngredient().test(inputItem)) {
                    continue;
                }

                if (model.isInheritance()) {
                    ItemEnchantments tagEnchantments = inputItem.getTagEnchantments();
                    tagEnchantments.entrySet().forEach(holderEntry -> map.put(holderEntry.getKey(), Math.max(map.computeIfAbsent(holderEntry.getKey(), k -> 1), holderEntry.getIntValue())));
                }

                matchIds.add(j);
                break;
            }
        }

        return new Tuple2<>(matchIds, map);
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        if (input.items().stream().filter(i -> !i.isEmpty()).count() != this.ingredients.size()) {
            return false;
        }

        Tuple2<Set<Integer>, Map<Holder<Enchantment>, Integer>> check = check(input);
        Set<Integer> matchIds = check.getA();
        Map<Holder<Enchantment>, Integer> map = check.getB();

        if (matchIds.size() != tagIngredients.size()) {
            return false;
        }

        return map.isEmpty() || map.size() == 1;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider registries) {
        Tuple2<Set<Integer>, Map<Holder<Enchantment>, Integer>> check = check(input);
        Map<Holder<Enchantment>, Integer> map = check.getB();

        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        map.forEach(mutable::set);

        ItemStack copy = result.copy();
        EnchantmentHelper.setEnchantments(copy, mutable.toImmutable());
        return copy;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.tagIngredients.size();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SbRecipeSerializer.PROUD_SOUL_SHAPELESS_RECIPE.get();
    }


}