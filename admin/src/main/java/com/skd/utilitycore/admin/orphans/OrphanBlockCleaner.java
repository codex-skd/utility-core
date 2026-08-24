package com.skd.utilitycore.admin.orphans;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.admin.config.AdminConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrphanBlockCleaner {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Executor BACKGROUND_EXECUTOR = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "utilitycore-orphancleaner");
        t.setDaemon(true);
        return t;
    });
    private static final Pattern REGION_PATTERN = Pattern.compile("^r\\.(-?\\d+)\\.(-?\\d+)\\.mca$");
    private static final BlockState AIR_STATE = Blocks.AIR.defaultBlockState();

    private final MinecraftServer server;
    private final boolean dryRun;
    private final Set<String> blacklistedNamespaces = new HashSet<>();
    private final Set<String> blacklistedBlockIds = new HashSet<>();

    private final AtomicLong regionsScanned = new AtomicLong(0);
    private final AtomicLong regionsModified = new AtomicLong(0);
    private final AtomicLong sectionsModified = new AtomicLong(0);
    private final AtomicLong paletteEntriesRewritten = new AtomicLong(0);
    private final AtomicLong blocksReplaced = new AtomicLong(0);
    private final AtomicLong blockEntitiesRemoved = new AtomicLong(0);
    private final Map<String, Long> rewrittenByBlockId = new ConcurrentHashMap<>();

    public OrphanBlockCleaner(MinecraftServer server, boolean dryRun) {
        this.server = server;
        this.dryRun = dryRun;
        parseBlacklist();
    }

    private void parseBlacklist() {
        List<? extends String> blacklist = AdminConfig.ORPHAN_BLOCKS_BLACKLIST.get();
        for (String entry : blacklist) {
            if (entry.contains(":")) {
                blacklistedBlockIds.add(entry.toLowerCase());
            } else {
                blacklistedNamespaces.add(entry.toLowerCase());
            }
        }
        LOGGER.info("[OrphanBlockCleaner] Loaded {} namespace wildcards and {} exact block IDs from config",
                blacklistedNamespaces.size(), blacklistedBlockIds.size());
    }

    private boolean isBlacklisted(String blockId) {
        String lower = blockId.toLowerCase();
        if (blacklistedBlockIds.contains(lower)) {
            return true;
        }
        int colon = lower.indexOf(':');
        if (colon > 0) {
            String ns = lower.substring(0, colon);
            return blacklistedNamespaces.contains(ns);
        }
        return false;
    }

    public CompletableFuture<CleanupReport> run() {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info("[OrphanBlockCleaner] Starting orphan block cleanup (dryRun={})", dryRun);
            if (!dryRun) {
                LOGGER.warn("[OrphanBlockCleaner] WARNING: This is an IRREVERSIBLE edit to world data. Ensure you have a backup of the world folder before proceeding!");
            }

            ResourceKey<Level>[] dims = new ResourceKey[]{Level.OVERWORLD, Level.NETHER, Level.END};
            for (ResourceKey<Level> dimKey : dims) {
                ServerLevel level = server.getLevel(dimKey);
                if (level == null) {
                    LOGGER.warn("[OrphanBlockCleaner] Dimension {} not available, skipping", dimKey.identifier());
                    continue;
                }
                processDimension(level, dimKey);
            }

            CleanupReport report = new CleanupReport(
                    regionsScanned.get(),
                    regionsModified.get(),
                    sectionsModified.get(),
                    paletteEntriesRewritten.get(),
                    blocksReplaced.get(),
                    blockEntitiesRemoved.get(),
                    Collections.unmodifiableMap(new HashMap<>(rewrittenByBlockId))
            );
            LOGGER.info("[OrphanBlockCleaner] Cleanup complete. Report: {}", report);
            return report;
        }, BACKGROUND_EXECUTOR);
    }

    private void processDimension(ServerLevel level, ResourceKey<Level> dimKey) {
        LOGGER.info("[OrphanBlockCleaner] Processing dimension: {}", dimKey.identifier());

        ServerChunkCache chunkSource = level.getChunkSource();
        ChunkMap chunkMap = chunkSource.chunkMap;
        Path regionFolder = getRegionFolder(dimKey);
        if (regionFolder == null) {
            LOGGER.warn("[OrphanBlockCleaner] Could not determine region folder for {}", dimKey.identifier());
            return;
        }

        File regionDir = regionFolder.toFile();
        if (!regionDir.isDirectory()) {
            LOGGER.info("[OrphanBlockCleaner] Region folder not found for dimension {}", dimKey.identifier());
            return;
        }

        File[] regionFiles = regionDir.listFiles((dir, name) -> name.endsWith(".mca"));
        if (regionFiles == null || regionFiles.length == 0) {
            LOGGER.info("[OrphanBlockCleaner] No region files in dimension {}", dimKey.identifier());
            return;
        }
        LOGGER.info("[OrphanBlockCleaner] Found {} region files in dimension {}", regionFiles.length, dimKey.identifier());

        Set<ChunkPos> loadedChunks = Collections.synchronizedSet(new HashSet<>());

        for (File regionFile : regionFiles) {
            if (Thread.currentThread().isInterrupted()) {
                LOGGER.warn("[OrphanBlockCleaner] Interrupted, stopping region scan");
                break;
            }
            processRegionFile(regionFile, chunkSource, chunkMap, loadedChunks);
        }

        if (!loadedChunks.isEmpty()) {
            processLoadedChunks(level, loadedChunks);
        }
    }

    private void processRegionFile(File regionFile, ServerChunkCache chunkSource, ChunkMap chunkMap, Set<ChunkPos> loadedChunks) {
        regionsScanned.incrementAndGet();
        String name = regionFile.getName();
        Matcher matcher = REGION_PATTERN.matcher(name);
        if (!matcher.matches()) {
            LOGGER.warn("[OrphanBlockCleaner] Skipping non-region file: {}", name);
            return;
        }

        int regionX = Integer.parseInt(matcher.group(1));
        int regionZ = Integer.parseInt(matcher.group(2));
        int chunksModifiedInRegion = 0;

        for (int localX = 0; localX < 32; localX++) {
            for (int localZ = 0; localZ < 32; localZ++) {
                int chunkX = regionX * 32 + localX;
                int chunkZ = regionZ * 32 + localZ;
                ChunkPos pos = new ChunkPos(chunkX, chunkZ);

                LevelChunk loaded = chunkSource.getChunkNow(chunkX, chunkZ);
                if (loaded != null) {
                    loadedChunks.add(pos);
                    continue;
                }

                try {
                    Optional<CompoundTag> chunkTagOpt = chunkMap.read(pos).join();
                    if (chunkTagOpt.isEmpty()) {
                        continue;
                    }

                    CompoundTag chunkTag = chunkTagOpt.get();
                    boolean modified = processChunkTag(chunkTag);
                    if (modified) {
                        chunksModifiedInRegion++;
                        if (!dryRun) {
                            if (chunkSource.getChunkNow(chunkX, chunkZ) != null) {
                                loadedChunks.add(pos);
                                LOGGER.warn("[OrphanBlockCleaner] Chunk {} became loaded before write, deferring to loaded-chunk path", pos);
                            } else {
                                chunkMap.write(pos, chunkTag).join();
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("[OrphanBlockCleaner] Error processing chunk {} in region {}: {}", pos, name, e.getMessage(), e);
                }
            }
        }

        if (chunksModifiedInRegion > 0) {
            regionsModified.incrementAndGet();
            LOGGER.info("[OrphanBlockCleaner] Modified {} chunks in region {}", chunksModifiedInRegion, name);
        }
    }

    private boolean processChunkTag(CompoundTag chunkTag) {
        CompoundTag dataTag = chunkTag.getCompound("Level").orElse(chunkTag);
        ListTag sectionsTag = dataTag.getListOrEmpty("sections");
        if (sectionsTag.isEmpty()) {
            return false;
        }

        boolean chunkModified = false;

        for (int i = 0; i < sectionsTag.size(); i++) {
            CompoundTag sectionTag = sectionsTag.getCompoundOrEmpty(i);
            Optional<CompoundTag> blockStatesOpt = sectionTag.getCompound("block_states");
            if (blockStatesOpt.isEmpty()) {
                continue;
            }

            CompoundTag blockStatesTag = blockStatesOpt.get();
            ListTag paletteTag = blockStatesTag.getListOrEmpty("palette");
            if (paletteTag.isEmpty()) {
                continue;
            }

            boolean sectionModified = false;
            for (int j = 0; j < paletteTag.size(); j++) {
                CompoundTag stateTag = paletteTag.getCompoundOrEmpty(j);
                String blockId = stateTag.getStringOr("Name", "");
                if (isBlacklisted(blockId)) {
                    sectionModified = true;
                    chunkModified = true;
                    paletteEntriesRewritten.incrementAndGet();
                    rewrittenByBlockId.merge(blockId, 1L, Long::sum);
                    if (!dryRun) {
                        CompoundTag airTag = new CompoundTag();
                        airTag.putString("Name", "minecraft:air");
                        paletteTag.set(j, airTag);
                    }
                }
            }

            if (sectionModified) {
                sectionsModified.incrementAndGet();
            }
        }

        if (chunkModified) {
            ListTag blockEntitiesTag = dataTag.getListOrEmpty("block_entities");
            if (!blockEntitiesTag.isEmpty()) {
                ListTag filtered = new ListTag();
                for (int i = 0; i < blockEntitiesTag.size(); i++) {
                    CompoundTag beTag = blockEntitiesTag.getCompoundOrEmpty(i);
                    String beId = beTag.getStringOr("id", "");
                    if (isBlacklisted(beId)) {
                        blockEntitiesRemoved.incrementAndGet();
                    } else {
                        filtered.add(beTag);
                    }
                }
                if (!dryRun) {
                    dataTag.put("block_entities", filtered);
                }
            }
        }

        return chunkModified;
    }

    private void processLoadedChunks(ServerLevel level, Set<ChunkPos> loadedChunks) {
        LOGGER.info("[OrphanBlockCleaner] Scheduling {} loaded chunks for server-thread processing", loadedChunks.size());
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            ServerChunkCache chunkSource = level.getChunkSource();
            for (ChunkPos pos : loadedChunks) {
                LevelChunk chunk = chunkSource.getChunkNow(pos.x(), pos.z());
                if (chunk == null) {
                    continue;
                }
                processLoadedChunk(level, chunk);
            }
        }, server::execute);
        future.join();
    }

    private void processLoadedChunk(ServerLevel level, LevelChunk chunk) {
        LevelChunkSection[] sections = chunk.getSections();
        for (int i = 0; i < sections.length; i++) {
            LevelChunkSection section = sections[i];
            if (section == null || section.hasOnlyAir()) {
                continue;
            }

            boolean sectionModified = false;
            int baseY = chunk.getMinY() + i * 16;
            for (int y = 0; y < 16; y++) {
                int worldY = baseY + y;
                for (int z = 0; z < 16; z++) {
                    for (int x = 0; x < 16; x++) {
                        BlockState state = section.getBlockState(x, y, z);
                        Identifier blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
                        if (isBlacklisted(blockId.toString())) {
                            sectionModified = true;
                            BlockPos pos = chunk.getPos().getBlockAt(x, worldY, z);
                            BlockEntity blockEntity = level.getBlockEntity(pos);
                            if (!dryRun) {
                                level.setBlock(pos, AIR_STATE, 3);
                                if (blockEntity != null) {
                                    level.removeBlockEntity(pos);
                                }
                            }
                            if (blockEntity != null) {
                                blockEntitiesRemoved.incrementAndGet();
                            }
                            blocksReplaced.incrementAndGet();
                            rewrittenByBlockId.merge(blockId.toString(), 1L, Long::sum);
                        }
                    }
                }
            }

            if (sectionModified) {
                sectionsModified.incrementAndGet();
            }
        }
    }

    private Path getRegionFolder(ResourceKey<Level> dimKey) {
        try {
            LevelStorageSource.LevelStorageAccess storageSource = getStorageSource(server);
            return storageSource.getDimensionPath(dimKey).resolve("region");
        } catch (Exception e) {
            LOGGER.error("[OrphanBlockCleaner] Failed to resolve region folder for {}: {}", dimKey.identifier(), e.getMessage());
            return null;
        }
    }

    private static LevelStorageSource.LevelStorageAccess getStorageSource(MinecraftServer server) throws Exception {
        Field field = MinecraftServer.class.getDeclaredField("storageSource");
        field.setAccessible(true);
        return (LevelStorageSource.LevelStorageAccess) field.get(server);
    }

    public static class CleanupReport {
        public final long regionsScanned;
        public final long regionsModified;
        public final long sectionsModified;
        public final long paletteEntriesRewritten;
        public final long blocksReplaced;
        public final long blockEntitiesRemoved;
        public final Map<String, Long> rewrittenByBlockId;

        public CleanupReport(long regionsScanned, long regionsModified, long sectionsModified,
                             long paletteEntriesRewritten, long blocksReplaced, long blockEntitiesRemoved,
                             Map<String, Long> rewrittenByBlockId) {
            this.regionsScanned = regionsScanned;
            this.regionsModified = regionsModified;
            this.sectionsModified = sectionsModified;
            this.paletteEntriesRewritten = paletteEntriesRewritten;
            this.blocksReplaced = blocksReplaced;
            this.blockEntitiesRemoved = blockEntitiesRemoved;
            this.rewrittenByBlockId = rewrittenByBlockId;
        }

        @Override
        public String toString() {
            return "CleanupReport{" +
                    "regionsScanned=" + regionsScanned +
                    ", regionsModified=" + regionsModified +
                    ", sectionsModified=" + sectionsModified +
                    ", paletteEntriesRewritten=" + paletteEntriesRewritten +
                    ", blocksReplaced=" + blocksReplaced +
                    ", blockEntitiesRemoved=" + blockEntitiesRemoved +
                    ", rewrittenByBlockId=" + rewrittenByBlockId +
                    '}';
        }
    }
}
