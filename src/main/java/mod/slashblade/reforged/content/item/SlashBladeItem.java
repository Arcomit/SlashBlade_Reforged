package mod.slashblade.reforged.content.item;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationGraph;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.content.init.SbEntityType;
import mod.slashblade.reforged.content.init.SbActions;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
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
                .component(SbDataComponentTypes.MODEL_LOCATION, DefaultResources.DEFAULT_MODEL)
                .component(SbDataComponentTypes.TEXTURE_LOCATION, DefaultResources.DEFAULT_TEXTURE)
                .component(SbDataComponentTypes.DRAW_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponentTypes.WALK_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponentTypes.SPRINT_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponentTypes.WALK_UNSHEATHE_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponentTypes.SPRINT_UNSHEATHE_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponentTypes.SHEATH_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponentTypes.PUT_AWAY_ACTION, DefaultResources.DEFAULT_ACTION)
                //                .component(SbDataComponents.BASIC_EXAMPLE,           new SlashBladeAnimationGraph(1,true))

                .component(SbDataComponentTypes.SLASH_BLADE_LOGIC, new SlashBladeLogic())
                .component(SbDataComponentTypes.SLASH_BLADE_STYLE, new SlashBladeStyle())
        );


    }

}
