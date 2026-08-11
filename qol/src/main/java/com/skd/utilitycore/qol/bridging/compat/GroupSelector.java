package com.skd.utilitycore.qol.bridging.compat;

import net.minecraft.world.item.ItemStack;

public interface GroupSelector {

    boolean passes(ItemStack stack);

}
