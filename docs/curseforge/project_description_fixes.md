<h1 align="center">&#9881;&#65039; Utility Core Fixes</h1>

<p align="center"><strong>Compatibility fixes and crash prevention for modded Minecraft servers.</strong></p>

<p align="center">
  <img src="https://img.shields.io/curseforge/dt/PROJECT_ID?style=plastic&logo=curseforge&label=downloads" alt="CurseForge Downloads">
  <img src="https://img.shields.io/curseforge/v/PROJECT_ID?style=plastic&logo=curseforge&label=latest" alt="CurseForge Version">
  <img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
</p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<p><strong>Utility Core Fixes</strong> is a Swiss Army knife of crash prevention and compatibility fixes for modded Minecraft servers. Each fix targets a specific broken interaction between popular mods (or vanilla) that would otherwise crash the server or break gameplay. All features are <strong>toggleable individually</strong> from <code>config/utility_core_fixes-common.toml</code> (or the in-game mod menu).</p>

<p>What it covers:</p>
<ul>
<li><strong>Server safety</strong> — clamp negative damage, cap OutpostZero infection damage</li>
<li><strong>Mod compatibility</strong> — Corail Tombstone, Curios API, EvilCraft, OutpostZero fixes</li>
<li><strong>World integrity</strong> — remove orphaned block entity data to stop chunk-load crashes</li>
<li><strong>Anti-cheat tuning</strong> — whitelist entities from vanilla's <code>moved too quickly</code> vehicle check</li>
<li><strong>Nether portal reliability</strong> — remembers the correct return portal instead of vanilla's error-prone closest-portal search</li>
</ul>

<br>

<h2>&#127919; Features</h2>

<h3>&#128737;&#65039; Negative Damage Fix</h3>
<p>Clamps negative damage values to zero, preventing <code>IllegalArgumentException: Damage cannot be negative</code> server crashes from any mod interaction (e.g., Apothic Attributes + Tombstone).</p>

<h3>&#129516; OutpostZero Infection Damage Cap</h3>
<p>Caps <code>outpostzero:infection</code> damage to 10,000 so armor is not destroyed before death events fire.</p>

<h3>&#128296; Block Entity Mismatch Fix</h3>
<p>Prevents crashes from stale block entity NBT by removing orphaned block entity data when a block/entity type mismatch is detected on chunk load (e.g., after a mod that changed block entity types is removed).</p>

<h3>&#128373; Curios Loot Predicate Crash Fix</h3>
<p>Prevents a server crash caused by Curios API's NBT merging during loot table predicate evaluation. Requires Curios to be present, but the mod does not hard-depend on it.</p>

<h3>&#9775;&#65039; Corail Tombstone Compatibility</h3>
<ul>
<li><strong>GUI Scale Fix</strong> — Prevents Tombstone from forcing GUI scale to 4 when opening its menus; restores your configured scale on close</li>
<li><strong>Item Init Fix</strong> — Properly initializes NBT data for Tombstone items (lollipop color, magic_scroll random effect) when obtained via <code>/give</code> or creative menu</li>
<li><strong>Error Handler</strong> — Suppresses mixin errors from Tombstone's <code>ItemInputMixin</code> to prevent crashes on server startup</li>
</ul>

<h3>&#128661; Vehicle Anti-Cheat Whitelist</h3>
<p>Whitelists entity types from vanilla's <code>moved too quickly</code> vehicle anti-cheat, preventing spam of warnings like <code>entity.evilcraft.broom (vehicle of X) moved too quickly!</code>. Hooks the inline vehicle check inside <code>ServerGamePacketListenerImpl.handleMoveVehicle</code> (the dedicated <code>isVehicleMovingTooFast</code> / <code>checkVehicleMovement</code> methods no longer exist in 26.2).</p>
<ul>
<li>Supports wildcards: <code>"modid:*"</code> whitelists every entity of that mod</li>
<li>Scoped redirect: only neutralizes the vehicle check for whitelisted vehicles, leaving player movement, difficulty and gamemode permission checks untouched</li>
</ul>

<h3>&#127774; Nether Return Portal Fix</h3>
<p>Fixes vanilla's unreliable Nether portal linking in multiplayer: normally, returning through a portal searches for the <em>closest</em> portal on the other side instead of the exact one you originally used, which can send you to the wrong portal when several exist near each other. This fix remembers which portal you departed from every time you cross between the Overworld and the Nether, paired with the portal you arrived at, and redirects you back to that exact remembered portal on the return trip — falling back to vanilla behavior if it no longer exists.</p>

