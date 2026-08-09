# Utility Core — Registro de cambios
## 1.11.12
- Fix: el selector de recetas mostraba la segunda opción al hacer clic pero volvía a la primera opción inmediatamente. El servidor solo actualizaba el slot remoto (la copia de lo que ve el cliente) pero no el resultado real del menú, así que `broadcastChanges()` revertía la salida a la receta 0 en el siguiente tick. Ahora al seleccionar una receta el servidor escribe también el slot de resultado real (`ResultContainer`) y fija `recipeUsed`, de modo que la selección se mantiene.
- Re-activado por defecto: `enableCraftingRecipeSelector` vuelve a `true`.
- Logs de depuración del selector con prefijo `[RecipeSelector]` (nivel INFO).

## 1.11.11
- Logging limpio: eliminados los miles de entradas de log añadidas durante el diagnóstico del selector de recetas.

## 1.11.10
- Desactivado por defecto el selector de recetas (`enableCraftingRecipeSelector` default false) pendiente de fix del lado servidor.

## 1.11.9
- Diagnóstico: Agregado logging del número de recetas que encuentra el servidor al colocar items en la mesa de crafteo.

## 1.11.8
- Diagnóstico: Agregado logging para verificar si el mixin slotChangedCraftingGrid se ejecuta en el servidor.


## 1.11.7
- Diagnóstico: Agregado logging completo en servidor para rastrear selección de recetas. Ahora registra: cuándo se detectan múltiples recetas, cuándo se actualiza la lista, qué receta está seleccionada, y cuándo falla una selección. Esto permite diagnosticar por qué el selector siempre vuelve a la primera opción.

## 1.11.6
- Fix: la selección de receta volvía a la primera opción incluso después de haber sido seleccionada. El servidor disparaba un recálculo de recetas tras procesar la selección del cliente (`menu.slotsChanged()`), lo que volvía a ejecutar toda la lógica de validación y reseteba la selección. Ahora al recibir la selección del cliente se aplica directamente sin disparar el recálculo, permitiendo que la selección se mantenga fija.

## 1.11.5
- Fix: al seleccionar una receta en el selector, automáticamente volvía a seleccionar la primera opción. Cuando se recibía la confirmación del servidor, volvía a ejecutarse el cálculo de recetas, pero la lista de recetas guardadas no se reinicializaba si los ingredientes no habían cambiado. Ahora se asegura que siempre esté inicializada, incluso cuando se procesa una selección.

## 1.11.4
- Fix: el selector de recetas no respondía a clics del mouse. La detección del clic dependía de la variable `hovering` del frame anterior, causando desajuste temporal. Ahora el hit-testing se realiza directamente en el evento de mouse, registrando inmediatamente cuando el jugador hace clic en una variante.

## 1.11.3
- Fix: el selector de recetas parpadeaba y desaparecía al cambiar ingredientes. El cliente recalculaba recetas cada frame, pero el servidor enviaba la lista de forma asincrónica, causando una condición de carrera que limpiaba el cache. Ahora el paquete de sincronización incluye los inputs procesados, evitando que el cliente recalcule innecesariamente hasta que cambien los ingredientes nuevamente.

## 1.11.2
- Fix: el selector de recetas desaparecía cuando había múltiples recetas válidas. El selector se renderizaba en una grilla fija que excedía los límites visibles de la pantalla de crafting. Ahora calcula dinámicamente el número de columnas basándose en el espacio disponible y reposiciona la grilla para mantenerse dentro de los límites visibles, funcionando correctamente con 2-16+ recetas.

## 1.11.1
- Fix: la prevención de mobs (`spawnSchematic.preventMobSpawns`) solo cubría el rango de altura exacto del esquema (`minPos.getY()`-`maxPos.getY()`). Cuando el esquema flota sobre el terreno (`spawnSchematic.surfaceOffset`), el suelo natural bajo la estructura —mismo footprint X/Z, oscuro de noche— quedaba fuera de ese rango y seguían apareciendo mobs ahí. Nuevo `isWithinMobSpawnColumn()` protege toda la columna, desde el fondo del mundo hasta el techo del esquema; la protección de romper/colocar bloques y explosiones sigue usando `isWithinBounds()` sin cambios.

