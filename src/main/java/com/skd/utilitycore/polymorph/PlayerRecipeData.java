package com.skd.utilitycore.polymorph;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class PlayerRecipeData {

    private final List<RecipePair> recipeList = new ArrayList<>();
    private int selectedIndex = 0;
    private List<ItemStack> lastInputs = List.of();

    public void setRecipes(List<RecipePair> pairs, List<ItemStack> inputs) {
        recipeList.clear();
        recipeList.addAll(pairs);
        this.lastInputs = inputs;
        if (selectedIndex >= recipeList.size()) {
            selectedIndex = 0;
        }
    }

    public List<RecipePair> getRecipeList() {
        return recipeList;
    }

    public RecipePair getSelectedRecipe() {
        if (recipeList.isEmpty()) {
            return null;
        }
        if (selectedIndex >= recipeList.size()) {
            selectedIndex = 0;
        }
        return recipeList.get(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < recipeList.size()) {
            this.selectedIndex = index;
        }
    }

    public void cycleSelection() {
        if (recipeList.size() > 1) {
            selectedIndex = (selectedIndex + 1) % recipeList.size();
        }
    }

    public boolean hasMultipleRecipes() {
        return recipeList.size() > 1;
    }

    public void clear() {
        recipeList.clear();
        selectedIndex = 0;
        lastInputs = List.of();
    }

    public boolean inputsChanged(List<ItemStack> newInputs) {
        if (lastInputs.size() != newInputs.size()) {
            return true;
        }
        for (int i = 0; i < lastInputs.size(); i++) {
            if (!ItemStack.matches(lastInputs.get(i), newInputs.get(i))) {
                return true;
            }
        }
        return false;
    }
}
