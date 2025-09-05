package mod.slashblade.reforged.utils;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface ICreateDamageSource {
    DamageSource createDamageSource(LivingEntity attacker, Entity target);
}
