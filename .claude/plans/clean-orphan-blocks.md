# Plan: `/utilitycore cleanorphans` — purge orphaned block-registry entries left by removed mods

## Goal

Add an op-only server command in `utility_core_admin` that strips blockstates whose
namespace belongs to a configured "dead mods" blacklist (default: `naraka` and
`warlockery`, see below) from the whole world, replacing every match with
`minecraft:air`, without freezing the server.

## Background (already established, do not re-derive)

- Two mods (`naraka`, `warlockery`) were uninstalled from this server, but their
  block registry names (e.g. `naraka:amethyst_ore`, `warlockery:silver_ore`) are
  still present verbatim in the `block_states.palette` NBT list of saved chunk
  sections. NeoForge apparently keeps a runtime placeholder for the unresolved
  block instead of converting it to air, which is why it renders as the classic
  magenta/black missing-texture block in-game while still being solid/collidable.
- These were global worldgen features (ore veins, trees), so they are spread
  across the *entire* explored world, not localized. A 40-region sample (out of
  2555 overworld region files) already showed ~467,000 combined palette
  occurrences. **A block-by-block scan, or force-loading every region as a live
  chunk, is not viable.** The fix must operate at the *palette* level: each chunk
  section only has a handful of distinct palette entries, so rewriting the
  matching `Name` values in the palette fixes every instance of that block in
  that section in one shot — O(chunks), not O(blocks).
