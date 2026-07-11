package com.skd.utilitycore.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ItemInput.class, priority = 10000)
public class MixinItemInputBridge {

    @Unique
    public ItemStack createItemStack(int count, boolean allowOversizedStacks) throws CommandSyntaxException {
        return ((ItemInput) (Object) this).createItemStack(count);
    }
}
