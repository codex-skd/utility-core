package com.skd.utilitycore.compat.schematic;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.block.state.properties.SlabType;

/**
 * Maps the legacy WorldEdit/MCEdit "Alpha" schematic format (Materials=Alpha) to
 * modern block states. Block IDs follow the pre-1.13 numeric ID table; the optional
 * "Data" byte array carries the block metadata that selects variants/colors/facings.
 */
public final class LegacyBlockMap {

    private static final Block[] BLOCKS = new Block[256];

    private static final Block[] PLANKS = {
            Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS,
            Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS
    };
    private static final Block[] SAPLINGS = {
            Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING,
            Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING
    };
    private static final Block[] LOGS = {
            Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG
    };
    private static final Block[] LOGS2 = { Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG };
    private static final Block[] LEAVES = {
            Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES
    };
    private static final Block[] DOUBLE_PLANTS = {
            Blocks.SUNFLOWER, Blocks.LILAC, Blocks.TALL_GRASS, Blocks.LARGE_FERN, Blocks.ROSE_BUSH, Blocks.PEONY
    };
    private static final Block[] STONE_SLABS = {
            Blocks.STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.OAK_SLAB, Blocks.COBBLESTONE_SLAB,
            Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB
    };
    private static final Block[] WOOD_SLABS = {
            Blocks.OAK_SLAB, Blocks.SPRUCE_SLAB, Blocks.BIRCH_SLAB,
            Blocks.JUNGLE_SLAB, Blocks.ACACIA_SLAB, Blocks.DARK_OAK_SLAB
    };
    private static final Block[] MONSTER_EGGS = {
            Blocks.INFESTED_STONE, Blocks.INFESTED_COBBLESTONE, Blocks.INFESTED_STONE_BRICKS,
            Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS
    };
    private static final Block[] SKULLS = {
            Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.ZOMBIE_HEAD,
            Blocks.PLAYER_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD
    };

