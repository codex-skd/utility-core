# Graph Report - 26.1.2  (2026-07-27)

## Corpus Check
- 52 files · ~116,660 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 383 nodes · 375 edges · 126 communities (34 shown, 92 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 22 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

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
- Minecraft
- Pre
- RecipeHolder
- RecipeManager
- SubscribeEvent
- EventBusSubscriber
- ServerLevel
- ServerStartedEvent
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
- IEventBus
- Logger
- Mod
- ModContainer
- ServerStartedEvent
- SubscribeEvent
- Mod
- ModContainer

## God Nodes (most connected - your core abstractions)
1. `ChunkGenManager` - 30 edges
2. `Utility Core — Registro de cambios` - 30 edges
3. `PolymorphClientHandler` - 15 edges
4. `Flujo de trabajo — Utility Core (NeoForge)` - 14 edges
5. `CurseForge — Variables del proyecto` - 13 edges
6. `UtilityCore` - 10 edges
7. `PlayerRecipeData` - 10 edges
8. `SyncRecipesPacket` - 8 edges
9. `EnderDragonRespawnHandler` - 7 edges
10. `Formato de descripciones CurseForge` - 7 edges

## Surprising Connections (you probably didn't know these)
- `UtilityCore` --references--> `ChunkGenManager`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/UtilityCore.java → src/main/java/com/skd/utilitycore/compat/ChunkGenManager.java

## Import Cycles
- None detected.

## Communities (126 total, 92 thin omitted)

### Community 0 - ".onExtractBackground"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.09
Nodes (7): Closing, CraftingInput, PolymorphClientHandler, AccessorAbstractContainerScreen, MixinCraftingScreen, MixinInventoryScreen, RecipePair

### Community 2 - "PlayerRecipeData"
Cohesion: 0.83
Nodes (3): AttachmentType, DeferredRegister, ModAttachments

### Community 3 - "PolymorphApi.java"
Cohesion: 0.22
Nodes (9): Archivos de CurseForge, Buenas prácticas, Ejemplo de estructura HTML para release notes, Elementos HTML disponibles, Elementos HTML permitidos, Estructura de la descripción general, Estructura del proyecto, Formato de descripciones CurseForge (+1 more)

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.06
Nodes (35): 1. Desarrollo, 2. Copiar a instancia de pruebas, 3. Probar en instancia, 4. Preparar versión para CurseForge, 5. Release estable, 6. Actualizar Knowledge Graph (Graphify), Archivos que pasan a GitHub, Buenas prácticas (+27 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.06
Nodes (30): 0.0.1-beta.1, 0.0.1-beta.2, 0.0.1-beta.3, 0.0.1-beta.4, 0.0.1-beta.5, 0.0.1-beta.6, 1.0.14, 1.0.15 (+22 more)

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.11
Nodes (8): AbstractContainerMenu, ByteBuf, CustomPacketPayload, MixinCraftingMenu, SelectRecipePacket, SyncRecipesPacket, PlayerRecipeData, RegistryFriendlyByteBuf

### Community 7 - "UtilityCore.java"
Cohesion: 0.10
Nodes (15): Field, UtilityCore, Gson, MinecraftServer, PlayerLoggedInEvent, PlayerLoggedOutEvent, Post, RegisterCommandsEvent (+7 more)

### Community 9 - "MixinDamageContainer.java"
Cohesion: 0.40
Nodes (5): BooleanValue, Builder, Config, IntValue, ModConfigSpec

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.33
Nodes (5): Build, Features, Known Incompatibilities, Requirements, Utility Core

### Community 11 - "gradlew"
Cohesion: 0.60
Nodes (3): CallbackInfoReturnable, MixinItemInput, Method

### Community 12 - "build.gradle"
Cohesion: 0.50
Nodes (3): Fixed, Technical Details, Utility Core 1.0.20

### Community 13 - "settings.gradle"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 14 - "EnderDragonRespawnHandler.java"
Cohesion: 0.42
Nodes (3): BlockPos, EnderDragonFight, EnderDragonRespawnHandler

### Community 16 - "TombstoneErrorHandler"
Cohesion: 0.39
Nodes (5): ErrorAction, TombstoneErrorHandler, IMixinConfig, IMixinErrorHandler, IMixinInfo

## Knowledge Gaps
- **82 isolated node(s):** `1.1.1`, `1.1.0`, `1.0.41`, `1.0.40`, `1.0.39` (+77 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **92 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Flujo de trabajo — Utility Core (NeoForge)` connect `MixinCraftingMenu.java` to `PolymorphApi.java`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **What connects `1.1.1`, `1.1.0`, `1.0.41` to the rest of the system?**
  _82 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.onExtractBackground` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._
- **Should `PolymorphClientHandler.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08870967741935484 - nodes in this community are weakly interconnected._
- **Should `MixinCraftingMenu.java` be split into smaller, more focused modules?**
  _Cohesion score 0.05555555555555555 - nodes in this community are weakly interconnected._
- **Should `SelectRecipePacket.java` be split into smaller, more focused modules?**
  _Cohesion score 0.06451612903225806 - nodes in this community are weakly interconnected._
- **Should `SyncRecipesPacket` be split into smaller, more focused modules?**
  _Cohesion score 0.10837438423645321 - nodes in this community are weakly interconnected._