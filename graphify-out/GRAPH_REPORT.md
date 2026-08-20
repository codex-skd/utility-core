# Graph Report - 26.2  (2026-08-20)

## Corpus Check
- 174 files · ~429,465 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 1027 nodes · 1766 edges · 132 communities (120 shown, 12 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 26 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `02f2162b`
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
- .onExtractBackground
- TombstoneErrorHandler
- Formato de descripciones CurseForge
- MixinItemInput.java
- AccessorCraftingMenu.java
- Utility Core
- UtilityCoreClient.java
- gradlew
- Utility Core — Registro de cambios
- Publicación a GitHub (CI/CD)
- OutpostZeroCompat.java
- settings.gradle
- Level
- Player
- RecipeHolder
- RecipeType
- MixinVehicleAntiCheatWhitelist.java
- MixinTBScreen.java
- Minecraft
- Post
- SubscribeEvent
- SubscribeEvent
- AccessorCraftingMenu.java
- Utility Core Fixes - CurseForge Documentation
- AccessorCraftingMenu.java
- RecipeFinder.java
- Block
- BlockPos
- BlockState
- Accessor
- Mixin
- Accessor
- Mixin
- ResultContainer
- project_description_qol.md
- ReflectSupport
- MixinLevelChunk.java
- MixinLootItemEntityPropertyCondition.java
- Bridge.java
- UtilityCoreQoLBridging.java
- BridgingResult
- PlacementAlignment.java
- PlacementAxisModeOverride
- BridgingConfig
- BridgingKeyMappings
- BridgingAdjacency
- Path.java
- SourcePerspective
- InfoStrings.java
- [2.1.1] - 2026-08-12
- [Admin] 2.2.3
- [Admin] 2.3.0
- [Fixes] 2.4.0
- [QoL] 2.3.0
- [Fixes] 2.3.5
- [QoL] 2.2.5

## God Nodes (most connected - your core abstractions)
1. `Utility Core — Registro de cambios` - 47 edges
2. `ChunkGenManager` - 31 edges
3. `SpawnSchematicManager` - 24 edges
4. `SpongeSchematicReader` - 21 edges
5. `Perspective` - 21 edges
6. `LegacyBlockMap` - 18 edges
7. `PolymorphClientHandler` - 18 edges
8. `PlayerRecipeData` - 17 edges
9. `PlayerRecipeData` - 16 edges
10. `BridgingPreContext` - 15 edges

## Surprising Connections (you probably didn't know these)
- `getPlacementAxisMode()` --references--> `PlacementAxisMode`  [EXTRACTED]
  qol/src/main/java/com/skd/utilitycore/qol/bridging/PlacementAxisModeOverride.java → qol/src/main/java/com/skd/utilitycore/qol/bridging/PlacementAxisMode.java
- `UtilityCoreAdmin` --references--> `ChunkGenManager`  [EXTRACTED]
  admin/src/main/java/com/skd/utilitycore/admin/UtilityCoreAdmin.java → admin/src/main/java/com/skd/utilitycore/admin/chunkgen/ChunkGenManager.java
- `UtilityCoreAdmin` --references--> `SpawnSchematicManager`  [EXTRACTED]
  admin/src/main/java/com/skd/utilitycore/admin/UtilityCoreAdmin.java → admin/src/main/java/com/skd/utilitycore/admin/schematic/SpawnSchematicManager.java
- `BridgingConfig` --references--> `BridgingAdjacency`  [EXTRACTED]
  qol/src/main/java/com/skd/utilitycore/qol/bridging/BridgingConfig.java → qol/src/main/java/com/skd/utilitycore/qol/bridging/BridgingAdjacency.java
- `BridgingConfig` --references--> `PlacementAxisMode`  [EXTRACTED]
  qol/src/main/java/com/skd/utilitycore/qol/bridging/BridgingConfig.java → qol/src/main/java/com/skd/utilitycore/qol/bridging/PlacementAxisMode.java

## Import Cycles
- None detected.

## Communities (132 total, 12 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.12
Nodes (15): Admin, Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Fixes, Flujo de trabajo — Utility Core (NeoForge) (+7 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.07
Nodes (31): Closing, CraftingInput, ModifyArg, CraftingContainer, CraftingRecipe, EventBusSubscriber, GuiGraphicsExtractor, ItemStack (+23 more)

### Community 2 - "ChunkGenManager"
Cohesion: 0.10
Nodes (16): ChunkGenManager, DimState, Gson, Level, Logger, MinecraftServer, ResourceKey, RootState (+8 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.06
Nodes (44): AbstractContainerMenu, Biome, ByteBuf, CustomPacketPayload, BiomeDimensionTitleHandler, Component, EventBusSubscriber, Level (+36 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.18
Nodes (14): ClientLevel, HitResult, MultiPlayerGameMode, NotNull, BlockHitResult, BlockPos, CallbackInfo, Direction (+6 more)

### Community 5 - "Config.java"
Cohesion: 0.10
Nodes (20): DamageType, EventBusSubscriber, Pre, ResourceKey, SubscribeEvent, Unique, OutpostZeroCompat, FixesConfig (+12 more)

### Community 6 - "PolymorphApi.java"
Cohesion: 0.10
Nodes (14): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, AttachmentType, DeferredRegister (+6 more)

### Community 7 - "MixinCraftingMenu.java"
Cohesion: 0.10
Nodes (14): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, AttachmentType, DeferredRegister (+6 more)

### Community 8 - "CurseForge — Variables del proyecto"
Cohesion: 0.07
Nodes (29): AddPackFindersEvent, DataPackFolderLoader, Logger, AdminConfig, BooleanValue, Builder, ConfigValue, EnumValue (+21 more)

### Community 10 - ".onExtractBackground"
Cohesion: 0.27
Nodes (9): DebugScreenOverlay, DeltaTracker, BridgingCrosshairTweaks, CrosshairRenderingMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Minecraft (+1 more)

### Community 11 - "TombstoneErrorHandler"
Cohesion: 0.07
Nodes (37): Camera, FunctionalInterface, LevelRenderState, BlockPos, PoseStack, SubmitNodeCollector, SpecialBridgingEnvironmentHandler, CallbackInfo (+29 more)

### Community 13 - "Formato de descripciones CurseForge"
Cohesion: 0.17
Nodes (11): Automatic Chunk Pregeneration (ChunkGen), Build, Configuration, Credits, Data Pack Folder (opt-in), Features, Known Incompatibilities, Requirements (+3 more)

### Community 14 - "MixinItemInput.java"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.2), Prioridad de instrucciones, Workflow del mod

### Community 15 - "AccessorCraftingMenu.java"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 16 - "Utility Core"
Cohesion: 0.16
Nodes (8): AttachmentType, DeferredRegister, ModAttachments, ItemStack, PlayerRecipeData, ItemStack, RecipeHolder, RecipePair

### Community 17 - "UtilityCoreClient.java"
Cohesion: 0.18
Nodes (12): BlockPos, EventBusSubscriber, Player, SubscribeEvent, SpawnProtectionHandler, BlockEvent, BreakBlockEvent, Detonate (+4 more)

### Community 19 - "gradlew"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

### Community 20 - "Utility Core — Registro de cambios"
Cohesion: 0.05
Nodes (40): 1.0.0, 1.10.0, 1.11.0, 1.11.1, 1.11.10, 1.11.11, 1.11.12, 1.11.13 (+32 more)

### Community 26 - "Publicación a GitHub (CI/CD)"
Cohesion: 0.12
Nodes (15): BlockPos, Gson, Identifier, Logger, MinecraftServer, ServerLevel, MarkerData, SpawnSchematicManager (+7 more)

### Community 27 - "OutpostZeroCompat.java"
Cohesion: 0.19
Nodes (5): ItemStack, PlayerRecipeData, ItemStack, RecipeHolder, RecipePair

### Community 37 - "settings.gradle"
Cohesion: 0.12
Nodes (15): Build & release, Config migration: YACL3 → native `ModConfigSpec`, Definition of done — Task A, Definition of done — Task B, Global constraints (both tasks), Mixin merge, Package layout, Plan: Fix admin/fixes config registration + port BridgingMod into qol (+7 more)

### Community 38 - "Level"
Cohesion: 0.10
Nodes (19): 3 Proyectos CurseForge Separados, Build, Configuración por Mod, CurseForge — Variables del proyecto (v2.0.0+ Multi-Mod), Estructura Changelog (HTML), Estructura de Archivos por Mod, Flujo de Upload (Por Mod), Legacy unsuffixed project_id kept for back-compat with curseforge-upload.ps1 (points at Fixes). (+11 more)

### Community 39 - "Player"
Cohesion: 0.29
Nodes (7): isDirectionEnabled(), Direction, PlacementAxisMode, BOTH, NONE, XZ, Y

### Community 40 - "RecipeHolder"
Cohesion: 0.33
Nodes (7): ErrorAction, Logger, Override, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

### Community 42 - "RecipeType"
Cohesion: 0.15
Nodes (12): 1. Utility Core Fixes, 2. Utility Core Admin, 3. Utility Core QoL, Common Settings, CurseForge Setup (Per Project), Dependencies, JAR Locations (After Build), Overview (+4 more)

### Community 43 - "MixinVehicleAntiCheatWhitelist.java"
Cohesion: 0.29
Nodes (10): EntityType, Entity, Logger, MinecraftServer, Mixin, Redirect, ResourceKey, Unique (+2 more)

### Community 53 - "MixinTBScreen.java"
Cohesion: 0.60
Nodes (3): InvokerServerCommonPacketListenerImpl, Mixin, Invoker

### Community 55 - "Minecraft"
Cohesion: 0.25
Nodes (9): Item, GroupSelector, ItemStack, Block, Identifier, ItemStack, SpecialGroupHandlerEntry, SpecialHandlers (+1 more)

### Community 56 - "Post"
Cohesion: 0.38
Nodes (6): CallbackInfo, Inject, Mixin, Unique, MixinTBScreen, Screen

### Community 58 - "SubscribeEvent"
Cohesion: 0.35
Nodes (7): InteractionResult, BlockHitResult, BlockPos, Direction, ItemStack, Level, Player

### Community 67 - "SubscribeEvent"
Cohesion: 0.36
Nodes (6): Vec3, Vector3fc, VectorSupport, Vector3d, Vector3dc, Vector3f

### Community 69 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 70 - "Utility Core Fixes - CurseForge Documentation"
Cohesion: 0.29
Nodes (3): GameSupport, ItemStack, Player

### Community 73 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 74 - "RecipeFinder.java"
Cohesion: 0.48
Nodes (5): Level, RecipeHolder, RecipeManager, RecipeType, RecipeFinder

### Community 76 - "BlockPos"
Cohesion: 0.48
Nodes (5): Level, RecipeHolder, RecipeManager, RecipeType, RecipeFinder

### Community 77 - "BlockState"
Cohesion: 0.53
Nodes (3): AccessorAbstractContainerScreen, Accessor, Mixin

### Community 79 - "Accessor"
Cohesion: 0.53
Nodes (3): AccessorAbstractContainerScreen, Accessor, Mixin

### Community 80 - "Mixin"
Cohesion: 0.60
Nodes (3): IEventBus, PayloadRegistrar, ModNetwork

### Community 81 - "Accessor"
Cohesion: 0.40
Nodes (4): Utility Core Admin, Utility Core Fixes, Utility Core - Icon Generation Prompts, Utility Core QoL

### Community 83 - "Mixin"
Cohesion: 0.60
Nodes (3): IEventBus, PayloadRegistrar, ModNetwork

### Community 104 - "MixinLevelChunk.java"
Cohesion: 0.24
Nodes (11): BlockEntity, EntityBlock, BlockPos, BlockState, Level, Logger, Mixin, Redirect (+3 more)

### Community 106 - "MixinLootItemEntityPropertyCondition.java"
Cohesion: 0.29
Nodes (9): EntityPredicate, Entity, Logger, Mixin, Redirect, ServerLevel, Unique, Vec3 (+1 more)

### Community 107 - "Bridge.java"
Cohesion: 0.51
Nodes (6): Bridge, BlockHitResult, BlockPos, Direction, ItemStack, Level

### Community 108 - "UtilityCoreQoLBridging.java"
Cohesion: 0.24
Nodes (4): ModIds, Identifier, Logger, UtilityCoreQoLBridging

### Community 109 - "BridgingResult"
Cohesion: 0.31
Nodes (6): BridgingResult, BlockPos, Direction, BridgingStateTracker, LocalPlayer, Player

### Community 110 - "PlacementAlignment.java"
Cohesion: 0.31
Nodes (8): from(), getTexturePath(), Direction, Identifier, PlacementAlignment, DOWN, HORIZONTAL, UP

### Community 111 - "PlacementAxisModeOverride"
Cohesion: 0.25
Nodes (7): getPlacementAxisMode(), PlacementAxisModeOverride, BOTH, FALLBACK, NONE, XZ, Y

### Community 112 - "BridgingConfig"
Cohesion: 0.29
Nodes (7): DoubleValue, BridgingConfig, BooleanValue, Builder, EnumValue, IntValue, ModConfigSpec

### Community 113 - "BridgingKeyMappings"
Cohesion: 0.53
Nodes (3): Category, KeyMapping, BridgingKeyMappings

### Community 114 - "BridgingAdjacency"
Cohesion: 0.33
Nodes (5): BridgingAdjacency, CORNERS, EDGES, FULL, NONE

### Community 115 - "Path.java"
Cohesion: 0.73
Nodes (3): BlockPos, Vec3, Path

### Community 116 - "SourcePerspective"
Cohesion: 0.40
Nodes (4): SourcePerspective, ALWAYS_EYELINE, COPY_TOGGLE_PERSPECTIVE, LET_BRIDGING_MOD_DECIDE

## Knowledge Gaps
- **139 isolated node(s):** `SURFACE`, `FIXED`, `NONE`, `CORNERS`, `EDGES` (+134 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **12 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `RecipePair` connect `PolymorphApi.java` to `PolymorphClientHandler.java`?**
  _High betweenness centrality (0.042) - this node is a cross-community bridge._
- **Why does `ChunkGenManager` connect `ChunkGenManager` to `CurseForge — Variables del proyecto`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `NONE` to the rest of the system?**
  _139 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Flujo de trabajo — Utility Core (NeoForge)` be split into smaller, more focused modules?**
  _Cohesion score 0.125 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.07013574660633484 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.10121951219512196 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.0601404741000878 - nodes in this community are weakly interconnected._