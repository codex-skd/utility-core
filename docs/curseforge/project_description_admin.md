<h1 align="center">&#9881;&#65039; Utility Core Admin</h1>

<p align="center"><strong>Server administration utilities for Minecraft servers.</strong></p>

<p align="center">
  <img src="https://img.shields.io/curseforge/dt/PROJECT_ID?style=plastic&logo=curseforge&label=downloads" alt="CurseForge Downloads">
  <img src="https://img.shields.io/curseforge/v/PROJECT_ID?style=plastic&logo=curseforge&label=latest" alt="CurseForge Version">
  <img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
</p>

<hr>

<h2>Overview</h2>

<p><strong>Utility Core Admin</strong> provides essential server administration utilities for Minecraft servers. All features are individually toggleable via <code>config/utility_core_admin-common.toml</code> (or the in-game mod menu).</p>

<h2>Features</h2>

<h3>Spawn Schematic</h3>
<ul>
  <li>Pastes a Sponge/WorldEdit <code>.schem</code> structure at world creation (X=0, Z=0)</li>
  <li>Configurable height placement: <strong>SURFACE</strong> (aligns to highest ground block + offset) or <strong>FIXED</strong> (absolute Y)</li>
  <li>Includes basic lobby schematic embedded; supports external file at <code>utility_core/spawn_schem/schematic_spawn.schem</code></li>
  <li>Sets world spawn inside structure</li>
  <li>Permanent build protection (blocks, explosions) + optional mob spawn prevention</li>
  <li>Runtime toggle: <code>/utilitycore spawnprotection on|off|status</code></li>
</ul>

<h3>Automatic Chunk Pre-generation (ChunkGen)</h3>
<ul>
  <li>Generates chunks in spiral from (0,0) when server is empty</li>
  <li>Duty cycle: configurable load seconds / rest seconds (wall-clock)</li>
  <li>Pauses automatically when players join (configurable)</li>
  <li>Persists progress to disk (<code>utility_core/chunk_pregen/utility_core_chunk_gen.json</code>)</li>
  <li>Independent per dimension: Overworld, Nether, End</li>
  <li>Commands: <code>/utilitycore chunkgen status|start|pause|stop|reset</code></li>
</ul>

<h3>Server Rules Enforcement</h3>
<ul>
  <li>Applies game rules from <code>utility_core/server_rules.json</code> on server start</li>
  <li>Idempotent: only changes rules that differ from config</li>
  <li>Format: <code>{ "rules": { "ruleId": "value" } }</code></li>
  <li>Command: <code>/utilitycore rules apply|status</code></li>
</ul>

<h3>Data Pack Folder Loader</h3>
<ul>
  <li>Auto-loads all datapacks (<code>.zip</code> or folders) from <code><game-dir>/datapacks/</code> into every world</li>
  <li>Works on dedicated servers and single-player</li>
  <li>No per-world enabling needed</li>
</ul>

<hr>

<h2>Commands</h2>

<pre><code>/utilitycore chunkgen status|start|pause|stop|reset
/utilitycore rules apply|status
/utilitycore spawnprotection on|off|status
/utilitycore help
</code></pre>

<hr>

<h2>Configuration</h2>

<p>Via <code>utility_core_admin-common.toml</code>:</p>

<pre><code># Spawn Schematic
enableSpawnSchematic = false
spawnSchematic.heightMode = "SURFACE"
spawnSchematic.surfaceOffset = 70
spawnSchematic.fixedY = 64
spawnSchematic.preventMobSpawns = true
spawnSchematic.protectionEnabled = true

# ChunkGen
chunkGen.enabled = false
chunkGen.chunksPerTick = 100
chunkGen.maxRadius = 0
chunkGen.runWithPlayers = false
chunkGen.dimensionOverworld = true
chunkGen.dimensionNether = false
chunkGen.dimensionEnd = false
chunkGen.loadSeconds = 600
chunkGen.restSeconds = 300

# Data Pack Folder
dataPackFolder.enabled = false
dataPackFolder.path = "datapacks"
</code></pre>

<hr>

<h2>Spawn Schematic File</h2>

<ul>
  <li><strong>External</strong>: Place at <code>utility_core/spawn_schem/schematic_spawn.schem</code> (takes priority)</li>
  <li><strong>Embedded</strong>: Basic lobby included in mod resources</li>
  <li><strong>Format</strong>: Sponge Schematic v2/v3 or legacy WorldEdit (Materials=Alpha)</li>
</ul>

<hr>

<h2>Requirements</h2>

<table>
  <tr><th>Requirement</th><th>Version</th></tr>
  <tr><td>Minecraft</td><td>26.2 (1.21.1)</td></tr>
  <tr><td>NeoForge</td><td>26.2.0.37-beta+</td></tr>
</table>

<p><strong>Important</strong>: Set <code>pause-when-empty-seconds=-1</code> in <code>server.properties</code> so the server doesn't pause while empty.</p>

<hr>

<p>MIT License. Developed by <strong>Stalking Dragons</strong>.</p>