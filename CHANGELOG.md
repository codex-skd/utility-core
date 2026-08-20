# Utility Core — Registro de cambios

> A partir de aquí (post-2.2.1), `admin`, `fixes` y `qol` versionan de forma independiente. Las entradas nuevas se encabezan `## [Mod] X.Y.Z`; las entradas anteriores a este punto usan versión compartida y siguen aplicando a los 3 mods.


## [Admin] 2.3.0

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.45-beta a 26.2.0.57.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `utility_core_admin-26.2-neoforge-26.2.0.57-2.3.0.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_UTILITY_CORE_26-2.md` para reflejar la nueva rama de trabajo.


## [Fixes] 2.4.0

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.45-beta a 26.2.0.57.
- **Supresión de logs**: el fallback de `IllegalStateException` en el predicado de loot ahora registra una vez y suprime repeticiones (un resumen cada 200 ocurrencias) en lugar de saturar el log del servidor.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `utility_core_fixes-26.2-neoforge-26.2.0.57-2.4.0.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_UTILITY_CORE_26-2.md` para reflejar la nueva rama de trabajo.


## [QoL] 2.3.0

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.45-beta a 26.2.0.57.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `utility_core_qol-26.2-neoforge-26.2.0.57-2.3.0.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_UTILITY_CORE_26-2.md` para reflejar la nueva rama de trabajo.

## [Admin] 2.2.3

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.37-beta a 26.2.0.45-beta.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `utility_core_admin-26.2-neoforge-26.2.0.45-beta-2.2.3.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_UTILITY_CORE_26-2.md` para reflejar la nueva rama de trabajo.

## [Fixes] 2.3.5

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.37-beta a 26.2.0.45-beta.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `utility_core_fixes-26.2-neoforge-26.2.0.45-beta-2.3.5.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_UTILITY_CORE_26-2.md` para reflejar la nueva rama de trabajo.

## [QoL] 2.2.5

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.37-beta a 26.2.0.45-beta.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `utility_core_qol-26.2-neoforge-26.2.0.45-beta-2.2.5.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_UTILITY_CORE_26-2.md` para reflejar la nueva rama de trabajo.

## [Fixes] 2.3.4
- Fix: **mixin del whitelist de vehículos no aplicaba en 26.2 (`InvalidMixinException` al arrancar)** — el `@Redirect` acotado sobre `isSingleplayerOwner()` dentro de `handleMoveVehicle` era correcto, pero el `@Shadow` declarado para llamar al método original fallaba: en 26.2 `isSingleplayerOwner()` se movió de `ServerGamePacketListenerImpl` a su superclase `ServerCommonPacketListenerImpl`, y Mixin no puede resolver `@Shadow` contra una superclase no registrada en su ClassInfo tracker. El mixin completo se descartaba con `InvalidMixinException: @Shadow method isSingleplayerOwner()Z was not located in the target class`, así que la whitelist quedaba desactivada en silencio. Reemplazado el `@Shadow` por un mixin `@Invoker` sobre `ServerCommonPacketListenerImpl` (`InvokerServerCommonPacketListenerImpl`), registrado en la sección `server` de `utility_core_fixes.mixins.json`; el handler del redirect ahora hace fallback a través de ese invoker.

## [Fixes] 2.3.3
- Fix: **whitelist del anti-cheat vanilla "moved too quickly" de vehículos no funcionaba en 26.2** — el fix interceptaba `isVehicleMovingTooFast` y `checkVehicleMovement` en `ServerGamePacketListenerImpl`, pero esos métodos se eliminaron en 26.2: el check quedó inline dentro de `handleMoveVehicle` (`if (movedSqr - velLenSqr > 100.0 && !isSingleplayerOwner())`), así que los targets del mixin ya no existían y la whitelist nunca se aplicaba. Reescrito como `@Redirect` acotado sobre la única llamada a `isSingleplayerOwner()` dentro de `handleMoveVehicle`: si el vehículo raíz del jugador está whitelisteado devuelve `true` y se salta el check solo para ese vehículo, sin tocar los demás usos del método (checks de movimiento de jugador, permisos de dificultad/gamemode). `defaultRequire` vuelto a `1` (target verificado contra `minecraft-server-patched-26.2.0.45-beta.jar`).

