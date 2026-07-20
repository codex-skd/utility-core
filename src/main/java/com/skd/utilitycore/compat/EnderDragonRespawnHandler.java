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

        if (!tryRespawn(fight, endLevel)) {
            UtilityCore.LOGGER.warn("[UtilityCore] Dragon respawn failed — dragon will NOT respawn.");
        }
    }

    private static boolean tryRespawn(EnderDragonFight fight, ServerLevel level) {
        boolean hasYung = false;
        try {
            Class<?> iFight = Class.forName(YUNG_IFIGHT);
            hasYung = iFight.isInstance(fight);
        } catch (ClassNotFoundException e) {
            hasYung = false;
        }

        if (hasYung) {
            return tryYungPlaceAndAdvance(fight, level);
        }

        return tryVanillaRespawn(level, fight);
    }

    private static boolean tryYungPlaceAndAdvance(EnderDragonFight fight, ServerLevel level) {
        // Place crystals mimicking ItemEndCrystal.useOn(): setBeamTarget is required for YUNG detection
        boolean placed = false;
        for (int dist = 3; dist <= 7; dist += 2) {
            if (placed = placeCrystals(level, new BlockPos(0, 60, 0), dist)) break;
        }
        if (!placed) placed = placeCrystals(level, new BlockPos(0, 60, 0), 2);
        if (!placed) placed = placeCrystalsFallback(level);

        if (!placed) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place crystals for YUNG respawn");
            return false;
        }

        return tryYungAdvance(fight);
    }

    private static boolean tryVanillaRespawn(ServerLevel level, EnderDragonFight fight) {
        UtilityCore.LOGGER.info("[UtilityCore] Using vanilla respawn with crystal placement");
        boolean placed = placeCrystals(level, new BlockPos(0, 60, 0), 2);
        if (!placed) placed = placeCrystals(level, new BlockPos(0, 60, 0), 3);
        if (!placed) placed = placeCrystalsFallback(level);

        if (!placed) {
            UtilityCore.LOGGER.warn("[UtilityCore] Could not place 4 EndCrystals");
            return false;
        }

        fight.tryRespawn();
        return true;
    }

    private static boolean placeCrystals(ServerLevel level, BlockPos center, int dist) {
        BlockPos[] targets = new BlockPos[4];
        int i = 0;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = center.relative(dir, dist);
            if (!level.getBlockState(pos).is(Blocks.BEDROCK)) return false;
            if (!level.getBlockState(pos.above()).isAir() || !level.getBlockState(pos.above(2)).isAir()) return false;
            targets[i++] = pos;
        }

        UtilityCore.LOGGER.info("[UtilityCore] Placing 4 crystals (dist={})", dist);
        for (BlockPos bedrock : targets) {
            EndCrystal crystal = new EndCrystal(level, bedrock.getX() + 0.5, bedrock.getY() + 1.0, bedrock.getZ() + 0.5);
            crystal.setBeamTarget(bedrock);
            crystal.setInvulnerable(true);
            level.addFreshEntity(crystal);
        }
        return true;
    }

    private static boolean placeCrystalsFallback(ServerLevel level) {
        for (int dist = 7; dist >= 2; dist--) {
            int placed = 0;
            BlockPos[] targets = new BlockPos[4];
            int i = 0;
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos pos = new BlockPos(0, 60, 0).relative(dir, dist);
                if (!level.getBlockState(pos.above()).isAir() || !level.getBlockState(pos.above(2)).isAir()) continue;
                targets[i++] = pos;
                placed++;
            }
            if (placed == 4) {
                UtilityCore.LOGGER.info("[UtilityCore] Fallback placing 4 crystals (dist={})", dist);
                for (BlockPos pos : targets) {
                    EndCrystal crystal = new EndCrystal(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                    crystal.setBeamTarget(pos);
                    crystal.setInvulnerable(true);
                    level.addFreshEntity(crystal);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean tryYungAdvance(EnderDragonFight fight) {
        try {
            Class<?> iFight = Class.forName(YUNG_IFIGHT);
            if (!iFight.isInstance(fight)) {
                UtilityCore.LOGGER.warn("[UtilityCore] EnderDragonFight does not implement IBetterDragonFight");
                return false;
            }

            Method getStage = iFight.getMethod("getDragonRespawnStage");
            Object stage = getStage.invoke(fight);
            if (stage != null) {
                UtilityCore.LOGGER.info("[UtilityCore] YUNG respawn already in progress, skipping");
                return true;
            }

            Class<?> stageClass = Class.forName(YUNG_STAGE);
            Object startStage = stageClass.getField("START").get(null);

            Method advance = iFight.getMethod("advanceRespawnStage", stageClass);
            advance.invoke(fight, startStage);

            UtilityCore.LOGGER.info("[UtilityCore] YUNG dragon respawn initiated via advanceRespawnStage(START)");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Exception e) {
            UtilityCore.LOGGER.warn("[UtilityCore] YUNG respawn failed: {}", e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
            return false;
        }
    }

}
