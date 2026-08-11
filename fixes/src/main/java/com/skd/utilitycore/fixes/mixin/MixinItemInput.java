package com.skd.utilitycore.fixes.mixin;

import com.skd.utilitycore.fixes.config.FixesConfig;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Mixin(ItemInput.class)
public class MixinItemInput {

    private static final boolean TOMBSTONE_ACTIVE;
    private static Object lollipopItem;
    private static Object magicScrollItem;
    private static Method resetColorMethod;
    private static Method setRandomMagicEffectMethod;

    static {
        boolean active = false;
        try {
            Class<?> modItems = Class.forName("ovh.corail.tombstone.registry.ModItems");
            Field lollipopField = modItems.getField("lollipop");
            Field magicScrollField = modItems.getField("magic_scroll");
            lollipopItem = lollipopField.get(null);
            magicScrollItem = magicScrollField.get(null);

            Class<?> lollipopClass = Class.forName("ovh.corail.tombstone.item.ItemLollipop");
            resetColorMethod = lollipopClass.getMethod("resetColor", ItemStack.class);

            Class<?> magicScrollClass = Class.forName("ovh.corail.tombstone.item.ItemMagicScroll");
            setRandomMagicEffectMethod = magicScrollClass.getMethod("setRandomMagicEffect", ItemStack.class);

            active = true;
        } catch (Exception e) {
            // Tombstone not available - skip compat logic
        }
        TOMBSTONE_ACTIVE = active;
    }

    @Inject(method = "createItemStack", at = @At("RETURN"), cancellable = true)
    private void onCreateItemStack(int count, CallbackInfoReturnable<ItemStack> cir) {
        if (!TOMBSTONE_ACTIVE || !FixesConfig.ENABLE_TOMBSTONE_ITEM_INIT_FIX.get()) return;

        ItemStack stack = cir.getReturnValue();
        if (stack.isEmpty()) return;

        try {
            if (stack.getItem() == lollipopItem) {
                cir.setReturnValue((ItemStack) resetColorMethod.invoke(lollipopItem, stack));
            } else if (stack.getItem() == magicScrollItem) {
                cir.setReturnValue((ItemStack) setRandomMagicEffectMethod.invoke(magicScrollItem, stack));
            }
        } catch (Exception e) {
            // Reflection call failed - leave the original result
        }
    }
}