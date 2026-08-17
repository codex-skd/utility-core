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

@Mixin(LootItemEntityPropertyCondition.class)
public abstract class MixinLootItemEntityPropertyCondition {
    private static final Logger LOGGER = LogUtils.getLogger();

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
                // Registry access failure while merging Curios inventory NBT (e.g. enchantment registry
                // not accessible in this tick context). Fail the predicate instead of crashing the server.
                LOGGER.warn("[UtilityCoreFixes] Caught IllegalStateException in EntityPredicate.matches during loot table predicate evaluation. Returning false to prevent crash.", e);
                return false;
            } else {
                throw e;
            }
        }
    }
}
