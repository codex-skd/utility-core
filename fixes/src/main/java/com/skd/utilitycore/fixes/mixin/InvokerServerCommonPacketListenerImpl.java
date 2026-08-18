package com.skd.utilitycore.fixes.mixin;

import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerCommonPacketListenerImpl.class)
public interface InvokerServerCommonPacketListenerImpl {

    @Invoker("isSingleplayerOwner")
    boolean utility_core$invokeIsSingleplayerOwner();
}