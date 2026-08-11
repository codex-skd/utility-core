package com.skd.utilitycore.admin.schematic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import com.skd.utilitycore.admin.config.AdminConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.util.ProblemReporter;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class SpawnSchematicManager {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path EXTERNAL_SCHEMATIC_FILE = Path.of("utility_core", "spawn_schem", "schematic_spawn.schem");
    private static final Identifier EMBEDDED_SCHEMATIC = Identifier.parse("utility_core_admin:schematics/spawn_lobby.schem");

    private static SpawnSchematicManager instance;

    private BlockPos minPos;
    private BlockPos maxPos;
    private boolean hasBounds = false;

    private static class MarkerData {
        @SerializedName("placed") boolean placed;
        @SerializedName("min_x") int minX;
        @SerializedName("min_y") int minY;
        @SerializedName("min_z") int minZ;
        @SerializedName("max_x") int maxX;
        @SerializedName("max_y") int maxY;
        @SerializedName("max_z") int maxZ;
    }

    public SpawnSchematicManager() {
        instance = this;
    }

    public static SpawnSchematicManager getInstance() {
        return instance;
    }

    public void onServerStarted(MinecraftServer server) {
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) {
            LOGGER.warn("[SpawnSchematic] Overworld not available, skipping");
            return;
        }

        Path worldFolder = server.getWorldPath(LevelResource.ROOT);
        Path markerFile = worldFolder.resolve("utilitycore_spawn_schematic.json");

        if (Files.exists(markerFile)) {
            loadMarker(markerFile);
            LOGGER.info("[SpawnSchematic] Marker exists, world already processed");
            return;
        }

        if (!AdminConfig.ENABLE_SPAWN_SCHEMATIC.get()) {
            writeNotPlaced(markerFile);
            LOGGER.info("[SpawnSchematic] Feature disabled, marker written as not-placed");
            return;
        }

        SpongeSchematicReader reader;
        try {
            HolderLookup.Provider registryAccess = server.registryAccess();
            HolderLookup<Block> blockLookup = registryAccess.lookupOrThrow(Registries.BLOCK);

            // Try external file first, fallback to embedded
            if (Files.exists(EXTERNAL_SCHEMATIC_FILE)) {
                LOGGER.info("[SpawnSchematic] Loading external schematic from {}", EXTERNAL_SCHEMATIC_FILE);
                reader = SpongeSchematicReader.parse(EXTERNAL_SCHEMATIC_FILE, blockLookup);
            } else {
                LOGGER.info("[SpawnSchematic] Loading embedded schematic from {}", EMBEDDED_SCHEMATIC);
                try (InputStream is = server.getClass().getClassLoader().getResourceAsStream("data/" + EMBEDDED_SCHEMATIC.getNamespace() + "/schematics/" + EMBEDDED_SCHEMATIC.getPath())) {
                    if (is == null) {
                        throw new IOException("Embedded schematic not found in resources");
                    }
                    reader = SpongeSchematicReader.parseFromStream(is, blockLookup);
                }
            }
        } catch (Exception e) {
            LOGGER.error("[SpawnSchematic] Failed to parse schematic: {}", e.getMessage());
            writeNotPlaced(markerFile);
            return;
        }

        ServerLevelData overworldData = server.getWorldData().overworldData();

        int originX = -reader.getWidth() / 2;
        int originZ = -reader.getLength() / 2;
        int originY = resolveOriginY(overworld, reader, originX, originZ);

        BlockPos origin = new BlockPos(originX, originY, originZ);
        BlockPos min = new BlockPos(
                Math.min(origin.getX(), origin.getX() + reader.getWidth() - 1),
                Math.min(origin.getY(), origin.getY() + reader.getHeight() - 1),
                Math.min(origin.getZ(), origin.getZ() + reader.getLength() - 1)
        );
        BlockPos max = new BlockPos(
                Math.max(origin.getX(), origin.getX() + reader.getWidth() - 1),
                Math.max(origin.getY(), origin.getY() + reader.getHeight() - 1),
                Math.max(origin.getZ(), origin.getZ() + reader.getLength() - 1)
        );

        HolderLookup.Provider registryAccess = server.registryAccess();

        for (int y = 0; y < reader.getHeight(); y++) {
            for (int z = 0; z < reader.getLength(); z++) {
                for (int x = 0; x < reader.getWidth(); x++) {
                    BlockState state = reader.getBlock(x, y, z);
                    if (state.isAir()) continue;
                    BlockPos worldPos = origin.offset(x, y, z);
                    overworld.setBlock(worldPos, state, 3);
                }
            }
        }

        for (int y = 0; y < reader.getHeight(); y++) {
            for (int z = 0; z < reader.getLength(); z++) {
                for (int x = 0; x < reader.getWidth(); x++) {
                    BlockPos schematicPos = new BlockPos(x, y, z);
                    CompoundTag beData = reader.getBlockEntities().get(schematicPos);
                    if (beData == null) continue;
                    BlockPos worldPos = origin.offset(schematicPos);
                    BlockEntity be = overworld.getBlockEntity(worldPos);
                    if (be != null) {
                        CompoundTag merged = beData.copy();
                        merged.remove("Pos");
                        merged.remove("Id");
                        if (!merged.isEmpty()) {
                            try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(LOGGER)) {
                                be.loadWithComponents(TagValueInput.create(reporter, registryAccess, merged));
                            }
                        }
                    }
                }
            }
        }

        BlockPos spawnPos = findSafeSpawn(overworld, origin, reader);
        overworldData.setSpawn(LevelData.RespawnData.of(Level.OVERWORLD, spawnPos, 0.0f, 0.0f));
        LOGGER.info("[SpawnSchematic] World spawn set to {}", spawnPos);

        saveMarker(markerFile, true, min, max);

        this.minPos = min;
        this.maxPos = max;
        this.hasBounds = true;

        LOGGER.info("[SpawnSchematic] Schematic placed at origin {} (bounds: {} to {})", origin, min, max);
    }

    public boolean hasProtectionEnabled() {
        return hasBounds && AdminConfig.ENABLE_SPAWN_SCHEMATIC.get() && AdminConfig.SPAWN_SCHEMATIC_PROTECTION_ENABLED.get();
    }

    public boolean isWithinBounds(BlockPos pos) {
        if (!hasProtectionEnabled()) return false;
        return pos.getX() >= minPos.getX() && pos.getX() <= maxPos.getX()
                && pos.getY() >= minPos.getY() && pos.getY() <= maxPos.getY()
                && pos.getZ() >= minPos.getZ() && pos.getZ() <= maxPos.getZ();
    }

    public boolean isWithinMobSpawnColumn(BlockPos pos) {
        if (!hasProtectionEnabled()) return false;
        return pos.getX() >= minPos.getX() && pos.getX() <= maxPos.getX()
                && pos.getY() <= maxPos.getY()
                && pos.getZ() >= minPos.getZ() && pos.getZ() <= maxPos.getZ();
    }

    public BlockPos getMinPos() {
        return minPos;
    }

    public BlockPos getMaxPos() {
        return maxPos;
    }

    public int distanceToExit(BlockPos pos) {
        if (!hasBounds) return 0;
        int exitX = axisDistanceToExit(pos.getX(), minPos.getX(), maxPos.getX());
        int exitZ = axisDistanceToExit(pos.getZ(), minPos.getZ(), maxPos.getZ());
        return Math.min(exitX, exitZ);
    }

    private int axisDistanceToExit(int value, int min, int max) {
        if (value < min) return min - value;
        if (value > max) return value - max;
        return Math.min(value - min + 1, max - value + 1);
    }

    private int resolveOriginY(ServerLevel level, SpongeSchematicReader reader, int originX, int originZ) {
        forceLoadChunks(level, originX, originZ, reader.getWidth(), reader.getLength());

        int raw;
        switch (AdminConfig.SPAWN_SCHEMATIC_HEIGHT_MODE.get()) {
            case FIXED:
                raw = AdminConfig.SPAWN_SCHEMATIC_FIXED_Y.get();
                break;
            case SURFACE:
            default:
                int surface = level.getHeight(Heightmap.Types.WORLD_SURFACE, originX, originZ);
                for (int z = 0; z < reader.getLength(); z++) {
                    for (int x = 0; x < reader.getWidth(); x++) {
                        int h = level.getHeight(Heightmap.Types.WORLD_SURFACE, originX + x, originZ + z);
                        if (h > surface) surface = h;
                    }
                }
                raw = surface + AdminConfig.SPAWN_SCHEMATIC_SURFACE_OFFSET.get();
                break;
        }

        int minY = level.getMinY();
        int maxY = level.getMaxY() - 1;
        return Math.max(minY, Math.min(maxY, raw));
    }

    private void forceLoadChunks(ServerLevel level, int minX, int minZ, int width, int length) {
        int minChunkX = (minX >> 4) - 1;
        int minChunkZ = (minZ >> 4) - 1;
        int maxChunkX = ((minX + width - 1) >> 4) + 1;
        int maxChunkZ = ((minZ + length - 1) >> 4) + 1;
        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                level.getChunk(cx, cz, ChunkStatus.FULL, true);
            }
        }
    }

    private BlockPos findSafeSpawn(ServerLevel level, BlockPos origin, SpongeSchematicReader reader) {
        int centerX = origin.getX() + reader.getWidth() / 2;
        int centerZ = origin.getZ() + reader.getLength() / 2;
        int minY = origin.getY();
        int maxY = origin.getY() + reader.getHeight() - 1;

        for (int y = maxY - 1; y >= minY + 1; y--) {
            BlockPos below = new BlockPos(centerX, y - 1, centerZ);
            BlockPos current = new BlockPos(centerX, y, centerZ);
            BlockPos above = new BlockPos(centerX, y + 1, centerZ);
            if (level.getBlockState(below).isSolid() && level.getBlockState(current).isAir() && level.getBlockState(above).isAir()) {
                return current;
            }
        }

        BlockPos fallback = new BlockPos(centerX, maxY + 1, centerZ);
        LOGGER.warn("[SpawnSchematic] No safe spawn found in structure, falling back to {}", fallback);
        return fallback;
    }

    private void loadMarker(Path path) {
        try {
            String json = Files.readString(path);
            MarkerData data = GSON.fromJson(json, MarkerData.class);
            if (data != null && data.placed) {
                this.minPos = new BlockPos(data.minX, data.minY, data.minZ);
                this.maxPos = new BlockPos(data.maxX, data.maxY, data.maxZ);
                this.hasBounds = true;
                LOGGER.info("[SpawnSchematic] Loaded protection bounds: {} to {}", minPos, maxPos);
            }
        } catch (IOException e) {
            LOGGER.warn("[SpawnSchematic] Could not read marker file: {}", e.getMessage());
        }
    }

    private void writeNotPlaced(Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, GSON.toJson(new MarkerData()));
        } catch (IOException e) {
            LOGGER.warn("[SpawnSchematic] Could not write marker: {}", e.getMessage());
        }
    }

    private void saveMarker(Path path, boolean placed, BlockPos min, BlockPos max) {
        MarkerData data = new MarkerData();
        data.placed = placed;
        data.minX = min.getX();
        data.minY = min.getY();
        data.minZ = min.getZ();
        data.maxX = max.getX();
        data.maxY = max.getY();
        data.maxZ = max.getZ();
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, GSON.toJson(data));
        } catch (IOException e) {
            LOGGER.warn("[SpawnSchematic] Could not save marker: {}", e.getMessage());
        }
    }
}