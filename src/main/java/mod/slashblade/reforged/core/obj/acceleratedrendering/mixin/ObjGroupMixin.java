package mod.slashblade.reforged.core.obj.acceleratedrendering.mixin;

import com.github.argon4w.acceleratedrendering.core.CoreFeature;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.IBufferGraph;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.VertexConsumerExtension;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.renderers.IAcceleratedRenderer;
import com.github.argon4w.acceleratedrendering.core.meshes.IMesh;
import com.github.argon4w.acceleratedrendering.features.entities.AcceleratedEntityRenderingFeature;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.experimental.ExtensionMethod;
import mod.slashblade.reforged.core.obj.ObjFace;
import mod.slashblade.reforged.core.obj.ObjGroup;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Shadow @Final  private List<ObjFace>            faces;

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

        if (		CoreFeature.isRenderingLevel				()
                &&	AcceleratedEntityRenderingFeature.isEnabled						()
                &&	AcceleratedEntityRenderingFeature	.shouldUseAcceleratedPipeline	()
                &&	extension							.isAccelerated					()
        ) {
            ci			.cancel		();
            PoseStack.Pose pPose = WriteVerticesInfo.getPoseStack().last();
            extension	.doRender	(
                    this,
                    null,
                    pPose.pose  (),
                    pPose.normal(),
                    WriteVerticesInfo.getLightMap(),
                    WriteVerticesInfo.getOverlayMap(),
                    0
            );
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

    }
}
