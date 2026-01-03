package mod.slashblade.reforged.core.obj.acceleratedrendering.mixin;

import com.github.argon4w.acceleratedrendering.core.CoreFeature;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.IBufferGraph;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.VertexConsumerExtension;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.renderers.IAcceleratedRenderer;
import com.github.argon4w.acceleratedrendering.core.meshes.IMesh;
import com.github.argon4w.acceleratedrendering.core.meshes.collectors.CulledMeshCollector;
import com.github.argon4w.acceleratedrendering.features.entities.AcceleratedEntityRenderingFeature;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.experimental.ExtensionMethod;
import mod.slashblade.reforged.core.obj.ObjFace;
import mod.slashblade.reforged.core.obj.ObjGroup;
import mod.slashblade.reforged.utils.PoseStackAutoCloser;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import net.minecraft.util.FastColor;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-07 12:58
 * @Description: TODO:未完成
 */
@ExtensionMethod(VertexConsumerExtension.class)
@Mixin          (ObjGroup               .class)
public class ObjGroupMixin implements IAcceleratedRenderer<Void> {

    @Shadow @Final  private List<ObjFace> faces;

    @Shadow private float                    x;
    @Shadow private float                    y;
    @Shadow private float                    z;
    @Shadow private Quaternionf              rotation = new Quaternionf();
    @Shadow private float                    xScale   = 1;
    @Shadow private float                    yScale   = 1;
    @Shadow private float                    zScale   = 1;

    @Unique private final   Map<IBufferGraph, IMesh> meshes = new Object2ObjectOpenHashMap<>();

    @Inject(
            method		= "writeVertices",
            at			= @At("HEAD"),
            cancellable	= true
    )
    public void compileFast(
            VertexConsumer vertexConsumer,
            CallbackInfo   ci
    ) {

        var extension = vertexConsumer.getAccelerated();
        if (AcceleratedEntityRenderingFeature.isEnabled()
                && AcceleratedEntityRenderingFeature.shouldUseAcceleratedPipeline()
                && (
                        CoreFeature.isRenderingLevel()
                                || (
                                        CoreFeature.isRenderingGui()
                                            && AcceleratedEntityRenderingFeature.shouldAccelerateInGui()
                        )
                )
                && extension.isAccelerated()) {
            ci.cancel();

            PoseStack poseStack = WriteVerticesInfo.getPoseStack();
            if (poseStack == null) return;
            try (PoseStackAutoCloser PSAC1 = PoseStackAutoCloser.pushMatrix(poseStack)) {
                poseStack.translate(x / 16, y / 16, z / 16);
                poseStack.mulPose(rotation);
                poseStack.scale(xScale, yScale, zScale);

                Color col = WriteVerticesInfo.getColor();

                int color = FastColor.ARGB32.color
                        (
                                col.getAlpha(),
                                col.getRed(),
                                col.getGreen(),
                                col.getBlue()
                        );

                if (faces.size() > 0) {
                    extension.doRender(this, null,
                            poseStack.last().pose(),
                            poseStack.last().normal(),
                            WriteVerticesInfo.getLightMap(),
                            WriteVerticesInfo.getOverlayMap(),
                            color
                    );
                }
            }
        }

    }

    @Unique
    @Override
    public void render(
            VertexConsumer	vertexConsumer,
            Void			context,
            Matrix4f		transform,
            Matrix3f		normal,
            int				light,
            int				overlay,
            int				color
    ) {

        var extension	= vertexConsumer.getAccelerated	();
        var mesh		= meshes		.get			(extension);

        extension.beginTransform(transform, normal);

        if (mesh != null) {
            mesh.write(
                    extension,
                    color,
                    light,
                    overlay
            );

            extension.endTransform();
            return;
        }

        var culledMeshCollector	= new CulledMeshCollector(extension);
        var meshBuilder			= extension.decorate	 (culledMeshCollector);

        WriteVerticesInfo.resetPoseStack    ();
        WriteVerticesInfo.resetUvOperator   ();
        WriteVerticesInfo.resetAlphaOverride();
        WriteVerticesInfo.resetColor        ();
        for (var face : faces) {
            face.writeVertices(meshBuilder);
        }

        culledMeshCollector.flush();

        mesh = AcceleratedEntityRenderingFeature
                .getMeshType()
                .getBuilder	()
                .build		(culledMeshCollector);

        meshes	.put	(extension, mesh);
        mesh	.write	(
                extension,
                color,
                light,
                overlay
        );

        extension.endTransform();

    }
}
