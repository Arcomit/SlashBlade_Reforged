package mod.slashblade.reforged.generated.group;

import com.mojang.datafixers.types.Func;
import mod.slashblade.reforged.generated.Generator;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemStackBuilder {

    ItemStack itemStack;

    public ItemStackBuilder(Item item) {
        this.itemStack = new ItemStack(item);
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    public <D> ItemStackBuilder set(DataComponentType<D> dataComponentType, D d) {
        this.itemStack.set(dataComponentType, d);
        return this;
    }

    public <D> ItemStackBuilder set(DataComponentType<D> dataComponentType, Function<D, D> consumer, Supplier<D> def) {
        D d = itemStack.get(dataComponentType);
        if (d == null) {
            d = def.get();
        }
        d = consumer.apply(d);
        this.itemStack.set(dataComponentType, d);
        return this;
    }

    public ItemStackBuilder setDamageValue(int v) {
        itemStack.setDamageValue(v);
        return this;
    }

    public ItemStackBuilder setEnchantment(ItemEnchantments itemEnchantments) {
        EnchantmentHelper.setEnchantments(itemStack, itemEnchantments);
        return this;
    }

    public ItemStackBuilder setEnchantment(ItemEnchantments.Mutable itemEnchantments) {
        EnchantmentHelper.setEnchantments(itemStack, itemEnchantments.toImmutable());
        return this;
    }


    public ItemStackBuilder setEnchantment(Map<ResourceKey<Enchantment>, Integer> itemEnchantments) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = Generator.grtProvider().lookup(Registries.ENCHANTMENT).orElseThrow();
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        itemEnchantments.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                e -> registryLookup.getOrThrow(e.getKey()),
                                Map.Entry::getValue
                        )
                )
                .forEach(mutable::set);
        EnchantmentHelper.setEnchantments(itemStack, mutable.toImmutable());
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
