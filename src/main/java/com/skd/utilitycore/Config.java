package com.skd.utilitycore;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_CRAFTING_RECIPE_SELECTOR = BUILDER
            .comment("Enable the recipe selector widget in the crafting table GUI when multiple recipes match the same inputs")
            .define("enableCraftingRecipeSelector", true);

    public static final ModConfigSpec.IntValue MAX_RECIPES_DISPLAYED = BUILDER
            .comment("Maximum number of alternative recipes to display in the selector")
            .defineInRange("maxRecipesDisplayed", 16, 1, 64);

    public static final ModConfigSpec.BooleanValue LOG_DETECTED_CONFLICTS = BUILDER
            .comment("Log recipe conflicts to the console for debugging")
            .define("logDetectedConflicts", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
