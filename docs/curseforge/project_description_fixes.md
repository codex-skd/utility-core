<h1 align="center">&#9881;&#65039; Utility Core Fixes</h1>

<p align="center"><strong>Compatibility fixes and crash prevention for modded Minecraft servers.</strong></p>

<p align="center">
  <img src="https://img.shields.io/curseforge/dt/PROJECT_ID?style=plastic&logo=curseforge&label=downloads" alt="CurseForge Downloads">
  <img src="https://img.shields.io/curseforge/v/PROJECT_ID?style=plastic&logo=curseforge&label=latest" alt="CurseForge Version">
  <img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
</p>

<hr>

<h2>Overview</h2>

<p><strong>Utility Core Fixes</strong> provides essential compatibility fixes and crash prevention for modded Minecraft servers. All features are individually toggleable via <code>config/utility_core_fixes-common.toml</code> (or the in-game mod menu).</p>

<h2>Features</h2>

<h3>Vehicle Anti-Cheat Whitelist</h3>
<p>Allows configurable whitelisting of entity types from vanilla's <code>moved too quickly</code> vehicle anti-cheat. Prevents spam of warnings like <code>entity.evilcraft.broom (vehicle of X) moved too quickly!</code> by hooking the inline vehicle check inside <code>ServerGamePacketListenerImpl.handleMoveVehicle</code> (the dedicated <code>isVehicleMovingTooFast</code> / <code>checkVehicleMovement</code> methods no longer exist in 26.2). Configure via <code>vehicleAntiCheatWhitelist</code> (default: <code>["evilcraft:broom"]</code>).</p>

<h3>Corail Tombstone Compatibility</h3>
<ul>
  <li><strong>GUI Scale Fix</strong> — Prevents Corail Tombstone from forcing GUI scale to 4 when opening its menus; restores your configured scale on close</li>
  <li><strong>Item Init Fix</strong> — Properly initializes NBT data for Tombstone items (lollipop color, magic_scroll random effect) when obtained via <code>/give</code> or creative menu</li>
  <li><strong>Error Handler</strong> — Suppresses mixin errors from Tombstone's <code>ItemInputMixin</code> to prevent crashes on server startup</li>
</ul>

<h3>Negative Damage Fix</h3>
<p>Clamps negative damage values to zero, preventing <code>IllegalArgumentException: Damage cannot be negative</code> server crashes from any mod interaction (e.g., Apothic Attributes + Tombstone).</p>

<h3>OutpostZero Infection Damage Cap</h3>
<p>Caps <code>outpostzero:infection</code> damage type to 10,000 to prevent armor destruction before death events fire.</p>

<hr>

<h2>Configuration</h2>

<p>All features configurable via <code>utility_core_fixes-common.toml</code>:</p>

<pre><code># Tombstone fixes
enableTombstoneGuiScaleFix = true
enableTombstoneItemInitFix = true
enableTombstoneErrorHandler = true

# General fixes
enableNegativeDamageFix = true
enableOutpostZeroDamageCap = true

# Vehicle anti-cheat whitelist
enableVehicleAntiCheatWhitelist = true
vehicleAntiCheatWhitelist = ["evilcraft:broom"]
</code></pre>

<hr>

<h2>Requirements</h2>

<table>
  <tr><th>Requirement</th><th>Version</th></tr>
  <tr><td>Minecraft</td><td>26.2 (1.21.1)</td></tr>
  <tr><td>NeoForge</td><td>26.2.0.37-beta+</td></tr>
</table>

<p><strong>Optional dependencies</strong> (fixes only activate when mods present):</p>
<ul>
  <li>Corail Tombstone</li>
  <li>OutpostZero</li>
  <li>EvilCraft (for vehicle anti-cheat whitelist)</li>
</ul>

<hr>

<p>MIT License. Developed by <strong>Stalking Dragons</strong>.</p>