# Graph Report - .  (2026-07-20)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 165 nodes · 310 edges · 14 communities
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 11 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `dc599350`
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

## God Nodes (most connected - your core abstractions)
1. `PolymorphClientHandler` - 17 edges
2. `PlayerRecipeData` - 16 edges
3. `SyncRecipesPacket` - 11 edges
4. `RecipePair` - 11 edges
5. `SelectRecipePacket` - 8 edges
6. `Config` - 5 edges
7. `PolymorphApi` - 5 edges
8. `MixinCraftingMenu` - 5 edges
9. `UtilityCore` - 4 edges
10. `ModAttachments` - 4 edges

## Surprising Connections (you probably didn't know these)
- `PolymorphClientHandler` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/client/PolymorphClientHandler.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java
- `ModAttachments` --references--> `PlayerRecipeData`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/attachment/ModAttachments.java → src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java
- `PlayerRecipeData` --references--> `RecipePair`  [EXTRACTED]
  src/main/java/com/skd/utilitycore/polymorph/PlayerRecipeData.java → src/main/java/com/skd/utilitycore/polymorph/RecipePair.java

## Import Cycles
- None detected.

## Communities (14 total, 0 thin omitted)

### Community 0 - ".onExtractBackground"
Cohesion: 0.12
Nodes (18): BooleanValue, Builder, IntValue, ModConfigSpec, Config, AccessorAbstractContainerScreen, Accessor, Mixin (+10 more)

### Community 1 - "PolymorphClientHandler.java"
Cohesion: 0.16
Nodes (12): CraftingInput, EventBusSubscriber, Minecraft, Pre, CraftingContainer, CraftingRecipe, GuiGraphicsExtractor, ItemStack (+4 more)

### Community 2 - "PlayerRecipeData"
Cohesion: 0.16
Nodes (8): AttachmentType, DeferredRegister, ModAttachments, ItemStack, PlayerRecipeData, ItemStack, RecipeHolder, RecipePair

### Community 3 - "PolymorphApi.java"
Cohesion: 0.18
Nodes (11): Level, Player, RecipeHolder, RecipeManager, RecipeType, PolymorphApi, Level, RecipeHolder (+3 more)

### Community 4 - "MixinCraftingMenu.java"
Cohesion: 0.25
Nodes (13): AbstractContainerMenu, ServerLevel, CallbackInfo, CraftingContainer, CraftingRecipe, Inject, ItemStack, Mixin (+5 more)

### Community 5 - "SelectRecipePacket.java"
Cohesion: 0.26
Nodes (7): ByteBuf, CustomPacketPayload, IPayloadContext, Override, StreamCodec, Type, SelectRecipePacket

### Community 6 - "SyncRecipesPacket"
Cohesion: 0.27
Nodes (7): RegistryFriendlyByteBuf, IPayloadContext, ItemStack, Override, StreamCodec, Type, SyncRecipesPacket

### Community 7 - "UtilityCore.java"
Cohesion: 0.29
Nodes (7): Logger, IEventBus, ModNetwork, IEventBus, Mod, ModContainer, UtilityCore

### Community 8 - "AccessorCraftingMenu.java"
Cohesion: 0.43
Nodes (5): AccessorCraftingMenu, Accessor, CraftingContainer, Mixin, ResultContainer

### Community 9 - "MixinDamageContainer.java"
Cohesion: 0.60
Nodes (3): Redirect, Mixin, MixinDamageContainer

### Community 10 - "UtilityCoreClient.java"
Cohesion: 0.60
Nodes (3): Mod, ModContainer, UtilityCoreClient

### Community 11 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PolymorphClientHandler` connect `PolymorphClientHandler.java` to `.onExtractBackground`, `PlayerRecipeData`?**
  _High betweenness centrality (0.100) - this node is a cross-community bridge._
- **Why does `PlayerRecipeData` connect `PlayerRecipeData` to `PolymorphApi.java`, `SelectRecipePacket.java`?**
  _High betweenness centrality (0.087) - this node is a cross-community bridge._
- **Why does `RecipePair` connect `PlayerRecipeData` to `PolymorphClientHandler.java`, `SelectRecipePacket.java`?**
  _High betweenness centrality (0.080) - this node is a cross-community bridge._
- **Should `.onExtractBackground` be split into smaller, more focused modules?**
  _Cohesion score 0.11965811965811966 - nodes in this community are weakly interconnected._