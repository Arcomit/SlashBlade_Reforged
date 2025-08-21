package mod.slashblade.reforged.core.obj;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 12:16
 * @Description: 模型面
 */
@OnlyIn(Dist.CLIENT)
public class ObjFace {

    protected SimpleVector3f[] vertices;
    protected SimpleVector3f[] vertexUvs;
    protected SimpleVector3f[] vertexNormals;
    protected SimpleVector3f   faceNormal;

    private Matrix4f transform;
    private Matrix3f normal;
    private float    averageU = 0F;
    private float    averageV = 0F;

    public  void writeVertices(VertexConsumer vertexConsumer){
        if (WriteVerticesInfo.getPoseStack() != null){


            transform  = WriteVerticesInfo.getPoseStack().last().pose();
            normal     = WriteVerticesInfo.getPoseStack().last().normal();
        }
        if ((vertexUvs != null) && (vertexUvs.length > 0)) {
            for (int i = 0; i < vertexUvs.length; ++i) {
                averageU += vertexUvs[i].getX() * WriteVerticesInfo.getUvOperator().x() + WriteVerticesInfo.getUvOperator().z();
                averageV += vertexUvs[i].getY() * WriteVerticesInfo.getUvOperator().y() + WriteVerticesInfo.getUvOperator().w();
            }

            averageU = averageU / vertexUvs.length;
            averageV = averageV / vertexUvs.length;
        }

        for (int i = 0; i < vertices.length; ++i) {
            writeVertex(vertexConsumer, i);
        }

        averageU  = 0F;
        averageV  = 0F;
        transform = null;
        normal    = null;
    }

    private void writeVertex  (VertexConsumer vertexConsumer, int index){
        Vector3f vertices3f = vertices[index].toJoml();

        Vector3f normal3f;
        if (vertexNormals != null && vertexNormals.length > 0){

            normal3f = vertexNormals[index].toJoml();

        }else {

            normal3f = faceNormal          .toJoml();

        }

        if (transform != null && normal != null){

            vertices3f = vertices3f.mulPosition(transform);

            normal3f   = normal3f  .mul        (normal);
            normal3f               .normalize  ();

        }

        vertexConsumer.addVertex(
                vertices3f.x(),
                vertices3f.y(),
                vertices3f.z()
        );

        Color color = WriteVerticesInfo.getColor();
        vertexConsumer.setColor(
                color.getRed  (),
                color.getGreen(),
                color.getBlue (),
                WriteVerticesInfo.getAlphaOverride().apply(
                        new Vector4f(
                                vertices[index].getX(),
                                vertices[index].getY(),
                                vertices[index].getZ(),
                                1.0F
                        ),      color.getAlpha())
        );

        if ((vertexUvs != null) && (vertexUvs.length > 0)) {
            float offsetU = 0.0005F;
            float offsetV = 0.0005F;

            float textureU = vertexUvs[index].getX() * WriteVerticesInfo.getUvOperator().x() + WriteVerticesInfo.getUvOperator().z();
            float textureV = vertexUvs[index].getY() * WriteVerticesInfo.getUvOperator().y() + WriteVerticesInfo.getUvOperator().w();

            if (textureU > averageU) {
                offsetU = -offsetU;
            }
            if (textureV > averageV) {
                offsetV = -offsetV;
            }

            vertexConsumer.setUv(textureU + offsetU, textureV + offsetV);
        }else {
            vertexConsumer.setUv(0, 0);
        }

        vertexConsumer.setOverlay(WriteVerticesInfo.getOverlayMap());
        vertexConsumer.setLight  (WriteVerticesInfo.getLightMap  ());
        vertexConsumer.setNormal (
                normal3f.x(),
                normal3f.y(),
                normal3f.z()
        );
    }

    /**
     * 使用三个顶点按右手定则计算垂直于表面的单位向量并返回。
     * @return {@link SimpleVector3f} 垂直于表面的单位法向量
     */
    public SimpleVector3f computeUnitNormal() {
        // 计算两个边向量
        double vx1 = vertices[1].getX() - vertices[0].getX();
        double vy1 = vertices[1].getY() - vertices[0].getY();
        double vz1 = vertices[1].getZ() - vertices[0].getZ();

        double vx2 = vertices[2].getX() - vertices[0].getX();
        double vy2 = vertices[2].getY() - vertices[0].getY();
        double vz2 = vertices[2].getZ() - vertices[0].getZ();

        // 计算叉积（法线向量）
        double nx  = vy1 * vz2 - vz1 * vy2;
        double ny  = vz1 * vx2 - vx1 * vz2;
        double nz  = vx1 * vy2 - vy1 * vx2;

        // 归一化
        double length = Math.sqrt(nx * nx + ny * ny + nz * nz);
        return new SimpleVector3f((float)(nx / length), (float)(ny / length), (float)(nz / length));
    }
}
