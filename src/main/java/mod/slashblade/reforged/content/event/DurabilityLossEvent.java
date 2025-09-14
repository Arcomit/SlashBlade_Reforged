package mod.slashblade.reforged.content.event;

import lombok.Getter;
import lombok.Setter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class DurabilityLossEvent extends SlashBladeEvent {
    double basicLoss;
    double durabilityLevel;

    public DurabilityLossEvent(ItemStack item, SlashBladeLogic slashBladeLogic, @Nullable LivingEntity user, double basicLoss) {
        super(item, slashBladeLogic, user);
        this.basicLoss = basicLoss;
    }

    public void addDurabilityLevel(double modification) {
        this.durabilityLevel += modification;
    }

    public void reduceDurabilityLevel(double modification) {
        this.durabilityLevel -= modification;
    }
}
