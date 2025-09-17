package mod.slashblade.reforged.utils.constant;

import com.google.gson.JsonElement;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeMaterial;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.init.SbRegistrys;
import mod.slashblade.reforged.content.loot.LootItemStack;
import mod.slashblade.reforged.content.recipe.*;
import mod.slashblade.reforged.content.register.SpecialAttack;
import mod.slashblade.reforged.content.register.SpecialEffect;
import mod.slashblade.reforged.core.animation.utils.GsonUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public static final Codec<Color> COLOR = Codec.INT.comapFlatMap(
            i -> DataResult.success(new Color(i, true)),
            Color::getRGB
    );

    public static final Codec<SlashBladeLogic> SLASH_BLADE_LOGIC = ofJson(SlashBladeLogic.class);
    public static final Codec<SlashBladeStyle> SLASH_BLADE_STYLE = ofJson(SlashBladeStyle.class);
    public static final Codec<SlashBladeMaterial> SLASH_BLADE_MATERIAL = ofJson(SlashBladeMaterial.class);

    public static final Codec<IRecipeInputItem> RECIPE_INPUT_ITEM_CODEC = SbRegistrys.RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.byNameCodec()
            .dispatch(IRecipeInputItem::getSerializer, i -> i.codec().fieldOf("value"));

    public static final Codec<IRecipeInputItem.IngredientRecipeInputItem> INGREDIENT_RECIPE_INPUT_ITEM = Ingredient.CODEC
            .comapFlatMap(i -> DataResult.success(new IRecipeInputItem.IngredientRecipeInputItem(i)), IRecipeInputItem.IngredientRecipeInputItem::getIngredient);
    public static final Codec<IRecipeInputItem.SlashBladeRecipeInputItem> SLASH_BLADE_RECIPE_INPUT_ITEM = ItemStack.CODEC
            .comapFlatMap(i -> DataResult.success(new IRecipeInputItem.SlashBladeRecipeInputItem(i)), IRecipeInputItem.SlashBladeRecipeInputItem::getItemStack);
    public static final Codec<IRecipeInputItem.EnchantmentItemRecipeInputItem> ENCHANTMENT_ITEM_RECIPE_INPUT_ITEM = RecordCodecBuilder.create(
            i -> i.group(
                            RECIPE_INPUT_ITEM_CODEC.fieldOf("base").forGetter(IRecipeInputItem.EnchantmentItemRecipeInputItem::getBase),
                            ItemEnchantments.CODEC.fieldOf("enchantment").forGetter(IRecipeInputItem.EnchantmentItemRecipeInputItem::getEnchantments)
                    )
                    .apply(i, IRecipeInputItem.EnchantmentItemRecipeInputItem::new)
    );

    public static final Codec<EnchantmentTagIngredient> ENCHANTMENT_TAG_INGREDIENT = RecordCodecBuilder.create(
            i -> i.group(
                            RECIPE_INPUT_ITEM_CODEC.fieldOf("ingredient").forGetter(EnchantmentTagIngredient::getIngredient),
                            Codec.BOOL.fieldOf("inheritance").forGetter(EnchantmentTagIngredient::isInheritance)
                    )
                    .apply(i, EnchantmentTagIngredient::new)
    );

    public static final Codec<SpecialAttack> SA = SbRegistrys.SPECIAL_ATTACK_REGISTRY.byNameCodec();
    public static final Codec<SpecialEffect> SE = SbRegistrys.SPECIAL_EFFECT_REGISTRY.byNameCodec();

    public static final MapCodec<ProudSoulShapelessRecipe> PROUD_SOUL_SHAPELESS_RECIPE = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Codec.STRING.optionalFieldOf("group", "").forGetter(ProudSoulShapelessRecipe::getGroup),
                            CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CustomRecipe::category),
                            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(ProudSoulShapelessRecipe::getResult),
                            ENCHANTMENT_TAG_INGREDIENT
                                    .listOf()
                                    .fieldOf("ingredients")
                                    .flatXmap(
                                            p_301021_ -> {
                                                EnchantmentTagIngredient[] ingredient = p_301021_.toArray(EnchantmentTagIngredient[]::new); // Neo skip the empty check and immediately create the array.
                                                if (ingredient.length == 0) {
                                                    return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                } else {
                                                    return ingredient.length > ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()
                                                            ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()))
                                                            : DataResult.success(NonNullList.of(EnchantmentTagIngredient.EMPTY, ingredient));
                                                }
                                            },
                                            DataResult::success
                                    )
                                    .forGetter(ProudSoulShapelessRecipe::getTagIngredients)
                    )
                    .apply(i, ProudSoulShapelessRecipe::new)
    );

    public static final MapCodec<SlashBladeRecipe> SLASH_BLADE_RECIPE = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.STRING.listOf().optionalFieldOf("pattern", List.of()).forGetter(SlashBladeRecipe::getPattern),
                            Codec.unboundedMap(CHARACTER_CODEC, RECIPE_INPUT_ITEM_CODEC).optionalFieldOf("key", Map.of()).forGetter(SlashBladeRecipe::getKey),
                            CHARACTER_CODEC.optionalFieldOf("mainSlashBladeKey", ' ').forGetter(SlashBladeRecipe::getMainSlashBladeKey),
                            ItemStack.CODEC.optionalFieldOf("result", ItemStack.EMPTY).forGetter(SlashBladeRecipe::getResult)
                    )
                    .apply(instance, SlashBladeRecipe::new)
    );
    public static final MapCodec<ProudSoulShapedRecipe> PROUD_SOUL_SHAPED_RECIPE = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.STRING.listOf().optionalFieldOf("pattern", List.of()).forGetter(ProudSoulShapedRecipe::getPattern),
                            Codec.unboundedMap(CHARACTER_CODEC, RECIPE_INPUT_ITEM_CODEC).optionalFieldOf("key", Map.of()).forGetter(ProudSoulShapedRecipe::getKey),
                            CHARACTER_CODEC.listOf().optionalFieldOf("mainSlashBladeKey", new ArrayList<>()).forGetter(ProudSoulShapedRecipe::getInheritanceKey),
                            ItemStack.CODEC.optionalFieldOf("result", ItemStack.EMPTY).forGetter(ProudSoulShapedRecipe::getResult)
                    )
                    .apply(instance, ProudSoulShapedRecipe::new)
    );

    public static final MapCodec<LootItemStack> LOOT_ITEM_STACK = LootItemStack.CODEC;

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