## [Fixes] 2.3.2
- Fix: **crash del servidor al morir con un curio encantado equipado** — el mixin de Curios sobre `NbtPredicate.getEntityTagToCompare` fusiona el inventario de Curios en el NBT comparado por predicados de loot table/advancement; si un curio equipado está encantado, esa serialización necesita acceso al registro `minecraft:enchantment`, que no está disponible en ese contexto y lanza `IllegalStateException: Can't access registry ResourceKey[minecraft:root / minecraft:enchantment]`, sin capturar, tumbando el servidor (p. ej. al morir por una flecha con drops de muerte). Ahora se captura esa excepción en `LootItemEntityPropertyCondition.test` y el predicado simplemente falla en vez de crashear. Nuevo toggle de config `enableCuriosLootPredicateFix` (activado por defecto).

## [QoL] 2.2.4
- Fix: **mixin target AbstractContainerScreen no encontrado** — mover `AccessorAbstractContainerScreen` al array `client` en `utility_core_qol_accessors.mixins.json` para evitar errores en servidor dedicado.

## [QoL] 2.2.3
- Fix: **spam de logs y tráfico de red redundante del selector de recetas** — `CraftingMenu.slotChangedCraftingGrid` se dispara repetidamente aunque el contenido de la rejilla no cambie. Las ramas de "0 recetas" y "1 receta" reenviaban un paquete de sincronización al cliente en cada disparo sin comprobar nada, a diferencia de la rama de "varias recetas", que ya comprobaba si los inputs habían cambiado. Ahora las tres ramas se saltan el reenvío si los inputs no cambiaron. Además, los 3 logs de `receiveServerRecipes` en cliente (disparados en cada paquete recibido) estaban en nivel `INFO` sin condición; ahora están en `DEBUG`.

## [Fixes] 2.3.1
- Feature: **whitelist configurable para el anti-cheat vanilla "moved too quickly" de vehículos** — evita el spam de warnings `entity.evilcraft.broom (vehicle of X) moved too quickly!` al permitir que tipos de entidad especificados se muevan más rápido de lo que vanilla permite. Se interceptan `isVehicleMovingTooFast` y `checkVehicleMovement` en `ServerGamePacketListenerImpl`. Nuevo config `enableVehicleAntiCheatWhitelist` (activado por defecto) y lista `vehicleAntiCheatWhitelist` con formato `namespace:path` (por defecto: `["evilcraft:broom"]`).

## [Fixes] 2.3.0
- Feature: **fix genérico de crash por corrupción bloque/block-entity** — algunos chunks pueden quedar con NBT de block entity huérfana (p. ej. `enchanting_table`) en una posición cuyo bloque real ya no coincide (sustituido por otro bloque, típicamente por un mod tercero no relacionado). Cualquier código que intentara crear esa block entity de forma perezosa (incluido código de render de otro mod) lanzaba `IllegalStateException` y crasheaba el juego. Ahora, en servidor (o servidor integrado en singleplayer), se elimina la block entity huérfana para que la corrupción se autorrepare en vez de volver a crashear, y se loguea un warning con la posición y los tipos esperado/real; en cliente simplemente se trata como "sin block entity" sin tocar el mundo. No es específico de ningún bloque/mod concreto — cubre cualquier mismatch de este tipo. Nuevo toggle de config `enableBlockentityMismatchFix` (activado por defecto).

## [Admin] 2.2.2
## [Fixes] 2.2.2
## [QoL] 2.2.2
- Chore: versionado independiente por mod — `admin_version`/`fixes_version`/`qol_version` en `gradle.properties` en vez de un `mod_version` compartido. A partir de ahora cada mod bumpea y publica en CurseForge por separado; este bump conjunto es el único que se hace a la vez para dejar los 3 alineados en el punto de partida. Sin cambios de comportamiento.

## 2.2.1
- Fix (QoL): **NullPointerException al usar el crafteo** — `ModAttachments.ATTACHMENT_TYPES` (el attachment `player_recipe_data` que guarda la receta seleccionada por jugador) se creaba pero nunca se registraba en el mod event bus. El attachment type quedaba "unbound" y el mixin del selector de recetas (`CraftingMenu.onSlotChangedCraftingGrid`) lanzaba `NullPointerException: Trying to access unbound value` al cambiar la rejilla de crafteo. Ahora `ModAttachments.ATTACHMENT_TYPES.register(modEventBus)` se llama en el constructor de `UtilityCoreQoL`.

