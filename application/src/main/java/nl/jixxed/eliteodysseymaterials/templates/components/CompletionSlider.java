package nl.jixxed.eliteodysseymaterials.templates.components;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.SliderBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextFieldBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;

import java.util.concurrent.TimeUnit;
import java.util.function.DoubleConsumer;

public class CompletionSlider extends HBox implements Template, Destroyable {

    private Double min;
    private Double max;
    private Double initial;
    private Slider progressSlider;
    private Disposable subscribe;

    public CompletionSlider(Double min, Double max, Double initial) {
        this.min = min;
        this.max = max;
        this.initial = initial;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        progressSlider = SliderBuilder.builder()
                .withMin(min)
                .withMax(max)
                .withValue(initial)
                .build();
        progressSlider.setFocusTraversable(false);
        progressSlider.setStyle("-fx-fit-to-width: true;-fx-max-width: 15em;-fx-pref-width: 15em;");
        final TextField textvalue = TextFieldBuilder.builder().withFocusTraversable(false).build();
        textvalue.textProperty().bind(progressSlider.valueProperty().map(number -> Formatters.NUMBER_FORMAT_2.format(number) + "%"));
        textvalue.setDisable(true);
        textvalue.setStyle("-fx-max-width: 4.5em;-fx-min-width: 4.5em;");
        this.getChildren().add(progressSlider);
        this.getChildren().add(textvalue);
    }


    @Override
    public void initEventHandling() {
        //NOOP
    }

    public void setChangeListener(final DoubleConsumer function) {
        if(subscribe != null) {
            subscribe.dispose();
        }
        subscribe = Observable.create((ObservableEmitter<Number> emitter) -> progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    emitter.onNext(newValue);
                }))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> {
                    Platform.runLater(() -> function.accept(newValue.doubleValue()));
                });
    }

    @Override
    public void destroy() {
        subscribe.dispose();
    }
}
