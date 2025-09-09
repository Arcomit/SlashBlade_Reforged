package mod.slashblade.reforged.generated.client.language;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.generated.group.SlashBladeItemStacks;
import net.minecraft.world.item.ItemStack;

public class LanguageItems {

    public static final LanguageItem TEST = new LanguageItem("other.test")
            .addTranslation(LanguageTypes.ZH_CN, "总该说些什么...")
            .addTranslation(LanguageTypes.EN_US, "I should say something...");


    public static final LanguageItem PROUD_SOUL = new LanguageItem(SbItems.PROUD_SOUL.get().getDescriptionId())
            .addTranslation(LanguageTypes.ZH_CN, "耀魂碎片")
            .addTranslation(LanguageTypes.EN_US, "Proud Soul")
            .addTranslation(LanguageTypes.JA_JP, "刀の魂片");

    public static final LanguageItem PROUD_SOUL_INGOT = new LanguageItem(SbItems.PROUD_SOUL_INGOT.get().getDescriptionId())
            .addTranslation(LanguageTypes.ZH_CN, "耀魂铁锭")
            .addTranslation(LanguageTypes.EN_US, "Blade Soul Ingot")
            .addTranslation(LanguageTypes.JA_JP, "刀の魂塊");

    public static final LanguageItem PROUD_SOUL_TINY = new LanguageItem(SbItems.PROUD_SOUL_TINY.get().getDescriptionId())
            .addTranslation(LanguageTypes.ZH_CN, "破碎的耀魂")
            .addTranslation(LanguageTypes.EN_US, "Tiny Proud Soul")
            .addTranslation(LanguageTypes.JA_JP, "刀の薄魂片");

    public static final LanguageItem PROUD_SOUL_SPHERE = new LanguageItem(SbItems.PROUD_SOUL_SPHERE.get().getDescriptionId())
            .addTranslation(LanguageTypes.ZH_CN, "耀魂宝珠")
            .addTranslation(LanguageTypes.EN_US, "Blade Soul Sphere")
            .addTranslation(LanguageTypes.JA_JP, "刀の魂珠");

    public static final LanguageItem PROUD_SOUL_CRYSTAL = new LanguageItem(SbItems.PROUD_SOUL_CRYSTAL.get().getDescriptionId())
            .addTranslation(LanguageTypes.ZH_CN, "耀魂结晶")
            .addTranslation(LanguageTypes.EN_US, "Blade Soul Crystal")
            .addTranslation(LanguageTypes.JA_JP, "刀の魂晶");

    public static final LanguageItem PROUD_SOUL_TRAPEZOHEDRON = new LanguageItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get().getDescriptionId())
            .addTranslation(LanguageTypes.ZH_CN, "耀偏方三八面魂")
            .addTranslation(LanguageTypes.EN_US, "Blade Soul Trapezohedron")
            .addTranslation(LanguageTypes.JA_JP, "揺蕩う刀の魂結晶");

    public static final LanguageItem SLASH_BLADE_ITEM_GROUP = new LanguageItem("itemGroup." + SlashbladeMod.MODID + ".example")
            .addTranslation(LanguageTypes.ZH_CN, "拔刀剑")
            .addTranslation(LanguageTypes.EN_US, "SlashBlade");

    public static final LanguageItem ANONYMITY_NAMELESS = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.ANONYMITY_NAMELESS.get()))
            .addTranslation(LanguageTypes.ZH_CN, "无铭「无名」")
            .addTranslation(LanguageTypes.EN_US, "Anonymity -Nameless-");

    public static final LanguageItem ANONYMITY_WOOD = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.ANONYMITY_WOOD.get()))
            .addTranslation(LanguageTypes.ZH_CN, "无铭刀「木偶」")
            .addTranslation(LanguageTypes.EN_US, "Anonymity -Wood-");

    public static final LanguageItem SHARPNESS_WHITE = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.SHARPNESS_WHITE.get()))
            .addTranslation(LanguageTypes.ZH_CN, "利刀「白鞘」")
            .addTranslation(LanguageTypes.EN_US, "Sharpness White");

    public static final LanguageItem SLASH_BLADE = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.SLASH_BLADE.get()))
            .addTranslation(LanguageTypes.ZH_CN, "大太刀「」")
            .addTranslation(LanguageTypes.EN_US, "SlashBlade");


    public static final LanguageItem TOOLTIP_GLORY = new LanguageItem("slash_blade_item.tooltip.glory")
            .addTranslation(LanguageTypes.ZH_CN, "荣耀: %s");

    public static final LanguageItem TOOLTIP_KILL = new LanguageItem("slash_blade_item.tooltip.kill")
            .addTranslation(LanguageTypes.ZH_CN, "击杀: %s");

    public static final LanguageItem TOOLTIP_REFINE = new LanguageItem("slash_blade_item.tooltip.refine")
            .addTranslation(LanguageTypes.ZH_CN, "锻造: %s");

    public static final LanguageItem TOOLTIP_ATTACK = new LanguageItem("slash_blade_item.tooltip.attack")
            .addTranslation(LanguageTypes.ZH_CN, "基础伤害: %s");


    public static String getItemDescriptionId(ItemStack itemStack) {
        SlashBladeLogic slashBladeLogic = itemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return itemStack.getDescriptionId();
        }
        return slashBladeLogic.getDescriptionId();
    }
}
