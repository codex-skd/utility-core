package com.skd.utilitycore.qol.network;

import com.skd.utilitycore.qol.common.network.SyncRecipesPacket;
import com.skd.utilitycore.qol.client.PolymorphClientHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncRecipesPacketHandler {

    public static void handle(SyncRecipesPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            PolymorphClientHandler.receiveServerRecipes(packet.outputs(), packet.inputs());
        });
    }
}