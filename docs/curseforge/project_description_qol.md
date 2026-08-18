<h1 align="center">&#128161; Utility Core QoL</h1>

<p align="center"><strong>Quality of life client-side improvements for Minecraft.</strong></p>

<p align="center">
  <img src="https://img.shields.io/curseforge/dt/PROJECT_ID?style=plastic&logo=curseforge&label=downloads" alt="CurseForge Downloads">
  <img src="https://img.shields.io/curseforge/v/PROJECT_ID?style=plastic&logo=curseforge&label=latest" alt="CurseForge Version">
  <img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
</p>

<hr>

<h2>Overview</h2>

<p><strong>Utility Core QoL</strong> provides quality-of-life client-side improvements for Minecraft. All features are individually toggleable via <code>config/utility_core_qol-common.toml</code> (or the in-game mod menu).</p>

<h2>Features</h2>

<h3>Recipe Selector (Polymorph System)</h3>
<ul>
  <li>When multiple crafting recipes match the same ingredients, shows a clickable grid to choose which result you want</li>
  <li>Works in <strong>Crafting Table</strong> (3&times;3) and <strong>Player Inventory</strong> (2&times;2) crafting grids</li>
  <li>Syncs selection to server for correct result item</li>
  <li>Configurable max recipes displayed (1-64, default 16)</li>
  <li>Keyboard/mouse navigation</li>
</ul>

<h3>Biome & Dimension Titles</h3>
<ul>
  <li>Shows vanilla title/subtitle when entering a new biome or dimension</li>
  <li>Ported from Traveler's Titles (LGPLv3) by YUNGNICKYOUNG</li>
  <li>Reuses vanilla title HUD (no custom renderer)</li>
  <li>Fade timings: 10/70/20 ticks</li>
</ul>

<h3>Title Vertical Offset</h3>
<ul>
  <li>Configurable vertical shift for vanilla title/subtitle text (-200 to +200, default +75)</li>
  <li>Positive = up, Negative = down</li>
</ul>

<h3>Bridging Assist</h3>
<ul>
  <li>New "reacharound" placement feature ported from BridgingMod.</li>
  <li>Helps you bridge/scaffold by extending block placement to positions you're looking at but not directly reaching.</li>
  <li>Includes a crosshair indicator and outline showing where the next block will go.</li>
  <li>Features slab assist and configurable distance/axes/delay settings.</li>
  <li>Toggle with keybind (default: comma).</li>
</ul>

<hr>

<h2>Configuration</h2>

<p>Via <code>utility_core_qol-common.toml</code>:</p>

<pre><code># Recipe Selector
enableCraftingRecipeSelector = true
maxRecipesDisplayed = 16
logDetectedConflicts = false

# Biome/Dimension Titles
enableBiomeDimensionTitles = true

# Title Vertical Offset
titleVerticalOffset = 75  # -200 to 200
</code></pre>

<hr>

<h2>How Recipe Selector Works</h2>

<ol>
  <li>Open crafting table or inventory crafting grid</li>
  <li>Place ingredients that match multiple recipes</li>
  <li>Grid appears showing all possible results</li>
  <li>Click desired result &rarr; server syncs selection</li>
  <li>Result updates in output slot</li>
</ol>

<hr>

<h2>API (for other mods)</h2>

<pre><code>// Get player's recipe selection data
PolymorphApi.INSTANCE.getPlayerRecipeData(player)

// Find all recipes matching input
PolymorphApi.INSTANCE.getRecipesFor(recipeManager, recipeType, input, level)

// Clear player's recipe data
PolymorphApi.INSTANCE.clearPlayerRecipeData(player)
</code></pre>

<hr>

<h2>Credits</h2>

<ul>
  <li><strong>Biome/Dimension Titles</strong>: Ported from <a href="https://www.curseforge.com/minecraft/mc-mods/travelers-titles">Traveler's Titles</a> by YUNGNICKYOUNG (LGPLv3)</li>
</ul>

<hr>

<h2>Requirements</h2>

<table>
  <tr><th>Requirement</th><th>Version</th></tr>
  <tr><td>Minecraft</td><td>26.2</td></tr>
  <tr><td>NeoForge</td><td>26.2.0.37-beta+</td></tr>
</table>

<p><strong>Side</strong>: Client (primary) + Server (sync packets)</p>

<hr>

<h2>Known Incompatibilities</h2>

<blockquote><strong>Fast Workbench (fastbench)</strong>: Conflicts with the recipe selection system. Remove it to use this feature.</blockquote>

<hr>

<p>MIT License. Developed by <strong>Stalking Dragons</strong>.</p>
<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons — Minecraft Modding</em>
</p>