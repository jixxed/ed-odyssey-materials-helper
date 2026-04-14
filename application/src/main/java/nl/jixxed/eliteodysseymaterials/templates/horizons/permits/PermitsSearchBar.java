/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.permits;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextFieldBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.PermitsSearch;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsPermitsShow;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PermitSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class PermitsSearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private DestroyableTextField textField;
    private Disposable subscribe;
    private DestroyableComboBox<HorizonsPermitsShow> showPermitsComboBox;


    public PermitsSearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("root");
        initSearchTextField();
        initSearchTextFilter();
        applyFontSizingHack();

        HBox.setHgrow(this.textField, Priority.ALWAYS);

        this.getNodes().addAll(this.textField, this.showPermitsComboBox);
    }

    public void initEventHandling() {
        //hack for component resizing on other fontsizes
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.textField.styleProperty().set(fontStyle);
        }));
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.textField.styleProperty().set(fontStyle);
    }

    private void initSearchTextFilter() {
        final DestroyableTooltip showMaterialsTooltip = TooltipBuilder.builder()
                .withText("search.filter.placeholder")
                .build();
        this.showPermitsComboBox = ComboBoxBuilder.builder(HorizonsPermitsShow.class)
                .withStyleClasses("root", "filter-and-sort")
                .withSelected(HorizonsPermitsShow.valueOf(PreferencesService.getPreference("search.horizons.permits.filter", "ALL")))
                .withItemsProperty(LocaleService.getListBinding(
                        HorizonsPermitsShow.ALL,
                        HorizonsPermitsShow.ALL_EXCEPT_STARTER,
                        HorizonsPermitsShow.FEDERAL_NAVY_RANK,
                        HorizonsPermitsShow.IMPERIAL_NAVY_RANK,
                        HorizonsPermitsShow.ALLIED,
                        HorizonsPermitsShow.GAME_RANK,
                        HorizonsPermitsShow.STARTER,
                        HorizonsPermitsShow.FREE
                ))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new PermitSearchEvent(new PermitsSearch(getQueryOrDefault(this.textField),getShowOrDefault(this.showPermitsComboBox))));
                        PreferencesService.setPreference("search.horizons.permits.filter", newValue.name());
                    }
                })
                .asLocalized()
                .withPromptTextProperty(LocaleService.getStringBinding("search.filter.placeholder"))
                .withToolTip(showMaterialsTooltip)
                .build();
    }

    private void initSearchTextField() {
        this.textField = TextFieldBuilder.builder()
                .withStyleClasses("root", "search-input")
                .withPromptTextProperty(LocaleService.getStringBinding("search.text.placeholder"))
                .withFocusTraversable(false)
                .build();
        subscribe = Observable
                .create((ObservableEmitter<String> emitter) -> this.textField.textProperty().addListener((_, _, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(() -> EventService.publish(new PermitSearchEvent(new PermitsSearch(getQueryOrDefault(this.textField),getShowOrDefault(this.showPermitsComboBox))))));
    }

    @SuppressWarnings("java:S1144")
    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }
    private HorizonsPermitsShow getShowOrDefault(final ComboBox<HorizonsPermitsShow> permitsShowComboBox) {
        return (permitsShowComboBox.getValue() != null) ? permitsShowComboBox.getValue() : HorizonsPermitsShow.ALL;
    }


    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
