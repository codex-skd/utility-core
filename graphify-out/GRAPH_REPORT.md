# Graph Report - 26.2  (2026-07-30)

## Corpus Check
- 35 files · ~113,564 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 323 nodes · 550 edges · 25 communities (24 shown, 1 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 22 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e512245b`
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
- UtilityCoreClient.java
- CLAUDE.md — utility_core (26.2)
- gradlew
- Utility Core — Registro de cambios

## God Nodes (most connected - your core abstractions)
1. `ChunkGenManager` - 30 edges
2. `PolymorphClientHandler` - 18 edges
3. `PlayerRecipeData` - 16 edges
4. `Flujo de trabajo — Utility Core (NeoForge)` - 14 edges
5. `CurseForge — Variables del proyecto` - 13 edges
6. `UtilityCore` - 12 edges
7. `SyncRecipesPacket` - 11 edges
8. `RecipePair` - 11 edges
9. `SelectRecipePacket` - 8 edges
10. `Formato de descripciones CurseForge` - 7 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java
- `PolymorphClientHandler` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/client/PolymorphClientHandler.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java
- `ModAttachments` --references--> `PlayerRecipeData`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/attachment/ModAttachments.java → src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java
- `PlayerRecipeData` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java

## Import Cycles
- None detected.

## Communities (25 total, 1 thin omitted)

### Community 0 - "Flujo de trabajo — Utility Core (NeoForge)"
Cohesion: 0.06
Nodes (35): 1. Desarrollo, 2. Copiar a instancia de pruebas, 3. Probar en instancia, 4. Preparar versión para CurseForge, 5. Release estable, 6. Actualizar Knowledge Graph (Graphify), Archivos que pasan a GitHub, Buenas prácticas (+27 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.11
Nodes (18): Closing, CraftingInput, CraftingContainer, CraftingRecipe, EventBusSubscriber, GuiGraphicsExtractor, ItemStack, Minecraft (+10 more)

### Community 2 - "ChunkGenManager"
Cohesion: 0.15
Nodes (10): Field, Gson, MinecraftServer, RegisterCommandsEvent, ChunkGenManager, DimState, Level, Logger (+2 more)

### Community 3 - "PlayerRecipeData"
Cohesion: 0.11
Nodes (15): AttachmentType, ByteBuf, CustomPacketPayload, DeferredRegister, ModAttachments, IPayloadContext, Override, StreamCodec (+7 more)

### Community 4 - "UtilityCore.java"
Cohesion: 0.14
Nodes (13): PlayerLoggedInEvent, PlayerLoggedOutEvent, Post, ServerStartedEvent, ServerStoppingEvent, IEventBus, ModNetwork, IEventBus (+5 more)

### Community 5 - "Config.java"
Cohesion: 0.15
Nodes (14): BooleanValue, Builder, DamageType, IntValue, ModConfigSpec, Redirect, EventBusSubscriber, Pre (+6 more)

### Community 6 - "PolymorphApi.java"
Cohesion: 0.18
Nodes (11): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, Level, RecipeHolder (+3 more)

### Community 7 - "MixinCraftingMenu.java"
Cohesion: 0.25
Nodes (13): AbstractContainerMenu, ServerLevel, CallbackInfo, CraftingContainer, CraftingRecipe, Inject, ItemStack, Mixin (+5 more)

### Community 8 - "CurseForge — Variables del proyecto"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 9 - "SyncRecipesPacket"
Cohesion: 0.24
Nodes (7): RegistryFriendlyByteBuf, IPayloadContext, ItemStack, Override, StreamCodec, Type, SyncRecipesPacket

### Community 10 - ".onExtractBackground"
Cohesion: 0.26
Nodes (8): AccessorAbstractContainerScreen, Accessor, Mixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin, MixinCraftingScreen

### Community 11 - "TombstoneErrorHandler"
Cohesion: 0.33
Nodes (7): ErrorAction, IMixinConfig, IMixinErrorHandler, IMixinInfo, Logger, Override, TombstoneErrorHandler

### Community 12 - "MixinTBScreen.java"
Cohesion: 0.36
Nodes (7): Screen, CallbackInfo, Inject, Minecraft, Mixin, Unique, MixinTBScreen

### Community 13 - "Formato de descripciones CurseForge"
Cohesion: 0.22
Nodes (9): Archivos de CurseForge, Buenas prácticas, Ejemplo de estructura HTML para release notes, Elementos HTML disponibles, Elementos HTML permitidos, Estructura de la descripción general, Estructura del proyecto, Formato de descripciones CurseForge (+1 more)

### Community 14 - "MixinItemInput.java"
Cohesion: 0.43
Nodes (6): CallbackInfoReturnable, Method, Inject, ItemStack, Mixin, MixinItemInput

### Community 15 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 16 - "Utility Core"
Cohesion: 0.33
Nodes (5): Build, Features, Known Incompatibilities, Requirements, Utility Core

### Community 17 - "UtilityCoreClient.java"
Cohesion: 0.60
Nodes (3): Mod, ModContainer, UtilityCoreClient

### Community 18 - "CLAUDE.md — utility_core (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.2), Paso 0 obligatorio, Prioridad de instrucciones

### Community 19 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **53 isolated node(s):** `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.1`, `Features`, `Requirements` (+48 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **1 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ChunkGenManager` connect `ChunkGenManager` to `UtilityCore.java`?**
  _High betweenness centrality (0.092) - this node is a cross-community bridge._
- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `SyncRecipesPacket`, `PlayerRecipeData`?**
  _High betweenness centrality (0.044) - this node is a cross-community bridge._
- **Why does `PlayerRecipeData` connect `PlayerRecipeData` to `PolymorphApi.java`?**
  _High betweenness centrality (0.034) - this node is a cross-community bridge._
- **What connects `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.1` to the rest of the system?**
  _53 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Flujo de trabajo — Utility Core (NeoForge)` be split into smaller, more focused modules?**
  _Cohesion score 0.05555555555555555 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.10873440285204991 - nodes in this community are weakly interconnected._
- **Should `ChunkGenManager` be split into smaller, more focused modules?**
  _Cohesion score 0.14516129032258066 - nodes in this community are weakly interconnected._