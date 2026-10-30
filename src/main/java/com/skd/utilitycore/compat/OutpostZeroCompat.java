package com.skd.utilitycore.compat;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = UtilityCore.MODID)
public class OutpostZeroCompat {
    private static final ResourceKey<DamageType> INFECTION_DAMAGE_TYPE =
            ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("outpostzero:infection"));
    private static final float MAX_DAMAGE = 10000.0f;

    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        if (!Config.ENABLE_OUTPOSTZERO_DAMAGE_CAP.get()) return;
        if (event.getSource().is(INFECTION_DAMAGE_TYPE)) {
            float damage = event.getNewDamage();
            if (damage > MAX_DAMAGE) {
                event.setNewDamage(MAX_DAMAGE);
            }
        }
    }
}
