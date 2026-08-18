# Utility Core QoL - CurseForge Documentation

## Project Info
- **Project Name**: Utility Core QoL
- **Project Slug**: utility-core-qol
- **Mod ID**: `utility_core_qol`
- **Display Name**: Utility Core QoL
- **Version**: 2.0.0-beta.1 (CurseForge) / 2.0.0 (NeoForge internal)
- **NeoForge Version**: 26.2.0.37-beta
- **Minecraft Version**: 26.2
- **License**: MIT
- **Category**: Client QoL

---

## Description (Long)
Utility Core QoL - Quality of life client-side improvements for Minecraft.

### Features

**Recipe Selector (Polymorph System)**
- When multiple crafting recipes match the same ingredients, shows a clickable grid to choose which result you want
- Works in **Crafting Table** (3x3) and **Player Inventory** (2x2) crafting grids
- Syncs selection to server for correct result item
- Configurable max recipes displayed (1-64, default 16)
- Keyboard/mouse navigation

**Biome & Dimension Titles**
- Shows vanilla title/subtitle when entering a new biome or dimension
- Ported from Traveler's Titles (LGPLv3) by YUNGNICKYOUNG
- Reuses vanilla title HUD (no custom renderer)
- Fade-in/out timings: 10/70/20 ticks

**Title Vertical Offset**
- Configurable vertical shift for vanilla title/subtitle text
- Range: -200 to +200 pixels (default +75)
- Positive = up, Negative = down

---

## Configuration
Via `utility_core_qol-common.toml`:

```toml
# Recipe Selector
enableCraftingRecipeSelector = true
maxRecipesDisplayed = 16
logDetectedConflicts = false

# Biome/Dimension Titles
enableBiomeDimensionTitles = true

# Title Vertical Offset
titleVerticalOffset = 75  # -200 to 200
```

---

## How Recipe Selector Works
1. Open crafting table or inventory crafting grid
2. Place ingredients that match multiple recipes
3. Grid appears showing all possible results
4. Click desired result → server syncs selection
4. Result updates in output slot

---

## API (for other mods)
```java
// Get player's recipe selection data
PolymorphApi.INSTANCE.getPlayerRecipeData(player)

// Find all recipes matching input
PolymorphApi.INSTANCE.getRecipesFor(recipeManager, recipeType, input, level)

// Clear player's recipe data
PolymorphApi.INSTANCE.clearPlayerRecipeData(player)
```

---

## Credits
- **Biome/Dimension Titles**: Ported from [Traveler's Titles](https://github.com/YUNGNICKYOUNG/TravelersTitles) by YUNGNICKYOUNG (LGPLv3)

---

## Requirements
- **NeoForge**: 26.2.0.37-beta+
- **Side**: Client (primary) + Server (sync packets)

---

## Changelog (2.0.0-beta.1)
- Initial split from monolithic Utility Core
- All QoL features preserved from v1.11.x
- Standalone mod with own modId (`utility_core_qol`)
- Recipe Selector, Biome Titles, Title Offset