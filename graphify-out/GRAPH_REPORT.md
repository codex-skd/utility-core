# Graph Report - 26.1.2  (2026-07-31)

## Corpus Check
- 61 files · ~119,478 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 437 nodes · 521 edges · 119 communities (40 shown, 79 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 35 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `ca22a436`
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
- Minecraft
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
- CallbackInfo
- Inject
- Minecraft
- Mixin
- Unique
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
1. `Utility Core — Registro de cambios` - 37 edges
2. `ChunkGenManager` - 30 edges
3. `SpongeSchematicReader` - 17 edges
4. `SpawnSchematicManager` - 16 edges
5. `PolymorphClientHandler` - 15 edges
6. `UtilityCore` - 13 edges
7. `Flujo de trabajo — Utility Core (NeoForge)` - 13 edges
8. `CurseForge — Variables del proyecto` - 13 edges
9. `Publicación a GitHub (CI/CD)` - 11 edges
10. `PlayerRecipeData` - 10 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `SpawnSchematicManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/schematic/SpawnSchematicManager.java
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java

## Import Cycles
- None detected.

## Communities (119 total, 79 thin omitted)

### Community 0 - ".onExtractBackground"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.11
Nodes (5): Closing, CraftingInput, PolymorphClientHandler, AccessorAbstractContainerScreen, RecipePair

### Community 2 - "PlayerRecipeData"
Cohesion: 0.14
Nodes (14): Block, BlockState, CompoundTag, HolderLookup, BlockPos, Gson, Logger, MinecraftServer (+6 more)

### Community 3 - "PolymorphApi.java"
Cohesion: 0.26
Nodes (8): BlockEvent, BreakBlockEvent, Detonate, EntityMultiPlaceEvent, EntityPlaceEvent, EventBusSubscriber, SubscribeEvent, SpawnProtectionHandler

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.06
Nodes (32): Archivos de CurseForge, Buenas prácticas, Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, ¿Cuándo incrementar versión?, Ejemplo de estructura HTML para release notes, Ejemplos (+24 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.05
Nodes (37): 0.0.1-beta.1, 0.0.1-beta.2, 0.0.1-beta.3, 0.0.1-beta.4, 0.0.1-beta.5, 0.0.1-beta.6, 1.0.14, 1.0.15 (+29 more)

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.10
Nodes (8): AbstractContainerMenu, ByteBuf, CustomPacketPayload, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData, RegistryFriendlyByteBuf

### Community 7 - "UtilityCore.java"
Cohesion: 0.07
Nodes (25): AttachmentType, DeferredRegister, Field, ModAttachments, ModNetwork, PlayerLoggedInEvent, PlayerLoggedOutEvent, Post (+17 more)

### Community 9 - "MixinDamageContainer.java"
Cohesion: 0.17
Nodes (12): 0. Determinar alcance de versión, 1. Desarrollo, 2. Probar en local, 3. Preparar versión para CurseForge, 4. Release estable, 5. Actualizar Knowledge Graph (Graphify), Archivos que pasan a GitHub, Backend LLM: Ollama local (+4 more)

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.29
Nodes (6): Build, Features, Known Incompatibilities, Requirements, Spawn Schematic (opt-in), Utility Core

### Community 11 - "gradlew"
Cohesion: 0.11
Nodes (14): BooleanValue, Builder, CallbackInfoReturnable, DamageType, OutpostZeroCompat, MixinCraftingScreen, MixinDamageContainer, MixinInventoryScreen (+6 more)

### Community 12 - "build.gradle"
Cohesion: 0.50
Nodes (3): Fixed, Technical Details, Utility Core 1.0.20

### Community 13 - "settings.gradle"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 14 - "EnderDragonRespawnHandler.java"
Cohesion: 0.50
Nodes (3): CLAUDE.md — utility_core (26.1.2), Paso 0 obligatorio, Prioridad de instrucciones

### Community 16 - "TombstoneErrorHandler"
Cohesion: 0.39
Nodes (5): ErrorAction, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

## Knowledge Gaps
- **92 isolated node(s):** `Paso 0 obligatorio`, `Prioridad de instrucciones`, `1.2.0`, `1.1.7`, `1.1.6` (+87 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **79 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnSchematicManager` connect `PlayerRecipeData` to `PolymorphApi.java`, `UtilityCore.java`?**
  _High betweenness centrality (0.045) - this node is a cross-community bridge._
- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `SyncRecipesPacket`?**
  _High betweenness centrality (0.039) - this node is a cross-community bridge._
- **What connects `Paso 0 obligatorio`, `Prioridad de instrucciones`, `1.2.0` to the rest of the system?**
  _92 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.onExtractBackground` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `PlayerRecipeData` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._
- **Should `MixinCraftingMenu.java` be split into smaller, more focused modules?**
  _Cohesion score 0.06060606060606061 - nodes in this community are weakly interconnected._