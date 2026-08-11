package com.skd.utilitycore.qol.mixin;

import com.skd.utilitycore.qol.config.QoLConfig;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Hud.class)
public class MixinHud {

    @ModifyArg(
        method = "extractTitle(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V",
        at = @At(value = "INVOKE", target = "Lorg/joml/Matrix3x2fStack;translate(FF)Lorg/joml/Matrix3x2f;", ordinal = 0),
        index = 1
    )
    private float modifyTitleTranslateY(float y) {
        return y - (float) QoLConfig.TITLE_VERTICAL_OFFSET.get();
    }
}