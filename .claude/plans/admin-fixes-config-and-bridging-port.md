# Plan: Fix admin/fixes config registration + port BridgingMod into qol

Repo root for all work: `G:\Proyectos\Mods_Minecraft\utility_core\neoforge\26.2`
Gradle multi-project: root `utility_core` + subprojects `admin`, `fixes`, `qol`.

Follow `docs/WORKFLOW_UTILITY_CORE_26-2.md`:
- Code, logs, commits: English. Internal docs (CHANGELOG.md, this plan): Spanish where the workflow requires it — CHANGELOG entries in Spanish, commit messages in English Conventional Commits format `<type>[<scope>]: <description>` + body with `v<version>`.
- Never upgrade NeoForge/Minecraft/library versions.
- Never delete files outside what this plan explicitly names.
- Build with `./gradlew.bat build` after each task; must be green before committing.
- Commit + push after each functional change (one logical commit per task, not per file).

---

## TASK A — Fix missing `@Mod` entrypoints in `admin` and `fixes`

### Problem
`admin` and `fixes` subprojects have no `@Mod`-annotated class anywhere (`grep -rn "@Mod(" admin/src fixes/src` → no matches). `AdminConfig.java` and `FixesConfig.java` define `ModConfigSpec`s that are referenced all over each module's code, but the spec is never registered via `modContainer.registerConfig(...)`. Result: neither module generates its own `.toml` config file and neither functions as a real standalone mod. Only `qol` has a working entrypoint (`qol/src/main/java/com/skd/utilitycore/qol/UtilityCoreQoL.java`).

### Reference pattern (qol, already correct — do not modify)
```java
@Mod(UtilityCoreQoL.MODID)
public class UtilityCoreQoL {
    public static final String MODID = "utility_core_qol";
    public UtilityCoreQoL(IEventBus modEventBus, ModContainer modContainer) {
        // ...network registration...
        modContainer.registerConfig(ModConfig.Type.COMMON, QoLConfig.SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
```

### Steps

1. **`admin/src/main/java/com/skd/utilitycore/admin/UtilityCoreAdmin.java`** (new file)
   - `@Mod("utility_core_admin")` (matches `admin/src/main/resources/META-INF/neoforge.mods.toml` modId).
   - Constructor `(IEventBus modEventBus, ModContainer modContainer)`.
   - `modContainer.registerConfig(ModConfig.Type.COMMON, AdminConfig.SPEC);`
   - `modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);`
   - Investigate these existing admin classes to see what lifecycle wiring they expect but currently never receive (no entrypoint has ever called them):
     - `admin/.../chunkgen/ChunkGenManager.java`
     - `admin/.../schematic/SpawnSchematicManager.java`
     - `admin/.../schematic/SpawnProtectionHandler.java`
     - `admin/.../compat/DataPackFolderLoader.java`
     - `admin/.../rules/ServerRulesManager.java`
   - Register whatever event listeners / `modEventBus.addListener(...)` / `NeoForge.EVENT_BUS.register(...)` calls these classes need, mirroring how the root `utility_core` mod (`src/main/java/com/skd/utilitycore/UtilityCore.java` if present, else its main class) wires up its own listeners as a style reference. Do not invent new behavior — just connect what's already written to a real lifecycle hook.

2. **`fixes/src/main/java/com/skd/utilitycore/fixes/UtilityCoreFixes.java`** (new file)
   - `@Mod("utility_core_fixes")`.
   - Constructor `(IEventBus modEventBus, ModContainer modContainer)`.
   - `modContainer.registerConfig(ModConfig.Type.COMMON, FixesConfig.SPEC);`
   - `modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);`
   - Fixes' mixins (`MixinDamageContainer`, `MixinItemInput`, `MixinTBScreen`, `TombstoneErrorHandler`) and `OutpostZeroCompat` likely work via pure mixin injection (loaded from `utility_core_fixes.mixins.json`, no explicit registration needed) — verify this is true, and specifically verify that `FixesConfig.SPEC.isLoaded()` guard calls will work correctly once the spec is actually registered (currently they'd always be checking an unregistered spec).

3. **Delete `src/main/java/com/skd/utilitycore/Config.java`** (root `utility_core` project)
   - Confirmed dead: `Config.SPEC` and no other static member of this class is referenced anywhere (verified via repo-wide grep before writing this plan). It's a stale pre-split duplicate of `AdminConfig`/`FixesConfig`/`QoLConfig` combined.
   - **Before deleting**, re-grep the whole repo for `com.skd.utilitycore.Config` and bare `Config.` usages restricted to that package to be sure nothing new references it. If anything does, stop and report instead of deleting.

