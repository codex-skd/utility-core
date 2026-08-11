package com.skd.utilitycore.qol.network;

import com.skd.utilitycore.qol.common.attachment.ModAttachments;
import com.skd.utilitycore.qol.common.attachment.PlayerRecipeData;
import com.skd.utilitycore.qol.common.attachment.RecipePair;
import com.skd.utilitycore.qol.common.network.SelectRecipePacket;
import com.skd.utilitycore.qol.UtilityCoreQoL;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SelectRecipePacketHandler {

    public static void handle(SelectRecipePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            PlayerRecipeData data = player.getData(ModAttachments.getPlayerRecipeData().get());

            UtilityCoreQoL.LOGGER.info("[RecipeSelector][S] handle SelectRecipePacket: index={} data.size={} data.selectedIndex={}",
                    packet.selectedIndex(), data.getRecipeList().size(), data.getSelectedIndex());

            if (packet.selectedIndex() >= 0 && packet.selectedIndex() < data.getRecipeList().size()) {
                data.setSelectedIndex(packet.selectedIndex());
                RecipePair selected = data.getSelectedRecipe();
                AbstractContainerMenu menu = player.containerMenu;

                UtilityCoreQoL.LOGGER.info("[RecipeSelector][S] after setSelectedIndex({}): data.selectedIndex={} selected={} menu={}",
                        packet.selectedIndex(), data.getSelectedIndex(), selected != null ? selected.output() : null, menu);

                if (selected != null && menu instanceof AbstractCraftingMenu acm) {
                    ItemStack resultCurrent = menu.getSlot(0).getItem();
                    UtilityCoreQoL.LOGGER.info("[RecipeSelector][S] result slot before: {} | applying selected output {}",
                            resultCurrent, selected.output());

                    Slot resultSlot = menu.getSlot(0);
                    resultSlot.set(selected.output());
                    ResultContainer resultContainer = resultSlot.container instanceof ResultContainer rc ? rc : null;
                    if (resultContainer != null) {
                        resultContainer.setRecipeUsed(selected.recipe());
                    }
                    menu.setRemoteSlot(0, selected.output());
                    if (player instanceof ServerPlayer sp) {
                        sp.connection.send(new ClientboundContainerSetSlotPacket(
                                menu.containerId, menu.incrementStateId(), 0, selected.output()));
                    }
                    UtilityCoreQoL.LOGGER.info("[RecipeSelector][S] applied selection: slot0 actual={} recipeUsed={} -> broadcastChanges() no longer reverts",
                            menu.getSlot(0).getItem(),
                            resultContainer != null && resultContainer.getRecipeUsed() != null
                                    ? resultContainer.getRecipeUsed().id()
                                    : "none");
                } else {
                    UtilityCoreQoL.LOGGER.warn("[RecipeSelector][S] selected==null or menu not AbstractCraftingMenu: selected={} menu={}", selected, menu);
                }
            } else {
                UtilityCoreQoL.LOGGER.warn("[RecipeSelector][S] invalid index {} (data.size={})", packet.selectedIndex(), data.getRecipeList().size());
            }
        });
    }
}