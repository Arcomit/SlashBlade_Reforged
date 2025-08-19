package mod.slashblade.reforged.content.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 18:06
 * @Description: TODO
 */
public class SBRenderTypes extends RenderStateShard {
    private static final Map<ResourceLocation, RenderType> SLASH_BLADE_BLEND_CACHE = new HashMap<>();

    public static RenderType getSlashBladeBlend(ResourceLocation texture) {
        return SLASH_BLADE_BLEND_CACHE.computeIfAbsent(texture, t -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER)
                    .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                    .setTextureState(new RenderStateShard.TextureStateShard(t, false, true))
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .createCompositeState(true);

            return RenderType.create("slashblade_blend_" + t, DefaultVertexFormat.NEW_ENTITY,
                    VertexFormat.Mode.TRIANGLES, 256, true, false, state);
        });
    }

    public SBRenderTypes(String name, Runnable setupState, Runnable clearState) {
        super(name, setupState, clearState);
    }
}