## 1.11.0
- Breaking change: el **esquema de spawn ya no va empaquetado en el JAR**. El mod ya no extrae ningún esquema por defecto; ahora se lee siempre de `<game-dir>/utility_core/spawn_schem/schematic_spawn.schem`. Si el archivo no existe, la feature se omite con un error en el log. Debes colocar tu propio `.schem` antes de crear/regenerar el mundo. (De paso se quita el payload binario grande del JAR del mod.)
- Nueva feature: **comando de protección del spawn**. `/utilitycore spawnprotection on|off|status` activa/desactiva en caliente la protección de construcción de la zona del esquema (romper/colocar bloques y explosiones), para construir temporalmente dentro y luego reactivarla. El estado se persiste en la config.
- Nueva feature: **mensaje de zona protegida**. Al intentar romper/colocar dentro de la zona, el jugador recibe un mensaje indicando que el área está protegida y cuántos bloques debe moverse para salir de ella.
- Nueva config: `spawnSchematic.protectionEnabled` (default true) — protección de construcción de la zona; se cambia en caliente con `/utilitycore spawnprotection`.

## 1.10.0
- Breaking change: se elimina la opción `chunkGen.keepAlive` (era poco fiable: no encontraba el campo de pausa del servidor en todas las versiones). A partir de ahora, para que ChunkGen funcione con el servidor vacío hay que desactivar la pausa por inactividad poniendo `pause-when-empty-seconds=-1` en `server.properties` (sin esto, el servidor se pausa a los 60s y la generación se congela).
- Cambio de límite: `chunkGen.chunksPerTick` ahora tiene un **tope duro de 100 aplicado en código** (rango configurable 1-100; el tope anterior era 300). Generar a 300 chunks/tick dejaba el servidor a TPS casi nulos durante el pregen, congelando los logins y la descarga del mundo. Default 100. Recomendado 1-8 para que el servidor siga respondiendo.

## 1.9.0
- Nueva feature: **sin mobs en la zona de spawn**. `spawnSchematic.preventMobSpawns` (default true) impide la aparición natural de mobs dentro de los límites del esquema de spawn (`MobSpawnEvent.SpawnPlacementCheck`, solo razones `NATURAL`; spawn eggs y comandos siguen funcionando). Útil cuando el esquema no tiene iluminación. La zona ya era inpicable/incolocable y a prueba de explosiones desde la protección del spawn.
- Nueva feature: **Server Rules Manager**. Archivo `utility_core/server_rules.json` con la lista de gamerules a forzar (p. ej. `"players_sleeping_percentage": 50`). En el arranque del servidor cada regla se compara con su valor actual y **solo se aplica si difiere** (vía API de `GameRules`, sin comandos); si ya coincide no se toca. Comandos: `/utilitycore rules apply` y `/utilitycore rules status`. Si quitas una regla del archivo, el servidor la deja como esté.
- Fix: ChunkGen **reiniciaba en bucle** al alcanzar `chunkGen.maxRadius` (terminaba y volvía a empezar a regenerar lo mismo para siempre). Ahora al completar todas las dimensiones se persiste un estado `completed` y no se reanuda; para regenerar hay que usar `/utilitycore chunkgen reset`.
- Fix: keepAlive no encontraba el campo de pausa del servidor en 26.2 (está en nanosegundos: `nextTickTimeNanos`). Ahora lo localiza por nombre con la unidad correcta (con fallback al antiguo `nextTickTick` en milisegundos), de modo que el servidor vacío no se pausa a los 60s y el ciclo de trabajo no se congela.

