package com.skd.utilitycore.qol.bridging.mixin;

import com.skd.utilitycore.qol.bridging.BridgingConfig;
import com.skd.utilitycore.qol.bridging.compat.BridgingCrosshairTweaks;
import com.skd.utilitycore.qol.bridging.raytrace.PlacementAlignment;
import com.skd.utilitycore.qol.bridging.raytrace.BridgingStateTracker;
import com.skd.utilitycore.qol.bridging.util.GameSupport;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class CrosshairRenderingMixin {

    @Unique
    private static final int ICON_SIZE = 32;

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private DebugScreenOverlay debugOverlay;

    @Inject(method = "extractCrosshair(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V",
            at = @At(value = "TAIL"))
    public void renderPlacementAssistMarker(GuiGraphicsExtractor gui, DeltaTracker deltaTracker, CallbackInfo ci) {
        if(BridgingStateTracker.getLastTickTarget() == null) return;
        if(BridgingCrosshairTweaks.forceHidden) return;
        if(this.minecraft.gameRenderer.gameRenderState().guiRenderState.isHudHidden) return;

        if(!BridgingConfig.SHOW_CROSSHAIR.get()) return;

        boolean isBridgingActive = BridgingConfig.ENABLE_BRIDGING_ASSIST.get() &&
                                   (!BridgingConfig.ONLY_BRIDGE_WHEN_CROUCHED.get() || GameSupport.isControllerCrouching());

        if(!isBridgingActive)
            return;

        Direction direction = BridgingStateTracker.getLastTickTarget().direction();
        PlacementAlignment alignment = PlacementAlignment.from(direction);

        if(alignment == null) return;

        int w = gui.guiWidth();
        int h = gui.guiHeight();

        int x = ((w - ICON_SIZE + 1) / 2);
        int y = ((h - ICON_SIZE + 1) / 2);

        y += BridgingCrosshairTweaks.yShift;
        y += this.debugOverlay.showDebugScreen() ? 15 : 0;

        gui.blitSprite(
                RenderPipelines.CROSSHAIR,
                alignment.getTexturePath(),
                x, y,
                ICON_SIZE, ICON_SIZE
        );


    }

}