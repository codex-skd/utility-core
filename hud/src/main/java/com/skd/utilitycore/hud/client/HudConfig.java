package com.skd.utilitycore.hud.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HudConfig {
    public static boolean visible = true;
    public static boolean neverShow = false;
    public static int x = 10;
    public static int y = 10;
    public static Orientation orientation = Orientation.HORIZONTAL;
    public static float scale = 1.0f;

    private static final Path CONFIG_PATH = Minecraft.getInstance().gameDirectory.toPath()
            .resolve("config/utility_core_hud/hud_config.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                String json = Files.readString(CONFIG_PATH);
                HudConfigData data = GSON.fromJson(json, HudConfigData.class);
                if (data != null) {
                    visible = data.visible;
                    neverShow = data.neverShow;
                    x = data.x;
                    y = data.y;
                    orientation = data.orientation;
                    scale = data.scale;
                }
            }
        } catch (IOException e) {
            // ignore and use defaults
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            HudConfigData data = new HudConfigData();
            data.visible = visible;
            data.neverShow = neverShow;
            data.x = x;
            data.y = y;
            data.orientation = orientation;
            data.scale = scale;
            String json = GSON.toJson(data);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            // ignore
        }
    }

    private static class HudConfigData {
        boolean visible;
        boolean neverShow;
        int x;
        int y;
        Orientation orientation;
        float scale;
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
}
