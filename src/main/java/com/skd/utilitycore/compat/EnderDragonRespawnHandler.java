package com.skd.utilitycore.compat;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.lang.reflect.Field;

@EventBusSubscriber(modid = UtilityCore.MODID)
public class EnderDragonRespawnHandler {

    private static Field exitPortalLocationField;

    static {
        try {
            exitPortalLocationField = EnderDragonFight.class.getDeclaredField("exitPortalLocation");
            exitPortalLocationField.setAccessible(true);
        } catch (Exception e) {
            UtilityCore.LOGGER.error("[UtilityCore] Failed to access EnderDragonFight.exitPortalLocation via reflection", e);
        }
    }

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

        fight.tryRespawn();

        BlockPos portalPos = getExitPortalLocation(fight);
        if (portalPos == null) {
            UtilityCore.LOGGER.warn("[UtilityCore] Ender Dragon respawn failed: exit portal location unknown");
            return;
        }

        int placed = 0;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            placed += tryPlaceCrystal(endLevel, portalPos.above(1).relative(dir, 3));
            placed += tryPlaceCrystal(endLevel, portalPos.above(1).relative(dir, 7));
            placed += tryPlaceCrystal(endLevel, portalPos.below(2).relative(dir, 2));
        }

        if (placed > 0) {
            UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon respawn: placed {} crystals around portal at {}", placed, portalPos);
        }

        fight.tryRespawn();
        UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon full respawn sequence initiated");
    }

    private static int tryPlaceCrystal(ServerLevel level, BlockPos pos) {
        if (!level.getEntitiesOfClass(EndCrystal.class, new AABB(pos)).isEmpty()) return 0;
        EndCrystal crystal = new EndCrystal(EntityType.END_CRYSTAL, level);
        crystal.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        level.addFreshEntity(crystal);
        return 1;
    }

    private static BlockPos getExitPortalLocation(EnderDragonFight fight) {
        if (exitPortalLocationField == null) return null;
        try {
            return (BlockPos) exitPortalLocationField.get(fight);
        } catch (Exception e) {
            return null;
        }
    }
}
