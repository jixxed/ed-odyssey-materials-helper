package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.export.CsvExporter;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.export.XlsExporter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.service.SupportService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static nl.jixxed.eliteodysseymaterials.helper.DeeplinkHelper.deeplinkConsumer;
import static nl.jixxed.eliteodysseymaterials.helper.DeeplinkHelper.slefConsumer;
import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

@Slf4j
public class General extends VBox implements Template {
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Application application;
    private Label journalFolderLabel;
    private Label selectedFolderLabel;
    private Button journalSelectButton;
    private Label fontsizeLabel;
    private ComboBox<FontSize> fontsizeSelect;
    private ComboBox<ApplicationLocale> languageSelect;
    private Label languageLabel;
    private Label wipLabel;
    private Label wipExplainLabel;
    private CheckBox wipCheckBox;
    private Label urlSchemeLinkingLabel;
    private Button urlSchemeLinkingButton;
    private Label urlSchemeLinkingActiveLabel;
    private Label pollLabel;
    private Label pollExplainLabel;
    private CheckBox pollCheckBox;
    private DestroyableLabel blueprintExpandedLabel;
    private DestroyableLabel blueprintExpandedExplainLabel;
    private CheckBox blueprintExpandedCheckBox;

    public General(final Application application) {
        this.application = application;
        this.initComponents();
        this.initEventHandling();
        applyFontSizingHack();
    }

    private void applyFontSizingHack() {
        final Integer size = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        applyFontSizeToComponents(size, this.journalSelectButton, this.fontsizeSelect, this.languageSelect);

    }

