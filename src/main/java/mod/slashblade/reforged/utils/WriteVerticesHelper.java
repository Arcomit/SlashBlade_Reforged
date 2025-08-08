package mod.slashblade.reforged.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.joml.Vector4f;

import java.awt.*;
import java.util.function.BiFunction;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-06 14:59
 * @Description: 写入顶点帮助类
 */
public class WriteVerticesHelper {

    public static final BiFunction<Vector4f, Integer, Integer> ALPHA_NO_OVERRIDE   = (v, a) -> a;
    public static final BiFunction<Vector4f, Integer, Integer> ALPHA_OVERRIDEYZZ   = (v, a) -> v.y() == 0 ? 0 : a;
    public static final Vector4f                               UV_DEFAULT_OPERATOR = new Vector4f(1, 1, 0, 0);

    @Getter@Setter private static Color                                  color         = Color.WHITE;
    @Getter@Setter private static int                                    lightMap      = LightTexture.FULL_BRIGHT;
    @Getter@Setter private static int                                    overlayMap    = OverlayTexture.NO_OVERLAY;
    @Getter@Setter private static PoseStack                              poseStack;
    @Getter@Setter private static BiFunction<Vector4f, Integer, Integer> alphaOverride = ALPHA_NO_OVERRIDE;
    @Getter@Setter private static Vector4f                               uvOperator    = UV_DEFAULT_OPERATOR;

    public static void resetColor(){ color = Color.WHITE; }

    public static void resetAlphaOverride() { alphaOverride = ALPHA_NO_OVERRIDE; }

    public static void setUvOperator(float uScale, float vScale, float uOffset, float vOffset) {
        uvOperator = new Vector4f(uScale, vScale, uOffset, vOffset);
    }
    public static void resetUvOperator() { uvOperator = UV_DEFAULT_OPERATOR; }

    public static void resetLightMap(){ lightMap = LightTexture.FULL_BRIGHT; }

    public static void resetOverlayMap(){ overlayMap = OverlayTexture.NO_OVERLAY;}

    public static void resetPoseStack(){ poseStack = null; }
}
