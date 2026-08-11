package com.skd.utilitycore.qol.common.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record SyncRecipesPacket(List<ItemStack> outputs, List<ItemStack> inputs) implements CustomPacketPayload {

    public static Type<SyncRecipesPacket> createType(String modId) {
        return new Type<>(Identifier.parse(modId + ":sync_recipes"));
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncRecipesPacket> STREAM_CODEC =
            StreamCodec.of(SyncRecipesPacket::encode, SyncRecipesPacket::decode);

    private static void encode(RegistryFriendlyByteBuf buf, SyncRecipesPacket packet) {
        buf.writeVarInt(packet.outputs.size());
        for (ItemStack stack : packet.outputs) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack);
        }
        buf.writeVarInt(packet.inputs.size());
        for (ItemStack stack : packet.inputs) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack);
        }
    }

    private static SyncRecipesPacket decode(RegistryFriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<ItemStack> outputs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            outputs.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(buf));
        }
        int inputSize = buf.readVarInt();
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < inputSize; i++) {
            inputs.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(buf));
        }
        return new SyncRecipesPacket(outputs, inputs);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return createType("utility_core_qol");
    }
}