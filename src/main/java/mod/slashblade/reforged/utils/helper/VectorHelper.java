package mod.slashblade.reforged.utils.helper;

import net.minecraft.world.phys.Vec3;

public class VectorHelper {
    public static Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = pitch * ((float) Math.PI / 180F);
        float f1 = -yaw * ((float) Math.PI / 180F);
        float f2 = MathHelper.cos(f1);
        float f3 = MathHelper.sin(f1);
        float f4 = MathHelper.cos(f);
        float f5 = MathHelper.sin(f);
        return new Vec3(f3 * f4, -f5, f2 * f4);
    }
}
