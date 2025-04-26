package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.MaterialOrientation;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class OdysseyMaterials extends DestroyableVBox implements DestroyableEventTemplate {

    private DestroyableComboBox<MaterialOrientation> readingDirectionSelect;
    private DestroyableComboBox<OdysseyMaterial> overrideSelect;
    private DestroyableListView<OdysseyMaterial> overrideListView;

    private static final Callback<ListView<OdysseyMaterial>, ListCell<OdysseyMaterial>> cellFactory = listView -> new DestroyableListCell<>() {
        {
            ((DestroyableListView) listView).register(this);
            register(EventService.addListener(true, listView, EngineerEvent.class, _ -> {
                updateText(getItem(), this.emptyProperty().get());
            }));
        }

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
    private BooleanBinding listContainsSelectedBinding;

    public OdysseyMaterials() {
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);

        final DestroyableLabel overviewLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.odyssey.materials")
                .build();
        final DestroyableHBox readingDirectionSetting = createReadingDirectionSetting();
        final DestroyableHBox soloModeSetting = createSoloModeSetting();
        final DestroyableHBox irrelevantOverrideList = createIrrelevantOverrideList();
        final DestroyableHBox irrelevantOverrideSetting = createIrrelevantOverrideSetting();

        this.getNodes().addAll(overviewLabel, readingDirectionSetting, soloModeSetting, irrelevantOverrideSetting, irrelevantOverrideList);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizeToComponents(fontSizeEvent.getFontSize(), this.readingDirectionSelect)));
    }

    private static void applyFontSizeToComponents(Integer size, Node... components) {
        final String style = String.format("-fx-font-size: %dpx;", size);
        for (Node component : components) {
            component.setStyle(style);
        }
    }

    private DestroyableHBox createReadingDirectionSetting() {
        DestroyableLabel readingDirectionLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.reading.direction")
                .build();

        this.readingDirectionSelect = ComboBoxBuilder.builder(MaterialOrientation.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withSelected(MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "VERTICAL")))
                .withItemsProperty(LocaleService.getListBinding(MaterialOrientation.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.ORIENTATION, newValue.name());
                        EventService.publish(new OrientationChangeEvent(newValue));
                    }
                })
                .asLocalized()
                .build();

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(readingDirectionLabel, this.readingDirectionSelect)
                .buildHBox();
    }

    private DestroyableHBox createIrrelevantOverrideSetting() {
        DestroyableLabel overrideLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.material.override")
                .build();

        final ListBinding<OdysseyMaterial> odysseyMaterialListBinding = LocaleService.getListBinding(OdysseyMaterial.getAllIrrelevantMaterialsWithoutOverride().toArray(OdysseyMaterial[]::new));
        this.overrideSelect = ComboBoxBuilder.builder(OdysseyMaterial.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(odysseyMaterialListBinding)
//                .withValueChangeListener((_, _, newValue) ->
//                        this.overrideAddButton.disableProperty().set(newValue == null || this.overrideListView.getItems().contains(newValue))
//                )
                .asLocalized()
                .build();

        listContainsSelectedBinding = Bindings.createBooleanBinding(() -> this.overrideListView.getItems().contains(this.overrideSelect.getSelectionModel().getSelectedItem()), this.overrideSelect.getSelectionModel().selectedItemProperty(), new SimpleListProperty<>(this.overrideListView.getItems()).sizeProperty());
        DestroyableButton overrideAddButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText("tab.settings.material.override.add")
                .withOnAction(e -> {
                    if (this.overrideSelect.getSelectionModel().getSelectedItem() != null) {
                        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
                        final List<OdysseyMaterial> items = Arrays.stream(irrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(OdysseyMaterial::subtypeForName).collect(Collectors.toList());
                        items.add(this.overrideSelect.getSelectionModel().getSelectedItem());
                        PreferencesService.setPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, items.stream().map(OdysseyMaterial::name).collect(Collectors.joining(",")));
                        this.overrideListView.getItems().add(this.overrideSelect.getSelectionModel().getSelectedItem());
                        EventService.publish(new IrrelevantMaterialOverrideEvent());
                    }
                })
                .withDisableProperty(listContainsSelectedBinding)
                .build();

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(overrideLabel, this.overrideSelect, overrideAddButton)
                .buildHBox();
    }

    private DestroyableHBox createIrrelevantOverrideList() {
        DestroyableLabel overrideListLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.material.override.list")
                .build();

        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");

        final ObservableList<OdysseyMaterial> items = Arrays.stream(irrelevantValues.split(","))
                .filter(string -> !string.isEmpty())
                .map(OdysseyMaterial::subtypeForName)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        this.overrideListView = ListViewBuilder.builder(OdysseyMaterial.class)
                .withStyleClass("override-list")
                .withItems(items)
                .withCellFactory(cellFactory)
                .build();

        DestroyableButton overrideRemoveButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText("tab.settings.material.override.remove")
                .withOnAction(e -> {
                    final String currentIrrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
                    final ObservableList<OdysseyMaterial> currentItems = Arrays.stream(currentIrrelevantValues.split(","))
                            .filter(string -> !string.isEmpty()).map(OdysseyMaterial::subtypeForName)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList));
                    currentItems.remove(this.overrideListView.getSelectionModel().getSelectedItem());
                    PreferencesService.setPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, currentItems.stream()
                            .map(OdysseyMaterial::name)
                            .collect(Collectors.joining(",")));
                    this.overrideListView.getItems().remove(this.overrideListView.getSelectionModel().getSelectedItem());
                    EventService.publish(new IrrelevantMaterialOverrideEvent());
                })
                .withDisableProperty(this.overrideListView.getSelectionModel().selectedItemProperty().isNull())
                .build();

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(overrideListLabel, this.overrideListView, overrideRemoveButton)
                .buildHBox();
    }


    private DestroyableHBox createSoloModeSetting() {
        DestroyableLabel soloModeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.solo.mode")
                .build();

        DestroyableLabel soloModeExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.solo.mode.explain")
                .build();

        DestroyableCheckBox soloModeCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.SOLO_MODE, Boolean.FALSE))
                .withSelectedProperty((_, _, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.SOLO_MODE, newValue);
                    EventService.publish(new SoloModeEvent(newValue));
                })
                .build();

        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(soloModeLabel, soloModeCheckBox, soloModeExplainLabel)
                .buildHBox();
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        listContainsSelectedBinding.dispose();
    }
}
