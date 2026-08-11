package com.skd.utilitycore.admin.rules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

// Enforces a curated list of server game rules from utility_core/server_rules.json:
// { "rules": { "<ruleId>": <value> } }
// Rules are applied only when their current value differs from the configured one,
// so nothing is re-executed when the server already matches the configuration.
public class ServerRulesManager {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path RULES_FILE = Path.of("utility_core", "server_rules.json");

    private Map<String, GameRule<?>> ruleIndex;

    public void apply(MinecraftServer server) {
        JsonObject rules = loadRules();
        if (rules == null) return;

        Map<String, GameRule<?>> index = ruleIndex();
        GameRules gameRules = server.getGameRules();
        for (Map.Entry<String, JsonElement> entry : rules.entrySet()) {
            GameRule<?> rule = index.get(entry.getKey());
            if (rule == null) {
                LOGGER.warn("[ServerRules] Unknown game rule '{}' - skipped", entry.getKey());
                continue;
            }
            applyRule(server, gameRules, rule, entry.getValue().getAsString());
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void applyRule(MinecraftServer server, GameRules gameRules, GameRule rule, String desired) {
        try {
            DataResult<?> parse = rule.deserialize(desired);
            Object value = parse.result().orElse(null);
            if (value == null) {
                LOGGER.warn("[ServerRules] Could not parse value '{}' for rule '{}' - skipped", desired, rule.id());
                return;
            }
            String current = gameRules.getAsString(rule);
            if (current.equals(desired)) return;
            gameRules.set(rule, value, server);
            LOGGER.info("[ServerRules] Applied rule '{}': {} -> {}", rule.id(), current, desired);
        } catch (Exception ex) {
            LOGGER.warn("[ServerRules] Could not apply rule '{}': {}", rule.id(), ex.getMessage());
        }
    }

    public String status(MinecraftServer server) {
        JsonObject rules = loadRules();
        if (rules == null) {
            return "§e[ServerRules] No rules file at utility_core/server_rules.json";
        }
        Map<String, GameRule<?>> index = ruleIndex();
        GameRules gameRules = server.getGameRules();
        StringBuilder sb = new StringBuilder("§a[ServerRules] Configured rules:");
        for (Map.Entry<String, JsonElement> entry : rules.entrySet()) {
            GameRule<?> rule = index.get(entry.getKey());
            if (rule == null) {
                sb.append("\n§7 - §e").append(entry.getKey()).append("§7 = §cunknown rule");
                continue;
            }
            sb.append("\n§7 - §e").append(entry.getKey()).append("§7 = §a")
                    .append(gameRules.getAsString(rule))
                    .append("§7 (config §f").append(entry.getValue().getAsString()).append("§7)");
        }
        return sb.toString();
    }

    private JsonObject loadRules() {
        if (!Files.exists(RULES_FILE)) return null;
        try {
            String json = Files.readString(RULES_FILE);
            JsonObject root = GSON.fromJson(json, JsonObject.class);
            if (root == null || !root.has("rules")) {
                LOGGER.warn("[ServerRules] Invalid rules file, missing 'rules' object");
                return null;
            }
            JsonObject rules = root.getAsJsonObject("rules");
            if (rules == null || rules.isEmpty()) return null;
            return rules;
        } catch (IOException e) {
            LOGGER.warn("[ServerRules] Could not read rules file: {}", e.getMessage());
            return null;
        }
    }

    private Map<String, GameRule<?>> ruleIndex() {
        if (ruleIndex == null) {
            ruleIndex = new HashMap<>();
            BuiltInRegistries.GAME_RULE.stream().forEach(rule -> ruleIndex.put(rule.id(), rule));
        }
        return ruleIndex;
    }
}