package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterialShow;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterialSort;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public
class OdysseyMaterialSearchBar extends HBox {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private TextField textField;
    private ComboBox<OdysseyMaterialShow> showMaterialsComboBox;
    private ComboBox<OdysseyMaterialSort> sortMaterialsComboBox;

    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    public OdysseyMaterialSearchBar() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("root");
        initSearchTextField();
        initSearchTextFilter();
        initSearchTextSort();

        setDefaultOptions();

        applyFontSizingHack();

        HBox.setHgrow(this.textField, Priority.ALWAYS);
//        HBox.setHgrow(this.showMaterialsComboBox, Priority.ALWAYS);
//        HBox.setHgrow(this.sortMaterialsComboBox, Priority.ALWAYS);

        this.getChildren().addAll(this.textField, this.showMaterialsComboBox, this.sortMaterialsComboBox);
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.showMaterialsComboBox.styleProperty().set(fontStyle);
        this.textField.styleProperty().set(fontStyle);
        this.sortMaterialsComboBox.styleProperty().set(fontStyle);
    }

    private void initSearchTextSort() {
        final Tooltip sortMaterialsTooltip = TooltipBuilder.builder()
                .withText(LocaleService.getStringBinding("search.sort.placeholder"))
                .build();
        this.sortMaterialsComboBox = ComboBoxBuilder.builder(OdysseyMaterialSort.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(OdysseyMaterialSort.ENGINEER_BLUEPRINT_IRRELEVANT, OdysseyMaterialSort.RELEVANT_IRRELEVANT, OdysseyMaterialSort.ALPHABETICAL, OdysseyMaterialSort.QUANTITY))
                .withPromptTextProperty(LocaleService.getStringBinding("search.sort.placeholder"))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), newValue, getShowOrDefault(this.showMaterialsComboBox))));
                        PreferencesService.setPreference("search.sort", newValue.name());
                    }
                })
                .asLocalized()
                .withToolTip(sortMaterialsTooltip)
                .build();
    }

    private void initSearchTextFilter() {
        final Tooltip showMaterialsTooltip = TooltipBuilder.builder().withText(LocaleService.getStringBinding("search.filter.placeholder")).build();
        this.showMaterialsComboBox = ComboBoxBuilder.builder(OdysseyMaterialShow.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(OdysseyMaterialShow.ALL,
                        OdysseyMaterialShow.ALL_WITH_STOCK,
                        OdysseyMaterialShow.ALL_ENGINEER_BLUEPRINT,
                        OdysseyMaterialShow.REQUIRED_ENGINEER_BLUEPRINT,
                        OdysseyMaterialShow.ALL_ENGINEER,
                        OdysseyMaterialShow.REQUIRED_ENGINEER,
                        OdysseyMaterialShow.BLUEPRINT,
                        OdysseyMaterialShow.IRRELEVANT,
                        OdysseyMaterialShow.IRRELEVANT_WITH_STOCK,
                        OdysseyMaterialShow.NOT_ON_WISHLIST,
                        OdysseyMaterialShow.BACKPACK,
                        OdysseyMaterialShow.FLEETCARRIER,
                        OdysseyMaterialShow.PROHIBITED,
                        OdysseyMaterialShow.FAVOURITES))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), newValue)));
                        PreferencesService.setPreference("search.filter", newValue.name());
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
        Observable.create((ObservableEmitter<String> emitter) -> this.textField.textProperty().addListener((observable, oldValue, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> EventService.publish(new SearchEvent(new Search(newValue, getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.showMaterialsComboBox)))));
    }


    private void initEventHandling() {
        //hack for component resizing on other fontsizes
        this.eventListeners.add(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.showMaterialsComboBox.styleProperty().set(fontStyle);
            this.textField.styleProperty().set(fontStyle);
            this.sortMaterialsComboBox.styleProperty().set(fontStyle);
        }));
        this.eventListeners.add(EventService.addListener(true, this, OdysseyTabSelectedEvent.class, event -> {
            if (OdysseyTabs.OVERVIEW.equals(event.getSelectedTab())) {
                this.textField.setDisable(false);
                this.showMaterialsComboBox.setDisable(false);
                this.sortMaterialsComboBox.setDisable(false);
            } else {
                this.textField.setDisable(true);
                this.showMaterialsComboBox.setDisable(true);
                this.sortMaterialsComboBox.setDisable(true);
            }
        }));
        this.eventListeners.add(EventService.addListener(true, this, SoloModeEvent.class, soloModeEvent ->
                EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.showMaterialsComboBox))))
        ));
    }

    private void setDefaultOptions() {
        try {
            final OdysseyMaterialSort materialSort = OdysseyMaterialSort.valueOf(PreferencesService.getPreference("search.sort", "ALPHABETICAL"));
            this.sortMaterialsComboBox.getSelectionModel().select(materialSort);
        } catch (final IllegalArgumentException ex) {
            log.error("sort error", ex);
        }

        try {
            final OdysseyMaterialShow filter = OdysseyMaterialShow.valueOf(PreferencesService.getPreference("search.filter", "ALL"));
            this.showMaterialsComboBox.getSelectionModel().select(filter);
        } catch (final IllegalArgumentException ex) {
            log.error("filter error", ex);
        }
    }


    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }

    private OdysseyMaterialShow getShowOrDefault(final ComboBox<OdysseyMaterialShow> showMaterialsComboBox) {
        return (showMaterialsComboBox.getValue() != null) ? showMaterialsComboBox.getValue() : OdysseyMaterialShow.ALL;
    }

    private OdysseyMaterialSort getSortOrDefault(final ComboBox<OdysseyMaterialSort> sortMaterialsComboBox) {
        return (sortMaterialsComboBox.getValue() != null) ? sortMaterialsComboBox.getValue() : OdysseyMaterialSort.ALPHABETICAL;
    }

}
