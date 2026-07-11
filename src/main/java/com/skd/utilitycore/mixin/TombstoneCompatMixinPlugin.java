package com.skd.utilitycore.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class TombstoneCompatMixinPlugin implements IMixinConfigPlugin {

    private static final String TOMBSTONE_ITEM_INPUT_MIXIN = "ovh.corail.tombstone.mixin.ItemInputMixin";
    private static final String OUR_ITEM_INPUT_MIXIN = "com.skd.utilitycore.mixin.MixinItemInput";

    static final boolean TOMBSTONE_MIXIN_BROKEN;

    static {
        boolean broken = false;
        try {
            Class<?> mixinClass = Class.forName(TOMBSTONE_ITEM_INPUT_MIXIN);
            for (Method m : mixinClass.getDeclaredMethods()) {
                if (m.getName().equals("methodCreateItemStack")) {
                    Class<?>[] params = m.getParameterTypes();
                    broken = params.length == 3
                        && params[0] == int.class
                        && params[1] == boolean.class;
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            // Tombstone not installed
        }
        TOMBSTONE_MIXIN_BROKEN = broken;
    }

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (TOMBSTONE_MIXIN_BROKEN) {
            if (mixinClassName.equals(TOMBSTONE_ITEM_INPUT_MIXIN)) {
                return false;
            }
        } else {
            if (mixinClassName.equals(OUR_ITEM_INPUT_MIXIN)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
