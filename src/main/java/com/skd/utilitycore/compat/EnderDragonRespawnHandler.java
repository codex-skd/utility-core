package com.skd.utilitycore.compat;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = UtilityCore.MODID)
public class EnderDragonRespawnHandler {

    private static final String YUNG_IFIGHT = "com.yungnickyoung.minecraft.betterendisland.world.IBetterDragonFight";

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

        tryRespawn(fight, endLevel);
    }

    private static boolean hasYung() {
        try {
            Class.forName(YUNG_IFIGHT);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static void tryRespawn(EnderDragonFight fight, ServerLevel level) {
        if (hasYung()) {
            tryRespawnYung(fight, level);
        } else {
            tryRespawnVanilla(fight, level);
        }
    }

    private static void tryRespawnYung(EnderDragonFight fight, ServerLevel level) {
        // YUNG checks crystals at distance 7 (BEI radius) first, then distance 2 (vanilla radius)
        // Try dist=7 first (optimistic for BEI), then dist=2 (vanilla fallback)
        boolean placed = placeCrystals(level, 7) || placeCrystals(level, 2);

        if (!placed) {
            // If both fail, try any distance
            for (int d = 3; d <= 6; d++) {
                if (placeCrystals(level, d)) {
                    placed = true;
                    break;
                }
            }
        }

        if (!placed) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for YUNG dragon respawn");
            return;
        }

        // tryRespawn() triggers YUNG's override → spawnDragon() → checks crystals at dist=7 or dist=2
        fight.tryRespawn();
        UtilityCore.LOGGER.info("[UtilityCore] YUNG dragon respawn initiated via tryRespawn()");
    }

    private static void tryRespawnVanilla(EnderDragonFight fight, ServerLevel level) {
        boolean placed = placeCrystals(level, 2) || placeCrystals(level, 3);

        if (!placed) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for vanilla dragon respawn");
            return;
        }

        fight.tryRespawn();
        UtilityCore.LOGGER.info("[UtilityCore] Vanilla dragon respawn initiated");
    }

    private static boolean placeCrystals(ServerLevel level, int dist) {
        BlockPos center = new BlockPos(0, 60, 0);
        BlockPos[] targets = new BlockPos[4];
        int i = 0;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = center.relative(dir, dist);
            if (!level.getBlockState(pos).is(Blocks.BEDROCK)) return false;
            if (!level.getBlockState(pos.above()).isAir() || !level.getBlockState(pos.above(2)).isAir()) return false;
            targets[i++] = pos;
        }

        UtilityCore.LOGGER.info("[UtilityCore] Placing 4 crystals with beamTarget (dist={})", dist);
        for (BlockPos bedrock : targets) {
            EndCrystal crystal = new EndCrystal(level, bedrock.getX() + 0.5, bedrock.getY() + 1.0, bedrock.getZ() + 0.5);
            crystal.setBeamTarget(bedrock);
            crystal.setInvulnerable(true);
            level.addFreshEntity(crystal);
        }
        return true;
    }
}
