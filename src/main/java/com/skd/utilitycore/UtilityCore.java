package com.skd.utilitycore;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.attachment.ModAttachments;
import com.skd.utilitycore.network.ModNetwork;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(UtilityCore.MODID)
public class UtilityCore {

    public static final String MODID = "utility_core";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UtilityCore(IEventBus modEventBus, ModContainer modContainer) {
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModNetwork.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
