package mod.slashblade.reforged.content.init;

import com.mojang.serialization.MapCodec;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.recipe.ProudSoulShapedRecipe;
import mod.slashblade.reforged.content.recipe.ProudSoulShapelessRecipe;
import mod.slashblade.reforged.content.recipe.ProudSoulSmeltingRecipe;
import mod.slashblade.reforged.content.recipe.SlashBladeRecipe;
import mod.slashblade.reforged.utils.constant.ByteBufCodecConstants;
import mod.slashblade.reforged.utils.constant.CodecConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SbRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTRY = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, SlashbladeMod.MODID);

    public static final Supplier<RecipeSerializer<SlashBladeRecipe>> SLASH_BLADE_RECIPE = RECIPE_SERIALIZER_REGISTRY.register(
            "slashblade_recipe",
            () -> new RecipeSerializer<>() {
                @Override
                public @NotNull MapCodec<SlashBladeRecipe> codec() {
                    return CodecConstants.SLASH_BLADE_RECIPE;
                }

                @Override
                public @NotNull StreamCodec<RegistryFriendlyByteBuf, SlashBladeRecipe> streamCodec() {
                    return ByteBufCodecConstants.SLASH_BLADE_RECIPE;
                }
            }
    );

    public static final Supplier<RecipeSerializer<ProudSoulShapelessRecipe>> PROUD_SOUL_SHAPELESS_RECIPE = RECIPE_SERIALIZER_REGISTRY.register(
            "proud_soul_shapeless_recipe",
            () -> new RecipeSerializer<>() {
                @Override
                public @NotNull MapCodec<ProudSoulShapelessRecipe> codec() {
                    return CodecConstants.PROUD_SOUL_SHAPELESS_RECIPE;
                }

                @Override
                public @NotNull StreamCodec<RegistryFriendlyByteBuf, ProudSoulShapelessRecipe> streamCodec() {
                    return ByteBufCodecConstants.PROUD_SOUL_SHAPELESS_RECIPE;
                }
            }
    );

    public static final Supplier<RecipeSerializer<ProudSoulShapedRecipe>> PROUD_SOUL_SHAPED_RECIPE = RECIPE_SERIALIZER_REGISTRY.register(
            "proud_soul_shaped_recipe",
            () -> new RecipeSerializer<>() {
                @Override
                public @NotNull MapCodec<ProudSoulShapedRecipe> codec() {
                    return CodecConstants.PROUD_SOUL_SHAPED_RECIPE;
                }

                @Override
                public @NotNull StreamCodec<RegistryFriendlyByteBuf, ProudSoulShapedRecipe> streamCodec() {
                    return ByteBufCodecConstants.PROUD_SOUL_SHAPED_RECIPE;
                }
            }
    );

    public static final Supplier<RecipeSerializer<ProudSoulSmeltingRecipe>> PROUD_SOUL_SMELTING_RECIPE = RECIPE_SERIALIZER_REGISTRY.register(
            "proud_soul_smelting_recipe",
            () -> new SimpleCookingSerializer<>(ProudSoulSmeltingRecipe::new, 200)
    );

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZER_REGISTRY.register(bus);
    }

}
