package mod.slashblade.reforged.utils;

import mod.slashblade.reforged.content.event.AttackEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface ICreateDamageSource {
    @Nullable
    AttackEvent.DamageSourceInfo createDamageSource(LivingEntity attacker, Entity target);
}
