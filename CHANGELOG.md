# Utility Core Changelog

## 1.0.18
- Fixed: recipe conflict selector in inventory 2x2 crafting grid positioned on top of the grid instead of to the right of the result slot
- Moved selector position from `leftPos + 124, topPos + 30` (overlapping grid) to `leftPos + 172, topPos + 27` (right of result slot)
- Fixed: selector in inventory screen rendered behind status effects (buffs/debuffs covering the selector)
- Changed injection point from `extractBackground` to `extractRenderState` to render in the top GUI layer
- Fine-tuned Y alignment by -1 pixel

## 1.0.17
- Fixed: recipe conflict selector would persist on screen after clearing the crafting grid or switching to a non-conflicting recipe
- Server now clears client recipe cache and PlayerRecipeData when crafting grid has ≤1 matching recipe
- Client now clears recipe cache when closing crafting/inventory screen
- Fixed: recipe selector staying visible in inventory screen after using it once

## 1.0.16
- Fixed: Tombstone auto-resets GUI scale to maximum when opening its menus and fails to restore it on screen close
- Added mixin for TBScreen.removed() to restore original GUI scale as a safety net when screens are closed unexpectedly

## 1.0.15
- Fixed crash: Tombstone 9.5.6 mixin `ItemInputMixin` crashes on `ItemCombinerScreen` due to changed method signature in MC 1.21.3
- Registered `TombstoneErrorHandler` via `Mixins.registerErrorHandlerClass()` to suppress the mixin injection error gracefully instead of crashing

## 1.0.14
- Fixed crash: Tombstone 9.5.6 mixin `ItemInputMixin` crashes on `ItemCombinerScreen` due to changed method signatures in MC 1.21.3
- Added `TombstoneErrorHandler` implementing `IMixinErrorHandler` to suppress the incompatibility error

## 0.0.1-beta.6
- Fixed crash on startup: packet type identifier format corrected (`Identifier.parse` instead of `createType`)
- Mouse click handling moved from mixin to NeoForge `ScreenEvent.MouseButtonPressed.Pre`
- Removed Mixin annotation processor dependency (moddev handles it)

## 0.0.1-beta.5
- Fixed crash: `mouseClicked` method not found in `CraftingScreen` (method is inherited, not defined in target)
- Replaced mixin-based click handling with NeoForge screen events
- Separated client event handling into `PolymorphClientHandler`

## 0.0.1-beta.4
- Fixed crash: `mouseClicked` signature changed to `MouseButtonEvent` in Minecraft 26.1.2
- Updated `@Inject` target for new input API

## 0.0.1-beta.3
- Fixed crash: `leftPos`/`topPos` fields moved to `AccessorAbstractContainerScreen` (parent class)
- Fixed refmap cross-reference issue between mixin classes

## 0.0.1-beta.2
- Fixed crash on startup: mixin cross-reference replaced with shared `RecipeFinder` utility class

## 0.0.1-beta.1
- Initial beta release
- Recipe conflict resolution for crafting table: choose between multiple crafting outputs
- Configurable settings (enable/disable, max recipes displayed)
- Developer API for custom integrations
- English translations
