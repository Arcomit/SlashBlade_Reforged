package mod.slashblade.reforged.content.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 18:06
 * @Description: 集中管理所有模组渲染类型
 */
public class SbRenderTypes extends RenderStateShard {

    private static final Map<ResourceLocation, RenderType> SLASH_BLADE_BLEND_CACHE = new WeakHashMap<>();

    public static RenderType getBlend(ResourceLocation texture) {
        return SLASH_BLADE_BLEND_CACHE.computeIfAbsent(texture, tex -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState      (RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER)
                    .setOutputState      (ITEM_ENTITY_TARGET)
                    .setTextureState     (new TextureStateShard(tex, false, true))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState    (LIGHTMAP)
                    .setOverlayState     (OVERLAY)
                    .setWriteMaskState   (COLOR_DEPTH_WRITE)
                    .createCompositeState(true);

            return RenderType.create(
                    "slashblade_blend",
                    DefaultVertexFormat.NEW_ENTITY,
                    VertexFormat.Mode  .TRIANGLES,
                    256, true, false, state
            );
        });
    }

    public SbRenderTypes(String name, Runnable setupState, Runnable clearState) {
        super(name, setupState, clearState);
    }
}
