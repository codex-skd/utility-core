package com.skd.utilitycore.polymorph;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecipeFinder {

    public static <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipesFor(
            RecipeManager recipeManager, RecipeType<T> recipeType, I input, Level level) {
        List<RecipeHolder<T>> results = new ArrayList<>();
        for (RecipeHolder<?> holder : recipeManager.getRecipes()) {
            if (holder.value().getType() == recipeType) {
                @SuppressWarnings("unchecked")
                RecipeHolder<T> typed = (RecipeHolder<T>) holder;
                if (typed.value().matches(input, level)) {
                    results.add(typed);
                }
            }
        }
        results.sort(Comparator.comparing(r -> r.id().toString()));
        return results;
    }
}
