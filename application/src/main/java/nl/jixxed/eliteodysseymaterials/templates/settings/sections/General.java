package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.input.Clipboard;
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
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.service.SupportService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.window.FXWinUtil;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static nl.jixxed.eliteodysseymaterials.helper.DeeplinkHelper.deeplinkConsumer;
import static nl.jixxed.eliteodysseymaterials.helper.DeeplinkHelper.slefConsumer;
import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

@Slf4j
public class General extends DestroyableVBox implements DestroyableEventTemplate {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private DestroyableLabel selectedFolderLabel;
    private DestroyableButton journalSelectButton;
    private DestroyableComboBox<FontSize> fontsizeSelect;
    private DestroyableComboBox<ApplicationLocale> languageSelect;
    private DestroyableButton urlSchemeLinkingButton;
    private DestroyableLabel urlSchemeLinkingActiveLabel;

    public final BooleanProperty registered = new SimpleBooleanProperty(RegistryService.isRegistered());

    public General() {
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
        final DestroyableLabel generalLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.general")
                .build();
        final DestroyableHBox langSetting = createLangSetting();
        final DestroyableHBox fontSetting = creatFontSetting();
        final DestroyableHBox customJournalFolderSetting = createCustomJournalFolderSetting();
        final DestroyableHBox pollSetting = createPollSetting();
        final DestroyableHBox updateCheckSetting = createUpdateCheckSetting();
        final DestroyableHBox urlSchemeLinkingSetting = createUrlSchemeLinkingSetting();
        final DestroyableHBox exportInventory = createExportInventorySetting();
        final DestroyableHBox blueprintExpandedSetting = createBlueprintExpandedSetting();
        final DestroyableHBox importFromClipboardSetting = createImportFromClipboardSetting();
        final DestroyableHBox importSlefFromClipboardSetting = createImportSlefFromClipboardSetting();
        final DestroyableHBox supportPackageSetting = createSupportPackageSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(
                generalLabel,
                langSetting,
                fontSetting,
                customJournalFolderSetting,
                pollSetting,
                updateCheckSetting,
                urlSchemeLinkingSetting
        );
        if (OsCheck.isWindows()) {
            final DestroyableHBox darkModeSetting = createDarkModeSetting();
            this.getNodes().add(darkModeSetting);
        }
        this.getNodes().addAll(
                exportInventory,
                blueprintExpandedSetting,
                importFromClipboardSetting,
                importSlefFromClipboardSetting,
                supportPackageSetting
        );
    }

