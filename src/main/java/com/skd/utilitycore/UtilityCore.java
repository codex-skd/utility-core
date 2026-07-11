package com.skd.utilitycore;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.attachment.ModAttachments;
import com.skd.utilitycore.network.ModNetwork;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Mod(UtilityCore.MODID)
public class UtilityCore {

    public static final String MODID = "utility_core";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UtilityCore(IEventBus modEventBus, ModContainer modContainer) {
        disableTombstoneBrokenMixin();
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModNetwork.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SuppressWarnings("unchecked")
    private static void disableTombstoneBrokenMixin() {
        try {
            Class<?> configClass = Class.forName("org.spongepowered.asm.mixin.transformer.Config");
            Field allConfigsField = configClass.getDeclaredField("allConfigs");
            allConfigsField.setAccessible(true);
            Map<String, Object> allConfigs = (Map<String, Object>) allConfigsField.get(null);
            Object tombstoneConfig = allConfigs.get("tombstone.mixins.json");
            if (tombstoneConfig == null) {
                LOGGER.info("[UtilityCore] Tombstone not present, skipping mixin fix");
                return;
            }
            Method getMethod = configClass.getDeclaredMethod("get");
            getMethod.setAccessible(true);
            Object mixinConfig = getMethod.invoke(tombstoneConfig);
            Field mixinClassesField = mixinConfig.getClass().getDeclaredField("mixinClasses");
            mixinClassesField.setAccessible(true);
            List<String> mixinClasses = (List<String>) mixinClassesField.get(mixinConfig);
            boolean removed = mixinClasses.removeIf("ovh.corail.tombstone.mixin.ItemInputMixin"::equals);
            LOGGER.info("[UtilityCore] {} ItemInputMixin from tombstone config",
                removed ? "Removed" : "Did not find");
        } catch (Exception e) {
            LOGGER.warn("[UtilityCore] Could not disable Tombstone mixin: {}", e.getMessage());
        }
    }
}
