# Graph Report - 26.1.2  (2026-08-03)

## Corpus Check
- 76 files · ~125,239 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 511 nodes · 686 edges · 135 communities (53 shown, 82 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 42 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `0e3f5f5e`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- .onExtractBackground
- PolymorphClientHandler.java
- PlayerRecipeData
- PolymorphApi.java
- MixinCraftingMenu.java
- SelectRecipePacket.java
- SyncRecipesPacket
- UtilityCore.java
- AccessorCraftingMenu.java
- MixinDamageContainer.java
- UtilityCoreClient.java
- gradlew
- build.gradle
- settings.gradle
- EnderDragonRespawnHandler.java
- CurseForge — Variables del proyecto
- TombstoneErrorHandler
- MixinTBScreen.java
- Utility Core
- Utility Core 1.0.20
- BiomeDimensionTitleHandler.java
- 1.0.22.md
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
- LegacyBlockMap
- Pre
- RecipeHolder
- RecipeManager
- SubscribeEvent
- EventBusSubscriber
- Pre
- ResourceKey
- SubscribeEvent
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
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
- Inject
- ItemStack
- Mixin
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
- Block
- BlockPos
- BlockState
- Logger
- CallbackInfo
- Inject
- Mixin
- Unique
- Minecraft

## God Nodes (most connected - your core abstractions)
1. `Utility Core — Registro de cambios` - 46 edges
2. `ChunkGenManager` - 32 edges
3. `SpawnSchematicManager` - 24 edges
4. `LegacyBlockMap` - 18 edges
5. `SpongeSchematicReader` - 16 edges
6. `PolymorphClientHandler` - 15 edges
7. `UtilityCore` - 14 edges
8. `CurseForge — Variables del proyecto` - 13 edges
9. `ServerRulesManager` - 12 edges
10. `Utility Core` - 11 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java
- `UtilityCore` --references--> `ServerRulesManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ServerRulesManager.java

## Import Cycles
- None detected.

## Communities (135 total, 82 thin omitted)

### Community 0 - ".onExtractBackground"
Cohesion: 0.04
Nodes (46): 0.0.1-beta.1, 0.0.1-beta.2, 0.0.1-beta.3, 0.0.1-beta.4, 0.0.1-beta.5, 0.0.1-beta.6, 1.0.14, 1.0.15 (+38 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.09
Nodes (7): Closing, CraftingInput, PolymorphClientHandler, AccessorAbstractContainerScreen, MixinCraftingScreen, MixinInventoryScreen, RecipePair

### Community 2 - "PlayerRecipeData"
Cohesion: 0.13
Nodes (10): CompoundTag, SpongeSchematicReader, HolderLookup, BlockPos, Gson, Logger, MinecraftServer, ServerLevel (+2 more)

### Community 3 - "PolymorphApi.java"
Cohesion: 0.18
Nodes (11): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, SpawnPlacementCheck, BlockPos, EventBusSubscriber (+3 more)

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.10
Nodes (8): AbstractContainerMenu, ByteBuf, CustomPacketPayload, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData, RegistryFriendlyByteBuf

### Community 7 - "UtilityCore.java"
Cohesion: 0.11
Nodes (14): CallbackInfoReturnable, Connection, Field, MixinItemInput, Method, RegisterCommandsEvent, ChunkGenManager, DimState (+6 more)

### Community 9 - "MixinDamageContainer.java"
Cohesion: 0.31
Nodes (8): GameRule, GameRules, JsonObject, Gson, Logger, MinecraftServer, ServerRulesManager, SuppressWarnings

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.08
Nodes (21): AddPackFindersEvent, BooleanValue, Builder, ConfigValue, DamageType, EnumValue, OutpostZeroCompat, MixinDamageContainer (+13 more)

### Community 11 - "gradlew"
Cohesion: 0.17
Nodes (11): Automatic Chunk Pregeneration (ChunkGen), Build, Configuration, Credits, Data Pack Folder (opt-in), Features, Known Incompatibilities, Requirements (+3 more)

### Community 12 - "build.gradle"
Cohesion: 0.50
Nodes (3): Fixed, Technical Details, Utility Core 1.0.20

### Community 13 - "settings.gradle"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 14 - "EnderDragonRespawnHandler.java"
Cohesion: 0.12
Nodes (15): AttachmentType, DeferredRegister, ModAttachments, ModNetwork, PlayerLoggedInEvent, PlayerLoggedOutEvent, ServerStartedEvent, ServerStoppingEvent (+7 more)

### Community 16 - "TombstoneErrorHandler"
Cohesion: 0.39
Nodes (5): ErrorAction, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

### Community 19 - "Utility Core"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.1.2), Prioridad de instrucciones, Workflow del mod

### Community 22 - "BiomeDimensionTitleHandler.java"
Cohesion: 0.30
Nodes (10): Biome, Component, LocalPlayer, BiomeDimensionTitleHandler, EventBusSubscriber, Level, Minecraft, Post (+2 more)

### Community 52 - "LegacyBlockMap"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

## Knowledge Gaps
- **84 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.11.0` (+79 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **82 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `PlayerRecipeData` to `PolymorphApi.java`, `EnderDragonRespawnHandler.java`?**
  _High betweenness centrality (0.064) - this node is a cross-community bridge._
- **Why does `ChunkGenManager` connect `UtilityCore.java` to `EnderDragonRespawnHandler.java`?**
  _High betweenness centrality (0.049) - this node is a cross-community bridge._
- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `SyncRecipesPacket`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _84 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.onExtractBackground` be split into smaller, more focused modules?**
  _Cohesion score 0.0425531914893617 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.09247311827956989 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.13333333333333333 - nodes in this community are weakly interconnected._