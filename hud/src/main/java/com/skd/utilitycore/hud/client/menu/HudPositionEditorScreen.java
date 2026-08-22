package com.skd.utilitycore.hud.client.menu;

import com.skd.utilitycore.hud.client.HudConfig;
import com.skd.utilitycore.hud.client.HudConfig.Orientation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HudPositionEditorScreen extends Screen {
    private final Screen parent;

    protected HudPositionEditorScreen(Screen parent) {
        super(Component.translatable("hud.editor.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 2;

        this.addRenderableWidget(Button.builder(Component.translatable("hud.editor.scale_up"), btn -> {
            HudConfig.scale = Math.min(HudConfig.scale + 0.1f, 5.0f);
        }).pos(centerX + 50, y).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("hud.editor.scale_down"), btn -> {
            HudConfig.scale = Math.max(HudConfig.scale - 0.1f, 0.1f);
        }).pos(centerX + 74, y).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("hud.editor.done"), btn -> {
            HudConfig.save();
            Minecraft.getInstance().gui.setScreen(parent);
        }).pos(centerX - 50, y).size(60, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("hud.editor.cancel"), btn -> {
            Minecraft.getInstance().gui.setScreen(parent);
        }).pos(centerX - 50, y + 24).size(60, 20).build());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}