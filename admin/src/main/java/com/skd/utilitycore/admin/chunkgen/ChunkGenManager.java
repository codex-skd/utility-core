package com.skd.utilitycore.admin.chunkgen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import com.skd.utilitycore.admin.config.AdminConfig;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
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
    private boolean restPhase = false;
    private long phaseStartMillis = 0L;
    private int dimIndex = 0;
    private int ticksSinceLastLog = 0;
    private boolean completed = false;

    private static class RootState {
        @SerializedName("dimensions") Map<String, DimState> dimensions = new HashMap<>();
        @SerializedName("completed") boolean completed;
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

    private Field listenerField;
    private boolean listenerFieldSearched = false;

    public ChunkGenManager() {
        loadState();
    }

    public void tick(MinecraftServer server) {
        if (!AdminConfig.CHUNK_GEN_ENABLED.get()) {
            paused = false;
            return;
        }

        int playerCount = server.getPlayerCount();
        boolean joining = hasJoiningConnection(server);

        if (!running) {
            if (playerCount == 0 && !joining) {
                start(server);
            }
            return;
        }

        if ((playerCount > 0 || joining) && !AdminConfig.CHUNK_GEN_RUN_WITH_PLAYERS.get()) {
            if (!paused) {
                paused = true;
                LOGGER.info("[ChunkGen] Player detected, pausing generation");
            }
            return;
        }

        if (paused) {
            paused = false;
            phaseStartMillis = Util.getMillis();
            LOGGER.info("[ChunkGen] No more players, resuming generation");
        }

        // Duty cycle: loadSeconds of generation, then restSeconds of rest (wall-clock).
        long now = Util.getMillis();
        int loadSecs = Math.max(1, AdminConfig.CHUNK_GEN_LOAD_SECONDS.get());
        int restSecs = Math.max(0, AdminConfig.CHUNK_GEN_REST_SECONDS.get());

        if (!restPhase) {
            if (now - phaseStartMillis >= (long) loadSecs * 1000L) {
                restPhase = true;
                phaseStartMillis = now;
                LOGGER.info("[ChunkGen] Duty cycle: loaded for {}s, entering {}s rest period", loadSecs, restSecs);
                return;
            }
        } else if (restSecs <= 0 || now - phaseStartMillis >= (long) restSecs * 1000L) {
            restPhase = false;
            phaseStartMillis = now;
            LOGGER.info("[ChunkGen] Duty cycle: rest period over, resuming loading for {}s", loadSecs);
        } else {
            return;
        }

        int chunksPerTick = Math.min(100, AdminConfig.CHUNK_GEN_CHUNKS_PER_TICK.get());
        int maxRadius = AdminConfig.CHUNK_GEN_MAX_RADIUS.get();

        for (int i = 0; i < chunksPerTick; i++) {
            if (i % 5 == 0) {
                int pc = server.getPlayerCount();
                if (pc > 0 || hasJoiningConnection(server)) {
                    paused = true;
                    phaseStartMillis = 0L;
                    LOGGER.info("[ChunkGen] Player detected mid-batch, aborting generation");
                    return;
                }
            }
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

    // A player counts as "present" as soon as their connection reaches the
    // login/config handshake, so chunk generation stops before the world download.
    private boolean hasJoiningConnection(MinecraftServer server) {
        if (server.getConnection() == null) return false;
        try {
            Connection[] conns = server.getConnection().getConnections().toArray(new Connection[0]);
            for (Connection c : conns) {
                if (c == null || !c.isConnected()) continue;
                if (c.getReceiving() != PacketFlow.SERVERBOUND) continue;
                Object listener = readPacketListener(c);
                if (listener instanceof ServerConfigurationPacketListenerImpl
                        || listener instanceof ServerLoginPacketListenerImpl) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    private Object readPacketListener(Connection c) {
        if (!listenerFieldSearched) {
            listenerFieldSearched = true;
            try {
                Field f = Connection.class.getDeclaredField("packetListener");
                f.setAccessible(true);
                listenerField = f;
            } catch (Exception ignored) {
                listenerField = null;
            }
        }
        if (listenerField == null) return null;
        try {
            return listenerField.get(c);
        } catch (Exception ignored) {
            return null;
        }
    }

    private void start(MinecraftServer server) {
        if (completed) {
            running = false;
            return;
        }
        boolean[] configs = {
            AdminConfig.CHUNK_GEN_DIMENSION_OVERWORLD.get(),
            AdminConfig.CHUNK_GEN_DIMENSION_NETHER.get(),
            AdminConfig.CHUNK_GEN_DIMENSION_END.get()
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
        restPhase = false;
        phaseStartMillis = Util.getMillis();
        dimIndex = 0;
        LOGGER.info("[ChunkGen] Auto-starting. Duty cycle: {}s load / {}s rest. Dimensions: {}",
                AdminConfig.CHUNK_GEN_LOAD_SECONDS.get(), AdminConfig.CHUNK_GEN_REST_SECONDS.get(),
                String.join(", ", activeDimensions()));
        saveState();
    }

    private void stopAll() {
        running = false;
        paused = false;
        restPhase = false;
        phaseStartMillis = 0L;
        completed = true;
        LOGGER.info("[ChunkGen] All dimensions completed");
        saveState();
    }

    public void pause() {
        if (!running) return;
        paused = true;
        phaseStartMillis = Util.getMillis();
        LOGGER.info("[ChunkGen] Generation paused");
        saveState();
    }

    public void stop() {
        if (!running) return;
        running = false;
        paused = false;
        restPhase = false;
        phaseStartMillis = 0L;
        LOGGER.info("[ChunkGen] Generation stopped");
        saveState();
    }

    public void reset() {
        dims.clear();
        running = false;
        paused = false;
        restPhase = false;
        phaseStartMillis = 0L;
        completed = false;
        try {
            Files.deleteIfExists(STATE_FILE);
        } catch (IOException ignored) {}
        LOGGER.info("[ChunkGen] Progress reset for all dimensions");
        saveState();
    }

    public void onPlayerJoin() {
        if (running) {
            paused = true;
            phaseStartMillis = 0L;
            LOGGER.info("[ChunkGen] Player joined. Total generated: {} chunks. Pausing generation", totalGenerated());
        } else {
            LOGGER.info("[ChunkGen] Player joined. Total generated: {} chunks", totalGenerated());
        }
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
    public boolean isRestPhase() { return restPhase; }
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

    // --- Persistence ---

    private void loadState() {
        if (Files.notExists(STATE_FILE)) return;
        try {
            String json = Files.readString(STATE_FILE);
            RootState root = GSON.fromJson(json, RootState.class);
            if (root != null && root.dimensions != null) {
                dims.putAll(root.dimensions);
                completed = root.completed;
                LOGGER.info("[ChunkGen] Loaded state: {} chunks across {} dimensions{}",
                        totalGenerated(), dims.size(), completed ? " (completed)" : "");
            }
        } catch (IOException e) {
            LOGGER.warn("[ChunkGen] Could not load state: {}", e.getMessage());
        }
    }

    private void saveState() {
        if (dims.isEmpty() && !completed) return;
        RootState root = new RootState();
        root.dimensions = dims;
        root.completed = completed;
        try {
            Files.createDirectories(STATE_FILE.getParent());
            Files.writeString(STATE_FILE, GSON.toJson(root));
        } catch (IOException e) {
            LOGGER.warn("[ChunkGen] Could not save state: {}", e.getMessage());
        }
    }
}