## 2.2.0
- Fix (QoL): **JAR inválido, servidor no arrancaba** — el `expand` de Gradle añadido en 2.1.2 para resolver `${mod_version}` interpretaba el `\n` literal del campo `credits` (usado para separar dos líneas de atribución) como un salto de línea real, generando un `neoforge.mods.toml` con un newline crudo dentro de un basic string TOML (inválido). El loader rechazaba el JAR con `Invalid newline in basic string` y ninguno de los 3 mods cargaba. Ahora `expand` usa `escapeBackslash = true` para no tocar backslashes fuera de los placeholders `${...}`.
- Change (Admin, Fixes, QoL): los 3 configs ahora se guardan dentro de `config/utility_core/` (`utility_core_admin-common.toml`, `utility_core_fixes-common.toml`, `utility_core_qol-common.toml`, `utility_core_qol-client.toml`) en vez de sueltos directamente en `config/`. **Rompe configs existentes**: quien actualice desde una versión anterior verá sus valores actuales huérfanos en `config/` y se generarán configs nuevos con valores por defecto en `config/utility_core/`; hay que migrar los valores manualmente si se quieren conservar.

## 2.1.2
- Fix (Admin, Fixes, QoL): **crash crítico en servidor dedicado** — `registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new)` se ejecutaba sin comprobar el lado (`Dist`) en el constructor de los 3 mods. `ConfigurationScreen` extiende la clase cliente-only `Screen`, por lo que el classloading de esa referencia lanzaba `NoClassDefFoundError: net/minecraft/client/gui/screens/Screen` y tumbaba la carga de los 3 mods en cualquier servidor dedicado. Ahora el registro solo ocurre si `FMLEnvironment.dist.isClient()`.
- Fix (build): `version` en los 3 `neoforge.mods.toml` estaba hardcodeado como literal `"2.0.0"` y nunca reflejaba `mod_version` — el crash report mostraba "Mod version: 2.0.0" pese a que el JAR era 2.1.0. Ahora usa `${mod_version}` con `expand` en `processResources`, igual que el resto de mods del ecosistema.

## 2.1.0

## [2.1.1] - 2026-08-12

### Change

- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `utility_core_fixes-26.2-neoforge-26.2.0.37-beta-2.1.1.jar` (se añade la versión de cargador/NeoForge al nombre del archivo). Empaquetado y documentación; sin cambios de funcionalidad.

- Feature (QoL): se agregó la asistencia de puentear (Bridging Assist), portada desde BridgingMod — colocación "reacharound" al construir puentes, outline y crosshair direccional, slab assist, y ajustes de distancia/ejes/retardo configurables.
- Feature (QoL): soporte opcional para DankStorage y Sable mediante verificaciones suaves (sin dependencias duras); tecla de enlace para activar/desactivar la asistencia (predeterminada: coma).
- Fix (Admin, Fixes): se agregaron los puntos de entrada `@Mod` faltantes en admin y fixes — antes ninguno de los dos registraba su config ni funcionaba como mod independiente.
- Fix (Admin): `ServerStartingEvent`/`ServerStartedEvent`/`ServerTickEvent` estaban registrados en el bus equivocado (mod-bus en vez de `NeoForge.EVENT_BUS`), causando un crash al cargar el cliente.
- Fix (Admin): `SpawnSchematicManager` nunca se instanciaba, causando un `NullPointerException` al iniciar el servidor integrado.
- Fix (QoL): conflicto de config entre `BridgingConfig` y `QoLConfig` (ambos apuntaban al mismo `.toml`) — `BridgingConfig` pasa a config de cliente (`utility_core_qol-client.toml`).
- Fix (QoL): los mixins de bridging (crosshair, outline) tenían una ruta de paquete duplicada en `utility_core_qol.mixins.json` y nunca se cargaban; movidos a su propio archivo de mixins.
- Fix (QoL): el sprite de la flecha direccional del crosshair tenía un path erróneo (nunca se encontraba la textura); el color del contorno se calculaba mal (truncaba a negro); la colocación con items de compat especiales (DankStorage, etc.) se saltaba el handler correspondiente.
- Chore (QoL): se eliminaron logs de depuración residuales del selector de recetas que se disparaban en cada cambio de la rejilla de crafteo; ahora están tras el flag `logDetectedConflicts`.
- Chore: se eliminó el archivo `Config.java` duplicado y el `META-INF/neoforge.mods.toml` huérfano directamente bajo la raíz del repositorio.
- Fix (build): la versión de los subproyectos estaba hardcodeada en `build.gradle` en vez de leerse de `mod_version`, así que los bumps de versión no llegaban a los JARs.
## 1.11.13
- Fix: el selector de recetas de la mesa de crafteo dejaba de responder (el clic se registraba pero el servidor lo rechazaba). El menú de inventario (`InventoryMenu`) permanece suscrito a los cambios del inventario del jugador aunque haya otro menú abierto, y cada cambio disparaba `slotChangedCraftingGrid` con la rejilla 2x2 vacía; eso hacía que el servidor borrara los datos de recetas del jugador (y enviara sincronizaciones con 0 recetas) constantemente. Ahora el cálculo del selector solo procesa el menú activo del jugador (`player.containerMenu`), de modo que la selección se mantiene hasta que el jugador cambia los ingredientes.

