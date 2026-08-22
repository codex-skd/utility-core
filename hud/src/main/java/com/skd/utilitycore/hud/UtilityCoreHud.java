package com.skd.utilitycore.hud;

import com.skd.utilitycore.hud.client.HudConfig;
import com.skd.utilitycore.hud.client.menu.HudMenuScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import java.util.ArrayList;
import java.util.List;

@Mod(UtilityCoreHud.MODID)
public class UtilityCoreHud {
    public static final String MODID = "utility_core_hud";

    public UtilityCoreHud(IEventBus modEventBus) {
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(UtilityCoreHud::onRenderGui);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        HudConfig.load();
    }

    private void onRegisterCommands(final RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("hudmenu")
            .executes(ctx -> {
                Minecraft.getInstance().execute(() -> {
                    Minecraft.getInstance().gui.setScreen(new HudMenuScreen(null));
                });
                return 1;
            }));
    }

    private static void onRenderGui(RenderGuiLayerEvent.Post event) {
        if (!HudConfig.visible || HudConfig.neverShow) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        List<ItemStack> armor = new ArrayList<>();
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = mc.player.getItemBySlot(slot);
            if (!stack.isEmpty()) armor.add(stack);
        }
        if (armor.isEmpty()) return;

        var graphics = event.getGuiGraphics();
        int baseX = HudConfig.x;
        int baseY = HudConfig.y;
        int offset = 0;
        for (ItemStack stack : armor) {
            int x = baseX + (HudConfig.orientation == HudConfig.Orientation.HORIZONTAL ? offset : 0);
            int y = baseY + (HudConfig.orientation == HudConfig.Orientation.VERTICAL ? offset : 0);
            graphics.item(stack, x, y);
            graphics.itemDecorations(mc.font, stack, x, y);
            offset += 16;
        }
    }
}
