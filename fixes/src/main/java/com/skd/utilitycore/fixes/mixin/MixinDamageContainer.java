package com.skd.utilitycore.fixes.mixin;

import com.google.common.base.Preconditions;
import com.skd.utilitycore.fixes.config.FixesConfig;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageContainer.class)
public class MixinDamageContainer {

    @Unique
    private static boolean isConfigLoaded() {
        try {
            return FixesConfig.SPEC.isLoaded();
        } catch (Exception e) {
            return false;
        }
    }

    @Unique
    private static boolean isNegativeDamageFixEnabled() {
        if (!isConfigLoaded()) return false;
        return FixesConfig.ENABLE_NEGATIVE_DAMAGE_FIX.get();
    }

    @Redirect(method = "setNewDamage", at = @At(value = "INVOKE", target = "Lcom/google/common/base/Preconditions;checkArgument(ZLjava/lang/Object;)V"), require = 0)
    private void utility_core$bypassNegativeDamageCheck(boolean expression, Object message) {
        if (isNegativeDamageFixEnabled()) {
            // Suppress "Damage cannot be negative" to prevent server crash.
        } else {
            Preconditions.checkArgument(expression, message);
        }
    }
}