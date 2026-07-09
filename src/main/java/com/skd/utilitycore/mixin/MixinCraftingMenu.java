package com.skd.utilitycore.mixin;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.attachment.ModAttachments;
import com.skd.utilitycore.network.SyncRecipesPacket;
import com.skd.utilitycore.polymorph.PlayerRecipeData;
import com.skd.utilitycore.polymorph.RecipeFinder;
import com.skd.utilitycore.polymorph.RecipePair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(CraftingMenu.class)
public class MixinCraftingMenu {

    @Inject(method = "slotChangedCraftingGrid", at = @At("HEAD"), cancellable = true)
    private static void onSlotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player,
                                                   CraftingContainer container, ResultContainer result,
                                                   RecipeHolder<CraftingRecipe> recipe, CallbackInfo ci) {
        if (!Config.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) {
            return;
        }

        RecipeManager rm = ((ServerLevel) level).recipeAccess();
        CraftingInput input = container.asCraftInput();

        List<RecipeHolder<CraftingRecipe>> allRecipes;
        try {
            allRecipes = RecipeFinder.getRecipesFor(rm, RecipeType.CRAFTING, input, level);
        } catch (Exception e) {
            com.skd.utilitycore.UtilityCore.LOGGER.error("[UtilityCore] slotChangedCraftingGrid recipe lookup failed: {}", e.getMessage(), e);
            return;
        }

        List<ItemStack> outputs = new ArrayList<>();
        List<RecipePair> pairs = new ArrayList<>();
        for (RecipeHolder<CraftingRecipe> holder : allRecipes) {
            ItemStack output = holder.value().assemble(input);
            pairs.add(RecipePair.of(holder, output.copy()));
            outputs.add(output.copy());
        }

        if (allRecipes.isEmpty()) {
            result.setItem(0, ItemStack.EMPTY);
            menu.setRemoteSlot(0, ItemStack.EMPTY);
            ci.cancel();
            return;
        }

        sendSyncPacket(player, outputs);

        if (allRecipes.size() == 1) {
            RecipeHolder<CraftingRecipe> single = allRecipes.get(0);
            ItemStack assembled = single.value().assemble(input);
            result.setItem(0, assembled);
            result.setRecipeUsed(single);
            menu.setRemoteSlot(0, assembled);
            ci.cancel();
            return;
        }

        PlayerRecipeData data = player.getData(ModAttachments.PLAYER_RECIPE_DATA);
        List<ItemStack> inputs = captureInputs(container);

        if (data.inputsChanged(inputs)) {
            data.setRecipes(pairs, inputs);
        }

        RecipePair selected = data.getSelectedRecipe();
        RecipeHolder<CraftingRecipe> holderToUse;

        if (selected != null && allRecipes.stream().anyMatch(
                r -> r.id().equals(((RecipeHolder<?>) selected.recipe()).id()))) {
            holderToUse = (RecipeHolder<CraftingRecipe>) (RecipeHolder<?>) selected.recipe();
        } else {
            holderToUse = allRecipes.get(0);
            data.setRecipes(pairs, inputs);
        }

        ItemStack assembled = holderToUse.value().assemble(input);
        result.setItem(0, assembled);
        result.setRecipeUsed(holderToUse);
        menu.setRemoteSlot(0, assembled);

        ci.cancel();
    }

    @Unique
    private static List<ItemStack> captureInputs(CraftingContainer container) {
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            inputs.add(container.getItem(i).copy());
        }
        return inputs;
    }

    @Unique
    private static void sendSyncPacket(Player player, List<ItemStack> outputs) {
        if (player instanceof ServerPlayer sp) {
            PacketDistributor.sendToPlayer(sp, new SyncRecipesPacket(outputs));
        }
    }
}
