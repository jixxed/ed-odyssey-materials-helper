package nl.jixxed.eliteodysseymaterials.templates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Show;
import nl.jixxed.eliteodysseymaterials.enums.Sort;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SearchBar extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private String query = "";

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
                    Show.IRRELEVANT_WITH_STOCK
            );
    private final ObservableList<Sort> sortOptions =
            FXCollections.observableArrayList(
                    Sort.ENGINEER_BLUEPRINT_IRRELEVANT, Sort.RELEVANT_IRRELEVANT, Sort.ALPHABETICAL
            );

    public SearchBar(final Consumer<String> searchBarCallback, final Consumer<Show> showOptionCallback, final Consumer<Sort> sortOptionCallback) {
        super();
        final TextField textField = new TextField();
        textField.setAccessibleText("text");
        textField.getStyleClass().add("search");
        textField.setPromptText("Search");
        textField.setFocusTraversable(false);

        Observable.create((ObservableEmitter<String> emitter) -> textField.textProperty().addListener((observable, oldValue, newValue) -> emitter.onNext(newValue)))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe((newValue) -> {
                    this.query = newValue;
                    searchBarCallback.accept(newValue);
                });

        final ComboBox<Show> showMaterialsComboBox = new ComboBox<>(this.showOptions);
        showMaterialsComboBox.getStyleClass().add("filter-and-sort");
        showMaterialsComboBox.setPromptText("Show materials:");
        showMaterialsComboBox.setTooltip(new Tooltip("Show materials"));
        showMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            APPLICATION_STATE.setShow(newValue);
            showOptionCallback.accept(newValue);

        });
        final ComboBox<Sort> sortMaterialsComboBox = new ComboBox<>(this.sortOptions);
        sortMaterialsComboBox.getStyleClass().add("filter-and-sort");
        sortMaterialsComboBox.setPromptText("Sort materials:");
        sortMaterialsComboBox.setTooltip(new Tooltip("Sort materials"));
        sortMaterialsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            APPLICATION_STATE.setSort(newValue);
            sortOptionCallback.accept(newValue);
        });
        HBox.setHgrow(textField, Priority.ALWAYS);//Added this line
        HBox.setHgrow(showMaterialsComboBox, Priority.ALWAYS);//Added this line
        HBox.setHgrow(sortMaterialsComboBox, Priority.ALWAYS);//Added this line
        this.getChildren().addAll(textField, showMaterialsComboBox, sortMaterialsComboBox);
    }

    public String getQuery() {
        return this.query;
    }
}
