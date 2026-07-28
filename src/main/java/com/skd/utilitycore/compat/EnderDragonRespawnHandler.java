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
import java.util.stream.StreamSupport;

@EventBusSubscriber(modid = UtilityCore.MODID)
public class EnderDragonRespawnHandler {

    private static final String YUNG_ACCESSOR = "com.yungnickyoung.minecraft.betterendisland.mixin.accessor.EnderDragonFightAccessor";

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

    private static BlockPos getPortalCenter(EnderDragonFight fight) {
        try {
            Class<?> accessor = Class.forName(YUNG_ACCESSOR);
            if (accessor.isInstance(fight)) {
                Object portal = accessor.getMethod("getPortalLocation").invoke(fight);
                if (portal instanceof BlockPos pos) {
                    UtilityCore.LOGGER.info("[UtilityCore] YUNG exit portal at {}", pos);
                    return pos;
                }
            }
        } catch (Exception ignored) {}
        return new BlockPos(0, 60, 0);
    }

    private static void tryRespawn(EnderDragonFight fight, ServerLevel level) {
        BlockPos portal = getPortalCenter(fight);
        int py = portal.getY();
        UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn: portal={}, hasWon={}, respawnStage={}",
                portal, fight.hasPreviouslyKilledDragon(), getRespawnStage(fight));

        boolean placed = forceCrystals(level, portal.getX(), py, portal.getZ());

