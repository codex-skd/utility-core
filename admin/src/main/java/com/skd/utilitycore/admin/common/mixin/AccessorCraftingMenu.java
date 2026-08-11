package com.skd.utilitycore.admin.common.mixin;

import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractCraftingMenu.class)
public interface AccessorCraftingMenu {

    @Accessor("craftSlots")
    CraftingContainer utility_core$getCraftSlots();

    @Accessor("resultSlots")
    ResultContainer utility_core$getResultSlots();
}
