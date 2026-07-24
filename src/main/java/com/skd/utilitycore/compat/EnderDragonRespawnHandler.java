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
        // Try YUNG accessor first — gets the exit portal position from its mixin
        try {
            Class<?> accessor = Class.forName(YUNG_ACCESSOR);
            if (accessor.isInstance(fight)) {
                Object portal = accessor.getMethod("getPortalLocation").invoke(fight);
                if (portal instanceof BlockPos pos) {
                    UtilityCore.LOGGER.info("[UtilityCore] YUNG exit portal at {}", pos);
                    return pos;
                }
            }
        } catch (Exception e) {
            // YUNG not present or method not accessible
        }
        return new BlockPos(0, 60, 0);
    }

    private static void tryRespawn(EnderDragonFight fight, ServerLevel level) {
        BlockPos portal = getPortalCenter(fight);

        // Place crystals at YUNG's BEI position (dist=7 from portal.above(1))
        // and at vanilla position (dist=2 from portal) as fallback
        boolean placed = placeCrystalsAt(level, portal.above(1), 7)
                      || placeCrystalsAt(level, portal, 2);

        if (!placed) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for dragon respawn");
            return;
        }

        fight.tryRespawn();
        UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn initiated");
    }

    private static boolean placeCrystalsAt(ServerLevel level, BlockPos origin, int dist) {
        // Place EndCrystal entities at origin.relative(dir, dist).above()
        // YUNG: checks for EndCrystal entities at exitPortal.above(1).relative(dir, 7)
        // Vanilla: checks at exitPortal.below(2).relative(dir, 2)
        // We just place — YUNG/vanilla will find them by entity lookup

        if (dist == 7) {
            // YUNG BEI mode — place without bedrock check
            UtilityCore.LOGGER.info("[UtilityCore] Placing 4 crystals dist={} from {}", dist, origin);
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos target = origin.relative(dir, dist);
                BlockPos crystalPos = target.above();
                EndCrystal crystal = new EndCrystal(level, crystalPos.getX() + 0.5, crystalPos.getY(), crystalPos.getZ() + 0.5);
                crystal.setBeamTarget(target);
                crystal.setInvulnerable(true);
                level.addFreshEntity(crystal);
            }
            return true;
        }

        // Legacy mode (dist=2, 3, etc.) — try to find bedrock positions
        BlockPos[] targets = new BlockPos[4];
        int i = 0;
        BlockPos center = new BlockPos(origin.getX(), 60, origin.getZ());
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
