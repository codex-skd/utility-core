# Utility Core

A library mod for NeoForge (MC 26.1.2) that provides shared utilities and features for mod developers and players.

## Features

### Recipe Conflict Resolution (Polymorph-style)
When multiple crafting recipes match the same inputs, a selection widget appears in the crafting table and inventory 2x2 grid, allowing players to choose which output they want. The selected recipe is highlighted in green, and alternatives are shown with a white background.

- **Server-side**: Detects matching recipes, stores the player's selection via attachments, syncs the output list to the client
- **Client-side**: Renders a recipe selector grid, sends selection changes back to the server
- **Instant update**: The result slot updates immediately when clicking an alternative recipe

### Damage Safety
Negative damage values from mod interactions (e.g., Apothic Attributes critical strikes + Tombstone's Decrepitude effect) are automatically clamped to 0, preventing `IllegalArgumentException: Damage cannot be negative` crashes.

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
- **Fast Workbench (fastbench)**: Conflicts with the recipe selection system. Remove it to use this feature.

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
```
