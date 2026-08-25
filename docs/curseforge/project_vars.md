# CurseForge — Variables del proyecto (v2.0.0+ Multi-Mod)

> Las siguientes variables son leídas por scripts de upload para los 3 mods independientes.

# Script variables (key=value format for curseforge-upload.ps1 / curseforge-upload-submodule.ps1)
# This repo is split multi-mod (admin/fixes/qol/hud) — use curseforge-upload-submodule.ps1 with -SubModule.
# project_id is suffixed per submodule; api_token/game_versions/release_type are shared (no suffix needed).
project_id_fixes = 1648135
project_id_admin = 1601825
project_id_qol = 1648134
project_id_hud = 1662269
# Legacy unsuffixed project_id kept for back-compat with curseforge-upload.ps1 (points at Fixes).
project_id = 1648135
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
game_versions = 9638, 9639, 16498, 10150
release_type = release

---

## 4 Proyectos CurseForge Separados

| Mod | CurseForge Project ID | Mod ID | Display Name | Slug Sugerido |
|-----|----------------------|--------|--------------|---------------|
| **Fixes** | `1648135` | `utility_core_fixes` | Utility Core Fixes | `utility-core-fixes` |
| **QoL** | `1648134` | `utility_core_qol` | Utility Core QoL | `utility-core-qol` |
| **Admin** | `1601825` | `utility_core_admin` | Utility Core Admin | `utility-core-admin` |
| **HUD** | `1662269` | `utility_core_hud` | Utility Core HUD | `utility-core-hud` |

> **Nota**: El mismo token funciona para los 3 proyectos (token heredado del proyecto original).

---

## Tokens (Restaurados del historial original)

| Mod | Upload Token | Core API Key |
|-----|--------------|--------------|
| Fixes | `ee776b0a-ee95-4850-b554-06be02a8657f` | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |
| Admin | `ee776b0a-ee95-4850-b554-06be02a8657f` | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |
| QoL | `ee776b0a-ee95-4850-b554-06be02a8657f` | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |
| HUD | `ee776b0a-ee95-4850-b554-06be02a8657f` | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |

> **Nota**: Token heredado del proyecto original (1601825). Verificar permisos en los nuevos proyectos (1648135, 1648134).

---

## Variables Comunes

| Variable | Valor |
|----------|-------|
| `minecraft_version` | `26.2` |
| `framework` | `neoforge` |
| `neo_version` | `26.2.0.57` |
| `java_version` | `21` |
| `admin_version` / `fixes_version` / `qol_version` / `hud_version` | Independientes por mod, en `gradle.properties` (raíz) — no hay `mod_version` compartido |
| `environment` | `Client`, `Server` (Fixes/Admin) / `Client` primary + Server sync (QoL) / `Client` only (HUD) |

---

## Estructura de Archivos por Mod

```
fixes/build/libs/utility_core_fixes-26.2-neoforge-26.2.0.57-<fixes_version>.jar
admin/build/libs/utility_core_admin-26.2-neoforge-26.2.0.57-<admin_version>.jar
qol/build/libs/utility_core_qol-26.2-neoforge-26.2.0.57-<qol_version>.jar
hud/build/libs/utility_core_hud-26.2-neoforge-26.2.0.57-<hud_version>.jar
```

### Nomenclatura JAR
`utility_core_<mod>-<mc>-neoforge-<neo_version>-<mod_version>.jar` (ej: `utility_core_fixes-26.2-neoforge-26.2.0.57-2.2.1.jar`)

---

## Configuración por Mod

### Utility Core Fixes
- **Mod ID**: `utility_core_fixes`
- **Display Name**: Utility Core Fixes
- **Category**: Server Utility
- **Side**: Both
- **Dependencies**: None (optional: Corail Tombstone, OutpostZero)
- **Config**: `utility_core/utility_core_fixes-common.toml`
- **Description**: `docs/curseforge/project_description_fixes.md`

### Utility Core Admin
- **Mod ID**: `utility_core_admin`
- **Display Name**: Utility Core Admin
- **Category**: Server Admin
- **Side**: Server (primary) + Client (config)
- **Dependencies**: None
- **Config**: `utility_core/utility_core_admin-common.toml`
- **Description**: `docs/curseforge/project_description_admin.md`

