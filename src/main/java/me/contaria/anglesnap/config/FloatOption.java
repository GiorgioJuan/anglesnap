package me.contaria.anglesnap.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class FloatOption extends Option<Float> {
    private final float min;
    private final float max;

    private float value;

    protected FloatOption(String id, float min, float max, float defaultValue) {
        super(id);
        this.min = min;
        this.max = max;
        this.setValue(defaultValue);
    }

    @Override
    public Float getValue() {
        return this.value;
    }

    @Override
    public void setValue(Float value) {
        this.value = Math.min(Math.max(value, this.min), this.max);
    }

    @Override
    public boolean hasWidget() {
        return true;
    }

    @Override
    public ClickableWidget createWidget(int x, int y, int width, int height) {
        return new SliderWidget(x, y, width, height, FloatOption.this.getMessage(), ((double) this.getValue() - this.min) / (FloatOption.this.max - FloatOption.this.min)) {
            @Override
            protected void updateMessage() {
                this.setMessage(FloatOption.this.getMessage());
            }

            @Override
            protected void applyValue() {
                FloatOption.this.setValue(FloatOption.this.min + (float) ((FloatOption.this.max - FloatOption.this.min) * this.value));
            }
        };
    }

    @Override
    public Text getDefaultMessage() {
        return Text.literal(String.valueOf(Math.round(FloatOption.this.getValue() * 100.0f) / 100.0f));
    }

    @Override
    protected void fromJson(JsonElement jsonElement) {
        this.setValue(jsonElement.getAsFloat());
    }

    @Override
    protected JsonElement toJson() {
        return new JsonPrimitive(this.getValue());
    }
}