## 1.8.0
- Nueva feature: ChunkGen con **ciclo de trabajo por bloques**. Ahora la generación automática de chunks trabaja en bloques de tiempo real: `chunkGen.loadSeconds` (default 600 = 10 min) cargando chunks y `chunkGen.restSeconds` (default 300 = 5 min) de descanso sin generar, para dar respiros periódicos al servidor. El ciclo se mide por tiempo real (no por ticks), porque durante la generación el servidor va a TPS bajos.
- Mejora: el ChunkGen ahora **pausa sí o sí cuando entra un jugador**. Antes solo miraba `server.getPlayerCount()`, que no cuenta al jugador hasta que termina el login; ahora detecta la conexión en cuanto empieza el *handshake* login/configuración (vía `ServerLoginPacketListenerImpl`/`ServerConfigurationPacketListenerImpl`) y aborta la generación incluso a mitad de un lote (`chunksPerTick`) de chunks. `onPlayerJoin` también fuerza la pausa directamente.

## 1.7.0
- Nueva feature: Data Pack Folder. Carga automáticamente todos los datapacks (`.zip` o carpeta con `pack.mcmeta`) situados en `<game-dir>/datapacks` en todos los mundos, como el mod Global Packs. Los packs se registran con `required=true`, así que quedan siempre activos sin activarlos mundo a mundo. Funciona en servidores dedicados y en single-player. Config: `dataPackFolder.enabled` (default false) y `dataPackFolder.path` (default `datapacks`).

## 1.6.1
- Fix: bloques incorrectos al pegar esquemas legacy `.schematic`. `LegacyBlockMap` pasaba el metadata completo a los índices de variante sin enmascarar los bits — troncos/log2 (bits de eje), losas (bit de mitad superior), plantas dobles (bit de mitad), huevos de monstruo y cabezas (bits altos) resolvían al bloque equivocado (p. ej. una losa de piedra salía como cuarzo, o un tronco de roble como jungla). Ahora el índice de variante se enmascara correctamente (`data & 7` / `& 3` / `& 1`).

## 1.6.0
- Nueva feature: el esquema de spawn por defecto es ahora un lobby que flota **70 bloques por encima del terreno** (`spawnSchematic.surfaceOffset` default 0 → 70). La rama 26.1.2 también extrae ahora el esquema empaquetado automáticamente cuando falta el archivo.
- Mejora: el lector ahora auto-detecta y parsea el formato legacy WorldEdit/MCEdit `.schematic` (`Materials=Alpha`: arrays `Blocks`/`Data`/`AddBlocks` + `TileEntities`), además de Sponge v2/v3. Nueva `LegacyBlockMap` traduce los IDs numéricos pre-1.13 + metadata a `BlockState` modernos (colores, losas, escaleras, troncos, raíles, etc.).

## 1.5.1
- Fix: `MixinTBScreen` fallaba en el cliente (rompía el fix de escala de GUI de Tombstone). Inyectaba en `Screen.init(Minecraft, int, int)`, método que ya no existe en esta versión de Minecraft — el punto real es el método `final` `init(int, int)`. La inyección ahora targetea `init(II)V` (se ejecuta en todas las pantallas y en resize), de modo que el fix vuelve a aplicarse.

## 1.5.0
- Fix: Spawn Schematic se pegaba en Y=-64 al crear un mundo nuevo. El chunk bajo la zona de spawn aún no estaba generado al calcular la altura, así que el heightmap devolvía el mínimo de construcción (-64). Ahora el mod fuerza la generación de todos los chunks bajo la huella del esquema antes de calcular la colocación.
- Mejora: nueva configuración de altura para el esquema de spawn:
  - `spawnSchematic.heightMode` (default `SURFACE`): alinea la base del esquema con el bloque de suelo más alto bajo su huella, asentándolo en el terreno.
  - `spawnSchematic.surfaceOffset` (default 0): bloques extra sobre el suelo en modo `SURFACE`. 0 = a ras del suelo, positivo = elevado, negativo = enterrado.
  - `spawnSchematic.fixedY` (default 64): coordenada Y absoluta cuando `heightMode=FIXED`.
- Mejora: en modo `SURFACE` se usa la superficie más alta bajo toda la huella del esquema (no una sola columna), evitando que quede enterrado en terrenos irregulares.

