package mod.slashblade.reforged.content.entity;


import com.mojang.datafixers.kinds.IdF;
import mod.slashblade.reforged.content.init.SbItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BladeStandEntity extends ItemFrame {

    protected static final EntityDataAccessor<ItemStack> CURRENT_TYPE = SynchedEntityData.defineId(BladeStandEntity.class, EntityDataSerializers.ITEM_STACK);

    public BladeStandEntity(EntityType<? extends ItemFrame> entityType, Level level, ItemStack currentType) {
        super(entityType, level);
        if (currentType != null) {
            setCurrentType(currentType);
        }
    }

    public BladeStandEntity(EntityType<? extends ItemFrame> entityType, Level level, ItemStack itemStack, BlockPos placePos, Direction dir) {
        this(entityType, level, itemStack);

        pos = placePos;
        setDirection(dir);
    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CURRENT_TYPE, new ItemStack(SbItems.BLADESTAND_1.get()));
    }

    public ItemStack getCurrentType() {
        return this.entityData.get(CURRENT_TYPE);
    }

    public void setCurrentType(ItemStack currentType) {
        this.entityData.set(CURRENT_TYPE, currentType);
    }

    @Override
    protected @NotNull ItemStack getFrameItemStack() {
        return getCurrentType();
    }


}
