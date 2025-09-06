package mod.slashblade.reforged.content.data;

import lombok.Data;
import lombok.experimental.Accessors;
import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

@Data
@Accessors(chain = true)
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
    float attack = 1;

    /***
     * 攻击距离
     */
    @SaveField
    float attackDistance = 1;

    /***
     * 最大耐久度
     */
    @SaveField
    double maxDurable = 4096;

    /***
     * 当前的耐久度
     */
    @SaveField
    double durable = 4096;

    /***
     * 荣耀数
     */
    @SaveField
    int gloryCount;

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

    /***
     * 瞄准目的Id
     */
    @SaveField
    int targetEntityId;

    @Nullable
    public Entity getTargetEntity(Level level) {
        int id = this.getTargetEntityId();
        return id < 0
                ? null
                : level.getEntity(id);
    }

    public void setTargetEntity(Entity entity) {

        if (entity == null) {
            setTargetEntityId(-1);
            return;
        }

        setTargetEntityId(entity.getId());
    }

    public boolean canUse() {
        return !isBroken() && !isSealed();
    }

    public String getDescriptionId() {
        return  SlashbladeMod.MODID + "." + key + ".name";
    }

    public Component getDescription() {
       return Component.translatable(this.getDescriptionId());
    }

}
