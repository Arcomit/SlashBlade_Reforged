package mod.slashblade.reforged.content.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ProudSoulItem extends Item {

    public ProudSoulItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
}
