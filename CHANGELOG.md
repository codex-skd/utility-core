# Utility Core Changelog

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
