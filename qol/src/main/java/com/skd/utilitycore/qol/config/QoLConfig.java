package com.skd.utilitycore.qol.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class QoLConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_CRAFTING_RECIPE_SELECTOR = BUILDER
            .comment("EN: Enable the recipe selector widget in the crafting table GUI when multiple recipes match the same inputs",
                     "ES: Activa el selector de recetas en la mesa de crafteo cuando varias recetas coinciden con los mismos ingredientes")
            .define("enableCraftingRecipeSelector", true);

    public static final ModConfigSpec.IntValue MAX_RECIPES_DISPLAYED = BUILDER
            .comment("EN: Maximum number of alternative recipes to display in the selector",
                     "ES: Número máximo de recetas alternativas a mostrar en el selector")
            .defineInRange("maxRecipesDisplayed", 16, 1, 64);

    public static final ModConfigSpec.BooleanValue LOG_DETECTED_CONFLICTS = BUILDER
            .comment("EN: Log recipe conflicts to the console for debugging",
                     "ES: Registra en consola los conflictos de recetas para depuración")
            .define("logDetectedConflicts", false);

    public static final ModConfigSpec.BooleanValue ENABLE_BIOME_DIMENSION_TITLES = BUILDER
            .comment("EN: Shows a title on screen when entering a new biome or dimension.",
                     "ES: Muestra un título en pantalla al entrar en un bioma o dimensión nuevos.")
            .define("enableBiomeDimensionTitles", true);

    public static final ModConfigSpec.IntValue TITLE_VERTICAL_OFFSET = BUILDER
            .comment("EN: Shifts vanilla title/subtitle text vertically from screen center. Positive values move the text up (toward the top of the screen); negative values move it down.",
                     "ES: Desplaza verticalmente el título/subtítulo vanilla desde el centro de la pantalla. Valores positivos mueven el texto hacia arriba; negativos hacia abajo.")
            .defineInRange("titleVerticalOffset", 75, -200, 200);

    public static final ModConfigSpec SPEC = BUILDER.build();
}