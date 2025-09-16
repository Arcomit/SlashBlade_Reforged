package mod.slashblade.reforged.content.init;

import com.mojang.serialization.Codec;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.recipe.IRecipeInputItem;
import mod.slashblade.reforged.content.recipe.IRecipeInputItemSerializer;
import mod.slashblade.reforged.utils.constant.ByteBufCodecConstants;
import mod.slashblade.reforged.utils.constant.CodecConstants;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SbRecipeInputItemSerializer {
    public static final DeferredRegister<IRecipeInputItemSerializer<?>> RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY = DeferredRegister.create(SbRegistrys.RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY, SlashbladeMod.MODID);

    public static final Supplier<IRecipeInputItemSerializer<IRecipeInputItem>> EMPTY_ITEM = RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.register(
            "empty_item",
            () -> IRecipeInputItemSerializer.of(Codec.unit(IRecipeInputItem.EMPTY), StreamCodec.unit(IRecipeInputItem.EMPTY))
    );

    public static final Supplier<IRecipeInputItemSerializer<IRecipeInputItem.IngredientRecipeInputItem>> INGREDIENT_RECIPE_INPUT_ITEM = RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.register(
            "ingredient",
            () -> IRecipeInputItemSerializer.of(CodecConstants.INGREDIENT_RECIPE_INPUT_ITEM, ByteBufCodecConstants.INGREDIENT_RECIPE_INPUT_ITEM)
    );

    public static final Supplier<IRecipeInputItemSerializer<IRecipeInputItem.SlashBladeRecipeInputItem>> SLASH_BLADE_RECIPE_INPUT_ITEM = RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.register(
            "slash_blade",
            () -> IRecipeInputItemSerializer.of(CodecConstants.SLASH_BLADE_RECIPE_INPUT_ITEM, ByteBufCodecConstants.SLASH_BLADE_RECIPE_INPUT_ITEM)
    );

    public static final Supplier<IRecipeInputItemSerializer<IRecipeInputItem.EnchantmentItemRecipeInputItem>> ENCHANTMENT_ITEM_RECIPE_INPUT_ITEM = RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.register(
            "enchantment_item",
            () -> IRecipeInputItemSerializer.of(CodecConstants.ENCHANTMENT_ITEM_RECIPE_INPUT_ITEM, ByteBufCodecConstants.ENCHANTMENT_ITEM_RECIPE_INPUT_ITEM)
    );


    public static void register(IEventBus bus) {
        RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.register(bus);
    }

}
