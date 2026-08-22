package com.skd.utilitycore.fixes.mixin;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.fixes.config.FixesConfig;
import com.skd.utilitycore.fixes.common.attachment.ModAttachments;
import com.skd.utilitycore.fixes.common.attachment.ReturnPortalData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.border.WorldBorder;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(NetherPortalBlock.class)
public abstract class MixinNetherReturnPortalFix {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Unique
    private static boolean isConfigEnabled() {
        return FixesConfig.SPEC.isLoaded() && FixesConfig.ENABLE_NETHER_RETURN_PORTAL_FIX.get();
    }

    @Unique
    private static boolean isOverworldToNether(ResourceKey<Level> origin, ResourceKey<Level> destination) {
        return Level.OVERWORLD.equals(origin) && Level.NETHER.equals(destination);
    }

    @Unique
    private static boolean isNetherToOverworld(ResourceKey<Level> origin, ResourceKey<Level> destination) {
        return Level.NETHER.equals(origin) && Level.OVERWORLD.equals(destination);
    }

    @Unique
    private static BlockPos findOriginPortalPos(Entity entity, BlockPos portalEntryPos) {
        BlockState state = entity.level().getBlockState(portalEntryPos);
        if (state.is(Blocks.NETHER_PORTAL)) {
            return portalEntryPos.immutable();
        }
        for (Direction dir : Direction.values()) {
            BlockPos offset = portalEntryPos.relative(dir);
            if (entity.level().getBlockState(offset).is(Blocks.NETHER_PORTAL)) {
                return offset.immutable();
            }
        }
        return null;
    }

    @Redirect(
        method = "getExitPortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/portal/PortalForcer;findClosestPortalPosition(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/level/border/WorldBorder;)Ljava/util/Optional;"
        )
    )
    private Optional<BlockPos> utility_core$redirectFindClosestPortal(
        PortalForcer instance,
        BlockPos approximateExitPos,
        boolean toNether,
        WorldBorder worldBorder,
        ServerLevel newLevel,
        Entity entity,
        BlockPos portalEntryPos,
        BlockPos approximateExitPos2,
        boolean toNether2,
        WorldBorder worldBorder2
    ) {
        if (!isConfigEnabled()) {
            return instance.findClosestPortalPosition(approximateExitPos, toNether, worldBorder);
        }

        ResourceKey<Level> originDimension = entity.level().dimension();
        ResourceKey<Level> destinationDimension = newLevel.dimension();

        if (!isOverworldToNether(originDimension, destinationDimension) && !isNetherToOverworld(originDimension, destinationDimension)) {
            return instance.findClosestPortalPosition(approximateExitPos, toNether, worldBorder);
        }

        ReturnPortalData data = entity.getData(ModAttachments.RETURN_PORTAL_DATA);
        BlockPos originPortalPos = findOriginPortalPos(entity, portalEntryPos);

        if (originPortalPos == null) {
            LOGGER.debug("[UtilityCoreFixes] Entity {} not in a nether portal block at {}, skipping return portal tracking", entity, portalEntryPos);
            return instance.findClosestPortalPosition(approximateExitPos, toNether, worldBorder);
        }

        if (isOverworldToNether(originDimension, destinationDimension)) {
            Optional<BlockPos> vanillaResult = instance.findClosestPortalPosition(approximateExitPos, toNether, worldBorder);
            vanillaResult.ifPresent(exitPos -> {
                data.setReturnPortal(destinationDimension, exitPos, originPortalPos);
                LOGGER.debug("[UtilityCoreFixes] Stored return portal mapping: {} (Overworld) -> {} (Nether) for entity {}", originPortalPos, exitPos, entity);
            });
            return vanillaResult;
        }

        if (isNetherToOverworld(originDimension, destinationDimension)) {
            Optional<BlockPos> remembered = data.getReturnPortal(originDimension, originPortalPos);
            if (remembered.isPresent()) {
                BlockPos rememberedPos = remembered.get();
                if (newLevel.getBlockState(rememberedPos).is(Blocks.NETHER_PORTAL)) {
                    LOGGER.debug("[UtilityCoreFixes] Redirected return portal: {} (Nether) -> {} (Overworld) for entity {}", originPortalPos, rememberedPos, entity);
                    return Optional.of(rememberedPos);
                } else {
                    data.removeReturnPortal(originDimension, originPortalPos);
                    LOGGER.debug("[UtilityCoreFixes] Remembered portal at {} no longer valid, falling back to vanilla search", rememberedPos);
                }
            }
            Optional<BlockPos> vanillaResult = instance.findClosestPortalPosition(approximateExitPos, toNether, worldBorder);
            vanillaResult.ifPresent(exitPos -> {
                data.setReturnPortal(originDimension, originPortalPos, exitPos);
                LOGGER.debug("[UtilityCoreFixes] Stored return portal mapping: {} (Nether) -> {} (Overworld) for entity {}", originPortalPos, exitPos, entity);
            });
            return vanillaResult;
        }

        return instance.findClosestPortalPosition(approximateExitPos, toNether, worldBorder);
    }
}