package com.skd.utilitycore.fixes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FixesConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_TOMBSTONE_GUI_SCALE_FIX = BUILDER
            .comment("EN: Prevents Corail Tombstone from forcing GUI scale to 4 when opening its menus. Requires Tombstone to be present.",
                     "ES: Evita que Corail Tombstone fije la escala de GUI a 4 al abrir sus menús. Requiere que Tombstone esté presente.")
            .define("enableTombstoneGuiScaleFix", true);

    public static final ModConfigSpec.BooleanValue ENABLE_TOMBSTONE_ITEM_INIT_FIX = BUILDER
            .comment("EN: Properly initializes NBT data for Tombstone items (lollipop, magic_scroll) when obtained via /give. Requires Tombstone to be present.",
                     "ES: Inicializa correctamente los datos NBT de ítems de Tombstone (lollipop, magic_scroll) al obtenerlos por /give. Requiere que Tombstone esté presente.")
            .define("enableTombstoneItemInitFix", true);

    public static final ModConfigSpec.BooleanValue ENABLE_TOMBSTONE_ERROR_HANDLER = BUILDER
            .comment("EN: Suppresses mixin errors from Corail Tombstone's ItemInputMixin to prevent crashes on startup. Requires Tombstone to be present.",
                     "ES: Suprime errores de mixin de ItemInputMixin de Corail Tombstone para evitar crashes al inicio. Requiere que Tombstone esté presente.")
            .define("enableTombstoneErrorHandler", true);

    public static final ModConfigSpec.BooleanValue ENABLE_NEGATIVE_DAMAGE_FIX = BUILDER
            .comment("EN: Prevents server crash from 'Damage cannot be negative' IllegalArgumentException. Applies to any mod that produces negative damage values.",
                     "ES: Evita el crash del servidor por IllegalArgumentException 'Damage cannot be negative'. Aplica a cualquier mod que produzca valores de daño negativos.")
            .define("enableNegativeDamageFix", true);

    public static final ModConfigSpec.BooleanValue ENABLE_OUTPOSTZERO_DAMAGE_CAP = BUILDER
            .comment("EN: Caps OutpostZero infection damage to 10000 to prevent armor destruction before death events fire. Requires OutpostZero to be present.",
                     "ES: Limita el daño de infección de OutpostZero a 10000 para evitar destrucción de armadura antes de que salten los eventos de muerte. Requiere que OutpostZero esté presente.")
            .define("enableOutpostZeroDamageCap", true);

    public static final ModConfigSpec.BooleanValue ENABLE_BLOCKENTITY_MISMATCH_FIX = BUILDER
            .comment("EN: Prevents crashes from stale block entity NBT by removing orphaned block entity data when block/entity type mismatch is detected.",
                     "ES: Evita los crashes por NBT de bloque de entidad obsoleto eliminando los datos de bloque de entidad huérfanos cuando se detecta una discrepancia de tipo.")
            .define("enableBlockentityMismatchFix", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}