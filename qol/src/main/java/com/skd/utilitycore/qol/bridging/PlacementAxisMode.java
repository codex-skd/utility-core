package com.skd.utilitycore.qol.bridging;

import net.minecraft.core.Direction;

public enum PlacementAxisMode {
    NONE,
    XZ,
    Y,
    BOTH;

    public boolean isDirectionEnabled(Direction direction) {
        return switch (this) {
            case NONE -> false;
            case XZ -> direction.getAxis() == Direction.Axis.X || direction.getAxis() == Direction.Axis.Z;
            case Y -> direction.getAxis() == Direction.Axis.Y;
            case BOTH -> true;
        };
    }
}