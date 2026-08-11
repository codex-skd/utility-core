package com.skd.utilitycore.qol.bridging;

public enum PlacementAxisModeOverride {
    FALLBACK,
    NONE,
    XZ,
    Y,
    BOTH;

    public PlacementAxisMode getPlacementAxisMode(PlacementAxisMode baseMode) {
        return switch (this) {
            case FALLBACK -> baseMode;
            case NONE -> PlacementAxisMode.NONE;
            case XZ -> PlacementAxisMode.XZ;
            case Y -> PlacementAxisMode.Y;
            case BOTH -> PlacementAxisMode.BOTH;
        };
    }
}
