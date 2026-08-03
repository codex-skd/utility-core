package com.skd.utilitycore.compat.schematic;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.Iterator;

// The schematic is always pasted in the overworld, so bounds only mean anything there —
// other dimensions could coincidentally share the same coordinates otherwise.
@EventBusSubscriber(modid = UtilityCore.MODID)
public class SpawnProtectionHandler {

    private static boolean isOverworld(net.neoforged.neoforge.event.level.BlockEvent event) {
        return event.getLevel() instanceof Level level && level.dimension() == Level.OVERWORLD;
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr != null && isOverworld(event) && mgr.isWithinBounds(event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr != null && isOverworld(event) && mgr.isWithinBounds(event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMultiPlace(BlockEvent.EntityMultiPlaceEvent event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr == null || !isOverworld(event)) return;
        for (var snapshot : event.getReplacedBlockSnapshots()) {
            if (mgr.isWithinBounds(snapshot.getPos())) {
                event.setCanceled(true);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr == null || event.getLevel().dimension() != Level.OVERWORLD) return;
        Iterator<BlockPos> it = event.getAffectedBlocks().iterator();
        while (it.hasNext()) {
            if (mgr.isWithinBounds(it.next())) {
                it.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onSpawnPlacementCheck(MobSpawnEvent.SpawnPlacementCheck event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr == null || !Config.SPAWN_SCHEMATIC_PREVENT_MOB_SPAWNS.get()) return;
        if (event.getSpawnType() != EntitySpawnReason.NATURAL) return;
        if (event.getLevel().getLevel().dimension() != Level.OVERWORLD) return;
        if (mgr.isWithinBounds(event.getPos())) {
            event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.FAIL);
        }
    }
}
