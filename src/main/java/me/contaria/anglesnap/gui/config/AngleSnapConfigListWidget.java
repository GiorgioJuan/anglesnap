package me.contaria.anglesnap.gui.config;

import me.contaria.anglesnap.AngleSnap;
import me.contaria.anglesnap.config.Option;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;
import java.util.List;

public class AngleSnapConfigListWidget extends ElementListWidget<AngleSnapConfigListWidget.AbstractEntry> {
    public AngleSnapConfigListWidget(MinecraftClient minecraftClient, int width, int height, int y) {
        super(minecraftClient, width, height, y, 24, 0);

        for (Option<?> option : AngleSnap.CONFIG.getOptions()) {
            if (option.hasWidget()) {
                this.addEntry(new Entry(option));
            }
        }
    }

    @Override
    public int getRowWidth() {
        return this.width;
    }

    @Override
    protected int getScrollbarX() {
        return this.width - 5;
    }

    public abstract static class AbstractEntry extends ElementListWidget.Entry<AbstractEntry> {
        protected final MinecraftClient client;

        protected AbstractEntry(MinecraftClient client) {
            this.client = client;
        }

        protected void renderWidgetAt(DrawContext context, int mouseX, int mouseY, float tickDelta, ClickableWidget widget, int x, int y) {
            widget.setX(x);
            widget.setY(y);
            widget.render(context, mouseX, mouseY, tickDelta);
        }
    }

    public static class Entry extends AbstractEntry {
        private final Option<?> option;

        private final List<ClickableWidget> children;
        private final ClickableWidget widget;

        public Entry(Option<?> option) {
            super(MinecraftClient.getInstance());
            this.option = option;
            this.children = new ArrayList<>();
            this.widget = this.addChild(option.createWidget(0, 0, 150, 20));
        }

        private <T extends ClickableWidget> T addChild(T widget) {
            this.children.add(widget);
            return widget;
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return this.children;
        }

        @Override
        public List<? extends Element> children() {
            return this.children;
        }

        @Override
        public void setFocused(boolean focused) {
            super.setFocused(focused);
            if (!focused) {
                this.setFocused(null);
            }
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextRenderer textRenderer = this.client.textRenderer;
            if (hovered) {
                context.fill(x, y, x + entryWidth, y + entryHeight, ColorHelper.getArgb(100, 200, 200, 200));
            }
            int textY = y + (entryHeight - textRenderer.fontHeight + 1) / 2;
            context.drawText(textRenderer, this.option.getName(), x + 5, textY, Colors.WHITE, true);
            this.renderWidgetAt(context, mouseX, mouseY, tickDelta, this.widget, x + 155, y);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }
}
