# Utility Core — Registro de cambios

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
