# Utility Core

A library mod for NeoForge (MC 26.1.2) providing shared utilities and features.

## Features

- **Recipe Conflict Resolution**: When multiple crafting recipes match the same ingredients, a selector appears in the crafting table. Click an alternative to choose the desired result.
- **Negative Damage Safety**: Negative damage from mod interactions (e.g. Apothic Attributes + Tombstone) is clamped to 0, preventing server crashes.
- **Configurable**: Enable/disable features and configure the maximum number of shown recipes from the in-game config menu or `config/utility_core-common.toml`.
- **Developer API**: `PolymorphApi` for other mods to integrate with the recipe selection system.
- **Spawn Schematic (opt-in)**: Pastes a WorldEdit/FAWE `.schem` file at world creation, protects the area permanently, and sets the world spawn point inside the structure.
- **Data Pack Folder (opt-in)**: Loads every datapack (`.zip` or folder) placed in `<game-directory>/datapacks` into every world automatically (dedicated servers and single-player), like the Global Packs mod. Packs are always enabled, no per-world toggle needed.

## Requirements

- NeoForge 26.1.2+ (client and server)

## Spawn Schematic (opt-in)

This feature pastes a WorldEdit/FAWE schematic at world creation, permanently protects the pasted area, and sets the world spawn inside the structure. The structure is centered on the vanilla world spawn. Both Sponge `.schem` (v2/v3) and the legacy WorldEdit/MCEdit `.schematic` format are supported and auto-detected.

**Placement:** `<game-directory>/schematics/schematic_spawn.schem`

**Bundled default:** The mod ships a lobby schematic and extracts it to the path above automatically on the first server start if the file is missing, so the feature works out of the box. It is a legacy WorldEdit `.schematic` designed to float **70 blocks above the terrain**. To use your own schematic, simply replace `schematic_spawn.schem` with your file (keep the same filename) **before** creating/regenerating the world.

**Height placement:** controlled by three config options:

- `spawnSchematic.heightMode = SURFACE` (default): aligns the **bottom** of the schematic to the highest ground block under its footprint, plus `spawnSchematic.surfaceOffset` (default `70`), so it floats above the terrain instead of being buried at the world's minimum Y (-64).
- `spawnSchematic.surfaceOffset = 70`: extra blocks **above the ground** where the bottom is placed. `0` = flush with the ground, positive = raised, negative = buried.
- `spawnSchematic.heightMode = FIXED` + `spawnSchematic.fixedY = 64`: places the bottom at an **absolute Y coordinate** instead of following the terrain.

> **IMPORTANT:** This only works on a brand-new world save (no existing region files). To apply it to a world that already exists, you **must delete the world save folder** first, then start the server with `enableSpawnSchematic=true`. This is intentional — it prevents the schematic from being applied to a world that has already generated terrain.

## Known Incompatibilities

- **Fast Workbench (fastbench)**: Conflicts with the recipe selection system. Uninstall it to use this feature.

## Build

```bash
gradlew build
```

The jar will be generated at `build/libs/utility_core-<minecraft_version>-neoforge-<version>.jar`.
