# Graph Report - 26.2  (2026-08-09)

## Corpus Check
- 70 files · ~126,516 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 503 nodes · 748 edges · 106 communities (48 shown, 58 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 46 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `44e7aafe`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Flujo de trabajo — Utility Core (NeoForge)
- PolymorphClientHandler.java
- ChunkGenManager
- PlayerRecipeData
- UtilityCore.java
- Config.java
- PolymorphApi.java
- MixinCraftingMenu.java
- CurseForge — Variables del proyecto
- SyncRecipesPacket
- .onExtractBackground
- TombstoneErrorHandler
- Formato de descripciones CurseForge
- MixinItemInput.java
- AccessorCraftingMenu.java
- Utility Core
- gradlew
- Utility Core — Registro de cambios
- Publicación a GitHub (CI/CD)
- OutpostZeroCompat.java
- Level
- Player
- RecipeHolder
- RecipeManager
- RecipeType
- CraftingContainer
- EventBusSubscriber
- Level
- Minecraft
- Post
- ResourceKey
- SubscribeEvent
- EventBusSubscriber
- Pre
- ResourceKey
- SubscribeEvent
- Block
- BlockPos
- BlockState
- Logger
- Accessor
- Mixin
- Accessor
- CraftingContainer
- Mixin
- ResultContainer
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
- Mixin
- Mixin
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
- Inject
- ItemStack
- Mixin
- CallbackInfo
- Inject
- Mixin
- Unique
- IEventBus
- ItemStack
- Level
- RecipeHolder
- RecipeManager
- RecipeType
- ItemStack
- RecipeHolder
- Logger
- Override
- Mod
- ModContainer

## God Nodes (most connected - your core abstractions)
1. `Utility Core — Registro de cambios` - 48 edges
2. `ChunkGenManager` - 32 edges
3. `SpawnSchematicManager` - 24 edges
4. `PolymorphClientHandler` - 18 edges
5. `LegacyBlockMap` - 18 edges
6. `SpongeSchematicReader` - 16 edges
7. `UtilityCore` - 14 edges
8. `CurseForge — Variables del proyecto` - 13 edges
9. `ServerRulesManager` - 12 edges
10. `SyncRecipesPacket` - 11 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java
- `UtilityCore` --references--> `ServerRulesManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ServerRulesManager.java
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java
- `PolymorphClientHandler` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/client/PolymorphClientHandler.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java

## Import Cycles
- None detected.

## Communities (106 total, 58 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.09
Nodes (18): Closing, CraftingInput, AccessorAbstractContainerScreen, MixinCraftingScreen, MixinInventoryScreen, RecipePair, CraftingContainer, CraftingRecipe (+10 more)

### Community 2 - "ChunkGenManager"
Cohesion: 0.07
Nodes (26): AttachmentType, Connection, DeferredRegister, Field, ModAttachments, ModNetwork, PlayerLoggedInEvent, PlayerLoggedOutEvent (+18 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.20
Nodes (12): ByteBuf, CustomPacketPayload, RegistryFriendlyByteBuf, Override, StreamCodec, Type, SelectRecipePacket, ItemStack (+4 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.13
Nodes (10): CompoundTag, SpongeSchematicReader, HolderLookup, BlockPos, Gson, Logger, MinecraftServer, ServerLevel (+2 more)

### Community 5 - "Config.java"
Cohesion: 0.08
Nodes (21): BooleanValue, Builder, CallbackInfoReturnable, ConfigValue, DamageType, EnumValue, OutpostZeroCompat, MixinDamageContainer (+13 more)

### Community 6 - "PolymorphApi.java"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 7 - "MixinCraftingMenu.java"
Cohesion: 0.21
Nodes (8): AddPackFindersEvent, Biome, Component, BiomeDimensionTitleHandler, LocalPlayer, Pack, DataPackFolderLoader, Logger

### Community 8 - "CurseForge — Variables del proyecto"
Cohesion: 0.31
Nodes (8): GameRule, GameRules, JsonObject, Gson, Logger, MinecraftServer, ServerRulesManager, SuppressWarnings

### Community 9 - "SyncRecipesPacket"
Cohesion: 0.12
Nodes (15): AbstractContainerMenu, PlayerRecipeData, CallbackInfo, CraftingContainer, CraftingRecipe, Inject, ItemStack, Mixin (+7 more)

### Community 11 - "TombstoneErrorHandler"
Cohesion: 0.39
Nodes (5): ErrorAction, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

### Community 13 - "Formato de descripciones CurseForge"
Cohesion: 0.17
Nodes (11): Automatic Chunk Pregeneration (ChunkGen), Build, Configuration, Credits, Data Pack Folder (opt-in), Features, Known Incompatibilities, Requirements (+3 more)

### Community 14 - "MixinItemInput.java"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.2), Prioridad de instrucciones, Workflow del mod

### Community 15 - "AccessorCraftingMenu.java"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 20 - "Utility Core — Registro de cambios"
Cohesion: 0.04
Nodes (47): 0.0.0-beta.1, 0.0.0-beta.1, 1.0.0, 1.0.0, 1.10.0, 1.10.0, 1.11.0, 1.11.0 (+39 more)

### Community 26 - "Publicación a GitHub (CI/CD)"
Cohesion: 0.18
Nodes (11): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, SpawnPlacementCheck, BlockPos, EventBusSubscriber (+3 more)

### Community 27 - "OutpostZeroCompat.java"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

## Knowledge Gaps
- **83 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.11.12` (+78 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **58 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `UtilityCore.java` to `Publicación a GitHub (CI/CD)`, `ChunkGenManager`?**
  _High betweenness centrality (0.071) - this node is a cross-community bridge._
- **Why does `ServerRulesManager` connect `CurseForge — Variables del proyecto` to `ChunkGenManager`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _83 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08637873754152824 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.06939890710382514 - nodes in this community are weakly interconnected._
- **Should `UtilityCore.java` be split into smaller, more focused modules?**
  _Cohesion score 0.13333333333333333 - nodes in this community are weakly interconnected._
- **Should `Config.java` be split into smaller, more focused modules?**
  _Cohesion score 0.0766488413547237 - nodes in this community are weakly interconnected._