    static {
        put(0, Blocks.AIR);
        put(1, Blocks.STONE);
        put(2, Blocks.GRASS_BLOCK);
        put(3, Blocks.DIRT);
        put(4, Blocks.COBBLESTONE);
        put(5, Blocks.OAK_PLANKS);
        put(6, Blocks.OAK_SAPLING);
        put(7, Blocks.BEDROCK);
        put(8, Blocks.WATER);
        put(9, Blocks.WATER);
        put(10, Blocks.LAVA);
        put(11, Blocks.LAVA);
        put(12, Blocks.SAND);
        put(13, Blocks.GRAVEL);
        put(14, Blocks.GOLD_ORE);
        put(15, Blocks.IRON_ORE);
        put(16, Blocks.COAL_ORE);
        put(17, Blocks.OAK_LOG);
        put(18, Blocks.OAK_LEAVES);
        put(19, Blocks.SPONGE);
        put(20, Blocks.GLASS);
        put(21, Blocks.LAPIS_ORE);
        put(22, Blocks.LAPIS_BLOCK);
        put(23, Blocks.DISPENSER);
        put(24, Blocks.SANDSTONE);
        put(25, Blocks.NOTE_BLOCK);
        put(27, Blocks.POWERED_RAIL);
        put(28, Blocks.DETECTOR_RAIL);
        put(29, Blocks.STICKY_PISTON);
        put(30, Blocks.COBWEB);
        put(31, Blocks.SHORT_GRASS);
        put(32, Blocks.DEAD_BUSH);
        put(33, Blocks.PISTON);
        put(34, Blocks.PISTON_HEAD);
        put(36, Blocks.MOVING_PISTON);
        put(37, Blocks.DANDELION);
        put(38, Blocks.POPPY);
        put(39, Blocks.BROWN_MUSHROOM);
        put(40, Blocks.RED_MUSHROOM);
        put(41, Blocks.GOLD_BLOCK);
        put(42, Blocks.IRON_BLOCK);
        put(43, Blocks.STONE_SLAB);
        put(44, Blocks.STONE_SLAB);
        put(45, Blocks.BRICKS);
        put(46, Blocks.TNT);
        put(47, Blocks.BOOKSHELF);
        put(48, Blocks.MOSSY_COBBLESTONE);
        put(49, Blocks.OBSIDIAN);
        put(50, Blocks.TORCH);
        put(51, Blocks.FIRE);
        put(52, Blocks.SPAWNER);
        put(53, Blocks.OAK_STAIRS);
        put(54, Blocks.CHEST);
        put(55, Blocks.REDSTONE_WIRE);
        put(56, Blocks.DIAMOND_ORE);
        put(57, Blocks.DIAMOND_BLOCK);
        put(58, Blocks.CRAFTING_TABLE);
        put(59, Blocks.WHEAT);
        put(60, Blocks.FARMLAND);
        put(61, Blocks.FURNACE);
        put(62, Blocks.FURNACE);
        put(63, Blocks.OAK_SIGN);
        put(64, Blocks.OAK_DOOR);
        put(65, Blocks.LADDER);
        put(66, Blocks.RAIL);
        put(67, Blocks.COBBLESTONE_STAIRS);
        put(68, Blocks.OAK_WALL_SIGN);
        put(69, Blocks.LEVER);
        put(70, Blocks.STONE_PRESSURE_PLATE);
        put(71, Blocks.IRON_DOOR);
        put(72, Blocks.OAK_PRESSURE_PLATE);
        put(73, Blocks.REDSTONE_ORE);
        put(74, Blocks.REDSTONE_ORE);
        put(75, Blocks.REDSTONE_TORCH);
        put(76, Blocks.REDSTONE_TORCH);
        put(77, Blocks.STONE_BUTTON);
        put(78, Blocks.SNOW);
        put(79, Blocks.ICE);
        put(80, Blocks.SNOW_BLOCK);
        put(81, Blocks.CACTUS);
        put(82, Blocks.CLAY);
        put(83, Blocks.SUGAR_CANE);
        put(84, Blocks.JUKEBOX);
        put(85, Blocks.OAK_FENCE);
        put(86, Blocks.PUMPKIN);
        put(87, Blocks.NETHERRACK);
        put(88, Blocks.SOUL_SAND);
        put(89, Blocks.GLOWSTONE);
        put(90, Blocks.NETHER_PORTAL);
        put(91, Blocks.JACK_O_LANTERN);
        put(92, Blocks.CAKE);
        put(93, Blocks.REPEATER);
        put(94, Blocks.REPEATER);
        put(96, Blocks.OAK_TRAPDOOR);
        put(97, Blocks.INFESTED_STONE);
        put(98, Blocks.STONE_BRICKS);
        put(99, Blocks.BROWN_MUSHROOM_BLOCK);
        put(100, Blocks.RED_MUSHROOM_BLOCK);
        put(101, Blocks.IRON_BARS);
        put(102, Blocks.GLASS_PANE);
        put(103, Blocks.MELON);
        put(104, Blocks.PUMPKIN_STEM);
        put(105, Blocks.MELON_STEM);
        put(106, Blocks.VINE);
        put(107, Blocks.OAK_FENCE_GATE);
        put(108, Blocks.BRICK_STAIRS);
        put(109, Blocks.STONE_BRICK_STAIRS);
        put(110, Blocks.MYCELIUM);
        put(111, Blocks.LILY_PAD);
        put(112, Blocks.NETHER_BRICKS);
        put(113, Blocks.NETHER_BRICK_FENCE);
        put(114, Blocks.NETHER_BRICK_STAIRS);
        put(115, Blocks.NETHER_WART);
        put(116, Blocks.ENCHANTING_TABLE);
        put(117, Blocks.BREWING_STAND);
        put(118, Blocks.CAULDRON);
        put(119, Blocks.END_PORTAL);
        put(120, Blocks.END_PORTAL_FRAME);
        put(121, Blocks.END_STONE);
        put(122, Blocks.DRAGON_EGG);
        put(123, Blocks.REDSTONE_LAMP);
        put(124, Blocks.REDSTONE_LAMP);
        put(125, Blocks.OAK_SLAB);
        put(126, Blocks.OAK_SLAB);
        put(127, Blocks.COCOA);
        put(128, Blocks.SANDSTONE_STAIRS);
        put(129, Blocks.EMERALD_ORE);
        put(130, Blocks.ENDER_CHEST);
        put(131, Blocks.TRIPWIRE_HOOK);
        put(132, Blocks.TRIPWIRE);
        put(133, Blocks.EMERALD_BLOCK);
        put(134, Blocks.SPRUCE_STAIRS);
        put(135, Blocks.BIRCH_STAIRS);
        put(136, Blocks.JUNGLE_STAIRS);
        put(137, Blocks.COMMAND_BLOCK);
        put(138, Blocks.BEACON);
        put(139, Blocks.COBBLESTONE_WALL);
        put(140, Blocks.FLOWER_POT);
        put(141, Blocks.CARROTS);
        put(142, Blocks.POTATOES);
        put(143, Blocks.OAK_BUTTON);
        put(144, Blocks.SKELETON_SKULL);
        put(145, Blocks.ANVIL);
        put(146, Blocks.TRAPPED_CHEST);
        put(147, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
        put(148, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
        put(149, Blocks.COMPARATOR);
        put(150, Blocks.COMPARATOR);
        put(151, Blocks.DAYLIGHT_DETECTOR);
        put(152, Blocks.REDSTONE_BLOCK);
        put(153, Blocks.NETHER_QUARTZ_ORE);
        put(154, Blocks.HOPPER);
        put(155, Blocks.QUARTZ_BLOCK);
        put(156, Blocks.QUARTZ_STAIRS);
        put(157, Blocks.ACTIVATOR_RAIL);
        put(158, Blocks.DROPPER);
        put(159, Blocks.TERRACOTTA);
        put(161, Blocks.ACACIA_LEAVES);
        put(162, Blocks.ACACIA_LOG);
        put(163, Blocks.ACACIA_STAIRS);
        put(164, Blocks.DARK_OAK_STAIRS);
        put(165, Blocks.SLIME_BLOCK);
        put(166, Blocks.BARRIER);
        put(167, Blocks.IRON_TRAPDOOR);
        put(168, Blocks.PRISMARINE);
        put(169, Blocks.SEA_LANTERN);
        put(170, Blocks.HAY_BLOCK);
        put(172, Blocks.TERRACOTTA);
        put(173, Blocks.COAL_BLOCK);
        put(174, Blocks.PACKED_ICE);
        put(175, Blocks.SUNFLOWER);
        put(178, Blocks.DAYLIGHT_DETECTOR);
        put(179, Blocks.RED_SANDSTONE);
        put(180, Blocks.RED_SANDSTONE_STAIRS);
        put(181, Blocks.RED_SANDSTONE_SLAB);
        put(182, Blocks.RED_SANDSTONE_SLAB);
        put(183, Blocks.SPRUCE_FENCE_GATE);
        put(184, Blocks.BIRCH_FENCE_GATE);
        put(185, Blocks.JUNGLE_FENCE_GATE);
        put(186, Blocks.DARK_OAK_FENCE_GATE);
        put(187, Blocks.ACACIA_FENCE_GATE);
        put(188, Blocks.SPRUCE_FENCE);
        put(189, Blocks.BIRCH_FENCE);
        put(190, Blocks.JUNGLE_FENCE);
        put(191, Blocks.DARK_OAK_FENCE);
        put(192, Blocks.ACACIA_FENCE);
        put(193, Blocks.SPRUCE_DOOR);
        put(194, Blocks.BIRCH_DOOR);
        put(195, Blocks.JUNGLE_DOOR);
        put(196, Blocks.ACACIA_DOOR);
        put(197, Blocks.DARK_OAK_DOOR);
        put(198, Blocks.END_ROD);
        put(199, Blocks.CHORUS_PLANT);
        put(200, Blocks.CHORUS_FLOWER);
        put(201, Blocks.PURPUR_BLOCK);
        put(202, Blocks.PURPUR_PILLAR);
        put(203, Blocks.PURPUR_STAIRS);
        put(204, Blocks.PURPUR_SLAB);
        put(205, Blocks.PURPUR_SLAB);
        put(206, Blocks.END_STONE_BRICKS);
        put(207, Blocks.BEETROOTS);
        put(208, Blocks.DIRT_PATH);
        put(209, Blocks.END_GATEWAY);
        put(210, Blocks.REPEATING_COMMAND_BLOCK);
        put(211, Blocks.CHAIN_COMMAND_BLOCK);
        put(212, Blocks.FROSTED_ICE);
        put(213, Blocks.MAGMA_BLOCK);
        put(214, Blocks.NETHER_WART_BLOCK);
        put(215, Blocks.RED_NETHER_BRICKS);
        put(216, Blocks.BONE_BLOCK);
        put(217, Blocks.STRUCTURE_VOID);
        put(218, Blocks.OBSERVER);
        put(255, Blocks.STRUCTURE_BLOCK);
    }

    private LegacyBlockMap() {
    }

    public static BlockState toState(int id, int data) {
        switch (id) {
            case 5:
                return pick(PLANKS, data).defaultBlockState();
            case 6:
                return pick(SAPLINGS, data).defaultBlockState();
            case 8:
            case 9:
                return Blocks.WATER.defaultBlockState();
            case 10:
            case 11:
                return Blocks.LAVA.defaultBlockState();
            case 12:
                return (data & 1) == 1 ? Blocks.RED_SAND.defaultBlockState() : Blocks.SAND.defaultBlockState();
            case 17:
                return log(pick(LOGS, data), data);
            case 18:
                return pick(LEAVES, data).defaultBlockState();
            case 19:
                return (data & 1) == 1 ? Blocks.WET_SPONGE.defaultBlockState() : Blocks.SPONGE.defaultBlockState();
            case 24:
                return sandstone(data);
            case 26:
                return Blocks.BED.pick(DyeColor.byId(data & 15)).defaultBlockState();
            case 31:
                return switch (data & 3) {
                    case 0 -> Blocks.DEAD_BUSH.defaultBlockState();
                    case 2 -> Blocks.FERN.defaultBlockState();
                    default -> Blocks.SHORT_GRASS.defaultBlockState();
                };
            case 35:
                return Blocks.WOOL.pick(DyeColor.byId(data & 15)).defaultBlockState();
            case 43:
                return slab(pick(STONE_SLABS, data), data, true);
            case 44:
                return slab(pick(STONE_SLABS, data), data, false);
            case 50:
            case 75:
            case 76:
                return (id == 75 || id == 76) ? Blocks.REDSTONE_TORCH.defaultBlockState() : Blocks.TORCH.defaultBlockState();
            case 53:
                return stairs(Blocks.OAK_STAIRS, data);
            case 65:
                return facing(Blocks.LADDER, data);
            case 66:
                return rail(data);
            case 95:
                return Blocks.STAINED_GLASS.pick(DyeColor.byId(data & 15)).defaultBlockState();
            case 96:
                return Blocks.OAK_TRAPDOOR.defaultBlockState();
            case 97:
                return pick(MONSTER_EGGS, data).defaultBlockState();
            case 98:
                return stoneBricks(data);
            case 99:
                return Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState();
            case 100:
                return Blocks.RED_MUSHROOM_BLOCK.defaultBlockState();
            case 108:
                return stairs(Blocks.BRICK_STAIRS, data);
            case 109:
                return stairs(Blocks.STONE_BRICK_STAIRS, data);
            case 114:
                return stairs(Blocks.NETHER_BRICK_STAIRS, data);
            case 123:
            case 124:
                return Blocks.REDSTONE_LAMP.defaultBlockState();
            case 125:
                return slab(pick(WOOD_SLABS, data), data, true);
            case 126:
                return slab(pick(WOOD_SLABS, data), data, false);
            case 128:
                return stairs(Blocks.SANDSTONE_STAIRS, data);
            case 134:
                return stairs(Blocks.SPRUCE_STAIRS, data);
            case 135:
                return stairs(Blocks.BIRCH_STAIRS, data);
            case 136:
                return stairs(Blocks.JUNGLE_STAIRS, data);
            case 144:
                return pick(SKULLS, data).defaultBlockState();
            case 145:
                return Blocks.ANVIL.defaultBlockState();
            case 151:
            case 178:
                return Blocks.DAYLIGHT_DETECTOR.defaultBlockState();
            case 155:
                return quartz(data);
            case 156:
                return stairs(Blocks.QUARTZ_STAIRS, data);
            case 159:
                return Blocks.DYED_TERRACOTTA.pick(DyeColor.byId(data & 15)).defaultBlockState();
            case 160:
                return Blocks.STAINED_GLASS_PANE.pick(DyeColor.byId(data & 15)).defaultBlockState();
            case 161:
                return (data & 1) == 1 ? Blocks.DARK_OAK_LEAVES.defaultBlockState() : Blocks.ACACIA_LEAVES.defaultBlockState();
            case 162:
                return log(pick(LOGS2, data), data);
            case 163:
                return stairs(Blocks.ACACIA_STAIRS, data);
            case 164:
                return stairs(Blocks.DARK_OAK_STAIRS, data);
            case 168:
                return prismarine(data);
            case 170:
                return axis(Blocks.HAY_BLOCK, data);
            case 171:
                return Blocks.CARPET.pick(DyeColor.byId(data & 15)).defaultBlockState();
            case 175:
                return doublePlant(data);
            case 179:
                return redSandstone(data);
            case 180:
                return stairs(Blocks.RED_SANDSTONE_STAIRS, data);
            case 181:
                return slab(Blocks.RED_SANDSTONE_SLAB, data, true);
            case 182:
                return slab(Blocks.RED_SANDSTONE_SLAB, data, false);
            case 202:
                return axis(Blocks.PURPUR_PILLAR, data);
            case 203:
                return stairs(Blocks.PURPUR_STAIRS, data);
            case 204:
                return slab(Blocks.PURPUR_SLAB, data, true);
            case 205:
                return slab(Blocks.PURPUR_SLAB, data, false);
            case 216:
                return axis(Blocks.BONE_BLOCK, data);
            default:
                if (id >= 219 && id <= 234) {
                    return Blocks.DYED_SHULKER_BOX.pick(DyeColor.byId(id - 219)).defaultBlockState();
                }
                Block b = BLOCKS[id];
                return b == null ? Blocks.AIR.defaultBlockState() : b.defaultBlockState();
        }
    }

    private static void put(int id, Block block) {
        if (id < 256) {
            BLOCKS[id] = block;
        }
    }

    private static Block pick(Block[] array, int data) {
        return array[Math.max(0, Math.min(data, array.length - 1))];
    }

    private static BlockState log(Block base, int data) {
        BlockState state = base.defaultBlockState();
        int axisBits = (data >> 2) & 3;
        Direction.Axis axis = axisBits == 1 ? Direction.Axis.X : axisBits == 2 ? Direction.Axis.Z : Direction.Axis.Y;
        return state.hasProperty(BlockStateProperties.AXIS)
                ? state.setValue(BlockStateProperties.AXIS, axis)
                : state;
    }

    private static BlockState axis(Block base, int data) {
        return log(base, data);
    }

    private static BlockState stairs(Block base, int data) {
        BlockState state = base.defaultBlockState();
        Direction facing = switch (data & 3) {
            case 0 -> Direction.EAST;
            case 1 -> Direction.WEST;
            case 2 -> Direction.SOUTH;
            default -> Direction.NORTH;
        };
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }
        if (state.hasProperty(BlockStateProperties.HALF)) {
            state = state.setValue(BlockStateProperties.HALF, (data & 4) != 0 ? Half.TOP : Half.BOTTOM);
        }
        return state;
    }

    private static BlockState slab(Block base, int data, boolean doubleSlab) {
        BlockState state = base.defaultBlockState();
        if (!state.hasProperty(BlockStateProperties.SLAB_TYPE)) {
            return state;
        }
        SlabType type = doubleSlab
                ? SlabType.DOUBLE
                : (data & 8) != 0 ? SlabType.TOP : SlabType.BOTTOM;
        return state.setValue(BlockStateProperties.SLAB_TYPE, type);
    }

    private static BlockState facing(Block base, int data) {
        BlockState state = base.defaultBlockState();
        Direction facing = switch (data) {
            case 2 -> Direction.SOUTH;
            case 3 -> Direction.NORTH;
            case 4 -> Direction.EAST;
            case 5 -> Direction.WEST;
            default -> Direction.NORTH;
        };
        return state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)
                ? state.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                : state;
    }

