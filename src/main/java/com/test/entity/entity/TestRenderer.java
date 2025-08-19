package com.test.entity.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TestRenderer extends EntityRenderer<Projectile> {

    // 黑色颜色 (RGB)
    private static final float RED = 0.0f;
    private static final float GREEN = 0.0f;
    private static final float BLUE = 0.0f;
    private static final float ALPHA = 1.0f;

    // 长条尺寸 (x, y, z)
    private static final float WIDTH = 0.2f;
    private static final float HEIGHT = 0.2f;
    private static final float LENGTH = 1.0f;

    public TestRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Projectile entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        poseStack.pushPose();

        // 计算实际位置（考虑部分刻）
        double x = Mth.lerp(partialTicks, entity.xo, entity.getX());
        double y = Mth.lerp(partialTicks, entity.yo, entity.getY());
        double z = Mth.lerp(partialTicks, entity.zo, entity.getZ());
        poseStack.translate(-x, -y, -z);

        // 获取移动方向用于旋转
        Vec3 motion = entity.getDeltaMovement();
        float motionX = (float)motion.x;
        float motionY = (float)motion.y;
        float motionZ = (float)motion.z;

        // 计算旋转角度
        float yaw = (float)(Math.atan2(motionZ, motionX) * (180F / (float)Math.PI) - 90.0F);
        float pitch = (float)(-Math.atan2(motionY, Math.sqrt(motionX * motionX + motionZ * motionZ)) * (180F / (float)Math.PI));

        // 应用旋转
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.ZP.rotationDegrees(pitch));

        // 轻微脉动效果
        float pulse = Mth.sin((entity.tickCount + partialTicks) * 0.2F) * 0.05F + 1.0F;
        poseStack.scale(pulse, pulse, pulse);

        // 移动到正确位置
        poseStack.translate(x, y + HEIGHT/2, z);

        // 绘制黑色长条
        renderRod(poseStack, buffer, packedLight);

        poseStack.popPose();
    }

    private void renderRod(PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        //Matrix3f matrix3f = pose.normal();

        // 创建顶点消费者
        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entitySolid(getTextureLocation(null)));

        // 计算顶点位置
        float halfWidth = WIDTH / 2;
        float halfHeight = HEIGHT / 2;

        // 长条的8个顶点
        Vector3f[] vertices = {
                // 前端
                new Vector3f(-halfWidth, -halfHeight, LENGTH/2),  // 0
                new Vector3f(halfWidth, -halfHeight, LENGTH/2),   // 1
                new Vector3f(halfWidth, halfHeight, LENGTH/2),    // 2
                new Vector3f(-halfWidth, halfHeight, LENGTH/2),   // 3

                // 后端
                new Vector3f(-halfWidth, -halfHeight, -LENGTH/2), // 4
                new Vector3f(halfWidth, -halfHeight, -LENGTH/2),  // 5
                new Vector3f(halfWidth, halfHeight, -LENGTH/2),   // 6
                new Vector3f(-halfWidth, halfHeight, -LENGTH/2)   // 7
        };

        // 绘制六个面
        drawFace(vertexBuilder, matrix4f, pose, vertices[0], vertices[1], vertices[2], vertices[3]); // 前端
        drawFace(vertexBuilder, matrix4f, pose, vertices[5], vertices[4], vertices[7], vertices[6]); // 后端
        drawFace(vertexBuilder, matrix4f, pose, vertices[4], vertices[0], vertices[3], vertices[7]); // 左侧
        drawFace(vertexBuilder, matrix4f, pose, vertices[1], vertices[5], vertices[6], vertices[2]); // 右侧
        drawFace(vertexBuilder, matrix4f, pose, vertices[3], vertices[2], vertices[6], vertices[7]); // 顶部
        drawFace(vertexBuilder, matrix4f, pose, vertices[4], vertices[5], vertices[1], vertices[0]); // 底部
    }

    private void drawFace(VertexConsumer builder, Matrix4f matrix4f, PoseStack.Pose matrix3f,
                          Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4) {
        // 法线向量
        Vector3f normal = new Vector3f(v2).sub(v1).cross(new Vector3f(v3).sub(v1)).normalize();

        // 绘制两个三角形组成四边形
        addVertex(builder, matrix4f, matrix3f, v1, normal);
        addVertex(builder, matrix4f, matrix3f, v2, normal);
        addVertex(builder, matrix4f, matrix3f, v3, normal);

        addVertex(builder, matrix4f, matrix3f, v1, normal);
        addVertex(builder, matrix4f, matrix3f, v3, normal);
        addVertex(builder, matrix4f, matrix3f, v4, normal);
    }

    private void addVertex(VertexConsumer builder, Matrix4f matrix4f, PoseStack.Pose matrix3f,
                           Vector3f position, Vector3f normal) {
        builder.addVertex(matrix4f, position.x(), position.y(), position.z());
        builder.setColor(RED, GREEN, BLUE, ALPHA);
        builder.setUv(0, 0);
        builder.setOverlay(OverlayTexture.NO_OVERLAY);
        builder.setLight(0xF000F0);
        builder.setNormal(matrix3f, normal.x(), normal.y(), normal.z());
    }

    @Override
    public ResourceLocation getTextureLocation(Projectile entity) {
        // 返回一个虚拟纹理路径（实际不使用纹理）
        return SlashbladeMod.prefix("textures/entity/black_rod.png");
    }
}
