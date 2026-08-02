package com.skd.utilitycore.client.titles;

import com.skd.utilitycore.Config;
import com.skd.utilitycore.UtilityCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/**
 * Ported from Traveler's Titles (LGPLv3) by YUNGNICKYOUNG: shows a vanilla title/subtitle
 * when the player's biome or dimension changes, reusing vanilla's own title HUD instead of
 * a custom renderer.
 */
@EventBusSubscriber(modid = UtilityCore.MODID, value = Dist.CLIENT)
public class BiomeDimensionTitleHandler {

    private static ResourceKey<Biome> lastBiome;
    private static ResourceKey<Level> lastDimension;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!Config.ENABLE_BIOME_DIMENSION_TITLES.get()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return;

        ResourceKey<Level> dimension = mc.level.dimension();
        if (lastDimension != null && !lastDimension.equals(dimension)) {
            showTitle(mc, dimensionName(dimension));
            lastBiome = biomeKey(player, mc);
            lastDimension = dimension;
            return;
        }
        lastDimension = dimension;

        ResourceKey<Biome> biome = biomeKey(player, mc);
        if (biome != null && lastBiome != null && !lastBiome.equals(biome)) {
            showTitle(mc, biomeName(biome));
        }
        lastBiome = biome;
    }

    private static ResourceKey<Biome> biomeKey(LocalPlayer player, Minecraft mc) {
        Holder<Biome> holder = mc.level.getBiome(player.blockPosition());
        return holder.unwrapKey().orElse(null);
    }

    private static Component biomeName(ResourceKey<Biome> key) {
        return Component.translatable("biome." + key.identifier().getNamespace() + "." + key.identifier().getPath());
    }

    private static Component dimensionName(ResourceKey<Level> key) {
        return Component.translatable("dimension." + key.identifier().getNamespace() + "." + key.identifier().getPath());
    }

    private static void showTitle(Minecraft mc, Component title) {
        mc.gui.setTimes(10, 70, 20);
        mc.gui.setTitle(title);
        mc.gui.setSubtitle(Component.empty());
    }
}