    private static BlockState rail(int data) {
        BlockState state = Blocks.RAIL.defaultBlockState();
        if (!state.hasProperty(BlockStateProperties.RAIL_SHAPE)) {
            return state;
        }
        RailShape shape = switch (data) {
            case 1 -> RailShape.EAST_WEST;
            case 2 -> RailShape.SOUTH_EAST;
            case 3 -> RailShape.SOUTH_WEST;
            case 4 -> RailShape.NORTH_WEST;
            case 5 -> RailShape.NORTH_EAST;
            case 6 -> RailShape.ASCENDING_EAST;
            case 7 -> RailShape.ASCENDING_WEST;
            case 8 -> RailShape.ASCENDING_NORTH;
            case 9 -> RailShape.ASCENDING_SOUTH;
            default -> RailShape.NORTH_SOUTH;
        };
        return state.setValue(BlockStateProperties.RAIL_SHAPE, shape);
    }

    private static BlockState sandstone(int data) {
        return switch (data & 3) {
            case 1 -> Blocks.CHISELED_SANDSTONE.defaultBlockState();
            case 2 -> Blocks.CUT_SANDSTONE.defaultBlockState();
            default -> Blocks.SANDSTONE.defaultBlockState();
        };
    }

    private static BlockState redSandstone(int data) {
        return switch (data & 3) {
            case 1 -> Blocks.CHISELED_RED_SANDSTONE.defaultBlockState();
            case 2 -> Blocks.CUT_RED_SANDSTONE.defaultBlockState();
            default -> Blocks.RED_SANDSTONE.defaultBlockState();
        };
    }

