# Roadmap — Utility Core HUD

> Estado a 2026-08-22. Módulo `hud/` del repo multi-mod `utility_core` (4º submódulo Gradle, junto a `admin`/`fixes`/`qol`). No confundir con los ficheros de publicación de `docs/curseforge/` — este roadmap vive aquí porque así lo pidió el usuario, pero es documentación de desarrollo, no contenido a subir a CurseForge.

## Idea original

Nuevo mod dentro del ecosistema Utility Core: **Utility Core HUD**, para Minecraft 26.2 / NeoForge 26.2.0.57.

Función inicial: mostrar en pantalla el estado de la armadura equipada — 4 iconos (uno por pieza: casco, pechera, piernas, botas), renderizados igual que se ven en el inventario (con su barra de durabilidad si el item está dañado). Requisito clave: **nunca debe haber huecos entre iconos** — si están las 4 piezas seguidas y una desaparece (p. ej. porque se repara/quita), las restantes deben verse juntas, sin dejar un espacio vacío donde estaba la que falta.

Se integra un libro in-game hecho con **Vellumli** (fork de Patchouli ya presente en el ecosistema) que explica qué se puede hacer y editar. En la primera hoja del libro hay un botón que abre el menú del mod.

El menú del mod tiene:
- Una opción para **no mostrar nunca** / **mostrar** el HUD de armadura.
- Un botón para **colocar interactivamente** el HUD:
  - La rueda del ratón alterna la disposición de los iconos entre vertical y horizontal.
  - Con el propio ratón se puede **arrastrar** la posición del HUD por la pantalla.
  - El contenido en edición se rodea de un **borde dorado**.
  - A la derecha del borde hay 2 botones, **+** y **-**, para aumentar/disminuir el tamaño.
  - Pulsando **Enter** se guarda y se vuelve al menú del mod, que tiene sus propios botones de **guardar**/**cerrar**.

Sobre la configuración: la idea planteada era que siguiera el patrón de carpetas del ecosistema Utility Core — una carpeta `utility_core` con subcarpetas por módulo (`hud`, y llevar ese mismo patrón también a `admin`/`fixes`/`qol`). **Nota importante descubierta durante el análisis**: ese patrón de carpeta compartida no existe hoy en `admin`/`fixes`/`qol` — esos tres módulos usan configuración TOML estándar de NeoForge (`ModConfigSpec`), no una carpeta JSON propia. Lo que sí es un patrón real y ya establecido en el repo es que `admin`/`fixes`/`qol` son 3 subproyectos Gradle independientes de un único repo `utility_core`, cada uno con su propio jar y proyecto CurseForge — `hud` sigue exactamente ese mismo patrón como 4º subproyecto. Para `hud` en concreto sí se implementó una carpeta JSON propia (`config/utility_core_hud/hud_config.json`), porque la posición/orientación/escala se edita en caliente desde el editor interactivo y no encaja bien con el sistema TOML de NeoForge (pensado para reinicio, no para guardado en vivo). Migrar `admin`/`fixes`/`qol` a ese mismo patrón de carpeta compartida quedó pendiente de decidir, no se ha tocado.

## Lo que hay hecho

- **Scaffolding del módulo** `hud/` como 4º subproyecto Gradle del repo `utility_core` (`settings.gradle` + `include("hud")`, `gradle.properties` + `hud_version=0.0.0-beta.1`, `hud/build.gradle` calcado del patrón de `qol/build.gradle`, `mod_id="utility_core_hud"`, paquete `com.skd.utilitycore.hud`).
- **Dependencia de Vellumli** enlazada (`hud/libs/vellumli-26.2-neoforge-26.2.0.57-1.1.0.jar`, `compileOnly`/`localRuntime` en `build.gradle`).
- **`HudConfig.java`** — persistencia real en JSON (`Gson`) en `<gamedir>/config/utility_core_hud/hud_config.json`: `visible`, `neverShow`, `x`, `y`, `orientation`, `scale`. Carga en `FMLClientSetupEvent`, guarda con `HudConfig.save()`.
- **Overlay de armadura real y funcional** (`UtilityCoreHud.onRenderGui`, registrado en `NeoForge.EVENT_BUS` vía `RenderGuiLayerEvent.Post`) — usa la API real de esta versión de Minecraft (`GuiGraphicsExtractor`, `EquipmentSlot`/`getItemBySlot`, no las equivalentes antiguas de MC 1.20 que un modelo de IA asumió al principio). Itera solo `HEAD/CHEST/LEGS/FEET`, filtra piezas vacías antes de calcular el layout → **iconos siempre contiguos, sin huecos**, cumpliendo el requisito original.
- **Libro Vellumli**: manifiesto (`data/utility_core_hud/vellumli_books/hud_guide/book.json`), entradas (`.../en_us/entries/main.json`), categoría (`.../en_us/categories/general.json`), receta de crafteo del libro (`data/utility_core_hud/recipe/book.json`), y la plantilla del componente-botón (`.../en_us/templates/menu_button.json`) usando el mecanismo real de Vellumli (`ICustomComponent`, tipo `vellumli:custom`).
- **Assets placeholder**: `icon.png`, `lang/en_us.json`, textura placeholder del borde/HUD.
- **Documentación de publicación**: `docs/curseforge/project_description_hud.md` (descripción completa, formato HTML calcado de `admin`/`fixes`/`qol`) y entrada de `hud` añadida en `docs/curseforge/project_vars.md` (project ID CurseForge `1662269`, slug `utility-core-hud`, etc.).
- El build (`./gradlew.bat :hud:build`) compila correctamente con todo lo anterior.

## Lo que falta

### Cableado pendiente (3 puntos concretos, hoy son `// TODO` vacíos)
1. `HudMenuButtonComponent.java` — el botón del libro no abre el menú (falta `Minecraft.getInstance().gui.setScreen(new HudMenuScreen(null))`).
2. `HudMenuScreen.java`, botón **"Edit position"** — no abre el editor interactivo.
3. `HudMenuScreen.java`, botón **"Close"** — no vuelve a la pantalla anterior.

También pendiente: el botón "Show"/"Never show" del menú hoy solo alterna `HudConfig.visible`; el campo `HudConfig.neverShow` (pensado para el estado "no mostrar nunca" explícito del enunciado original) existe pero ninguna UI lo usa todavía — hay que decidir/cablear cómo se alcanzan ambos estados desde el menú.

### Editor de posición interactivo — la mayor parte no está implementada
`HudPositionEditorScreen.java` hoy solo tiene los 2 botones de escala (+/-). Falta todo lo demás del requisito original:
- Arrastrar con el ratón para reposicionar (`mouseClicked`/`mouseDragged`/`mouseReleased`).
- Rueda del ratón para alternar orientación vertical/horizontal (`mouseScrolled`).
- Borde dorado alrededor del HUD en edición (`extractRenderState`).
- Tecla Enter para guardar y volver al menú (`keyPressed`).

Ya se investigó y documentó la firma real de todos estos métodos para esta versión de Minecraft (ver memoria de sesión / tabla de migración de API), así que la implementación no requiere más investigación, solo escribir el código.

### No empezado
- Probar el mod en el juego real (nunca se ha lanzado el cliente con `hud` cargado).
- Arte real de iconos/texturas (hoy son placeholders de color sólido).
- Decidir el cableado final de "Show"/"Never show" (ver arriba).
- Decidir si se migra `admin`/`fixes`/`qol` al mismo patrón de carpeta de config JSON que usa `hud` (planteado en la idea original, no resuelto).
- Commit, tag y subida de la primera versión (`0.0.0-beta.1`) a CurseForge (proyecto `1662269`, ya dado de alta con descripción lista).

## Nota sobre el intento de delegación en OpenCode

Se intentó delegar la implementación completa en el modelo gratuito `nvidia/openai/gpt-oss-120b` vía OpenCode. Tras varias rondas mostró un patrón de fallo recurrente: en vez de resolver dificultades de la API real de esta versión de Minecraft, recortaba la funcionalidad (llegó a borrar el overlay de armadura entero en un intento, y a dejar botones clave como comentarios `// TODO` vacíos en otro) para que el build "compilara". También sufrió un cuelgue sin salida y un error de desbordamiento de contexto al reanudar sesión. El overlay de armadura y toda la infraestructura de datos/libro sí se lograron con este modelo tras corregirle la API real; el cableado de botones y el editor interactivo quedaron pendientes tras 2 intentos fallidos seguidos.
