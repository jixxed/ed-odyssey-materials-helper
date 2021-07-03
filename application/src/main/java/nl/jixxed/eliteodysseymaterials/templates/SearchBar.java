package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.enums.Show;
import nl.jixxed.eliteodysseymaterials.enums.Sort;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.SearchEvent;

import java.util.concurrent.TimeUnit;

public class SearchBar extends HBox {

    final Button button = new Button();
    private final ObservableList<Show> showOptions =
            FXCollections.observableArrayList(
                    Show.ALL,
                    Show.ALL_WITH_STOCK,
                    Show.ALL_ENGINEER_BLUEPRINT,
                    Show.REQUIRED_ENGINEER_BLUEPRINT,
                    Show.ALL_ENGINEER,
                    Show.REQUIRED_ENGINEER,
                    Show.BLUEPRINT,
                    Show.IRRELEVANT,
                    Show.IRRELEVANT_WITH_STOCK,
                    Show.FAVOURITES
            );
    private final ObservableList<Sort> sortOptions =
            FXCollections.observableArrayList(
                    Sort.ENGINEER_BLUEPRINT_IRRELEVANT, Sort.RELEVANT_IRRELEVANT, Sort.ALPHABETICAL
            );

    public SearchBar() {
        super();

        this.button.setText((PreferencesService.getPreference("recipes.visible", Boolean.TRUE)) ? "<" : ">");
        this.button.getStyleClass().add("menubutton");

        final TextField textField = new TextField();
        final ComboBox<Show> showMaterialsComboBox = new ComboBox<>();//this.showOptions
        final ComboBox<Sort> sortMaterialsComboBox = new ComboBox<>();//this.sortOptions
        showMaterialsComboBox.itemsProperty().bind(LocaleService.getListBinding(Show.ALL,
                Show.ALL_WITH_STOCK,
                Show.ALL_ENGINEER_BLUEPRINT,
                Show.REQUIRED_ENGINEER_BLUEPRINT,
                Show.ALL_ENGINEER,
                Show.REQUIRED_ENGINEER,
                Show.BLUEPRINT,
                Show.IRRELEVANT,
                Show.IRRELEVANT_WITH_STOCK,
                Show.FAVOURITES));
        sortMaterialsComboBox.itemsProperty().bind(LocaleService.getListBinding(Sort.ENGINEER_BLUEPRINT_IRRELEVANT, Sort.RELEVANT_IRRELEVANT, Sort.ALPHABETICAL));
        textField.getStyleClass().add("search-input");
        textField.promptTextProperty().bind(LocaleService.getStringBinding("search.text.placeholder"));
        textField.setFocusTraversable(false);

        Observable.create((ObservableEmitter<String> emitter) -> textField.textProperty().addListener((observable, oldValue, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe((newValue) -> {
                    EventService.publish(new SearchEvent(new Search(newValue, getSortOrDefault(sortMaterialsComboBox), getShowOrDefault(showMaterialsComboBox))));
                });

        showMaterialsComboBox.getStyleClass().add("filter-and-sort");
        showMaterialsComboBox.promptTextProperty().bind(LocaleService.getStringBinding("search.filter.placeholder"));
        final Tooltip showMaterialsTooltip = new Tooltip();
        showMaterialsTooltip.textProperty().bind(LocaleService.getStringBinding("search.filter.placeholder"));
        showMaterialsComboBox.setTooltip(showMaterialsTooltip);
        showMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            EventService.publish(new SearchEvent(new Search(getQueryOrDefault(textField), getSortOrDefault(sortMaterialsComboBox), newValue)));

        });
        sortMaterialsComboBox.getStyleClass().add("filter-and-sort");
        sortMaterialsComboBox.promptTextProperty().bind(LocaleService.getStringBinding("search.sort.placeholder"));
        final Tooltip sortMaterialsTooltip = new Tooltip();
        sortMaterialsTooltip.textProperty().bind(LocaleService.getStringBinding("search.sort.placeholder"));
        sortMaterialsComboBox.setTooltip(sortMaterialsTooltip);
        sortMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            EventService.publish(new SearchEvent(new Search(getQueryOrDefault(textField), newValue, getShowOrDefault(showMaterialsComboBox))));
        });
        HBox.setHgrow(textField, Priority.ALWAYS);
        HBox.setHgrow(showMaterialsComboBox, Priority.ALWAYS);
        HBox.setHgrow(sortMaterialsComboBox, Priority.ALWAYS);
        this.getChildren().addAll(this.button, textField, showMaterialsComboBox, sortMaterialsComboBox);
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

    public Button getButton() {
        return this.button;
    }
}
