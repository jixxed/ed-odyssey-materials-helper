package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ChangeListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSlider;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SliderBuilder extends AbstractControlBuilder<SliderBuilder> {
    private double value;
    private double min;
    private double max;
    private ChangeListener<Number> changeListener;
    private Integer majorTickUnit;

    public static SliderBuilder builder() {
        return new SliderBuilder();
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

    public SliderBuilder withMajorTickUnit(Integer majorTickUnit) {
        this.majorTickUnit = majorTickUnit;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableSlider build() {
        final DestroyableSlider slider = new DestroyableSlider(this.min, this.max, this.value);
        super.build(slider);

        if (this.majorTickUnit != null) {
            slider.setMinorTickCount(0);
            slider.setMajorTickUnit(majorTickUnit);
            slider.setSnapToTicks(true);
        }
        if (this.changeListener != null) {
            slider.addChangeListener(slider.valueProperty(), this.changeListener);
        }

        return slider;
    }
}
