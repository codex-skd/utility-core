# Utility Core

Un mod librería para NeoForge (MC 26.1.2) que proporciona utilidades y características compartidas.

## Características

- **Resolución de conflictos de recetas**: Cuando varias recetas de crafteo coinciden con los mismos ingredientes, aparece un selector en la mesa de crafteo. Haz clic en una alternativa para elegir el resultado deseado.
- **Seguridad antidaño negativo**: El daño negativo de interacciones entre mods (ej. Apothic Attributes + Tombstone) se limita a 0, evitando caídas del servidor.
- **Configurable**: Activa/desactiva funciones y configura el máximo de recetas mostradas desde el menú de configuración del juego o `config/utility_core-common.toml`.
- **API para desarrolladores**: `PolymorphApi` para que otros mods se integren con el sistema de selección de recetas.

## Requisitos

- NeoForge 26.1.2+ (cliente y servidor)

## Incompatibilidades conocidas

- **Fast Workbench (fastbench)**: Entra en conflicto con el sistema de selección de recetas. Desinstálalo para usar esta función.

## Compilar

```bash
gradlew build
```

El jar se generará en `build/libs/utility_core-<versión>.jar`.
