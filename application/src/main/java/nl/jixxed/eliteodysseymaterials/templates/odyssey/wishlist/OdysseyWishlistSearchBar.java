package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
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
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistMaterialSearch;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyWishlistMaterialSort;
import nl.jixxed.eliteodysseymaterials.enums.WishlistMaterialGrouping;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public
class OdysseyWishlistSearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private DestroyableTextField textField;
    private DestroyableComboBox<WishlistMaterialGrouping> groupMaterialsComboBox;
    private DestroyableComboBox<OdysseyWishlistMaterialSort> sortMaterialsComboBox;
    private Disposable subscribe;


    public OdysseyWishlistSearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("root");
        initSearchTextField();
        initSearchTextFilter();
        initSearchTextSort();

        setDefaultOptions();

        applyFontSizingHack();

        HBox.setHgrow(this.textField, Priority.ALWAYS);

        this.getNodes().addAll(this.textField, this.groupMaterialsComboBox, this.sortMaterialsComboBox);
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.groupMaterialsComboBox.styleProperty().set(fontStyle);
        this.textField.styleProperty().set(fontStyle);
        this.sortMaterialsComboBox.styleProperty().set(fontStyle);
    }

    private void initSearchTextSort() {
        final DestroyableTooltip sortMaterialsTooltip = TooltipBuilder.builder()
                .withText("search.sort.placeholder")
                .build();
        this.sortMaterialsComboBox = ComboBoxBuilder.builder(OdysseyWishlistMaterialSort.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(OdysseyWishlistMaterialSort.ALPHABETICAL, OdysseyWishlistMaterialSort.QUANTITY_REQUIRED))
                .withPromptTextProperty(LocaleService.getStringBinding("search.sort.placeholder"))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new OdysseyWishlistSearchEvent(new OdysseyWishlistMaterialSearch(getQueryOrDefault(this.textField), newValue, getShowOrDefault(this.groupMaterialsComboBox))));
                        PreferencesService.setPreference("search.odyssey.wishlist.sort", newValue.name());
                    }
                })
                .asLocalized()
                .withToolTip(sortMaterialsTooltip)
                .build();
    }

    private void initSearchTextFilter() {
        final Tooltip groupMaterialsTooltip = TooltipBuilder.builder()
                .withText("search.grouping.placeholder")
                .build();
        this.groupMaterialsComboBox = ComboBoxBuilder.builder(WishlistMaterialGrouping.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(WishlistMaterialGrouping.CATEGORY,
                        WishlistMaterialGrouping.NONE))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new OdysseyWishlistSearchEvent(new OdysseyWishlistMaterialSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.groupMaterialsComboBox))));
                        PreferencesService.setPreference("search.odyssey.wishlist.grouping", newValue.name());
                    }
                })
                .asLocalized()
                .withPromptTextProperty(LocaleService.getStringBinding("search.grouping.placeholder"))
                .withToolTip(groupMaterialsTooltip)
                .build();
    }

    private void initSearchTextField() {
        this.textField = TextFieldBuilder.builder()
                .withStyleClasses("root", "search-input")
                .withPromptTextProperty(LocaleService.getStringBinding("search.text.placeholder"))
                .withFocusTraversable(false)
                .build();
        subscribe = Observable.create((ObservableEmitter<String> emitter) -> this.textField.addChangeListener(this.textField.textProperty(), (_, _, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(_ -> EventService.publish(new OdysseyWishlistSearchEvent(new OdysseyWishlistMaterialSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.groupMaterialsComboBox)))));
    }


    public void initEventHandling() {
        //hack for component resizing on other fontsizes
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.groupMaterialsComboBox.styleProperty().set(fontStyle);
            this.textField.styleProperty().set(fontStyle);
            this.sortMaterialsComboBox.styleProperty().set(fontStyle);
        }));
    }

    private void setDefaultOptions() {
        try {
            final OdysseyWishlistMaterialSort materialSort = OdysseyWishlistMaterialSort.valueOf(PreferencesService.getPreference("search.odyssey.wishlist.sort", "ALPHABETICAL"));
            this.sortMaterialsComboBox.getSelectionModel().select(materialSort);
        } catch (final IllegalArgumentException ex) {
            log.error("sort error", ex);
        }

        try {
            final WishlistMaterialGrouping filter = WishlistMaterialGrouping.valueOf(PreferencesService.getPreference("search.odyssey.wishlist.grouping", "CATEGORY"));
            this.groupMaterialsComboBox.getSelectionModel().select(filter);
        } catch (final IllegalArgumentException ex) {
            log.error("grouping error", ex);
        }
    }


    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }

    private WishlistMaterialGrouping getShowOrDefault(final ComboBox<WishlistMaterialGrouping> groupMaterialsComboBox) {
        return (groupMaterialsComboBox.getValue() != null) ? groupMaterialsComboBox.getValue() : WishlistMaterialGrouping.CATEGORY;
    }

    private OdysseyWishlistMaterialSort getSortOrDefault(final ComboBox<OdysseyWishlistMaterialSort> sortMaterialsComboBox) {
        return (sortMaterialsComboBox.getValue() != null) ? sortMaterialsComboBox.getValue() : OdysseyWishlistMaterialSort.ALPHABETICAL;
    }

    @Override
    public void destroyInternal() {
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
