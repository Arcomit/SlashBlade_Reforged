package mod.slashblade.reforged.content.item;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.init.SbActions;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.generated.client.language.LanguageItems;
import mod.slashblade.reforged.utils.DefaultResources;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                .component(SbDataComponentTypes.DRAW_ACTION, SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.WALK_ACTION, SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.SPRINT_ACTION, SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.WALK_UNSHEATHE_ACTION, SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.SPRINT_UNSHEATHE_ACTION, SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.SHEATH_ACTION, SbActions.IDLE_ACTION.getId())
                .component(SbDataComponentTypes.PUT_AWAY_ACTION, SbActions.IDLE_ACTION.getId())
                //                .component(SbDataComponents.BASIC_EXAMPLE,           new SlashBladeAnimationGraph(1,true))

                .component(SbDataComponentTypes.SLASH_BLADE_LOGIC, new SlashBladeLogic())
                .component(SbDataComponentTypes.SLASH_BLADE_STYLE, new SlashBladeStyle())
        );

    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(@NotNull ItemStack stack) {
        SlashBladeLogic slashBladeLogic = stack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return ItemAttributeModifiers.EMPTY;
        }

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocationConstants.SLASH_BLADE_ATTACK, slashBladeLogic.getAttack(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        SlashBladeLogic slashBladeLogic = stack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return;
        }

        tooltipComponents.add(Component.empty());

        tooltipComponents.add(Component.translatable(LanguageItems.TOOLTIP_GLORY.getKey(), slashBladeLogic.getProudSoul()));
        tooltipComponents.add(Component.translatable(LanguageItems.TOOLTIP_KILL.getKey(), slashBladeLogic.getKill()));
        tooltipComponents.add(Component.translatable(LanguageItems.TOOLTIP_REFINE.getKey(), slashBladeLogic.getRefine()));

        tooltipComponents.add(Component.empty());

        tooltipComponents.add(Component.translatable(LanguageItems.TOOLTIP_ATTACK.getKey(), slashBladeLogic.getAttack()));


    }

    @Override
    public @NotNull String getDescriptionId(@NotNull ItemStack stack) {
        SlashBladeLogic slashBladeLogic = stack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return getDescriptionId();
        }
        return slashBladeLogic.getDescriptionId();
    }


    /*@Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, tooltipComponents, isAdvanced);
        
        // 添加拔刀剑的特殊提示
        tooltipComponents.add(Component.translatable("item.slashblade_reforged.slashblade.tooltip.1")
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.slashblade_reforged.slashblade.tooltip.2")
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.slashblade_reforged.slashblade.tooltip.3")
                .withStyle(ChatFormatting.GRAY));
        
        // 添加使用说明
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(Component.translatable("item.slashblade_reforged.slashblade.tooltip.usage")
                .withStyle(ChatFormatting.YELLOW));
    }*/


}
