package com.skd.utilitycore.fixes.mixin;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.fixes.config.FixesConfig;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LootItemEntityPropertyCondition.class)
public abstract class MixinLootItemEntityPropertyCondition {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final AtomicBoolean firstOccurrence = new AtomicBoolean(false);
    private static final AtomicInteger occurrenceCounter = new AtomicInteger(0);

    @Unique
    private static boolean isConfigLoaded() {
        try {
            return FixesConfig.SPEC.isLoaded();
        } catch (Exception e) {
            return false;
        }
    }

    @Unique
    private static boolean isCuriosLootPredicateFixEnabled() {
        if (!isConfigLoaded()) return false;
        return FixesConfig.ENABLE_CURIOS_LOOT_PREDICATE_FIX.get();
    }

    @Redirect(method = "test", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/predicates/entity/EntityPredicate;matches(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean utility_core$redirectEntityPredicateMatches(EntityPredicate entityPredicate, ServerLevel level, Vec3 pos, Entity entity) {
        try {
            return entityPredicate.matches(level, pos, entity);
        } catch (IllegalStateException e) {
            if (isCuriosLootPredicateFixEnabled()) {
                if (firstOccurrence.compareAndSet(false, true)) {
                    LOGGER.warn("[UtilityCoreFixes] Caught IllegalStateException in EntityPredicate.matches during loot table predicate evaluation. Returning false to prevent crash.", e);
                } else {
                    int count = occurrenceCounter.incrementAndGet();
                    if (count % 200 == 0) {
                        LOGGER.warn("[UtilityCoreFixes] Caught IllegalStateException in EntityPredicate.matches during loot table predicate evaluation. Returning false to prevent crash. [Suppressed {} similar messages]", count);
                    }
                }
                return false;
            } else {
                throw e;
            }
        }
    }
}