        if (!placed) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for dragon respawn");
            return;
        }

        try {
            fight.tryRespawn();
            UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn initiated. Post-call state: hasWon={}, respawnStage={}",
                    fight.hasPreviouslyKilledDragon(), getRespawnStage(fight));
        } catch (Exception e) {
            UtilityCore.LOGGER.error("[UtilityCore] Dragon respawn tryRespawn() threw: {}", e.getMessage(), e);
        }

        // Log all end crystals in the area for verification
        BlockPos searchCenter = new BlockPos(0, 60, 0);
        int crystalCount = 0;
        for (EndCrystal crystal : StreamSupport.stream(level.getEntities().getAll().spliterator(), false)
                .filter(e -> e instanceof EndCrystal)
                .map(e -> (EndCrystal) e).toList()) {
            crystalCount++;
            if (crystal.blockPosition().distSqr(searchCenter) < 10000) {
                UtilityCore.LOGGER.info("[UtilityCore]   Crystal #{} at {} beam={} invuln={}",
                        crystalCount, crystal.blockPosition(), crystal.getBeamTarget(), crystal.isInvulnerable());
            }
        }
        UtilityCore.LOGGER.info("[UtilityCore] Total end crystals in End dimension: {}", crystalCount);
    }

    private static String getRespawnStage(EnderDragonFight fight) {
        try {
            for (java.lang.reflect.Field f : EnderDragonFight.class.getDeclaredFields()) {
                if (f.getType().isEnum() || f.getName().contains("Stage") || f.getName().contains("stage") || f.getName().contains("respawn")) {
                    f.setAccessible(true);
                    Object val = f.get(fight);
                    if (val != null) {
                        return f.getName() + "=" + val;
                    }
                }
            }
        } catch (Exception ignored) {}
        return "unknown";
    }

    private static boolean forceCrystals(ServerLevel level, int px, int py, int pz) {
        UtilityCore.LOGGER.info("[UtilityCore] forceCrystals: center=({},{},{}), moving=YUNG_BEI", px, py, pz);
        // Try YUNG BEI positions first: dist=7 at portal.above(1) height
        if (tryPlaceAt(level, px, py + 1, pz, 7)) {
            UtilityCore.LOGGER.info("[UtilityCore] forceCrystals: SUCCESS at YUNG BEI dist=7 Y={}", py + 1);
            return true;
        }
        UtilityCore.LOGGER.info("[UtilityCore] forceCrystals: YUNG BEI failed, trying vanilla dist=2 Y={}", py - 2);
        // Try vanilla positions: dist=2 at portal.below(2) height
        if (tryPlaceAt(level, px, py - 2, pz, 2)) {
            UtilityCore.LOGGER.info("[UtilityCore] forceCrystals: SUCCESS at vanilla dist=2 Y={}", py - 2);
            return true;
        }
        // Fallback: try distances 3-6 at portal Y
        UtilityCore.LOGGER.info("[UtilityCore] forceCrystals: vanilla failed, trying fallback dists 3-6 at Y={}", py);
        for (int d = 3; d <= 6; d++) {
            if (tryPlaceAt(level, px, py, pz, d)) {
                UtilityCore.LOGGER.info("[UtilityCore] forceCrystals: SUCCESS at fallback dist={} Y={}", d, py);
                return true;
            }
        }
        // Last resort: place bedrock + crystal at YUNG positions regardless of terrain
        UtilityCore.LOGGER.info("[UtilityCore] No bedrock found, placing bedrock + crystals at dist=7 Y={}", py + 1);
        placeCrystalWithBedrock(level, px, py + 1, pz, 7);
        return true;
    }

    private static boolean tryPlaceAt(ServerLevel level, int cx, int cy, int cz, int dist) {
        BlockPos[] positions = new BlockPos[4];
        int i = 0;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = new BlockPos(
                cx + dir.getStepX() * dist,
                cy,
                cz + dir.getStepZ() * dist
            );
            BlockPos found = null;
            for (int y = cy + 10; y >= cy - 10; y--) {
                BlockPos check = new BlockPos(pos.getX(), y, pos.getZ());
                boolean isBedrock = level.getBlockState(check).is(Blocks.BEDROCK);
                boolean hasAirAbove = level.getBlockState(check.above()).isAir();
                UtilityCore.LOGGER.info("[UtilityCore]   tryPlaceAt scanning: pos={} bedrock={} airAbove={}", check, isBedrock, hasAirAbove);
                if (isBedrock && hasAirAbove) {
                    found = check;
                    break;
                }
            }
            if (found == null) {
                UtilityCore.LOGGER.info("[UtilityCore]   tryPlaceAt FAILED at dir={} pos=({},{},{}) — no bedrock+air found in range", dir, pos.getX(), cy, pos.getZ());
                return false;
            }
            positions[i++] = found;
        }

        UtilityCore.LOGGER.info("[UtilityCore] Placing 4 crystals on bedrock (center=({},{},{}), dist={})", cx, cy, cz, dist);
        for (BlockPos bedrock : positions) {
            EndCrystal crystal = new EndCrystal(level, bedrock.getX() + 0.5, bedrock.getY() + 1.0, bedrock.getZ() + 0.5);
            crystal.setBeamTarget(bedrock);
            crystal.setInvulnerable(true);
            level.addFreshEntity(crystal);
            UtilityCore.LOGGER.info("[UtilityCore]   Placed crystal at ({},{},{}) beam={}", bedrock.getX(), bedrock.getY(), bedrock.getZ(), bedrock);
        }
        return true;
    }

    private static void placeCrystalWithBedrock(ServerLevel level, int cx, int cy, int cz, int dist) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = new BlockPos(
                cx + dir.getStepX() * dist,
                cy,
                cz + dir.getStepZ() * dist
            );
            UtilityCore.LOGGER.info("[UtilityCore] placeCrystalWithBedrock: placing at {}", pos);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
            level.setBlock(pos.above(2), Blocks.AIR.defaultBlockState(), 3);
            level.setBlock(pos, Blocks.BEDROCK.defaultBlockState(), 3);
            if (!level.getBlockState(pos).is(Blocks.BEDROCK)) {
                UtilityCore.LOGGER.warn("[UtilityCore]   Failed to place bedrock at {}", pos);
            }
            EndCrystal crystal = new EndCrystal(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
            crystal.setBeamTarget(pos);
            crystal.setInvulnerable(true);
            if (!level.addFreshEntity(crystal)) {
                UtilityCore.LOGGER.warn("[UtilityCore]   Failed to spawn crystal at {}", pos.above());
            }
        }
        // Also place vanilla-position crystals (dist=2, Y=cy-4) so fight.tryRespawn() can detect them
        int vanillaY = cy - 4;
        UtilityCore.LOGGER.info("[UtilityCore] placeCrystalWithBedrock: also placing vanilla crystals at dist=2 Y={}", vanillaY);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = new BlockPos(
                cx + dir.getStepX() * 2,
                vanillaY,
                cz + dir.getStepZ() * 2
            );
            if (level.getBlockState(pos).is(Blocks.BEDROCK) && level.getBlockState(pos.above()).isAir()) {
                EndCrystal crystal = new EndCrystal(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                crystal.setBeamTarget(pos);
                crystal.setInvulnerable(true);
                level.addFreshEntity(crystal);
            } else {
                // Place bedrock ourselves, then crystal
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(pos, Blocks.BEDROCK.defaultBlockState(), 3);
                EndCrystal crystal = new EndCrystal(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                crystal.setBeamTarget(pos);
                crystal.setInvulnerable(true);
                level.addFreshEntity(crystal);
            }
        }
    }
}
