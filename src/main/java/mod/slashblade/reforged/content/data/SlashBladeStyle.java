package mod.slashblade.reforged.content.data;

import lombok.Data;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;

@Data
public class SlashBladeStyle {

    @SaveField(canBeNull = true)
    @Nullable
    protected ResourceLocation summondSwordModel;

    @SaveField(canBeNull = true)
    @Nullable
    protected ResourceLocation summondSwordTexture;

    @SaveField(canBeNull = true)
    @Nullable
    protected ResourceLocation slashEffectModel;

    @SaveField(canBeNull = true)
    @Nullable
    protected ResourceLocation slashEffectTexture;

    @SaveField(canBeNull = true)
    @Nullable
    protected ResourceLocation judgementCutModel;

    @SaveField(canBeNull = true)
    @Nullable
    protected ResourceLocation judgementCutTexture;

    /***
     * 携带模式
     */
    @SaveField
    CarryType carryType = CarryType.NAKED;

    /***
     * 没有刀鞘
     */
    @SaveField
    boolean noScabbard;

    @SaveField
    Color carryColor = new Color(0x3333FF);
}
