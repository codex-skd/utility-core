# Graph Report - 26.1.2  (2026-08-02)

## Corpus Check
- 66 files · ~119,594 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 438 nodes · 489 edges · 146 communities (46 shown, 100 thin omitted)
- Extraction: 92% EXTRACTED · 8% INFERRED · 0% AMBIGUOUS · INFERRED: 37 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `19a981a1`
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
- MixinItemInput.java
- Utility Core
- Utility Core 1.0.20
- Utility Core 1.0.21
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
- Gson
- Level
- Logger
- MinecraftServer
- ResourceKey
- Mod
- ModContainer
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
- CallbackInfo
- Inject
- Mixin
- Unique
- IEventBus
- Logger
- Mod
- ModContainer
- SubscribeEvent

## God Nodes (most connected - your core abstractions)
1. `Utility Core — Registro de cambios` - 40 edges
2. `ChunkGenManager` - 26 edges
3. `LegacyBlockMap` - 18 edges
4. `SpawnSchematicManager` - 16 edges
5. `SpongeSchematicReader` - 16 edges
6. `PolymorphClientHandler` - 15 edges
7. `CurseForge — Variables del proyecto` - 13 edges
8. `UtilityCore` - 11 edges
9. `Flujo de trabajo — Utility Core (NeoForge)` - 11 edges
10. `PlayerRecipeData` - 10 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java

## Import Cycles
- None detected.

## Communities (146 total, 100 thin omitted)

### Community 0 - ".onExtractBackground"
Cohesion: 0.05
Nodes (40): 0.0.1-beta.1, 0.0.1-beta.2, 0.0.1-beta.3, 0.0.1-beta.4, 0.0.1-beta.5, 0.0.1-beta.6, 1.0.14, 1.0.15 (+32 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.12
Nodes (7): Closing, CraftingInput, PolymorphClientHandler, AccessorAbstractContainerScreen, MixinCraftingScreen, MixinInventoryScreen, Minecraft

### Community 2 - "PlayerRecipeData"
Cohesion: 0.16
Nodes (5): CompoundTag, MarkerData, SpawnSchematicManager, SpongeSchematicReader, HolderLookup

### Community 3 - "PolymorphApi.java"
Cohesion: 0.24
Nodes (6): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, SpawnProtectionHandler

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.09
Nodes (9): AbstractContainerMenu, ByteBuf, CustomPacketPayload, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData, RecipePair (+1 more)

### Community 7 - "UtilityCore.java"
Cohesion: 0.09
Nodes (12): Field, ChunkGenManager, DimState, RootState, UtilityCore, ModNetwork, PlayerLoggedInEvent, PlayerLoggedOutEvent (+4 more)

### Community 9 - "MixinDamageContainer.java"
Cohesion: 0.29
Nodes (6): Build, Features, Known Incompatibilities, Requirements, Spawn Schematic (opt-in), Utility Core

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.60
Nodes (3): CallbackInfoReturnable, MixinItemInput, Method

### Community 11 - "gradlew"
Cohesion: 0.24
Nodes (9): BooleanValue, Builder, EnumValue, Config, SpawnHeightMode, FIXED, SURFACE, IntValue (+1 more)

### Community 12 - "build.gradle"
Cohesion: 0.50
Nodes (3): Fixed, Technical Details, Utility Core 1.0.20

### Community 13 - "settings.gradle"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 14 - "EnderDragonRespawnHandler.java"
Cohesion: 0.83
Nodes (3): AttachmentType, DeferredRegister, ModAttachments

### Community 16 - "TombstoneErrorHandler"
Cohesion: 0.39
Nodes (5): ErrorAction, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

### Community 19 - "Utility Core"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.1.2), Prioridad de instrucciones, Workflow del mod

### Community 52 - "LegacyBlockMap"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

## Knowledge Gaps
- **73 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.6.0` (+68 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **100 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `PlayerRecipeData` to `PolymorphApi.java`, `UtilityCore.java`?**
  _High betweenness centrality (0.034) - this node is a cross-community bridge._
- **Why does `UtilityCore` connect `UtilityCore.java` to `PlayerRecipeData`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _73 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.onExtractBackground` be split into smaller, more focused modules?**
  _Cohesion score 0.04878048780487805 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.1164021164021164 - nodes in this community are weakly interconnected._
- **Should `SelectRecipePacket.java` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._
- **Should `SyncRecipesPacket` be split into smaller, more focused modules?**
  _Cohesion score 0.08912655971479501 - nodes in this community are weakly interconnected._