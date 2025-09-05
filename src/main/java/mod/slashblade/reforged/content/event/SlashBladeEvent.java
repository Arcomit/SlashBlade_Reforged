package mod.slashblade.reforged.content.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.Nullable;

/**
 * @Author: til
 * @Description: 使用拔刀剑交互的事件基类
 */
@Getter
@AllArgsConstructor
public class SlashBladeEvent extends Event {
    final ItemStack item;
    final SlashBladeLogic slashBladeLogic;
    @Nullable
    final LivingEntity user;
}
