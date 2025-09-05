package mod.slashblade.reforged.content.client.renderer.item.layer;

import com.maydaymemory.mae.basic.BoneTransform;
import com.maydaymemory.mae.basic.Pose;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.slashblade.reforged.content.client.renderer.SbRenderTypes;
import mod.slashblade.reforged.content.client.renderer.item.SlashBladeItemRenderer;
import mod.slashblade.reforged.content.item.SlashBladeItem;
import mod.slashblade.reforged.core.animation.AnimationAsset;
import mod.slashblade.reforged.core.animation.event.AnimationManager;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRenderer;
import mod.slashblade.reforged.core.itemrenderer.DynamicItemRendererRegistry;
import mod.slashblade.reforged.core.obj.ObjModel;
import mod.slashblade.reforged.core.obj.event.ObjModelManager;
import mod.slashblade.reforged.utils.DefaultResources;
import mod.slashblade.reforged.utils.PoseStackAutoCloser;
import mod.slashblade.reforged.utils.WriteVerticesInfo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-19 10:28
 * @Description: 第三人称拔刀剑物品渲染
 */
public class SlashBladeThirdPersonLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T,M> {

    public SlashBladeThirdPersonLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack         poseStack,
                       @NotNull MultiBufferSource bufferSource,
                       int                        packedLight,
                       @NotNull LivingEntity      livingEntity,
                       float                      limbSwing,
                       float                      limbSwingAmount,
                       float                      partialTick,
                       float                      ageInTicks,
                       float                      netHeadYaw,
                       float                      headPitch)
    {
        ItemStack itemStack = livingEntity.getMainHandItem();
        if (itemStack.isEmpty()) return;

        @Nullable
        DynamicItemRenderer renderer = DynamicItemRendererRegistry.INSTANCE.get(itemStack.getItem());
        if (renderer != null && renderer instanceof SlashBladeItemRenderer bladeRenderer) {
            bladeRenderer.renderThirdPerson(
                    poseStack,
                    bufferSource,
                    packedLight,
                    livingEntity,
                    itemStack,
                    limbSwing,
                    limbSwingAmount,
                    partialTick,
                    ageInTicks,
                    netHeadYaw,
                    headPitch);
        }
    }
}
