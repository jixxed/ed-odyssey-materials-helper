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
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextFieldBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.Show;
import nl.jixxed.eliteodysseymaterials.enums.Sort;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.concurrent.TimeUnit;

@Slf4j
class SearchBar extends HBox {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private Button button;
    private TextField textField;
    private ComboBox<Show> showMaterialsComboBox;
    private ComboBox<Sort> sortMaterialsComboBox;

    SearchBar() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("root");
        initMenuButton();
        initSearchTextField();
        initSearchTextFilter();
        initSearchTextSort();

        setDefaultOptions();

        applyFontSizingHack();

        HBox.setHgrow(this.textField, Priority.ALWAYS);
        HBox.setHgrow(this.showMaterialsComboBox, Priority.ALWAYS);
        HBox.setHgrow(this.sortMaterialsComboBox, Priority.ALWAYS);

        this.getChildren().addAll(this.button, this.textField, this.showMaterialsComboBox, this.sortMaterialsComboBox);
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.button.styleProperty().set(fontStyle);
        this.showMaterialsComboBox.styleProperty().set(fontStyle);
        this.textField.styleProperty().set(fontStyle);
        this.sortMaterialsComboBox.styleProperty().set(fontStyle);
    }

    private void initSearchTextSort() {
        final Tooltip sortMaterialsTooltip = TooltipBuilder.builder()
                .withText(LocaleService.getStringBinding("search.sort.placeholder"))
                .build();
        this.sortMaterialsComboBox = ComboBoxBuilder.builder(Sort.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(Sort.ENGINEER_BLUEPRINT_IRRELEVANT, Sort.RELEVANT_IRRELEVANT, Sort.ALPHABETICAL, Sort.QUANTITY))
                .withPromptTextProperty(LocaleService.getStringBinding("search.sort.placeholder"))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), newValue, getShowOrDefault(this.showMaterialsComboBox))));
                        PreferencesService.setPreference("search.sort", newValue.name());
                    }
                })
                .withToolTip(sortMaterialsTooltip)
                .build();
    }

    private void initSearchTextFilter() {
        final Tooltip showMaterialsTooltip = TooltipBuilder.builder().withText(LocaleService.getStringBinding("search.filter.placeholder")).build();
        this.showMaterialsComboBox = ComboBoxBuilder.builder(Show.class)
                .withStyleClasses("root", "filter-and-sort")
                .withItemsProperty(LocaleService.getListBinding(Show.ALL,
                        Show.ALL_WITH_STOCK,
                        Show.ALL_ENGINEER_BLUEPRINT,
                        Show.REQUIRED_ENGINEER_BLUEPRINT,
                        Show.ALL_ENGINEER,
                        Show.REQUIRED_ENGINEER,
                        Show.BLUEPRINT,
                        Show.IRRELEVANT,
                        Show.IRRELEVANT_WITH_STOCK,
                        Show.PROHIBITED,
                        Show.FAVOURITES))
                .withValueChangeListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), newValue)));
                        PreferencesService.setPreference("search.filter", newValue.name());
                    }
                })
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

    private void initMenuButton() {
        this.button = new Button();
        this.button.setText(isRecipeBarVisible() ? "<" : ">");
        this.button.getStyleClass().addAll("root", "menubutton");
        this.button.setOnAction(event -> {
            this.button.setText(isRecipeBarVisible() ? ">" : "<");
            EventService.publish(new MenuButtonClickedEvent());
        });
    }

    private void initEventHandling() {
        EventService.addListener(BlueprintClickEvent.class, blueprintClickEvent -> {
            this.button.setText("<");
        });
        //hack for component resizing on other fontsizes
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.button.styleProperty().set(fontStyle);
            this.showMaterialsComboBox.styleProperty().set(fontStyle);
            this.textField.styleProperty().set(fontStyle);
            this.sortMaterialsComboBox.styleProperty().set(fontStyle);
        });
        EventService.addListener(TabSelectedEvent.class, event -> {
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
        EventService.addListener(SoloModeEvent.class, soloModeEvent ->
                EventService.publish(new SearchEvent(new Search(getQueryOrDefault(this.textField), getSortOrDefault(this.sortMaterialsComboBox), getShowOrDefault(this.showMaterialsComboBox))))
        );
    }

    private void setDefaultOptions() {
        try {
            final Sort sort = Sort.valueOf(PreferencesService.getPreference("search.sort", "ALPHABETICAL"));
            this.sortMaterialsComboBox.getSelectionModel().select(sort);
        } catch (final IllegalArgumentException ex) {
            log.error("sort error", ex);
        }

        try {
            final Show filter = Show.valueOf(PreferencesService.getPreference("search.filter", "ALL"));
            this.showMaterialsComboBox.getSelectionModel().select(filter);
        } catch (final IllegalArgumentException ex) {
            log.error("filter error", ex);
        }
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

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE);
    }
}
