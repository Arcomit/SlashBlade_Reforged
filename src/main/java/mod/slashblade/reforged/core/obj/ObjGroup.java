package mod.slashblade.reforged.core.obj;

import com.mojang.blaze3d.vertex.VertexConsumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 12:10
 * @Description: 模型组
 */
@OnlyIn(Dist.CLIENT)
@Getter@AllArgsConstructor
public class ObjGroup {

    private       String        name;
    private final List<ObjFace> faces = new ArrayList<>();

    public void writeVertices(VertexConsumer vertexConsumer){
        for (ObjFace face : faces) {

            face.writeVertices(vertexConsumer);

        }
    }
}
