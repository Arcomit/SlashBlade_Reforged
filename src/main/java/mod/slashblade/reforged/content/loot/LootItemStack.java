package mod.slashblade.reforged.content.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.slashblade.reforged.content.init.SbLootPoolEntries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class LootItemStack extends LootPoolSingletonContainer {
    public static final MapCodec<LootItemStack> CODEC = RecordCodecBuilder.mapCodec(
            s -> s.group(ItemStack.CODEC.fieldOf("item_stack").forGetter(p_298016_ -> p_298016_.itemStack))
                    .and(singletonFields(s))
                    .apply(s, LootItemStack::new)
    );


    ItemStack itemStack;

    public LootItemStack(ItemStack itemStack, int weight, int quality, List<LootItemCondition> conditions, List<LootItemFunction> functions) {
        super(weight, quality, conditions, functions);
        this.itemStack = itemStack;
    }

    @Override
    protected void createItemStack(@NotNull Consumer<ItemStack> stackConsumer, @NotNull LootContext lootContext) {
        stackConsumer.accept(itemStack);
    }

    @Override
    public @NotNull LootPoolEntryType getType() {
        return SbLootPoolEntries.LOOT_ITEM_STACK.get();
    }
    public static LootPoolSingletonContainer.Builder<?> lootTableItem(ItemStack item) {
        return simpleBuilder(
                (w, h, c, l) -> new LootItemStack(item, w, h, c, l)
        );
    }

}