4. **Delete `G:\Proyectos\Mods_Minecraft\utility_core\neoforge\26.2\META-INF\neoforge.mods.toml`**
   - This is a stray file directly under the `26.2` root (NOT under any `src/main/resources`), an orphaned duplicate of `qol`'s mods.toml. Not part of any Gradle sourceSet (verified against `build.gradle`). Safe to delete.

5. Build: `./gradlew.bat build` from `26.2` root. Fix any compile errors surfaced by adding the two new entrypoint classes or removing `Config.java`.

6. Do not touch `qol`'s entrypoint/config — it's already correct.

### Definition of done — Task A
- [ ] `admin` and `fixes` each have a working `@Mod` entrypoint class registering their own `ModConfigSpec`.
- [ ] `Config.java` (root) removed, nothing else references it.
- [ ] Stray root `META-INF/neoforge.mods.toml` removed.
- [ ] `./gradlew.bat build` succeeds.
- [ ] `gradle.properties` `mod_version` bumped as a PATCH (bugfix restoring intended behavior) from `2.0.0-beta.1`.
- [ ] `CHANGELOG.md` updated in Spanish, following the existing entry style/format at the top of the file.
- [ ] One commit, English Conventional Commits: `fix[admin/fixes]: <description>` with `v<version>` in the body, pushed to `minecraft/26.2/neoforge-26.2.0.37-beta/production`.

---

## TASK B — Port BridgingMod into `qol` (do after Task A is committed and green)

