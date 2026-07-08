# Utility Core

A library mod for NeoForge that provides shared utilities and features for mod developers and players.

## Features

### Recipe Conflict Resolution (Polymorph-style)
When multiple crafting recipes match the same inputs, a selection widget appears in the crafting table GUI allowing players to choose which output they want. The selected recipe is highlighted in green.

### Configurable
All features can be configured via the in-game config screen or the config file:
- Enable/disable the crafting recipe selector
- Maximum number of alternative recipes displayed

### Developer API
Other mods can integrate with the recipe selection system using the provided `PolymorphApi` class.

## Requirements
- NeoForge 26.1.2+
- Minecraft 26.1.2
- Both client and server

## Compatibility
- Designed to be compatible with any mod
- Server-side functionality with client-side UI

### Known Incompatibilities
- **Fast Workbench (fastbench)**: This mod conflicts with Utility Core's recipe selection system. Utility Core will not function correctly if Fast Workbench is installed. Please remove Fast Workbench if you want to use the recipe selector feature.

## Installation
1. Download the latest version
2. Place the jar in your `mods` folder on **both client and server**
3. Start the game/server
4. Open a crafting table and place items to see the recipe selector in action

## Configuration
Configuration can be accessed via:
- In-game: Mods menu > Utility Core > Config
- File: `config/utility_core-common.toml`

## For Developers

```java
// Access the API
PolymorphApi api = PolymorphApi.getInstance();

// Get all recipes matching a specific input
List<RecipeHolder<CraftingRecipe>> recipes = api.getRecipesFor(
    recipeManager, RecipeType.CRAFTING, craftingInput, level
);

// Get player recipe selection data
PlayerRecipeData data = api.getPlayerRecipeData(player);

// Clear player recipe selection data
api.clearPlayerRecipeData(player);

## Changelog

### 1.0.3
- Fixed server crash with Armadillo + Tombstone + Apothic Attributes: negative damage clamped to 0
```
