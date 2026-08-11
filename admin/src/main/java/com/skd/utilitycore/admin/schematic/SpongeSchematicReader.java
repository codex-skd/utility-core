package com.skd.utilitycore.admin.schematic;

import com.mojang.logging.LogUtils;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpongeSchematicReader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final short width;
    private final short height;
    private final short length;
    private final BlockState[] blocks;
    private final Map<BlockPos, CompoundTag> blockEntities;

    private SpongeSchematicReader(short width, short height, short length, BlockState[] blocks, Map<BlockPos, CompoundTag> blockEntities) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.blocks = blocks;
        this.blockEntities = blockEntities;
    }

    public static SpongeSchematicReader parse(Path path, HolderLookup<Block> blockLookup) throws IOException {
        CompoundTag root = NbtIo.readCompressed(path, NbtAccounter.unlimitedHeap());
        return parseFromCompound(root, blockLookup);
    }

    public static SpongeSchematicReader parseFromStream(InputStream is, HolderLookup<Block> blockLookup) throws IOException {
        CompoundTag root = NbtIo.readCompressed(is, NbtAccounter.unlimitedHeap());
        return parseFromCompound(root, blockLookup);
    }

    private static SpongeSchematicReader parseFromCompound(CompoundTag root, HolderLookup<Block> blockLookup) {
        boolean isLegacy = !root.contains("Schematic") && root.contains("Blocks");
        if (isLegacy) {
            return parseLegacy(root);
        }

        boolean isV3 = root.contains("Schematic");
        CompoundTag schematic = isV3 ? root.getCompoundOrEmpty("Schematic") : root;

        int version = schematic.getIntOr("Version", 2);
        short width = schematic.getShortOr("Width", (short) 0);
        short height = schematic.getShortOr("Height", (short) 0);
        short length = schematic.getShortOr("Length", (short) 0);
        int dataVersion = schematic.getIntOr("DataVersion", 0);

        LOGGER.info("[SpawnSchematic] Reading schematic v{} (data version {}): {}x{}x{}", version, dataVersion, width, height, length);

        CompoundTag paletteTag;
        byte[] blockData;
        ListTag blockEntitiesList;

        if (isV3) {
            CompoundTag blocksTag = schematic.getCompoundOrEmpty("Blocks");
            paletteTag = blocksTag.getCompoundOrEmpty("Palette");
            blockData = blocksTag.getByteArray("Data").orElse(new byte[0]);
            blockEntitiesList = blocksTag.getListOrEmpty("BlockEntities");
        } else {
            paletteTag = schematic.getCompoundOrEmpty("Palette");
            blockData = schematic.getByteArray("BlockData").orElse(new byte[0]);
            blockEntitiesList = schematic.getListOrEmpty("TileEntities");
        }

        Map<Integer, BlockState> paletteMap = new HashMap<>();
        for (String key : paletteTag.keySet()) {
            int id = paletteTag.getIntOr(key, 0);
            BlockState state = parseBlockState(key, blockLookup);
            paletteMap.put(id, state);
        }

        int[] paletteIds = decodeVarInts(blockData);
        int totalBlocks = width * height * length;

        if (paletteIds.length != totalBlocks) {
            LOGGER.warn("[SpawnSchematic] Expected {} blocks, got {} varint values", totalBlocks, paletteIds.length);
        }

        BlockState[] blocks = new BlockState[totalBlocks];
        for (int idx = 0; idx < totalBlocks; idx++) {
            if (idx < paletteIds.length) {
                BlockState state = paletteMap.get(paletteIds[idx]);
                blocks[idx] = state != null ? state : Blocks.AIR.defaultBlockState();
            } else {
                blocks[idx] = Blocks.AIR.defaultBlockState();
            }
        }

        Map<BlockPos, CompoundTag> blockEntities = new HashMap<>();
        for (int i = 0; i < blockEntitiesList.size(); i++) {
            CompoundTag be = blockEntitiesList.getCompoundOrEmpty(i);
            int[] pos = be.getIntArray("Pos").orElse(new int[0]);
            if (pos.length == 3) {
                blockEntities.put(new BlockPos(pos[0], pos[1], pos[2]), be);
            }
        }

        LOGGER.info("[SpawnSchematic] Parsed {} blocks, {} block entities", totalBlocks, blockEntities.size());

        return new SpongeSchematicReader(width, height, length, blocks, blockEntities);
    }

    /**
     * Parses the legacy WorldEdit/MCEdit ".schematic" format (Materials=Alpha):
     * Width/Height/Length shorts, a "Blocks" byte array with pre-1.13 numeric block
     * IDs, an optional "Data" byte array with block metadata, and an optional
     * "AddBlocks" nibble array extending the IDs to 16 bits.
     */
    private static SpongeSchematicReader parseLegacy(CompoundTag root) {
        short width = root.getShortOr("Width", (short) 0);
        short height = root.getShortOr("Height", (short) 0);
        short length = root.getShortOr("Length", (short) 0);
        int totalBlocks = width * height * length;

        byte[] blockIds = root.getByteArray("Blocks").orElse(new byte[0]);
        byte[] blockData = root.getByteArray("Data").orElse(new byte[0]);
        byte[] addBlocks = root.getByteArray("AddBlocks").orElse(new byte[0]);

        BlockState[] blocks = new BlockState[totalBlocks];
        Map<Integer, BlockState> paletteMap = new HashMap<>();
        for (int idx = 0; idx < totalBlocks; idx++) {
            int id = idx < blockIds.length ? (blockIds[idx] & 0xFF) : 0;
            if (addBlocks.length > 0 && idx / 2 < addBlocks.length) {
                int nibble = (idx & 1) == 0 ? (addBlocks[idx / 2] & 0x0F) : ((addBlocks[idx / 2] >> 4) & 0x0F);
                id |= nibble << 8;
            }
            int data = idx < blockData.length ? (blockData[idx] & 0xFF) : 0;
            int key = (id << 8) | data;
            BlockState state = paletteMap.get(key);
            if (state == null) {
                state = LegacyBlockMap.toState(id, data);
                paletteMap.put(key, state);
            }
            blocks[idx] = state != null ? state : Blocks.AIR.defaultBlockState();
        }

        Map<BlockPos, CompoundTag> blockEntities = new HashMap<>();
        ListTag tileEntities = root.getListOrEmpty("TileEntities");
        for (int i = 0; i < tileEntities.size(); i++) {
            CompoundTag be = tileEntities.getCompoundOrEmpty(i);
            int[] pos = be.getIntArray("pos").orElseGet(() -> new int[]{
                    be.getIntOr("x", 0), be.getIntOr("y", 0), be.getIntOr("z", 0)
            });
            if (pos.length == 3) {
                CompoundTag clean = be.copy();
                clean.remove("id");
                clean.remove("x");
                clean.remove("y");
                clean.remove("z");
                clean.remove("pos");
                blockEntities.put(new BlockPos(pos[0], pos[1], pos[2]), clean);
            }
        }

        LOGGER.info("[SpawnSchematic] Parsed legacy WorldEdit schematic (Materials=Alpha): {}x{}x{} with {} blocks, {} block entities", width, height, length, totalBlocks, blockEntities.size());

        return new SpongeSchematicReader(width, height, length, blocks, blockEntities);
    }

    public BlockState getBlock(int x, int y, int z) {
        int idx = index(x, y, z);
        if (idx < 0 || idx >= blocks.length) return Blocks.AIR.defaultBlockState();
        return blocks[idx];
    }

    private int index(int x, int y, int z) {
        return (y * length + z) * width + x;
    }

    public short getWidth() { return width; }
    public short getHeight() { return height; }
    public short getLength() { return length; }
    public BlockState[] getBlocks() { return blocks; }
    public Map<BlockPos, CompoundTag> getBlockEntities() { return blockEntities; }

    private static BlockState parseBlockState(String str, HolderLookup<Block> blockLookup) {
        try {
            var result = BlockStateParser.parseForBlock(blockLookup, str, false);
            return result.blockState();
        } catch (Exception e) {
            LOGGER.warn("[SpawnSchematic] Failed to parse block state '{}': {}", str, e.getMessage());
            return Blocks.AIR.defaultBlockState();
        }
    }

    public static int[] decodeVarInts(byte[] data) {
        List<Integer> list = new ArrayList<>();
        int i = 0;
        while (i < data.length) {
            int value = 0;
            int shift = 0;
            byte b;
            do {
                if (i >= data.length) break;
                b = data[i++];
                value |= (b & 0x7F) << shift;
                shift += 7;
                if (shift > 35) {
                    value = 0;
                    break;
                }
            } while ((b & 0x80) != 0);
            list.add(value);
        }
        int[] result = new int[list.size()];
        for (int j = 0; j < list.size(); j++) {
            result[j] = list.get(j);
        }
        return result;
    }
}