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

import javafx.scene.chart.NumberAxis;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class NumberAxisBuilder {
    private Double min;
    private Double max;
    private Double tickUnit;
    private String localeKey;
    private Boolean autoRanging;

    public static NumberAxisBuilder builder() {
        return new NumberAxisBuilder();
    }

    public NumberAxisBuilder withLowerBound(final Double min) {
        this.min = min;
        return this;
    }

    public NumberAxisBuilder withUpperBound(final Double max) {
        this.max = max;
        return this;
    }

    public NumberAxisBuilder withTickUnit(final Double tickUnit) {
        this.tickUnit = tickUnit;
        return this;
    }

    public NumberAxisBuilder withLabel(final String localeKey) {
        this.localeKey = localeKey;
        return this;
    }

    public NumberAxisBuilder withAutoRanging(Boolean autoRanging) {
        this.autoRanging = autoRanging;
        return this;
    }

    @SuppressWarnings("unchecked")
    public NumberAxis build() {
        final NumberAxis numberAxis = new NumberAxis();
        if (this.min != null) {
            numberAxis.setLowerBound(this.min);
        }
        if (this.max != null) {
            numberAxis.setUpperBound(this.max);
        }
        if (this.tickUnit != null) {
            numberAxis.setTickUnit(this.tickUnit);
        }
        if (this.localeKey != null) {
            numberAxis.labelProperty().bind(LocaleService.getStringBinding(this.localeKey));
        }
        if (this.autoRanging != null) {
            numberAxis.setAutoRanging(this.autoRanging);
        }
        return numberAxis;
    }

}
