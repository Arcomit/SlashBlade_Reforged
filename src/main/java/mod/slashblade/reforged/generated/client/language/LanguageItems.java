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

    // 无铭刀「竹光」 (Anonymity -Bamboo Light-)
    public static final LanguageItem ANONYMITY_BAMBOO_LIGHT = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.ANONYMITY_BAMBOO_LIGHT.get()))
            .addTranslation(LanguageTypes.ZH_CN, "无铭刀「竹光」")
            .addTranslation(LanguageTypes.EN_US, "Anonymity -Bamboo Light-");

    // 名刀「银纸竹光」 (Noted -Silver Bamboo Light-)
    public static final LanguageItem NOTED_SILVER_BAMBOO_LIGHT = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.NOTED_SILVER_BAMBOO_LIGHT.get()))
            .addTranslation(LanguageTypes.ZH_CN, "名刀「银纸竹光」")
            .addTranslation(LanguageTypes.EN_US, "Noted -Silver Bamboo Light-");

    // 木刀「铁刀木」 (Ironwood -Tagayasan-)
    public static final LanguageItem IRONWOOD_TAGAYASAN = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.IRONWOOD_TAGAYASAN.get()))
            .addTranslation(LanguageTypes.ZH_CN, "木刀「铁刀木」")
            .addTranslation(LanguageTypes.EN_US, "Ironwood -Tagayasan-");

    // 宝刀「付丧」结月 (Noble -Tukumo- Violet)
    public static final LanguageItem NOBLE_TUKUMO_VIOLET = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.NOBLE_TUKUMO_VIOLET.get()))
            .addTranslation(LanguageTypes.ZH_CN, "宝刀「付丧」结月")
            .addTranslation(LanguageTypes.EN_US, "Noble -Tukumo- Violet");

    // 「千鹤」村正 (-Chizuru- Muramasa)
    public static final LanguageItem CHIZURU_MURAMASA = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.CHIZURU_MURAMASA.get()))
            .addTranslation(LanguageTypes.ZH_CN, "「千鹤」村正")
            .addTranslation(LanguageTypes.EN_US, "-Chizuru- Muramasa");

    // 鞘 (Scabbard)
    public static final LanguageItem SCABBARD = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.SCABBARD.get()))
            .addTranslation(LanguageTypes.ZH_CN, "鞘")
            .addTranslation(LanguageTypes.EN_US, "Scabbard");

    // 利刀「无名」红玉 (Sharpness -nameless- Ruby)
    public static final LanguageItem SHARPNESS_NAMELESS_RUBY = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.SHARPNESS_NAMELESS_RUBY.get()))
            .addTranslation(LanguageTypes.ZH_CN, "利刀「无名」红玉")
            .addTranslation(LanguageTypes.EN_US, "Sharpness -nameless- Ruby");

    // 狐月刀「黑狐」 (Crescent -BlackFoxes-)
    public static final LanguageItem CRESCENT_BLACK_FOXES = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.CRESCENT_BLACK_FOXES.get()))
            .addTranslation(LanguageTypes.ZH_CN, "狐月刀「黑狐」")
            .addTranslation(LanguageTypes.EN_US, "Crescent -BlackFoxes-");

    // 狐月刀「白狐」 (Crescent -WeissFox-)
    public static final LanguageItem CRESCENT_WEISS_FOX = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.CRESCENT_WEISS_FOX.get()))
            .addTranslation(LanguageTypes.ZH_CN, "狐月刀「白狐」")
            .addTranslation(LanguageTypes.EN_US, "Crescent -WeissFox-");

    // 「击柝」露台 (-Wooden- Rodai)
    public static final LanguageItem WOODEN_RODAI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.WOODEN_RODAI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "「击柝」露台")
            .addTranslation(LanguageTypes.EN_US, "-Wooden- Rodai");

    // 「锐岩」露台 (-Stone- Rodai)
    public static final LanguageItem STONE_RODAI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.STONE_RODAI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "「锐岩」露台")
            .addTranslation(LanguageTypes.EN_US, "-Stone- Rodai");

    // 利刀「铁」露台 (Named -Steel- Rodai)
    public static final LanguageItem NAMED_STEEL_RODAI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.NAMED_STEEL_RODAI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "利刀「铁」露台")
            .addTranslation(LanguageTypes.EN_US, "Named -Steel- Rodai");

    // 宝刀「山吹」露台 (Named -Golden- Rodai)
    public static final LanguageItem NAMED_GOLDEN_RODAI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.NAMED_GOLDEN_RODAI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "宝刀「山吹」露台")
            .addTranslation(LanguageTypes.EN_US, "Named -Golden- Rodai");

    // 名刀「金刚」露台 (Named -Diamond- Rodai)
    public static final LanguageItem NAMED_DIAMOND_RODAI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.NAMED_DIAMOND_RODAI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "名刀「金刚」露台")
            .addTranslation(LanguageTypes.EN_US, "Named -Diamond- Rodai");

    // 名刀「玄钰」露台 (Named -Netherite- Rodai)
    public static final LanguageItem NAMED_NETHERITE_RODAI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.NAMED_NETHERITE_RODAI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "名刀「玄钰」露台")
            .addTranslation(LanguageTypes.EN_US, "Named -Netherite- Rodai");

    // 锈刀 (Sabigatana)
    public static final LanguageItem SABIGATANA = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.SABIGATANA.get()))
            .addTranslation(LanguageTypes.ZH_CN, "锈刀")
            .addTranslation(LanguageTypes.EN_US, "Sabigatana");

    // 刚剑「胴田贯」 (Steel -Doutanuki-)
    public static final LanguageItem STEEL_DOUTANUKI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.STEEL_DOUTANUKI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "刚剑「胴田贯」")
            .addTranslation(LanguageTypes.EN_US, "Steel -Doutanuki-");

    // 枯石大刀 (Koseki)
    public static final LanguageItem KOSEKI = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.KOSEKI.get()))
            .addTranslation(LanguageTypes.ZH_CN, "枯石大刀")
            .addTranslation(LanguageTypes.EN_US, "Koseki");

    // 魔剑「阎魔刀」 (Yamato)
    public static final LanguageItem YAMATO = new LanguageItem(getItemDescriptionId(SlashBladeItemStacks.YAMATO.get()))
            .addTranslation(LanguageTypes.ZH_CN, "魔剑「阎魔刀」")
            .addTranslation(LanguageTypes.EN_US, "Yamato");


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
