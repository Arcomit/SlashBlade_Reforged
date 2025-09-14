package mod.slashblade.reforged.content.event;

import lombok.Getter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.item.SlashBladeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.Nullable;

public class BrokenEvent extends SlashBladeEvent {
    @Getter
    final SlashBladeLogic.SlashBladeLogicBuilder slashBladeLogicBuilder;
    public BrokenEvent(ItemStack item, SlashBladeLogic slashBladeLogic, @Nullable LivingEntity user, SlashBladeLogic.SlashBladeLogicBuilder slashBladeLogicBuilder) {
        super(item, slashBladeLogic, user);
        this.slashBladeLogicBuilder = slashBladeLogicBuilder;
    }




}