    private static BlockState stoneBricks(int data) {
        return switch (data & 7) {
            case 1 -> Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
            case 2 -> Blocks.CRACKED_STONE_BRICKS.defaultBlockState();
            case 3 -> Blocks.CHISELED_STONE_BRICKS.defaultBlockState();
            default -> Blocks.STONE_BRICKS.defaultBlockState();
        };
    }

    private static BlockState quartz(int data) {
        return switch (data & 7) {
            case 1 -> Blocks.CHISELED_QUARTZ_BLOCK.defaultBlockState();
            case 2 -> axis(Blocks.QUARTZ_PILLAR, 0);
            case 3 -> axis(Blocks.QUARTZ_PILLAR, 1);
            case 4 -> axis(Blocks.QUARTZ_PILLAR, 2);
            default -> Blocks.QUARTZ_BLOCK.defaultBlockState();
        };
    }

    private static BlockState prismarine(int data) {
        return switch (data & 7) {
            case 1 -> Blocks.PRISMARINE_BRICKS.defaultBlockState();
            case 2 -> Blocks.DARK_PRISMARINE.defaultBlockState();
            default -> Blocks.PRISMARINE.defaultBlockState();
        };
    }

    private static BlockState doublePlant(int data) {
        BlockState state = pick(DOUBLE_PLANTS, data).defaultBlockState();
        if (state.hasProperty(BlockStateProperties.HALF)) {
            state = state.setValue(BlockStateProperties.HALF, (data & 8) != 0 ? Half.TOP : Half.BOTTOM);
        }
        return state;
    }
}
