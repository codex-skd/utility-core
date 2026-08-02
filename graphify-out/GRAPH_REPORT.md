# Graph Report - 26.2  (2026-08-02)

## Corpus Check
- 53 files · ~118,097 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 421 nodes · 524 edges · 132 communities (32 shown, 100 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 37 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `77c51e2f`
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
- MixinTBScreen.java
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
- Gson
- Level
- Logger
- MinecraftServer
- ResourceKey
- EventBusSubscriber
- Pre
- ResourceKey
- SubscribeEvent
- EventBusSubscriber
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
1. `ChunkGenManager` - 26 edges
2. `LegacyBlockMap` - 18 edges
3. `SpawnSchematicManager` - 16 edges
4. `SpongeSchematicReader` - 16 edges
5. `PolymorphClientHandler` - 15 edges
6. `UtilityCore` - 13 edges
7. `Utility Core — Registro de cambios` - 13 edges
8. `CurseForge — Variables del proyecto` - 13 edges
9. `Flujo de trabajo — Utility Core (NeoForge)` - 11 edges
10. `PlayerRecipeData` - 10 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java

## Import Cycles
- None detected.

## Communities (132 total, 100 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.11
Nodes (5): Closing, CraftingInput, PolymorphClientHandler, AccessorAbstractContainerScreen, RecipePair

### Community 2 - "ChunkGenManager"
Cohesion: 0.18
Nodes (4): Field, ChunkGenManager, DimState, RootState

### Community 3 - "PlayerRecipeData"
Cohesion: 0.11
Nodes (16): AttachmentType, DeferredRegister, ModAttachments, ModNetwork, PlayerLoggedInEvent, PlayerLoggedOutEvent, RegisterCommandsEvent, ServerStartedEvent (+8 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.16
Nodes (5): CompoundTag, MarkerData, SpawnSchematicManager, SpongeSchematicReader, HolderLookup

### Community 5 - "Config.java"
Cohesion: 0.08
Nodes (21): BooleanValue, Builder, CallbackInfoReturnable, ConfigValue, DamageType, EnumValue, OutpostZeroCompat, MixinCraftingScreen (+13 more)

### Community 6 - "PolymorphApi.java"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 7 - "MixinCraftingMenu.java"
Cohesion: 0.44
Nodes (4): Biome, Component, BiomeDimensionTitleHandler, LocalPlayer

### Community 9 - "SyncRecipesPacket"
Cohesion: 0.11
Nodes (8): AbstractContainerMenu, ByteBuf, CustomPacketPayload, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData, RegistryFriendlyByteBuf

### Community 10 - ".onExtractBackground"
Cohesion: 0.39
Nodes (4): AddPackFindersEvent, Pack, DataPackFolderLoader, Logger

### Community 11 - "TombstoneErrorHandler"
Cohesion: 0.39
Nodes (5): ErrorAction, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

### Community 13 - "Formato de descripciones CurseForge"
Cohesion: 0.25
Nodes (7): Build, Data Pack Folder (opt-in), Features, Known Incompatibilities, Requirements, Spawn Schematic (opt-in), Utility Core

### Community 14 - "MixinItemInput.java"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.2), Prioridad de instrucciones, Workflow del mod

### Community 15 - "AccessorCraftingMenu.java"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 20 - "Utility Core — Registro de cambios"
Cohesion: 0.14
Nodes (13): 0.0.0-beta.1, 1.0.0, 1.1.0, 1.2.0, 1.3.0, 1.3.1, 1.4.0, 1.5.0 (+5 more)

### Community 26 - "Publicación a GitHub (CI/CD)"
Cohesion: 0.24
Nodes (6): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, SpawnProtectionHandler

### Community 27 - "OutpostZeroCompat.java"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

## Knowledge Gaps
- **44 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.7.0` (+39 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **100 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `UtilityCore.java` to `Publicación a GitHub (CI/CD)`, `PlayerRecipeData`?**
  _High betweenness centrality (0.073) - this node is a cross-community bridge._
- **Why does `ChunkGenManager` connect `ChunkGenManager` to `PlayerRecipeData`?**
  _High betweenness centrality (0.052) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _44 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.10582010582010581 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.11396011396011396 - nodes in this community are weakly interconnected._
- **Should `Config.java` be split into smaller, more focused modules?**
  _Cohesion score 0.07661290322580645 - nodes in this community are weakly interconnected._
- **Should `PolymorphApi.java` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._