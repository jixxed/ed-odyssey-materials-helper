package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsCommoditiesSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HorizonsCommoditiesSearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private DestroyableTextField textField;
    private DestroyableComboBox<HorizonsCommoditiesShow> showMaterialsComboBox;
    private DestroyableComboBox<HorizonsCommoditiesSort> sortMaterialsComboBox;
    private Disposable subscribe;


    public HorizonsCommoditiesSearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("root");
        initSearchTextField();
        initSearchTextFilter();
        initSearchTextSort();

//        applyFontSizingHack();

        HBox.setHgrow(this.textField, Priority.ALWAYS);

        this.getNodes().addAll(this.textField, this.showMaterialsComboBox, this.sortMaterialsComboBox);
    }

    public void initEventHandling() {
        //hack for component resizing on other fontsizes
//        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
//            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
//            this.styleProperty().set(fontStyle);
//            this.showMaterialsComboBox.styleProperty().set(fontStyle);
//            this.textField.styleProperty().set(fontStyle);
//            this.sortMaterialsComboBox.styleProperty().set(fontStyle);
//        }));
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
        final DestroyableTooltip sortMaterialsTooltip = TooltipBuilder.builder()
                .withText("search.sort.placeholder")
                .build();
        this.sortMaterialsComboBox = ComboBoxBuilder.builder(HorizonsCommoditiesSort.class)
                .withStyleClasses("root", "filter-and-sort")
                .withSelected(HorizonsCommoditiesSort.valueOf(PreferencesService.getPreference("search.commodities.sort", "ALPHABETICAL")))
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
        final DestroyableTooltip showMaterialsTooltip = TooltipBuilder.builder()
                .withText("search.filter.placeholder")
                .build();
        this.showMaterialsComboBox = ComboBoxBuilder.builder(HorizonsCommoditiesShow.class)
                .withStyleClasses("root", "filter-and-sort")
                .withSelected(HorizonsCommoditiesShow.valueOf(PreferencesService.getPreference("search.commodities.filter", "ALL")))
                .withItemsProperty(LocaleService.getListBinding(
                        HorizonsCommoditiesShow.ALL,
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
                        HorizonsCommoditiesShow.POWERPLAY,
                        HorizonsCommoditiesShow.SOLD_AT_STATION,
                        HorizonsCommoditiesShow.BOUGHT_AT_STATION))
                .withValueChangeListener((_, _, newValue) -> {
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
        subscribe = Observable.create((ObservableEmitter<String> emitter) -> addChangeListener(this.textField.textProperty(), (_, _, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe(_ -> EventService.publish(new HorizonsCommoditiesSearchEvent(new CommoditiesSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.showMaterialsComboBox)))));
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

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
