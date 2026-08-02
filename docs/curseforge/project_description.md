<h1 align="center">&#9881;&#65039; Utility Core</h1>

<p align="center"><strong>A multi-purpose library mod for NeoForge modpacks. Recipe conflict resolution, damage safety, automatic chunk pregeneration, spawn schematic, global data packs, and mod compatibility fixes.</strong></p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<p>Utility Core is a Swiss Army knife for modded Minecraft servers. It started as a simple recipe conflict resolver and evolved into a collection of essential utilities that every modpack needs. All features are <strong>toggleable individually</strong> from <code>config/utility_core-common.toml</code> (or the in-game mod menu).</p>

<p>What it covers:</p>
<ul>
<li><strong>Crafting</strong> — resolve recipe conflicts with an in-grid selector</li>
<li><strong>Server safety</strong> — clamp negative damage, cap OutpostZero infection damage, pre-generate chunks</li>
<li><strong>World creation</strong> — paste a spawn schematic and protect the area, or auto-load global datapacks</li>
<li><strong>Client</strong> — biome/dimension titles, Tombstone GUI fixes</li>
<li><strong>Developer API</strong> — <code>PolymorphApi</code> for recipe integration</li>
</ul>

<br>

<h2>&#127919; Features</h2>

<h3>&#128279; Recipe Conflict Resolution</h3>
<p>When multiple crafting recipes match the same inputs, a selection widget appears in the crafting table and inventory 2x2 grid. Click any alternative to instantly change the output.</p>
<ul>
<li>Detects matching recipes server-side via attachments</li>
<li>Renders a selector grid client-side with real-time updates</li>
<li>Configurable max recipes displayed</li>
</ul>

<h3>&#128737;&#65039; Damage Safety</h3>
<p>Negative damage values from mod interactions (e.g., Apothic Attributes + Tombstone) are clamped to zero, preventing <code>IllegalArgumentException: Damage cannot be negative</code> server crashes.</p>

<h3>&#128220; Biome/Dimension Titles</h3>
<p>Shows a title on screen when entering a new biome or dimension. Logic ported from <a href="https://www.curseforge.com/minecraft/mc-mods/travelers-titles">Traveler's Titles</a> by YUNGNICKYOUNG (LGPLv3), rebuilt on vanilla's own title HUD.</p>

<h3>&#127918; Spawn Schematic (Opt-in)</h3>
<p>Pastes a WorldEdit/FAWE schematic at world creation, permanently protects the pasted area, and sets the world spawn point inside the structure. Place the file at <code>schematics/schematic_spawn.schem</code> in your server's game directory.</p>
<blockquote><strong>Only works on a brand-new world save (no existing region files).</strong> To apply it to an existing world, you must delete the world save folder first, then start the server with <code>enableSpawnSchematic=true</code>. This is intentional and not a bug — it prevents the schematic from overriding already-generated terrain.</blockquote>
<ul>
<li>Parses Sponge Schematic v2/v3 and the legacy WorldEdit/MCEdit <code>.schematic</code> format (auto-detected)</li>
<li>Pastes centered on the vanilla world spawn with fully configurable height placement: <code>SURFACE</code> aligns the bottom to the highest ground block under the footprint plus <code>surfaceOffset</code> (default 70), and <code>FIXED</code> mode uses an absolute Y coordinate</li>
<li>Bundled lobby schematic extracted automatically on first start if the file is missing — replace <code>schematic_spawn.schem</code> with your own file (same filename) before creating a new world to use a custom one</li>
<li>Sets world spawn inside the structure with safe-position detection</li>
<li>Permanent area protection: prevents block breaking, block placing, and explosions inside the pasted area</li>
<li>Per-world marker ensures one-time application even if the mod is later removed and reinstalled</li>
</ul>

<h3>&#127758; Automatic Chunk Pregeneration (ChunkGen)</h3>
<p>Pre-generates chunks in a spiral pattern when the server is empty, eliminating lag from world generation when players explore. Multi-dimension support (Overworld, Nether, End).</p>
<ul>
<li><strong>Auto-pause</strong>: Stops when players join, resumes when they leave</li>
<li><strong>Multi-dimension</strong>: Generate Overworld, Nether, and End simultaneously</li>
<li><strong>Configurable speed</strong>: 1 to 300 chunks per tick</li>
<li><strong>Progress persistence</strong>: Survives server restarts and crashes</li>
<li><strong>Keep alive</strong>: Prevents vanilla server idle pause during generation</li>
<li><strong>Commands</strong>: <code>/utilitycore chunkgen status</code>, <code>start</code>, <code>pause</code>, <code>stop</code>, <code>reset</code></li>
</ul>

