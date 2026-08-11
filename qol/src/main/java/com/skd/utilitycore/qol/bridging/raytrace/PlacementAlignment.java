package com.skd.utilitycore.qol.bridging.raytrace;

import com.skd.utilitycore.qol.bridging.UtilityCoreQoLBridging;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

/**
 * Used to determine the indicator that should be
 * used when bridging assist is available.
 */
public enum PlacementAlignment {

    UP("up"),
    DOWN("down"),
    HORIZONTAL("horizontal");

    private final Identifier textureLocation;

    PlacementAlignment(String textureName) {
        this.textureLocation = Identifier.tryBuild(UtilityCoreQoLBridging.MOD_ID, "textures/gui/sprites/indicator/%s".formatted(textureName));
    }

    public Identifier getTexturePath() {
        return this.textureLocation;
    }

    public static PlacementAlignment from(Direction direction) {
        if(direction == null) return null;

        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            default -> HORIZONTAL;
        };
    }

}
