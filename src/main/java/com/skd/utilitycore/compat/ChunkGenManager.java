package com.skd.utilitycore.compat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import com.skd.utilitycore.Config;
import net.minecraft.util.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChunkGenManager {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path STATE_FILE = Path.of("config", "utility_core_chunk_gen.json");

    private State state;
    private boolean paused = false;
    private boolean running = false;
    private int ticksSinceLastLog = 0;

    private int dirX = 1, dirZ = 0;
    private int segmentLen = 1;
    private int stepsInSegment = 0;
    private int segmentsChanged = 0;
    private boolean firstStep = true;

    private static class State {
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

        State() {}

        State(int chunkX, int chunkZ, int radius, long totalGenerated, int segmentLen) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.radius = radius;
            this.totalGenerated = totalGenerated;
            this.segmentLen = segmentLen;
        }
    }

    public ChunkGenManager() {
        loadState();
    }

    private static Field nextTickTickField;
    static {
        try {
            Field f = MinecraftServer.class.getDeclaredField("nextTickTick");
            f.setAccessible(true);
            nextTickTickField = f;
        } catch (Exception e) {
            nextTickTickField = null;
        }
    }

    private void keepAlive(MinecraftServer server) {
        if (nextTickTickField != null) {
            try {
                nextTickTickField.setLong(server, Util.getMillis() + 50L);
            } catch (Exception ignored) {}
        }
    }

    public void tick(MinecraftServer server) {
        if (!Config.CHUNK_GEN_ENABLED.get()) return;

        int playerCount = server.getPlayerCount();

        if (!running) {
            if (playerCount == 0) {
                start(true);
            }
            return;
        }

        // Prevent server from pausing while generation is active
        if (playerCount == 0) {
            keepAlive(server);
        }

        if (playerCount > 0 && !Config.CHUNK_GEN_RUN_WITH_PLAYERS.get()) {
            if (!paused) {
                paused = true;
                LOGGER.info("[ChunkGen] Player joined, pausing generation. Position: ({}), chunks generated: {}",
                        state != null ? state.chunkX + ", " + state.chunkZ : "none", state != null ? state.totalGenerated : 0);
            }
            return;
        }

        if (paused) {
            paused = false;
            LOGGER.info("[ChunkGen] No more players, resuming generation. Position: ({}), chunks generated: {}",
                    state != null ? state.chunkX + ", " + state.chunkZ : "none", state != null ? state.totalGenerated : 0);
        }

        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        int chunksPerTick = Config.CHUNK_GEN_CHUNKS_PER_TICK.get();
        int maxRadius = Config.CHUNK_GEN_MAX_RADIUS.get();

        for (int i = 0; i < chunksPerTick; i++) {
            if (state == null) break;

            if (maxRadius > 0 && state.radius > maxRadius) {
                stop(true);
                return;
            }

            int cx = state.chunkX;
            int cz = state.chunkZ;
            overworld.getChunk(cx, cz, ChunkStatus.FULL, true);
            state.totalGenerated++;

            moveToNext();
            updateRadius();

            ticksSinceLastLog++;
            if (ticksSinceLastLog >= 100) {
                ticksSinceLastLog = 0;
                LOGGER.info("[ChunkGen] Generated {} chunks so far. Position: ({}, {}), radius: {}",
                        state.totalGenerated, state.chunkX, state.chunkZ, state.radius);
            }
        }

        saveState();
    }

    public void onPlayerJoin() {
        if (!Config.CHUNK_GEN_ENABLED.get() || state == null) return;
        LOGGER.info("[ChunkGen] Player joined. Position: ({}, {}), chunks generated: {}",
                state.chunkX, state.chunkZ, state.totalGenerated);
        saveState();
    }

    public void onServerStopping() {
        if (state != null) {
            saveState();
            LOGGER.info("[ChunkGen] Server stopping. Progress saved at ({}, {}), {} chunks generated",
                    state.chunkX, state.chunkZ, state.totalGenerated);
        }
    }

    public void onPlayerLeave(MinecraftServer server) {
        if (!Config.CHUNK_GEN_ENABLED.get() || server == null || state == null) return;
        LOGGER.info("[ChunkGen] Player left. Position: ({}, {}), chunks generated: {}",
                state.chunkX, state.chunkZ, state.totalGenerated);
    }

    public void start(boolean auto) {
        if (state == null) {
            state = new State(0, 0, 0, 0, 1);
        }
        running = true;
        paused = false;
        firstStep = true;
        syncStateToSpiral();
        if (auto) {
            LOGGER.info("[ChunkGen] Auto-starting generation from ({}, {}). Previously generated: {} chunks",
                    state.chunkX, state.chunkZ, state.totalGenerated);
        } else {
            LOGGER.info("[ChunkGen] Generation started from ({}, {}). Previously generated: {} chunks",
                    state.chunkX, state.chunkZ, state.totalGenerated);
        }
        saveState();
    }

    public void pause() {
        if (!running || state == null) return;
        paused = true;
        LOGGER.info("[ChunkGen] Generation paused at ({}, {}). Generated: {} chunks",
                state.chunkX, state.chunkZ, state.totalGenerated);
        saveState();
    }

    public void stop() {
        if (!running || state == null) return;
        running = false;
        paused = false;
        LOGGER.info("[ChunkGen] Generation stopped at ({}, {}). Generated: {} chunks",
                state.chunkX, state.chunkZ, state.totalGenerated);
        saveState();
    }

    public void reset() {
        state = new State(0, 0, 0, 0, 1);
        running = false;
        paused = false;
        dirX = 1; dirZ = 0;
        segmentLen = 1;
        stepsInSegment = 0;
        segmentsChanged = 0;
        firstStep = true;
        LOGGER.info("[ChunkGen] Progress reset to (0, 0)");
        saveState();
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    public State getState() {
        return state;
    }

    public int getChunkX() { return state != null ? state.chunkX : 0; }
    public int getChunkZ() { return state != null ? state.chunkZ : 0; }
    public int getRadius() { return state != null ? state.radius : 0; }
    public long getTotalGenerated() { return state != null ? state.totalGenerated : 0; }

    private void moveToNext() {
        if (firstStep) {
            firstStep = false;
            return;
        }

        state.chunkX += dirX;
        state.chunkZ += dirZ;
        stepsInSegment++;

        if (stepsInSegment >= segmentLen) {
            stepsInSegment = 0;
            segmentsChanged++;

            int tmp = dirX;
            dirX = dirZ;
            dirZ = -tmp;

            if (segmentsChanged % 2 == 0) {
                segmentLen++;
            }
        }
    }

    private void updateRadius() {
        int r = Math.max(Math.abs(state.chunkX), Math.abs(state.chunkZ));
        if (r > state.radius) {
            state.radius = r;
        }
    }

    private void syncStateToSpiral() {
        dirX = state.dirX;
        dirZ = state.dirZ;
        segmentLen = state.segmentLen;
        stepsInSegment = state.stepsInSegment;
        segmentsChanged = state.segmentsChanged;
        firstStep = state.firstStep;
    }

    private void saveStateToSpiral() {
        if (state == null) return;
        state.dirX = dirX;
        state.dirZ = dirZ;
        state.segmentLen = segmentLen;
        state.stepsInSegment = stepsInSegment;
        state.segmentsChanged = segmentsChanged;
        state.firstStep = firstStep;
    }

    private void stop(boolean complete) {
        running = false;
        paused = false;
        if (complete) {
            LOGGER.info("[ChunkGen] Generation complete! Generated {} chunks up to radius {}",
                    state != null ? state.totalGenerated : 0, state != null ? state.radius : 0);
        }
        saveState();
    }

    private void loadState() {
        if (Files.notExists(STATE_FILE)) return;
        try {
            String json = Files.readString(STATE_FILE);
            state = GSON.fromJson(json, State.class);
            if (state != null) {
                syncStateToSpiral();
                LOGGER.info("[ChunkGen] Loaded state: position ({}, {}), {} chunks generated",
                        state.chunkX, state.chunkZ, state.totalGenerated);
            }
        } catch (IOException e) {
            LOGGER.warn("[ChunkGen] Could not load state file: {}", e.getMessage());
        }
    }

    private void saveState() {
        if (state == null) return;
        saveStateToSpiral();
        try {
            Files.createDirectories(STATE_FILE.getParent());
            Files.writeString(STATE_FILE, GSON.toJson(state));
        } catch (IOException e) {
            LOGGER.warn("[ChunkGen] Could not save state file: {}", e.getMessage());
        }
    }
}
