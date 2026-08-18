# Utility Core Admin - CurseForge Documentation

## Project Info
- **Project Name**: Utility Core Admin
- **Project Slug**: utility-core-admin
- **Mod ID**: `utility_core_admin`
- **Display Name**: Utility Core Admin
- **Version**: 2.0.0-beta.1 (CurseForge) / 2.0.0 (NeoForge internal)
- **NeoForge Version**: 26.2.0.37-beta
- **Minecraft Version**: 26.2
- **License**: MIT
- **Category**: Server Admin Tools

---

## Description (Long)
Utility Core Admin - Server administration utilities for Minecraft servers.

### Features

**Spawn Schematic**
- Pastes a Sponge/WorldEdit `.schem` structure at world creation (X=0, Z=0)
- Configurable height placement: **SURFACE** (aligns to highest block + offset) or **FIXED** (absolute Y)
- Includes basic lobby schematic embedded; supports external file at `utility_core/spawn_schem/schematic_spawn.schem`
- Sets world spawn inside structure
- Permanent build protection (blocks, explosions) + optional mob spawn prevention
- Runtime toggle: `/utilitycore spawnprotection on|off|status`

**Automatic Chunk Pre-generation (ChunkGen)**
- Generates chunks in spiral from (0,0) when server is empty
- Duty cycle: configurable load seconds / rest seconds (wall-clock)
- Pauses automatically when players join (configurable)
- Persists progress to disk (`utility_core/chunk_pregen/utility_core_chunk_gen.json`)
- Independent per dimension: Overworld, Nether, End
- Commands: `/utilitycore chunkgen status|start|pause|stop|reset`

**Server Rules Enforcement**
- Applies game rules from `utility_core/server_rules.json` on server start
- Idempotent: only changes rules that differ from config
- Format: `{ "rules": { "ruleId": "value" } }`
- Command: `/utilitycore rules apply|status`

**Data Pack Folder Loader**
- Auto-loads all datapacks (`.zip` or folders) from `<game-dir>/datapacks/` into every world
- Works on dedicated servers and single-player
- No per-world enabling needed
- Configurable folder path

---

## Commands
```
/utilitycore chunkgen status|start|pause|stop|reset
/utilitycore rules apply|status
/utilitycore spawnprotection on|off|status
/utilitycore help
```

---

## Configuration
Via `utility_core_admin-common.toml`:

```toml
# Spawn Schematic
enableSpawnSchematic = false
spawnSchematic.heightMode = "SURFACE"  # or "FIXED"
spawnSchematic.surfaceOffset = 70
spawnSchematic.fixedY = 64
spawnSchematic.preventMobSpawns = true
spawnSchematic.protectionEnabled = true

# ChunkGen
chunkGen.enabled = false
chunkGen.chunksPerTick = 100
chunkGen.maxRadius = 0
chunkGen.runWithPlayers = false
chunkGen.dimensionOverworld = true
chunkGen.dimensionNether = false
chunkGen.dimensionEnd = false
chunkGen.loadSeconds = 600
chunkGen.restSeconds = 300

# Data Pack Folder
dataPackFolder.enabled = false
dataPackFolder.path = "datapacks"
```

---

## Spawn Schematic File
- **External**: Place at `utility_core/spawn_schem/schematic_spawn.schem` (takes priority)
- **Embedded**: Basic lobby included in mod resources
- **Format**: Sponge Schematic v2/v3 or legacy WorldEdit (Materials=Alpha)

---

## Requirements
- **Server must NOT pause when empty**: Set `pause-when-empty-seconds=-1` in `server.properties`
- **NeoForge**: 26.2.0.37-beta+
- **Side**: Server (primary) + Client (config screen)

---

## Changelog (2.0.0-beta.1)
- Initial split from monolithic Utility Core
- All admin features preserved from v1.11.x
- Standalone mod with own modId (`utility_core_admin`)
- Embedded default spawn lobby schematic