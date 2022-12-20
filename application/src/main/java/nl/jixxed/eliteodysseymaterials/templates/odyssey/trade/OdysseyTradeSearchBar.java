package nl.jixxed.eliteodysseymaterials.templates.odyssey.trade;

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
import nl.jixxed.eliteodysseymaterials.domain.TradeSearch;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.TradeShow;
import nl.jixxed.eliteodysseymaterials.enums.TradeSort;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public
class OdysseyTradeSearchBar extends HBox {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private TextField textField;
    private ComboBox<TradeShow> showTradeComboBox;
    private ComboBox<TradeSort> sortTradeComboBox;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public OdysseyTradeSearchBar() {
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
        HBox.setHgrow(this.showTradeComboBox, Priority.ALWAYS);
        HBox.setHgrow(this.sortTradeComboBox, Priority.ALWAYS);

        this.getChildren().addAll(this.textField, this.showTradeComboBox, this.sortTradeComboBox);
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.showTradeComboBox.styleProperty().set(fontStyle);
        this.textField.styleProperty().set(fontStyle);
        this.sortTradeComboBox.styleProperty().set(fontStyle);
    }

    private void initSearchTextSort() {
        final Tooltip sortMaterialsTooltip = TooltipBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.search.sort.placeholder"))
                .build();
        this.sortTradeComboBox = ComboBoxBuilder.builder(TradeSort.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(TradeSort.NAME_OFFER, TradeSort.NAME_RECEIVE, TradeSort.QUANTITY_OFFER, TradeSort.QUANTITY_RECEIVE))
                .withPromptTextProperty(LocaleService.getStringBinding("trade.search.sort.placeholder"))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new TradeSearchEvent(new TradeSearch(getQueryOrDefault(this.textField), newValue, getShowOrDefault(this.showTradeComboBox))));
                        PreferencesService.setPreference("trade.search.sort", newValue.name());
                    }
                })
                .asLocalized()
                .withToolTip(sortMaterialsTooltip)
                .build();
    }

    private void initSearchTextFilter() {
        final Tooltip showMaterialsTooltip = TooltipBuilder.builder().withText(LocaleService.getStringBinding("trade.search.filter.placeholder")).build();
        this.showTradeComboBox = ComboBoxBuilder.builder(TradeShow.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(TradeShow.ALL, TradeShow.CAN_TRADE, TradeShow.ACTIVE))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new TradeSearchEvent(new TradeSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortTradeComboBox), newValue)));
                        PreferencesService.setPreference("trade.search.filter", newValue.name());
                    }
                })
                .asLocalized()
                .withPromptTextProperty(LocaleService.getStringBinding("trade.search.filter.placeholder"))
                .withToolTip(showMaterialsTooltip)
                .build();
    }

    private void initSearchTextField() {
        this.textField = TextFieldBuilder.builder()
                .withStyleClasses("root", "search-input")
                .withPromptTextProperty(LocaleService.getStringBinding("trade.search.text.placeholder"))
                .withFocusTraversable(false)
                .build();
        Observable.create((ObservableEmitter<String> emitter) -> this.textField.textProperty().addListener((observable, oldValue, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(() -> EventService.publish(new TradeSearchEvent(new TradeSearch(newValue, getSortOrDefault(this.sortTradeComboBox), getShowOrDefault(this.showTradeComboBox))))));
    }


    private void initEventHandling() {
        //hack for component resizing on other fontsizes
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.showTradeComboBox.styleProperty().set(fontStyle);
            this.textField.styleProperty().set(fontStyle);
            this.sortTradeComboBox.styleProperty().set(fontStyle);
        }));
        this.eventListeners.add(EventService.addListener(this, SoloModeEvent.class, soloModeEvent ->
                EventService.publish(new TradeSearchEvent(new TradeSearch(getQueryOrDefault(this.textField), getSortOrDefault(this.sortTradeComboBox), getShowOrDefault(this.showTradeComboBox))))
        ));
    }

    private void setDefaultOptions() {
        try {
            final TradeSort tradeSort = TradeSort.valueOf(PreferencesService.getPreference("trade.search.sort", "NAME_OFFER"));
            this.sortTradeComboBox.getSelectionModel().select(tradeSort);
        } catch (final IllegalArgumentException ex) {
            log.error("sort error", ex);
        }

        try {
            final TradeShow filter = TradeShow.valueOf(PreferencesService.getPreference("trade.search.filter", "ALL"));
            this.showTradeComboBox.getSelectionModel().select(filter);
        } catch (final IllegalArgumentException ex) {
            log.error("filter error", ex);
        }
    }


    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }

    private TradeShow getShowOrDefault(final ComboBox<TradeShow> showTradeComboBox) {
        return (showTradeComboBox.getValue() != null) ? showTradeComboBox.getValue() : TradeShow.ALL;
    }

    private TradeSort getSortOrDefault(final ComboBox<TradeSort> sortTradeComboBox) {
        return (sortTradeComboBox.getValue() != null) ? sortTradeComboBox.getValue() : TradeSort.NAME_OFFER;
    }

}
