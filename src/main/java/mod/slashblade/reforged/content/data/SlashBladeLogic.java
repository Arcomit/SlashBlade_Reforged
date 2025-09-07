package mod.slashblade.reforged.content.data;

import lombok.*;
import lombok.experimental.Accessors;
import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SlashBladeLogic {

    /***
     * 刀的名称
     */
    @SaveField
    String key;

    /***
     * 基础攻击力
     */
    @SaveField
    @Builder.Default
    float attack = 1;

    /***
     * 攻击距离
     */
    @SaveField
    @Builder.Default
    float attackDistance = 1;

    /***
     * 最大耐久度
     */
    @SaveField
    @Builder.Default
    double maxDurable = 4096;

    /***
     * 当前的耐久度
     */
    @SaveField
    @Builder.Default
    double durable = 4096;

    /***
     * 荣耀数
     */
    @SaveField
    int glory;

    /***
     * 击杀数
     */
    @SaveField
    int killCount;

    /***
     * 锻造数
     */
    @SaveField
    int refine;

    /***
     * 刀是损坏的
     */
    @SaveField
    boolean broken;

    /***
     * 你拔不出来(原版有这个属性就搬过来了)
     */
    @SaveField
    boolean sealed;



    public SlashBladeLogic setMaxDurable(double maxDurable) {
        this.maxDurable = maxDurable;
        if (this.durable > maxDurable) {
            this.durable = maxDurable;
        }
        return this;
    }

    public boolean meetConditions(SlashBladeLogic model) {
        if (!canUse()) {
            return false;
        }

        if (!getKey().equals(model.getKey())) {
            return false;
        }

        if (getGlory() < model.getGlory()) {
            return false;
        }

        if (getKillCount() < model.getKillCount()) {
            return false;
        }

        if (getRefine() < model.getRefine()) {
            return false;
        }

        // TODO  SE 判定
        return true;
    }


    public boolean canUse() {
        return !isBroken() && !isSealed();
    }

    public String getDescriptionId() {
        return SlashbladeMod.MODID + "." + key + ".name";
    }

    public Component getDescription() {
        return Component.translatable(this.getDescriptionId());
    }


    @Override
    public SlashBladeLogic clone() {
        try {
            return (SlashBladeLogic) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }

    public SlashBladeLogic.SlashBladeLogicBuilder toBuilder() {
        return new SlashBladeLogicBuilder()
                .key(this.key)
                .attack(this.attack)
                .attackDistance(this.attackDistance)
                .maxDurable(this.maxDurable)
                .durable(this.durable)
                .glory(this.glory)
                .killCount(this.killCount)
                .refine(this.refine)
                .broken(this.broken)
                .sealed(this.sealed);
    }

}
