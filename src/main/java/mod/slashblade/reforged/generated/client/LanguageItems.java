package mod.slashblade.reforged.generated.client;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.generated.SlashBladeItemStacks;
import net.minecraft.world.item.ItemStack;

public class LanguageItems {

    public static final LanguageItem TEST = new LanguageItem("other.test")
            .addTranslation(LanguageTypes.ZH_CN, "总该说些什么...")
            .addTranslation(LanguageTypes.EN_US, "I should say something...");

    public static final LanguageItem SLASH_BLADE_ITEM_GROUP = new LanguageItem("itemGroup." + SlashbladeMod.MODID + ".example")
            .addTranslation(LanguageTypes.ZH_CN, "拔刀剑")
            .addTranslation(LanguageTypes.EN_US, "SlashBlade");

    public static final LanguageItem ANONYMITY_NAMELESS = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.ANONYMITY_NAMELESS.get()))
            .addTranslation(LanguageTypes.ZH_CN, "无铭「无名」")
            .addTranslation(LanguageTypes.EN_US, "Anonymity -Nameless-");


    public static String getItemDescriptionId(ItemStack itemStack) {
        SlashBladeLogic slashBladeLogic = itemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return itemStack.getDescriptionId();
        }
        return slashBladeLogic.getDescriptionId();
    }
}
