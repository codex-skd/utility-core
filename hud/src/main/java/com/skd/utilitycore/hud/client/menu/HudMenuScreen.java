package com.skd.utilitycore.hud.client.menu;

import com.skd.utilitycore.hud.client.HudConfig;
import com.skd.utilitycore.hud.client.HudConfig.Orientation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HudMenuScreen extends Screen {
    private final Screen parent;
    private Button toggleButton;

    public HudMenuScreen(Screen parent) {
        super(Component.translatable("hud.menu.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int y = this.height / 2 - 40;
        int x = this.width / 2 - 100;

        toggleButton = this.addRenderableWidget(Button.builder(Component.translatable(getToggleLabel()), btn -> {
            cycleVisibility();
            btn.setMessage(Component.translatable(getToggleLabel()));
        }).pos(x, y).size(200, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("hud.menu.edit_position"), btn -> {
            Minecraft.getInstance().gui.setScreen(new HudPositionEditorScreen(this));
        }).pos(x, y + 24).size(200, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("hud.menu.save"), btn -> {
            HudConfig.save();
        }).pos(x, y + 48).size(95, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("hud.menu.close"), btn -> {
            Minecraft.getInstance().gui.setScreen(parent);
        }).pos(x + 105, y + 48).size(95, 20).build());
    }

    private void cycleVisibility() {
        if (HudConfig.visible && !HudConfig.neverShow) {
            HudConfig.visible = false;
            HudConfig.neverShow = false;
        } else if (!HudConfig.visible && !HudConfig.neverShow) {
            HudConfig.visible = false;
            HudConfig.neverShow = true;
        } else {
            HudConfig.visible = true;
            HudConfig.neverShow = false;
        }
    }

    private String getToggleLabel() {
        if (HudConfig.visible && !HudConfig.neverShow) return "hud.menu.never_show";
        if (!HudConfig.visible && !HudConfig.neverShow) return "hud.menu.show";
        return "hud.menu.show";
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
