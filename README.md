# Utility Core

A library mod for NeoForge (MC 26.2) providing shared utilities and features.

## Features

- **Recipe Conflict Resolution**: When multiple crafting recipes match the same ingredients, a selector appears in the crafting table. Click an alternative to choose the desired result.
- **Negative Damage Safety**: Negative damage from mod interactions (e.g. Apothic Attributes + Tombstone) is clamped to 0, preventing server crashes.
- **Corail Tombstone Compatibility**: GUI scale fix, item NBT init fix, and mixin error suppression.
- **OutpostZero Damage Cap**: Limits infection damage to prevent instant death before death events fire.
- **Automatic Chunk Pregeneration (ChunkGen)**: Pre-generates chunks in a spiral pattern when the server is empty.
- **Biome/Dimension Titles**: Shows a title on screen when entering a new biome or dimension. Logic ported from [Traveler's Titles](https://www.curseforge.com/minecraft/mc-mods/travelers-titles) by YUNGNICKYOUNG (LGPLv3).
- **Spawn Schematic (opt-in)**: Pastes a WorldEdit/FAWE `.schem` file at world creation, protects the area permanently, and sets the world spawn point inside the structure.
- **Configurable**: Enable/disable features and configure the maximum number of shown recipes from the in-game config menu or `config/utility_core-common.toml`.
- **Developer API**: `PolymorphApi` for other mods to integrate with the recipe selection system.

## Requirements

- NeoForge 26.2.0.32-beta+ (client and server)

## Spawn Schematic (opt-in)

This feature pastes a WorldEdit/FAWE Sponge Schematic (`.schem`) at world creation, permanently protects the pasted area, and sets the world spawn inside the structure.

**Placement:** `<game-directory>/schematics/schematic_spawn.schem`

> **IMPORTANT:** This only works on a brand-new world save (no existing region files). To apply it to a world that already exists, you **must delete the world save folder** first, then start the server with `enableSpawnSchematic=true`. This is intentional — it prevents the schematic from being applied to a world that has already generated terrain.

## Known Incompatibilities

- **Fast Workbench (fastbench)**: Conflicts with the recipe selection system. Uninstall it to use this feature.

## Build

```bash
gradlew build
```

The jar will be generated at `build/libs/utility_core-<minecraft_version>-neoforge-<version>.jar`.
