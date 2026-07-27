package com.skd.utilitycore.mixin;

import com.google.common.base.Preconditions;
import com.skd.utilitycore.Config;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageContainer.class)
public class MixinDamageContainer {

    @Redirect(method = "setNewDamage", at = @At(value = "INVOKE", target = "Lcom/google/common/base/Preconditions;checkArgument(ZLjava/lang/Object;)V"), require = 0)
    private void utility_core$bypassNegativeDamageCheck(boolean expression, Object message) {
        if (Config.ENABLE_NEGATIVE_DAMAGE_FIX.get()) {
            // Suppress "Damage cannot be negative" to prevent server crash.
        } else {
            Preconditions.checkArgument(expression, message);
        }
    }
}