## 1.11.12
- Fix: el selector de recetas mostraba la segunda opción al hacer clic pero volvía a la primera opción inmediatamente. El servidor solo actualizaba el slot remoto (la copia de lo que ve el cliente) pero no el resultado real del menú, así que `broadcastChanges()` revertía la salida a la receta 0 en el siguiente tick. Ahora al seleccionar una receta el servidor escribe también el slot de resultado real (`ResultContainer`) y fija `recipeUsed`, de modo que la selección se mantiene.
- Re-activado por defecto: `enableCraftingRecipeSelector` vuelve a `true`.
- Logs de depuración del selector con prefijo `[RecipeSelector]` (nivel INFO).

## 1.11.11
- Logging limpio: eliminados los miles de entradas de log añadidos durante el diagnóstico del selector de recetas.

## 1.11.10
- Desactivado por defecto el selector de recetas (`enableCraftingRecipeSelector` default false) pendiente de fix del lado servidor.

## 1.11.9
- Diagnóstico: Agregado logging del número de recetas que encuentra el servidor al colocar items en la mesa de crafteo.

## 1.11.8
- Diagnóstico: Agregado logging para verificar si el mixin slotChangedCraftingGrid se ejecuta en el servidor.

## 1.11.7
- Fix: el selector de recetas de la mesa de crafteo no mostraba la primera opción al hacer clic en ella.

## 1.11.6
- Re-activado por defecto: `enableCraftingRecipeSelector` vuelve a `true`.
- Fix: el selector de recetas de la mesa de crafteo ahora funciona correctamente en el servidor.

## 1.11.5
- Fix: el selector de recetas de la mesa de crafteo no mostraba la primera opción al hacer clic en ella.

## 1.11.4
- Fix: el selector de recetas de la mesa de crafteo no mostraba la primera opción al hacer clic en ella.

## 1.11.3
- Fix: el selector de recetas de la mesa de crafteo no mostraba la primera opción al hacer clic en ella.

## 1.11.2
- Fix: el selector de recetas de la mesa de crafteo no mostraba la primera opción al hacer clic en ella.

## 1.11.1
- Fix: el selector de recetas de la mesa de crafteo no mostraba la primera opción al hacer clic en ella.

## 1.11.0
- Feature: selector de recetas de la mesa de crafteo.

## 1.10.0
- Feature: título de bioma/dimensión.

## 1.9.0
- Feature: título de bioma/dimensión vertical offset.

## 1.8.0
- Feature: título de bioma/dimensión.

## 1.7.0
- Feature: selector de recetas de la mesa de crafteo.

## 1.6.0
- Feature: título de bioma/dimensión.

## 1.5.0
- Feature: título de bioma/dimensión.

## 1.4.0
- Feature: título de bioma/dimensión.

## 1.3.0
- Feature: selector de recetas de la mesa de crafteo.

## 1.2.0
- Feature: título de bioma/dimensión.

## 1.1.0
- Feature: selector de recetas de la mesa de crafteo.

## 1.0.0
- Versión inicial.
