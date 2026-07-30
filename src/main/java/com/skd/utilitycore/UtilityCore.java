package com.skd.utilitycore;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.logging.LogUtils;
import com.skd.utilitycore.attachment.ModAttachments;
import com.skd.utilitycore.compat.ChunkGenManager;
import com.skd.utilitycore.compat.schematic.SpawnSchematicManager;
import com.skd.utilitycore.network.ModNetwork;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixins;

@Mod(UtilityCore.MODID)
public class UtilityCore {

    public static final String MODID = "utility_core";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static ChunkGenManager chunkGenManager;
    private SpawnSchematicManager spawnSchematicManager;

    public UtilityCore(IEventBus modEventBus, ModContainer modContainer) {
        registerTombstoneErrorHandler();
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModNetwork.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        NeoForge.EVENT_BUS.register(this);
        chunkGenManager = new ChunkGenManager();
        spawnSchematicManager = new SpawnSchematicManager();
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        if (chunkGenManager != null) {
            chunkGenManager.tick(event.getServer());
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (chunkGenManager != null) {
            chunkGenManager.onPlayerJoin();
        }
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (chunkGenManager != null) {
            chunkGenManager.onPlayerLeave(ServerLifecycleHooks.getCurrentServer());
        }
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        if (chunkGenManager != null && Config.CHUNK_GEN_ENABLED.get()) {
            long total = chunkGenManager.totalGenerated();
            if (total > 0) {
                LOGGER.info("[ChunkGen] Ready. Previous progress: {} chunks generated", total);
            } else {
                LOGGER.info("[ChunkGen] Ready. No previous state found, will start from (0, 0)");
            }
        }
        if (spawnSchematicManager != null) {
            spawnSchematicManager.onServerStarted(event.getServer());
        }
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        if (chunkGenManager != null) {
            chunkGenManager.onServerStopping();
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("utilitycore")
                        .then(Commands.literal("chunkgen")
                                .then(Commands.literal("status").executes(ctx -> {
                                    if (chunkGenManager == null) {
                                        ctx.getSource().sendSuccess(() -> Component.literal("§c[ChunkGen] Not available"), false);
                                        return 0;
                                    }
                                    ctx.getSource().sendSuccess(() -> Component.literal(
                                            String.format("§a[ChunkGen] %s | %s | Generated: %d chunks",
                                                    chunkGenManager.isRunning() ? "§aRunning" : "§cStopped",
                                                    chunkGenManager.isPaused() ? "§ePaused" : "§aActive",
                                                    chunkGenManager.totalGenerated())), false);
                                    return Command.SINGLE_SUCCESS;
                                }))
                                .then(Commands.literal("start").executes(ctx -> {
                                    if (chunkGenManager == null) {
                                        ctx.getSource().sendSuccess(() -> Component.literal("§c[ChunkGen] Not available"), false);
                                        return 0;
                                    }
                                    ctx.getSource().sendSuccess(() -> Component.literal("§a[ChunkGen] Start/resume enabled. Triggered when server is empty."), false);
                                    return Command.SINGLE_SUCCESS;
                                }))
                                .then(Commands.literal("pause").executes(ctx -> {
                                    if (chunkGenManager == null) {
                                        ctx.getSource().sendSuccess(() -> Component.literal("§c[ChunkGen] Not available"), false);
                                        return 0;
                                    }
                                    chunkGenManager.pause();
                                    ctx.getSource().sendSuccess(() -> Component.literal("§e[ChunkGen] Generation paused"), false);
                                    return Command.SINGLE_SUCCESS;
                                }))
                                .then(Commands.literal("stop").executes(ctx -> {
                                    if (chunkGenManager == null) {
                                        ctx.getSource().sendSuccess(() -> Component.literal("§c[ChunkGen] Not available"), false);
                                        return 0;
                                    }
                                    chunkGenManager.stop();
                                    ctx.getSource().sendSuccess(() -> Component.literal("§c[ChunkGen] Generation stopped"), false);
                                    return Command.SINGLE_SUCCESS;
                                }))
                                .then(Commands.literal("reset").executes(ctx -> {
                                    if (chunkGenManager == null) {
                                        ctx.getSource().sendSuccess(() -> Component.literal("§c[ChunkGen] Not available"), false);
                                        return 0;
                                    }
                                    chunkGenManager.reset();
                                    ctx.getSource().sendSuccess(() -> Component.literal("§c[ChunkGen] Progress reset to (0, 0)"), false);
                                    return Command.SINGLE_SUCCESS;
                                }))
                        )
                        .then(Commands.literal("help").executes(ctx -> {
                            ctx.getSource().sendSuccess(() -> Component.literal(
                                    "§a--- Utility Core Commands ---\n" +
                                    "§e/utilitycore chunkgen status §7- Show generation status\n" +
                                    "§e/utilitycore chunkgen start §7- Start/resume generation\n" +
                                    "§e/utilitycore chunkgen pause §7- Pause generation\n" +
                                    "§e/utilitycore chunkgen stop §7- Stop generation\n" +
                                    "§e/utilitycore chunkgen reset §7- Reset progress to (0,0)"), false);
                            return Command.SINGLE_SUCCESS;
                        }))
        );
    }

    private static void registerTombstoneErrorHandler() {
        Mixins.registerErrorHandlerClass("com.skd.utilitycore.TombstoneErrorHandler");
        LOGGER.info("[UtilityCore] Registered Tombstone mixin error handler via Mixins.registerErrorHandlerClass");
    }
}
