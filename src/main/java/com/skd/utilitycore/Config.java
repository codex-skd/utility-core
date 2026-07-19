package com.skd.utilitycore;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_CRAFTING_RECIPE_SELECTOR = BUILDER
            .comment("EN: Enable the recipe selector widget in the crafting table GUI when multiple recipes match the same inputs",
                     "ES: Activa el selector de recetas en la mesa de crafteo cuando varias recetas coinciden con los mismos ingredientes")
            .define("enableCraftingRecipeSelector", true);

    public static final ModConfigSpec.IntValue MAX_RECIPES_DISPLAYED = BUILDER
            .comment("EN: Maximum number of alternative recipes to display in the selector",
                     "ES: N\u00famero m\u00e1ximo de recetas alternativas a mostrar en el selector")
            .defineInRange("maxRecipesDisplayed", 16, 1, 64);

    public static final ModConfigSpec.BooleanValue LOG_DETECTED_CONFLICTS = BUILDER
            .comment("EN: Log recipe conflicts to the console for debugging",
                     "ES: Registra en consola los conflictos de recetas para depuraci\u00f3n")
            .define("logDetectedConflicts", false);

    public static final ModConfigSpec.BooleanValue ENABLE_TOMBSTONE_GUI_SCALE_FIX = BUILDER
            .comment("EN: Prevents Corail Tombstone from forcing GUI scale to 4 when opening its menus. Requires Tombstone to be present.",
                     "ES: Evita que Corail Tombstone fije la escala de GUI a 4 al abrir sus men\u00fas. Requiere que Tombstone est\u00e9 presente.")
            .define("enableTombstoneGuiScaleFix", true);

    public static final ModConfigSpec.BooleanValue ENABLE_TOMBSTONE_ITEM_INIT_FIX = BUILDER
            .comment("EN: Properly initializes NBT data for Tombstone items (lollipop, magic_scroll) when obtained via /give. Requires Tombstone to be present.",
                     "ES: Inicializa correctamente los datos NBT de \u00edtems de Tombstone (lollipop, magic_scroll) al obtenerlos por /give. Requiere que Tombstone est\u00e9 presente.")
            .define("enableTombstoneItemInitFix", true);

    public static final ModConfigSpec.BooleanValue ENABLE_TOMBSTONE_ERROR_HANDLER = BUILDER
            .comment("EN: Suppresses mixin errors from Corail Tombstone's ItemInputMixin to prevent crashes on startup. Requires Tombstone to be present.",
                     "ES: Suprime errores de mixin de ItemInputMixin de Corail Tombstone para evitar crashes al inicio. Requiere que Tombstone est\u00e9 presente.")
            .define("enableTombstoneErrorHandler", true);

    public static final ModConfigSpec.BooleanValue ENABLE_NEGATIVE_DAMAGE_FIX = BUILDER
            .comment("EN: Prevents server crash from 'Damage cannot be negative' IllegalArgumentException. Applies to any mod that produces negative damage values.",
                     "ES: Evita el crash del servidor por IllegalArgumentException 'Damage cannot be negative'. Aplica a cualquier mod que produzca valores de da\u00f1o negativos.")
            .define("enableNegativeDamageFix", true);

    public static final ModConfigSpec.BooleanValue ENABLE_OUTPOSTZERO_DAMAGE_CAP = BUILDER
            .comment("EN: Caps OutpostZero infection damage to 10000 to prevent armor destruction before death events fire. Requires OutpostZero to be present.",
                     "ES: Limita el da\u00f1o de infecci\u00f3n de OutpostZero a 10000 para evitar destrucci\u00f3n de armadura antes de que salten los eventos de muerte. Requiere que OutpostZero est\u00e9 presente.")
            .define("enableOutpostZeroDamageCap", true);

    public static final ModConfigSpec.BooleanValue ENABLE_END_DRAGON_RESPAWN = BUILDER
            .comment("EN: Automatically respawns the Ender Dragon with full vanilla animation (obsidian towers, crystals) on server start if it was previously killed.",
                     "ES: Reaparece autom\u00e1ticamente al Ender Dragon con animaci\u00f3n vanilla completa (torres de obsidiana, cristales) al iniciar el servidor si fue asesinado previamente.")
            .define("enableEndDragonRespawn", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
