package mod.slashblade.reforged.content.item;

import mod.slashblade.reforged.content.init.SbActions;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 16:36
 * @Description: 拔刀剑主类
 */
public class SlashBladeItem extends SwordItem {

    public SlashBladeItem(Tier tier, Properties properties) {
        super(tier, properties
                .component(SbDataComponentTypes.MODEL_LOCATION,          DefaultResources.DEFAULT_MODEL)
                .component(SbDataComponentTypes.TEXTURE_LOCATION,        DefaultResources.DEFAULT_TEXTURE)
                .component(SbDataComponentTypes.DRAW_ACTION,             SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.WALK_ACTION,             SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.SPRINT_ACTION,           SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.WALK_UNSHEATHE_ACTION,   SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.SPRINT_UNSHEATHE_ACTION, SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.SHEATH_ACTION,           SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.PUT_AWAY_ACTION,         SbActions.IDLE_ACTION.getId())
//                .component(SbDataComponents.BASIC_EXAMPLE,           new SlashBladeAnimationGraph(1,true))
        );
    }
}
