# Flujo de trabajo — Utility Core (NeoForge)

> **Versión del workflow**: 1.16.0 (codex-docs)
> Este archivo pertenece al proyecto **Utility Core**. Cambios aquí solo afectan a este proyecto.
> **Trabaja directamente con este archivo**: es el workflow operativo del mod, autocontenido. No leas `codex-docs/WORKFLOW_AGENT.md` ni `WORKFLOW_GENERIC.md` de forma rutinaria.
> On-demand (solo si la tarea lo necesita): `codex-docs/reference/CURSEFORGE.md` (formato HTML al publicar), `codex-docs/reference/GRAPHIFY.md` (backend LLM de Graphify), `codex-docs/reference/REPO_SETUP.md` (setup único de repo).

## Específico del mod

| Dato | Valor |
|---|---|
| Mod ID (`gradle.properties`) | `utility_core` |
| Clase principal | `UtilityCore` |
| Display name (Title Case) | `Utility Core` |
| Versiones de Minecraft | `26.1.2 y 26.2` |
| Rama | `minecraft/26.2/neoforge-26.2.0.57/production` |


## Sub-módulos (versionado independiente)

### Admin

| Dato | Valor |
|---|---|
| Mod ID | `utility_core_admin` |
| Display name | `Utility Core Admin` |
| Versión actual | `2.4.0` (CurseForge file ID 8724564) |
| Estado | ✅ Release estable 2.4.0 |

### Fixes

| Dato | Valor |
|---|---|
| Mod ID | `utility_core_fixes` |
| Display name | `Utility Core Fixes` |
| Versión actual | `2.4.0` (CurseForge file ID 8690350) |
| Estado | ✅ Release estable 2.4.0 |

### QoL

| Dato | Valor |
|---|---|
| Mod ID | `utility_core_qol` |
| Display name | `Utility Core QoL` |
| Versión actual | `2.4.0` (CurseForge file ID 8724530) |
| Estado | ✅ Release estable 2.4.0 |

## Convenciones de nomenclatura

| Convención | Uso | Ejemplo |
|---|---|---|
| **snake_case** | `mod_id`, assets/, packages Java | `utility_core` |
| **PascalCase** | Clases Java principales | `UtilityCore` |
| **camelCase** | Variables, métodos, config keys | `utility_coreConfig` |
| **Title Case** | Display name (README, CHANGELOG, docs, CurseForge) | `Utility Core` |

## Organización y ramas

- Un repo GitLab por mod, una rama `minecraft/<mc>/neoforge-<neo>/production` por versión. Este clon local trabaja en la rama `production` de esta versión.
- Carpetas: `<mod_id>/<framework>/<mc-version>/` — este clon vive en `<mod_id>/neoforge/<mc-version>/`.
- `*/main` y CI/CD: setup único al crear el repo (`codex-docs/reference/REPO_SETUP.md`) — no releer ni modificar.

## Estructura del proyecto

`build.gradle` · `gradle.properties` (mod_id, mod_version, mod_group_id, mod_framework) · `settings.gradle` · `src/main/java/<package>/` · `src/main/resources/assets/<mod_id>/` · `META-INF/neoforge.mods.toml` · `libs/` (versionado) · `lib_ext/` y `temp/` (no versionados) · `docs/` (WORKFLOW + curseforge/) · `CHANGELOG.md` · `README.md` · `graphify-out/` (versionado).

## Versionado

- **Versionado independiente por mod**: `admin`, `fixes` y `qol` son 3 proyectos de CurseForge separados y versionan cada uno en su propia línea SemVer — no hay que subir los 3 a la vez ni mantenerlos sincronizados en número.
- Beta `0.0.0-beta.X` · Release `X.Y.Z` (SemVer: MAJOR breaking / MINOR feature / PATCH fix)
- `admin_version`, `fixes_version`, `qol_version` en `gradle.properties` (raíz, uno por subproyecto) · `mod_framework` compartido. JAR: `<mod_id>-<mc>-<framework>-<loader>-<version>.jar`

## Commits (Conventional Commits)

`<tipo>[<ámbito>]: <descripción>` · tipos `feat fix refactor docs chore style perf test` · el mensaje incluye la versión (`v<version>`) del mod afectado, con el ámbito (`fix(qol): ...`) indicando cuál.

## Tags

Cada subida a CurseForge crea tag por mod: beta `<mc>-neoforge-<mod>-beta.X` · release `<mc>-neoforge-<mod>-X.Y.Z` (ej. `26.2-neoforge-qol-2.3.0`). Un cambio que solo afecta a un mod solo tagea/publica ese mod, no los otros dos.

## Flujo por tarea

**0. Alcance** — si el mod tiene varias versiones de Minecraft, preguntar con la herramienta `question`: **"Todas"** o una versión. No asumir. Además, como `admin`/`fixes`/`qol` versionan por separado, identificar a cuál(es) afecta el cambio antes de tocar versión — no bumpear los 3 si solo cambió uno.

**1. Desarrollo**

```bash
git checkout minecraft/26.2/neoforge-26.2.0.57/production
./gradlew.bat build
git add -A
git commit -m "feat: <descripción>

v<version>"
git push
```

**2. CurseForge** — solo si el usuario confirma:
- Bump `<mod>_version` (`admin_version`/`fixes_version`/`qol_version`) en gradle.properties del mod(s) afectado(s) → `./gradlew.bat clean build`
- Release notes `docs/curseforge/versions/<version>_<mod>.md` (HTML, uno por mod) + actualizar `CHANGELOG.md`
- Commit `chore(<mod>): bump version to <version>` → tag `<mc>-neoforge-<mod>-<version>` → push
- Subir JAR: uno por mod, a su proyecto CurseForge correspondiente (ver `docs/curseforge/project_vars.md` para IDs/tokens)
- Formato HTML de descripciones/changelog: `codex-docs/reference/CURSEFORGE.md`

**3. Release estable** — bump `X.Y.Z` + tag.

**4. Graphify** — tras cada push a remoto. Versión 0.9.12: **`build` no existe**, usar `extract` (1ª vez) o `update . --force` (tras cambios):

```bash
GRAPHIFY="C:\Users\llagu\AppData\Local\Packages\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\LocalCache\local-packages\Python313\Scripts\graphify.exe"
"$GRAPHIFY" update . --force
git add graphify-out/ && git commit -m "chore: update knowledge graph" && git push
```

Leer siempre `GRAPH_REPORT.md`, nunca `graph.json`/`graph.html` (pesan >1MB). Sin copias fechadas de `graphify-out/`. Backend LLM: `codex-docs/reference/GRAPHIFY.md`.

## Buenas prácticas

- Un commit por cambio lógico · commit+push tras cada cambio funcional y de docs
- `clean build` antes del JAR final · versionar antes de CurseForge · CHANGELOG al día
- Graphify actualizado tras cada release · nomenclatura consistente · sin basura en repo (`nul`, `*_errors.txt`, `TEMPLATE_LICENSE.txt`) · `.gitignore` excluye `temp/` y `lib_ext/`
- README en inglés siempre actualizado · sin residuos de mod original (paquetes, clases, toml, lang, assets) · atribución de fork explícita (README, project_description, credits)

## Idioma

| Ámbito | Idioma |
|---|---|
| código, logs, commits | en-US |
| README.md | en-US |
| docs internas (docs/, CHANGELOG, este archivo) | es-ES |
| CurseForge | en-US |
