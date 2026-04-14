/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.components;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.SliderBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextFieldBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSlider;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTextField;

import java.util.concurrent.TimeUnit;
import java.util.function.DoubleConsumer;

@Slf4j
public class CompletionSlider extends DestroyableHBox implements DestroyableTemplate {

    private Double min;
    private Double max;
    private Double initial;
    private DestroyableSlider progressSlider;
    private Disposable subscribe;
    private DoubleConsumer onChange;

    public CompletionSlider(Double min, Double max, Double initial, DoubleConsumer onChange) {
        this.min = min;
        this.max = max;
        this.initial = initial;
        this.onChange = onChange;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("completion-slider");
        progressSlider = SliderBuilder.builder()
                .withStyleClass("slider")
                .withMin(min)
                .withMax(max)
                .withValue(initial)
                .withFocusTraversable(false)
                .build();

        final DestroyableTextField textValue = TextFieldBuilder.builder()
                .withDisable(true)
                .withStyleClass("value")
                .withFocusTraversable(false)
                .build();
        textValue.addBinding(textValue.textProperty(), progressSlider.valueProperty().map(number -> Formatters.NUMBER_FORMAT_2.format(number) + "%"));

        subscribe = Observable.create((ObservableEmitter<Number> emitter) -> addChangeListener(progressSlider.valueProperty(), (_, _, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(() -> {
                            if (onChange != null) onChange.accept(newValue.doubleValue());
                        }),
                        t -> log.error(t.getMessage(), t));


        this.getNodes().addAll(progressSlider, textValue);
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
        onChange = null;
    }
}
