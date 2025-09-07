package mod.slashblade.reforged.content.recipe;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface IRecipeInputItemSerializer<R extends IRecipeInputItem> {
    Codec<R> codec();

    StreamCodec<RegistryFriendlyByteBuf, R> streamCodec();


    static <R extends IRecipeInputItem> IRecipeInputItemSerializer<R> of(Codec<R> codec, StreamCodec<RegistryFriendlyByteBuf, R> streamCodec) {
        return new RecipeInputItemSerializer<>(codec, streamCodec);
    }

    @AllArgsConstructor
    class RecipeInputItemSerializer<R extends IRecipeInputItem> implements IRecipeInputItemSerializer<R> {

        Codec<R> codec;
        StreamCodec<RegistryFriendlyByteBuf, R> streamCodec;

        @Override
        public Codec<R> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, R> streamCodec() {
            return streamCodec;
        }
    }
}
