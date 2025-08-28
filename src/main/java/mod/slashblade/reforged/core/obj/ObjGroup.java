package mod.slashblade.reforged.core.obj;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mod.slashblade.reforged.utils.PoseStackAutoCloser;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 12:10
 * @Description: 模型组
 */
@OnlyIn(Dist.CLIENT)
@Getter@Setter
public class ObjGroup {

    private final String        name;
    private final List<ObjFace> faces    = new ArrayList<>();

    private       float         x;
    private       float         y;
    private       float         z;
    private       Quaternionf   rotation = new Quaternionf();
    private       float         xScale   = 1;
    private       float         yScale   = 1;
    private       float         zScale   = 1;

    public ObjGroup(String name) {
        this.name = name;
    }

    public void writeVertices(VertexConsumer vertexConsumer){
        PoseStack poseStack = WriteVerticesInfo.getPoseStack();
        try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(poseStack)) {
            poseStack.translate(x / 16, y / 16, z / 16);
            poseStack.mulPose(rotation);
            poseStack.scale(xScale, yScale, zScale);
            for (ObjFace face : faces) {

                face.writeVertices(vertexConsumer);

            }
        }
    }

    public void resetPose(){
        x = 0;
        y = 0;
        z = 0;
        rotation.identity();
        xScale = 1;
        yScale = 1;
        zScale = 1;
    }
}
