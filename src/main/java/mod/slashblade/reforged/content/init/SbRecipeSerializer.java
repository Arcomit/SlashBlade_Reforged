package mod.slashblade.reforged.content.init;

import com.mojang.serialization.MapCodec;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.recipe.IRecipeInputItemSerializer;
import mod.slashblade.reforged.content.recipe.SlashBladeRecipe;
import mod.slashblade.reforged.utils.constant.ByteBufCodecConstants;
import mod.slashblade.reforged.utils.constant.CodecConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
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

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZER_REGISTRY.register(bus);
    }

}
