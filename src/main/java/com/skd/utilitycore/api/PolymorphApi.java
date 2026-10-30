package com.skd.utilitycore.api;

import com.skd.utilitycore.attachment.ModAttachments;
import com.skd.utilitycore.polymorph.PlayerRecipeData;
import com.skd.utilitycore.polymorph.RecipeFinder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class PolymorphApi {

    public static final PolymorphApi INSTANCE = new PolymorphApi();

    public static PolymorphApi getInstance() {
        return INSTANCE;
    }

    public PlayerRecipeData getPlayerRecipeData(Player player) {
        return player.getData(ModAttachments.PLAYER_RECIPE_DATA);
    }

    public <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipesFor(
            RecipeManager recipeManager, RecipeType<T> recipeType, I input, Level level) {
        return RecipeFinder.getRecipesFor(recipeManager, recipeType, input, level);
    }

    public void clearPlayerRecipeData(Player player) {
        player.getData(ModAttachments.PLAYER_RECIPE_DATA).clear();
    }
}
