package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.loot.LootItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class SbLootPoolEntries {

    public static final DeferredRegister<LootPoolEntryType> LOOT_POOL_ENTRY_TYPE = DeferredRegister.create(Registries.LOOT_POOL_ENTRY_TYPE, SlashbladeMod.MODID);

    public static final Supplier<LootPoolEntryType> LOOT_ITEM_STACK = LOOT_POOL_ENTRY_TYPE.register("loot_item_stack", () -> new LootPoolEntryType(LootItemStack.CODEC));

    public static void register(IEventBus bus) {
        LOOT_POOL_ENTRY_TYPE.register(bus);
    }
}
