package com.skd.utilitycore.qol.bridging.raytrace;

import com.skd.utilitycore.qol.bridging.util.flags.Flags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record BridgingPreContext(Level level, Perspective cameraPerspective, Perspective playerPerspective, Player player, Flags flags) {

}
