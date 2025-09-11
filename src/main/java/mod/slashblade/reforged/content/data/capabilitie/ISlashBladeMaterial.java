package mod.slashblade.reforged.content.data.capabilitie;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.world.item.ItemStack;

/***
 * 可以作为锻刀的材料
 */
public interface ISlashBladeMaterial {

    /***
     * 获取加成的荣耀值
     */
    int getAddProudSoul();

    /***
     * 获取修复的耐久
     */
    double getRepairDamageValue();


}
