# Utility Core 2.0.0-beta.1 - CurseForge Projects Summary

## Overview
Monorepo split into 3 independent CurseForge projects. All require NeoForge 26.2.0.37-beta (MC 26.2).

---

## Projects

| Project | Mod ID | JAR Name | Category | Version (NeoForge) | Version (CurseForge) |
|---------|--------|----------|----------|-------------------|---------------------|
| **Fixes** | `utility_core_fixes` | `utility_core_fixes-2.0.0-beta.1.jar` | Server Utility | 2.0.0 | 2.0.0-beta.1 |
| **Admin** | `utility_core_admin` | `utility_core_admin-2.0.0-beta.1.jar` | Server Admin | 2.0.0 | 2.0.0-beta.1 |
| **QoL** | `utility_core_qol` | `utility_core_qol-2.0.0-beta.1.jar` | Client QoL | 2.0.0 | 2.0.0-beta.1 |

---

## Common Settings
- **NeoForge**: 26.2.0.37-beta
- **Minecraft**: 26.2
- **License**: MIT
- **Group**: `com.skd.utilitycore`
- **Java**: 21

---

## CurseForge Setup (Per Project)

### 1. Utility Core Fixes
- **Slug**: `utility-core-fixes`
- **Mod ID**: `utility_core_fixes`
- **Dependencies**: None (optional: Corail Tombstone, OutpostZero)
- **Category**: Server Utility
- **Side**: Both

### 2. Utility Core Admin
- **Slug**: `utility-core-admin`
- **Mod ID**: `utility_core_admin`
- **Dependencies**: None
- **Category**: Server Admin
- **Side**: Server (primary) + Client (config)

### 3. Utility Core QoL
- **Slug**: `utility-core-qol`
- **Mod ID**: `utility_core_qol`
- **Dependencies**: None
- **Category**: Client QoL
- **Side**: Client (primary) + Server (sync)

---

## Upload Checklist (Per Project)
- [ ] Create CurseForge project with slug above
- [ ] Set Mod ID exactly as above
- [ ] Upload JAR: `utility_core_<name>-2.0.0-beta.1.jar`
- [ ] Set version string: `2.0.0-beta.1`
- [ ] Set NeoForge version: 26.2.0.37-beta
- [ ] Set Minecraft version: 26.2
- [ ] Copy description from `docs/CURSEFORGE_<NAME>.md`
- [ ] Set category as above
- [ ] Add logo: `assets/utility_core_<name>/icon.png`

---

## Versioning Strategy
| Context | Version |
|---------|---------|
| NeoForge `neoforge.mods.toml` | `2.0.0` (strict SemVer) |
| CurseForge upload form | `2.0.0-beta.1` |
| Git tag | `v2.0.0-beta.1-fixes`, `v2.0.0-beta.1-admin`, `v2.0.0-beta.1-qol` |
| Changelog | `2.0.0-beta.1` |

---

## JAR Locations (After Build)
```
fixes/build/libs/utility_core_fixes-2.0.0-beta.1.jar
admin/build/libs/utility_core_admin-2.0.0-beta.1.jar
qol/build/libs/utility_core_qol-2.0.0-beta.1.jar
```

---

## Dependencies
**None required** for any of the 3 mods. All optional mod compat (Tombstone, OutpostZero) is detected at runtime.