package mod.slashblade.reforged.content.item;

import mod.slashblade.reforged.content.data.SlashBladeMaterial;
import mod.slashblade.reforged.content.data.capabilitie.ISlashBladeMaterial;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ProudSoulItem extends Item {

    final SlashBladeMaterial slashBladeMaterial;

    public ProudSoulItem(Properties properties, SlashBladeMaterial slashBladeMaterial) {
        super(properties
                .component(SbDataComponentTypes.SLASH_BLADE_MATERIAL.get(), slashBladeMaterial)
        );
        this.slashBladeMaterial = slashBladeMaterial;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

}
