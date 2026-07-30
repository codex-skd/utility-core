# Utility Core — Registro de cambios

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
