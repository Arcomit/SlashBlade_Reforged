package mod.slashblade.reforged.core.obj;

import com.maydaymemory.mae.basic.BoneTransform;
import com.maydaymemory.mae.basic.Pose;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lombok.Getter;
import mod.slashblade.reforged.content.client.camera.CameraAnimationHandler;
import mod.slashblade.reforged.core.animation.event.AnimationManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 12:08
 * @Description: 模型主类
 */
@OnlyIn(Dist.CLIENT)
@Getter
public class ObjModel {

    private final Map<String, ObjGroup> Groups = new HashMap<>();

    public void applyPose(Pose pose) {
        for (BoneTransform boneTransform : pose.getBoneTransforms()) {
            String groupName = AnimationManager.INDEX_PROVIDER.getGroupName(boneTransform.boneIndex());
            if (!groupName.equals("camera")){
                ObjGroup group = Groups.get(groupName);
                if (group != null) {
                    Vector3fc translation = boneTransform.translation();
                    Quaternionfc rotation = boneTransform.rotation().asQuaternion();
                    Vector3fc scale = boneTransform.scale();
                    group.setX(translation.x());
                    group.setY(translation.y());
                    group.setZ(translation.z());
                    group.getRotation().set(rotation);
                    group.setXScale(scale.x());
                    group.setYScale(scale.y());
                    group.setZScale(scale.z());
                }
            }else {
                Vector3fc translation = boneTransform.translation();
                Vector3f rotationAngles = new Vector3f(boneTransform.rotation().asEulerAngle());
                CameraAnimationHandler.setCameraPos(translation);
                CameraAnimationHandler.setCameraRotation(rotationAngles);
            }
        }
    }

    public void resetPose() {
        for (ObjGroup group : Groups.values()) {

            group.resetPose();

        }
    }

    /**
     * 写入所有模型组的顶点
     * @param vertexConsumer 需要写入的顶点消费者
     */
    public void writeVerticesAll(VertexConsumer vertexConsumer){
        for (ObjGroup group : Groups.values()) {

            group.writeVertices(vertexConsumer);

        }
    }

    /**
     * 写入指定模型组的顶点
     * @param vertexConsumer 需要写入的顶点消费者
     * @param groupName      指定的模型组名
     */
    public void writeVerticesOnly(VertexConsumer vertexConsumer, String... groupName){
        for (String name : groupName) {

            writeVerticesOnly(vertexConsumer, name);

        }
    }

    /**
     * 写入指定模型组的顶点(单个组)
     * @param vertexConsumer 需要写入的顶点消费者
     * @param groupName      指定的模型组名
     */
    public void writeVerticesOnly(VertexConsumer vertexConsumer, String groupName){
        ObjGroup group = Groups.get(groupName);
        if (group != null) {

            group.writeVertices(vertexConsumer);

        }
    }
}
