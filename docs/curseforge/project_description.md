<h1 align="center">⚙️ Utility Core</h1>

<p align="center"><strong>A library mod that resolves crafting conflicts and prevents damage crashes.</strong></p>

<br>

---

<br>

<h2>✨ Overview</h2>

<p>Utility Core provides shared utilities for NeoForge modpacks. When multiple crafting recipes match the same ingredients, a selection widget lets you pick the output. Negative damage from mod interactions is automatically clamped to zero, preventing server crashes.</p>

<br>

<h2>🎯 Features</h2>

<h3>🔀 Recipe Conflict Resolution</h3>
<p>When multiple crafting recipes match the same inputs, a selection widget appears in the crafting table and inventory 2x2 grid. The selected recipe is highlighted in green, and alternatives are shown with a white background. Click any alternative to instantly change the output.</p>
<ul>
<li><strong>Server-side</strong>: Detects matching recipes, stores the player&#8217;s selection via attachments, syncs the output list to the client</li>
<li><strong>Client-side</strong>: Renders a recipe selector grid, sends selection changes back to the server</li>
<li><strong>Instant update</strong>: The result slot updates immediately when clicking an alternative recipe</li>
</ul>

<h3>🛡️ Damage Safety</h3>
<p>Negative damage values from mod interactions (e.g., Apothic Attributes critical strikes + Tombstone&#8217;s Decrepitude effect) are automatically clamped to 0, preventing <code>IllegalArgumentException: Damage cannot be negative</code> crashes that would otherwise crash the server.</p>

<h3>⚰️ Corail Tombstone Compatible</h3>
<p>Automatically suppresses incompatible mixin errors from Tombstone 9.x without breaking functionality. GUI scale is restored after opening Tombstone menus.</p>

<h3>⚙️ Configurable</h3>
<p>All features can be configured via the in-game config screen or the config file:</p>
<ul>
<li>Enable/disable the crafting recipe selector</li>
<li>Maximum number of alternative recipes displayed</li>
</ul>

<h3>💻 Developer API</h3>
<p>Other mods can integrate with the recipe selection system using the provided <code>PolymorphApi</code> class.</p>

<br>

<h2>📋 Requirements</h2>

<table>
<tr><td><strong>Minecraft</strong></td><td>26.1.2</td></tr>
<tr><td><strong>NeoForge</strong></td><td>26.1.2.78+</td></tr>
</table>

<br>

<h2>🎮 How to Use</h2>

<ol>
<li>Install the mod on <strong>both client and server</strong>.</li>
<li>Open a crafting table and place items that match multiple recipes.</li>
<li>A selector grid appears — click an alternative recipe to choose your output.</li>
<li>Configure via <strong>Mods menu &gt; Utility Core &gt; Config</strong> or <code>config/utility_core-common.toml</code>.</li>
</ol>

<h3>Known Incompatibilities</h3>
<blockquote><strong>Fast Workbench (fastbench)</strong>: This mod conflicts with the recipe selection system. Remove it to use this feature.</blockquote>

<br>

<h2>🔧 For Developers</h2>

<pre><code>// Access the API
PolymorphApi api = PolymorphApi.getInstance();

// Get all recipes matching a specific input
List&lt;RecipeHolder&lt;CraftingRecipe&gt;&gt; recipes = api.getRecipesFor(
    recipeManager, RecipeType.CRAFTING, craftingInput, level
);

// Get player recipe selection data
PlayerRecipeData data = api.getPlayerRecipeData(player);

// Clear player recipe selection data
api.clearPlayerRecipeData(player);
</code></pre>

<br>

---

<br>

<h2>🙏 Credits</h2>

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
