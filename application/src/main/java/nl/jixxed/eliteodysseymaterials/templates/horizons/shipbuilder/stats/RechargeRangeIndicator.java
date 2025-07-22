package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.css.PseudoClass;
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

public class RechargeRangeIndicator extends DestroyableVBox implements DestroyableEventTemplate {


    private DestroyablePane lines;
    private DestroyableHBox values;
    private DestroyableLine bar;
    private DestroyableLine start;
    private DestroyableLine recharge;
    private DestroyableLine end;
    private DestroyableLine current;
    private DestroyableLabel startValueLabel;
    private DestroyableLabel endValueLabel;
    private DestroyableLabel currentValueLabel;
    private DestroyableLabel titleLabel;
    private double startValue;
    private double endValue;
    private double currentValue;
    private double rechargeValue;
    private String title;
    private String currentValueLocalizationKey;


    public RechargeRangeIndicator(double startValue, double endValue, double currentValue, double rechargeValue, String title, String currentValueLocalizationKey) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.currentValue = currentValue;
        this.rechargeValue = rechargeValue;
        this.title = title;
        this.currentValueLocalizationKey = currentValueLocalizationKey;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("range-indicator");
        this.startValueLabel = LabelBuilder.builder()
                .build();
        this.endValueLabel = LabelBuilder.builder()
                .build();
        this.currentValueLabel = LabelBuilder.builder()
                .withStyleClass("current")
                .build();
        this.titleLabel = LabelBuilder.builder()
                .withText(title)
                .build();
        this.values = BoxBuilder.builder()
                .withNodes(this.startValueLabel, new GrowingRegion(), this.currentValueLabel, new GrowingRegion(), this.endValueLabel)
                .withStyleClass("values")
                .buildHBox();
        bar = new DestroyableLine(0, 0, 0, 0);
        start = new DestroyableLine(0, 0, 0, 0);
        end = new DestroyableLine(0, 0, 0, 0);
        recharge = new DestroyableLine(0, 0, 0, 0);
        current = new DestroyableLine(0, 0, 0, 0);
        bar.getStyleClass().add("line");
        start.getStyleClass().add("line");
        recharge.getStyleClass().add("line");
        end.getStyleClass().add("line");
        current.getStyleClass().addAll("line", "line-current");
        lines = PaneBuilder.builder()
                .withNodes(bar, start, end, recharge, current)
                .build();
        update();
        this.getNodes().addAll(this.titleLabel, lines, values);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
    }

    public void updateValues(double startValue, double currentValue, double endValue, double rechargeValue) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.currentValue = currentValue;
        this.rechargeValue = rechargeValue;

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
        double interval = (Double.isNaN(rechargeValue) || endValue == 0d) ? 0D : (rechargeValue - startValue) / (endValue - startValue);
        if (rechargeValue >= startValue) {
            recharge.setEndX(sliderWidth * interval);
            recharge.setStartX(sliderWidth * interval);
            recharge.setEndY(sliderHeight / 2);
        } else {
            recharge.setEndX(0);
            recharge.setStartX(0);
            recharge.setEndY(0);
        }
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
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("optimal"), !Double.isNaN(rechargeValue) && currentValue <= rechargeValue);
    }
}
