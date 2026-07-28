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
        BlockPos center = portal.above(1);
        UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn: portal={}, center={}, hasWon={}, respawnStage={}",
                portal, center, fight.hasPreviouslyKilledDragon(), getRespawnStage(fight));

        placeYungCrystals(level, center);
        placeVanillaCrystals(level, center);

        try {
            fight.tryRespawn();
            UtilityCore.LOGGER.info("[UtilityCore] Dragon respawn initiated. Post-call state: hasWon={}, respawnStage={}",
                    fight.hasPreviouslyKilledDragon(), getRespawnStage(fight));
        } catch (Exception e) {
            UtilityCore.LOGGER.error("[UtilityCore] Dragon respawn tryRespawn() threw: {}", e.getMessage(), e);
        }

        int crystalCount = 0;
        BlockPos searchCenter = new BlockPos(0, 60, 0);
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

    private static void placeYungCrystals(ServerLevel level, BlockPos center) {
        UtilityCore.LOGGER.info("[UtilityCore] Placing YUNG BEI crystals at dist=7 from {}", center);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos target = center.relative(dir, 7);
            BlockPos bedrock = findBedrockWithAirAbove(level, target);
            if (bedrock == null) {
                bedrock = target;
                level.setBlock(bedrock, Blocks.BEDROCK.defaultBlockState(), 3);
                level.setBlock(bedrock.above(), Blocks.AIR.defaultBlockState(), 3);
            }
            spawnCrystal(level, bedrock);
        }
    }

    private static void placeVanillaCrystals(ServerLevel level, BlockPos center) {
        UtilityCore.LOGGER.info("[UtilityCore] Placing vanilla detection crystals at dist=2 from {}", center);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos target = center.relative(dir, 2);
            BlockPos bedrock = findBedrockWithAirAbove(level, target);
            if (bedrock == null) {
                bedrock = target;
                level.setBlock(bedrock, Blocks.BEDROCK.defaultBlockState(), 3);
                level.setBlock(bedrock.above(), Blocks.AIR.defaultBlockState(), 3);
            }
            spawnCrystal(level, bedrock);
        }
    }

    private static BlockPos findBedrockWithAirAbove(ServerLevel level, BlockPos pos) {
        for (int y = pos.getY() + 10; y >= pos.getY() - 10; y--) {
            BlockPos check = new BlockPos(pos.getX(), y, pos.getZ());
            if (level.getBlockState(check).is(Blocks.BEDROCK) && level.getBlockState(check.above()).isAir()) {
                return check;
            }
        }
        return null;
    }

    private static void spawnCrystal(ServerLevel level, BlockPos bedrock) {
        EndCrystal crystal = new EndCrystal(level, bedrock.getX() + 0.5, bedrock.getY() + 1.0, bedrock.getZ() + 0.5);
        crystal.setBeamTarget(bedrock);
        crystal.setInvulnerable(true);
        level.addFreshEntity(crystal);
        UtilityCore.LOGGER.info("[UtilityCore]   Crystal placed at {} (bedrock={})", bedrock.above(), bedrock);
    }
}
