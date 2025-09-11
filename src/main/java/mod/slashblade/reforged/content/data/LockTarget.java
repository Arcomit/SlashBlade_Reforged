package mod.slashblade.reforged.content.data;

import lombok.Getter;
import lombok.Setter;
import mod.slashblade.reforged.content.data.capabilitie.ILockTarget;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import javax.annotation.Nullable;

@Getter
@Setter
public class LockTarget implements ILockTarget, INBTSerializable<Tag> {

    @SaveField
    @Nullable
    public Entity targetEntity;


    @Override
    public @UnknownNullability Tag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull Tag nbt) {

    }
}
