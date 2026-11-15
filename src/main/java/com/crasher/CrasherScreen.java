package com.crasher;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrasherScreen extends Screen {
    private static final int BG_COLOR   = 0xDD1A0000;
    private static final int FRAME_CRIM = 0xFF550000;
    private static final int TITLE_COL  = 0xFFFF2020;

    private ButtonWidget startBtn;
    private boolean running = false;

    public CrasherScreen() {
        super(Text.literal("SERVER CRASHER"));
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        startBtn = ButtonWidget.builder(Text.literal("▲  ЗАПУСТИТЬ  ▲"), b -> {
            if (!running) {
                running = true;
                PacketSpammer.start();
                startBtn.setMessage(Text.literal("✦  КРАШИМ...  ✦"));
            }
        }).dimensions(cx - 90, cy + 10, 180, 24).build();

        addDrawableChild(startBtn);
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        ctx.fill(0, 0, this.width, this.height, BG_COLOR);

        int bx1 = width / 2 - 140, by1 = height / 2 - 90;
        int bx2 = width / 2 + 140, by2 = height / 2 + 90;
        for (int i = 0; i < 3; i++) {
            ctx.drawBorder(bx1 - i, by1 - i, bx2 - bx1 + i * 2, by2 - by1 + i * 2, FRAME_CRIM);
        }

        for (int i = 0; i < 12; i++) {
            int dx = bx1 + (i * 23) % (bx2 - bx1);
            int dy = by2 + 4 + (i % 5) * 4;
            ctx.fill(dx, dy, dx + 2, dy + 8, FRAME_CRIM);
        }

        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("☠ SERVER CRASHER ☠"),
                width / 2, height / 2 - 70, TITLE_COL);
        ctx.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Отправка пакетов на сервер..."),
                width / 2, height / 2 - 40, 0xFFFFAAAA);

        super.render(ctx, mx, my, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
