package com.skd.utilitycore.client;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import com.skd.utilitycore.network.SelectRecipePacket;
import com.skd.utilitycore.polymorph.RecipeFinder;
import com.skd.utilitycore.polymorph.RecipePair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeAccess;
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
    private static int selectedIndex = 0;

    @SubscribeEvent
    public static void onScreenClosed(ScreenEvent.Closing event) {
        if (event.getScreen() instanceof CraftingScreen || event.getScreen() instanceof InventoryScreen) {
            cachedRecipes.clear();
            lastInputs.clear();
            selectedIndex = 0;
        }
    }

    @SubscribeEvent
    public static void onMousePressed(ScreenEvent.MouseButtonPressed.Pre event) {
        if (!(event.getScreen() instanceof CraftingScreen) && !(event.getScreen() instanceof InventoryScreen)) return;
        if (!Config.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) return;

        double mouseX = event.getMouseX();
        double mouseY = event.getMouseY();
        int button = event.getButton();

        if (button == 0 && cachedRecipes.size() > 1 && hovering) {
            int column = ((int) mouseX - selectorX) / 18;
            int row = ((int) mouseY - selectorY) / 18;
            int index = row * 4 + column;
            if (index >= 0 && index < cachedRecipes.size()) {
                selectedIndex = index;
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
        RecipeManager rm = getRecipeManager(mc);
        if (rm == null) {
            return;
        }
        CraftingInput input = container.asCraftInput();
        List<ItemStack> inputs = captureInputs(container);
        if (inputsChanged(lastInputs, inputs)) {
            List<RecipeHolder<CraftingRecipe>> recipes;
            try {
                recipes = RecipeFinder.getRecipesFor(rm, RecipeType.CRAFTING, input, mc.level);
            } catch (Exception e) {
                UtilityCore.LOGGER.error("Error finding recipes: {}", e.getMessage());
                cachedRecipes.clear();
                lastInputs.clear();
                return;
            }
            UtilityCore.LOGGER.debug("[UtilityCore] updateRecipeCache: found {} recipes", recipes.size());
            setRecipeOutputs(recipes, input);
            lastInputs.clear();
            lastInputs.addAll(inputs);
        }
    }

    private static void setRecipeOutputs(List<RecipeHolder<CraftingRecipe>> recipes, CraftingInput input) {
        int max = Config.MAX_RECIPES_DISPLAYED.get();
        cachedRecipes.clear();
        for (RecipeHolder<CraftingRecipe> holder : recipes) {
            if (cachedRecipes.size() >= max) break;
            ItemStack output = holder.value().assemble(input);
            cachedRecipes.add(RecipePair.of(holder, output.copy()));
        }
    }

    public static void receiveServerRecipes(List<ItemStack> outputs) {
        cachedRecipes.clear();
        for (ItemStack stack : outputs) {
            cachedRecipes.add(RecipePair.of(null, stack));
        }
        if (selectedIndex >= cachedRecipes.size()) {
            selectedIndex = 0;
        }
    }

    public static int getSelectedIndex() {
        return selectedIndex;
    }

    public static void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    private static RecipeManager getRecipeManager(Minecraft mc) {
        var integratedServer = mc.getSingleplayerServer();
        if (integratedServer != null) {
            return integratedServer.getRecipeManager();
        }
        RecipeAccess recipeAccess = mc.level.recipeAccess();
        if (recipeAccess instanceof RecipeManager rm) {
            return rm;
        }
        return null;
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

    public static ItemStack renderRecipeSelector(GuiGraphicsExtractor extractor, Minecraft mc, int mouseX, int mouseY,
                                                  int selX, int selY) {
        List<RecipePair> recipes = cachedRecipes;
        if (recipes.size() <= 1) return ItemStack.EMPTY;
        int columns = Math.min(4, recipes.size());
        int rows = (int) Math.ceil((double) recipes.size() / columns);

        boolean hoveringNow = false;
        ItemStack hoveredStack = ItemStack.EMPTY;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < columns; i++) {
                int idx = j * columns + i;
                if (idx >= recipes.size()) break;

                int x = selX + i * 18;
                int y = selY + j * 18;

                int bgColor = idx == selectedIndex ? 0xFF448844 : 0xFFFFFFFF;
                if (mouseX >= x && mouseX < x + 18 && mouseY >= y && mouseY < y + 18) {
                    bgColor = idx == selectedIndex ? 0xFF66AA66 : 0xFFCCCCCC;
                    hoveringNow = true;
                    hoveredStack = recipes.get(idx).output();
                }

                extractor.fill(x, y, x + 18, y + 18, 0xFF000000);
                extractor.fill(x + 1, y + 1, x + 17, y + 17, bgColor);
                extractor.item(recipes.get(idx).output(), x + 1, y + 1);
                extractor.itemDecorations(mc.font, recipes.get(idx).output(), x + 1, y + 1);
            }
        }
        hovering = hoveringNow;
        return hoveredStack;
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
