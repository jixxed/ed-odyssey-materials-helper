package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.PaneBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class RangeIndicator extends DestroyableVBox implements DestroyableEventTemplate {


    private DestroyablePane lines;
    private DestroyableHBox values;
    private DestroyableLine bar;
    private DestroyableLine start;
    private DestroyableLine end;
    private DestroyableLine current;
    private DestroyableLabel startValueLabel;
    private DestroyableLabel endValueLabel;
    private DestroyableLabel currentValueLabel;
    private DestroyableLabel titleLabel;
    private double startValue;
    private double endValue;
    private double currentValue;

    private final String title;
    private String currentValueLocalizationKey;

    public RangeIndicator(double startValue, double endValue, double currentValue, String title, String currentValueLocalizationKey) {
        this.startValue = startValue;
        this.title = title;
        this.endValue = endValue;
        this.currentValue = currentValue;
        this.currentValueLocalizationKey = currentValueLocalizationKey;

        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.startValueLabel = LabelBuilder.builder()
                .build();
        this.endValueLabel = LabelBuilder.builder()
                .build();
        this.currentValueLabel = LabelBuilder.builder()
                .withStyleClass("range-indicator-current")
                .build();
        this.titleLabel = LabelBuilder.builder()
                .withText(title)
                .build();
        this.values = BoxBuilder.builder()
                .withNodes(this.startValueLabel, new GrowingRegion(), this.currentValueLabel, new GrowingRegion(), this.endValueLabel)
                .withStyleClass("range-indicator-values")
                .buildHBox();
        bar = new DestroyableLine(0, 0, 0, 0);
        start = new DestroyableLine(0, 0, 0, 0);
        end = new DestroyableLine(0, 0, 0, 0);
        current = new DestroyableLine(0, 0, 0, 0);
        bar.getStyleClass().add("range-indicator-line");
        start.getStyleClass().add("range-indicator-line");
        end.getStyleClass().add("range-indicator-line");
        current.getStyleClass().addAll("range-indicator-line", "range-indicator-line-current");
        lines = PaneBuilder.builder()
                .withNodes(bar, start, end, current)
                .build();
        this.getStyleClass().add("range-indicator");
        update();
        this.getNodes().addAll(this.titleLabel, lines, values);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
    }

    public void updateValues(double startValue, double currentValue, double endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.currentValue = currentValue;
        update();
    }

    public void update() {
        this.startValueLabel.setText(Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(startValue));
        this.endValueLabel.setText(Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(endValue));
        this.currentValueLabel.addBinding(this.currentValueLabel.textProperty(), LocaleService.getStringBinding(currentValueLocalizationKey, Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(currentValue)));

        double sliderWidth = ScalingHelper.getPixelDoubleFromEm(15D);
        double sliderHeight = ScalingHelper.getPixelDoubleFromEm(1D);
        bar.setEndX(sliderWidth);
        bar.setStartY(sliderHeight / 2);
        bar.setEndY(sliderHeight / 2);

        start.setEndY(sliderHeight / 2);

        end.setEndX(sliderWidth);
        end.setStartX(sliderWidth);
        end.setEndY(sliderHeight / 2);
        if (Double.isFinite(currentValue) && Double.isFinite(endValue) && Double.isFinite(startValue) && !(currentValue <= startValue) && !(currentValue >= endValue)) {
            double progress = (Double.isNaN(currentValue) || endValue == 0d) ? 0D : (currentValue - startValue) / (endValue - startValue);
            current.setStartX(sliderWidth * progress);
            current.setEndX(sliderWidth * progress);
            current.setEndY(sliderHeight);
        } else if (Double.isInfinite(currentValue) && (Double.isInfinite(endValue) || (Double.isFinite(startValue) && Double.isFinite(endValue) && endValue >= startValue))) {
            current.setStartX(end.getEndX());
            current.setEndX(end.getEndX());
            current.setEndY(sliderHeight);
        } else if (Double.isInfinite(currentValue) && (Double.isInfinite(startValue) || (Double.isFinite(startValue) && Double.isFinite(endValue) && startValue > endValue))) {
            current.setStartX(start.getEndX());
            current.setEndX(start.getEndX());
            current.setEndY(sliderHeight);
        } else if (currentValue <= startValue) {
            current.setStartX(start.getEndX());
            current.setEndX(start.getEndX());
            current.setEndY(sliderHeight);
        } else if (currentValue >= endValue) {
            current.setStartX(end.getEndX());
            current.setEndX(end.getEndX());
            current.setEndY(sliderHeight);
        }
    }
}