<h3>&#128230; Data Pack Folder (Opt-in)</h3>
<p>Loads every datapack (<code>.zip</code> or folder) placed in <code>&lt;game-dir&gt;/datapacks</code> into every world automatically, mirroring the Global Packs mod. Works on dedicated servers and in single-player; no per-world toggle needed (packs are always enabled).</p>
<ul>
<li><code>dataPackFolder.enabled</code> — master toggle (default false)</li>
<li><code>dataPackFolder.path</code> — folder scanned, relative to the game directory (default <code>datapacks</code>)</li>
<li>Invalid datapacks (missing/broken <code>pack.mcmeta</code>) are skipped</li>
</ul>

<h3>&#9762;&#65039; Corail Tombstone Compatibility</h3>
<ul>
<li>Suppresses incompatible mixin errors from Tombstone 9.x</li>
<li>Restores GUI scale after opening Tombstone screens</li>
<li>Fixes NBT initialization for Tombstone items obtained via <code>/give</code></li>
</ul>

<h3>&#128299; Other Fixes</h3>
<ul>
<li><strong>OutpostZero Damage Cap</strong>: Limits infection damage to 10000 to prevent instant death</li>
</ul>

<h3>&#9881;&#65039; Fully Configurable</h3>
<p>Every feature can be enabled/disabled individually via <code>config/utility_core-common.toml</code>:</p>

<table>
<tr><th>Config</th><th>Default</th><th>Mod</th><th>Why</th></tr>
<tr><td><code>enableCraftingRecipeSelector</code></td><td>true</td><td>Any mod with recipe conflicts</td><td>Shows a selector UI when multiple crafting recipes match the same ingredients. Incompatible with Fast Workbench (fastbench).</td></tr>
<tr><td><code>maxRecipesDisplayed</code></td><td>16</td><td>Any mod with recipe conflicts</td><td>Maximum number of alternative recipes shown in the selector (1-64).</td></tr>
<tr><td><code>logDetectedConflicts</code></td><td>false</td><td>Any mod with recipe conflicts</td><td>Logs recipe conflicts to the console for debugging.</td></tr>
<tr><td><code>enableNegativeDamageFix</code></td><td>true</td><td>Apothic Attributes + Tombstone</td><td>Apothic critical strikes + Tombstone Decrepitude can produce negative damage values, crashing the server with <code>IllegalArgumentException: Damage cannot be negative</code>. This clamps damage to 0.</td></tr>
<tr><td><code>enableBiomeDimensionTitles</code></td><td>true</td><td>Vanilla / All</td><td>Shows a title on screen when entering a new biome or dimension. Client-side only.</td></tr>
<tr><td><code>titleVerticalOffset</code></td><td>75</td><td>Vanilla / All</td><td>Shifts the vanilla title/subtitle vertically from the screen center (-200 to 200). Positive = up.</td></tr>
<tr><td><code>enableTombstoneGuiScaleFix</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone forces GUI scale to 4 when opening its screens. This restores the original scale when closing.</td></tr>
<tr><td><code>enableTombstoneItemInitFix</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone items (lollipop, magic_scroll) obtained via <code>/give</code> lack proper NBT data. This initializes them correctly.</td></tr>
<tr><td><code>enableTombstoneErrorHandler</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone 9.x has a mixin (<code>ItemInputMixin</code>) that fails to apply in certain NeoForge versions. Instead of crashing, this suppresses the error gracefully.</td></tr>
<tr><td><code>enableOutpostZeroDamageCap</code></td><td>true</td><td>OutpostZero</td><td>OutpostZero infection damage can bypass death events and destroy armor before the player dies. Caps damage at 10000 to allow death events to fire first.</td></tr>
<tr><td><code>enableSpawnSchematic</code></td><td>false</td><td>Vanilla / All</td><td>Pastes a .schem schematic at world creation and permanently protects the area. Only takes effect on brand-new worlds. Requires <code>schematics/schematic_spawn.schem</code> in the game directory.</td></tr>
<tr><td><code>spawnSchematic.heightMode</code></td><td>SURFACE</td><td>Vanilla / All</td><td>Height placement for the spawn schematic. <code>SURFACE</code> aligns the bottom to the highest ground block under its footprint (default); <code>FIXED</code> places it at the absolute coordinate <code>spawnSchematic.fixedY</code>.</td></tr>
<tr><td><code>spawnSchematic.surfaceOffset</code></td><td>70</td><td>Vanilla / All</td><td>Extra blocks above the ground where the bottom of the schematic is placed (only in <code>SURFACE</code> mode). 0 = flush, positive = raised, negative = buried. The bundled lobby default is designed for 70.</td></tr>
<tr><td><code>spawnSchematic.fixedY</code></td><td>64</td><td>Vanilla / All</td><td>Absolute Y coordinate for the bottom of the schematic (only in <code>FIXED</code> mode).</td></tr>
<tr><td><code>chunkGen.enabled</code></td><td>false</td><td>Vanilla / All</td><td>Automatic chunk pregenerator. No dependencies.</td></tr>
<tr><td><code>chunkGen.chunksPerTick</code></td><td>1</td><td>Vanilla / All</td><td>Chunks generated per server tick (1-300). Higher = faster but more CPU.</td></tr>
<tr><td><code>chunkGen.maxRadius</code></td><td>0</td><td>Vanilla / All</td><td>Maximum generation radius in chunks (0 = unlimited).</td></tr>
<tr><td><code>chunkGen.dimensionOverworld</code></td><td>true</td><td>Vanilla / All</td><td>Generate chunks in the Overworld.</td></tr>
<tr><td><code>chunkGen.dimensionNether</code></td><td>false</td><td>Vanilla / All</td><td>Generate chunks in the Nether.</td></tr>
<tr><td><code>chunkGen.dimensionEnd</code></td><td>false</td><td>Vanilla / All</td><td>Generate chunks in The End.</td></tr>
<tr><td><code>chunkGen.runWithPlayers</code></td><td>false</td><td>Vanilla / All</td><td>If true, generation continues even when players are online. If false, pauses on player join.</td></tr>
<tr><td><code>chunkGen.keepAlive</code></td><td>true</td><td>Vanilla / All</td><td>Prevents the dedicated server from idling (60s timeout) while chunks are being generated.</td></tr>
<tr><td><code>dataPackFolder.enabled</code></td><td>false</td><td>Vanilla / All</td><td>Auto-loads every datapack from <code>&lt;game-dir&gt;/&lt;dataPackFolder.path&gt;</code> into all worlds (always enabled). Like Global Packs for datapacks.</td></tr>
<tr><td><code>dataPackFolder.path</code></td><td>datapacks</td><td>Vanilla / All</td><td>Folder (relative to the game directory) scanned for datapacks when <code>dataPackFolder.enabled</code> is true.</td></tr>
</table>

