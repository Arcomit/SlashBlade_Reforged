package mod.slashblade.reforged.utils.constant;

import com.google.gson.JsonElement;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.init.SbRegistrys;
import mod.slashblade.reforged.content.recipe.IRecipeInputItem;
import mod.slashblade.reforged.content.recipe.IRecipeInputItemSerializer;
import mod.slashblade.reforged.content.recipe.SlashBladeRecipe;
import mod.slashblade.reforged.core.animation.utils.GsonUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CodecConstants {

    public static final Codec<Character> CHARACTER_CODEC = Codec.STRING.comapFlatMap(
            s -> s.length() == 1
                    ? DataResult.success(s.charAt(0))
                    : DataResult.error(() -> "Expected single character, got: " + s),
            String::valueOf
    );

    public static final Codec<SlashBladeLogic> SLASH_BLADE_LOGIC = ofJson(SlashBladeLogic.class);
    public static final Codec<SlashBladeStyle> SLASH_BLADE_STYLE = ofJson(SlashBladeStyle.class);

    public static final Codec<IRecipeInputItem.IngredientRecipeInputItem> INGREDIENT_RECIPE_INPUT_ITEM = Ingredient.CODEC
            .comapFlatMap(i -> DataResult.success(new IRecipeInputItem.IngredientRecipeInputItem(i)), IRecipeInputItem.IngredientRecipeInputItem::getIngredient);
    public static final Codec<IRecipeInputItem.SlashBladeRecipeInputItem> SLASH_BLADE_RECIPE_INPUT_ITEM = ItemStack.CODEC
            .comapFlatMap(i -> DataResult.success(new IRecipeInputItem.SlashBladeRecipeInputItem(i)), IRecipeInputItem.SlashBladeRecipeInputItem::getItemStack);

    public static final Codec<IRecipeInputItem> RECIPE_INPUT_ITEM_CODEC = SbRegistrys.RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.byNameCodec()
            .dispatch(IRecipeInputItem::getSerializer, i -> i.codec().fieldOf("value"));

    public static final MapCodec<SlashBladeRecipe.SlashBladeRecipeData> SLASH_BLADE_RECIPE_DATA = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.STRING.listOf().optionalFieldOf("pattern", List.of()).forGetter(SlashBladeRecipe.SlashBladeRecipeData::getPattern),
                            Codec.unboundedMap(CHARACTER_CODEC, RECIPE_INPUT_ITEM_CODEC).optionalFieldOf("key", Map.of()).forGetter(SlashBladeRecipe.SlashBladeRecipeData::getKey),
                            CHARACTER_CODEC.optionalFieldOf("mainSlashBladeKey", 'M').forGetter(SlashBladeRecipe.SlashBladeRecipeData::getMainSlashBladeKey),
                            ItemStack.CODEC.optionalFieldOf("result", ItemStack.EMPTY).forGetter(SlashBladeRecipe.SlashBladeRecipeData::getResult)
                    )
                    .apply(instance, SlashBladeRecipe.SlashBladeRecipeData::new)
    );
    public static final MapCodec<SlashBladeRecipe> SLASH_BLADE_RECIPE = conversionMapCodec(SLASH_BLADE_RECIPE_DATA, SlashBladeRecipe::new, SlashBladeRecipe::getSlashBladeRecipeData);


    public static <D> Codec<D> ofJson(Type type) {
        return new PrimitiveCodec<D>() {
            @Override
            public <T> DataResult<D> read(DynamicOps<T> ops, T input) {
                JsonElement jsonElement = ops.convertTo(JsonOps.INSTANCE, input);
                return DataResult.success(GsonUtil.SERVER_GSON.fromJson(jsonElement, type));
            }

            @Override
            public <T> T write(DynamicOps<T> ops, D value) {
                JsonElement jsonElement = GsonUtil.SERVER_GSON.toJsonTree(value);
                return JsonOps.INSTANCE.convertTo(ops, jsonElement);
            }
        };
    }


    public static <S, N> MapCodec<N> conversionMapCodec(MapCodec<S> source, Function<S, N> converter, Function<N, S> reverseConverter) {
        return MapCodec.of(
                source.comap(reverseConverter),
                source.map(converter)
        );

    }

}
