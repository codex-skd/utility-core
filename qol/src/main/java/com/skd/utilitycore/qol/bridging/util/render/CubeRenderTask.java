package com.skd.utilitycore.qol.bridging.util.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.skd.utilitycore.qol.bridging.raytrace.Perspective;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.core.BlockPos;

@FunctionalInterface
public interface CubeRenderTask {

    void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Perspective view, BlockPos pos, int argbColor);

}
