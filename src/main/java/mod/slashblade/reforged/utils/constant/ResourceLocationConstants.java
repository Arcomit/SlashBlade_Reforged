package mod.slashblade.reforged.utils.constant;

import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.resources.ResourceLocation;

/**
 * @Author: til
 * @Description: 常量类
 */
public class ResourceLocationConstants {

    public static final ResourceLocation DEFAULT = SlashbladeMod.prefix("default");

    public static final ResourceLocation DEFAULT_TEXTURE = SlashbladeMod.prefix(StringConstants.SPECIAL, StringConstants.TEXTURE);

    public static final ResourceLocation DEFAULT_SUMMOND_MODEL = SlashbladeMod.prefix(StringConstants.SPECIAL, "summond", StringConstants.MODEL);
    public static final ResourceLocation DEFAULT_SUMMOND_TEXTURE = SlashbladeMod.prefix(StringConstants.SPECIAL, "summond", StringConstants.TEXTURE);

    public static final ResourceLocation DEFAULT_JUDGEMENT_CUT_MODEL = SlashbladeMod.prefix(StringConstants.SPECIAL, "slash", StringConstants.MODEL);
    public static final ResourceLocation DEFAULT_JUDGEMENT_CUT_TEXTURE = SlashbladeMod.prefix(StringConstants.SPECIAL, "slash", StringConstants.TEXTURE);

    public static final ResourceLocation DEFAULT_SLASH_MODEL = SlashbladeMod.prefix(StringConstants.SPECIAL, "slashdim", StringConstants.MODEL);
    public static final ResourceLocation DEFAULT_SLASH_TEXTURE = SlashbladeMod.prefix(StringConstants.SPECIAL, "slashdim", StringConstants.TEXTURE);

    public static final ResourceLocation SLASH_BLADE_ATTACK_MULTIPLIED_BASE = SlashbladeMod.prefix("slash_blade_attack_multiplied_base");
    public static final ResourceLocation SLASH_BLADE_ATTACK_MULTIPLIED_TOTAL = SlashbladeMod.prefix("slash_blade_attack_multiplied_total");
}
