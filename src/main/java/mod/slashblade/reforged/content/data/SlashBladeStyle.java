package mod.slashblade.reforged.content.data;

import lombok.Data;
import mod.slashblade.reforged.content.entity.JudgementCutEntity;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
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
    Color color = new Color(0x3333FF);

    public void decorate(SummondSwordEntity summondSwordEntity) {
        summondSwordEntity.setColor(color);
        if (summondSwordModel != null) {
            summondSwordEntity.setModel(summondSwordModel);
        }
        if (summondSwordTexture != null) {
            summondSwordEntity.setTexture(summondSwordTexture);
        }
    }

    public void decorate(JudgementCutEntity judgementCutEntity) {
        judgementCutEntity.setColor(color);
        if (judgementCutModel != null) {
            judgementCutEntity.setModel(judgementCutModel);
        }
        if (judgementCutTexture != null) {
            judgementCutEntity.setTexture(judgementCutTexture);
        }
    }

}
