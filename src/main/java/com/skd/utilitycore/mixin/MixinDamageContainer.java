package com.skd.utilitycore.mixin;

import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DamageContainer.class)
public class MixinDamageContainer {

    @ModifyVariable(method = "setNewDamage", at = @At("HEAD"), argsOnly = true)
    private float utility_core$clampNegativeDamage(float newDamage) {
        return Math.max(0.0F, newDamage);
    }
}
