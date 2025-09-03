package mod.slashblade.reforged.content.item;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationGraph;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.content.init.SbDataComponents;
import mod.slashblade.reforged.content.init.SbEntityType;
import mod.slashblade.reforged.utils.DefaultResources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 16:36
 * @Description: 拔刀剑主类
 */
public class SlashBladeItem extends SwordItem {

    public SlashBladeItem(Tier tier, Properties properties) {
        super(tier, properties
                .component(SbDataComponents.MODEL_LOCATION, DefaultResources.DEFAULT_MODEL)
                .component(SbDataComponents.TEXTURE_LOCATION, DefaultResources.DEFAULT_TEXTURE)
                .component(SbDataComponents.DRAW_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponents.WALK_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponents.SPRINT_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponents.WALK_UNSHEATHE_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponents.SPRINT_UNSHEATHE_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponents.SHEATH_ACTION, DefaultResources.DEFAULT_ACTION)
                .component(SbDataComponents.PUT_AWAY_ACTION, DefaultResources.DEFAULT_ACTION)
                //                .component(SbDataComponents.BASIC_EXAMPLE,           new SlashBladeAnimationGraph(1,true))

                .component(SbDataComponents.SLASH_BLADE_LOGIC, new SlashBladeLogic())
                .component(SbDataComponents.SLASH_BLADE_STYLE, new SlashBladeStyle())
        );


    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        InteractionResult interactionResult = super.useOn(context);

        if (context.getPlayer() == null) {
            return interactionResult;
        }

        if (context.getPlayer().level().isClientSide()) {
            return interactionResult;
        }

        SummondSwordEntity summondSwordEntity = new SummondSwordEntity(SbEntityType.SUMMOND_SWORD_ENTITY.get(), context.getPlayer().level(), context.getPlayer());

        context.getPlayer().level().addFreshEntity(summondSwordEntity);

        return interactionResult;
    }
}
