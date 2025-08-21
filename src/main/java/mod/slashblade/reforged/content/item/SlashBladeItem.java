package mod.slashblade.reforged.content.item;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.init.SbDataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 16:36
 * @Description: 拔刀剑主类
 */
public class SlashBladeItem extends SwordItem {

    public SlashBladeItem(Tier tier, Properties properties) {
        super(tier, properties
                .component(SbDataComponents.DRAW_ACTION,             SlashbladeMod.prefix("default_idle"))
                .component(SbDataComponents.WALK_ACTION,             SlashbladeMod.prefix("default_idle"))
                .component(SbDataComponents.SPRINT_ACTION,           SlashbladeMod.prefix("default_idle"))
                .component(SbDataComponents.WALK_UNSHEATHE_ACTION,   SlashbladeMod.prefix("default_idle"))
                .component(SbDataComponents.SPRINT_UNSHEATHE_ACTION, SlashbladeMod.prefix("default_idle"))
                .component(SbDataComponents.SHEATH_ACTION,           SlashbladeMod.prefix("default_idle"))
                .component(SbDataComponents.PUT_AWAY_ACTION,         SlashbladeMod.prefix("default_idle"))
        );
    }
}
