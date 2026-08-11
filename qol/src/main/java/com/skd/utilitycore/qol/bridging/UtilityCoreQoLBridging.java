package com.skd.utilitycore.qol.bridging;

import com.skd.utilitycore.qol.bridging.ModIds;
import com.skd.utilitycore.qol.bridging.BridgingConfig;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class UtilityCoreQoLBridging {

    public static final String MOD_ID = "utility_core_qol";

    // Environment -- simple platform indepentent way of tracking incompatible mods.
    private static Set<String> compatibilityNeededMods = new HashSet<>();

    // Not quite incompatible, but some config defaults need to be changed when a mod is detected.
    public static void noteIncompatibleMod(String modId) {
        compatibilityNeededMods.add(modId.toLowerCase());
    }

    public static SourcePerspective getCompatibleSourcePerspective() {
        SourcePerspective cfgPerspective = BridgingConfig.PERSPECTIVE_LOCK.get();

        // if LET_BRIDGING_MOD_DECIDE, figure out if mod compatibility. Otherwise
        // respect user choice.
        if(cfgPerspective != SourcePerspective.LET_BRIDGING_MOD_DECIDE)
            return cfgPerspective;

        return compatibilityNeededMods.contains(ModIds.FREE_LOOK)
                ? SourcePerspective.ALWAYS_EYELINE
                : SourcePerspective.COPY_TOGGLE_PERSPECTIVE;
    }

    // Note: Config loading/unloading is handled by NeoForge when we register the spec
    // No need for explicit init() method like with YACL3

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(UtilityCoreQoLBridging.MOD_ID, name);
    }

    // We don't need a getConfig() method since we access values directly from BridgingConfig
    // Example: BridgingConfig.ENABLE_BRIDGING_ASSIST.get()

    public static Logger getLogger() {
        return LoggerFactory.getLogger(UtilityCoreQoLBridging.class);
    }

    // The config directory is handled by NeoForge, not needed here
}