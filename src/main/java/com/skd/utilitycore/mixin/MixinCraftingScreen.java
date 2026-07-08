package com.skd.utilitycore.mixin;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.client.PolymorphClientHandler;
import com.skd.utilitycore.polymorph.RecipePair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CraftingScreen.class)
public class MixinCraftingScreen {

    @Inject(method = "extractBackground", at = @At("TAIL"))
    private void onExtractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!Config.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) return;

        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        CraftingMenu menu = (CraftingMenu) screen.getMenu();
        AbstractCraftingMenu craftingMenu = menu;
        CraftingContainer container = ((AccessorCraftingMenu) craftingMenu).utility_core$getCraftSlots();

        PolymorphClientHandler.updateRecipeCache(container, mc);
        List<RecipePair> cachedRecipes = PolymorphClientHandler.getCachedRecipes();

        AccessorAbstractContainerScreen accessor = (AccessorAbstractContainerScreen) (Object) this;
        int selX = accessor.utility_core$getLeftPos() + 155;
        int selY = accessor.utility_core$getTopPos() + 30;
        PolymorphClientHandler.setSelectorPosition(selX, selY);

        if (cachedRecipes.size() > 1) {
            renderRecipeSelector(extractor, mc, mouseX, mouseY, cachedRecipes, selX, selY);
        }
    }

    private static void renderRecipeSelector(GuiGraphicsExtractor extractor, Minecraft mc, int mouseX, int mouseY,
                                              List<RecipePair> recipes, int selX, int selY) {
        int columns = Math.min(4, recipes.size());
        int rows = (int) Math.ceil((double) recipes.size() / columns);
        int selected = PolymorphClientHandler.getSelectedIndex();

        boolean hovering = false;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < columns; i++) {
                int idx = j * columns + i;
                if (idx >= recipes.size()) break;

                int x = selX + i * 18;
                int y = selY + j * 18;

                int bgColor = idx == selected ? 0xFF448844 : 0xFFFFFFFF;
                if (mouseX >= x && mouseX < x + 18 && mouseY >= y && mouseY < y + 18) {
                    bgColor = idx == selected ? 0xFF66AA66 : 0xFFCCCCCC;
                    hovering = true;
                }

                extractor.fill(x, y, x + 18, y + 18, 0xFF000000);
                extractor.fill(x + 1, y + 1, x + 17, y + 17, bgColor);
                extractor.item(recipes.get(idx).output(), x + 1, y + 1);
                extractor.itemDecorations(mc.font, recipes.get(idx).output(), x + 1, y + 1);
            }
        }
        PolymorphClientHandler.setHovering(hovering);
    }
}