## 1.4.0
- Mejora: Spawn Schematic ahora se ancla en las coordenadas del mundo X=0, Z=0 (antes se centraba en el spawn vanilla), con la altura calculada del terreno en ese punto. La ruta del esquema externo cambia a `<game-dir>/utility_core/spawn_schem/schematic_spawn.schem` (mismo patrón de carpeta que ChunkGen). El mod incluye un esquema de ejemplo empaquetado que se extrae automáticamente en el primer arranque si el archivo no existe, de modo que la feature funciona "out of the box". Para usar un esquema propio, basta con sustituir `schematic_spawn.schem` por el suyo (mismo nombre) antes de borrar/regenerar el mundo.

## 1.3.1
- Fix crítico: v1.3.0 crasheaba el cliente al arrancar (`MixinTransformerError` / `InjectionError` en `MixinHud`). El target del `@ModifyArg` tenía el descriptor de retorno mal (`(FF)V` en vez de `(FF)Lorg/joml/Matrix3x2f;`), por lo que el injection point nunca encontraba el método real y Mixin lo trataba como error fatal. Verificado contra el bytecode real de `Hud.class` con `javap`.

## 1.3.0
- Mejora: los títulos vanilla (bioma/dimensión, `/title`, etc.) ahora se renderizan más arriba, fuera del área central de la pantalla. Nueva opción `titleVerticalOffset` (default: 75px, rango -200..200, positivo = hacia arriba) para ajustar el desplazamiento vertical desde el centro.

## 1.2.0
- Nueva feature: Spawn Schematic (opt-in). Coloca un esquema .schem de WorldEdit/FAWE al crear un mundo nuevo, protege el área permanentemente y establece el punto de aparición dentro de la estructura. Configurable mediante `enableSpawnSchematic` (default: false). El archivo .schem debe colocarse en `<game-dir>/schematics/schematic_spawn.schem`.
  > **IMPORTANTE:** Solo funciona en mundos nuevos. Para aplicar a un mundo existente, el operador debe eliminar la carpeta del mundo primero.

