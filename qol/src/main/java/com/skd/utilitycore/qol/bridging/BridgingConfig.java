package com.skd.utilitycore.qol.bridging;

import com.skd.utilitycore.qol.bridging.BridgingAdjacency;
import com.skd.utilitycore.qol.bridging.PlacementAxisMode;
import com.skd.utilitycore.qol.bridging.PlacementAxisModeOverride;
import com.skd.utilitycore.qol.bridging.SourcePerspective;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BridgingConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Feature tab
    public static final ModConfigSpec.BooleanValue ENABLE_BRIDGING_ASSIST = BUILDER
            .comment("EN: a.k.a. 'Reacharound Placement' in keybinds. The Quark thing.",
                     "ES: a.k.a. 'Colocación de Alcance' en las vinculaciones de teclas. La cosa de Quark.")
            .define("enableBridgingAssist", true);

    public static final ModConfigSpec.DoubleValue MIN_BRIDGE_DISTANCE = BUILDER
            .comment("EN: Minimum distance to start bridging assist.",
                     "ES: Distancia mínima para comenzar la asistencia de puentear.")
            .defineInRange("minBridgeDistance", 20.0, 0.0, 100.0);

    public static final ModConfigSpec.DoubleValue MIN_BRIDGE_DISTANCE_HORIZONTAL = BUILDER
            .comment("EN: Minimum horizontal distance to start bridging assist.",
                     "ES: Distancia horizontal mínima para comenzar la asistencia de puentear.")
            .defineInRange("minBridgeDistanceHorizontal", 1.0, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue MIN_BRIDGE_DISTANCE_VERTICAL = BUILDER
            .comment("EN: Minimum vertical distance to start bridging assist.",
                     "ES: Distancia vertical mínima para comenzar la asistencia de puentear.")
            .defineInRange("minBridgeDistanceVertical", 0.1, 0.0, 10.0);

    public static final ModConfigSpec.BooleanValue ONLY_BRIDGE_WHEN_CROUCHED = BUILDER
            .comment("EN: Only bridge when crouched.",
                     "ES: Solo puentear cuando está agachado.")
            .define("onlyBridgeWhenCrouched", false);

    public static final ModConfigSpec.EnumValue<PlacementAxisMode> SUPPORTED_BRIDGE_AXES = BUILDER
            .comment("EN: Supported bridge axes for placement.",
                     "ES: Ejes soportados para puentear.")
            .defineEnum("supportedBridgeAxes", PlacementAxisMode.BOTH);

    public static final ModConfigSpec.EnumValue<PlacementAxisModeOverride> SUPPORTED_BRIDGE_AXES_WHEN_CROUCHED = BUILDER
            .comment("EN: Supported bridge axes when crouched override.",
                     "ES: Sobreescritura de ejes soportados para puentear cuando está agachado.")
            .defineEnum("supportedBridgeAxesWhenCrouched", PlacementAxisModeOverride.FALLBACK);

    public static final ModConfigSpec.IntValue DELAY_POST_BRIDGING = BUILDER
            .comment("EN: Delay in ticks after bridging before allowing another action.",
                     "ES: Retraso en tics después de puentear antes de permitir otra acción.")
            .defineInRange("delayPostBridging", 4, 0, 20);

    // VFX tab
    public static final ModConfigSpec.BooleanValue SHOW_CROSSHAIR = BUILDER
            .comment("EN: Show crosshair when bridging assist is active.",
                     "ES: Mostrar el punto de mira cuando la asistencia de puentear esté activa.")
            .define("showCrosshair", true);

    public static final ModConfigSpec.BooleanValue SHOW_OUTLINE = BUILDER
            .comment("EN: Show outline when bridging assist is active.",
                     "ES: Mostrar contorno cuando la asistencia de puentear esté activa.")
            .define("showOutline", true);

    public static final ModConfigSpec.BooleanValue SHOW_OUTLINE_EVEN_WHEN_NOT_BRIDGING = BUILDER
            .comment("EN: Show outline even when not actively bridging.",
                     "ES: Mostrar contorno incluso cuando no se esté puentear activamente.")
            .define("showOutlineEvenWhenNotBridging", false);

    public static final ModConfigSpec.BooleanValue NON_BRIDGE_RESPECTS_CROUCH_RULES = BUILDER
            .comment("EN: Non-bridging outline respects crouch rules.",
                     "ES: El contorno de no-puentear respeta las reglas de agacharse.")
            .define("nonBridgeRespectsCrouchRules", true);

    // We'll skip the color field for now as it's more complex with ModConfigSpec
    // For a proper implementation, we'd need to handle ARGB values
    // But for simplicity, we'll use separate red, green, blue, alpha values or just use a preset
    public static final ModConfigSpec.DoubleValue OUTLINE_RED = BUILDER
            .comment("EN: Red component of outline color (0-1).",
                     "ES: Componente rojo del color del contorno (0-1).")
            .defineInRange("outlineRed", 0.0, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue OUTLINE_GREEN = BUILDER
            .comment("EN: Green component of outline color (0-1).",
                     "ES: Componente verde del color del contorno (0-1).")
            .defineInRange("outlineGreen", 0.0, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue OUTLINE_BLUE = BUILDER
            .comment("EN: Blue component of outline color (0-1).",
                     "ES: Componente azul del color del contorno (0-1).")
            .defineInRange("outlineBlue", 0.4, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue OUTLINE_ALPHA = BUILDER
            .comment("EN: Alpha component of outline color (0-1).",
                     "ES: Componente alfa del color del contorno (0-1).")
            .defineInRange("outlineAlpha", 0.4, 0.0, 1.0);

    // Fixes tab
    public static final ModConfigSpec.BooleanValue SKIP_TORCH_BRIDGING = BUILDER
            .comment("EN: Skip torch bridging to prevent accidental placement.",
                     "ES: Saltar el puentear de antorchas para evitar colocación accidental.")
            .define("skipTorchBridging", true);

    public static final ModConfigSpec.BooleanValue ENABLE_SLAB_ASSIST = BUILDER
            .comment("EN: Enable slab assist for bridging.",
                     "ES: Habilitar la asistencia de losas para puentear.")
            .define("enableSlabAssist", true);

    public static final ModConfigSpec.BooleanValue ENABLE_NON_SOLID_REPLACE = BUILDER
            .comment("EN: Enable non-solid block replacement.",
                     "ES: Habilitar el reemplazo de bloques no sólidos.")
            .define("enableNonSolidReplace", true);

    public static final ModConfigSpec.DoubleValue BRIDGING_SNAP_STRENGTH = BUILDER
            .comment("EN: Strength of bridging snap to grid (0-1).",
                     "ES: Fuerza de ajuste de puentear a cuadrícula (0-1).")
            .defineInRange("bridgingSnapStrength", 1.0, 0.0, 1.0);

    public static final ModConfigSpec.EnumValue<BridgingAdjacency> BRIDGING_ADJACENCY = BUILDER
            .comment("EN: Adjacency checking for bridging placement.",
                     "ES: Comprobación de adyacencia para colocación de puentear.")
            .defineEnum("bridgingAdjacency", BridgingAdjacency.CORNERS);

    public static final ModConfigSpec.EnumValue<SourcePerspective> PERSPECTIVE_LOCK = BUILDER
            .comment("EN: Perspective lock for bridging calculations.",
                     "ES: Bloqueo de perspectiva para cálculos de puentear.")
            .defineEnum("perspectiveLock", SourcePerspective.LET_BRIDGING_MOD_DECIDE);

    // Debug tab
    public static final ModConfigSpec.BooleanValue SHOW_DEBUG_HIGHLIGHT = BUILDER
            .comment("EN: Show debug highlight when bridging.",
                     "ES: Mostrar resaltado de depuración al puentear.")
            .define("showDebugHighlight", true);

    public static final ModConfigSpec.BooleanValue SHOW_NON_BRIDGING_DEBUG_HIGHLIGHT = BUILDER
            .comment("EN: Show non-bridging debug highlight.",
                     "ES: Mostrar resaltado de depuración de no-puentear.")
            .define("showNonBridgingDebugHighlight", false);

    public static final ModConfigSpec.BooleanValue SHOW_DEBUG_TRACE = BUILDER
            .comment("EN: Show debug trace of bridging calculations.",
                     "ES: Mostrar rastro de depuración de cálculos de puentear.")
            .define("showDebugTrace", false);

    public static final ModConfigSpec SPEC = BUILDER.build();
}