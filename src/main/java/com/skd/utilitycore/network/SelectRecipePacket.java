package com.skd.utilitycore.network;

import com.skd.utilitycore.UtilityCore;
import com.skd.utilitycore.attachment.ModAttachments;
import com.skd.utilitycore.polymorph.PlayerRecipeData;
import com.skd.utilitycore.polymorph.RecipePair;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SelectRecipePacket(int selectedIndex) implements CustomPacketPayload {

    public static final Type<SelectRecipePacket> TYPE =
            new Type<>(Identifier.parse(UtilityCore.MODID + ":select_recipe"));

    public static final StreamCodec<ByteBuf, SelectRecipePacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    SelectRecipePacket::selectedIndex,
                    SelectRecipePacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SelectRecipePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            PlayerRecipeData data = player.getData(ModAttachments.PLAYER_RECIPE_DATA);
            data.setSelectedIndex(packet.selectedIndex);
            RecipePair selected = data.getSelectedRecipe();
            AbstractContainerMenu menu = player.containerMenu;
            if (menu instanceof CraftingMenu cm && selected != null) {
                menu.setRemoteSlot(0, selected.output());
                var slots = cm.getInputGridSlots();
                if (!slots.isEmpty()) {
                    menu.slotsChanged(slots.getFirst().container);
                }
            }
        });
    }
}
