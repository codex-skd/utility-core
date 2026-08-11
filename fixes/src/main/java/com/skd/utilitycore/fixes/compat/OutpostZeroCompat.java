package com.skd.utilitycore.fixes.compat;

import com.skd.utilitycore.fixes.config.FixesConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Unique;

@EventBusSubscriber(modid = "utility_core_fixes")
public class OutpostZeroCompat {
    private static final ResourceKey<DamageType> INFECTION_DAMAGE_TYPE =
            ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.tryParse("outpostzero:infection"));
    private static final float MAX_DAMAGE = 10000.0f;

    @Unique
    private static boolean isConfigLoaded() {
        try {
            return FixesConfig.SPEC.isLoaded();
        } catch (Exception e) {
            return false;
        }
    }

    @Unique
    private static boolean isOutpostZeroCapEnabled() {
        if (!isConfigLoaded()) return false;
        return FixesConfig.ENABLE_OUTPOSTZERO_DAMAGE_CAP.get();
    }

    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        if (!isOutpostZeroCapEnabled()) return;
        if (event.getSource().is(INFECTION_DAMAGE_TYPE)) {
            float damage = event.getNewDamage();
            if (damage > MAX_DAMAGE) {
                event.setNewDamage(MAX_DAMAGE);
            }
        }
    }
}