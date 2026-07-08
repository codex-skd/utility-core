package com.skd.utilitycore.network;

import com.skd.utilitycore.UtilityCore;
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
        });
    }
}
