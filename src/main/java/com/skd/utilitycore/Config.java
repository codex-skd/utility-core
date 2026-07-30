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

    public static final ModConfigSpec.BooleanValue ENABLE_BIOME_DIMENSION_TITLES = BUILDER
            .comment("EN: Shows a title on screen when entering a new biome or dimension.",
                     "ES: Muestra un título en pantalla al entrar en un bioma o dimensión nuevos.")
            .define("enableBiomeDimensionTitles", true);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_ENABLED = BUILDER
            .comment("EN: Enables automatic chunk generation. When enabled, chunks are generated in a spiral pattern from (0,0) when no players are online.",
                     "ES: Activa la generaci\u00f3n autom\u00e1tica de chunks. Cuando est\u00e1 activada, los chunks se generan en espiral desde (0,0) cuando no hay jugadores conectados.")
            .define("chunkGen.enabled", false);

    public static final ModConfigSpec.IntValue CHUNK_GEN_CHUNKS_PER_TICK = BUILDER
            .comment("EN: Number of chunks to generate per server tick. Higher values generate faster but may cause lag.",
                     "ES: N\u00famero de chunks a generar por tick del servidor. Valores m\u00e1s altos generan m\u00e1s r\u00e1pido pero pueden causar lag.")
            .defineInRange("chunkGen.chunksPerTick", 1, 1, 300);

    public static final ModConfigSpec.IntValue CHUNK_GEN_MAX_RADIUS = BUILDER
            .comment("EN: Maximum radius in chunks to generate (0 = unlimited). Stops when this radius is reached.",
                     "ES: Radio m\u00e1ximo en chunks a generar (0 = ilimitado). Se detiene al alcanzar este radio.")
            .defineInRange("chunkGen.maxRadius", 0, 0, 100000);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_RUN_WITH_PLAYERS = BUILDER
            .comment("EN: If true, generation continues even with players online. If false, pauses when players join.",
                     "ES: Si es true, la generaci\u00f3n contin\u00faa aunque haya jugadores. Si es false, se pausa cuando entran jugadores.")
            .define("chunkGen.runWithPlayers", false);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_KEEP_ALIVE = BUILDER
            .comment("EN: Prevents the server from pausing (60s idle timeout) while ChunkGen is running. May cause higher CPU usage on empty servers.",
                     "ES: Evita que el servidor se pause (timeout de 60s inactivo) mientras ChunkGen genera. Puede aumentar el uso de CPU en servidores vac\u00edos.")
            .define("chunkGen.keepAlive", true);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_DIMENSION_OVERWORLD = BUILDER
            .comment("EN: Pre-generate chunks in the Overworld.",
                     "ES: Pregenerar chunks en el Overworld.")
            .define("chunkGen.dimensionOverworld", true);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_DIMENSION_NETHER = BUILDER
            .comment("EN: Pre-generate chunks in the Nether.",
                     "ES: Pregenerar chunks en el Nether.")
            .define("chunkGen.dimensionNether", false);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_DIMENSION_END = BUILDER
            .comment("EN: Pre-generate chunks in The End.",
                     "ES: Pregenerar chunks en el End.")
            .define("chunkGen.dimensionEnd", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
