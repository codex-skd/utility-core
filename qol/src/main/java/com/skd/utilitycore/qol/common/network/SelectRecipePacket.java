package com.skd.utilitycore.qol.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SelectRecipePacket(int selectedIndex) implements CustomPacketPayload {

    public static Type<SelectRecipePacket> createType(String modId) {
        return new Type<>(Identifier.parse(modId + ":select_recipe"));
    }

    public static final StreamCodec<ByteBuf, SelectRecipePacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    SelectRecipePacket::selectedIndex,
                    SelectRecipePacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return createType("utility_core_qol");
    }
}