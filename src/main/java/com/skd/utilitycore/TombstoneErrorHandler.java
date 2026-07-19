package com.skd.utilitycore;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class TombstoneErrorHandler implements IMixinErrorHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public ErrorAction onPrepareError(IMixinConfig config, Throwable th, IMixinInfo mixin, ErrorAction action) {
        return action;
    }

    @Override
    public ErrorAction onApplyError(String targetClassName, Throwable th, IMixinInfo mixin, ErrorAction action) {
        if (!isEnabled()) return action;
        if (mixin != null && "ovh.corail.tombstone.mixin.ItemInputMixin".equals(mixin.getClassName())) {
            LOGGER.warn("[UtilityCore] Suppressed Tombstone ItemInputMixin error on {}: {}", targetClassName, th.getMessage());
            return ErrorAction.WARN;
        }
        return action;
    }

    private static boolean isEnabled() {
        try {
            return Config.ENABLE_TOMBSTONE_ERROR_HANDLER.get();
        } catch (Exception e) {
            return true;
        }
    }
}
