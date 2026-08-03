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

    public static final ModConfigSpec.IntValue TITLE_VERTICAL_OFFSET = BUILDER
            .comment("EN: Shifts vanilla title/subtitle text vertically from screen center. Positive values move the text up (toward the top of the screen); negative values move it down.",
                     "ES: Desplaza verticalmente el título/subtítulo vanilla desde el centro de la pantalla. Valores positivos mueven el texto hacia arriba; negativos hacia abajo.")
            .defineInRange("titleVerticalOffset", 75, -200, 200);

    public static final ModConfigSpec.BooleanValue ENABLE_SPAWN_SCHEMATIC = BUILDER
            .comment("EN: Pastes a WorldEdit/FAWE Sponge Schematic (.schem) at world creation, centered on world coordinates X=0, Z=0, protects the area permanently, and sets the world spawn inside the structure. This only takes effect on a brand-new world save (no existing region files). It does nothing on a world that already existed before this option was enabled. To apply to an already-existing world, the server operator must delete the world save folder first, then start the server with this enabled. The schematic is read from <game-dir>/utility_core/spawn_schem/schematic_spawn.schem; on first start, if the file is missing, a bundled example schematic is extracted automatically to that path, so the feature works out of the box. To use your own schematic, just replace that file (keep the same name) before creating/regenerating the world.",
                     "ES: Pega un esquema .schem de WorldEdit/FAWE al crear un mundo nuevo, centrado en las coordenadas X=0, Z=0 del mundo, protege el área permanentemente y establece el punto de aparición dentro de la estructura. Solo funciona en mundos nuevos sin archivos de región existentes. Para aplicar a un mundo ya existente, el operador debe eliminar la carpeta del mundo primero y luego iniciar el servidor con esta opción activada. El esquema se lee de <game-dir>/utility_core/spawn_schem/schematic_spawn.schem; en el primer arranque, si el archivo no existe, se extrae automáticamente un esquema de ejemplo empaquetado en el mod a esa ruta, de modo que la feature funciona \"out of the box\". Para usar un esquema propio, basta con sustituir ese archivo (con el mismo nombre) antes de borrar/regenerar el mundo.")
            .define("enableSpawnSchematic", false);

    public enum SpawnHeightMode {
        SURFACE,
        FIXED
    }

    public static final ModConfigSpec.EnumValue<SpawnHeightMode> SPAWN_SCHEMATIC_HEIGHT_MODE = BUILDER
            .comment("EN: How to determine the Y position when pasting the spawn schematic. SURFACE aligns the bottom of the schematic to the highest ground block under its footprint (plus spawnSchematic.surfaceOffset); FIXED places the bottom at the absolute coordinate spawnSchematic.fixedY.",
                     "ES: Cómo determinar la posición Y al pegar el esquema de spawn. SURFACE alinea la base del esquema con el bloque de suelo más alto bajo su huella (más spawnSchematic.surfaceOffset); FIXED coloca la base en la coordenada absoluta spawnSchematic.fixedY.")
            .defineEnum("spawnSchematic.heightMode", SpawnHeightMode.SURFACE);

    public static final ModConfigSpec.IntValue SPAWN_SCHEMATIC_SURFACE_OFFSET = BUILDER
            .comment("EN: Only used when spawnSchematic.heightMode=SURFACE. Extra blocks above the ground where the bottom of the schematic is placed. 0 = flush on the ground, positive = raised above the ground, negative = buried. The bundled default lobby schematic is designed to float 70 blocks above the surface.",
                     "ES: Solo se usa cuando spawnSchematic.heightMode=SURFACE. Bloques extra sobre el suelo donde se coloca la base del esquema. 0 = a ras del suelo, positivo = elevado sobre el suelo, negativo = enterrado. El esquema de lobby incluido por defecto está diseñado para flotar 70 bloques por encima de la superficie.")
            .defineInRange("spawnSchematic.surfaceOffset", 70, -2048, 2048);

    public static final ModConfigSpec.IntValue SPAWN_SCHEMATIC_FIXED_Y = BUILDER
            .comment("EN: Only used when spawnSchematic.heightMode=FIXED. Absolute Y coordinate where the bottom of the schematic is placed.",
                     "ES: Solo se usa cuando spawnSchematic.heightMode=FIXED. Coordenada Y absoluta donde se coloca la base del esquema.")
            .defineInRange("spawnSchematic.fixedY", 64, -64, 320);

    public static final ModConfigSpec.BooleanValue SPAWN_SCHEMATIC_PREVENT_MOB_SPAWNS = BUILDER
            .comment("EN: Prevents natural mob spawns inside the spawn schematic bounds. Useful when the schematic has no lighting.",
                     "ES: Evita la aparici\u00f3n natural de mobs dentro de los l\u00edmites del esquema de spawn. \u00datil cuando el esquema no tiene iluminaci\u00f3n.")
            .define("spawnSchematic.preventMobSpawns", true);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_ENABLED = BUILDER
            .comment("EN: IMPORTANT: The server must not pause while empty or chunk generation will stall and logins may time out. Set pause-when-empty-seconds=-1 in server.properties.",
                     "ES: IMPORTANTE: El servidor no debe pausarse estando vac\u00edo o la generaci\u00f3n de chunks se detendr\u00e1 y los logins podr\u00edan agotar el tiempo. Pon pause-when-empty-seconds=-1 en server.properties.",
                     "EN: Enables automatic chunk generation. When enabled, chunks are generated in a spiral pattern from (0,0) when no players are online.",
                     "ES: Activa la generaci\u00f3n autom\u00e1tica de chunks. Cuando est\u00e1 activada, los chunks se generan en espiral desde (0,0) cuando no hay jugadores conectados.")
            .define("chunkGen.enabled", false);

    public static final ModConfigSpec.IntValue CHUNK_GEN_CHUNKS_PER_TICK = BUILDER
            .comment("EN: Number of chunks to generate per server tick (hard cap of 100 enforced in code). Higher values generate faster but may cause lag. Recommended: 1-8.",
                     "ES: N\u00famero de chunks a generar por tick del servidor (tope fijo de 100 aplicado en c\u00f3digo). Valores m\u00e1s altos generan m\u00e1s r\u00e1pido pero pueden causar lag. Recomendado: 1-8.")
            .defineInRange("chunkGen.chunksPerTick", 100, 1, 100);

    public static final ModConfigSpec.IntValue CHUNK_GEN_MAX_RADIUS = BUILDER
            .comment("EN: Maximum radius in chunks to generate (0 = unlimited). Stops when this radius is reached.",
                     "ES: Radio m\u00e1ximo en chunks a generar (0 = ilimitado). Se detiene al alcanzar este radio.")
            .defineInRange("chunkGen.maxRadius", 0, 0, 100000);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_RUN_WITH_PLAYERS = BUILDER
            .comment("EN: If true, generation continues even with players online. If false, pauses when players join.",
                     "ES: Si es true, la generaci\u00f3n contin\u00faa aunque haya jugadores. Si es false, se pausa cuando entran jugadores.")
            .define("chunkGen.runWithPlayers", false);

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

    public static final ModConfigSpec.IntValue CHUNK_GEN_LOAD_SECONDS = BUILDER
            .comment("EN: Duty cycle: seconds of chunk generation before a rest period. Default 600 = 10 minutes.",
                     "ES: Ciclo de trabajo: segundos de generaci\u00f3n de chunks antes de un periodo de descanso. Por defecto 600 = 10 minutos.")
            .defineInRange("chunkGen.loadSeconds", 600, 60, 86400);

    public static final ModConfigSpec.IntValue CHUNK_GEN_REST_SECONDS = BUILDER
            .comment("EN: Duty cycle: seconds of rest (no generation) after each load period, to give the server a break. Default 300 = 5 minutes.",
                     "ES: Ciclo de trabajo: segundos de descanso (sin generaci\u00f3n) tras cada periodo de carga, para dar un respiro al servidor. Por defecto 300 = 5 minutos.")
            .defineInRange("chunkGen.restSeconds", 300, 0, 86400);

    public static final ModConfigSpec.BooleanValue DATA_PACK_FOLDER_ENABLED = BUILDER
            .comment("EN: Loads every datapack (.zip or folder) found in <game-dir>/<dataPackFolder.path> into every world automatically, without enabling it per world. Works on dedicated servers and single-player worlds.",
                     "ES: Carga autom\u00e1ticamente todos los datapacks (.zip o carpeta) encontrados en <game-dir>/<dataPackFolder.path> en todos los mundos, sin necesidad de activarlos mundo a mundo. Funciona en servidores dedicados y mundos de un solo jugador.")
            .define("dataPackFolder.enabled", false);

    public static final ModConfigSpec.ConfigValue<String> DATA_PACK_FOLDER_PATH = BUILDER
            .comment("EN: Folder scanned for datapacks (relative to the game directory) when dataPackFolder.enabled is true.",
                     "ES: Carpeta que se escanea en busca de datapacks (relativa al directorio del juego) cuando dataPackFolder.enabled est\u00e1 activado.")
            .define("dataPackFolder.path", "datapacks");

    static final ModConfigSpec SPEC = BUILDER.build();
}
