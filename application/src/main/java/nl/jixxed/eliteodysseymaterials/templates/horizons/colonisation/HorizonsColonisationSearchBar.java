package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

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
import nl.jixxed.eliteodysseymaterials.domain.ColonisationSearch;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsColonisationSort;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsColonisationSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HorizonsColonisationSearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private DestroyableTextField textField;
    private DestroyableComboBox<HorizonsColonisationSort> sortMaterialsComboBox;
    private Disposable subscribe;


    public HorizonsColonisationSearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("root");
        initSearchTextField();
        initSearchTextSort();

//        applyFontSizingHack();

        HBox.setHgrow(this.textField, Priority.ALWAYS);

        this.getNodes().addAll(this.textField, this.sortMaterialsComboBox);
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
        this.textField.styleProperty().set(fontStyle);
        this.sortMaterialsComboBox.styleProperty().set(fontStyle);
    }

    private void initSearchTextSort() {
        final DestroyableTooltip sortMaterialsTooltip = TooltipBuilder.builder()
                .withText("search.sort.placeholder")
                .build();
        this.sortMaterialsComboBox = ComboBoxBuilder.builder(HorizonsColonisationSort.class)
                .withStyleClasses("root", "filter-and-sort")
                .withSelected(HorizonsColonisationSort.valueOf(PreferencesService.getPreference("search.colonisation.sort", "ALPHABETICAL")))
                .withItemsProperty(LocaleService.getListBinding(
                        HorizonsColonisationSort.ALPHABETICAL,
                        HorizonsColonisationSort.CATEGORY,
                        HorizonsColonisationSort.REQUIRED,
                        HorizonsColonisationSort.DELIVERED,
                        HorizonsColonisationSort.TO_COLLECT,
                        HorizonsColonisationSort.TO_DELIVER
                ))
                .withPromptTextProperty(LocaleService.getStringBinding("search.sort.placeholder"))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new HorizonsColonisationSearchEvent(new ColonisationSearch(getQueryOrDefault(this.textField), newValue)));
                        PreferencesService.setPreference("search.colonisation.sort", newValue.name());
                    }
                })
                .asLocalized()
                .withToolTip(sortMaterialsTooltip)
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
                .subscribe(_ -> EventService.publish(new HorizonsColonisationSearchEvent(new ColonisationSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox)))),
                        t -> log.error(t.getMessage(), t));
    }


    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }


    private HorizonsColonisationSort getSortOrDefault(final ComboBox<HorizonsColonisationSort> sortMaterialsComboBox) {
        return (sortMaterialsComboBox.getValue() != null) ? sortMaterialsComboBox.getValue() : HorizonsColonisationSort.ALPHABETICAL;
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
