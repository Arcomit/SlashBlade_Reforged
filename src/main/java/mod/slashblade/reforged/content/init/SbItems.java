package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.item.ProudSoulItem;
import mod.slashblade.reforged.content.item.SlashBladeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 16:36
 * @Description: 集中管理所有模组物品的注册（且能够从中获取调用）
 */
public class SbItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SlashbladeMod.MODID);

    public static final DeferredHolder<Item, SwordItem> SLASH_BLADE = ITEMS.register(
            "slash_blade",
            () -> new SlashBladeItem(
                    Tiers.IRON,
                    new Item.Properties()
                            .fireResistant()
            )
    );

    public static final Supplier<Item> PROUD_SOUL = ITEMS.register("proud_soul", () -> new ProudSoulItem(new Item.Properties()));
    public static final Supplier<Item> PROUD_SOUL_INGOT = ITEMS.register("proud_soul_ingot", () -> new ProudSoulItem(new Item.Properties()));
    public static final Supplier<Item> PROUD_SOUL_TINY = ITEMS.register("proud_soul_tiny", () -> new ProudSoulItem(new Item.Properties()));
    public static final Supplier<Item> PROUD_SOUL_SPHERE = ITEMS.register("proud_soul_sphere", () -> new ProudSoulItem(new Item.Properties()));
    public static final Supplier<Item> PROUD_SOUL_CRYSTAL = ITEMS.register("proud_soul_crystal", () -> new ProudSoulItem(new Item.Properties()));
    public static final Supplier<Item> PROUD_SOUL_TRAPEZOHEDRON = ITEMS.register("proud_soul_trapezohedron", () -> new ProudSoulItem(new Item.Properties()));


    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
