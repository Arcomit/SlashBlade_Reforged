package mod.slashblade.reforged.core.obj.acceleratedrendering.mixin;

import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.renderers.IAcceleratedRenderer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-07 09:14
 * @Description: TODO:未完成
 */
@Mixin(Object.class)
public class ObjFaceMixin implements IAcceleratedRenderer<Void> {

    @Override
    public void render(VertexConsumer vertexConsumer, Void unused, Matrix4f matrix4f, Matrix3f matrix3f, int i, int i1, int i2) {

    }
}
