# Graph Report - utility_core  (2026-07-26)

## Corpus Check
- 51 files · ~116,115 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 380 nodes · 609 edges · 38 communities (37 shown, 1 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 22 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `b9a6eeda`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
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
- EnderDragonRespawnHandler.java
- CurseForge — Variables del proyecto
- TombstoneErrorHandler
- MixinTBScreen.java
- MixinItemInput.java
- Utility Core
- Utility Core 1.0.20
- Utility Core 1.0.21
- .onExtractBackground

## God Nodes (most connected - your core abstractions)
1. `ChunkGenManager` - 30 edges
2. `Utility Core — Registro de cambios` - 29 edges
3. `PolymorphClientHandler` - 18 edges
4. `PlayerRecipeData` - 16 edges
5. `Flujo de trabajo — Utility Core (NeoForge)` - 13 edges
6. `CurseForge — Variables del proyecto` - 13 edges
7. `UtilityCore` - 12 edges
8. `SyncRecipesPacket` - 11 edges
9. `RecipePair` - 11 edges
10. `EnderDragonRespawnHandler` - 8 edges

## Surprising Connections (you probably didn't know these)
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

## Communities (38 total, 1 thin omitted)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.15
Nodes (13): Closing, CraftingInput, CraftingContainer, CraftingRecipe, EventBusSubscriber, GuiGraphicsExtractor, ItemStack, Minecraft (+5 more)

### Community 2 - "PlayerRecipeData"
Cohesion: 0.10
Nodes (22): AbstractContainerMenu, AttachmentType, DeferredRegister, ModAttachments, CallbackInfo, CraftingContainer, CraftingRecipe, Inject (+14 more)

### Community 3 - "PolymorphApi.java"
Cohesion: 0.18
Nodes (11): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, Level, RecipeHolder (+3 more)

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.05
Nodes (43): 1. Desarrollo, 2. Copiar a instancia de pruebas, 3. Probar en instancia, 4. Preparar versión para CurseForge, 5. Release estable, 6. Actualizar Knowledge Graph (Graphify), Archivos de CurseForge, Archivos que pasan a GitHub (+35 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.07
Nodes (29): 0.0.1-beta.1, 0.0.1-beta.2, 0.0.1-beta.3, 0.0.1-beta.4, 0.0.1-beta.5, 0.0.1-beta.6, 1.0.14, 1.0.15 (+21 more)

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.16
Nodes (13): ByteBuf, CustomPacketPayload, RegistryFriendlyByteBuf, Override, StreamCodec, Type, SelectRecipePacket, IPayloadContext (+5 more)

### Community 7 - "UtilityCore.java"
Cohesion: 0.08
Nodes (23): Field, Gson, MinecraftServer, PlayerLoggedInEvent, PlayerLoggedOutEvent, Post, RegisterCommandsEvent, ServerStoppingEvent (+15 more)

### Community 8 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 9 - "MixinDamageContainer.java"
Cohesion: 0.15
Nodes (14): BooleanValue, Builder, CallbackInfoReturnable, IntValue, Method, ModConfigSpec, Redirect, Config (+6 more)

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.60
Nodes (3): Mod, ModContainer, UtilityCoreClient

### Community 11 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 14 - "EnderDragonRespawnHandler.java"
Cohesion: 0.33
Nodes (7): BlockPos, EnderDragonFight, EnderDragonRespawnHandler, EventBusSubscriber, ServerLevel, ServerStartedEvent, SubscribeEvent

### Community 15 - "CurseForge — Variables del proyecto"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 16 - "TombstoneErrorHandler"
Cohesion: 0.33
Nodes (7): ErrorAction, IMixinConfig, IMixinErrorHandler, IMixinInfo, Logger, Override, TombstoneErrorHandler

### Community 17 - "MixinTBScreen.java"
Cohesion: 0.36
Nodes (7): Screen, CallbackInfo, Inject, Minecraft, Mixin, Unique, MixinTBScreen

### Community 18 - "MixinItemInput.java"
Cohesion: 0.39
Nodes (6): DamageType, EventBusSubscriber, Pre, ResourceKey, SubscribeEvent, OutpostZeroCompat

### Community 19 - "Utility Core"
Cohesion: 0.33
Nodes (5): Build, Features, Known Incompatibilities, Requirements, Utility Core

### Community 20 - "Utility Core 1.0.20"
Cohesion: 0.50
Nodes (3): Fixed, Technical Details, Utility Core 1.0.20

### Community 35 - ".onExtractBackground"
Cohesion: 0.16
Nodes (13): AccessorAbstractContainerScreen, Accessor, Mixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin, MixinCraftingScreen (+5 more)

## Knowledge Gaps
- **80 isolated node(s):** `1.1.0`, `1.0.41`, `1.0.40`, `1.0.39`, `1.0.38` (+75 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **1 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `PlayerRecipeData`, `.onExtractBackground`, `SyncRecipesPacket`?**
  _High betweenness centrality (0.034) - this node is a cross-community bridge._
- **Why does `PlayerRecipeData` connect `PlayerRecipeData` to `PolymorphApi.java`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **What connects `1.1.0`, `1.0.41`, `1.0.40` to the rest of the system?**
  _80 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.0951219512195122 - nodes in this community are weakly interconnected._
- **Should `MixinCraftingMenu.java` be split into smaller, more focused modules?**
  _Cohesion score 0.045454545454545456 - nodes in this community are weakly interconnected._
- **Should `SelectRecipePacket.java` be split into smaller, more focused modules?**
  _Cohesion score 0.06666666666666667 - nodes in this community are weakly interconnected._
- **Should `UtilityCore.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08013468013468013 - nodes in this community are weakly interconnected._