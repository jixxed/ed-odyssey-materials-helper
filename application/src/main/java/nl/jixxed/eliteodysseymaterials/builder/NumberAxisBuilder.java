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
