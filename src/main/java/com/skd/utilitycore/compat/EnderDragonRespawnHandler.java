package com.skd.utilitycore.compat;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.lang.reflect.Method;

@EventBusSubscriber(modid = UtilityCore.MODID)
public class EnderDragonRespawnHandler {

    private static final String YUNG_IFIGHT = "com.yungnickyoung.minecraft.betterendisland.world.IBetterDragonFight";
    private static final String YUNG_STAGE = "com.yungnickyoung.minecraft.betterendisland.world.BetterDragonRespawnStage";

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        if (!Config.ENABLE_END_DRAGON_RESPAWN.get()) return;

        ServerLevel endLevel = event.getServer().getLevel(Level.END);
        if (endLevel == null) {
            UtilityCore.LOGGER.warn("[UtilityCore] Ender Dragon respawn: End dimension not found");
            return;
        }

        EnderDragonFight fight = endLevel.getDragonFight();
        if (fight == null) {
            UtilityCore.LOGGER.warn("[UtilityCore] Ender Dragon respawn: dragon fight not initialized");
            return;
        }

        if (!endLevel.getDragons().isEmpty()) {
            UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon respawn skipped: dragon is still alive");
            return;
        }

        if (!fight.hasPreviouslyKilledDragon()) {
            UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon respawn skipped: dragon has never been killed");
            return;
        }

        if (tryYungRespawn(fight)) return;

        UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon respawn via vanilla tryRespawn()");
        fight.tryRespawn();
    }

    private static boolean tryYungRespawn(EnderDragonFight fight) {
        try {
            Class<?> iFight = Class.forName(YUNG_IFIGHT);
            if (!iFight.isInstance(fight)) return false;

            Method getStage = iFight.getMethod("getDragonRespawnStage");
            Object stage = getStage.invoke(fight);
            if (stage != null) {
                UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon respawn skipped: YUNG respawn already in progress");
                return true;
            }

            Class<?> stageClass = Class.forName(YUNG_STAGE);
            Object startStage = stageClass.getField("START").get(null);

            Method advance = iFight.getMethod("advanceRespawnStage", stageClass);
            advance.invoke(fight, startStage);

            UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon respawn initiated via YUNG Better End Island API");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Exception e) {
            UtilityCore.LOGGER.error("[UtilityCore] Failed to initiate YUNG dragon respawn: {}", e.getMessage());
            return false;
        }
    }
}
