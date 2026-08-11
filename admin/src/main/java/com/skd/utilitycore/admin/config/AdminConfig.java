package com.skd.utilitycore.admin.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AdminConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Spawn Schematic
    public static final ModConfigSpec.BooleanValue ENABLE_SPAWN_SCHEMATIC = BUILDER
            .comment("EN: Pastes a WorldEdit/FAWE Sponge Schematic (.schem) at world creation, centered on world coordinates X=0, Z=0, protects the area permanently, and sets the world spawn inside the structure. This only takes effect on a brand-new world save (no existing region files). It does nothing on a world that already existed before this option was enabled. To apply to an already-existing world, the server operator must delete the world save folder first, then start the server with this enabled. The schematic is read from <game-dir>/utility_core/spawn_schem/schematic_spawn.schem; it is NOT bundled in the mod jar, so you must place your own .schem file there before creating/regenerating the world.",
                     "ES: Pega un esquema .schem de WorldEdit/FAWE al crear un mundo nuevo, centrado en las coordenadas X=0, Z=0 del mundo, protege el área permanentemente y establece el punto de aparición dentro de la estructura. Solo funciona en mundos nuevos sin archivos de región existentes. Para aplicar a un mundo ya existente, el operador debe eliminar la carpeta del mundo primero y luego iniciar el servidor con esta opción activada. El esquema se lee de <game-dir>/utility_core/spawn_schem/schematic_spawn.schem; NO viene empaquetado en el JAR del mod, así que debes colocar tu propio archivo .schem en esa ruta antes de borrar/regenerar el mundo.")
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
                     "ES: Evita la aparición natural de mobs dentro de los límites del esquema de spawn. Útil cuando el esquema no tiene iluminación.")
            .define("spawnSchematic.preventMobSpawns", true);

    public static final ModConfigSpec.BooleanValue SPAWN_SCHEMATIC_PROTECTION_ENABLED = BUILDER
            .comment("EN: Enables the build protection of the spawn schematic area (no block breaking/placing, no explosions). You can toggle it at runtime with /utilitycore spawnprotection on|off. Use 'off' to temporarily build inside the spawn area, then turn it back on.",
                     "ES: Activa la protección de construcción de la zona del esquema de spawn (no se pueden romper/colocar bloques ni hay explosiones). Puedes activarla/desactivarla en caliente con /utilitycore spawnprotection on|off. Usa 'off' para construir temporalmente dentro de la zona de spawn y luego vuelve a activarla.")
            .define("spawnSchematic.protectionEnabled", true);

    // Chunk Gen
    public static final ModConfigSpec.BooleanValue CHUNK_GEN_ENABLED = BUILDER
            .comment("EN: IMPORTANT: The server must not pause while empty or chunk generation will stall and logins may time out. Set pause-when-empty-seconds=-1 in server.properties.",
                     "ES: IMPORTANTE: El servidor no debe pausarse estando vacío o la generación de chunks se detendrá y los logins podrían agotar el tiempo. Pon pause-when-empty-seconds=-1 en server.properties.",
                     "EN: Enables automatic chunk generation. When enabled, chunks are generated in a spiral pattern from (0,0) when no players are online.",
                     "ES: Activa la generación automática de chunks. Cuando está activada, los chunks se generan en espiral desde (0,0) cuando no hay jugadores conectados.")
            .define("chunkGen.enabled", false);

    public static final ModConfigSpec.IntValue CHUNK_GEN_CHUNKS_PER_TICK = BUILDER
            .comment("EN: Number of chunks to generate per server tick (hard cap of 100 enforced in code). Higher values generate faster but may cause lag. Recommended: 1-8.",
                     "ES: Número de chunks a generar por tick del servidor (tope fijo de 100 aplicado en código). Valores más altos generan más rápido pero pueden causar lag. Recomendado: 1-8.")
            .defineInRange("chunkGen.chunksPerTick", 100, 1, 100);

    public static final ModConfigSpec.IntValue CHUNK_GEN_MAX_RADIUS = BUILDER
            .comment("EN: Maximum radius in chunks to generate (0 = unlimited). Stops when this radius is reached.",
                     "ES: Radio máximo en chunks a generar (0 = ilimitado). Se detiene al alcanzar este radio.")
            .defineInRange("chunkGen.maxRadius", 0, 0, 100000);

    public static final ModConfigSpec.BooleanValue CHUNK_GEN_RUN_WITH_PLAYERS = BUILDER
            .comment("EN: If true, generation continues even with players online. If false, pauses when players join.",
                     "ES: Si es true, la generación continúa aunque haya jugadores. Si es false, se pausa cuando entran jugadores.")
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
                     "ES: Ciclo de trabajo: segundos de generación de chunks antes de un periodo de descanso. Por defecto 600 = 10 minutos.")
            .defineInRange("chunkGen.loadSeconds", 600, 60, 86400);

    public static final ModConfigSpec.IntValue CHUNK_GEN_REST_SECONDS = BUILDER
            .comment("EN: Duty cycle: seconds of rest (no generation) after each load period, to give the server a break. Default 300 = 5 minutes.",
                     "ES: Ciclo de trabajo: segundos de descanso (sin generación) tras cada periodo de carga, para dar un respiro al servidor. Por defecto 300 = 5 minutos.")
            .defineInRange("chunkGen.restSeconds", 300, 0, 86400);

    // Data Pack Folder
    public static final ModConfigSpec.BooleanValue DATA_PACK_FOLDER_ENABLED = BUILDER
            .comment("EN: Loads every datapack (.zip or folder) found in <game-dir>/<dataPackFolder.path> into every world automatically, without enabling it per world. Works on dedicated servers and single-player worlds.",
                     "ES: Carga automáticamente todos los datapacks (.zip o carpeta) encontrados en <game-dir>/<dataPackFolder.path> en todos los mundos, sin necesidad de activarlos mundo a mundo. Funciona en servidores dedicados y mundos de un solo jugador.")
            .define("dataPackFolder.enabled", false);

    public static final ModConfigSpec.ConfigValue<String> DATA_PACK_FOLDER_PATH = BUILDER
            .comment("EN: Folder scanned for datapacks (relative to the game directory) when dataPackFolder.enabled is true.",
                     "ES: Carpeta que se escanea en busca de datapacks (relativa al directorio del juego) cuando dataPackFolder.enabled está activado.")
            .define("dataPackFolder.path", "datapacks");

    static final ModConfigSpec SPEC = BUILDER.build();
}