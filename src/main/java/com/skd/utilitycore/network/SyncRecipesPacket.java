package com.skd.utilitycore.network;

import com.skd.utilitycore.UtilityCore;
import com.skd.utilitycore.client.PolymorphClientHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record SyncRecipesPacket(List<ItemStack> outputs) implements CustomPacketPayload {

    public static final Type<SyncRecipesPacket> TYPE =
            new Type<>(Identifier.parse(UtilityCore.MODID + ":sync_recipes"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncRecipesPacket> STREAM_CODEC =
            StreamCodec.of(SyncRecipesPacket::encode, SyncRecipesPacket::decode);

    private static void encode(RegistryFriendlyByteBuf buf, SyncRecipesPacket packet) {
        buf.writeVarInt(packet.outputs.size());
        for (ItemStack stack : packet.outputs) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack);
        }
    }

    private static SyncRecipesPacket decode(RegistryFriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<ItemStack> outputs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            outputs.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(buf));
        }
        return new SyncRecipesPacket(outputs);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncRecipesPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            PolymorphClientHandler.receiveServerRecipes(packet.outputs);
        });
    }
}
