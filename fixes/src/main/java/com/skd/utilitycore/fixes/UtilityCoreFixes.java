package com.skd.utilitycore.fixes;

import com.skd.utilitycore.fixes.config.FixesConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.fml.ModContainer;

@Mod("utility_core_fixes")
public class UtilityCoreFixes {

    public static final String MODID = "utility_core_fixes";

    public UtilityCoreFixes(IEventBus modEventBus, ModContainer modContainer) {
        // Register config
        modContainer.registerConfig(ModConfig.Type.COMMON, FixesConfig.SPEC);
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
        
        // Fixes' mixins work via pure mixin injection (loaded from utility_core_fixes.mixins.json)
        // No explicit registration needed for mixins
    }
}
