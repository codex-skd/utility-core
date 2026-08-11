package com.skd.utilitycore.qol.mixin;

import com.skd.utilitycore.qol.client.PolymorphClientHandler;
import com.skd.utilitycore.qol.config.QoLConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class MixinInventoryScreen {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void onExtractRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!QoLConfig.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) return;

        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        InventoryMenu menu = (InventoryMenu) screen.getMenu();
        CraftingContainer container = menu.getCraftSlots();

        PolymorphClientHandler.updateRecipeCache(container, mc);

        com.skd.utilitycore.qol.common.mixin.AccessorAbstractContainerScreen accessor = (com.skd.utilitycore.qol.common.mixin.AccessorAbstractContainerScreen) (Object) this;
        int selX = accessor.utility_core$getLeftPos() + 172;
        int selY = accessor.utility_core$getTopPos() + 27;
        PolymorphClientHandler.setSelectorPosition(selX, selY);

        ItemStack hovered = PolymorphClientHandler.renderRecipeSelector(extractor, mc, mouseX, mouseY, selX, selY);
        if (!hovered.isEmpty()) {
            extractor.setTooltipForNextFrame(mc.font, hovered.getHoverName(), mouseX, mouseY);
        }
    }
}