<br>

<h2>&#9881;&#65039; Fully Configurable</h2>

<p>Every feature can be enabled/disabled individually via <code>config/utility_core_fixes-common.toml</code>:</p>

<table>
<tr><th>Config</th><th>Default</th><th>Mod</th><th>Why</th></tr>
<tr><td><code>enableTombstoneGuiScaleFix</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone forces GUI scale to 4 when opening its screens. This restores the original scale when closing.</td></tr>
<tr><td><code>enableTombstoneItemInitFix</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone items (lollipop, magic_scroll) obtained via <code>/give</code> lack proper NBT data. This initializes them correctly.</td></tr>
<tr><td><code>enableTombstoneErrorHandler</code></td><td>true</td><td>Corail Tombstone</td><td>Tombstone's <code>ItemInputMixin</code> fails to apply on certain NeoForge versions. Instead of crashing, this suppresses the error gracefully.</td></tr>
<tr><td><code>enableNegativeDamageFix</code></td><td>true</td><td>Apothic Attributes + Tombstone</td><td>Apothic critical strikes + Tombstone Decrepitude can produce negative damage values, crashing the server with <code>IllegalArgumentException: Damage cannot be negative</code>. This clamps damage to 0.</td></tr>
<tr><td><code>enableOutpostZeroDamageCap</code></td><td>true</td><td>OutpostZero</td><td>OutpostZero infection damage can bypass death events and destroy armor before the player dies. Caps damage at 10000 to allow death events to fire first.</td></tr>
<tr><td><code>enableBlockentityMismatchFix</code></td><td>true</td><td>Any mod that changes block entity types</td><td>Orphaned block entity NBT from a removed/changed mod crashes chunk loading. This strips the stale data instead.</td></tr>
<tr><td><code>enableCuriosLootPredicateFix</code></td><td>true</td><td>Curios API</td><td>Curios' NBT merging during loot table predicate evaluation crashes the server. This neutralizes the failing merge.</td></tr>
<tr><td><code>enableVehicleAntiCheatWhitelist</code></td><td>true</td><td>Vanilla / EvilCraft</td><td>Whitelists configured entity types from the <code>moved too quickly</code> vehicle anti-cheat.</td></tr>
<tr><td><code>vehicleAntiCheatWhitelist</code></td><td><code>["evilcraft:broom"]</code></td><td>EvilCraft</td><td>List of entity type IDs (<code>namespace:path</code>) to whitelist. Supports wildcards like <code>"modid:*"</code>.</td></tr>
<tr><td><code>enableNetherReturnPortalFix</code></td><td>true</td><td>Vanilla</td><td>Vanilla's closest-portal search can send players to the wrong Nether portal on return trips. This remembers and redirects to the exact portal originally used.</td></tr>
</table>

<br>

<h2>&#128196; Requirements</h2>

<table>
<tr><td><strong>Minecraft</strong></td><td>26.2</td></tr>
<tr><td><strong>NeoForge</strong></td><td>26.2.0.37-beta+</td></tr>
</table>

<p><strong>Side</strong>: Client + Server (universal). Install on both for all fixes to apply.</p>

<p><strong>Optional dependencies</strong> (fixes only activate when the mods are present):</p>
<ul>
<li>Corail Tombstone</li>
<li>OutpostZero</li>
<li>Curios API</li>
<li>EvilCraft (for vehicle anti-cheat whitelist)</li>
</ul>

<br>

<h2>&#127918; How to Use</h2>

<ol>
<li>Install the mod on <strong>both client and server</strong>.</li>
<li>Nothing to configure for the defaults to work — every fix is enabled by default.</li>
<li>Disable anything you don't need via <code>config/utility_core_fixes-common.toml</code> or the in-game mod menu.</li>
<li>For the vehicle anti-cheat whitelist: add the entity IDs you want to allow to <code>vehicleAntiCheatWhitelist</code> (wildcards supported).</li>
</ol>

<br>

---

<br>

<h2>&#128591; Credits</h2>

<p>Developed by <strong>Stalking Dragons</strong>.</p>

<br>
<br>

<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons — Minecraft Modding</em>
</p>