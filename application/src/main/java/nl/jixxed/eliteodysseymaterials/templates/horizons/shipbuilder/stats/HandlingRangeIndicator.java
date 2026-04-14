/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTooltip;

public class HandlingRangeIndicator extends RangeIndicator implements DestroyableEventTemplate {

    private DestroyableLabel currentBoostedValueLabel;
    private double currentBoostedValue;


    public HandlingRangeIndicator(double startValue, double endValue, double currentValue, double currentBoostedValue, String title, String currentValueLocalizationKey) {
        this.currentBoostedValue = currentBoostedValue;
        super(startValue, endValue, currentValue, title, currentValueLocalizationKey);
    }

    @Override
    public void initComponents() {
        super.initComponents();
        this.getStyleClass().add("handling-range-indicator");

        this.currentBoostedValueLabel = LabelBuilder.builder()
                .withStyleClass("current-boost")
                .build();
        DestroyableTooltip boostTooltip = TooltipBuilder.builder()
                .withStyleClass("handling-tooltip")
                .withShowDelay(Duration.millis(100))
                .withText("handling.tooltip.boost")
                .build();
        boostTooltip.install(currentBoostedValueLabel);
        var boost = BoxBuilder.builder()
                .withNodes(new GrowingRegion(), this.currentBoostedValueLabel, new GrowingRegion())
                .withStyleClass("values")
                .buildHBox();
        update();
        this.getNodes().addAll(boost);
    }

    @Override
    public void initEventHandling() {
        super.initEventHandling();
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
    }

    public void updateValues(double startValue, double currentValue, double currentBoostedValue, double endValue) {
        super.updateValues(startValue, currentValue, endValue);
        this.currentBoostedValue = currentBoostedValue;
        update();
    }

    private void update() {
        this.currentBoostedValueLabel.addBinding(this.currentBoostedValueLabel.textProperty(), LocaleService.getStringBinding(getCurrentValueLocalizationKey(), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(currentBoostedValue)));
    }
}
