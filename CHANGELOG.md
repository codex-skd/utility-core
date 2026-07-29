# Utility Core — Registro de cambios

## 0.0.0-beta.1
- Puerto completo a Minecraft 26.2 / NeoForge 26.2.0.32-beta desde la rama `26.1.2` (v1.1.7). Todas las clases fuente compilan sin cambios de API entre ambas versiones. Incluye:
  - Selector de recetas en conflicto (recipe selector): red, attachments, PolymorphApi, RecipeFinder
  - Fix de daño negativo (`MixinDamageContainer`)
  - Compatibilidad con Corail Tombstone: fix de escala de GUI, fix de NBT en ítems por `/give`, supresor de errores de mixin
  - Compat OutpostZero: cap de daño de infección a 10000
  - ChunkGen: pregeneración automática de chunks en espiral, multi-dimensión, persistencia de progreso
- No se incluye el respawn automático del Ender Dragon (retirado permanentemente en la rama 26.1.2 v1.1.7 por resultados visuales incorrectos con YUNG's Better End Island).
