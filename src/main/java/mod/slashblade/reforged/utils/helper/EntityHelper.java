package mod.slashblade.reforged.utils.helper;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
/**
 * @Author: til
 * @Description: 一些实体处理逻辑
 */
public class EntityHelper {

    public static Vec3 getEntityPosition(Entity owner) {
        return new Vec3(owner.getX(), owner.getY() + owner.getEyeHeight(), owner.getZ());
    }

}
