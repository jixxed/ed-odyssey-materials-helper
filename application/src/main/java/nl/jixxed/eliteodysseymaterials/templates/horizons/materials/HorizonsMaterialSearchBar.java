package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextFieldBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsMaterialsSearch;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialsShow;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsMaterialSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsTabSelectedEvent;

import java.util.concurrent.TimeUnit;

@Slf4j
public
class HorizonsMaterialSearchBar extends HBox {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private TextField textField;
    private ComboBox<HorizonsMaterialsShow> showMaterialsComboBox;


    public HorizonsMaterialSearchBar() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("root");
        initSearchTextField();
        initSearchTextFilter();
        applyFontSizingHack();
        setDefaultOptions();
        HBox.setHgrow(this.textField, Priority.ALWAYS);

        this.getChildren().addAll(this.textField, this.showMaterialsComboBox);
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.textField.styleProperty().set(fontStyle);
    }


    private void initSearchTextField() {
        this.textField = TextFieldBuilder.builder()
                .withStyleClasses("root", "search-input")
                .withPromptTextProperty(LocaleService.getStringBinding("search.text.placeholder"))
                .withFocusTraversable(false)
                .build();
        Observable.create((ObservableEmitter<String> emitter) -> this.textField.textProperty().addListener((observable, oldValue, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(() -> EventService.publish(new HorizonsMaterialSearchEvent(new HorizonsMaterialsSearch(newValue, getShowOrDefault(this.showMaterialsComboBox))))));
    }

    private void initSearchTextFilter() {
        final Tooltip showMaterialsTooltip = TooltipBuilder.builder().withText(LocaleService.getStringBinding("search.filter.placeholder")).build();
        this.showMaterialsComboBox = ComboBoxBuilder.builder(HorizonsMaterialsShow.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(HorizonsMaterialsShow.ALL,
                        HorizonsMaterialsShow.RAW,
                        HorizonsMaterialsShow.ENCODED,
                        HorizonsMaterialsShow.MANUFACTURED,
                        HorizonsMaterialsShow.GUARDIAN,
                        HorizonsMaterialsShow.THARGOID,
                        HorizonsMaterialsShow.HUMAN))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new HorizonsMaterialSearchEvent(new HorizonsMaterialsSearch(getQueryOrDefault(this.textField), getShowOrDefault(this.showMaterialsComboBox))));
                        PreferencesService.setPreference("search.horizons.materials.filter", newValue.name());
                    }
                })
                .asLocalized()
                .withPromptTextProperty(LocaleService.getStringBinding("search.filter.placeholder"))
                .withToolTip(showMaterialsTooltip)
                .build();
    }


    private void initEventHandling() {
        //hack for component resizing on other fontsizes
        this.eventListeners.add(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.textField.styleProperty().set(fontStyle);
        }));
        this.eventListeners.add(EventService.addListener(true, this, HorizonsTabSelectedEvent.class, event -> {
            this.textField.setDisable(!HorizonsTabs.MATERIALS.equals(event.getSelectedTab()));
            this.showMaterialsComboBox.setDisable(!HorizonsTabs.MATERIALS.equals(event.getSelectedTab()));
        }));
    }

    private void setDefaultOptions() {

        try {
            final HorizonsMaterialsShow filter = HorizonsMaterialsShow.valueOf(PreferencesService.getPreference("search.horizons.materials.filter", "ALL"));
            this.showMaterialsComboBox.getSelectionModel().select(filter);
        } catch (final IllegalArgumentException ex) {
            log.error("filter error", ex);
        }
    }

    @SuppressWarnings("java:S1144")
    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }

    private HorizonsMaterialsShow getShowOrDefault(final ComboBox<HorizonsMaterialsShow> showMaterialsComboBox) {
        return (showMaterialsComboBox.getValue() != null) ? showMaterialsComboBox.getValue() : HorizonsMaterialsShow.ALL;
    }

}
