package mod.slashblade.reforged.generated.group;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemStackBuilder {

    ItemStack itemStack;

    public ItemStackBuilder(Item item) {
        this.itemStack = new ItemStack(item);
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public <D> ItemStackBuilder set(Supplier<DataComponentType<D>> dataComponentType, D d) {
        this.itemStack.set(dataComponentType, d);
        return this;
    }


    public <D> ItemStackBuilder set(DataComponentType<D> dataComponentType, Consumer<D> consumer, Supplier<D> def) {
        D d = itemStack.get(dataComponentType);
        if (d == null) {
            d = def.get();
        }
        consumer.accept(d);
        this.itemStack.set(dataComponentType, d);
        return this;
    }


    public ItemStack build() {
        return itemStack;
    }
}
