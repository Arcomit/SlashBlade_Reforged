package mod.slashblade.reforged.core.obj;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 12:16
 * @Description: 模型面
 */
public class ObjFace {

    protected SimpleVector3f[] vertices;
    protected SimpleVector3f[] verticesUvs;
    protected SimpleVector3f[] vertexNormals;
    protected SimpleVector3f   faceNormal;

    /**
     * 计算并返回三角形面的单位法向量
     * 使用三个顶点按右手定则计算垂直于表面的单位向量
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