    @Override
    public void initComponents() {
        final Label generalLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.general"))
                .build();
        final HBox langSetting = createLangSetting();
        final HBox fontSetting = creatFontSetting();
        final HBox customJournalFolderSetting = createCustomJournalFolderSetting();
        final HBox pollSetting = createPollSetting();
        final HBox urlSchemeLinkingSetting = createUrlSchemeLinkingSetting();
        final HBox exportInventory = createExportInventorySetting();
        final HBox blueprintExpandedSetting = createBlueprintExpandedSetting();
        final HBox importFromClipboardSetting = createImportFromClipboardSetting();
        final HBox importSlefFromClipboardSetting = createImportSlefFromClipboardSetting();
        final HBox supportPackageSetting = createSupportPackageSetting();
        final HBox wipSetting = createWIPSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(generalLabel, langSetting, fontSetting, customJournalFolderSetting, pollSetting, urlSchemeLinkingSetting, exportInventory, blueprintExpandedSetting, importFromClipboardSetting,importSlefFromClipboardSetting,supportPackageSetting);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            applyFontSizeToComponents(fontSizeEvent.getFontSize(), this.journalSelectButton, this.fontsizeSelect, this.languageSelect);
        }));
        this.eventListeners.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            executorService.shutdownNow();
        }));
    }

    private static void applyFontSizeToComponents(Integer size, Node... components) {
        final String style =  String.format("-fx-font-size: %dpx;", size);

        for (Node component : components) {
            component.setStyle(style);
        }
    }

    private HBox createLangSetting() {
        this.languageLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.language"))
                .build();
        this.languageSelect = ComboBoxBuilder.builder(ApplicationLocale.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(ApplicationLocale.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        LocaleService.setCurrentLocale(newValue.getLocale());
                        PreferencesService.setPreference(PreferenceConstants.LANGUAGE, newValue.name());
                        EventService.publish(new LanguageChangedEvent(newValue.getLocale()));
                    }
                })
                .asLocalized()
                .build();

        this.languageSelect.getSelectionModel().select(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.LANGUAGE, "ENGLISH")));


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.languageLabel, this.languageSelect)
                .buildHBox();
    }

    private HBox createBlueprintExpandedSetting() {
        this.blueprintExpandedLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.blueprint.expanded")).build();
        this.blueprintExpandedExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.blueprint.expanded.explain")).build();
        this.blueprintExpandedCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.TOOLTIP_BLUEPRINT_EXPANDED, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.TOOLTIP_BLUEPRINT_EXPANDED, newValue);
                    EventService.publish(new TooltipBlueprintsExpandEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.blueprintExpandedLabel, this.blueprintExpandedCheckBox, this.blueprintExpandedExplainLabel)
                .buildHBox();
    }

    private HBox createWIPSetting() {
        this.wipLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.wip")).build();
        this.wipExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.wip.explain")).build();
        this.wipCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.WIP, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.WIP, newValue);
                    EventService.publish(new WipVisibilityEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.wipLabel, this.wipCheckBox, this.wipExplainLabel)
                .buildHBox();
    }

    private HBox createCustomJournalFolderSetting() {
        this.journalFolderLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.journal.folder")).build();
        this.selectedFolderLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER)).build();

        final DirectoryChooser journalFolderSelect = new DirectoryChooser();
        this.journalSelectButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.journal.folder.select"))
                .withOnAction(e -> {
                    File initialDirectory = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER));
                    if (!initialDirectory.exists()) {
                        initialDirectory = new File(OsConstants.DEFAULT_WATCHED_FOLDER);
                    }
                    if (initialDirectory.exists()) {
                        journalFolderSelect.setInitialDirectory(initialDirectory);
                    }
                    final File selectedDirectory = journalFolderSelect.showDialog(((FXApplication) this.application).getPrimaryStage());
                    if (selectedDirectory != null) {
                        this.selectedFolderLabel.setText(selectedDirectory.getAbsolutePath());
                        PreferencesService.setPreference(PreferenceConstants.JOURNAL_FOLDER, selectedDirectory.getAbsolutePath());
                        ApplicationState.getInstance().setWatchedFolder(selectedDirectory.getAbsolutePath());
                        ApplicationState.getInstance().setWatchedFile("");
                        EventService.publish(new WatchedFolderChangedEvent(selectedDirectory.getAbsolutePath()));
                    }
                })
                .build();

        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.journalFolderLabel, this.journalSelectButton, this.selectedFolderLabel)
                .buildHBox();
    }

    private HBox creatFontSetting() {
        this.fontsizeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.textsize"))
                .build();
        this.fontsizeSelect = ComboBoxBuilder.builder(FontSize.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(FontSize::values))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.TEXTSIZE, newValue.name());
                        EventService.publish(new FontSizeEvent(newValue.getSize()));
                    }
                })
                .asLocalized()
                .build();

        this.fontsizeSelect.getSelectionModel().select(FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")));

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.fontsizeLabel, this.fontsizeSelect)
                .buildHBox();
    }

    private HBox createPollSetting() {
        this.pollLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.poll")).build();
        this.pollExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.poll.explain")).build();
        this.pollCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.POLLING_FILE_MODE, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.POLLING_FILE_MODE, newValue);
                    EventService.publish(new PollingFileModeEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.pollLabel, this.pollCheckBox, this.pollExplainLabel)
                .buildHBox();
    }

    private HBox createUrlSchemeLinkingSetting() {
        this.urlSchemeLinkingLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.url.scheme")).build();
        this.urlSchemeLinkingButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding(RegistryService.isRegistered() ? "tab.settings.url.scheme.button.unregister" : "tab.settings.url.scheme.button.register"))
                .withOnAction(event -> {
                    this.urlSchemeLinkingActiveLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.url.scheme.checking"));
                    final boolean wasRegistered = RegistryService.isRegistered();
                    if (wasRegistered) {
                        RegistryService.unregisterApplication();
                    } else {
                        RegistryService.registerApplication();
                    }
                    this.urlSchemeLinkingButton.setDisable(true);
                    executorService.schedule(() -> {
                        boolean isRegisteredNow = RegistryService.isRegistered();
                        final int retries = 10;
                        int retry = 0;
                        while (wasRegistered == isRegisteredNow) {
                            try {
                                isRegisteredNow = RegistryService.isRegistered();
                                Thread.sleep(1000);
                                if (retry++ == retries) {
                                    break;
                                }
                            } catch (final InterruptedException e) {
                                log.error("Register check error", e);
                            }
                        }
                        REGISTERED.set(isRegisteredNow);
                        final boolean finalIsRegisteredNow = isRegisteredNow;
                        Platform.runLater(() -> {
                            this.urlSchemeLinkingButton.textProperty().bind(LocaleService.getStringBinding(finalIsRegisteredNow ? "tab.settings.url.scheme.button.unregister" : "tab.settings.url.scheme.button.register"));
                            this.urlSchemeLinkingActiveLabel.textProperty().bind(LocaleService.getStringBinding(finalIsRegisteredNow ? "tab.settings.url.scheme.registered" : "tab.settings.url.scheme.unregistered"));
                            this.urlSchemeLinkingButton.setDisable(false);
                        });
                    }, 1, TimeUnit.SECONDS);
                })
                .build();
        this.urlSchemeLinkingActiveLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding(RegistryService.isRegistered() ? "tab.settings.url.scheme.registered" : "tab.settings.url.scheme.unregistered")).build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.urlSchemeLinkingLabel, this.urlSchemeLinkingButton, this.urlSchemeLinkingActiveLabel)
                .buildHBox();
    }

    private HBox createExportInventorySetting() {
        final DestroyableLabel saveInventoryLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("settings.button.export.inventory")).build();
        final Button saveInventory = ButtonBuilder.builder().withText(LocaleService.getStringBinding("settings.button.export.inventory.save")).withOnAction(event -> {
            EventService.publish(new SaveInventoryEvent(
                    () -> TextExporter.createTextInventory(),
                    () -> CsvExporter.createCsvInventory(),
                    () -> XlsExporter.createXlsInventory()
            ));
        }).build();

        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(saveInventoryLabel, saveInventory)
                .buildHBox();
    }
    private HBox createImportFromClipboardSetting() {
        final DestroyableLabel importClipboardLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("settings.button.import.clipboard")).build();
        final DestroyableLabel importClipboardExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("settings.button.import.clipboard.explain")).build();
        final Button importClipboard = ButtonBuilder.builder().withText(LocaleService.getStringBinding("settings.button.import.clipboard.import")).withOnAction(event -> {
            Platform.runLater(()->{
                final String clipboard = Clipboard.getSystemClipboard().getString();
                if (clipboard.startsWith("edomh://")) {
                    deeplinkConsumer.accept(clipboard);
                }
            });
        }).build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(importClipboardLabel, importClipboard, importClipboardExplainLabel)
                .buildHBox();
    }
    private HBox createImportSlefFromClipboardSetting() {
        final DestroyableLabel importSlefClipboardLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("settings.button.import.clipboard.slef")).build();
        final DestroyableLabel importSlefClipboardExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("settings.button.import.clipboard.slef.explain")).build();
        final Button importSlefClipboard = ButtonBuilder.builder().withText(LocaleService.getStringBinding("settings.button.import.clipboard.import.slef")).withOnAction(event -> {
            Platform.runLater(()->{
                final String clipboard = Clipboard.getSystemClipboard().getString();
                if (!clipboard.startsWith("edomh://")) {
                    slefConsumer.accept(clipboard);
                }
            });
        }).build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(importSlefClipboardLabel, importSlefClipboard, importSlefClipboardExplainLabel)
                .buildHBox();
    }
    private HBox createSupportPackageSetting() {
        final DestroyableLabel supportPackageLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("settings.button.support.package")).build();
        final DestroyableLabel supportPackageExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("settings.button.support.package.explain")).build();
        final Button supportPackage = ButtonBuilder.builder().withText(LocaleService.getStringBinding("settings.button.support.package.create")).withOnAction(event -> {
            final String supportPackageFile = SupportService.createSupportPackage();
            HostServices host = application.getHostServices();
            host.showDocument(Path.of(supportPackageFile).toFile().getAbsoluteFile().getParent());

        }).build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(supportPackageLabel, supportPackage, supportPackageExplainLabel)
                .buildHBox();
    }
}
