package mod.slashblade.reforged.content.recipe;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbRecipeSerializer;
import mod.slashblade.reforged.content.init.SbRegistrys;
import mod.slashblade.reforged.utils.helper.SlashBladeHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SlashBladeRecipe implements CraftingRecipe {


    @Getter
    final SlashBladeRecipeData slashBladeRecipeData;

    @Getter
    int recipeWidth;
    @Getter
    int recipeHeight;

    int mainKeyId = -1;

    NonNullList<IRecipeInputItem> recipeInputItemPackList;
    NonNullList<Ingredient> ingredientNonNullList;

    public SlashBladeRecipe(
            List<String> pattern,
            Map<Character, IRecipeInputItem> key,
            char mainSlashBladeKey,
            ItemStack result
    ) {
        this(new SlashBladeRecipeData(pattern, key, mainSlashBladeKey, result));
    }

    public SlashBladeRecipe(SlashBladeRecipeData slashBladeRecipeData) {
        this.slashBladeRecipeData = slashBladeRecipeData;

        recipeHeight = slashBladeRecipeData.getPattern().size();

        if (recipeHeight == 0) {
            throw new IllegalArgumentException("recipe input height is 0");
        }

        recipeWidth = slashBladeRecipeData.getPattern().stream().max(Comparator.comparingInt(String::length)).get().length();

        if (recipeWidth == 0) {
            throw new IllegalArgumentException("recipe input width is 0");
        }

        recipeInputItemPackList = NonNullList.withSize(recipeWidth * recipeHeight, IRecipeInputItem.EMPTY);

        Set<Character> set = Sets.newHashSet(slashBladeRecipeData.getKey().keySet());

        if (set.contains(' ')) {
            throw new IllegalArgumentException("recipe input key cannot be used ' '");
        }

        for(int i = 0; i < recipeHeight; ++i) {
            for(int j = 0; j < slashBladeRecipeData.pattern.get(i).length(); ++j) {
                char s = slashBladeRecipeData.pattern.get(i).charAt(j);
                if (s == ' ') {
                    continue;
                }

                if (s == slashBladeRecipeData.getMainSlashBladeKey()) {
                    if (mainKeyId >= 0) {
                        throw new IllegalArgumentException("recipe input key used multiple times");
                    }
                    mainKeyId = j + recipeWidth * i;
                }

                IRecipeInputItem iRecipeInputItem = slashBladeRecipeData.getKey().get(s);
                if (iRecipeInputItem == null) {
                    throw new IllegalArgumentException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                recipeInputItemPackList.set(j + recipeWidth * i, iRecipeInputItem);
            }
        }

        if (!set.isEmpty()) {
            throw new IllegalArgumentException("Key defines symbols that aren't used in pattern: " + set);
        }

        // 如果没有mainKeyId表明不需要继承
        //if (mainKeyId < 0) {
        //    throw new IllegalArgumentException("recipe input key not found");
        //}


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
        return slashBladeRecipeData.getResult();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SbRecipeSerializer.SLASH_BLADE_RECIPE.get();
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        for(int i = 0; i <= input.width() - this.getRecipeWidth(); ++i) {
            for(int j = 0; j <= input.height() - this.getRecipeHeight(); ++j) {
                if (this.checkMatch(input, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean checkMatch(CraftingInput craftingInventory, int width, int height) {
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
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider registries) {
        ItemStack target = slashBladeRecipeData.getResult();
        SlashBladeLogic targetLogic = target.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (targetLogic == null) {
            return target;
        }

        if (mainKeyId < 0) {
            return target;
        }

        for(int i = 0; i <= input.width() - this.getRecipeWidth(); ++i) {
            for(int j = 0; j <= input.height() - this.getRecipeHeight(); ++j) {
                ItemStack inputItem = this.getResultSlashBladePack(input, i, j);
                if (inputItem == null) {
                    continue;
                }

                SlashBladeLogic inputLogic = inputItem.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
                if (inputLogic == null) {
                    continue;
                }

                return SlashBladeHelper.transformation(inputItem, inputLogic, target, targetLogic);
            }
        }
        return target;

    }

    @Nullable
    protected ItemStack getResultSlashBladePack(CraftingInput craftingInventory, int width, int height) {
        for(int i = 0; i < craftingInventory.width(); ++i) {
            for(int j = 0; j < craftingInventory.height(); ++j) {
                int k = i - width;
                int l = j - height;
                //int id = this.getRecipeWidth() - k - 1 + l * this.getRecipeWidth() /*: k + l * this.getRecipeWidth()*/;
                int id = k + l * this.getRecipeWidth();
                if (k < 0 || l < 0 || k >= this.getRecipeWidth() || l >= this.getRecipeHeight()) {
                    return null;
                }
                if (id == mainKeyId) {
                    return craftingInventory.getItem(i + j * craftingInventory.width());
                }
            }
        }
        return null;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredientNonNullList;
    }

    @Data
    @AllArgsConstructor
    public static class SlashBladeRecipeData {
        List<String> pattern;
        Map<Character, IRecipeInputItem> key;
        char mainSlashBladeKey;
        ItemStack result;

    }
}
