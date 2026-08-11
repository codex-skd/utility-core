# Utility Core — Registro de cambios
## 2.1.0-beta.1
- Feature: se agregó la asistencia de puentear (Bridging Assist) al módulo qol, portada desde BridgingMod.
- Feature: se agregaron los puntos de entrada @Mod faltantes en admin y fixes, permitiendo que cada subproyecto genere su propio archivo .toml de configuración y funcione como un mod independiente.
- Feature: se eliminó el archivo Config.java duplicado y el META-INF/neoforge.mods.toml huérfano directamente bajo la raíz del repositorio.
- Feature: se actualizaron los archivos de mezcla (mixins) para incluir el renderizado de la cruz y el contorno de puentear, así como la lógica de colocación.
- Feature: se agregó soporte opcional para DankStorage y Sable mediante verificaciones suaves (sin dependencias duras).
- Feature: se agregó una tecla de enlace para activar/desactivar la asistencia de puentear (predeterminada: tecla coma).
## 2.0.0-beta.2
- Fix: se agregaron los puntos de entrada @Mod faltantes en los subproyectos admin y fixes, permitiendo que cada módulo genere su propio archivo .toml de configuración y funcione como un mod independiente real.
- Fix: se eliminó el archivo Config.java duplicado y obsoleto del proyecto raíz.
- Fix: se eliminó el archivo META-INF/neoforge.mods.toml huérfano directamente bajo la raíz del repositorio.
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