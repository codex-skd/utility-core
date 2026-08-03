# Graph Report - 26.2  (2026-08-03)

## Corpus Check
- 56 files · ~120,877 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 449 nodes · 598 edges · 126 communities (34 shown, 92 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 41 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d452a372`
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
- CraftingRecipe
- EventBusSubscriber
- GuiGraphicsExtractor
- ItemStack
- Minecraft
- Pre
- RecipeHolder
- RecipeManager
- SubscribeEvent
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
- BlockPos
- Gson
- Logger
- MinecraftServer
- ServerLevel
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
- CraftingContainer
- CraftingRecipe
- Inject
- ItemStack
- Mixin
- Player
- RecipeHolder
- ResultContainer
- ServerLevel
- Unique
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
- IPayloadContext
- Override
- StreamCodec
- Type
- IPayloadContext
- ItemStack
- Override
- StreamCodec
- Type
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
1. `ChunkGenManager` - 33 edges
2. `LegacyBlockMap` - 18 edges
3. `SpawnSchematicManager` - 16 edges
4. `SpongeSchematicReader` - 16 edges
5. `PolymorphClientHandler` - 15 edges
6. `Utility Core — Registro de cambios` - 15 edges
7. `UtilityCore` - 14 edges
8. `CurseForge — Variables del proyecto` - 13 edges
9. `ServerRulesManager` - 12 edges
10. `Utility Core` - 11 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java
- `UtilityCore` --references--> `ServerRulesManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ServerRulesManager.java

## Import Cycles
- None detected.

## Communities (126 total, 92 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.09
Nodes (7): Closing, CraftingInput, PolymorphClientHandler, AccessorAbstractContainerScreen, MixinCraftingScreen, MixinInventoryScreen, RecipePair

### Community 2 - "ChunkGenManager"
Cohesion: 0.13
Nodes (10): Connection, RegisterCommandsEvent, ChunkGenManager, DimState, Gson, Level, Logger, MinecraftServer (+2 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.12
Nodes (15): AttachmentType, DeferredRegister, ModAttachments, ModNetwork, PlayerLoggedInEvent, PlayerLoggedOutEvent, ServerStartedEvent, ServerStoppingEvent (+7 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.16
Nodes (5): CompoundTag, MarkerData, SpawnSchematicManager, SpongeSchematicReader, HolderLookup

### Community 5 - "Config.java"
Cohesion: 0.09
Nodes (20): BooleanValue, Builder, CallbackInfoReturnable, ConfigValue, DamageType, EnumValue, Field, OutpostZeroCompat (+12 more)

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
Cohesion: 0.08
Nodes (10): AbstractContainerMenu, ByteBuf, CustomPacketPayload, PolymorphApi, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData (+2 more)

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
Cohesion: 0.12
Nodes (15): 0.0.0-beta.1, 1.0.0, 1.1.0, 1.2.0, 1.3.0, 1.3.1, 1.4.0, 1.5.0 (+7 more)

### Community 26 - "Publicación a GitHub (CI/CD)"
Cohesion: 0.22
Nodes (9): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, SpawnPlacementCheck, EventBusSubscriber, SubscribeEvent (+1 more)

### Community 27 - "OutpostZeroCompat.java"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

## Knowledge Gaps
- **50 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.9.0` (+45 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **92 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `UtilityCore.java` to `Publicación a GitHub (CI/CD)`, `PlayerRecipeData`?**
  _High betweenness centrality (0.067) - this node is a cross-community bridge._
- **Why does `ChunkGenManager` connect `ChunkGenManager` to `PlayerRecipeData`, `Config.java`?**
  _High betweenness centrality (0.061) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _50 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08870967741935484 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.13109243697478992 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.12 - nodes in this community are weakly interconnected._
- **Should `Config.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08620689655172414 - nodes in this community are weakly interconnected._