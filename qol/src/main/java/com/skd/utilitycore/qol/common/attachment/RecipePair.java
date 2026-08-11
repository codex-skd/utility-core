package com.skd.utilitycore.qol.common.attachment;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public record RecipePair(RecipeHolder<?> recipe, ItemStack output) {

    public static RecipePair of(RecipeHolder<?> recipe, ItemStack output) {
        return new RecipePair(recipe, output);
    }
}
