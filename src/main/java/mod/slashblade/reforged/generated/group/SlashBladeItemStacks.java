package mod.slashblade.reforged.generated.group;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.utils.constant.SlashBladeNameConstants;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * 集中管理所有拔刀剑相关的ItemStack供应商
 */
public class SlashBladeItemStacks {


    /***
     * 无铭「无名」
     */
    public static final Supplier<ItemStack> ANONYMITY_NAMELESS = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC,
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.ANONYMITY_NAMELESS)
                                    .attack(4)
                                    .build()
                    )
                    .build();

    /***
     * 利刀「白鞘」
     */
    public static final Supplier<ItemStack> SHARPNESS_WHITE = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC,
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.SHARPNESS_WHITE)
                                    .attack(3)
                                    .maxDurable(1024)
                                    .build()
                    )
                    .build();

    /***
     * 大太刀「」
     */
    public static final Supplier<ItemStack> SLASH_BLADE = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC,
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.SLASH_BLADE)
                                    .attack(4)
                                    .build()
                    )
                    .build();


}
