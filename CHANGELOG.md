# Utility Core — Registro de cambios

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