## 1.1.0
- Nueva feature: títulos de bioma/dimensión. Muestra un título vanilla al entrar en un bioma o dimensión nuevos (`enableBiomeDimensionTitles`, default true). Lógica portada de [Traveler's Titles](https://www.curseforge.com/minecraft/mc-mods/travelers-titles) de YUNGNICKYOUNG (LGPLv3), reimplementada sobre el sistema de títulos vanilla (`Hud.setTitle/setSubtitle/setTimes`) en vez de un renderer propio, ya que la API de GUI cambió sustancialmente en 26.2.

## 1.0.0
- Release estable. Puerto a 26.2 confirmado funcionando en servidor real. Sin cambios de código respecto a 0.0.0-beta.1.

## 0.0.0-beta.1
- Puerto completo a Minecraft 26.2 / NeoForge 26.2.0.32-beta desde la rama `26.1.2` (v1.1.7). Todas las clases fuente compilan sin cambios de API entre ambas versiones. Incluye:
  - Selector de recetas en conflicto (recipe selector): red, attachments, PolymorphApi, RecipeFinder
  - Fix de daño negativo (`MixinDamageContainer`)
  - Compatibilidad con Corail Tombstone: fix de escala de GUI, fix de NBT en ítems por `/give`, supresor de errores de mixin
  - Compat OutpostZero: cap de daño de infección a 10000
  - ChunkGen: pregeneración automática de chunks en espiral, multi-dimensión, persistencia de progreso
- No se incluye el respawn automático del Ender Dragon (retirado permanentemente en la rama 26.1.2 v1.1.7 por resultados visuales incorrectos con YUNG's Better End Island).---

## [1.11.2] - 2026-08-05

### Change

- **Recompilado contra NeoForge `26.2.0.37-beta`**: bump de `neo_version` en `gradle.properties` (`26.2.0.32-beta` -> `26.2.0.37-beta`). Verificado con `runServer` (arranque sin errores).

## [ility Core — Registro de cambios

## 1.11.1
- Fix: la prevención de mobs (`spawnSchematic.preventMobSpawns`) solo cubría el rango de altura exacto del esquema (`minPos.getY()`-`maxPos.getY()`). Cuando el esquema flota sobre el terreno (`spawnSchematic.surfaceOffset`), el suelo natural bajo la estructura —mismo footprint X/Z, oscuro de noche— quedaba fuera de ese rango y seguían apareciendo mobs ahí. Nuevo `isWithinMobSpawnColumn()` protege toda la columna, desde el fondo del mundo hasta el techo del esquema; la protección de romper/colocar bloques y explosiones sigue usando `isWithinBounds()` sin cambios.

## 1.11.0
- Breaking change: el **esquema de spawn ya no va empaquetado en el JAR**. El mod ya no extrae ningún esquema por defecto; ahora se lee siempre de `<game-dir>/utility_core/spawn_schem/schematic_spawn.schem`. Si el archivo no existe, la feature se omite con un error en el log. Debes colocar tu propio `.schem` antes de crear/regenerar el mundo. (De paso se quita el payload binario grande del JAR del mod.)
- Nueva feature: **comando de protección del spawn**. `/utilitycore spawnprotection on|off|status` activa/desactiva en caliente la protección de construcción de la zona del esquema (romper/colocar bloques y explosiones), para construir temporalmente dentro y luego reactivarla. El estado se persiste en la config.
- Nueva feature: **mensaje de zona protegida**. Al intentar romper/colocar dentro de la zona, el jugador recibe un mensaje indicando que el área está protegida y cuántos bloques debe moverse para salir de ella.
- Nueva config: `spawnSchematic.protectionEnabled` (default true) — protección de construcción de la zona; se cambia en caliente con `/utilitycore spawnprotection`.

## 1.10.0
- Breaking change: se elimina la opción `chunkGen.keepAlive` (era poco fiable: no encontraba el campo de pausa del servidor en todas las versiones). A partir de ahora, para que ChunkGen funcione con el servidor vacío hay que desactivar la pausa por inactividad poniendo `pause-when-empty-seconds=-1` en `server.properties` (sin esto, el servidor se pausa a los 60s y la generación se congela).
- Cambio de límite: `chunkGen.chunksPerTick` ahora tiene un **tope duro de 100 aplicado en código** (rango configurable 1-100; el tope anterior era 300). Generar a 300 chunks/tick dejaba el servidor a TPS casi nulos durante el pregen, congelando los logins y la descarga del mundo. Default 100. Recomendado 1-8 para que el servidor siga respondiendo.

## 1.9.0
- Nueva feature: **sin mobs en la zona de spawn**. `spawnSchematic.preventMobSpawns` (default true) impide la aparición natural de mobs dentro de los límites del esquema de spawn (`MobSpawnEvent.SpawnPlacementCheck`, solo razones `NATURAL`; spawn eggs y comandos siguen funcionando). Útil cuando el esquema no tiene iluminación. La zona ya era inpicable/incolocable y a prueba de explosiones desde la protección del spawn.
- Nueva feature: **Server Rules Manager**. Archivo `utility_core/server_rules.json` con la lista de gamerules a forzar (p. ej. `"players_sleeping_percentage": 50`). En el arranque del servidor cada regla se compara con su valor actual y **solo se aplica si difiere** (vía API de `GameRules`, sin comandos); si ya coincide no se toca. Comandos: `/utilitycore rules apply` y `/utilitycore rules status`. Si quitas una regla del archivo, el servidor la deja como esté.
- Fix: ChunkGen **reiniciaba en bucle** al alcanzar `chunkGen.maxRadius` (terminaba y volvía a empezar a regenerar lo mismo para siempre). Ahora al completar todas las dimensiones se persiste un estado `completed` y no se reanuda; para regenerar hay que usar `/utilitycore chunkgen reset`.
- Fix: keepAlive no encontraba el campo de pausa del servidor en 26.2 (está en nanosegundos: `nextTickTimeNanos`). Ahora lo localiza por nombre con la unidad correcta (con fallback al antiguo `nextTickTick` en milisegundos), de modo que el servidor vacío no se pausa a los 60s y el ciclo de trabajo no se congela.

## 1.8.0
- Nueva feature: ChunkGen con **ciclo de trabajo por bloques**. Ahora la generación automática de chunks trabaja en bloques de tiempo real: `chunkGen.loadSeconds` (default 600 = 10 min) cargando chunks y `chunkGen.restSeconds` (default 300 = 5 min) de descanso sin generar, para dar respiros periódicos al servidor. El ciclo se mide por tiempo real (no por ticks), porque durante la generación el servidor va a TPS bajos.
- Mejora: el ChunkGen ahora **pausa sí o sí cuando entra un jugador**. Antes solo miraba `server.getPlayerCount()`, que no cuenta al jugador hasta que termina el login; ahora detecta la conexión en cuanto empieza el *handshake* login/configuración (vía `ServerLoginPacketListenerImpl`/`ServerConfigurationPacketListenerImpl`) y aborta la generación incluso a mitad de un lote (`chunksPerTick`) de chunks. `onPlayerJoin` también fuerza la pausa directamente.

## 1.7.0
- Nueva feature: Data Pack Folder. Carga automáticamente todos los datapacks (`.zip` o carpeta con `pack.mcmeta`) situados en `<game-dir>/datapacks` en todos los mundos, como el mod Global Packs. Los packs se registran con `required=true`, así que quedan siempre activos sin activarlos mundo a mundo. Funciona en servidores dedicados y en single-player. Config: `dataPackFolder.enabled` (default false) y `dataPackFolder.path` (default `datapacks`).

## 1.6.1
- Fix: bloques incorrectos al pegar esquemas legacy `.schematic`. `LegacyBlockMap` pasaba el metadata completo a los índices de variante sin enmascarar los bits — troncos/log2 (bits de eje), losas (bit de mitad superior), plantas dobles (bit de mitad), huevos de monstruo y cabezas (bits altos) resolvían al bloque equivocado (p. ej. una losa de piedra salía como cuarzo, o un tronco de roble como jungla). Ahora el índice de variante se enmascara correctamente (`data & 7` / `& 3` / `& 1`).

## 1.6.0
- Nueva feature: el esquema de spawn por defecto es ahora un lobby que flota **70 bloques por encima del terreno** (`spawnSchematic.surfaceOffset` default 0 → 70). La rama 26.1.2 también extrae ahora el esquema empaquetado automáticamente cuando falta el archivo.
- Mejora: el lector ahora auto-detecta y parsea el formato legacy WorldEdit/MCEdit `.schematic` (`Materials=Alpha`: arrays `Blocks`/`Data`/`AddBlocks` + `TileEntities`), además de Sponge v2/v3. Nueva `LegacyBlockMap` traduce los IDs numéricos pre-1.13 + metadata a `BlockState` modernos (colores, losas, escaleras, troncos, raíles, etc.).

## 1.5.1
- Fix: `MixinTBScreen` fallaba en el cliente (rompía el fix de escala de GUI de Tombstone). Inyectaba en `Screen.init(Minecraft, int, int)`, método que ya no existe en esta versión de Minecraft — el punto real es el método `final` `init(int, int)`. La inyección ahora targetea `init(II)V` (se ejecuta en todas las pantallas y en resize), de modo que el fix vuelve a aplicarse.

## 1.5.0
- Fix: Spawn Schematic se pegaba en Y=-64 al crear un mundo nuevo. El chunk bajo la zona de spawn aún no estaba generado al calcular la altura, así que el heightmap devolvía el mínimo de construcción (-64). Ahora el mod fuerza la generación de todos los chunks bajo la huella del esquema antes de calcular la colocación.
- Mejora: nueva configuración de altura para el esquema de spawn:
  - `spawnSchematic.heightMode` (default `SURFACE`): alinea la base del esquema con el bloque de suelo más alto bajo su huella, asentándolo en el terreno.
  - `spawnSchematic.surfaceOffset` (default 0): bloques extra sobre el suelo en modo `SURFACE`. 0 = a ras del suelo, positivo = elevado, negativo = enterrado.
  - `spawnSchematic.fixedY` (default 64): coordenada Y absoluta cuando `heightMode=FIXED`.
- Mejora: en modo `SURFACE` se usa la superficie más alta bajo toda la huella del esquema (no una sola columna), evitando que quede enterrado en terrenos irregulares.

## 1.4.0
- Mejora: Spawn Schematic ahora se ancla en las coordenadas del mundo X=0, Z=0 (antes se centraba en el spawn vanilla), con la altura calculada del terreno en ese punto. La ruta del esquema externo cambia a `<game-dir>/utility_core/spawn_schem/schematic_spawn.schem` (mismo patrón de carpeta que ChunkGen). El mod incluye un esquema de ejemplo empaquetado que se extrae automáticamente en el primer arranque si el archivo no existe, de modo que la feature funciona "out of the box". Para usar un esquema propio, basta con sustituir `schematic_spawn.schem` por el suyo (mismo nombre) antes de borrar/regenerar el mundo.

## 1.3.1
- Fix crítico: v1.3.0 crasheaba el cliente al arrancar (`MixinTransformerError` / `InjectionError` en `MixinHud`). El target del `@ModifyArg` tenía el descriptor de retorno mal (`(FF)V` en vez de `(FF)Lorg/joml/Matrix3x2f;`), por lo que el injection point nunca encontraba el método real y Mixin lo trataba como error fatal. Verificado contra el bytecode real de `Hud.class` con `javap`.

## 1.3.0
- Mejora: los títulos vanilla (bioma/dimensión, `/title`, etc.) ahora se renderizan más arriba, fuera del área central de la pantalla. Nueva opción `titleVerticalOffset` (default: 75px, rango -200..200, positivo = hacia arriba) para ajustar el desplazamiento vertical desde el centro.

## 1.2.0
- Nueva feature: Spawn Schematic (opt-in). Coloca un esquema .schem de WorldEdit/FAWE al crear un mundo nuevo, protege el área permanentemente y establece el punto de aparición dentro de la estructura. Configurable mediante `enableSpawnSchematic` (default: false). El archivo .schem debe colocarse en `<game-dir>/schematics/schematic_spawn.schem`.
  > **IMPORTANTE:** Solo funciona en mundos nuevos. Para aplicar a un mundo existente, el operador debe eliminar la carpeta del mundo primero.

## 1.1.0
- Nueva feature: títulos de bioma/dimensión. Muestra un título vanilla al entrar en un bioma o dimensión nuevos (`enableBiomeDimensionTitles`, default true). Lógica portada de [Traveler's Titles](https://www.curseforge.com/minecraft/mc-mods/travelers-titles) de YUNGNICKYOUNG (LGPLv3), reimplementada sobre el sistema de títulos vanilla (`Hud.setTitle/setSubtitle/setTimes`) en vez de un renderer propio, ya que la API de GUI cambió sustancialmente en 26.2.

## 1.0.0
- Release estable. Puerto a 26.2 confirmado funcionando en servidor real. Sin cambios de código respecto a 0.0.0-beta.1.

## 0.0.0-beta.1
- Puerto completo a Minecraft 26.2 / NeoForge 26.2.0.32-beta desde la rama `26.1.2` (v1.1.7). Todas las clases fuente compilan sin cambios de API entre ambas versiones. Incluye:
  - Selector de recetas en conflicto (recipe selector): red, attachments, PolymorphApi, RecipeFinder
  - Fix de daño negativo (`MixinDamageContainer`)
  - Compatibilidad con Corail Tombstone: fix de escala de GUI, fix de NBT en ítems por `/give`, supresor de errores de mixin
  - Compat OutpostZero: cap de daño de infección a 10000
  - ChunkGen: pregeneración automática de chunks en espiral, multi-dimensión, persistencia de progreso
- No se incluye el respawn automático del Ender Dragon (retirado permanentemente en la rama 26.1.2 v1.1.7 por resultados visuales incorrectos con YUNG's Better End Island).
