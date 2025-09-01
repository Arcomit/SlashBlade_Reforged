package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.item.SlashBladeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 16:36
 * @Description: 集中管理所有模组物品的注册（且能够从中获取调用）
 */
public class SbItems {

    public static final DeferredRegister.Items ITEMS       = DeferredRegister.createItems(SlashbladeMod.MODID);

    public static final Supplier<SwordItem>    SLASH_BLADE = ITEMS.register(
            "slash_blade",
            () -> new SlashBladeItem(
                    Tiers.IRON,
                    new Item.Properties().attributes(
                            SwordItem.createAttributes(
                                    Tiers.IRON,
                                    3,
                                    0.0f
                            )
                    )
            )
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
