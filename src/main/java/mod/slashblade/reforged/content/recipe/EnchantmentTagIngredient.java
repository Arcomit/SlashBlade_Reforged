package mod.slashblade.reforged.content.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.world.item.crafting.Ingredient;

@Data
@AllArgsConstructor
public class EnchantmentTagIngredient {
    public static final EnchantmentTagIngredient EMPTY = new EnchantmentTagIngredient(new IRecipeInputItem.IngredientRecipeInputItem(Ingredient.EMPTY), false);

    IRecipeInputItem ingredient;

    /***
     * 参与附魔的继承
     */
    boolean inheritance;
}
