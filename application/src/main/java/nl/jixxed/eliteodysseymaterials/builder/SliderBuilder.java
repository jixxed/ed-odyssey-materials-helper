package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Slider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SliderBuilder {
    private double value, min, max;
    private final List<String> styleClasses = new ArrayList<>();
    private ChangeListener<Number> changeListener;

    public static SliderBuilder builder() {
        return new SliderBuilder();
    }

    public SliderBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public SliderBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public SliderBuilder withChangeListener(final ChangeListener<Number> changeListener) {
        this.changeListener = changeListener;
        return this;
    }

    public SliderBuilder withValue(final double value) {
        this.value = value;
        return this;
    }

    public SliderBuilder withMin(final double min) {
        this.min = min;
        return this;
    }

    public SliderBuilder withMax(final double max) {
        this.max = max;
        return this;
    }

    public Slider build() {
        final Slider slider = new Slider(this.min, this.max, this.value);
        slider.getStyleClass().addAll(this.styleClasses);
        if (this.changeListener != null) {
            slider.valueProperty().addListener(this.changeListener);
        }
        return slider;
    }
}
