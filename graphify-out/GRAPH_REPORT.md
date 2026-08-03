# Graph Report - 26.2  (2026-08-03)

## Corpus Check
- 57 files · ~121,346 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 450 nodes · 596 edges · 127 communities (35 shown, 92 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 41 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `76a14225`
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
1. `ChunkGenManager` - 32 edges
2. `LegacyBlockMap` - 18 edges
3. `SpawnSchematicManager` - 16 edges
4. `SpongeSchematicReader` - 16 edges
5. `Utility Core — Registro de cambios` - 16 edges
6. `PolymorphClientHandler` - 15 edges
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

## Communities (127 total, 92 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.08
Nodes (7): Closing, CraftingInput, PolymorphApi, PolymorphClientHandler, AccessorAbstractContainerScreen, RecipeFinder, RecipePair

### Community 2 - "ChunkGenManager"
Cohesion: 0.12
Nodes (12): Connection, Field, PlayerLoggedOutEvent, RegisterCommandsEvent, ChunkGenManager, DimState, Gson, Level (+4 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.12
Nodes (14): AttachmentType, DeferredRegister, ModAttachments, ModNetwork, PlayerLoggedInEvent, ServerStartedEvent, ServerStoppingEvent, IEventBus (+6 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.15
Nodes (5): CompoundTag, MarkerData, SpawnSchematicManager, SpongeSchematicReader, HolderLookup

### Community 5 - "Config.java"
Cohesion: 0.08
Nodes (21): BooleanValue, Builder, CallbackInfoReturnable, ConfigValue, DamageType, EnumValue, OutpostZeroCompat, MixinCraftingScreen (+13 more)

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
Cohesion: 0.10
Nodes (8): AbstractContainerMenu, ByteBuf, CustomPacketPayload, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData, RegistryFriendlyByteBuf

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
Nodes (16): 0.0.0-beta.1, 1.0.0, 1.10.0, 1.1.0, 1.2.0, 1.3.0, 1.3.1, 1.4.0 (+8 more)

### Community 26 - "Publicación a GitHub (CI/CD)"
Cohesion: 0.23
Nodes (9): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, SpawnPlacementCheck, EventBusSubscriber, SubscribeEvent (+1 more)

### Community 27 - "OutpostZeroCompat.java"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

## Knowledge Gaps
- **51 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.10.0` (+46 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **92 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `UtilityCore.java` to `Publicación a GitHub (CI/CD)`, `PlayerRecipeData`?**
  _High betweenness centrality (0.066) - this node is a cross-community bridge._
- **Why does `ChunkGenManager` connect `ChunkGenManager` to `PlayerRecipeData`?**
  _High betweenness centrality (0.059) - this node is a cross-community bridge._
- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `SyncRecipesPacket`?**
  _High betweenness centrality (0.049) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _51 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.0761904761904762 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.12063492063492064 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.12333333333333334 - nodes in this community are weakly interconnected._