# Utility Core — Registro de cambios

## 1.9.0
- Nueva feature: **sin mobs en la zona de spawn** (portado desde la rama 26.2). `spawnSchematic.preventMobSpawns` (default true) impide la aparición natural de mobs dentro de los límites del esquema de spawn (`MobSpawnEvent.SpawnPlacementCheck`, solo razones `NATURAL`; spawn eggs y comandos siguen funcionando). Útil cuando el esquema no tiene iluminación. La zona ya era inpicable/incolocable y a prueba de explosiones desde la protección del spawn.
- Nueva feature: **Server Rules Manager** (portado desde la rama 26.2). Archivo `utility_core/server_rules.json` con la lista de gamerules a forzar (p. ej. `"players_sleeping_percentage": 50`). En el arranque del servidor cada regla se compara con su valor actual y **solo se aplica si difiere** (vía API de `GameRules`, sin comandos); si ya coincide no se toca. Comandos: `/utilitycore rules apply` y `/utilitycore rules status`. Si quitas una regla del archivo, el servidor la deja como esté.
- Fix: ChunkGen **reiniciaba en bucle** al alcanzar `chunkGen.maxRadius` (terminaba y volvía a empezar a regenerar lo mismo para siempre). Ahora al completar todas las dimensiones se persiste un estado `completed` y no se reanuda; para regenerar hay que usar `/utilitycore chunkgen reset`.
- Fix: keepAlive no encontraba el campo de pausa del servidor (está en nanosegundos en estas versiones: `nextTickTimeNanos`). Ahora lo localiza por nombre con la unidad correcta (con fallback al antiguo `nextTickTick` en milisegundos), de modo que el servidor vacío no se pausa a los 60s y el ciclo de trabajo no se congela.


## 1.8.0
- Nueva feature: ChunkGen con **ciclo de trabajo por bloques** (portado desde la rama 26.2). Ahora la generación automática de chunks trabaja en bloques de tiempo real: `chunkGen.loadSeconds` (default 600 = 10 min) cargando chunks y `chunkGen.restSeconds` (default 300 = 5 min) de descanso sin generar, para dar respiros periódicos al servidor. El ciclo se mide por tiempo real (no por ticks), porque durante la generación el servidor va a TPS bajos.
- Mejora: el ChunkGen ahora **pausa sí o sí cuando entra un jugador**. Antes solo miraba `server.getPlayerCount()`, que no cuenta al jugador hasta que termina el login; ahora detecta la conexión en cuanto empieza el *handshake* login/configuración (vía `ServerLoginPacketListenerImpl`/`ServerConfigurationPacketListenerImpl`) y aborta la generación incluso a mitad de un lote (`chunksPerTick`) de chunks. `onPlayerJoin` también fuerza la pausa directamente.

