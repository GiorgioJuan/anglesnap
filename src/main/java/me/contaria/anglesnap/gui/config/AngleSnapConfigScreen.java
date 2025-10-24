package me.contaria.anglesnap.gui.config;

import me.contaria.anglesnap.AngleSnap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class AngleSnapConfigScreen extends Screen {
    private final Screen parent;

    public AngleSnapConfigScreen(Screen parent) {
        super(Text.translatable("anglesnap.gui.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int titleWidth = this.textRenderer.getWidth(this.title);
        this.addDrawableChild(new TextWidget((this.width - titleWidth) / 2, 10, titleWidth, 15, this.title, this.textRenderer));
        this.addDrawableChild(new AngleSnapConfigListWidget(this.client, this.width, this.height - 70, 35));
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(this.parent);
    }

    @Override
    public void removed() {
        AngleSnap.CONFIG.save();
    }
}
