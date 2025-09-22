package mod.slashblade.reforged.content.capability;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public interface ILockTarget {

    @Nullable
    Entity getTargetEntity();

    void setTargetEntity(@Nullable Entity entity);

}
