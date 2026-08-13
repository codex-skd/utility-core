package com.skd.utilitycore.fixes.mixin;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.fixes.config.FixesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelChunk.class)
public abstract class MixinLevelChunk {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Shadow
    public abstract void removeBlockEntity(BlockPos pos);

    @Shadow
    public abstract Level getLevel();

    @Unique
    private static boolean isConfigLoaded() {
        try {
            return FixesConfig.SPEC.isLoaded();
        } catch (Exception e) {
            return false;
        }
    }

    @Unique
    private static boolean isBlockentityMismatchFixEnabled() {
        if (!isConfigLoaded()) return false;
        return FixesConfig.ENABLE_BLOCKENTITY_MISMATCH_FIX.get();
    }

    @Redirect(method = "createBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/EntityBlock;newBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity utility_core$redirectNewBlockEntity(EntityBlock entityBlock, BlockPos blockPos, BlockState blockState) {
        try {
            return entityBlock.newBlockEntity(blockPos, blockState);
        } catch (IllegalStateException e) {
            Level level = this.getLevel();
            if (isBlockentityMismatchFixEnabled() && !level.isClientSide()) {
                // Remove in-memory block entity if present
                removeBlockEntity(blockPos);
                // Log the warning
                String actual = blockState.getBlock().toString();
                String expected = "unknown";
                String message = e.getMessage();
                if (message != null) {
                    int start = message.indexOf("Invalid block entity ");
                    if (start != -1) {
                        start += "Invalid block entity ".length();
                        int end = message.indexOf(" //", start);
                        if (end == -1) {
                            end = message.length();
                        }
                        expected = message.substring(start, end).trim();
                    }
                }
                LOGGER.warn("[UtilityCoreFixes] Removed stale block entity at {} (expected: {}, actual: {})", blockPos, expected, actual);
                return null;
            } else {
                throw e;
            }
        }
    }
}