# Utility Core

A library mod for NeoForge (MC 26.2) providing shared utilities and features.

> **Status**: Work in progress port from the [26.1.2 version](https://github.com/santiagolosadaborrajo/utility_core). Features are being ported incrementally; see `CHANGELOG.md` for progress.

## Planned Features (ported from 26.1.2)

- **Recipe Conflict Resolution**: When multiple crafting recipes match the same ingredients, a selector appears in the crafting table. Click an alternative to choose the desired result.
- **Negative Damage Safety**: Negative damage from mod interactions (e.g. Apothic Attributes + Tombstone) is clamped to 0, preventing server crashes.
- **Configurable**: Enable/disable features and configure the maximum number of shown recipes from the in-game config menu or `config/utility_core-common.toml`.
- **Developer API**: `PolymorphApi` for other mods to integrate with the recipe selection system.

## Requirements

- NeoForge 26.2.0.32-beta+ (client and server)

## Build

```bash
gradlew build
```

The jar will be generated at `build/libs/utility_core-<minecraft_version>-neoforge-<version>.jar`.
