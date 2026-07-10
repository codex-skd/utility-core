package com.skd.utilitycore.mixin;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.client.PolymorphClientHandler;
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

    @Inject(method = "extractBackground", at = @At("TAIL"))
    private void onExtractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!Config.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) return;

        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        InventoryMenu menu = (InventoryMenu) screen.getMenu();
        CraftingContainer container = menu.getCraftSlots();

        PolymorphClientHandler.updateRecipeCache(container, mc);

        AccessorAbstractContainerScreen accessor = (AccessorAbstractContainerScreen) (Object) this;
        int selX = accessor.utility_core$getLeftPos() + 124;
        int selY = accessor.utility_core$getTopPos() + 30;
        PolymorphClientHandler.setSelectorPosition(selX, selY);

        ItemStack hovered = PolymorphClientHandler.renderRecipeSelector(extractor, mc, mouseX, mouseY, selX, selY);
        if (!hovered.isEmpty()) {
            extractor.setTooltipForNextFrame(mc.font, hovered.getHoverName(), mouseX, mouseY);
        }
    }
}