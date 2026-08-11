package com.skd.utilitycore.qol.common.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AccessorAbstractContainerScreen {

    @Accessor("leftPos")
    int utility_core$getLeftPos();

    @Accessor("topPos")
    int utility_core$getTopPos();
}
