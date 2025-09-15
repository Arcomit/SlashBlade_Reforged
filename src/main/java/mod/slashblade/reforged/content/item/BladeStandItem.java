package mod.slashblade.reforged.content.item;

import lombok.Getter;
import mod.slashblade.reforged.content.entity.BladeStandEntity;
import mod.slashblade.reforged.content.init.SbEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BladeStandItem extends HangingEntityItem {
    @Getter
    boolean wallType;

    public BladeStandItem(Properties properties, boolean wallType) {
        super(SbEntityType.BLADE_STAND_ENTITY.get(), properties);
        this.wallType = wallType;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player playerentity = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (playerentity == null) {
            return InteractionResult.FAIL;
        }

        if (!this.mayPlace(playerentity, direction, itemstack, blockpos1)) {
            return InteractionResult.FAIL;
        }

        Level world = context.getLevel();
        HangingEntity hangingentity = new BladeStandEntity(SbEntityType.BLADE_STAND_ENTITY.get(), playerentity.level(), new ItemStack(this), blockpos1, direction);

        if (hangingentity.survives()) {
            if (!world.isClientSide) {
                hangingentity.playPlacementSound();
                world.addFreshEntity(hangingentity);
            }

            itemstack.shrink(1);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return InteractionResult.CONSUME;
        }
    }

    protected boolean mayPlace(@NotNull Player player, @NotNull Direction dir, @NotNull ItemStack stack, @NotNull BlockPos pos) {
        if (isWallType()) {
            return !dir.getAxis().isVertical() && !player.level().isOutsideBuildHeight(pos) && player.mayUseItemAt(pos, dir, stack);
        } else {
            return (dir == Direction.UP) && !player.level().isOutsideBuildHeight(pos) && player.mayUseItemAt(pos, dir, stack);
        }
    }
}
