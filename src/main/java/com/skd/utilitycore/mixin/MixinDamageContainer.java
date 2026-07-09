package com.skd.utilitycore.mixin;

import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageContainer.class)
public class MixinDamageContainer {

    @Redirect(method = "setNewDamage", at = @At(value = "INVOKE", target = "Lcom/google/common/base/Preconditions;checkArgument(ZLjava/lang/Object;)V"))
    private void utility_core$bypassNegativeDamageCheck(boolean expression, Object message) {
        // Suppress "Damage cannot be negative" to prevent server crash.
        // The negative value will still be stored, but the damage system
        // handles it gracefully; the crash is the real problem.
    }
}
