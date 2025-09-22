package mod.slashblade.reforged.content.event.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mod.slashblade.reforged.content.capability.IInputCapability;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

@Getter
@AllArgsConstructor
public class KeyEvent extends Event {

    final LivingEntity livingEntity;
    final IInputCapability playerInputCapability;


}
