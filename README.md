# Utility Core

A library mod for NeoForge (MC 26.2) providing shared utilities and features.

## Features

- **Recipe Conflict Resolution**: When multiple crafting recipes match the same ingredients, a selector appears in the crafting table. Click an alternative to choose the desired result.
- **Negative Damage Safety**: Negative damage from mod interactions (e.g. Apothic Attributes + Tombstone) is clamped to 0, preventing server crashes.
- **Corail Tombstone Compatibility**: GUI scale fix, item NBT init fix, and mixin error suppression.
- **OutpostZero Damage Cap**: Limits infection damage to prevent instant death before death events fire.
- **Automatic Chunk Pregeneration (ChunkGen)**: Pre-generates chunks in a spiral pattern when the server is empty.
- **Biome/Dimension Titles**: Shows a title on screen when entering a new biome or dimension. Logic ported from [Traveler's Titles](https://www.curseforge.com/minecraft/mc-mods/travelers-titles) by YUNGNICKYOUNG (LGPLv3).
- **Spawn Schematic (opt-in)**: Pastes a WorldEdit/FAWE `.schem` file at world creation centered on world coordinates (0,0), protects the area permanently, and sets the world spawn point inside the structure. A bundled example schematic is extracted automatically on first start.
- **Data Pack Folder (opt-in)**: Loads every datapack (`.zip` or folder) placed in `<game-directory>/datapacks` into every world automatically (dedicated servers and single-player), like the Global Packs mod. Packs are always enabled, no per-world toggle needed.
- **Configurable**: Enable/disable features and configure the maximum number of shown recipes from the in-game config menu or `config/utility_core-common.toml`.
- **Developer API**: `PolymorphApi` for other mods to integrate with the recipe selection system.

## Requirements

- NeoForge 26.2.0.32-beta+ (client and server)

## Spawn Schematic (opt-in)

This feature pastes a WorldEdit/FAWE schematic at world creation, permanently protects the pasted area, and sets the world spawn inside the structure. The structure is centered on world coordinates **X=0, Z=0**. Both Sponge `.schem` (v2/v3) and the legacy WorldEdit/MCEdit `.schematic` format are supported and auto-detected.

**Placement:** `<game-directory>/utility_core/spawn_schem/schematic_spawn.schem`

**Bundled default:** The mod ships a lobby schematic and extracts it to the path above automatically on the first server start if the file is missing, so the feature works out of the box. It is a legacy WorldEdit `.schematic` designed to float **70 blocks above the terrain**. To use your own schematic, simply replace `schematic_spawn.schem` with your file (keep the same filename) **before** creating/regenerating the world.

**Height placement:** controlled by three config options:

- `spawnSchematic.heightMode = SURFACE` (default): aligns the **bottom** of the schematic to the highest ground block under its footprint, plus `spawnSchematic.surfaceOffset` (default `70`), so it floats above the terrain instead of being buried at the world's minimum Y (-64).
- `spawnSchematic.surfaceOffset = 70`: extra blocks **above the ground** where the bottom is placed. `0` = flush with the ground, positive = raised, negative = buried.
- `spawnSchematic.heightMode = FIXED` + `spawnSchematic.fixedY = 64`: places the bottom at an **absolute Y coordinate** instead of following the terrain.

> **IMPORTANT:** This only works on a brand-new world save (no existing region files). To apply it to a world that already exists, you **must delete the world save folder** first, then start the server with `enableSpawnSchematic=true`. This is intentional — it prevents the schematic from being applied to a world that has already generated terrain.

## Data Pack Folder (opt-in)

Loads every datapack (`.zip` file or folder containing a `pack.mcmeta`) placed in `<game-directory>/datapacks` into every world automatically — no per-world toggle. Works on dedicated servers and in single-player (the integrated server picks it up too). A client connecting to a dedicated server receives the datapacks from the server, as usual.

- `dataPackFolder.enabled = true` enables the loader.
- `dataPackFolder.path = "datapacks"` is the folder scanned, relative to the game directory.
- Invalid datapacks (missing/broken `pack.mcmeta`) are skipped.

## Automatic Chunk Pregeneration (ChunkGen)

Pre-generates chunks in a spiral pattern from (0,0) when the server is empty, so exploration doesn't lag. Multi-dimension support.

- **Auto-pause**: stops when a player joins, resumes when they leave (unless `chunkGen.runWithPlayers`).
- **Progress persistence**: survives restarts and crashes.
- **Commands**: `/utilitycore chunkgen status`, `start`, `pause`, `stop`, `reset`.

## Configuration

All options live in `config/utility_core-common.toml` (also editable from the in-game mod menu). Every feature can be toggled individually.

| Config | Default | Description |
|---|---|---|
| `enableCraftingRecipeSelector` | true | Recipe conflict selector in the crafting grid. Incompatible with Fast Workbench. |
| `maxRecipesDisplayed` | 16 | Max alternative recipes shown in the selector (1-64). |
| `logDetectedConflicts` | false | Log recipe conflicts to the console. |
| `enableNegativeDamageFix` | true | Clamps negative damage to 0 (Apothic + Tombstone crash fix). |
| `enableBiomeDimensionTitles` | true | Title when entering a new biome/dimension (client). |
| `titleVerticalOffset` | 75 | Vertical shift of the title from screen center (-200..200). |
| `enableTombstoneGuiScaleFix` | true | Restores GUI scale after Tombstone screens. |
| `enableTombstoneItemInitFix` | true | Fixes NBT on Tombstone items given via `/give`. |
| `enableTombstoneErrorHandler` | true | Suppresses Tombstone mixin errors at startup. |
| `enableOutpostZeroDamageCap` | true | Caps OutpostZero infection damage to prevent instant death. |
| `enableSpawnSchematic` | false | Paste + protect a spawn schematic on new worlds. |
| `spawnSchematic.heightMode` | SURFACE | `SURFACE` = align to ground, `FIXED` = absolute Y. |
| `spawnSchematic.surfaceOffset` | 70 | Blocks above the ground in `SURFACE` mode. |
| `spawnSchematic.fixedY` | 64 | Absolute Y in `FIXED` mode. |
| `chunkGen.enabled` | false | Enable automatic chunk pregeneration. |
| `chunkGen.chunksPerTick` | 1 | Chunks generated per tick (1-300). |
| `chunkGen.maxRadius` | 0 | Max radius in chunks (0 = unlimited). |
| `chunkGen.dimensionOverworld` | true | Pre-generate the Overworld. |
| `chunkGen.dimensionNether` | false | Pre-generate the Nether. |
| `chunkGen.dimensionEnd` | false | Pre-generate The End. |
| `chunkGen.runWithPlayers` | false | Keep generating while players are online. |
| `chunkGen.keepAlive` | true | Prevent the 60s idle pause while generating. |
| `dataPackFolder.enabled` | false | Auto-load global datapacks into every world. |
| `dataPackFolder.path` | datapacks | Datapack folder, relative to the game directory. |

## Known Incompatibilities

- **Fast Workbench (fastbench)**: Conflicts with the recipe selection system. Uninstall it to use this feature.

## Build

```bash
gradlew build
```

The jar will be generated at `build/libs/utility_core-<minecraft_version>-neoforge-<version>.jar`.

## Credits

- Biome/Dimension Titles logic ported from [Traveler's Titles](https://www.curseforge.com/minecraft/mc-mods/travelers-titles) by YUNGNICKYOUNG (LGPLv3).
- Developed by **Stalking Dragons** — [https://codex.skdragons.com/](https://codex.skdragons.com/).
