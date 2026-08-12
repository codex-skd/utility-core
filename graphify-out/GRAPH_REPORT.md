# Graph Report - 26.2  (2026-08-12)

## Corpus Check
- 145 files · ~425,638 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 943 nodes · 1658 edges · 97 communities (90 shown, 7 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 26 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d1cf53dc`
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
- project_description.md
- Publicación a GitHub (CI/CD)
- OutpostZeroCompat.java
- settings.gradle
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
- UtilityCore.java
- EventBusSubscriber
- Pre
- ResourceKey
- SubscribeEvent
- AccessorCraftingMenu.java
- Utility Core Fixes - CurseForge Documentation
- PlacementAxisMode
- PlacementAxisModeOverride
- AccessorCraftingMenu.java
- RecipeFinder.java
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
- project_description_qol.md
- InfoStrings.java
- ReflectSupport
- project_description_fixes.md

## God Nodes (most connected - your core abstractions)
1. `ChunkGenManager` - 31 edges
2. `Utility Core — Registro de cambios` - 28 edges
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

## Communities (97 total, 7 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.06
Nodes (33): Closing, CraftingInput, ModifyArg, CraftingContainer, CraftingRecipe, EventBusSubscriber, GuiGraphicsExtractor, ItemStack (+25 more)

### Community 2 - "ChunkGenManager"
Cohesion: 0.10
Nodes (16): ChunkGenManager, DimState, Gson, Level, Logger, MinecraftServer, ResourceKey, RootState (+8 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.09
Nodes (29): Biome, ByteBuf, CustomPacketPayload, BiomeDimensionTitleHandler, Component, EventBusSubscriber, Level, LocalPlayer (+21 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.11
Nodes (18): InteractionResult, Item, GroupSelector, ItemStack, Block, Identifier, ItemStack, SpecialGroupHandlerEntry (+10 more)

### Community 5 - "Config.java"
Cohesion: 0.12
Nodes (18): FixesConfig, BooleanValue, Builder, ModConfigSpec, Mixin, Unique, MixinDamageContainer, CallbackInfo (+10 more)

### Community 6 - "PolymorphApi.java"
Cohesion: 0.10
Nodes (14): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, AttachmentType, DeferredRegister (+6 more)

### Community 7 - "MixinCraftingMenu.java"
Cohesion: 0.10
Nodes (14): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, AttachmentType, DeferredRegister (+6 more)

### Community 8 - "CurseForge — Variables del proyecto"
Cohesion: 0.07
Nodes (30): AddPackFindersEvent, DataPackFolderLoader, Logger, AdminConfig, BooleanValue, Builder, EnumValue, IntValue (+22 more)

### Community 9 - "SyncRecipesPacket"
Cohesion: 0.25
Nodes (13): AbstractContainerMenu, CallbackInfo, CraftingContainer, CraftingRecipe, Inject, ItemStack, Mixin, Player (+5 more)

### Community 10 - ".onExtractBackground"
Cohesion: 0.14
Nodes (17): DebugScreenOverlay, DeltaTracker, BridgingCrosshairTweaks, CrosshairRenderingMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Minecraft (+9 more)

### Community 11 - "TombstoneErrorHandler"
Cohesion: 0.33
Nodes (7): ErrorAction, Logger, Override, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

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

### Community 19 - "gradlew"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

### Community 20 - "Utility Core — Registro de cambios"
Cohesion: 0.07
Nodes (28): 1.0.0, 1.10.0, 1.11.0, 1.11.1, 1.11.10, 1.11.11, 1.11.12, 1.11.13 (+20 more)

### Community 22 - "project_description.md"
Cohesion: 0.20
Nodes (14): ClientLevel, HitResult, MultiPlayerGameMode, NotNull, BlockHitResult, BlockPos, CallbackInfo, Direction (+6 more)

### Community 26 - "Publicación a GitHub (CI/CD)"
Cohesion: 0.08
Nodes (26): BlockPos, EventBusSubscriber, Player, SubscribeEvent, SpawnProtectionHandler, BlockPos, Gson, Identifier (+18 more)

### Community 27 - "OutpostZeroCompat.java"
Cohesion: 0.19
Nodes (5): ItemStack, PlayerRecipeData, ItemStack, RecipeHolder, RecipePair

### Community 37 - "settings.gradle"
Cohesion: 0.12
Nodes (15): Build & release, Config migration: YACL3 → native `ModConfigSpec`, Definition of done — Task A, Definition of done — Task B, Global constraints (both tasks), Mixin merge, Package layout, Plan: Fix admin/fixes config registration + port BridgingMod into qol (+7 more)

### Community 38 - "Level"
Cohesion: 0.12
Nodes (15): 3 Proyectos CurseForge Separados, Build, Configuración por Mod, CurseForge — Variables del proyecto (v2.0.0+ Multi-Mod), Estructura Changelog (HTML), Estructura de Archivos por Mod, Flujo de Upload (Por Mod), Nomenclatura JAR (+7 more)

### Community 39 - "Player"
Cohesion: 0.16
Nodes (11): DoubleValue, BridgingConfig, BooleanValue, Builder, EnumValue, IntValue, ModConfigSpec, SourcePerspective (+3 more)

### Community 40 - "RecipeHolder"
Cohesion: 0.31
Nodes (7): BlockPos, Direction, Level, Player, Vector3fc, PathTraversalHandler, Vector3f

### Community 41 - "RecipeManager"
Cohesion: 0.32
Nodes (6): Camera, Entity, Player, Vec3, Vector3fc, Perspective

### Community 42 - "RecipeType"
Cohesion: 0.15
Nodes (12): 1. Utility Core Fixes, 2. Utility Core Admin, 3. Utility Core QoL, Common Settings, CurseForge Setup (Per Project), Dependencies, JAR Locations (After Build), Overview (+4 more)

### Community 43 - "CraftingContainer"
Cohesion: 0.27
Nodes (8): FunctionalInterface, BlockPos, PoseStack, SubmitNodeCollector, CubeRenderTask, BlockPos, PoseStack, SubmitNodeCollector

### Community 53 - "EventBusSubscriber"
Cohesion: 0.27
Nodes (8): BridgingAdjacency, CORNERS, EDGES, FULL, NONE, BlockPos, Vec3, Path

### Community 54 - "Level"
Cohesion: 0.53
Nodes (4): BlockPos, PoseStack, SubmitNodeCollector, Render

### Community 55 - "Minecraft"
Cohesion: 0.33
Nodes (7): DamageType, EventBusSubscriber, Pre, ResourceKey, SubscribeEvent, Unique, OutpostZeroCompat

### Community 56 - "Post"
Cohesion: 0.18
Nodes (10): API (for other mods), Changelog (2.0.0-beta.1), Configuration, Credits, Description (Long), Features, How Recipe Selector Works, Project Info (+2 more)

### Community 57 - "ResourceKey"
Cohesion: 0.33
Nodes (8): LevelRenderState, CallbackInfo, Inject, Mixin, PoseStack, SubmitNodeCollector, OutlineRendererMixin, Shadow

### Community 58 - "SubscribeEvent"
Cohesion: 0.20
Nodes (9): Changelog (2.0.0-beta.1), Commands, Configuration, Description (Long), Features, Project Info, Requirements, Spawn Schematic File (+1 more)

### Community 63 - "UtilityCore.java"
Cohesion: 0.51
Nodes (6): Bridge, BlockHitResult, BlockPos, Direction, ItemStack, Level

### Community 64 - "EventBusSubscriber"
Cohesion: 0.24
Nodes (4): ModIds, Identifier, Logger, UtilityCoreQoLBridging

### Community 65 - "Pre"
Cohesion: 0.31
Nodes (4): BridgingPreContext, Level, Player, Flags

### Community 66 - "ResourceKey"
Cohesion: 0.31
Nodes (6): BridgingResult, BlockPos, Direction, BridgingStateTracker, LocalPlayer, Player

### Community 67 - "SubscribeEvent"
Cohesion: 0.39
Nodes (5): Vec3, Vector3fc, VectorSupport, Vector3d, Vector3dc

### Community 69 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 70 - "Utility Core Fixes - CurseForge Documentation"
Cohesion: 0.25
Nodes (7): Changelog (2.0.0-beta.1), Compatibility, Configuration, Description (Long), Features, Project Info, Utility Core Fixes - CurseForge Documentation

### Community 71 - "PlacementAxisMode"
Cohesion: 0.29
Nodes (7): isDirectionEnabled(), Direction, PlacementAxisMode, BOTH, NONE, XZ, Y

### Community 72 - "PlacementAxisModeOverride"
Cohesion: 0.25
Nodes (7): getPlacementAxisMode(), PlacementAxisModeOverride, BOTH, FALLBACK, NONE, XZ, Y

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

### Community 78 - "Logger"
Cohesion: 0.53
Nodes (3): Category, KeyMapping, BridgingKeyMappings

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

## Knowledge Gaps
- **134 isolated node(s):** `SURFACE`, `FIXED`, `NONE`, `CORNERS`, `EDGES` (+129 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `RecipePair` connect `PolymorphApi.java` to `PolymorphClientHandler.java`?**
  _High betweenness centrality (0.044) - this node is a cross-community bridge._
- **Why does `ChunkGenManager` connect `ChunkGenManager` to `CurseForge — Variables del proyecto`?**
  _High betweenness centrality (0.037) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `NONE` to the rest of the system?**
  _134 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.06428571428571428 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.10121951219512196 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.0851063829787234 - nodes in this community are weakly interconnected._
- **Should `UtilityCore.java` be split into smaller, more focused modules?**
  _Cohesion score 0.1126984126984127 - nodes in this community are weakly interconnected._