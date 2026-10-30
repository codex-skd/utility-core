package com.skd.utilitycore.network;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = "1";

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
            registrar.playToServer(
                    SelectRecipePacket.TYPE,
                    SelectRecipePacket.STREAM_CODEC,
                    SelectRecipePacket::handle
            );
            registrar.playToClient(
                    SyncRecipesPacket.TYPE,
                    SyncRecipesPacket.STREAM_CODEC,
                    SyncRecipesPacket::handle
            );
        });
    }
}
