package mod.slashblade.reforged.content.recipe;

import lombok.Getter;
import mod.slashblade.reforged.content.data.SaveField;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbRecipeInputItemSerializer;
import mod.slashblade.reforged.content.init.SbRegistrys;
import mod.slashblade.reforged.content.item.SlashBladeItem;
import mod.slashblade.reforged.utils.helper.SlashBladeHelper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface IRecipeInputItem extends Predicate<ItemStack> {
    Ingredient toIngredient();

    IRecipeInputItemSerializer<?> getSerializer();

    IRecipeInputItem EMPTY = new IRecipeInputItem() {
        @Override
        public Ingredient toIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public boolean test(ItemStack itemStack) {
            return itemStack.isEmpty();
        }

        @Override
        public IRecipeInputItemSerializer<?> getSerializer() {
            return SbRecipeInputItemSerializer.EMPTY_ITEM.get();
        }
    };

    class IngredientRecipeInputItem implements IRecipeInputItem {
        @Getter
        protected Ingredient ingredient;

        public IngredientRecipeInputItem(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        public IngredientRecipeInputItem(ItemStack itemStack) {
            this.ingredient = Ingredient.of(itemStack);
        }

        public IngredientRecipeInputItem(TagKey<Item> tag) {
            this.ingredient = Ingredient.of(tag);
        }

        public IngredientRecipeInputItem(Item item) {
            this(new ItemStack(item));
        }

        @Override
        public Ingredient toIngredient() {
            return ingredient;
        }

        @Override
        public boolean test(ItemStack itemStack) {
            return ingredient.test(itemStack);
        }

        @Override
        public IRecipeInputItemSerializer<?> getSerializer() {
            return SbRecipeInputItemSerializer.INGREDIENT_RECIPE_INPUT_ITEM.get();
        }
    }


    class SlashBladeRecipeInputItem implements IRecipeInputItem {

        @Getter
        ItemStack itemStack;

        SlashBladeLogic slashBladeLogic;

        public SlashBladeRecipeInputItem(ItemStack itemStack) {
            this.itemStack = itemStack;
            this.slashBladeLogic = itemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        }

        @Override
        public Ingredient toIngredient() {
            return Ingredient.of(itemStack);
        }

        @Override
        public IRecipeInputItemSerializer<?> getSerializer() {
            return SbRecipeInputItemSerializer.SLASH_BLADE_RECIPE_INPUT_ITEM.get();
        }

        @Override
        public boolean test(ItemStack itemStack) {

            SlashBladeLogic input = itemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC.get());
            if (input == null) {
                return false;
            }

            return SlashBladeHelper.meetConditions(itemStack, input, this.itemStack, slashBladeLogic);

        }
    }

}
