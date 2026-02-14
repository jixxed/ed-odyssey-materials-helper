package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

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
import nl.jixxed.eliteodysseymaterials.domain.HorizonsEngineersSearch;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsEngineersShow;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsEngineerSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HorizonsEngineerSearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private DestroyableTextField textField;
    private Disposable subscribe;
    private DestroyableComboBox<HorizonsEngineersShow> showEngineersComboBox;


    public HorizonsEngineerSearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("root");
        initSearchTextField();
        initSearchTextFilter();
        applyFontSizingHack();

        HBox.setHgrow(this.textField, Priority.ALWAYS);

        this.getNodes().addAll(this.textField, this.showEngineersComboBox);
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
        this.showEngineersComboBox = ComboBoxBuilder.builder(HorizonsEngineersShow.class)
                .withStyleClasses("root", "filter-and-sort")
                .withSelected(HorizonsEngineersShow.valueOf(PreferencesService.getPreference("search.horizons.engineers.filter", "ALL")))
                .withItemsProperty(LocaleService.getListBinding(
                        HorizonsEngineersShow.ALL,
                        HorizonsEngineersShow.COLONIA,
                        HorizonsEngineersShow.BUBBLE
                ))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new HorizonsEngineerSearchEvent(new HorizonsEngineersSearch(getQueryOrDefault(this.textField),getShowOrDefault(this.showEngineersComboBox))));
                        PreferencesService.setPreference("search.horizons.engineers.filter", newValue.name());
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
                .subscribe(newValue -> Platform.runLater(() -> EventService.publish(new HorizonsEngineerSearchEvent(new HorizonsEngineersSearch(getQueryOrDefault(this.textField),getShowOrDefault(this.showEngineersComboBox))))));
    }

    @SuppressWarnings("java:S1144")
    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }
    private HorizonsEngineersShow getShowOrDefault(final ComboBox<HorizonsEngineersShow> showEngineersComboBox) {
        return (showEngineersComboBox.getValue() != null) ? showEngineersComboBox.getValue() : HorizonsEngineersShow.ALL;
    }


    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
