package com.skd.utilitycore.client;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import com.skd.utilitycore.mixin.AccessorAbstractContainerScreen;
import com.skd.utilitycore.mixin.AccessorCraftingMenu;
import com.skd.utilitycore.mixin.MixinCraftingScreen;
import com.skd.utilitycore.network.SelectRecipePacket;
import com.skd.utilitycore.polymorph.RecipeFinder;
import com.skd.utilitycore.polymorph.RecipePair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = UtilityCore.MODID, value = Dist.CLIENT)
public class PolymorphClientHandler {

    private static final List<RecipePair> cachedRecipes = new ArrayList<>();
    private static final List<ItemStack> lastInputs = new ArrayList<>();
    private static int selectorX;
    private static int selectorY;
    private static boolean hovering = false;

    @SubscribeEvent
    public static void onMousePressed(ScreenEvent.MouseButtonPressed.Pre event) {
        if (!(event.getScreen() instanceof CraftingScreen screen)) return;
        if (!Config.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) return;

        double mouseX = event.getMouseX();
        double mouseY = event.getMouseY();
        int button = event.getButton();

        if (button == 0 && cachedRecipes.size() > 1 && hovering) {
            int column = ((int) mouseX - selectorX) / 18;
            int row = ((int) mouseY - selectorY) / 18;
            int index = row * 4 + column;
            if (index >= 0 && index < cachedRecipes.size()) {
                ClientPacketDistributor.sendToServer(new SelectRecipePacket(index));
                event.setCanceled(true);
            }
        }
    }

    public static void updateRecipeCache(CraftingContainer container, Minecraft mc) {
        if (mc.level == null) {
            cachedRecipes.clear();
            return;
        }
        CraftingInput input = container.asCraftInput();

        List<ItemStack> inputs = captureInputs(container);
        if (inputsChanged(lastInputs, inputs)) {
            List<RecipeHolder<CraftingRecipe>> recipes;
            try {
                RecipeManager rm = (RecipeManager) mc.level.recipeAccess();
                recipes = RecipeFinder.getRecipesFor(rm, RecipeType.CRAFTING, input, mc.level);
            } catch (Exception e) {
                UtilityCore.LOGGER.error("Error finding recipes: {}", e.getMessage());
                cachedRecipes.clear();
                lastInputs.clear();
                return;
            }
            int max = Config.MAX_RECIPES_DISPLAYED.get();
            cachedRecipes.clear();
            for (RecipeHolder<CraftingRecipe> holder : recipes) {
                if (cachedRecipes.size() >= max) break;
                ItemStack output = holder.value().assemble(input);
                cachedRecipes.add(RecipePair.of(holder, output.copy()));
            }
            lastInputs.clear();
            lastInputs.addAll(inputs);
        }
    }

    public static List<RecipePair> getCachedRecipes() {
        return cachedRecipes;
    }

    public static void setSelectorPosition(int x, int y) {
        selectorX = x;
        selectorY = y;
    }

    public static void setHovering(boolean h) {
        hovering = h;
    }

    public static List<ItemStack> captureInputs(CraftingContainer container) {
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            inputs.add(container.getItem(i).copy());
        }
        return inputs;
    }

    public static boolean inputsChanged(List<ItemStack> oldInputs, List<ItemStack> newInputs) {
        if (oldInputs.size() != newInputs.size()) return true;
        for (int i = 0; i < oldInputs.size(); i++) {
            if (!ItemStack.matches(oldInputs.get(i), newInputs.get(i))) return true;
        }
        return false;
    }
}
