# CurseForge — Variables del proyecto (v2.0.0+ Multi-Mod)

> Las siguientes variables son leídas por scripts de upload para los 3 mods independientes.

---

## 3 Proyectos CurseForge Separados

| Mod | CurseForge Project ID | Mod ID | Display Name | Slug Sugerido |
|-----|----------------------|--------|--------------|---------------|
| **Fixes** | `1648135` | `utility_core_fixes` | Utility Core Fixes | `utility-core-fixes` |
| **QoL** | `1648134` | `utility_core_qol` | Utility Core QoL | `utility-core-qol` |
| **Admin** | `1601825` | `utility_core_admin` | Utility Core Admin | `utility-core-admin` |

> **Nota**: El mismo token funciona para los 3 proyectos (token heredado del proyecto original).

---

## Tokens (Restaurados del historial original)

| Mod | Upload Token | Core API Key |
|-----|--------------|--------------|
| Fixes | `ee776b0a-ee95-4850-b554-06be02a8657f` | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |
| Admin | `ee776b0a-ee95-4850-b554-06be02a8657f` | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |
| QoL | `ee776b0a-ee95-4850-b554-06be02a8657f` | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |

> **Nota**: Token heredado del proyecto original (1601825). Verificar permisos en los nuevos proyectos (1648135, 1648134).

---

## Variables Comunes

| Variable | Valor |
|----------|-------|
| `minecraft_version` | `26.2` |
| `framework` | `neoforge` |
| `neo_version` | `26.2.0.37-beta` |
| `java_version` | `21` |
| `mod_version` | `2.0.0-beta.1` (CurseForge) / `2.0.0` (NeoForge interno) |
| `environment` | `Client`, `Server` (Fixes/Admin) / `Client` primary + Server sync (QoL) |

---

## Estructura de Archivos por Mod

```
fixes/build/libs/utility_core_fixes-2.0.0-beta.1.jar
admin/build/libs/utility_core_admin-2.0.0-beta.1.jar
qol/build/libs/utility_core_qol-2.0.0-beta.1.jar
```

### Nomenclatura JAR
`utility_core_<mod>-<mod_version>.jar` (ej: `utility_core_fixes-2.0.0-beta.1.jar`)

---

## Configuración por Mod

### Utility Core Fixes
- **Mod ID**: `utility_core_fixes`
- **Display Name**: Utility Core Fixes
- **Category**: Server Utility
- **Side**: Both
- **Dependencies**: None (optional: Corail Tombstone, OutpostZero)
- **Config**: `utility_core_fixes-common.toml`
- **Description**: `docs/curseforge/project_description_fixes.md`

### Utility Core Admin
- **Mod ID**: `utility_core_admin`
- **Display Name**: Utility Core Admin
- **Category**: Server Admin
- **Side**: Server (primary) + Client (config)
- **Dependencies**: None
- **Config**: `utility_core_admin-common.toml`
- **Description**: `docs/curseforge/project_description_admin.md`

### Utility Core QoL
- **Mod ID**: `utility_core_qol`
- **Display Name**: Utility Core QoL
- **Category**: Client QoL
- **Side**: Client (primary) + Server (sync)
- **Dependencies**: None
- **Config**: `utility_core_qol-common.toml`
- **Description**: `docs/curseforge/project_description_qol.md`

---

## Flujo de Upload (Por Mod)

1. `./gradlew :fixes:clean :fixes:jar` (o `:admin:jar` / `:qol:jar`)
2. Copiar JAR a `docs/curseforge/build/` (opcional)
3. Crear tag: `git tag -a v2.0.0-beta.1-fixes -m "v2.0.0-beta.1: Fixes release"` + `git push origin <tag>`
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

| Contexto | Versión |
|----------|---------|
| NeoForge `neoforge.mods.toml` | `2.0.0` (strict SemVer) |
| CurseForge upload | `2.0.0-beta.1` |
| Git tag | `v2.0.0-beta.1-fixes`, `v2.0.0-beta.1-admin`, `v2.0.0-beta.1-qol` |
| Changelog | `2.0.0-beta.1` |

---

## Build

```bash
# Build todos
./gradlew :fixes:jar :admin:jar :qol:jar

# Build individual
./gradlew :fixes:jar
./gradlew :admin:jar
./gradlew :qol:jar
```

---

## Rutas de Documentación

| Archivo | Uso |
|---------|-----|
| `docs/ICON_PROMPTS.md` | 3 prompts para generación de iconos |
| `docs/curseforge/project_description_fixes.md` | Descripción Fixes (HTML) |
| `docs/curseforge/project_description_admin.md` | Descripción Admin (HTML) |
| `docs/curseforge/project_description_qol.md` | Descripción QoL (HTML) |
| `docs/curseforge/project_vars.md` | Este archivo |