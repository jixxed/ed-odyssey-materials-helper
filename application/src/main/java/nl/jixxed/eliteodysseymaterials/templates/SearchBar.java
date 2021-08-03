package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.Show;
import nl.jixxed.eliteodysseymaterials.enums.Sort;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.SearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.TabSelecetedEvent;

import java.util.concurrent.TimeUnit;

@Slf4j
class SearchBar extends HBox {

    private final Button button = new Button();
    private final TextField textField = createSearchTextField();


    private final ComboBox<Show> showMaterialsComboBox = createShowComboBox();
    private final ComboBox<Sort> sortMaterialsComboBox = createSortComboBox();

    SearchBar() {
        super();
        this.getStyleClass().add("root");
        this.button.setText((PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE)) ? "<" : ">");
        this.button.getStyleClass().addAll("root", "menubutton");
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            this.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
            this.button.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
            this.showMaterialsComboBox.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
            this.textField.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
            this.sortMaterialsComboBox.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
        });
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        this.styleProperty().set("-fx-font-size: " + fontSize + "px");
        this.button.styleProperty().set("-fx-font-size: " + fontSize + "px");
        this.showMaterialsComboBox.styleProperty().set("-fx-font-size: " + fontSize + "px");
        this.textField.styleProperty().set("-fx-font-size: " + fontSize + "px");
        this.sortMaterialsComboBox.styleProperty().set("-fx-font-size: " + fontSize + "px");

        setDefaultOptions();
        HBox.setHgrow(this.textField, Priority.ALWAYS);
        HBox.setHgrow(this.showMaterialsComboBox, Priority.ALWAYS);
        HBox.setHgrow(this.sortMaterialsComboBox, Priority.ALWAYS);
        this.getChildren().addAll(this.button, this.textField, this.showMaterialsComboBox, this.sortMaterialsComboBox);
        EventService.addListener(TabSelecetedEvent.class, event -> {
            if (Tabs.OVERVIEW.equals(event.getSelectedTab())) {
                this.textField.setDisable(false);
                this.showMaterialsComboBox.setDisable(false);
                this.sortMaterialsComboBox.setDisable(false);
            } else {
                this.textField.setDisable(true);
                this.showMaterialsComboBox.setDisable(true);
                this.sortMaterialsComboBox.setDisable(true);
            }
        });
    }

    private void setDefaultOptions() {
        try {
            final Sort sort = Sort.valueOf(PreferencesService.getPreference("search.sort", ""));
            this.sortMaterialsComboBox.getSelectionModel().select(sort);
        } catch (final IllegalArgumentException ex) {
            log.error("sort error", ex);
        }

        try {
            final Show filter = Show.valueOf(PreferencesService.getPreference("search.filter", ""));
            this.showMaterialsComboBox.getSelectionModel().select(filter);
        } catch (final IllegalArgumentException ex) {
            log.error("filter error", ex);
        }
    }

    private TextField createSearchTextField() {
        final TextField textField = new TextField();
        textField.getStyleClass().addAll("root", "search-input");
        textField.promptTextProperty().bind(LocaleService.getStringBinding("search.text.placeholder"));
        textField.setFocusTraversable(false);

        Observable.create((ObservableEmitter<String> emitter) -> textField.textProperty().addListener((observable, oldValue, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> EventService.publish(new SearchEvent(new Search(newValue, getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.showMaterialsComboBox)))));
        return textField;
    }

    private ComboBox<Sort> createSortComboBox() {
        final ComboBox<Sort> sortMaterialsComboBox = new ComboBox<>();
        sortMaterialsComboBox.itemsProperty().bind(LocaleService.getListBinding(Sort.ENGINEER_BLUEPRINT_IRRELEVANT, Sort.RELEVANT_IRRELEVANT, Sort.ALPHABETICAL, Sort.QUANTITY));
        sortMaterialsComboBox.getStyleClass().addAll("root", "filter-and-sort");
        sortMaterialsComboBox.promptTextProperty().bind(LocaleService.getStringBinding("search.sort.placeholder"));
        final Tooltip sortMaterialsTooltip = new Tooltip();
        sortMaterialsTooltip.textProperty().bind(LocaleService.getStringBinding("search.sort.placeholder"));
        sortMaterialsComboBox.setTooltip(sortMaterialsTooltip);
        sortMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), newValue, getShowOrDefault(this.showMaterialsComboBox))));
                PreferencesService.setPreference("search.sort", newValue.name());
            }
        });
        return sortMaterialsComboBox;
    }

    private ComboBox<Show> createShowComboBox() {
        final ComboBox<Show> showMaterialsComboBox = new ComboBox<>();
        showMaterialsComboBox.itemsProperty().bind(LocaleService.getListBinding(Show.ALL,
                Show.ALL_WITH_STOCK,
                Show.ALL_ENGINEER_BLUEPRINT,
                Show.REQUIRED_ENGINEER_BLUEPRINT,
                Show.ALL_ENGINEER,
                Show.REQUIRED_ENGINEER,
                Show.BLUEPRINT,
                Show.IRRELEVANT,
                Show.IRRELEVANT_WITH_STOCK,
                Show.PROHIBITED,
                Show.FAVOURITES));
        showMaterialsComboBox.getStyleClass().addAll("root", "filter-and-sort");
        showMaterialsComboBox.promptTextProperty().bind(LocaleService.getStringBinding("search.filter.placeholder"));
        final Tooltip showMaterialsTooltip = new Tooltip();
        showMaterialsTooltip.textProperty().bind(LocaleService.getStringBinding("search.filter.placeholder"));
        showMaterialsComboBox.setTooltip(showMaterialsTooltip);
        showMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), newValue)));
                PreferencesService.setPreference("search.filter", newValue.name());
            }
        });
        return showMaterialsComboBox;
    }

    private String getQueryOrDefault(final TextField textField) {
        return (textField.getText() != null) ? textField.getText() : "";
    }

    private Show getShowOrDefault(final ComboBox<Show> showMaterialsComboBox) {
        return (showMaterialsComboBox.getValue() != null) ? showMaterialsComboBox.getValue() : Show.ALL;
    }

    private Sort getSortOrDefault(final ComboBox<Sort> sortMaterialsComboBox) {
        return (sortMaterialsComboBox.getValue() != null) ? sortMaterialsComboBox.getValue() : Sort.ALPHABETICAL;
    }

    Button getButton() {
        return this.button;
    }
}
