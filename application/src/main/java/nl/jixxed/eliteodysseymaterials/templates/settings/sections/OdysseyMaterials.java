package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.MaterialOrientation;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class OdysseyMaterials extends VBox implements Template {

    private Label readingDirectionLabel;
    private ComboBox<MaterialOrientation> readingDirectionSelect;
    private CheckBox soloModeCheckBox;
    private Label soloModeLabel;
    private Label soloModeExplainLabel;
    private Label overrideLabel;
    private ComboBox<OdysseyMaterial> overrideSelect;
    private Button overrideAddButton;
    private Label overrideListLabel;
    private ListView<OdysseyMaterial> overrideListView;
    private Button overrideRemoveButton;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    public OdysseyMaterials() {
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label overviewLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.odyssey.materials"))
                .build();
        final HBox readingDirectionSetting = createReadingDirectionSetting();
        final HBox soloModeSetting = createSoloModeSetting();
        final HBox irrelevantOverrideSetting = createIrrelevantOverrideSetting();
        final HBox irrelevantOverrideList = createIrrelevantOverrideList();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(overviewLabel, readingDirectionSetting, soloModeSetting, irrelevantOverrideSetting, irrelevantOverrideList);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            applyFontSizeToComponents(fontSizeEvent.getFontSize(), this.readingDirectionSelect);
        }));
    }
    private void applyFontSizingHack() {
        final Integer size = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizeToComponents(size, this.readingDirectionSelect);

    }

    private static void applyFontSizeToComponents(Integer size, Node... components) {
        final String style =  String.format("-fx-font-size: %dpx;", size);
        for (Node component : components) {
            component.setStyle(style);
        }
    }
    private HBox createReadingDirectionSetting() {
        this.readingDirectionLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.reading.direction"))
                .build();
        this.readingDirectionSelect = ComboBoxBuilder.builder(MaterialOrientation.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(MaterialOrientation.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.ORIENTATION, newValue.name());
                        EventService.publish(new OrientationChangeEvent(newValue));
                    }
                })
                .asLocalized()
                .build();

        this.readingDirectionSelect.getSelectionModel().select(MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "VERTICAL")));

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.readingDirectionLabel, this.readingDirectionSelect)
                .buildHBox();
    }

    private HBox createIrrelevantOverrideSetting() {
        this.overrideLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override"))
                .build();

        final ListBinding<OdysseyMaterial> odysseyMaterialListBinding = LocaleService.getListBinding(OdysseyMaterial.getAllIrrelevantMaterialsWithoutOverride().toArray(OdysseyMaterial[]::new));
        this.overrideSelect = ComboBoxBuilder.builder(OdysseyMaterial.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(odysseyMaterialListBinding)
                .withValueChangeListener((observable, oldValue, newValue) ->
                        this.overrideAddButton.setDisable(newValue == null || this.overrideListView.getItems().contains(newValue))
                )
                .asLocalized()
                .build();

        this.overrideAddButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override.add"))
                .withOnAction(e -> {
                    if (this.overrideSelect.getSelectionModel().getSelectedItem() != null) {
                        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
                        final List<OdysseyMaterial> items = Arrays.stream(irrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(OdysseyMaterial::subtypeForName).collect(Collectors.toList());
                        items.add(this.overrideSelect.getSelectionModel().getSelectedItem());
                        PreferencesService.setPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, items.stream().map(OdysseyMaterial::name).collect(Collectors.joining(",")));
                        this.overrideListView.getItems().add(this.overrideSelect.getSelectionModel().getSelectedItem());
                        this.overrideAddButton.setDisable(true);
                        EventService.publish(new IrrelevantMaterialOverrideEvent());
                    }
                })
                .build();
        this.overrideAddButton.setDisable(true);

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.overrideLabel, this.overrideSelect, this.overrideAddButton)
                .buildHBox();
    }

    private HBox createIrrelevantOverrideList() {
        this.overrideListLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override.list"))
                .build();
        this.overrideListView = new ListView<>();
        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
        final ObservableList<OdysseyMaterial> items = Arrays.stream(irrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(OdysseyMaterial::subtypeForName).collect(Collectors.toCollection(FXCollections::observableArrayList));
        this.overrideListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                this.overrideRemoveButton.setDisable(newValue == null)
        );
        this.overrideListView.getItems().addListener((ListChangeListener<OdysseyMaterial>) c ->
                this.overrideAddButton.setDisable(this.overrideListView.getItems().contains(this.overrideSelect.getSelectionModel().getSelectedItem()))
        );
        this.overrideListView.setItems(items);
        this.overrideListView.setCellFactory(getCellFactory());
        this.overrideRemoveButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override.remove"))
                .withOnAction(e -> {
                    final String currentIrrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
                    final ObservableList<OdysseyMaterial> currentItems = Arrays.stream(currentIrrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(OdysseyMaterial::subtypeForName).collect(Collectors.toCollection(FXCollections::observableArrayList));
                    currentItems.remove(this.overrideListView.getSelectionModel().getSelectedItem());
                    PreferencesService.setPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, currentItems.stream().map(OdysseyMaterial::name).collect(Collectors.joining(",")));
                    this.overrideListView.getItems().remove(this.overrideListView.getSelectionModel().getSelectedItem());
                    this.overrideAddButton.setDisable(this.overrideSelect.getSelectionModel().getSelectedItem() == null || this.overrideListView.getItems().contains(this.overrideSelect.getSelectionModel().getSelectedItem()));
                    EventService.publish(new IrrelevantMaterialOverrideEvent());
                })
                .build();
        this.overrideRemoveButton.setDisable(true);
        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.overrideListLabel, this.overrideListView, this.overrideRemoveButton)
                .buildHBox();
    }

    private Callback<ListView<OdysseyMaterial>, ListCell<OdysseyMaterial>> getCellFactory() {
        return listView -> new ListCell<>() {

            @SuppressWarnings("java:S1068")
            private final EventListener<LanguageChangedEvent> engineerEventEventListener = EventService.addListener(this, LanguageChangedEvent.class, event ->
                    updateText(getItem(), this.emptyProperty().get())
            );


            @Override
            protected void updateItem(final OdysseyMaterial item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
            }

            private void updateText(final OdysseyMaterial item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()));
                }
            }

        };
    }

    private HBox createSoloModeSetting() {
        this.soloModeLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.solo.mode")).build();
        this.soloModeExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.solo.mode.explain")).build();
        this.soloModeCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.SOLO_MODE, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.SOLO_MODE, newValue);
                    EventService.publish(new SoloModeEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.soloModeLabel, this.soloModeCheckBox, this.soloModeExplainLabel)
                .buildHBox();
    }
}
