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

    private static void tryRespawn(EnderDragonFight fight, ServerLevel level) {
        // 1. First, try vanilla tryRespawn() — also works with YUNG (it overrides the method)
        //    tryRespawn() internally sets the respawn stage and handles crystal placement
        fight.tryRespawn();

        // 2. Verify crystals were placed by checking the dragon fight's respawn stage
        //    If tryRespawn() returned successfully, crystals should be placed.
        //    If not, manually place crystals with beamTarget (needed by YUNG)
        if (!wereCrystalsPlaced(level)) {
            UtilityCore.LOGGER.info("[UtilityCore] tryRespawn() did not place crystals, placing manually");
            placeCrystals(level);
        }

        // 3. If crystals still aren't placed after manual placement, try tryRespawn() again
        if (!wereCrystalsPlaced(level)) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for dragon respawn");
            return;
        }

        // 4. Call tryRespawn() again if crystals were just placed manually
        //    (tryRespawn is idempotent — calling it multiple times is safe)
        fight.tryRespawn();
        UtilityCore.LOGGER.info("[UtilityCore] Ender Dragon respawn initiated");
    }

    private static boolean wereCrystalsPlaced(ServerLevel level) {
        BlockPos podium = new BlockPos(0, 60, 0);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = podium.relative(dir, 3);
            BlockPos crystalPos = pos.above();
            if (level.getBlockState(pos).is(Blocks.BEDROCK) && level.getBlockState(crystalPos).isAir()) {
                return false;
            }
        }
        return true;
    }

    private static void placeCrystals(ServerLevel level) {
        BlockPos center = new BlockPos(0, 60, 0);

        for (int dist = 2; dist <= 7; dist++) {
            BlockPos[] targets = new BlockPos[4];
            int i = 0;
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos pos = center.relative(dir, dist);
                if (!level.getBlockState(pos).is(Blocks.BEDROCK)) break;
                if (!level.getBlockState(pos.above()).isAir() || !level.getBlockState(pos.above(2)).isAir()) break;
                targets[i++] = pos;
            }
            if (i == 4) {
                UtilityCore.LOGGER.info("[UtilityCore] Placing 4 crystals with beamTarget (dist={})", dist);
                for (BlockPos bedrock : targets) {
                    EndCrystal crystal = new EndCrystal(level, bedrock.getX() + 0.5, bedrock.getY() + 1.0, bedrock.getZ() + 0.5);
                    crystal.setBeamTarget(bedrock);
                    crystal.setInvulnerable(true);
                    level.addFreshEntity(crystal);
                }
                return;
            }
        }

        UtilityCore.LOGGER.warn("[UtilityCore] Could not find valid bedrock positions for crystals");
    }
}
