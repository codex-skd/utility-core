package com.skd.utilitycore.admin.schematic;

import com.skd.utilitycore.admin.config.AdminConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
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
@EventBusSubscriber(modid = "utility_core_admin")
public class SpawnProtectionHandler {

    private static boolean isOverworld(net.neoforged.neoforge.event.level.BlockEvent event) {
        return event.getLevel() instanceof Level level && level.dimension() == Level.OVERWORLD;
    }

    private static void warnProtected(Player player, BlockPos pos, SpawnSchematicManager mgr) {
        if (player == null) return;
        int dist = mgr.distanceToExit(pos);
        Component msg = dist > 0
                ? Component.literal("§c[SpawnSchematic] §7This area is protected. Move §e" + dist + " §7blocks outside to build here.")
                : Component.literal("§c[SpawnSchematic] §7This area is protected.");
        player.sendSystemMessage(msg);
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr != null && isOverworld(event) && mgr.isWithinBounds(event.getPos())) {
            event.setCanceled(true);
            warnProtected(event.getPlayer(), event.getPos(), mgr);
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr != null && isOverworld(event) && mgr.isWithinBounds(event.getPos())) {
            event.setCanceled(true);
            if (event.getEntity() instanceof Player player) {
                warnProtected(player, event.getPos(), mgr);
            }
        }
    }

    @SubscribeEvent
    public static void onMultiPlace(BlockEvent.EntityMultiPlaceEvent event) {
        SpawnSchematicManager mgr = SpawnSchematicManager.getInstance();
        if (mgr == null || !isOverworld(event)) return;
        for (var snapshot : event.getReplacedBlockSnapshots()) {
            if (mgr.isWithinBounds(snapshot.getPos())) {
                event.setCanceled(true);
                if (event.getEntity() instanceof Player player) {
                    warnProtected(player, snapshot.getPos(), mgr);
                }
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
        if (mgr == null || !AdminConfig.SPAWN_SCHEMATIC_PREVENT_MOB_SPAWNS.get()) return;
        if (event.getSpawnType() != EntitySpawnReason.NATURAL) return;
        if (event.getLevel().getLevel().dimension() != Level.OVERWORLD) return;
        if (mgr.isWithinMobSpawnColumn(event.getPos())) {
            event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.FAIL);
        }
    }
}