    private DestroyableHBox createDarkModeSetting() {
        final DestroyableLabel darkModeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.darkmode")
                .build();
        final DestroyableToggleSwitch darkModeCheckBox = ToggleSwitchBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.DARK_MODE, Boolean.FALSE))
                .withSelectedChangeListener((_, _, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.DARK_MODE, newValue);
                    FXWinUtil.setDarkMode(FXApplication.getInstance().getPrimaryStage(), PreferencesService.getPreference(PreferenceConstants.DARK_MODE, Boolean.FALSE));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(darkModeLabel, darkModeCheckBox)
                .buildHBox();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> applyFontSizeToComponents(fontSizeEvent.getFontSize(), this.journalSelectButton, this.fontsizeSelect, this.languageSelect)));
        register(EventService.addListener(false, true, TerminateApplicationEvent.class, _ -> executorService.shutdownNow()));
    }

    private static void applyFontSizeToComponents(Integer size, Node... components) {
        final String style = String.format("-fx-font-size: %dpx;", size);

        for (Node component : components) {
            component.setStyle(style);
        }
    }

    private DestroyableHBox createLangSetting() {
        DestroyableLabel languageLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.language")
                .build();
        this.languageSelect = ComboBoxBuilder.builder(ApplicationLocale.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(ApplicationLocale.values()))
                .withSelected(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.LANGUAGE, "ENGLISH")))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        LocaleService.setCurrentLocale(newValue.getLocale());
                        PreferencesService.setPreference(PreferenceConstants.LANGUAGE, newValue.name());
                        EventService.publish(new LanguageChangedEvent(newValue.getLocale()));
                    }
                })
                .asLocalized()
                .build();


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(languageLabel, this.languageSelect)
                .buildHBox();
    }

    private DestroyableHBox createBlueprintExpandedSetting() {
        DestroyableLabel blueprintExpandedLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.blueprint.expanded")
                .build();
        DestroyableLabel blueprintExpandedExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.blueprint.expanded.explain")
                .build();
        DestroyableCheckBox blueprintExpandedCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.TOOLTIP_BLUEPRINT_EXPANDED, Boolean.FALSE))
                .withSelectedProperty((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.TOOLTIP_BLUEPRINT_EXPANDED, newValue);
                    EventService.publish(new TooltipBlueprintsExpandEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(blueprintExpandedLabel, blueprintExpandedCheckBox, blueprintExpandedExplainLabel)
                .buildHBox();
    }

    private DestroyableHBox createCustomJournalFolderSetting() {
        DestroyableLabel journalFolderLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.journal.folder")
                .build();
        this.selectedFolderLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.getDefaultWatchedFolder()))
                .build();

        final DirectoryChooser journalFolderSelect = new DirectoryChooser();
        this.journalSelectButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText("tab.settings.journal.folder.select")
                .withOnAction(e -> {
                    File initialDirectory = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.getDefaultWatchedFolder()));
                    if (!initialDirectory.exists()) {
                        initialDirectory = new File(OsConstants.getDefaultWatchedFolder());
                    }
                    if (initialDirectory.exists()) {
                        journalFolderSelect.setInitialDirectory(initialDirectory);
                    }
                    final File selectedDirectory = journalFolderSelect.showDialog((FXApplication.getInstance()).getPrimaryStage());
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
                .withNodes(journalFolderLabel, this.journalSelectButton, this.selectedFolderLabel)
                .buildHBox();
    }

    private DestroyableHBox creatFontSetting() {
        DestroyableLabel fontsizeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.textsize")
                .build();
        this.fontsizeSelect = ComboBoxBuilder.builder(FontSize.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withSelected(FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")))
                .withItemsProperty(LocaleService.getListBinding(FontSize::values))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.TEXTSIZE, newValue.name());
                        EventService.publish(new FontSizeEvent(newValue.getSize()));
                    }
                })
                .asLocalized()
                .build();


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(fontsizeLabel, this.fontsizeSelect)
                .buildHBox();
    }

    private DestroyableHBox createPollSetting() {
        DestroyableLabel pollLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.poll")
                .build();
        DestroyableLabel pollExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.poll.explain")
                .build();
        DestroyableCheckBox pollCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.POLLING_FILE_MODE, Boolean.TRUE))
                .withSelectedProperty((_, _, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.POLLING_FILE_MODE, newValue);
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(pollLabel, pollCheckBox, pollExplainLabel)
                .buildHBox();
    }

    private DestroyableHBox createUpdateCheckSetting() {
        DestroyableLabel updateCheckLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.updateCheck")
                .build();
        DestroyableLabel updateCheckExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.updateCheck.explain")
                .build();
        DestroyableCheckBox pollCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.UPDATE_CHECKER_MODE, Boolean.TRUE))
                .withSelectedProperty((_, _, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.UPDATE_CHECKER_MODE, newValue);
                    EventService.publish(new PollingFileModeEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(updateCheckLabel, pollCheckBox, updateCheckExplainLabel)
                .buildHBox();
    }

    private DestroyableHBox createUrlSchemeLinkingSetting() {
        DestroyableLabel urlSchemeLinkingLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.url.scheme")
                .build();
        this.urlSchemeLinkingButton = ButtonBuilder.builder()
                .withText(RegistryService.isRegistered() ? "tab.settings.url.scheme.button.unregister" : "tab.settings.url.scheme.button.register")
                .withOnAction(_ -> {
                    this.urlSchemeLinkingActiveLabel.addBinding(this.urlSchemeLinkingActiveLabel.textProperty(), LocaleService.getStringBinding("tab.settings.url.scheme.checking"));
                    final boolean wasRegistered = RegistryService.isRegistered();
                    if (wasRegistered) {
                        RegistryService.unregisterApplication();
                    } else {
                        RegistryService.registerApplication();
                    }
                    this.urlSchemeLinkingButton.setDisable(true);
                    checkRegistration(wasRegistered);
                })
                .build();
        this.urlSchemeLinkingActiveLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(RegistryService.isRegistered() ? "tab.settings.url.scheme.registered" : "tab.settings.url.scheme.unregistered")
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(urlSchemeLinkingLabel, this.urlSchemeLinkingButton, this.urlSchemeLinkingActiveLabel)
                .buildHBox();
    }

    private void checkRegistration(boolean wasRegistered) {
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
                    Thread.currentThread().interrupt();
                }
            }
            final boolean finalIsRegisteredNow = isRegisteredNow;
            Platform.runLater(() -> {
                registered.set(finalIsRegisteredNow);
                EventService.publish(new ApplicationRegisteredEvent(finalIsRegisteredNow));
                this.urlSchemeLinkingButton.addBinding(this.urlSchemeLinkingButton.textProperty(), LocaleService.getStringBinding(finalIsRegisteredNow ? "tab.settings.url.scheme.button.unregister" : "tab.settings.url.scheme.button.register"));
                this.urlSchemeLinkingActiveLabel.addBinding(this.urlSchemeLinkingActiveLabel.textProperty(), LocaleService.getStringBinding(finalIsRegisteredNow ? "tab.settings.url.scheme.registered" : "tab.settings.url.scheme.unregistered"));
                this.urlSchemeLinkingButton.setDisable(false);
            });
        }, 1, TimeUnit.SECONDS);
    }

    private DestroyableHBox createExportInventorySetting() {
        final DestroyableLabel saveInventoryLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("settings.button.export.inventory")
                .build();
        final DestroyableButton saveInventory = ButtonBuilder.builder()
                .withText("settings.button.export.inventory.save")
                .withOnAction(_ -> EventService.publish(new SaveInventoryEvent(
                        TextExporter::createTextInventory,
                        CsvExporter::createCsvInventory,
                        XlsExporter::createXlsInventory)))
                .build();

        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(saveInventoryLabel, saveInventory)
                .buildHBox();
    }

    private DestroyableHBox createImportFromClipboardSetting() {
        final DestroyableLabel importClipboardLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("settings.button.import.clipboard")
                .build();
        final DestroyableLabel importClipboardExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("settings.button.import.clipboard.explain")
                .build();
        final DestroyableButton importClipboard = ButtonBuilder.builder()
                .withText("settings.button.import.clipboard.import")
                .withOnAction(_ ->
                        Platform.runLater(() -> {
                            final String clipboard = Clipboard.getSystemClipboard().getString();
                            if (clipboard != null && clipboard.startsWith("edomh://")) {
                                deeplinkConsumer.accept(clipboard);
                            }
                        }))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(importClipboardLabel, importClipboard, importClipboardExplainLabel)
                .buildHBox();
    }

    private DestroyableHBox createImportSlefFromClipboardSetting() {
        final DestroyableLabel importSlefClipboardLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("settings.button.import.clipboard.slef")
                .build();
        final DestroyableLabel importSlefClipboardExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("settings.button.import.clipboard.slef.explain")
                .build();
        final DestroyableButton importSlefClipboard = ButtonBuilder.builder()
                .withText("settings.button.import.clipboard.import.slef")
                .withOnAction(_ ->
                        Platform.runLater(() -> {
                            final String clipboard = Clipboard.getSystemClipboard().getString();
                            if (clipboard != null && !clipboard.startsWith("edomh://")) {
                                slefConsumer.accept(clipboard);
                            }
                        }))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(importSlefClipboardLabel, importSlefClipboard, importSlefClipboardExplainLabel)
                .buildHBox();
    }

    private DestroyableHBox createSupportPackageSetting() {
        final DestroyableLabel supportPackageLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("settings.button.support.package")
                .build();
        final DestroyableLabel supportPackageExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("settings.button.support.package.explain")
                .build();
        final DestroyableButton supportPackage = ButtonBuilder.builder()
                .withText("settings.button.support.package.create")
                .withOnAction(event -> {
                    final String supportPackageFile = SupportService.createSupportPackage();
                    HostServices host = FXApplication.getInstance().getHostServices();
                    host.showDocument(Path.of(supportPackageFile).toFile().getAbsoluteFile().getParent());
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(supportPackageLabel, supportPackage, supportPackageExplainLabel)
                .buildHBox();
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        executorService.shutdownNow();
        registered.unbind();
    }
}
