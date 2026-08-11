package com.skd.utilitycore.qol.bridging.mixin;

import com.skd.utilitycore.qol.bridging.BridgingConfig;
import com.skd.utilitycore.qol.bridging.util.GameSupport;
import com.skd.utilitycore.qol.bridging.util.render.Render;
import com.skd.utilitycore.qol.bridging.raytrace.BridgingPreContext;
import com.skd.utilitycore.qol.bridging.raytrace.Perspective;
import com.skd.utilitycore.qol.bridging.util.flags.Flags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
           
import com.skd.utilitycore.qol.bridging.BridgingConfig;
           
import com.skd.utilitycore.qol.bridging.util.GameSupport;
           
import com.skd.utilitycore.qol.bridging.util.render.Render;
           
import com.skd.utilitycore.qol.bridging.raytrace.BridgingPreContext;
           
import com.skd.utilitycore.qol.bridging.raytrace.Perspective;
           
import com.skd.utilitycore.qol.bridging.util.flags.Flags;
           
import com.mojang.blaze3d.vertex.PoseStack;
           
import net.minecraft.client.Minecraft;
           
import net.minecraft.client.renderer.*;
           
import net.minecraft.client.renderer.state.level.LevelRenderState;
           
import net.minecraft.world.entity.player.Player;
           
import org.spongepowered.asm.mixin.Mixin;
           
import org.spongepowered.asm.mixin.Shadow;
           
import org.spongepowered.asm.mixin.injection.At;
           
import org.spongepowered.asm.mixin.injection.Inject;
           
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class OutlineRendererMixin {

    @Shadow protected abstract void checkPoseStack(PoseStack poseStack);

    @Inject(method = "submitBlockOutline",
            at = @At("HEAD")
            )
    public void renderTracedViewPath(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LevelRenderState levelRenderState, CallbackInfo ci) {
        boolean isInDebugMenu = Minecraft.getInstance().getDebugOverlay().showDebugScreen();

        // Rules to display any bridging - whether these are followed or not depends on the config :)
        boolean isBridgingEnabled = BridgingConfig.ENABLE_BRIDGING_ASSIST.get() &&
                                    (!BridgingConfig.ONLY_BRIDGE_WHEN_CROUCHED.get() || GameSupport.isControllerCrouching());

        boolean shouldRenderOutline = (isInDebugMenu  && BridgingConfig.SHOW_OUTLINE.get()) ||
                                      (!isInDebugMenu && BridgingConfig.SHOW_OUTLINE.get());
        boolean isOutlineEnabled = shouldRenderOutline && isBridgingEnabled;

        boolean shouldRenderNonBridgeOutline = (isInDebugMenu  && BridgingConfig.SHOW_NON_BRIDGING_DEBUG_HIGHLIGHT.get()) ||
                                               (!isInDebugMenu && BridgingConfig.SHOW_OUTLINE_EVEN_WHEN_NOT_BRIDGING.get());
        boolean isNonBridgeOutlineEnabled = shouldRenderNonBridgeOutline &&
                                            (isBridgingEnabled || !BridgingConfig.NON_BRIDGE_RESPECTS_CROUCH_RULES.get());

        // Skip if nothing is valid to render.
        if(!(isOutlineEnabled || isNonBridgeOutlineEnabled))
            return;

        Player player = Minecraft.getInstance().player;

        // There may be a few cases where this would be useful to still show bridging for,
        // but that makes headaches.
        if (player == null)
            return;

        Perspective view = Perspective.getSourcePerspective(player);

        BridgingPreContext preContext = new BridgingPreContext(
                player.level(),
                view,
                Perspective.fromEntity(player),
                player,
                Flags.empty()
        );

        if(isInDebugMenu && BridgingConfig.SHOW_DEBUG_TRACE.get())
            Render.blocksInViewPath(poseStack, submitNodeCollector, preContext);

        if(isOutlineEnabled) Render.currentBridgingOutline(poseStack, submitNodeCollector, 0f);
        if(isNonBridgeOutlineEnabled) Render.currentNonBridgingOutline(poseStack, view, submitNodeCollector);

        this.checkPoseStack(poseStack);
    }

}