## 1.7.1
- Nueva feature: títulos de bioma/dimensión, portados desde la rama 26.2 (paridad de features). Muestra un título vanilla al entrar en un bioma o dimensión nuevos (`enableBiomeDimensionTitles`, default true; `titleVerticalOffset`, default 75). Lógica portada de [Traveler's Titles](https://www.curseforge.com/minecraft/mc-mods/travelers-titles) de YUNGNICKYOUNG (LGPLv3).

## 1.7.0
- Nueva feature: Data Pack Folder. Carga automáticamente todos los datapacks (`.zip` o carpeta con `pack.mcmeta`) situados en `<game-dir>/datapacks` en todos los mundos, como el mod Global Packs. Los packs se registran con `required=true`, así que quedan siempre activos sin activarlos mundo a mundo. Funciona en servidores dedicados y en single-player. Config: `dataPackFolder.enabled` (default false) y `dataPackFolder.path` (default `datapacks`).
- Incluye el fix de 26.2 1.6.1 (no publicado en esta rama): `LegacyBlockMap` ahora enmascara los bits de variante de los esquemas legacy `.schematic`.

## 1.6.0
- Nueva feature: el esquema de spawn por defecto es ahora un lobby que flota **70 bloques por encima del terreno** (`spawnSchematic.surfaceOffset` default 0 → 70). Esta rama también extrae ahora el esquema empaquetado automáticamente cuando falta el archivo.
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

## 1.2.0
- Nueva feature: Spawn Schematic (opt-in). Coloca un esquema .schem de WorldEdit/FAWE al crear un mundo nuevo, protege el área permanentemente y establece el punto de aparición dentro de la estructura. Configurable mediante `enableSpawnSchematic` (default: false). El archivo .schem debe colocarse en `<game-dir>/schematics/schematic_spawn.schem`.
  > **IMPORTANTE:** Solo funciona en mundos nuevos. Para aplicar a un mundo existente, el operador debe eliminar la carpeta del mundo primero.

## 1.1.7
- Removed: funcionalidad de respawn automático del Ender Dragon (`EnderDragonRespawnHandler`, config `enableEndDragonRespawn`). El resultado visual con YUNG's Better End Island no era el esperado (cristales flotando sin apoyo real) y no compensaba mantener la lógica. Se retira por completo en lugar de seguir parcheando.

## 1.1.6
- Fixed: el respawn automático del Ender Dragon nunca disparaba `tryRespawn()` porque los cristales se colocaban a distancia 7-8 (piedra base de YUNG BEI) o 2 (versiones anteriores), pero vanilla (`EnderDragonFight#tryRespawn()`) solo detecta cristales exactamente a **distancia 3** de cada punto cardinal respecto al centro del portal. Confirmado descompilando `EnderDragonFight.java` (Minecraft 26.1.2).
- Los cristales ya no dependen de un bloque de soporte (`EndCrystal` no tiene gravedad); se registra en el log si hay suelo sólido debajo para poder verificarlo visualmente.
- Se mantiene la comprobación anti-duplicados de la versión 1.1.5.

## 1.1.5
- Fixed: Dragon Respawn colocaba piedra base inventada cuando la torre real de YUNG BEI estaba a distancia 8 (no 7 exacto), ignorando la piedra base ya generada. Ahora se busca en un rango de distancias (6-9) con tolerancia lateral para encontrar la torre real y nunca se fabrica piedra base nueva.
- Fixed: Se podían colocar cristales duplicados/superpuestos si `onServerStarted` se disparaba varias veces (reinicios sucesivos) antes de completar el respawn. Ahora se comprueba si ya existe un cristal cerca antes de colocar uno nuevo.
- Removed: `placeVanillaCrystals()` (distancia 2) ya no se usa — fabricaba piedra base y cristales falsos que no correspondían a la estructura real de YUNG BEI.

## 1.1.4
- Fixed: Dragon Respawn `tryRespawn()` no detectaba cristales — colocados a dist=7 pero vanilla busca a dist=2. Ahora se colocan en ambas posiciones.

## 1.1.3
- Fixed: ChunkGen no agregaba dimensiones Nether/End si existía estado guardado de Overworld
- Fixed: Dragon respawn fallback coloca cristales también en posición vanilla (dist=2) para que tryRespawn() los detecte
- Fixed: Dragon respawn loguea fallos de colocación de bedrock/cristales

## 1.1.2
- EnderDragonRespawnHandler: logging detallado de cada etapa (portal, escaneo bedrock, tryRespawn, estado del fight, posición de cristales)
- ChunkGen: fix reanudación desde estado guardado (start() con dims pre-cargadas)
- Workflow: actualizado a v1.6.0 (repositorio independiente por versión)
- Limpieza: archivos IDE y cache eliminados del tracking (.eclipse, .gradle, .vscode)

## 1.1.1
- Fixed: ChunkGen no reanuda generación tras cargar estado guardado del JSON — `start()` no activaba `running=true` porque las dimensiones ya existían en el mapa
- Workflow: actualizado a v1.4.0 (organización workspace `utility_core/26.1.2/`)
- Workflow: añadida sección "Organización en el workspace"

## 1.1.0
- ChunkGen: soporte multi-dimensión (Overworld, Nether, End) configurable individualmente
- ChunkGen: keepAlive configurable (evita pausa del servidor mientras genera)
- ChunkGen: límite de chunks por tick aumentado a 300
- Fixed: null-safe saveState path creation

## 1.0.41
- ChunkGen: archivo de estado movido a `utility_core/chunk_pregen/utility_core_chunk_gen.json`

## 1.0.40
- ChunkGen: keepAlive ahora escanea todos los long fields de MinecraftServer para encontrar el tick timer (compatible con cualquier versión)
- ChunkGen: archivo de estado movido a la raíz del servidor (`utility_core_chunk_gen.json`)

## 1.0.39
- Corregido respawn del Ender Dragon con YUNG BEI: coloca bedrock + cristal en la posición exacta donde YUNG busca (dist=7, Y=portalY+1) si no encuentra bedrock existente

## 1.0.38
- Corregido respawn del Ender Dragon con YUNG BEI: escanea niveles Y alrededor del portal para encontrar bedrock a distancia 7

## 1.0.37
- ChunkGen: evita que el servidor se auto-pause mientras genera chunks (resetea `nextTickTick` por reflection)
- ChunkGen: la generación continúa aunque el servidor esté vacío

## 1.0.36
- Corregido respawn del Ender Dragon con YUNG Better End Island: coloca cristales a distancia 7 desde el portal central (YUNG BEI) o distancia 2 desde (0,60,0) (vanilla)
- Detecta la posición exacta del portal vía `EnderDragonFightAccessor.getPortalLocation()` si YUNG está presente

## 1.0.35
- ChunkGen: guardado de estado al apagar el servidor (ServerStoppingEvent)
- ChunkGen: log de progreso al iniciar el servidor
- Corregido versionado tras commit accidental del CI-main en rama production

## 1.0.34
- Corregido respawn del Ender Dragon con YUNG Better End Island: coloca cristales a distancia 7 (BEI) y 2 (vanilla), usa `tryRespawn()` en vez de reflection a API interna de YUNG
- Eliminada dependencia de reflection a `IBetterDragonFight` y `advanceRespawnStage()` — YUNG ahora se integra vía su override natural de `tryRespawn()`

## 1.0.33
- Añadido ChunkGen: generación automática de chunks en espiral desde (0,0)
- Auto-pausa al entrar jugadores, auto-reanudación al irse el último
- Progreso persistente en `config/utility_core_chunk_gen.json`
- Logs de progreso cada 100 chunks
- Comando `/utilitycore chunkgen` con subcomandos status/start/pause/stop/reset
- Config: `chunkGen.enabled`, `chunkGen.chunksPerTick`, `chunkGen.maxRadius`, `chunkGen.runWithPlayers`

## 1.0.32
- Corregido Ender Dragon auto-respawn con YUNG Better End Island: los cristales ahora tienen `setBeamTarget()` como `ItemEndCrystal.useOn()`, necesario para que YUNG los detecte
- Revertidas posiciones a cardinales (N/S/E/W) — diagonales no tienen bedrock en BEI
- Si YUNG presente: coloca cristales con beamTarget + `advanceRespawnStage(START)`
- Si YUNG ausente: coloca cristales + `tryRespawn()` vanilla

## 1.0.25
- Corregido Ender Dragon auto-respawn con YUNG's Better End Island: `tryRespawn()` es no-op con YUNG
- Detecta YUNG via reflexión y llama a `advanceRespawnStage(START)` en su API nativa
- Eliminada colocación manual de cristales (causaba apilamiento en reinicios)
- Eliminada doble llamada a `tryRespawn()` (corrompía máquina de estados)
- Fallback a `tryRespawn()` vanilla si YUNG no está presente

## 1.0.24
- Corregido Ender Dragon auto-respawn: `tryRespawn()` no coloca cristales, solo verifica si existen
- Ahora coloca 12 EndCrystal en posiciones vanilla (radio 3), BEI (radio 7) y YUNG-vanilla (radio 2) para cubrir todos los mods
- Añadida compatibilidad con YUNG's Better End Island (detecta y usa su API de respawn)
- Usa reflexión para acceder a `exitPortalLocation` y colocar cristales

## 1.0.23
- Añadidos toggles de configuración para TODAS las features (Tombstone, OutpostZero, Negative Damage, Ender Dragon)
- Nuevo `EnderDragonRespawnHandler`: reaparece al Ender Dragon automáticamente al iniciar el servidor si fue asesinado previamente (configurable, default: false)
- Todas las descripciones de configuración bilingües EN/ES
- Features de compatibilidad solo se activan si el mod correspondiente está presente Y el toggle está habilitado

## 1.0.22
- Añadida compatibilidad con OutpostZero: prevenida destrucción de armadura por daño de infección masivo
- Nuevo `OutpostZeroCompat` con `LivingDamageEvent.Pre` que limita daño `outpostzero:infection` a 10000
- Sin dependencia de compilación a OutpostZero — solo APIs vanilla/NeoForge

## 1.0.21
- Documentación: actualizado WORKFLOW.md con secciones Ramas y nuevo formato de tags
- Reestructurada rama de desarrollo a `minecraft/26.1.2/neoforge-26.1.2.78/production`
- Tags de CurseForge ahora siguen el formato `<mc-version>-neoforge-<version>`

## 1.0.20
- Reconstrucción limpia para solucionar jar corrupto (sin cambios funcionales respecto a 1.0.19)

## 1.0.19
- Corregido: selector de recetas en la cuadrícula 2x2 del inventario aparecía encima de la cuadrícula en lugar de a la derecha del slot de resultado
- Posición del selector cambiada de `leftPos + 124, topPos + 30` (superponiéndose a la cuadrícula) a `leftPos + 172, topPos + 27` (a la derecha del slot de resultado)
- Corregido: selector en pantalla de inventario se renderizaba detrás de los efectos de estado (buffos/debuffos tapando el selector)
- Punto de inyección cambiado de `extractBackground` a `extractRenderState` para renderizar en la capa superior de la GUI
- Ajuste de alineación Y de -1 píxel

## 1.0.17
- Corregido: el selector de recetas persistía en pantalla tras limpiar la cuadrícula de crafteo o cambiar a una receta sin conflicto
- El servidor ahora limpia la caché de recetas del cliente y `PlayerRecipeData` cuando la cuadrícula tiene ≤1 receta coincidente
- El cliente ahora limpia la caché de recetas al cerrar la pantalla de crafteo/inventario
- Corregido: selector de recetas permanecía visible en la pantalla de inventario después de usarlo una vez

## 1.0.16
- Corregido: Tombstone reinicia la escala de GUI al máximo al abrir sus menús y no la restaura al cerrarlos
- Añadido mixin para `TBScreen.removed()` que restaura la escala de GUI original como red de seguridad al cerrar pantallas inesperadamente

## 1.0.15
- Corregido error de inicio: el mixin `ItemInputMixin` de Tombstone 9.5.6 fallaba en `ItemCombinerScreen` debido a cambios de firma de métodos en MC 1.21.3
- Registrado `TombstoneErrorHandler` mediante `Mixins.registerErrorHandlerClass()` para suprimir el error de inyección del mixin sin que pete el juego

## 1.0.14
- Corregido error de inicio: el mixin `ItemInputMixin` de Tombstone 9.5.6 fallaba en `ItemCombinerScreen` debido a cambios de firma de métodos en MC 1.21.3
- Añadido `TombstoneErrorHandler` implementando `IMixinErrorHandler` para suprimir el error de incompatibilidad

## 0.0.1-beta.6
- Corregido error de inicio: formato del identificador de paquetes corregido (`Identifier.parse` en lugar de `createType`)
- Gestión de clics de ratón movida de mixin a `ScreenEvent.MouseButtonPressed.Pre` de NeoForge
- Eliminada dependencia del procesador de anotaciones Mixin (lo gestiona moddev)

## 0.0.1-beta.5
- Corregido error: método `mouseClicked` no encontrado en `CraftingScreen` (el método es heredado, no está definido en el objetivo)
- Gestión de clics basada en mixin reemplazada por eventos de pantalla de NeoForge
- Gestión de eventos del cliente separada en `PolymorphClientHandler`

## 0.0.1-beta.4
- Corregido error: firma de `mouseClicked` cambiada a `MouseButtonEvent` en Minecraft 26.1.2
- Actualizado objetivo de `@Inject` para la nueva API de entrada

## 0.0.1-beta.3
- Corregido error: campos `leftPos`/`topPos` movidos a `AccessorAbstractContainerScreen` (clase padre)
- Corregido problema de referencias cruzadas en refmap entre clases mixin

## 0.0.1-beta.2
- Corregido error de inicio: referencias cruzadas entre mixins reemplazadas con clase utilitaria `RecipeFinder` compartida

## 0.0.1-beta.1
- Primera versión beta
- Resolución de conflictos de recetas en mesa de crafteo: elegir entre múltiples resultados de crafteo
- Ajustes configurables (activar/desactivar, máximo de recetas mostradas)
- API para desarrolladores para integraciones personalizadas
- Traducciones al inglés
