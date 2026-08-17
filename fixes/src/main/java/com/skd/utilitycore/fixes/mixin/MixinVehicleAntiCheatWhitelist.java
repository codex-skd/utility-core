package com.skd.utilitycore.fixes.mixin;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.fixes.config.FixesConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinVehicleAntiCheatWhitelist {

    private static final Logger LOGGER = LogUtils.getLogger();

    // Cache for whitelisted entity types to avoid repeated config lookups
    @Unique
    private static final Set<ResourceKey<EntityType<?>>> WHITELIST_CACHE = ConcurrentHashMap.newKeySet();

    @Unique
    private static volatile boolean CACHE_INITIALIZED = false;

    @Unique
    private static void initializeCache(MinecraftServer server) {
        if (CACHE_INITIALIZED) return;
        synchronized (MixinVehicleAntiCheatWhitelist.class) {
            if (CACHE_INITIALIZED) return;
            try {
                if (!FixesConfig.SPEC.isLoaded()) return;

                if (!FixesConfig.ENABLE_VEHICLE_ANTICHEAT_WHITELIST.get()) {
                    CACHE_INITIALIZED = true;
                    return;
                }

                if (server == null) return;

                List<? extends String> whitelist = FixesConfig.VEHICLE_ANTICHEAT_WHITELIST.get();
                HolderLookup.Provider registryAccess = server.registryAccess();
                var entityTypeRegistry = registryAccess.lookupOrThrow(Registries.ENTITY_TYPE);

                for (String entry : whitelist) {
                    if (entry == null || entry.isEmpty()) continue;
                    String trimmed = entry.trim();

                    // Exact match: namespace:path
                    try {
                        Identifier id = Identifier.tryParse(trimmed);
                        if (id != null) {
                            ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
                            Optional<Holder.Reference<EntityType<?>>> holder = entityTypeRegistry.get(key);
                            if (holder.isPresent()) {
                                WHITELIST_CACHE.add(key);
                                LOGGER.info("[UtilityCoreFixes] Vehicle anti-cheat whitelisted: {}", trimmed);
                            } else {
                                LOGGER.warn("[UtilityCoreFixes] Vehicle anti-cheat whitelist entry not found in registry: {}", trimmed);
                            }
                        } else {
                            LOGGER.warn("[UtilityCoreFixes] Invalid vehicle anti-cheat whitelist entry (bad identifier): {}", trimmed);
                        }
                    } catch (Exception e) {
                        LOGGER.warn("[UtilityCoreFixes] Invalid vehicle anti-cheat whitelist entry: {}", trimmed);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("[UtilityCoreFixes] Failed to initialize vehicle anti-cheat whitelist cache: {}", e.getMessage());
            } finally {
                CACHE_INITIALIZED = true;
            }
        }
    }

    @Unique
    private static MinecraftServer getServer(ServerGamePacketListenerImpl listener) {
        ServerPlayer player = listener.player;
        if (player == null) return null;
        ServerLevel level = player.level();
        return level != null ? level.getServer() : null;
    }

    @Unique
    private static boolean isWhitelisted(Entity entity, ServerGamePacketListenerImpl listener) {
        if (!CACHE_INITIALIZED) {
            initializeCache(getServer(listener));
        }
        if (WHITELIST_CACHE.isEmpty()) return false;

        var key = entity.getType().builtInRegistryHolder().unwrapKey().orElse(null);
        return key != null && WHITELIST_CACHE.contains(key);
    }

    @Inject(
        method = "isVehicleMovingTooFast",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onIsVehicleMovingTooFast(Entity vehicle, CallbackInfoReturnable<Boolean> cir) {
        ServerGamePacketListenerImpl listener = (ServerGamePacketListenerImpl) (Object) this;
        if (vehicle != null && isWhitelisted(vehicle, listener)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
        method = "checkVehicleMovement",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onCheckVehicleMovement(CallbackInfoReturnable<Boolean> cir) {
        ServerGamePacketListenerImpl listener = (ServerGamePacketListenerImpl) (Object) this;
        Entity vehicle = listener.player.getVehicle();
        if (vehicle != null && isWhitelisted(vehicle, listener)) {
            cir.setReturnValue(true);
        }
    }
}