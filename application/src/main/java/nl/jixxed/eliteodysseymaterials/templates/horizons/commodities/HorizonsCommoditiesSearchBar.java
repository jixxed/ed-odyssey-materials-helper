package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

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
import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsCommoditiesShow;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsCommoditiesSort;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsCommoditiesSearchEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public
class HorizonsCommoditiesSearchBar extends HBox {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private TextField textField;
    private ComboBox<HorizonsCommoditiesShow> showMaterialsComboBox;
    private ComboBox<HorizonsCommoditiesSort> sortMaterialsComboBox;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public HorizonsCommoditiesSearchBar() {
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
        this.sortMaterialsComboBox = ComboBoxBuilder.builder(HorizonsCommoditiesSort.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(HorizonsCommoditiesSort.ALPHABETICAL, HorizonsCommoditiesSort.QUANTITY_SHIP, HorizonsCommoditiesSort.QUANTITY_FLEETCARRIER, HorizonsCommoditiesSort.QUANTITY_TOTAL))
                .withPromptTextProperty(LocaleService.getStringBinding("search.sort.placeholder"))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new HorizonsCommoditiesSearchEvent(new CommoditiesSearch(getQueryOrDefault(this.textField), newValue, getShowOrDefault(this.showMaterialsComboBox))));
                        PreferencesService.setPreference("search.commodities.sort", newValue.name());
                    }
                })
                .asLocalized()
                .withToolTip(sortMaterialsTooltip)
                .build();
    }

    private void initSearchTextFilter() {
        final Tooltip showMaterialsTooltip = TooltipBuilder.builder().withText(LocaleService.getStringBinding("search.filter.placeholder")).build();
        this.showMaterialsComboBox = ComboBoxBuilder.builder(HorizonsCommoditiesShow.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(HorizonsCommoditiesShow.ALL,
                        HorizonsCommoditiesShow.ALL_WITH_STOCK,
                        HorizonsCommoditiesShow.SHIP,
                        HorizonsCommoditiesShow.FLEETCARRIER,
                        HorizonsCommoditiesShow.CHEMICALS,
                        HorizonsCommoditiesShow.CONSUMER_ITEMS,
                        HorizonsCommoditiesShow.FOODS,
                        HorizonsCommoditiesShow.INDUSTRIAL_MATERIALS,
                        HorizonsCommoditiesShow.LEGAL_DRUGS,
                        HorizonsCommoditiesShow.MACHINERY,
                        HorizonsCommoditiesShow.MEDICINES,
                        HorizonsCommoditiesShow.METALS,
                        HorizonsCommoditiesShow.MINERALS,
                        HorizonsCommoditiesShow.NONMARKETABLE,
                        HorizonsCommoditiesShow.SALVAGE,
                        HorizonsCommoditiesShow.SLAVERY,
                        HorizonsCommoditiesShow.TECHNOLOGY,
                        HorizonsCommoditiesShow.TEXTILES,
                        HorizonsCommoditiesShow.WASTE,
                        HorizonsCommoditiesShow.WEAPONS,
                        HorizonsCommoditiesShow.POWERPLAY))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new HorizonsCommoditiesSearchEvent(new CommoditiesSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.showMaterialsComboBox))));
                        PreferencesService.setPreference("search.commodities.filter", newValue.name());
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
                .subscribe(newValue -> EventService.publish(new HorizonsCommoditiesSearchEvent(new CommoditiesSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.showMaterialsComboBox)))));
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
//        eventListeners.add(EventService.addListener(true, this, OdysseyTabSelectedEvent.class, event -> {
//            if (OdysseyTabs.OVERVIEW.equals(event.getSelectedTab())) {
//                this.textField.setDisable(false);
//                this.showMaterialsComboBox.setDisable(false);
//                this.sortMaterialsComboBox.setDisable(false);
//            } else {
//                this.textField.setDisable(true);
//                this.showMaterialsComboBox.setDisable(true);
//                this.sortMaterialsComboBox.setDisable(true);
//            }
//        });
    }

    private void setDefaultOptions() {
        try {
            final HorizonsCommoditiesSort materialSort = HorizonsCommoditiesSort.valueOf(PreferencesService.getPreference("search.commodities.sort", "ALPHABETICAL"));
            this.sortMaterialsComboBox.getSelectionModel().select(materialSort);
        } catch (final IllegalArgumentException ex) {
            log.error("sort error", ex);
        }

        try {
            final HorizonsCommoditiesShow filter = HorizonsCommoditiesShow.valueOf(PreferencesService.getPreference("search.commodities.filter", "ALL"));
            this.showMaterialsComboBox.getSelectionModel().select(filter);
        } catch (final IllegalArgumentException ex) {
            log.error("filter error", ex);
        }
    }


    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }

    private HorizonsCommoditiesShow getShowOrDefault(final ComboBox<HorizonsCommoditiesShow> showMaterialsComboBox) {
        return (showMaterialsComboBox.getValue() != null) ? showMaterialsComboBox.getValue() : HorizonsCommoditiesShow.ALL;
    }

    private HorizonsCommoditiesSort getSortOrDefault(final ComboBox<HorizonsCommoditiesSort> sortMaterialsComboBox) {
        return (sortMaterialsComboBox.getValue() != null) ? sortMaterialsComboBox.getValue() : HorizonsCommoditiesSort.ALPHABETICAL;
    }

}
