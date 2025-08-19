package mod.slashblade.reforged.content.client.camera;

import com.maydaymemory.mae.basic.YXZRotationView;
import com.maydaymemory.mae.util.MathUtil;
import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-13 14:26
 * @Description: TODO
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class CameraAnimationHandler {

    public static float x;


    public static float y;


    public static float z;


    private static float rotationProgress = 0;


    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (event.getCamera().getEntity() instanceof Player) {
            rotationProgress += 0.02f;


            Vector3f angles = new Vector3f((float) Math.toRadians(10), (float) Math.toRadians(78), (float) Math.toRadians(29));

            YXZRotationView view = new YXZRotationView(angles);
            Quaternionf quat1 = new Quaternionf(view.asQuaternion());

            Vector3f angles2 = new Vector3f((float) Math.toRadians(0), (float) Math.toRadians(0), (float) Math.toRadians(0));
            YXZRotationView view2 = new YXZRotationView(angles2);
            Quaternionf quat2 = new Quaternionf(view2.asQuaternion());

            //Quaternionf quat3 = MathUtil.nlerpShortestPath(quat1,quat2,(float) ((Math.cos(rotationProgress) + 1) * 0.5));
            Quaternionf quat3 = MathUtil.nlerpShortestPath(quat1,quat2,1);


            YXZRotationView viewFromQuat = new YXZRotationView(quat3);
            Vector3f resultAngles = new Vector3f(viewFromQuat.asEulerAngle());

            x = (float) Math.toDegrees(resultAngles.x);
            y = (float) Math.toDegrees(resultAngles.y);
            z = (float) Math.toDegrees(resultAngles.z);

            event.setYaw  (event.getYaw  () + y);

            event.setPitch(event.getPitch() + x);

            event.setRoll (event.getRoll () - z);
        }
    }
}
