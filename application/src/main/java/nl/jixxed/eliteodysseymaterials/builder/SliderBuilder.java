/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
            slider.setBlockIncrement(1);
        }
        if (this.changeListener != null) {
            slider.addChangeListener(slider.valueProperty(), this.changeListener);
        }

        return slider;
    }
}
