# Utility Core

A library mod for NeoForge (MC 26.1.2) that provides shared utilities and features.

## Features

- **Recipe Conflict Resolution**: When multiple crafting recipes match the same inputs, a selection widget appears in the crafting table GUI. Click an alternative to pick the desired output.
- **Damage Safety**: Negative damage from mod interactions (e.g., Apothic Attributes + Tombstone) is clamped to 0, preventing server crashes.
- **Configurable**: Enable/disable features and configure max displayed recipes via in-game config or `config/utility_core-common.toml`.
- **Developer API**: `PolymorphApi` for other mods to integrate with the recipe selection system.

## Requirements

- NeoForge 26.1.2+ (both client and server)

## Known Incompatibilities

- **Fast Workbench (fastbench)**: Conflicts with the recipe selection system. Remove it to use this feature.

## Build

```bash
gradlew build
```

The jar will be at `build/libs/utility_core-<version>.jar`.
