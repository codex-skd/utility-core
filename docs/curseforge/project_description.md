<h1 align="center">&#9881;&#65039; Utility Core</h1>

<p align="center"><strong>A multi-purpose library mod for NeoForge modpacks. Recipe conflict resolution, damage safety, Ender Dragon respawn, automatic chunk pregeneration, and mod compatibility fixes.</strong></p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<p>Utility Core is a Swiss Army knife for modded Minecraft servers. It started as a simple recipe conflict resolver and evolved into a collection of essential utilities that every modpack needs. All features are toggleable in the config.</p>

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

<h3>&#128123; Ender Dragon Auto-Respawn</h3>
<p>Automatically respawns the Ender Dragon on server start if it was previously killed. Fully compatible with YUNG&#8217;s Better End Island — places crystals at the correct positions and uses YUNG&#8217;s native respawn system.</p>

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

<h3>&#9762;&#65039; Corail Tombstone Compatibility</h3>
<ul>
<li>Suppresses incompatible mixin errors from Tombstone 9.x</li>
<li>Restores GUI scale after opening Tombstone screens</li>
<li>Fixes NBT initialization for Tombstone items obtained via <code>/give</code></li>
</ul>

<h3>&#128299; Other Fixes</h3>
<ul>
<li><strong>OutpostZero Damage Cap</strong>: Limits infection damage to 10000 to prevent instant death</li>
<li><strong>Ender Dragon compatibility</strong>: Works with YUNG&#8217;s Better End Island 4.x</li>
</ul>

<h3>&#9881;&#65039; Fully Configurable</h3>
<p>Every feature can be enabled/disabled individually via <code>config/utility_core-common.toml</code>:</p>

<table>
<tr><th>Config</th><th>Default</th><th>Mod</th><th>Why</th></tr>
<tr><td><code>enableCraftingRecipeSelector</code></td><td>true</td><td>Any mod with recipe conflicts</td><td>Shows a selector UI when multiple crafting recipes match the same ingredients. Incompatible with Fast Workbench (fastbench).</td></tr>
<tr><td><code>enableNegativeDamageFix</code></td><td>true</td><td>Apothic Attributes + Tombstone</td><td>Apothic critical strikes + Tombstone Decrepitude can produce negative damage values, crashing the server with <code>IllegalArgumentException: Damage cannot be negative</code>. This clamps damage to 0.</td></tr>
<tr><td><code>enableEndDragonRespawn</code></td><td>false</td><td>YUNG&#8217;s Better End Island / Vanilla</td><td>Automatically respawns the Ender Dragon on server start. Works with or without YUNG&#8217;s BEI:<br>With YUNG: detects portal at Y=63, places crystals at dist=7 (BEI radius)<br>Without YUNG: assumes portal at (0,60,0), places crystals at dist=2 (vanilla radius)<br>Last resort: force-places bedrock + crystal at the exact positions YUNG checks.</td></tr>
<tr><td><code>enableTombstoneGuiScaleFix</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone forces GUI scale to 4 when opening its screens. This restores the original scale when closing.</td></tr>
<tr><td><code>enableTombstoneItemInitFix</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone items (lollipop, magic_scroll) obtained via <code>/give</code> lack proper NBT data. This initializes them correctly.</td></tr>
<tr><td><code>enableTombstoneErrorHandler</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone 9.x has a mixin (<code>ItemInputMixin</code>) that fails to apply in certain NeoForge versions. Instead of crashing, this suppresses the error gracefully.</td></tr>
<tr><td><code>enableOutpostZeroDamageCap</code></td><td>true</td><td>OutpostZero</td><td>OutpostZero infection damage can bypass death events and destroy armor before the player dies. Caps damage at 10000 to allow death events to fire first.</td></tr>
<tr><td><code>chunkGen.enabled</code></td><td>false</td><td>Vanilla / All</td><td>Automatic chunk pregenerator. No dependencies.</td></tr>
<tr><td><code>chunkGen.chunksPerTick</code></td><td>1</td><td>Vanilla / All</td><td>Chunks generated per server tick (1-300). Higher = faster but more CPU.</td></tr>
<tr><td><code>chunkGen.dimensionOverworld</code></td><td>true</td><td>Vanilla / All</td><td>Generate chunks in the Overworld.</td></tr>
<tr><td><code>chunkGen.dimensionNether</code></td><td>false</td><td>Vanilla / All</td><td>Generate chunks in the Nether.</td></tr>
<tr><td><code>chunkGen.dimensionEnd</code></td><td>false</td><td>Vanilla / All</td><td>Generate chunks in The End.</td></tr>
<tr><td><code>chunkGen.runWithPlayers</code></td><td>false</td><td>Vanilla / All</td><td>If true, generation continues even when players are online. If false, pauses on player join.</td></tr>
<tr><td><code>chunkGen.keepAlive</code></td><td>true</td><td>Vanilla / All</td><td>Prevents the dedicated server from idling (60s timeout) while chunks are being generated.</td></tr>
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
<li>For Ender Dragon: set <code>enableEndDragonRespawn=true</code> and the dragon will respawn on next server start.</li>
<li>Use <code>/utilitycore chunkgen status</code> to monitor generation progress.</li>
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
