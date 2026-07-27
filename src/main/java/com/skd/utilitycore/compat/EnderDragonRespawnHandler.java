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

        // YUNG searches for crystals at:
        //   checkForBEIRespawnCrystals: dist=7 from portal.above(1)   → Y=py+1
        //   checkForVanillaRespawnCrystals: dist=2 from portal.below(2) → Y=py-2
        // If no bedrock exists at those positions, we PLACE bedrock there.

        boolean placed = forceCrystals(level, portal.getX(), py, portal.getZ());

        if (!placed) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for dragon respawn");
            return;
        }

        fight.tryRespawn();
        UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn initiated");
    }

    private static boolean forceCrystals(ServerLevel level, int px, int py, int pz) {
        // Try YUNG BEI positions first: dist=7 at portal.above(1) height
        if (tryPlaceAt(level, px, py + 1, pz, 7)) {
            return true;
        }
        // Try vanilla positions: dist=2 at portal.below(2) height
        if (tryPlaceAt(level, px, py - 2, pz, 2)) {
            return true;
        }
        // Fallback: try distances 3-6 at portal Y
        for (int d = 3; d <= 6; d++) {
            if (tryPlaceAt(level, px, py, pz, d)) {
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
            // Scan Y around target to find existing bedrock with air above
            BlockPos found = null;
            for (int y = cy + 10; y >= cy - 10; y--) {
                BlockPos check = new BlockPos(pos.getX(), y, pos.getZ());
                if (level.getBlockState(check).is(Blocks.BEDROCK) && level.getBlockState(check.above()).isAir()) {
                    found = check;
                    break;
                }
            }
            if (found == null) return false;
            positions[i++] = found;
        }

        UtilityCore.LOGGER.info("[UtilityCore] Placing 4 crystals on bedrock (center=({},{},{}), dist={})", cx, cy, cz, dist);
        for (BlockPos bedrock : positions) {
            EndCrystal crystal = new EndCrystal(level, bedrock.getX() + 0.5, bedrock.getY() + 1.0, bedrock.getZ() + 0.5);
            crystal.setBeamTarget(bedrock);
            crystal.setInvulnerable(true);
            level.addFreshEntity(crystal);
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
            // Clear area
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
            level.setBlock(pos.above(2), Blocks.AIR.defaultBlockState(), 3);
            // Place bedrock
            level.setBlock(pos, Blocks.BEDROCK.defaultBlockState(), 3);
            // Place crystal
            EndCrystal crystal = new EndCrystal(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
            crystal.setBeamTarget(pos);
            crystal.setInvulnerable(true);
            level.addFreshEntity(crystal);
        }
    }
}
