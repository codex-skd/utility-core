package com.skd.utilitycore.qol;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.qol.bridging.BridgingConfig;
import com.skd.utilitycore.qol.common.network.ModNetwork;
import com.skd.utilitycore.qol.common.network.SelectRecipePacket;
import com.skd.utilitycore.qol.common.network.SyncRecipesPacket;
import com.skd.utilitycore.qol.config.QoLConfig;
import com.skd.utilitycore.qol.network.SelectRecipePacketHandler;
import com.skd.utilitycore.qol.network.SyncRecipesPacketHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(UtilityCoreQoL.MODID)
public class UtilityCoreQoL {

    public static final String MODID = "utility_core_qol";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UtilityCoreQoL(IEventBus modEventBus, ModContainer modContainer) {
        ModNetwork.register(modEventBus, MODID, (registrar, modId) -> {
            registrar.playToServer(
                    SelectRecipePacket.createType(modId),
                    SelectRecipePacket.STREAM_CODEC,
                    SelectRecipePacketHandler::handle
            );
            registrar.playToClient(
                    SyncRecipesPacket.createType(modId),
                    SyncRecipesPacket.STREAM_CODEC,
                    SyncRecipesPacketHandler::handle
            );
        });
        modContainer.registerConfig(ModConfig.Type.COMMON, QoLConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.COMMON, BridgingConfig.SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