- The server runs **FastAsyncWorldSave** (`fastasyncworldsave-26.2-2.6.jar`),
  which changes how region files are compressed on disk (confirmed empirically:
  chunk data on disk uses Anvil compression type `4`, a custom LZ4-block framing
  that is NOT one of vanilla's three standard types). **Because of this, do NOT
  hand-roll Anvil/NBT region parsing.** Read and write chunks through vanilla's
  own region I/O classes (`net.minecraft.world.level.chunk.storage.RegionFile` /
  `RegionFileStorage`, whichever is more directly usable — check what's actually
  on the compile classpath and how the running server's `ChunkMap`/`IOWorker`
  obtains its `RegionFileStorage` instance, e.g. via
  `ServerLevel#getChunkSource()` → `ChunkMap`) so that whatever
  compression/codec FastAsyncWorldSave has patched in is transparently honored on
  both read and write. This also means: do not force a specific output
  compression type: let the existing writer machinery decide, exactly as normal
  chunk saves already do.

## Files to touch

- `admin/src/main/java/com/skd/utilitycore/admin/config/AdminConfig.java`
  — add the new config section (see "Config" below).
- `admin/src/main/java/com/skd/utilitycore/admin/orphans/OrphanBlockCleaner.java`
  (new file) — the actual cleanup logic (loaded-chunk path + unloaded/region-file
  path), run off the main thread.
- `admin/src/main/java/com/skd/utilitycore/admin/orphans/OrphanBlockCommand.java`
  (new file) — registers `/utilitycore cleanorphans [--dry-run]` via
  `RegisterCommandsEvent`, op-only (permission level 2 is this mod's existing
  convention for admin-ish commands — double check against any other op-gated
  logic in this module, e.g. `AdminConfig`/`ServerRulesManager`, and match it).
- Wherever this mod's `@EventBusSubscriber`/mod-bus listeners are wired up for
  the admin submodule (mirror how `SpawnProtectionHandler` and
  `SpawnSchematicManager` get instantiated/registered — check the admin
  submodule's main mod class, e.g. `UtilityCoreAdmin` or equivalent, for the
  `FMLCommonSetupEvent`/`RegisterCommandsEvent` wiring pattern already used
  there) — register the new command handler the same way.

## Config (`AdminConfig.java`)

Follow the existing style in this file exactly (`ModConfigSpec.Builder`,
bilingual EN/ES `.comment(...)`, dotted key namespacing like
`spawnSchematic.xxx` / `chunkGen.xxx`). Add a new `orphanBlocks.` section:

- `ModConfigSpec.ConfigValue<List<? extends String>>
  ORPHAN_BLOCKS_BLACKLIST` — `.defineList("orphanBlocks.blacklist", () ->
  List.of(<the 15 default ids below>), obj -> obj instanceof String)`. Each
  entry is either a full block id (`"warlockery:silver_ore"`) or a bare
  namespace wildcard (`"naraka"` — meaning "every block in this namespace").
  Decide the exact wildcard syntax (e.g. plain namespace with no colon = whole
  namespace, namespace:path = exact block) and document it in the comment.
  Default list (seed with the confirmed orphans, one entry per full id is fine
  for the default — the wildcard form is just for the *documented* convenience
  of the config, not required for the defaults):
  ```
  naraka:amethyst_ore
  naraka:deepslate_amethyst_ore
  naraka:deepslate_nectarium_ore
  naraka:nectarium_ore
  naraka:diamond_golem_spawner
  warlockery:silver_ore
  warlockery:deepslate_silver_ore
  warlockery:delvealloy_ore
  warlockery:deepslate_delvealloy_ore
  warlockery:alder_leaves
  warlockery:alder_log
  warlockery:hawthorn_leaves
  warlockery:hawthorn_log
  warlockery:rowan_leaves
  warlockery:rowan_log
  ```

## Command behavior

`/utilitycore cleanorphans` and `/utilitycore cleanorphans dryrun` (op-only,
same permission level as other admin commands in this mod).

- Scope: `minecraft:overworld`, `minecraft:the_nether`, `minecraft:the_end`
  only — skip every other dimension (do not even enumerate their region
  folders).
- On invocation:
  1. Immediately reply to the command sender that the operation has started and
     will report back when done (this will take a while given the world size).
  2. Kick off the whole pass on a background thread (not the server tick
     thread). Make sure it doesn't race with normal autosave/chunk unload —
     either run each dimension's file pass through the same
     `RegionFileStorage`/IO path the server itself uses (so it shares whatever
     locking that already provides), or, at minimum, take the obvious
     precaution of skipping/deferring any chunk that's actively being
     saved/is loaded, per the two-path design below.
  3. **Loaded-chunk path**: for each of the 3 target dimensions, iterate
     currently loaded chunks (`ServerLevel#getChunkSource()`'s loaded chunk
     holders, or simplest: `ServerLevel#hasChunk`/`getChunk` only for chunks
     already resident — do not force-load). For each `LevelChunkSection`,
     find blockstates whose `Block`'s registry name matches the blacklist and
     replace with `Blocks.AIR.defaultBlockState()` via the normal
     `Level#setBlock` API (flag `3`), and remove any `BlockEntity` at that
     position if it's now orphaned.
  4. **Unloaded-chunk path**: for every chunk in every region file on disk for
     that dimension that is *not* currently loaded, read the chunk NBT via the
     vanilla region I/O path described above, and for every
     `sections[].block_states.palette` entry whose `Name` matches the
     blacklist, rewrite that palette entry to `{"Name":"minecraft:air"}`
     (dropping any `Properties` compound on that entry). Also drop any entry
     in `block_entities` whose `id` matches the blacklist. Write the modified
     chunk NBT back through the same region I/O path (so compression stays
     whatever FastAsyncWorldSave expects).
  5. Track and log/report: dimensions processed, region files touched, chunk
     sections modified, palette entries rewritten (broken down by block id),
     block entities removed.
- **Dry-run** (`dryrun` subcommand): do all of the above scanning/matching but
  skip every write — just accumulate and report the same counts. Must be safe
  to run at any time with zero side effects.
- Non-dry-run must print, before starting, a clear warning in the command
  feedback that this is an irreversible edit to world data and the operator
  should have a backup of the world folder.
- Log everything through this module's existing `LOGGER` pattern (see
  `SpawnSchematicManager`'s use of `LogUtils.getLogger()`), in en-US.

## Constraints (do not violate)

- Never upgrade dependencies, mod versions, or the NeoForge version.
- Never delete files unless explicitly requested — this task does not require
  deleting any files, only rewriting NBT content inside existing region files.
- Match this module's existing code style — see `LegacyBlockMap.java` and
  `SpawnSchematicManager.java` in
  `admin/src/main/java/com/skd/utilitycore/admin/schematic/` for the
  conventions already used for block/NBT/world manipulation in this codebase
  (logger usage, config access via `AdminConfig`, package layout).
- Code, logs, and command feedback strings: en-US.
- Do not touch CurseForge release notes, versioning/gradle.properties bumps, or
  git tags/commits — that is handled separately after this plan is executed.
- Do not attempt to hand-roll the LZ4-block Anvil compression framing — use
  vanilla's region I/O classes so FastAsyncWorldSave's compression is handled
  transparently, on both read and write.

## Definition of done

- `./gradlew.bat :admin:compileJava` (or whatever the correct Gradle module
  task is — check `settings.gradle` for the actual admin subproject path/name)
  succeeds with no new warnings introduced by this change.
- `/utilitycore cleanorphans dryrun` and `/utilitycore cleanorphans` are both
  registered and reachable, gated to server operators.
- The default blacklist in `AdminConfig` contains exactly the 15 ids listed
  above.
- The implementation never force-loads a chunk it doesn't already have
  resident, and never manually re-implements Anvil compression — it goes
  through vanilla region I/O classes for the unloaded-chunk path.
- Report any deviation from this plan, and flag explicitly if the actual
  `RegionFileStorage`/chunk I/O API available in this NeoForge version doesn't
  match what's assumed above (API names can drift between versions) — describe
  what was found and used instead.
