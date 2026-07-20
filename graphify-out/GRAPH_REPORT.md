# Graph Report - utility_core  (2026-07-20)

## Corpus Check
- 40 files · ~111,154 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 306 nodes · 493 edges · 28 communities (27 shown, 1 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 11 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `7993805f`
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
- EnderDragonRespawnHandler.java
- CurseForge — Variables del proyecto
- TombstoneErrorHandler
- MixinTBScreen.java
- MixinItemInput.java
- Utility Core
- Utility Core 1.0.20
- Utility Core 1.0.21

## God Nodes (most connected - your core abstractions)
1. `Utility Core — Registro de cambios` - 19 edges
2. `PolymorphClientHandler` - 18 edges
3. `PlayerRecipeData` - 16 edges
4. `CurseForge — Variables del proyecto` - 13 edges
5. `SyncRecipesPacket` - 11 edges
6. `RecipePair` - 11 edges
7. `EnderDragonRespawnHandler` - 9 edges
8. `Flujo de trabajo — Mods Minecraft (NeoForge)` - 9 edges
9. `SelectRecipePacket` - 8 edges
10. `Formato de descripciones CurseForge` - 7 edges

## Surprising Connections (you probably didn't know these)
- `PolymorphClientHandler` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/client/PolymorphClientHandler.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java
- `ModAttachments` --references--> `PlayerRecipeData`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/attachment/ModAttachments.java → src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java
- `PlayerRecipeData` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java

## Import Cycles
- None detected.

## Communities (28 total, 1 thin omitted)

### Community 0 - ".onExtractBackground"
Cohesion: 0.26
Nodes (8): AccessorAbstractContainerScreen, Accessor, Mixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin, MixinInventoryScreen

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.11
Nodes (18): Closing, CraftingInput, CraftingContainer, CraftingRecipe, EventBusSubscriber, GuiGraphicsExtractor, ItemStack, Minecraft (+10 more)

### Community 2 - "PlayerRecipeData"
Cohesion: 0.11
Nodes (21): AbstractContainerMenu, AttachmentType, DeferredRegister, ModAttachments, CallbackInfo, CraftingContainer, CraftingRecipe, Inject (+13 more)

### Community 3 - "PolymorphApi.java"
Cohesion: 0.18
Nodes (11): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, Level, RecipeHolder (+3 more)

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.06
Nodes (31): 1. Desarrollo, 2. Copiar a instancia de pruebas, 3. Probar en instancia, 4. Preparar versión para CurseForge, 5. Release estable, Archivos de CurseForge, Buenas prácticas, Buenas prácticas (+23 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.10
Nodes (19): 0.0.1-beta.1, 0.0.1-beta.2, 0.0.1-beta.3, 0.0.1-beta.4, 0.0.1-beta.5, 0.0.1-beta.6, 1.0.14, 1.0.15 (+11 more)

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.14
Nodes (14): ByteBuf, CustomPacketPayload, RegistryFriendlyByteBuf, IPayloadContext, Override, StreamCodec, Type, SelectRecipePacket (+6 more)

### Community 7 - "UtilityCore.java"
Cohesion: 0.27
Nodes (7): IEventBus, ModNetwork, IEventBus, Logger, Mod, ModContainer, UtilityCore

### Community 8 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 9 - "MixinDamageContainer.java"
Cohesion: 0.15
Nodes (14): BooleanValue, Builder, DamageType, IntValue, ModConfigSpec, Redirect, ResourceKey, EventBusSubscriber (+6 more)

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.60
Nodes (3): Mod, ModContainer, UtilityCoreClient

### Community 11 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 14 - "EnderDragonRespawnHandler.java"
Cohesion: 0.36
Nodes (7): BlockPos, EnderDragonFight, ServerStartedEvent, EnderDragonRespawnHandler, EventBusSubscriber, ServerLevel, SubscribeEvent

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
Cohesion: 0.43
Nodes (6): CallbackInfoReturnable, Method, Inject, ItemStack, Mixin, MixinItemInput

### Community 19 - "Utility Core"
Cohesion: 0.33
Nodes (5): Build, Features, Known Incompatibilities, Requirements, Utility Core

### Community 20 - "Utility Core 1.0.20"
Cohesion: 0.50
Nodes (3): Fixed, Technical Details, Utility Core 1.0.20

## Knowledge Gaps
- **60 isolated node(s):** `1.0.32`, `1.0.25`, `1.0.24`, `1.0.23`, `1.0.22` (+55 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **1 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `PlayerRecipeData`, `SyncRecipesPacket`?**
  _High betweenness centrality (0.043) - this node is a cross-community bridge._
- **Why does `PlayerRecipeData` connect `PlayerRecipeData` to `PolymorphApi.java`, `SyncRecipesPacket`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **Why does `RecipePair` connect `PlayerRecipeData` to `PolymorphClientHandler.java`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **What connects `1.0.32`, `1.0.25`, `1.0.24` to the rest of the system?**
  _60 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.10873440285204991 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.10960960960960961 - nodes in this community are weakly interconnected._
- **Should `MixinCraftingMenu.java` be split into smaller, more focused modules?**
  _Cohesion score 0.0625 - nodes in this community are weakly interconnected._