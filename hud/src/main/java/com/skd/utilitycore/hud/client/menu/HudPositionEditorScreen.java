package com.skd.utilitycore.hud.client.menu;

import com.skd.utilitycore.hud.client.HudConfig;
import com.skd.utilitycore.hud.client.HudConfig.Orientation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class HudPositionEditorScreen extends Screen {
    private final Screen parent;
    private boolean dragging = false;
    private double dragOffsetX, dragOffsetY;
    private int initialConfigX, initialConfigY;

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
            onClose();
        }).pos(centerX - 50, y).size(60, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("hud.editor.cancel"), btn -> {
            HudConfig.x = initialConfigX;
            HudConfig.y = initialConfigY;
            onClose();
        }).pos(centerX - 50, y + 24).size(60, 20).build());

        initialConfigX = HudConfig.x;
        initialConfigY = HudConfig.y;
        clampPosition();
    }

    private void clampPosition() {
        HudBounds bounds = previewBounds();
        HudConfig.x = Math.clamp(HudConfig.x, 0, Math.max(0, this.width - bounds.width));
        HudConfig.y = Math.clamp(HudConfig.y, 0, Math.max(0, this.height - bounds.height));
    }

    private HudBounds previewBounds() {
        int iconSize = (int)(16 * HudConfig.scale);
        int count = getArmorCount();
        int totalWidth = HudConfig.orientation == Orientation.HORIZONTAL ? count * iconSize : iconSize;
        int totalHeight = HudConfig.orientation == Orientation.VERTICAL ? count * iconSize : iconSize;
        return new HudBounds(HudConfig.x, HudConfig.y, totalWidth, totalHeight);
    }

    private int getArmorCount() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 4;
        int count = 0;
        for (var slot : new net.minecraft.world.entity.EquipmentSlot[]{
                net.minecraft.world.entity.EquipmentSlot.HEAD,
                net.minecraft.world.entity.EquipmentSlot.CHEST,
                net.minecraft.world.entity.EquipmentSlot.LEGS,
                net.minecraft.world.entity.EquipmentSlot.FEET}) {
            if (!mc.player.getItemBySlot(slot).isEmpty()) count++;
        }
        return count == 0 ? 4 : count;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Dark semi-transparent overlay
        guiGraphics.fill(0, 0, this.width, this.height, 0xAA000000);

        // Draw preview of armor HUD
        drawHudPreview(guiGraphics);

        // Gold border frame around preview
        HudBounds bounds = previewBounds();
        int borderColor = bounds.contains(mouseX, mouseY) ? 0xFFFF5500 : 0xFFFFD700;
        guiGraphics.fill(bounds.x - 2, bounds.y - 2, bounds.x + bounds.width + 2, bounds.y - 1, borderColor);
        guiGraphics.fill(bounds.x - 2, bounds.y + bounds.height + 1, bounds.x + bounds.width + 2, bounds.y + bounds.height + 2, borderColor);
        guiGraphics.fill(bounds.x - 2, bounds.y - 2, bounds.x - 1, bounds.y + bounds.height + 2, borderColor);
        guiGraphics.fill(bounds.x + bounds.width + 1, bounds.y - 2, bounds.x + bounds.width + 2, bounds.y + bounds.height + 2, borderColor);

        // Info panel (centered bottom area) - exactly like better-party PartyHudPositionScreen
        int panelWidth = 230;
        int panelX = (this.width - 230) / 2;
        int panelY = this.height / 2 + 80;
        int panelHeight = 64;

        // Frame color: gold if hover on preview, dark gray otherwise
        HudBounds previewB = previewBounds();
        int frameColor = previewBounds().contains(mouseX, mouseY) ? 0xFDEA3D7D : 0xFF3E3E3D;

        // Draw frame exactly like PartyUi.frame (4 fills for 1px border)
        int px = (this.width - 230) / 2;
        int py = this.height / 2 + 80;
        int pw = 230;
        int ph = 64;
        int color = previewBounds().contains(mouseX, mouseY) ? 0xFDEA3D7D : 0xFF3E3E3D;
        guiGraphics.fill(px, py, px + 230, py + 1, color);
        guiGraphics.fill(px, py + 63, px + 230, py + 64, color);
        guiGraphics.fill(px, py, px + 1, py + 64, color);
        guiGraphics.fill(px + 229, py, px + 230, py + 64, color);

        // Text colors like better-party: hints=0xFFBBBBBB, values=0xFFD4D4D4, title=0xFFFFFFFF
        // Title
        guiGraphics.text(this.font, fitText(Component.translatable("hud.editor.title")), (this.width - 230) / 2 + 6, this.height / 2 + 80 + 6, 0xFFFFFFFF, true);
        
        // Hints (like better-party hints) - color 0xFFBBBBBB
        guiGraphics.text(this.font, fitText(Component.translatable("hud.editor.hint_drag")), (this.width - 230) / 2 + 6, this.height / 2 + 80 + 18, 0xFFBBBBBB, true);
        guiGraphics.text(this.font, fitText(Component.translatable("hud.editor.hint_scroll")), (this.width - 230) / 2 + 6, this.height / 2 + 80 + 31, 0xFFBBBBBB, true);
        
        // Values (like better-party values) - color 0xFFD4D4D4
        String scaleText = String.format("%.0f%%", HudConfig.scale * 100);
        guiGraphics.text(this.font, fitText(Component.translatable("hud.editor.values", scaleText)), (this.width - 230) / 2 + 6, this.height / 2 + 80 + 45, 0xFFD4D4D4, true);

        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }

    private String fitText(Component component) {
        String text = component.getString();
        int maxWidth = 218; // panelWidth - 12
        if (this.font.width(text) <= 218) return text;
        return this.font.plainSubstrByWidth(text, 218) + "...";
    }

    private void drawHudPreview(GuiGraphicsExtractor guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        int iconSize = (int)(16 * HudConfig.scale);
        int spacing = 0;
        int count = 0;

        var slots = new net.minecraft.world.entity.EquipmentSlot[]{
                net.minecraft.world.entity.EquipmentSlot.HEAD,
                net.minecraft.world.entity.EquipmentSlot.CHEST,
                net.minecraft.world.entity.EquipmentSlot.LEGS,
                net.minecraft.world.entity.EquipmentSlot.FEET};

        for (var slot : slots) {
            if (!mc.player.getItemBySlot(slot).isEmpty()) count++;
        }

        if (count == 0) count = 4;

        int offset = 0;
        for (var slot : slots) {
            ItemStack stack = mc.player.getItemBySlot(slot);
            int x = HudConfig.x + (HudConfig.orientation == Orientation.HORIZONTAL ? offset : 0);
            int y = HudConfig.y + (HudConfig.orientation == Orientation.VERTICAL ? offset : 0);
            
            if (!stack.isEmpty()) {
                // Render actual item with durability overlay
                guiGraphics.item(stack, x, y);
                guiGraphics.itemDecorations(mc.font, stack, x, y);
            } else {
                // Placeholder for empty slots (semi-transparent gold)
                guiGraphics.fill(x, y, x + iconSize, y + iconSize, 0x88FFD700);
            }
            offset += iconSize + 0;
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean bool) {
        if (super.mouseClicked(event, bool)) return true;

        double mouseX = event.x();
        double mouseY = event.y();
        HudBounds bounds = previewBounds();

        if (bounds.contains(mouseX, mouseY)) {
            dragging = true;
            dragOffsetX = mouseX - HudConfig.x;
            dragOffsetY = mouseY - HudConfig.y;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        if (dragging) {
            HudConfig.x = (int)Math.round(event.x() - dragOffsetX);
            HudConfig.y = (int)Math.round(event.y() - dragOffsetY);
            clampPosition();
            return true;
        }
        return super.mouseDragged(event, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (dragging) {
            dragging = false;
            HudConfig.save();
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY != 0) {
            HudConfig.scale = Math.clamp(HudConfig.scale + (float)scrollY * 0.1f, 0.1f, 5.0f);
            clampPosition();
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        int key = event.key();
        if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
            HudConfig.save();
            onClose();
            return true;
        }
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            onClose();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().gui.setScreen(parent);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private record HudBounds(int x, int y, int width, int height) {
        public boolean contains(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }
    }
}