package com.skd.utilitycore.mixin;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.attachment.ModAttachments;
import com.skd.utilitycore.network.SyncRecipesPacket;
import com.skd.utilitycore.polymorph.PlayerRecipeData;
import com.skd.utilitycore.polymorph.RecipeFinder;
import com.skd.utilitycore.polymorph.RecipePair;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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
    private static void onSlotChangedCraftingGrid(AbstractContainerMenu menu, ServerLevel level, Player player,
                                                   CraftingContainer container, ResultContainer result,
                                                   RecipeHolder<CraftingRecipe> recipe, CallbackInfo ci) {
        com.skd.utilitycore.UtilityCore.LOGGER.info("[UtilityCore] slotChangedCraftingGrid called");
        if (!Config.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) {
            com.skd.utilitycore.UtilityCore.LOGGER.info("[UtilityCore] Recipe selector is DISABLED");
            return;
        }

        RecipeManager rm = level.recipeAccess();
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
        List<ItemStack> inputs = captureInputs(container);

        com.skd.utilitycore.UtilityCore.LOGGER.info("[UtilityCore] Recipe calculation: {} recipes found for input {}",
            allRecipes.size(), inputs.stream().map(i -> i.getCount() + "x" + i.getHoverName()).toList());

        ItemStack finalResult;

        if (allRecipes.isEmpty()) {
            com.skd.utilitycore.UtilityCore.LOGGER.info("[UtilityCore] No recipes found");
            finalResult = ItemStack.EMPTY;
            sendSyncPacket(player, List.of(), inputs);
            player.getData(ModAttachments.PLAYER_RECIPE_DATA).clear();
        } else if (allRecipes.size() == 1) {
            com.skd.utilitycore.UtilityCore.LOGGER.info("[UtilityCore] Only 1 recipe found");
            sendSyncPacket(player, List.of(), inputs);
            player.getData(ModAttachments.PLAYER_RECIPE_DATA).clear();
            RecipeHolder<CraftingRecipe> single = allRecipes.get(0);
            result.setRecipeUsed(single);
            finalResult = single.value().assemble(input);
        } else {
            com.skd.utilitycore.UtilityCore.LOGGER.debug("[UtilityCore] Multiple recipes detected: {} variants, syncing to player", allRecipes.size());
            sendSyncPacket(player, outputs, inputs);

            PlayerRecipeData data = player.getData(ModAttachments.PLAYER_RECIPE_DATA);

            if (data.inputsChanged(inputs)) {
                com.skd.utilitycore.UtilityCore.LOGGER.debug("[UtilityCore] Inputs changed, updating recipe list");
                data.setRecipes(pairs, inputs);
            } else if (data.getRecipeList().isEmpty()) {
                com.skd.utilitycore.UtilityCore.LOGGER.debug("[UtilityCore] Recipe list empty, initializing");
                data.setRecipes(pairs, inputs);
            }

            int selectedIndex = data.getSelectedIndex();
            com.skd.utilitycore.UtilityCore.LOGGER.debug("[UtilityCore] Current selection: {}/{}", selectedIndex, data.getRecipeList().size());

            RecipePair selected = data.getSelectedRecipe();
            RecipeHolder<CraftingRecipe> holderToUse;

            if (selected != null && allRecipes.stream().anyMatch(
                    r -> r.id().equals(((RecipeHolder<?>) selected.recipe()).id()))) {
                com.skd.utilitycore.UtilityCore.LOGGER.debug("[UtilityCore] Using selected recipe at index {}", selectedIndex);
                holderToUse = (RecipeHolder<CraftingRecipe>) (RecipeHolder<?>) selected.recipe();
            } else {
                com.skd.utilitycore.UtilityCore.LOGGER.warn("[UtilityCore] Selected recipe invalid or not found, falling back to first recipe");
                holderToUse = allRecipes.get(0);
                data.setRecipes(pairs, inputs);
            }

            result.setRecipeUsed(holderToUse);
            finalResult = holderToUse.value().assemble(input);
        }

        result.setItem(0, finalResult);
        menu.setRemoteSlot(0, finalResult);
        if (player instanceof ServerPlayer sp) {
            sp.connection.send(new ClientboundContainerSetSlotPacket(
                    menu.containerId, menu.incrementStateId(), 0, finalResult));
        }
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
    private static void sendSyncPacket(Player player, List<ItemStack> outputs, List<ItemStack> inputs) {
        if (player instanceof ServerPlayer sp) {
            PacketDistributor.sendToPlayer(sp, new SyncRecipesPacket(outputs, inputs));
        }
    }
}