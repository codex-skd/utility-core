package com.skd.utilitycore.qol.bridging.raytrace;

import com.skd.utilitycore.qol.bridging.raytrace.BridgingPreContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record BridgingResult(BlockPos blockPos, Direction direction, BridgingPreContext context) {
}
