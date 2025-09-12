package mod.slashblade.reforged.content.data;

import io.netty.buffer.ByteBuf;
import lombok.*;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.register.SpecialAttack;
import mod.slashblade.reforged.content.register.SpecialEffect;
import mod.slashblade.reforged.utils.constant.ByteBufCodecConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@EqualsAndHashCode
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
    double durable = -1;

    /***
     * 荣耀数
     */
    @SaveField
    int proudSoul;

    /***
     * 击杀数
     */
    @SaveField
    int kill;

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

    @Nullable
    @SaveField(canBeNull = true)
    SpecialAttack sa;

    @Builder.Default
    @SaveField(customCodecMethod = "seStreamCodec")
    Map<SpecialEffect, Integer> se = new HashMap<>();

    /***
     * 无法使用
     * 刀鞘
     */
    @SaveField
    boolean sealed;

    /***
     * 易碎的
     * 处于该状态下耐久调往将直接消耗物品
     */
    @SaveField
    boolean fragile;

    /***
     * 特殊修复
     * 该刀需要特殊手段修复，使用铁砧无法修复
     */
    @SaveField
    boolean specialRepair;

    public SlashBladeLogic(String key, float attack, float attackDistance, double maxDurable, double durable, int proudSoul, int kill, int refine, boolean broken, @Nullable SpecialAttack sa, Map<SpecialEffect, Integer> se, boolean sealed, boolean fragile, boolean specialRepair) {
        this.key = key;
        this.attack = attack;
        this.attackDistance = attackDistance;
        this.maxDurable = maxDurable;
        this.durable = durable == -1
                ? maxDurable
                : Math.min(durable, maxDurable);
        this.proudSoul = proudSoul;
        this.kill = kill;
        this.refine = refine;
        this.broken = broken;
        this.sa = sa;
        this.se = se;
        this.sealed = sealed;
        this.fragile = fragile;
        this.specialRepair = specialRepair;
    }

    public SlashBladeLogic setMaxDurable(double maxDurable) {
        this.maxDurable = maxDurable;
        if (this.durable > maxDurable) {
            this.durable = maxDurable;
        }
        return this;
    }

    public boolean meetConditions(SlashBladeLogic model) {

        if (isBroken() && !model.isBroken()) {
            return false;
        }

        if (isSealed() && !model.isSealed()) {
            return false;
        }

        if (isSpecialRepair() && !model.isSpecialRepair()) {
            return false;
        }

        if (!getKey().equals(model.getKey())) {
            return false;
        }

        if (getProudSoul() < model.getProudSoul()) {
            return false;
        }

        if (getKill() < model.getKill()) {
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
                .proudSoul(this.proudSoul)
                .kill(this.kill)
                .refine(this.refine)
                .sa(this.sa)
                .se(new HashMap<>(this.se))
                .broken(this.broken)
                .sealed(this.sealed)
                .fragile(this.fragile)
                .specialRepair(this.specialRepair);
    }

    public static StreamCodec<ByteBuf, Map<SpecialEffect, Integer>> seStreamCodec() {
        return ByteBufCodecConstants.SPECIAL_EFFECT_LEVEL_MAP;
    }

}
