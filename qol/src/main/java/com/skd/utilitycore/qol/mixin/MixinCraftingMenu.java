package com.skd.utilitycore.qol.mixin;

import com.skd.utilitycore.qol.common.attachment.ModAttachments;
import com.skd.utilitycore.qol.common.network.SyncRecipesPacket;
import com.skd.utilitycore.qol.common.attachment.PlayerRecipeData;
import com.skd.utilitycore.qol.common.polymorph.RecipeFinder;
import com.skd.utilitycore.qol.common.attachment.RecipePair;
import com.skd.utilitycore.qol.config.QoLConfig;
import com.skd.utilitycore.qol.UtilityCoreQoL;
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
        if (!QoLConfig.ENABLE_CRAFTING_RECIPE_SELECTOR.get()) {
            return;
        }

        if (menu != player.containerMenu) {
            return;
        }

        RecipeManager rm = level.recipeAccess();
        CraftingInput input = container.asCraftInput();

        List<RecipeHolder<CraftingRecipe>> allRecipes;
        try {
            allRecipes = RecipeFinder.getRecipesFor(rm, RecipeType.CRAFTING, input, level);
        } catch (Exception e) {
            UtilityCoreQoL.LOGGER.error("[UtilityCoreQoL] slotChangedCraftingGrid recipe lookup failed: {}", e.getMessage(), e);
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
        boolean logConflicts = QoLConfig.LOG_DETECTED_CONFLICTS.get();

        ItemStack finalResult;

        if (allRecipes.isEmpty()) {
            finalResult = ItemStack.EMPTY;
            sendSyncPacket(player, List.of(), inputs);
            player.getData(ModAttachments.getPlayerRecipeData().get()).clear();
        } else if (allRecipes.size() == 1) {
            sendSyncPacket(player, List.of(), inputs);
            player.getData(ModAttachments.getPlayerRecipeData().get()).clear();
            RecipeHolder<CraftingRecipe> single = allRecipes.get(0);
            result.setRecipeUsed(single);
            finalResult = single.value().assemble(input);
        } else {
            sendSyncPacket(player, outputs, inputs);

            PlayerRecipeData data = player.getData(ModAttachments.getPlayerRecipeData().get());

            if (data.inputsChanged(inputs)) {
                data.setRecipes(pairs, inputs);
                if (logConflicts) {
                    UtilityCoreQoL.LOGGER.info("[RecipeSelector] conflict detected: {} matching recipes, inputs changed", pairs.size());
                }
            } else if (data.getRecipeList().isEmpty()) {
                data.setRecipes(pairs, inputs);
                if (logConflicts) {
                    UtilityCoreQoL.LOGGER.info("[RecipeSelector] conflict detected: {} matching recipes", pairs.size());
                }
            }

            RecipePair selected = data.getSelectedRecipe();
            RecipeHolder<CraftingRecipe> holderToUse;

            if (selected != null && allRecipes.stream().anyMatch(
                    r -> r.id().equals(((RecipeHolder<?>) selected.recipe()).id()))) {
                holderToUse = (RecipeHolder<CraftingRecipe>) (RecipeHolder<?>) selected.recipe();
            } else {
                holderToUse = allRecipes.get(0);
                data.setRecipes(pairs, inputs);
                if (logConflicts) {
                    UtilityCoreQoL.LOGGER.info("[RecipeSelector] selected recipe not in current list -> falling back to {}", holderToUse.id());
                }
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