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
        int portalY = portal.getY();

        // Try YUNG BEI positions (dist=7 from portal at portal Y)
        if (placeCrystalsAt(level, portal.getX(), portalY, portal.getZ(), 7)) {
            fight.tryRespawn();
            UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn initiated (YUNG dist=7)");
            return;
        }

        // Fallback: vanilla positions (dist=2 from (0, 60, 0))
        if (placeCrystalsAt(level, 0, 60, 0, 2)) {
            fight.tryRespawn();
            UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn initiated (vanilla dist=2)");
            return;
        }

        // Last resort: try any distance 3-6 from (0, 60, 0)
        for (int d = 3; d <= 6; d++) {
            if (placeCrystalsAt(level, 0, 60, 0, d)) {
                fight.tryRespawn();
                UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn initiated (fallback dist={})", d);
                return;
            }
        }

        UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for dragon respawn");
    }

    private static boolean placeCrystalsAt(ServerLevel level, int cx, int cy, int cz, int dist) {
        BlockPos[] targets = new BlockPos[4];
        int i = 0;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = new BlockPos(cx, cy, cz).relative(dir, dist);

            // Must be on bedrock with air above
            if (!level.getBlockState(pos).is(Blocks.BEDROCK)) return false;
            if (!level.getBlockState(pos.above()).isAir()) return false;
            if (!level.getBlockState(pos.above(2)).isAir()) return false;

            targets[i++] = pos;
        }

        UtilityCore.LOGGER.info("[UtilityCore] Placing 4 crystals on bedrock (center=({},{},{}), dist={})", cx, cy, cz, dist);
        for (BlockPos bedrock : targets) {
            EndCrystal crystal = new EndCrystal(level, bedrock.getX() + 0.5, bedrock.getY() + 1.0, bedrock.getZ() + 0.5);
            crystal.setBeamTarget(bedrock);
            crystal.setInvulnerable(true);
            level.addFreshEntity(crystal);
        }
        return true;
    }
}
