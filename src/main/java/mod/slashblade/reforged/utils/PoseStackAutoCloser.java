package mod.slashblade.reforged.utils;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-25 11:40
 * @Description: TODO
 */
public class PoseStackAutoCloser implements AutoCloseable {

    static public PoseStackAutoCloser pushMatrix(PoseStack ms) {
        return new PoseStackAutoCloser(ms);
    }

    PoseStack poseStack;

    PoseStackAutoCloser(PoseStack poseStack) {
        this.poseStack = poseStack;
        this.poseStack.pushPose();
    }

    @Override
    public void close() {
        this.poseStack.popPose();
    }
}