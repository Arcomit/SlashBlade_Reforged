package mod.slashblade.reforged.content.client.camera;

import com.maydaymemory.mae.basic.YXZRotationView;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.item.SlashBladeItem;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-13 14:26
 * @Description: TODO（未完成）
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class CameraAnimationHandler {

    public static float posX;
    public static float posY;
    public static float posZ;

    public static float xRotation;
    public static float yRotation;
    public static float zRotation;


    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        CameraType cameraType = Minecraft.getInstance().options.getCameraType();
        if (
                event.getCamera().getEntity() instanceof Player player &&
                cameraType == CameraType.FIRST_PERSON                  &&
                player.getMainHandItem().getItem() instanceof SlashBladeItem
        ) {

            event.setYaw  (event.getYaw  () + yRotation);

            event.setPitch(event.getPitch() + xRotation);

            event.setRoll (event.getRoll () - zRotation);
        }


    }

    public static void setCameraPos(Vector3fc translation){
        posX = translation.x() / 16.0f;
        posY = translation.y() / 16.0f;
        posZ = translation.z() / 16.0f;
    }

    public static void setCameraRotation(Vector3f rotationAngles){
        xRotation = -(float) Math.toDegrees(rotationAngles.x);
        yRotation = -(float) Math.toDegrees(rotationAngles.y);
        zRotation =  (float) Math.toDegrees(rotationAngles.z);
    }

    public static void resetCameraRotation(){
        xRotation = 0;
        yRotation = 0;
        zRotation = 0;
    }
}
