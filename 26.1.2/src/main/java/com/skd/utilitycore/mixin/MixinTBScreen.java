package com.skd.utilitycore.mixin;

import com.skd.utilitycore.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class MixinTBScreen {

    @Unique
    private static int utilityCore$savedGuiScale = -1;

    @Unique
    private static boolean utilityCore$isTombstoneScreen(Screen screen) {
        if (screen == null) return false;
        for (Class<?> c = screen.getClass(); c != Screen.class && c != null; c = c.getSuperclass()) {
            if ("ovh.corail.tombstone.gui.TBScreen".equals(c.getName())) {
                return true;
            }
        }
        return false;
    }

    @Inject(method = "init(Lnet/minecraft/client/Minecraft;II)V", at = @At("HEAD"))
    private void onScreenInit(Minecraft mc, int width, int height, CallbackInfo ci) {
        if (!Config.ENABLE_TOMBSTONE_GUI_SCALE_FIX.get()) return;
        Screen self = (Screen) (Object) this;
        if (utilityCore$isTombstoneScreen(self)) {
            utilityCore$savedGuiScale = mc.options.guiScale().get();
        }
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void onScreenRemoved(CallbackInfo ci) {
        if (!Config.ENABLE_TOMBSTONE_GUI_SCALE_FIX.get()) return;
        Screen self = (Screen) (Object) this;
        if (utilityCore$isTombstoneScreen(self) && utilityCore$savedGuiScale > 0) {
            Minecraft mc = Minecraft.getInstance();
            int currentScale = mc.options.guiScale().get();
            if (currentScale != utilityCore$savedGuiScale) {
                mc.options.guiScale().set(utilityCore$savedGuiScale);
                mc.resizeGui();
            }
            utilityCore$savedGuiScale = -1;
        }
    }
}