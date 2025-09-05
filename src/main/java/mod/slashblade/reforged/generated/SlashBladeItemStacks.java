package mod.slashblade.reforged.generated;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbEntityType;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.utils.constant.SlashBladeNameConstants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * 集中管理所有拔刀剑相关的ItemStack供应商
 */
public class SlashBladeItemStacks {


    public static final Supplier<ItemStack> ANONYMITY_NAMELESS = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC,
                            new SlashBladeLogic()
                                    .setKey(SlashBladeNameConstants.ANONYMITY_NAMELESS)
                                    .setAttack(3)
                    )
                    .build();

    public static class ItemStackBuilder {

        ItemStack itemStack;

        public ItemStackBuilder(Item item) {
            this.itemStack = new ItemStack(item);
        }

        public ItemStackBuilder(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public <D> ItemStackBuilder set(Supplier<DataComponentType<D>> dataComponentType, D d) {
            this.itemStack.set(dataComponentType, d);
            return this;
        }


        public ItemStack build() {
            return itemStack;
        }
    }

}
