package mod.slashblade.reforged.content.event;

import lombok.Getter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.entity.SlashEffectEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@Getter
public class SlashBladeDoSlashEvent extends SlashBladeEvent {
    final LivingEntity attacker;
    final SlashEffectEntity slashEffectEntity;

    public SlashBladeDoSlashEvent(ItemStack item, SlashBladeLogic slashBladeLogic,LivingEntity attacker, SlashEffectEntity slashEffectEntity) {
        super(item, slashBladeLogic, attacker);
        this.attacker = attacker;
        this.slashEffectEntity = slashEffectEntity;
    }
}
