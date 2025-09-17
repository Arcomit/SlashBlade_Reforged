package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.SlashBladeMaterial;
import mod.slashblade.reforged.content.item.BladeStandItem;
import mod.slashblade.reforged.content.item.ProudSoulItem;
import mod.slashblade.reforged.content.item.SlashBladeItem;
import mod.slashblade.reforged.content.register.SpecialAttack;
import mod.slashblade.reforged.content.register.SpecialEffect;
import mod.slashblade.reforged.generated.client.language.LanguageItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
            () -> new SlashBladeItem(Tiers.IRON, new Item.Properties())
    );

    public static final Supplier<Item> PROUD_SOUL_TINY = ITEMS.register(
            "proud_soul_tiny",
            () -> new ProudSoulItem(new Item.Properties(), new SlashBladeMaterial(32, 16, 1))
    );
    public static final Supplier<Item> PROUD_SOUL = ITEMS.register(
            "proud_soul",
            () -> new ProudSoulItem(new Item.Properties(), new SlashBladeMaterial(128, 64, 2))
    );
    public static final Supplier<Item> PROUD_SOUL_INGOT = ITEMS.register(
            "proud_soul_ingot",
            () -> new ProudSoulItem(new Item.Properties(), new SlashBladeMaterial(512, 512, 8))
    );
    public static final Supplier<Item> PROUD_SOUL_SPHERE = ITEMS.register(
            "proud_soul_sphere",
            () -> new ProudSoulItem(new Item.Properties(), new SlashBladeMaterial(1024, 256, 10))
    );
    public static final Supplier<Item> PROUD_SOUL_CRYSTAL = ITEMS.register(
            "proud_soul_crystal",
            () -> new ProudSoulItem(new Item.Properties(), new SlashBladeMaterial(2048, 1024, 16)) {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                    SpecialAttack sa = stack.get(SbDataComponentTypes.SA);
                    if (sa != null) {
                        tooltipComponents.add(Component.translatable(LanguageItems.SA.getKey(), Component.translatable(sa.getDescriptionId())));
                        tooltipComponents.add(Component.translatable(sa.getDocDescriptionId()));
                        tooltipComponents.add(Component.empty());
                    }
                }
            }
    );
    public static final Supplier<Item> PROUD_SOUL_TRAPEZOHEDRON = ITEMS.register(
            "proud_soul_trapezohedron",
            () -> new ProudSoulItem(new Item.Properties(), new SlashBladeMaterial(2048, 2048, 16)) {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                    SpecialEffect se = stack.get(SbDataComponentTypes.SE);
                    if (se != null) {
                        tooltipComponents.add(Component.translatable(LanguageItems.SE.getKey(), Component.translatable(se.getDescriptionId()), se.getMaxLevel()));
                        tooltipComponents.add(Component.translatable(se.getDocDescriptionId()));

                        if (se.isNotReplicable()) {
                            tooltipComponents.add(Component.translatable(LanguageItems.SE_NOT_REPLICABLE.getKey()));
                        }

                        tooltipComponents.add(Component.empty());
                    }
                }
            }
    );


    public static final Supplier<BladeStandItem> BLADESTAND_1 = ITEMS.register("bladestand_1", () -> new BladeStandItem(new Item.Properties(), false));
    public static final Supplier<BladeStandItem> BLADESTAND_2 = ITEMS.register("bladestand_2", () -> new BladeStandItem(new Item.Properties(), false));
    public static final Supplier<BladeStandItem> BLADESTAND_V = ITEMS.register("bladestand_v", () -> new BladeStandItem(new Item.Properties(), false));
    public static final Supplier<BladeStandItem> BLADESTAND_S = ITEMS.register("bladestand_s", () -> new BladeStandItem(new Item.Properties(), false));
    public static final Supplier<BladeStandItem> BLADESTAND_1W = ITEMS.register("bladestand_1w", () -> new BladeStandItem(new Item.Properties(), true));
    public static final Supplier<BladeStandItem> BLADESTAND_2W = ITEMS.register("bladestand_2w", () -> new BladeStandItem(new Item.Properties(), true));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
