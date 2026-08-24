# Graph Report - 26.2  (2026-08-24)

## Corpus Check
- 218 files · ~439,395 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 1197 nodes · 2063 edges · 151 communities (133 shown, 18 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 32 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `bc10fd0a`
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
- [Fixes] 2.5.1
- OrphanBlockCommand.java
- Plan: `/utilitycore cleanorphans` — purge orphaned block-registry entries left by removed mods
- [HUD] 0.0.0-beta.1
- BridgingAdjacency
- Path.java
- [HUD] 0.0.0-beta.1

## God Nodes (most connected - your core abstractions)
1. `Utility Core — Registro de cambios` - 54 edges
2. `ChunkGenManager` - 31 edges
3. `SpawnSchematicManager` - 24 edges
4. `SpongeSchematicReader` - 21 edges
5. `Perspective` - 21 edges
6. `LegacyBlockMap` - 18 edges
7. `PolymorphClientHandler` - 18 edges
8. `HudPositionEditorScreen` - 17 edges
9. `PlayerRecipeData` - 17 edges
10. `PlayerRecipeData` - 16 edges

## Surprising Connections (you probably didn't know these)
- `getPlacementAxisMode()` --references--> `PlacementAxisMode`  [EXTRACTED]
  qol/src/main/java/com/skd/utilitycore/qol/bridging/PlacementAxisModeOverride.java → qol/src/main/java/com/skd/utilitycore/qol/bridging/PlacementAxisMode.java
- `UtilityCoreAdmin` --references--> `ChunkGenManager`  [EXTRACTED]
  admin/src/main/java/com/skd/utilitycore/admin/UtilityCoreAdmin.java → admin/src/main/java/com/skd/utilitycore/admin/chunkgen/ChunkGenManager.java
- `UtilityCoreAdmin` --references--> `SpawnSchematicManager`  [EXTRACTED]
  admin/src/main/java/com/skd/utilitycore/admin/UtilityCoreAdmin.java → admin/src/main/java/com/skd/utilitycore/admin/schematic/SpawnSchematicManager.java
- `ModAttachments` --references--> `ReturnPortalData`  [EXTRACTED]
  fixes/src/main/java/com/skd/utilitycore/fixes/common/attachment/ModAttachments.java → fixes/src/main/java/com/skd/utilitycore/fixes/common/attachment/ReturnPortalData.java
- `BridgingConfig` --references--> `BridgingAdjacency`  [EXTRACTED]
  qol/src/main/java/com/skd/utilitycore/qol/bridging/BridgingConfig.java → qol/src/main/java/com/skd/utilitycore/qol/bridging/BridgingAdjacency.java

## Import Cycles
- None detected.

## Communities (151 total, 18 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.12
Nodes (15): Admin, Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Fixes, Flujo de trabajo — Utility Core (NeoForge) (+7 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.06
Nodes (33): Closing, CraftingInput, ModifyArg, CraftingContainer, CraftingRecipe, EventBusSubscriber, GuiGraphicsExtractor, ItemStack (+25 more)

### Community 2 - "ChunkGenManager"
Cohesion: 0.10
Nodes (16): ChunkGenManager, DimState, Gson, Level, Logger, MinecraftServer, ResourceKey, RootState (+8 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.06
Nodes (42): AbstractContainerMenu, Biome, ByteBuf, CustomPacketPayload, BiomeDimensionTitleHandler, Component, EventBusSubscriber, Level (+34 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.21
Nodes (12): ClientLevel, HitResult, MultiPlayerGameMode, NotNull, BlockHitResult, BlockPos, Direction, ItemStack (+4 more)

### Community 5 - "Config.java"
Cohesion: 0.08
Nodes (27): DamageType, ErrorAction, EventBusSubscriber, Pre, ResourceKey, SubscribeEvent, Unique, OutpostZeroCompat (+19 more)

### Community 6 - "PolymorphApi.java"
Cohesion: 0.10
Nodes (14): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, AttachmentType, DeferredRegister (+6 more)

### Community 7 - "MixinCraftingMenu.java"
Cohesion: 0.10
Nodes (14): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, AttachmentType, DeferredRegister (+6 more)

### Community 8 - "CurseForge — Variables del proyecto"
Cohesion: 0.07
Nodes (30): AddPackFindersEvent, DataPackFolderLoader, Logger, AdminConfig, BooleanValue, Builder, ConfigValue, EnumValue (+22 more)

### Community 10 - ".onExtractBackground"
Cohesion: 0.27
Nodes (9): DebugScreenOverlay, DeltaTracker, BridgingCrosshairTweaks, CrosshairRenderingMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Minecraft (+1 more)

### Community 11 - "TombstoneErrorHandler"
Cohesion: 0.05
Nodes (47): Camera, FunctionalInterface, LevelRenderState, BlockPos, PoseStack, SubmitNodeCollector, CallbackInfo, Inject (+39 more)

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
Cohesion: 0.08
Nodes (23): Button, FMLClientSetupEvent, HudConfig, HudConfigData, Gson, Orientation, HORIZONTAL, VERTICAL (+15 more)

### Community 19 - "gradlew"
Cohesion: 0.29
Nodes (3): Block, BlockState, LegacyBlockMap

### Community 20 - "Utility Core — Registro de cambios"
Cohesion: 0.05
Nodes (40): 1.0.0, 1.10.0, 1.11.0, 1.11.1, 1.11.10, 1.11.11, 1.11.12, 1.11.13 (+32 more)

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
Cohesion: 0.10
Nodes (20): 4 Proyectos CurseForge Separados, Build, Configuración por Mod, CurseForge — Variables del proyecto (v2.0.0+ Multi-Mod), Estructura Changelog (HTML), Estructura de Archivos por Mod, Flujo de Upload (Por Mod), Legacy unsuffixed project_id kept for back-compat with curseforge-upload.ps1 (points at Fixes). (+12 more)

### Community 39 - "Player"
Cohesion: 0.29
Nodes (7): isDirectionEnabled(), Direction, PlacementAxisMode, BOTH, NONE, XZ, Y

### Community 40 - "RecipeHolder"
Cohesion: 0.29
Nodes (9): EntityPredicate, Entity, Logger, Mixin, Redirect, ServerLevel, Unique, Vec3 (+1 more)

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
Cohesion: 0.21
Nodes (10): Item, GroupSelector, ItemStack, Block, Identifier, ItemStack, SpecialGroupHandlerEntry, SpecialHandlers (+2 more)

### Community 56 - "Post"
Cohesion: 0.38
Nodes (6): CallbackInfo, Inject, Mixin, Screen, Unique, MixinTBScreen

### Community 58 - "SubscribeEvent"
Cohesion: 0.16
Nodes (18): BlockPos, Level, Override, ResourceKey, PortalKey, ReturnPortalData, BlockPos, Entity (+10 more)

### Community 67 - "SubscribeEvent"
Cohesion: 0.31
Nodes (8): from(), getTexturePath(), Direction, Identifier, PlacementAlignment, DOWN, HORIZONTAL, UP

### Community 69 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 70 - "Utility Core Fixes - CurseForge Documentation"
Cohesion: 0.15
Nodes (8): CallbackInfo, Inject, BridgingStateTracker, LocalPlayer, Player, GameSupport, ItemStack, Player

### Community 73 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 74 - "RecipeFinder.java"
Cohesion: 0.48
Nodes (5): Level, RecipeHolder, RecipeManager, RecipeType, RecipeFinder

### Community 75 - "Block"
Cohesion: 0.22
Nodes (8): Cableado pendiente (3 puntos concretos, hoy son `// TODO` vacíos), Editor de posición interactivo — la mayor parte no está implementada, Idea original, Lo que falta, Lo que hay hecho, No empezado, Nota sobre el intento de delegación en OpenCode, Roadmap — Utility Core HUD

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
Cohesion: 0.14
Nodes (16): CleanupReport, BlockState, CompoundTag, Level, Logger, MinecraftServer, Override, ResourceKey (+8 more)

### Community 107 - "Bridge.java"
Cohesion: 0.51
Nodes (6): Bridge, BlockHitResult, BlockPos, Direction, ItemStack, Level

### Community 109 - "BridgingResult"
Cohesion: 0.25
Nodes (7): getPlacementAxisMode(), PlacementAxisModeOverride, BOTH, FALLBACK, NONE, XZ, Y

### Community 110 - "PlacementAlignment.java"
Cohesion: 0.35
Nodes (7): InteractionResult, BlockHitResult, BlockPos, Direction, ItemStack, Level, Player

### Community 112 - "BridgingConfig"
Cohesion: 0.18
Nodes (11): DoubleValue, BridgingConfig, BooleanValue, Builder, EnumValue, IntValue, ModConfigSpec, SourcePerspective (+3 more)

### Community 113 - "BridgingKeyMappings"
Cohesion: 0.53
Nodes (3): Category, KeyMapping, BridgingKeyMappings

### Community 114 - "BridgingAdjacency"
Cohesion: 0.24
Nodes (4): ModIds, Identifier, Logger, UtilityCoreQoLBridging

### Community 142 - "OrphanBlockCommand.java"
Cohesion: 0.36
Nodes (7): EventBusSubscriber, Logger, SubscribeEvent, OrphanBlockCommand, CommandContext, CommandSourceStack, RegisterCommandsEvent

### Community 143 - "Plan: `/utilitycore cleanorphans` — purge orphaned block-registry entries left by removed mods"
Cohesion: 0.22
Nodes (8): Background (already established, do not re-derive), Command behavior, Config (`AdminConfig.java`), Constraints (do not violate), Definition of done, Files to touch, Goal, Plan: `/utilitycore cleanorphans` — purge orphaned block-registry entries left by removed mods

### Community 147 - "BridgingAdjacency"
Cohesion: 0.33
Nodes (5): BridgingAdjacency, CORNERS, EDGES, FULL, NONE

### Community 148 - "Path.java"
Cohesion: 0.73
Nodes (3): BlockPos, Vec3, Path

## Knowledge Gaps
- **162 isolated node(s):** `SURFACE`, `FIXED`, `HORIZONTAL`, `VERTICAL`, `NONE` (+157 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **18 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ReturnPortalData` connect `SubscribeEvent` to `Utility Core`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **Why does `RecipePair` connect `PolymorphApi.java` to `PolymorphClientHandler.java`?**
  _High betweenness centrality (0.032) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `HORIZONTAL` to the rest of the system?**
  _162 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Flujo de trabajo — Utility Core (NeoForge)` be split into smaller, more focused modules?**
  _Cohesion score 0.125 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.06265664160401002 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.10121951219512196 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.06448412698412699 - nodes in this community are weakly interconnected._