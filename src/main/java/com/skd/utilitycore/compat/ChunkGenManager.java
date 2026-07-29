package com.skd.utilitycore.compat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import com.skd.utilitycore.Config;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkGenManager {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path STATE_FILE = Path.of("utility_core", "chunk_pregen", "utility_core_chunk_gen.json");
    private static final ResourceKey<Level>[] DIM_KEYS = new ResourceKey[]{Level.OVERWORLD, Level.NETHER, Level.END};
    private static final String[] DIM_NAMES = {"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

    private final Map<String, DimState> dims = new HashMap<>();
    private boolean running = false;
    private boolean paused = false;
    private int dimIndex = 0;
    private int ticksSinceLastLog = 0;

    private static class RootState {
        @SerializedName("dimensions") Map<String, DimState> dimensions = new HashMap<>();
    }

    private static class DimState {
        @SerializedName("chunk_x") int chunkX;
        @SerializedName("chunk_z") int chunkZ;
        @SerializedName("radius") int radius;
        @SerializedName("total_generated") long totalGenerated;
        @SerializedName("dir_x") int dirX = 1;
        @SerializedName("dir_z") int dirZ = 0;
        @SerializedName("segment_len") int segmentLen = 1;
        @SerializedName("steps_in_segment") int stepsInSegment = 0;
        @SerializedName("segments_changed") int segmentsChanged = 0;
        @SerializedName("first_step") boolean firstStep = true;
    }

    private Field tickField;
    private boolean tickFieldSearched = false;

    public ChunkGenManager() {
        loadState();
    }

    public void tick(MinecraftServer server) {
        if (!Config.CHUNK_GEN_ENABLED.get()) return;

        int playerCount = server.getPlayerCount();

        if (!running) {
            if (playerCount == 0) {
                start(server);
            }
            return;
        }

        if (playerCount > 0 && !Config.CHUNK_GEN_RUN_WITH_PLAYERS.get()) {
            if (!paused) {
                paused = true;
                LOGGER.info("[ChunkGen] Player joined, pausing generation");
            }
            return;
        }

        if (paused) {
            paused = false;
            LOGGER.info("[ChunkGen] No more players, resuming generation");
        }

        if (playerCount == 0) {
            keepAlive(server);
        }

        int chunksPerTick = Config.CHUNK_GEN_CHUNKS_PER_TICK.get();
        int maxRadius = Config.CHUNK_GEN_MAX_RADIUS.get();

        for (int i = 0; i < chunksPerTick; i++) {
            String dim = nextDimension();
            if (dim == null) return;

            DimState state = dims.get(dim);
            if (state == null) continue;

            ServerLevel level = null;
            for (int d = 0; d < DIM_NAMES.length; d++) {
                if (DIM_NAMES[d].equals(dim)) {
                    level = server.getLevel(DIM_KEYS[d]);
                    break;
                }
            }
            if (level == null) {
                LOGGER.warn("[ChunkGen] Dimension {} not found, skipping", dim);
                continue;
            }

            if (maxRadius > 0 && state.radius > maxRadius) {
                LOGGER.info("[ChunkGen] Dimension {} reached max radius {}, stopping", dim, maxRadius);
                dims.remove(dim);
                if (dims.isEmpty()) {
                    stopAll();
                    return;
                }
                continue;
            }

            level.getChunk(state.chunkX, state.chunkZ, ChunkStatus.FULL, true);
            state.totalGenerated++;
            moveToNext(state);
            updateRadius(state);

            ticksSinceLastLog++;
            if (ticksSinceLastLog >= 100) {
                ticksSinceLastLog = 0;
                LOGGER.info("[ChunkGen] Generated {} chunks so far. Pos: ({}, {}), radius: {}, dim: {}",
                        totalGenerated(), state.chunkX, state.chunkZ, state.radius, dim);
            }
        }

        saveState();
    }

    private String nextDimension() {
        List<String> active = activeDimensions();
        if (active.isEmpty()) return null;
        dimIndex = dimIndex % active.size();
        return active.get(dimIndex++);
    }

    private List<String> activeDimensions() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, DimState> e : dims.entrySet()) {
            if (e.getValue() != null) list.add(e.getKey());
        }
        return list;
    }

    private void start(MinecraftServer server) {
        boolean[] configs = {
            Config.CHUNK_GEN_DIMENSION_OVERWORLD.get(),
            Config.CHUNK_GEN_DIMENSION_NETHER.get(),
            Config.CHUNK_GEN_DIMENSION_END.get()
        };

        // Add any new dimensions from config that aren't already in saved state
        for (int i = 0; i < 3; i++) {
            if (configs[i] && !dims.containsKey(DIM_NAMES[i])) {
                if (server.getLevel(DIM_KEYS[i]) != null) {
                    DimState s = new DimState();
                    s.dirX = 1; s.dirZ = 0;
                    s.segmentLen = 1;
                    s.firstStep = true;
                    dims.put(DIM_NAMES[i], s);
                }
            }
        }

        if (dims.isEmpty()) {
            running = false;
            return;
        }

        running = true;
        paused = false;
        dimIndex = 0;
        LOGGER.info("[ChunkGen] Auto-starting. Dimensions: {}", String.join(", ", activeDimensions()));
        saveState();
    }

    private void stopAll() {
        running = false;
        paused = false;
        LOGGER.info("[ChunkGen] All dimensions completed");
        saveState();
    }

    public void pause() {
        if (!running) return;
        paused = true;
        LOGGER.info("[ChunkGen] Generation paused");
        saveState();
    }

    public void stop() {
        if (!running) return;
        running = false;
        paused = false;
        LOGGER.info("[ChunkGen] Generation stopped");
        saveState();
    }

    public void reset() {
        dims.clear();
        running = false;
        paused = false;
        LOGGER.info("[ChunkGen] Progress reset for all dimensions");
        saveState();
    }

    public void onPlayerJoin() {
        LOGGER.info("[ChunkGen] Player joined. Total generated: {} chunks", totalGenerated());
    }

    public void onPlayerLeave(MinecraftServer server) {
        LOGGER.info("[ChunkGen] Player left. Total generated: {} chunks", totalGenerated());
    }

    public void onServerStopping() {
        saveState();
        LOGGER.info("[ChunkGen] Server stopping. Saved progress: {} chunks", totalGenerated());
    }

    public boolean isRunning() { return running; }
    public boolean isPaused() { return paused; }
    public long totalGenerated() {
        return dims.values().stream().mapToLong(s -> s.totalGenerated).sum();
    }

    // --- Spiral ---

    private static void moveToNext(DimState s) {
        if (s.firstStep) { s.firstStep = false; return; }
        s.chunkX += s.dirX;
        s.chunkZ += s.dirZ;
        s.stepsInSegment++;
        if (s.stepsInSegment >= s.segmentLen) {
            s.stepsInSegment = 0;
            s.segmentsChanged++;
            int tmp = s.dirX;
            s.dirX = s.dirZ;
            s.dirZ = -tmp;
            if (s.segmentsChanged % 2 == 0) s.segmentLen++;
        }
    }

    private static void updateRadius(DimState s) {
        int r = Math.max(Math.abs(s.chunkX), Math.abs(s.chunkZ));
        if (r > s.radius) s.radius = r;
    }

    // --- Keep Alive ---

    private void keepAlive(MinecraftServer server) {
        if (!Config.CHUNK_GEN_KEEP_ALIVE.get()) return;
        if (!tickFieldSearched) {
            tickFieldSearched = true;
            long now = Util.getMillis();
            try {
                Field f = MinecraftServer.class.getDeclaredField("nextTickTick");
                f.setAccessible(true);
                long val = f.getLong(server);
                if (val >= now - 100_000 && val <= now + 100_000) {
                    tickField = f;
                    LOGGER.info("[ChunkGen] Found tick field: nextTickTick = {}", val);
                }
            } catch (Exception ignored) {}
            if (tickField == null) {
                for (Field f : MinecraftServer.class.getDeclaredFields()) {
                    if (f.getType() != long.class) continue;
                    try {
                        f.setAccessible(true);
                        long val = f.getLong(server);
                        if (val >= now - 100_000 && val <= now + 100_000) {
                            tickField = f;
                            LOGGER.info("[ChunkGen] Found tick field (fallback): {} = {}", f.getName(), val);
                            break;
                        }
                    } catch (Exception ignored) {}
                }
            }
            if (tickField == null) {
                LOGGER.warn("[ChunkGen] No tick field found. Server may pause after 60s idle.");
            }
        }
        if (tickField != null) {
            try { tickField.setLong(server, Util.getMillis() + 50L); }
            catch (Exception ignored) {}
        }
    }

    // --- Persistence ---

    private void loadState() {
        if (Files.notExists(STATE_FILE)) return;
        try {
            String json = Files.readString(STATE_FILE);
            RootState root = GSON.fromJson(json, RootState.class);
            if (root != null && root.dimensions != null) {
                dims.putAll(root.dimensions);
                LOGGER.info("[ChunkGen] Loaded state: {} chunks across {} dimensions",
                        totalGenerated(), dims.size());
            }
        } catch (IOException e) {
            LOGGER.warn("[ChunkGen] Could not load state: {}", e.getMessage());
        }
    }

    private void saveState() {
        if (dims.isEmpty()) return;
        RootState root = new RootState();
        root.dimensions = dims;
        try {
            Files.createDirectories(STATE_FILE.getParent());
            Files.writeString(STATE_FILE, GSON.toJson(root));
        } catch (IOException e) {
            LOGGER.warn("[ChunkGen] Could not save state: {}", e.getMessage());
        }
    }
}