<br>

<h2>&#128196; Requirements</h2>

<table>
<tr><td><strong>Minecraft</strong></td><td>26.1.2</td></tr>
<tr><td><strong>NeoForge</strong></td><td>26.1.2.78+</td></tr>
</table>

<br>

<h2>&#127918; How to Use</h2>

<ol>
<li>Install the mod on <strong>both client and server</strong>.</li>
<li>Configure via <code>config/utility_core-common.toml</code> or the mod menu.</li>
<li>For ChunkGen: set <code>chunkGen.enabled=true</code> and the generator will auto-start when the server is empty.</li>
<li>Use <code>/utilitycore chunkgen status</code> to monitor generation progress.</li>
<li>For Spawn Schematic: set <code>enableSpawnSchematic=true</code>, place your schematic at <code>schematics/schematic_spawn.schem</code>, then create a <strong>new world</strong>. <blockquote><strong>To apply to an existing world, you must delete the world save folder first</strong> — this is intentional.</blockquote></li>
<li>For Data Pack Folder: set <code>dataPackFolder.enabled=true</code>, drop your datapacks (<code>.zip</code> or folder) into <code>&lt;game-dir&gt;/datapacks</code> and start/restart the world. They load into every world automatically, always enabled.</li>
</ol>

<h3>Known Incompatibilities</h3>
<blockquote><strong>Fast Workbench (fastbench)</strong>: Conflicts with the recipe selection system. Remove it to use this feature.</blockquote>

<br>

<h2>&#128295; For Developers</h2>

<pre><code>// Access the recipe selection API
PolymorphApi api = PolymorphApi.getInstance();
List&lt;RecipeHolder&lt;CraftingRecipe&gt;&gt; recipes = api.getRecipesFor(
    recipeManager, RecipeType.CRAFTING, craftingInput, level
);
PlayerRecipeData data = api.getPlayerRecipeData(player);
api.clearPlayerRecipeData(player);
</code></pre>

<br>

---

<br>

<h2>&#128591; Credits</h2>

<p>Biome/Dimension Titles logic ported from <a href="https://www.curseforge.com/minecraft/mc-mods/travelers-titles">Traveler's Titles</a> by YUNGNICKYOUNG (LGPLv3).</p>
<p>Developed by <strong>Stalking Dragons</strong>.</p>

<br>
<br>

<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons — Minecraft Modding</em>
</p>
