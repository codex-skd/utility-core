# Graph Report - 26.2  (2026-08-01)

## Corpus Check
- 47 files · ~114,784 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 383 nodes · 709 edges · 32 communities
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 37 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `356d4564`
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
- TombstoneErrorHandler
- MixinTBScreen.java
- MixinItemInput.java
- AccessorCraftingMenu.java
- Utility Core
- UtilityCoreClient.java
- CLAUDE.md — utility_core (26.2)
- gradlew
- Utility Core — Registro de cambios
- Publicación a GitHub (CI/CD)
- OutpostZeroCompat.java

## God Nodes (most connected - your core abstractions)
1. `ChunkGenManager` - 30 edges
2. `SpawnSchematicManager` - 19 edges
3. `PolymorphClientHandler` - 18 edges
4. `SpongeSchematicReader` - 18 edges
5. `PlayerRecipeData` - 16 edges
6. `UtilityCore` - 13 edges
7. `CurseForge — Variables del proyecto` - 13 edges
8. `SyncRecipesPacket` - 11 edges
9. `RecipePair` - 11 edges
10. `Flujo de trabajo — Utility Core (NeoForge)` - 11 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java
- `PolymorphClientHandler` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/client/PolymorphClientHandler.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java
- `ModAttachments` --references--> `PlayerRecipeData`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/attachment/ModAttachments.java → src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java
- `PlayerRecipeData` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java

## Import Cycles
- None detected.

## Communities (32 total, 0 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Utility Core (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.08
Nodes (26): Closing, CraftingInput, CraftingContainer, CraftingRecipe, EventBusSubscriber, GuiGraphicsExtractor, ItemStack, Minecraft (+18 more)

### Community 2 - "ChunkGenManager"
Cohesion: 0.08
Nodes (23): Field, PlayerLoggedInEvent, PlayerLoggedOutEvent, RegisterCommandsEvent, ServerStartedEvent, ServerStoppingEvent, ChunkGenManager, DimState (+15 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.10
Nodes (22): AbstractContainerMenu, AttachmentType, DeferredRegister, ModAttachments, CallbackInfo, CraftingContainer, CraftingRecipe, Inject (+14 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.14
Nodes (14): Block, BlockState, CompoundTag, HolderLookup, BlockPos, Gson, Logger, MinecraftServer (+6 more)

### Community 5 - "Config.java"
Cohesion: 0.13
Nodes (15): BooleanValue, Builder, EnumValue, IntValue, ModConfigSpec, ModifyArg, Redirect, Config (+7 more)

### Community 6 - "PolymorphApi.java"
Cohesion: 0.18
Nodes (11): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, Level, RecipeHolder (+3 more)

### Community 7 - "MixinCraftingMenu.java"
Cohesion: 0.30
Nodes (10): Biome, Component, LocalPlayer, BiomeDimensionTitleHandler, EventBusSubscriber, Level, Minecraft, Post (+2 more)

### Community 8 - "CurseForge — Variables del proyecto"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 9 - "SyncRecipesPacket"
Cohesion: 0.16
Nodes (13): ByteBuf, CustomPacketPayload, RegistryFriendlyByteBuf, Override, StreamCodec, Type, SelectRecipePacket, IPayloadContext (+5 more)

### Community 11 - "TombstoneErrorHandler"
Cohesion: 0.33
Nodes (7): ErrorAction, IMixinConfig, IMixinErrorHandler, IMixinInfo, Logger, Override, TombstoneErrorHandler

### Community 12 - "MixinTBScreen.java"
Cohesion: 0.36
Nodes (7): Screen, CallbackInfo, Inject, Minecraft, Mixin, Unique, MixinTBScreen

### Community 14 - "MixinItemInput.java"
Cohesion: 0.43
Nodes (6): CallbackInfoReturnable, Method, Inject, ItemStack, Mixin, MixinItemInput

### Community 15 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 16 - "Utility Core"
Cohesion: 0.29
Nodes (6): Build, Features, Known Incompatibilities, Requirements, Spawn Schematic (opt-in), Utility Core

### Community 17 - "UtilityCoreClient.java"
Cohesion: 0.60
Nodes (3): Mod, ModContainer, UtilityCoreClient

### Community 18 - "CLAUDE.md — utility_core (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.2), Prioridad de instrucciones, Workflow del mod

### Community 19 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 20 - "Utility Core — Registro de cambios"
Cohesion: 0.20
Nodes (9): 0.0.0-beta.1, 1.0.0, 1.1.0, 1.2.0, 1.3.0, 1.3.1, 1.4.0, 1.5.0 (+1 more)

### Community 26 - "Publicación a GitHub (CI/CD)"
Cohesion: 0.26
Nodes (8): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, EventBusSubscriber, SubscribeEvent, SpawnProtectionHandler

### Community 27 - "OutpostZeroCompat.java"
Cohesion: 0.39
Nodes (6): DamageType, EventBusSubscriber, Pre, ResourceKey, SubscribeEvent, OutpostZeroCompat

## Knowledge Gaps
- **39 isolated node(s):** `SURFACE`, `FIXED`, `Workflow del mod`, `Prioridad de instrucciones`, `1.5.0` (+34 more)
  These have ≤1 connection - possible missing edges or undocumented components.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `UtilityCore.java` to `Publicación a GitHub (CI/CD)`, `ChunkGenManager`?**
  _High betweenness centrality (0.094) - this node is a cross-community bridge._
- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `SyncRecipesPacket`, `PlayerRecipeData`?**
  _High betweenness centrality (0.042) - this node is a cross-community bridge._
- **What connects `SURFACE`, `FIXED`, `Workflow del mod` to the rest of the system?**
  _39 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08309178743961353 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.08013468013468013 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.0951219512195122 - nodes in this community are weakly interconnected._
- **Should `UtilityCore.java` be split into smaller, more focused modules?**
  _Cohesion score 0.13655761024182078 - nodes in this community are weakly interconnected._