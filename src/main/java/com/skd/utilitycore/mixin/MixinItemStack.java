package com.skd.utilitycore.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Predicate;

@Mixin(ItemStack.class)
public class MixinItemStack {

    @Unique
    public boolean is(Object item) {
        ItemStack self = (ItemStack) (Object) this;
        if (item instanceof Item i) {
            return self.getItem() == i;
        }
        if (item instanceof Holder<?> h) {
            return self.is(holder -> holder == h);
        }
        return false;
    }
}