### Source (read-only reference, NOT part of this repo)
`G:\Proyectos\Mods_Minecraft\utility_core\neoforge\26.2\lib_ext\BridgingMod-latest\BridgingMod-latest\common\src\main\java\me\cg360\mod\bridging\`

### Scope (confirmed by user: port everything)
Port the full `common` module feature set:
- Core bridging assist: `building/Bridge.java`, `raytrace/*` (`BridgingPreContext`, `BridgingResult`, `BridgingStateTracker`, `PathTraversalHandler`, `Perspective`, `PlacementAlignment`), mixins `CrosshairRenderingMixin`, `OutlineRendererMixin`, `MinecraftClientMixin`.
- Secondary toggles: `skipTorchBridging`, `enableSlabAssist`, `enableNonSolidReplace`, `bridgingSnapStrength`, `bridgingAdjacency`, `perspectiveLock`.
- Debug highlight options (keep, per user): `showDebugHighlight`, `showNonBridgingDebugHighlight`, `showDebugTrace`.
- Compat integrations, ported as optional soft-dependency guards exactly like the original (`compat/impl/DankStorageCompat.java`, `compat/impl/items/DankStorageItemHandler.java`, `compat/impl/SableCompat.java`, `compat/impl/environment/SableEnvironmentHandler.java`, `compat/BridgingCrosshairTweaks.java`, `compat/GroupSelector.java`, `compat/SpecialHandlers.java`, `compat/type/*`). Do not add hard dependencies on DankStorage/Sable/DynamicCrosshair — preserve "only activate if present" checks.
- `BridgingKeyMappings.java` — port keybindings, avoid conflicts with qol's existing `client/PolymorphClientHandler.java`.
- `util/*` helpers as needed (`GameSupport`, `InfoStrings`, `Path`, `ReflectSupport`, `VectorSupport`, `render/CubeRenderTask`, `render/Render`, `flags/Flag`, `flags/Flags`).

**Skip**: `compat/impl/BankStorageCompat.java.old`, `compat/impl/items/BankStorageHandler.java.old` (already dead in source), anything fabric-specific.

### Config migration: YACL3 → native `ModConfigSpec`
Original `config/BridgingConfig.java` uses YACL3 (`dev.isxander.yacl3`), serializing to a standalone `config/bridgingmod.json` via `YACLPlatform.getConfigDir()` — fully separate from NeoForge's config system.

User decision: **drop YACL3 entirely.** Convert every `@SerialEntry` field in `BridgingConfig.java` into a `ModConfigSpec` entry, following `qol/src/main/java/com/skd/utilitycore/qol/config/QoLConfig.java`'s exact style (`ModConfigSpec.Builder`, bilingual `.comment("EN: ...", "ES: ...")`, `.define(...)` / `.defineInRange(...)` / `.defineEnum(...)`). Register in `qol`'s existing `UtilityCoreQoL.java` entrypoint:
```java
modContainer.registerConfig(ModConfig.Type.COMMON, QoLConfig.SPEC);
```
— either add the bridging entries directly into `QoLConfig.java`, or create a `BridgingConfig.java` under the new package with its own `ModConfigSpec` registered as an additional `ModConfig.Type.COMMON` alongside `QoLConfig.SPEC` (either is fine; pick whichever keeps the file from becoming unwieldy — if separate, still register both specs in `UtilityCoreQoL.java`). End result: settings land in `utility_core_qol-common.toml`.

No new dependency on YACL3. YACL3-only features (`@IncludeAnimatedImage`, `@IncludeExtraDescription`, `@Category` grouping, custom sliders) have no equivalent in plain NeoForge `ConfigurationScreen` — accepted tradeoff, just use clear bilingual comments and logical ordering, no custom config GUI.

### Package layout
- New package root: `com.skd.utilitycore.qol.bridging` (mirrors `com.skd.utilitycore.qol.*` convention already used for `common/api`, `common/attachment`, `common/network`, `common/polymorph`, `client/*`).
- Sub-packages mirroring original structure where useful: `.bridging.building`, `.bridging.raytrace`, `.bridging.compat`, `.bridging.util`.
- Reuse the `utility_core_qol` asset namespace — do **not** introduce a `bridgingmod` namespace. Only port textures actually referenced by ported Java code (check `common/src/main/resources/assets/bridgingmod/textures/gui/config/*` — several may be YACL3-config-screen-only and thus droppable once YACL3 is removed; only keep what outline/crosshair rendering code itself loads at runtime). Place kept textures under `qol/src/main/resources/assets/utility_core_qol/...`, update Java texture-path references accordingly.
- Lang: only port keys actually used by ported Java/keybindings, merge into `qol/src/main/resources/assets/utility_core_qol/lang/en_us.json` (and es if a Spanish lang file already exists for qol — check first).

### Mixin merge
Add `CrosshairRenderingMixin`, `MinecraftClientMixin`, `OutlineRendererMixin` into `qol/src/main/resources/META-INF` mixin config `utility_core_qol.mixins.json` (or the accessor one if appropriate), alongside existing `MixinCraftingMenu`, `MixinCraftingScreen`, `MixinHud`, `MixinInventoryScreen`. **Before merging**, check whether any ported mixin targets the same vanilla class/method as an existing qol mixin (e.g. both touching `Minecraft` or HUD rendering) — if so, resolve manually (do not let mixin injections silently clash); report the conflict if unsure how to resolve it rather than guessing.

### Build & release
1. `./gradlew.bat build` from `26.2` root, fix errors.
2. `mod_version` in `gradle.properties`: MINOR bump (new feature) from whatever Task A leaves it at.
3. `CHANGELOG.md`: new Spanish entry describing the bridging assist feature, mention it's a port from BridgingMod.
4. Attribution: check `lib_ext/BridgingMod-latest/BridgingMod-latest/LICENSE` for the correct license/wording, add a `credits` line in `qol/src/main/resources/META-INF/neoforge.mods.toml` (same pattern already used there for the Traveler's Titles attribution).
5. One commit, English Conventional Commits: `feat[qol]: <description>` with `v<version>` in the body, pushed to `minecraft/26.2/neoforge-26.2.0.37-beta/production`.

### Definition of done — Task B
- [ ] Bridging assist feature fully functional in `qol`, config-driven via native `ModConfigSpec` (no YACL3 dependency added).
- [ ] All requested sub-features present: core bridging, secondary toggles, debug highlights, soft-dependency compats (Dank Storage, Sable, Dynamic Crosshair/GroupSelector), keybindings.
- [ ] Mixins merged into `utility_core_qol.mixins.json` without silently clobbering existing qol mixins.
- [ ] Assets under `utility_core_qol` namespace only, no `bridgingmod` namespace introduced.
- [ ] `./gradlew.bat build` succeeds.
- [ ] `mod_version` MINOR-bumped, CHANGELOG.md updated (Spanish), credits added to qol's `neoforge.mods.toml`.
- [ ] Commit pushed.

---

## Global constraints (both tasks)
- No NeoForge/Minecraft/dependency version bumps.
- No file deletions beyond the two named in Task A step 3–4.
- No disabling of checks/mixins/tests to force a green build — if build fails and can't be resolved with reasonable effort, stop and report the exact error instead of committing.
- Follow existing bilingual (EN/ES) comment style for all new config entries.