### Utility Core QoL
- **Mod ID**: `utility_core_qol`
- **Display Name**: Utility Core QoL
- **Category**: Client QoL
- **Side**: Client (primary) + Server (sync)
- **Dependencies**: None
- **Config**: `utility_core/utility_core_qol-common.toml` (+ `utility_core/utility_core_qol-client.toml`)
- **Description**: `docs/curseforge/project_description_qol.md`

### Utility Core HUD
- **Mod ID**: `utility_core_hud`
- **Display Name**: Utility Core HUD
- **Category**: Client QoL
- **Side**: Client only
- **Dependencies**: Vellumli (required)
- **Config**: `config/utility_core/hud/` (posición/orientación/escala guardadas por el editor interactivo, no TOML estático)
- **Description**: `docs/curseforge/project_description_hud.md`

---

## Flujo de Upload (Por Mod)

1. `./gradlew :fixes:clean :fixes:build` (o `:admin:build` / `:qol:build`)
2. Bump solo la propiedad de versión del mod afectado (`fixes_version`, etc.) en `gradle.properties`
3. Crear tag: `git tag 26.2-neoforge-fixes-<version>` + `git push origin <tag>`
3. Subir JAR a CurseForge correspondiente
4. Pegar HTML de `docs/curseforge/project_description_<mod>.md` en descripción del proyecto CurseForge
5. Verificar con GET

---

## Estructura Changelog (HTML)

```html
<h2>v2.0.0-beta.1 - Initial Split Release</h2>

<h3>Features</h3>
<ul>
<li><strong>Mod split</strong>: Monolithic Utility Core split into 3 independent mods: Fixes, Admin, QoL.</li>
<li><strong>Standalone mods</strong>: Each mod has its own modId, config, mixins, and dependencies.</</ul>

<h3>Fixes</h3>
<ul>
<li>Tombstone GUI Scale / Item Init / Error Handler fixes</li>
<li>Negative Damage Fix (clamps to 0)</li>
<li>OutpostZero Infection Damage Cap (10k)</li>
</ul>

<h3>Admin</h3>
<ul>
<li>Spawn Schematic (SURFACE/FIXED height, external file support, protection)</li>
<li>ChunkGen (spiral, duty cycle, per-dimension, persistence)</li>
<li>Server Rules (from JSON, idempotent)</li>
<li>Data Pack Folder (auto-load from <game-dir>/datapacks/)</li>
</ul>

<h3>QoL</h3>
<ul>
<li>Recipe Selector (crafting table + inventory 2x2, server sync)</li>
<li>Biome/Dimension Titles (vanilla HUD, Traveler's Titles port)</li>
<li>Title Vertical Offset (-200 to +200)</li>
</ul>

<hr>
<p><strong>JAR</strong>: <code>utility_core_fixes-2.0.0-beta.1.jar</code></p>
```

---

## Versionado

Independiente por mod — cada uno en su propia línea SemVer, sin sincronizarse entre sí.

| Contexto | Formato |
|----------|---------|
| NeoForge `neoforge.mods.toml` | `<mod>_version` de `gradle.properties` (SemVer estricto) |
| CurseForge upload | Igual que `neoforge.mods.toml` |
| Git tag | `26.2-neoforge-<mod>-<version>` (ej. `26.2-neoforge-fixes-2.2.1`) |
| Changelog | `CHANGELOG.md` compartido, encabezado con `[<Mod>] <version>` |

---

## Build

```bash
# Build todos
./gradlew :fixes:jar :admin:jar :qol:jar :hud:jar

# Build individual
./gradlew :fixes:jar
./gradlew :admin:jar
./gradlew :qol:jar
./gradlew :hud:jar
```

---

## Rutas de Documentación

| Archivo | Uso |
|---------|-----|
| `docs/ICON_PROMPTS.md` | 3 prompts para generación de iconos |
| `docs/curseforge/project_description_fixes.md` | Descripción Fixes (HTML) |
| `docs/curseforge/project_description_admin.md` | Descripción Admin (HTML) |
| `docs/curseforge/project_description_qol.md` | Descripción QoL (HTML) |
| `docs/curseforge/project_description_hud.md` | Descripción HUD (HTML) |
| `docs/curseforge/project_vars.md` | Este archivo |