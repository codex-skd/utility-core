# Utility Core Fixes - CurseForge Documentation

## Project Info
- **Project Name**: Utility Core Fixes
- **Project Slug**: utility-core-fixes
- **Mod ID**: `utility_core_fixes`
- **Display Name**: Utility Core Fixes
- **Version**: 2.0.0-beta.1 (CurseForge) / 2.0.0 (NeoForge internal)
- **NeoForge Version**: 26.2.0.37-beta
- **Minecraft Version**: 26.2
- **License**: MIT
- **Category**: Server Utility / Fixes

---

## Description (Long)
Utility Core Fixes - Compatibility fixes and crash prevention for modded Minecraft servers.

### Features

**Corail Tombstone Fixes**
- **GUI Scale Fix**: Prevents Corail Tombstone from forcing GUI scale to 4 when opening its menus. Restores your configured scale on close.
- **Item Init Fix**: Properly initializes NBT data for Tombstone items (lollipop color, magic_scroll random effect) when obtained via `/give` or creative menu.
- **Error Handler**: Suppresses mixin errors from Tombstone's `ItemInputMixin` to prevent crashes on server startup.

**Negative Damage Fix**
- Prevents server crash from `IllegalArgumentException: "Damage cannot be negative"` caused by any mod producing negative damage values.

**OutpostZero Infection Damage Cap**
- Caps `outpostzero:infection` damage type to 10,000 to prevent armor destruction before death events fire.

---

## Configuration
All features configurable via `utility_core_fixes-common.toml`:

```toml
# Tombstone fixes
enableTombstoneGuiScaleFix = true
enableTombstoneItemInitFix = true
enableTombstoneErrorHandler = true

# General fixes
enableNegativeDamageFix = true
enableOutpostZeroDamageCap = true
```

---

## Compatibility
- **Requires**: NeoForge 26.2.0.37-beta+
- **Optional**: Corail Tombstone, OutpostZero (fixes only activate if mods present)
- **Side**: Both (client + server)

---

## Changelog (2.0.0-beta.1)
- Initial split from monolithic Utility Core
- All fixes preserved from v1.11.x
- Standalone mod with own modId (`utility_core_fixes`)