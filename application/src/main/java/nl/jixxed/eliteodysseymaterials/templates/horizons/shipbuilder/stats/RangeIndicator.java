package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

import java.util.ArrayList;
import java.util.List;

public class RangeIndicator extends VBox {
    protected final List<EventListener<?>> eventListeners = new ArrayList<>();

    private Pane lines;
    private HBox values;
    private Line bar;
    private Line start;
    private Line end;
    private Line current;
    private Label startValueLabel;
    private Label endValueLabel;
    private Label currentValueLabel;
    private Label titleLabel;
    private double startValue;
    private double endValue;
    private double currentValue;

    private  String currentValueLocalizationKey;
    public RangeIndicator(double startValue, double endValue, double currentValue, String title,String currentValueLocalizationKey) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.currentValue = currentValue;
        this.currentValueLocalizationKey = currentValueLocalizationKey;
        this.startValueLabel = LabelBuilder.builder().build();
        this.endValueLabel = LabelBuilder.builder().build();
        this.currentValueLabel = LabelBuilder.builder().withStyleClass("range-indicator-current").build();
        this.titleLabel = LabelBuilder.builder().withText(LocaleService.getStringBinding(title)).build();
        this.values = BoxBuilder.builder().withNodes(this.startValueLabel, new GrowingRegion(), this.currentValueLabel, new GrowingRegion(), this.endValueLabel).withStyleClass("range-indicator-values").buildHBox();
        bar = new Line(0,0,0, 0);
        start = new Line(0,0,0, 0);
        end = new Line(0,0,0, 0);
        current = new Line(0,0,0, 0);
        bar.getStyleClass().add("range-indicator-line");
        start.getStyleClass().add("range-indicator-line");
        end.getStyleClass().add("range-indicator-line");
        current.getStyleClass().addAll("range-indicator-line", "range-indicator-line-current");
        lines = new Pane(bar, start, end, current);
        this.getStyleClass().add("range-indicator");
        update();
        this.getChildren().addAll(this.titleLabel, lines, values);
        initEventHandling();
    }

    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
    }

    public void updateValues(double startValue, double currentValue, double endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.currentValue = currentValue;
        update();
    }
    public void update(){
        this.startValueLabel.setText(Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(startValue));
        this.endValueLabel.setText(Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(endValue));
        this.currentValueLabel.textProperty().bind(LocaleService.getStringBinding(currentValueLocalizationKey, Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(currentValue)));

        double sliderWidth = ScalingHelper.getPixelDoubleFromEm(15D);
        double sliderHeight = ScalingHelper.getPixelDoubleFromEm(1D);
        bar.setEndX(sliderWidth);
        bar.setStartY(sliderHeight / 2);
        bar.setEndY(sliderHeight / 2);

        start.setEndY(sliderHeight / 2);

        end.setEndX(sliderWidth);
        end.setStartX(sliderWidth);
        end.setEndY(sliderHeight / 2);
        if(Double.isFinite(currentValue) && Double.isFinite(endValue) && Double.isFinite(startValue) && !(currentValue <= startValue) && !(currentValue >= endValue)) {
            double progress = (Double.isNaN(currentValue) || endValue == 0d) ? 0D : (currentValue - startValue) / (endValue - startValue);
            current.setStartX(sliderWidth * progress);
            current.setEndX(sliderWidth * progress);
            current.setEndY(sliderHeight);
        }else
        if(Double.isInfinite(currentValue) && (Double.isInfinite(endValue) || (Double.isFinite(startValue) && Double.isFinite(endValue) && endValue >= startValue))){
            current.setStartX(end.getEndX());
            current.setEndX(end.getEndX());
            current.setEndY(sliderHeight);

        }else
        if(Double.isInfinite(currentValue) && (Double.isInfinite(startValue) || (Double.isFinite(startValue) && Double.isFinite(endValue) && startValue > endValue))){
            current.setStartX(start.getEndX());
            current.setEndX(start.getEndX());
            current.setEndY(sliderHeight);
        }else
        if(currentValue <= startValue){
            current.setStartX(start.getEndX());
            current.setEndX(start.getEndX());
            current.setEndY(sliderHeight);
        }else
        if(currentValue >= endValue){
            current.setStartX(end.getEndX());
            current.setEndX(end.getEndX());
            current.setEndY(sliderHeight);
        }
    }
}
