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
