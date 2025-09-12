package mod.slashblade.reforged.content.data;

import lombok.*;
import lombok.experimental.Accessors;
import mod.slashblade.reforged.content.entity.JudgementCutEntity;
import mod.slashblade.reforged.content.entity.SlashEffectEntity;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.utils.DefaultResources;
import mod.slashblade.reforged.utils.constant.R;
import mod.slashblade.reforged.utils.constant.ResourceLocationConstants;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SlashBladeStyle implements Cloneable {


    @SaveField(canBeNull = true)
    @Nullable
    @Builder.Default
    protected ResourceLocation model = R.Slashblade.Models.blade$obj;

    @SaveField(canBeNull = true)
    @Nullable
    @Builder.Default
    protected ResourceLocation texture = R.Slashblade.Models.blade$png;

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
    @Builder.Default
    CarryType carryType = CarryType.NAKED;

    /***
     * 没有刀鞘
     */
    @SaveField
    boolean noScabbard;

    @SaveField
    @Builder.Default
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

    public void decorate(SlashEffectEntity slashEffectEntity) {
        slashEffectEntity.setColor(color);
        if (slashEffectModel != null) {
            slashEffectEntity.setModel(slashEffectModel);
        }
        if (slashEffectTexture != null) {
            slashEffectEntity.setTexture(slashEffectTexture);
        }
    }

    @Override
    public SlashBladeStyle clone() {
        try {
            SlashBladeStyle cloned = (SlashBladeStyle) super.clone();
            // 深拷贝 Color 对象
            if (this.color != null) {
                cloned.color = new Color(this.color.getRGB());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }


    public SlashBladeStyle.SlashBladeStyleBuilder toBuilder() {
        return new SlashBladeStyleBuilder()
                .summondSwordModel(this.summondSwordModel)
                .summondSwordTexture(this.summondSwordTexture)
                .slashEffectModel(this.slashEffectModel)
                .slashEffectTexture(this.slashEffectTexture)
                .judgementCutModel(this.judgementCutModel)
                .judgementCutTexture(this.judgementCutTexture)
                .carryType(this.carryType)
                .noScabbard(this.noScabbard)
                .color(this.color);
    }

}
