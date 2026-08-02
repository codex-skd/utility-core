# Utility Core — Registro de cambios

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
