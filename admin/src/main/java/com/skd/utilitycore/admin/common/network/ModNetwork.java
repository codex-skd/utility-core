package com.skd.utilitycore.admin.common.network;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = "1";

    public static void register(IEventBus modEventBus, String modId,
                                 java.util.function.BiConsumer<PayloadRegistrar, String> registerPackets) {
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
            registerPackets.accept(registrar, modId);
        });
    }
}