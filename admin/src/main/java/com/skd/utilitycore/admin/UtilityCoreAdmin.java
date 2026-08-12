package com.skd.utilitycore.admin;

import com.skd.utilitycore.admin.chunkgen.ChunkGenManager;
import com.skd.utilitycore.admin.config.AdminConfig;
import com.skd.utilitycore.admin.compat.DataPackFolderLoader;
import com.skd.utilitycore.admin.rules.ServerRulesManager;
import com.skd.utilitycore.admin.schematic.SpawnSchematicManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.fml.ModContainer;

@Mod("utility_core_admin")
public class UtilityCoreAdmin {

    public static final String MODID = "utility_core_admin";

    private final ChunkGenManager chunkGenManager = new ChunkGenManager();
    private final ServerRulesManager serverRulesManager = new ServerRulesManager();
    private final SpawnSchematicManager spawnSchematicManager = new SpawnSchematicManager();

    public UtilityCoreAdmin(IEventBus modEventBus, ModContainer modContainer) {
        // Register config
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, AdminConfig.SPEC, "utility_core/utility_core_admin-common.toml");
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }

        // Register event listeners
        modEventBus.addListener(this::onCommonSetup);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
        NeoForge.EVENT_BUS.addListener(this::onServerStarted);
        NeoForge.EVENT_BUS.addListener(this::onServerTick);
        modEventBus.addListener(DataPackFolderLoader::onAddPackFinders);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        // Common setup logic if needed
    }

    private void onServerStarting(ServerStartingEvent event) {
        // Apply server rules when server starts
        serverRulesManager.apply(event.getServer());
    }

    private void onServerStarted(ServerStartedEvent event) {
        // Spawn schematic when server starts
        SpawnSchematicManager.getInstance().onServerStarted(event.getServer());
    }

    private void onServerTick(ServerTickEvent.Post event) {
        // Tick chunk generation manager
        chunkGenManager.tick(event.getServer());
    }
}
