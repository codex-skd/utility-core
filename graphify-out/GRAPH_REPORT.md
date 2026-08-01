# Graph Report - 26.1.2  (2026-08-02)

## Corpus Check
- 66 files · ~119,558 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 438 nodes · 584 edges · 117 communities (45 shown, 72 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 37 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a80b6380`
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
- UtilityCoreClient.java
- gradlew
- build.gradle
- settings.gradle
- EnderDragonRespawnHandler.java
- CurseForge — Variables del proyecto
- TombstoneErrorHandler
- MixinTBScreen.java
- Utility Core 1.0.20
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

## God Nodes (most connected - your core abstractions)
1. `Utility Core — Registro de cambios` - 40 edges
2. `ChunkGenManager` - 30 edges
3. `SpawnSchematicManager` - 19 edges
4. `SpongeSchematicReader` - 19 edges
5. `LegacyBlockMap` - 18 edges
6. `PolymorphClientHandler` - 15 edges
7. `UtilityCore` - 13 edges
8. `CurseForge — Variables del proyecto` - 13 edges
9. `Flujo de trabajo — Utility Core (NeoForge)` - 11 edges
10. `PlayerRecipeData` - 10 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java

## Import Cycles
- None detected.

## Communities (117 total, 72 thin omitted)

### Community 0 - ".onExtractBackground"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.10
Nodes (7): Closing, PolymorphClientHandler, AccessorAbstractContainerScreen, MixinCraftingScreen, MixinInventoryScreen, RecipePair, Minecraft

### Community 2 - "PlayerRecipeData"
Cohesion: 0.13
Nodes (14): CompoundTag, HolderLookup, BlockPos, Gson, Logger, MinecraftServer, ServerLevel, MarkerData (+6 more)

### Community 3 - "PolymorphApi.java"
Cohesion: 0.26
Nodes (8): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, EventBusSubscriber, SubscribeEvent, SpawnProtectionHandler

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.05
Nodes (40): 0.0.1-beta.1, 0.0.1-beta.2, 0.0.1-beta.3, 0.0.1-beta.4, 0.0.1-beta.5, 0.0.1-beta.6, 1.0.14, 1.0.15 (+32 more)

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.11
Nodes (8): AbstractContainerMenu, ByteBuf, CustomPacketPayload, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData, RegistryFriendlyByteBuf

### Community 7 - "UtilityCore.java"
Cohesion: 0.07
Nodes (25): AttachmentType, DeferredRegister, Field, ModAttachments, ModNetwork, PlayerLoggedInEvent, PlayerLoggedOutEvent, Post (+17 more)

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.29
Nodes (6): Build, Features, Known Incompatibilities, Requirements, Spawn Schematic (opt-in), Utility Core

### Community 11 - "gradlew"
Cohesion: 0.10
Nodes (17): BooleanValue, Builder, CallbackInfoReturnable, CraftingInput, DamageType, EnumValue, OutpostZeroCompat, MixinDamageContainer (+9 more)

### Community 12 - "build.gradle"
Cohesion: 0.50
Nodes (3): Fixed, Technical Details, Utility Core 1.0.20

### Community 13 - "settings.gradle"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 14 - "EnderDragonRespawnHandler.java"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.1.2), Prioridad de instrucciones, Workflow del mod

### Community 16 - "TombstoneErrorHandler"
Cohesion: 0.39
Nodes (5): ErrorAction, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

### Community 17 - "MixinTBScreen.java"
Cohesion: 0.40
Nodes (6): Screen, CallbackInfo, Inject, Mixin, Unique, MixinTBScreen

### Community 52 - "LegacyBlockMap"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

## Knowledge Gaps
- **73 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.6.0` (+68 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **72 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `PlayerRecipeData` to `PolymorphApi.java`, `UtilityCore.java`?**
  _High betweenness centrality (0.067) - this node is a cross-community bridge._
- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `gradlew`, `SyncRecipesPacket`?**
  _High betweenness centrality (0.037) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _73 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.onExtractBackground` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.10344827586206896 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.13360323886639677 - nodes in this community are weakly interconnected._
- **Should `SelectRecipePacket.java` be split into smaller, more focused modules?**
  _Cohesion score 0.04878048780487805 - nodes in this community are weakly interconnected._