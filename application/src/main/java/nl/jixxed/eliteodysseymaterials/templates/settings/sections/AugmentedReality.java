package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.ARService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleSwitch;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;
@Slf4j
public class AugmentedReality extends VBox implements Template {
    private static final String TESS4J_DIR = new File(OsConstants.TESS4J).getPath();
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private DestroyableLabel arOverlayLabel;
    private DestroyableToggleSwitch arOverlayButton;
    private TextField arCharacterWhitelistTextField;
    private final Application application;
    public AugmentedReality(Application application) {
        this.application = application;
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label arLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.ar"))
                .build();
        final Label arExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.ar.explain"))
                .build();
        final Hyperlink vccLink = HyperlinkBuilder.builder().withStyleClass(SETTINGS_LINK_CLASS).withAction(actionEvent ->
                this.application.getHostServices().showDocument("https://aka.ms/vs/17/release/vc_redist.x64.exe")).withText(LocaleService.getStringBinding("tab.settings.ar.link")).build();
        final HBox arSetting = createARSetting();
        final HBox arLocaleSetting = createARLocaleSetting();
        final HBox arBartenderSetting = createARBartenderSetting();
        final HBox arColorIrrelevantSetting = createARColorSetting(PreferenceConstants.AR_IRRELEVANT_COLOR, "tab.settings.ar.color.irrelevant", Color.RED);
        final HBox arColorWishlistSetting = createARColorSetting(PreferenceConstants.AR_WISHLIST_COLOR, "tab.settings.ar.color.wishlist", Color.LIME);
        final HBox arColorBlueprintSetting = createARColorSetting(PreferenceConstants.AR_BLUEPRINT_COLOR, "tab.settings.ar.color.blueprint", Color.BLUE);
        final HBox arColorBartenderSetting = createARColorSetting(PreferenceConstants.AR_BARTENDER_COLOR, "tab.settings.ar.color.bartender", Color.WHITE);

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(arLabel, BoxBuilder.builder().withNodes(arExplainLabel, vccLink).buildHBox(), arSetting, arLocaleSetting, arColorBlueprintSetting, arColorWishlistSetting, arColorIrrelevantSetting, arBartenderSetting, arColorBartenderSetting);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, ARDisableEvent.class, event -> this.arOverlayButton.setSelected(false)));
    }

    private HBox createARSetting() {
        this.arOverlayLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ar.toggle")).build();
        this.arOverlayButton = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((observable, oldValue, newValue) -> {
                    if (OsCheck.isWindows()) {
                        PreferencesService.setPreference(PreferenceConstants.ENABLE_AR, Boolean.TRUE.equals(newValue));
                        Platform.runLater(ARService::toggle);
                    }
                })
                .withSelected(PreferencesService.getPreference(PreferenceConstants.ENABLE_AR, false))
                .build();
        this.arOverlayButton.setDisable(!OsCheck.isWindows());
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.arOverlayLabel, this.arOverlayButton)
                .buildHBox();
    }
    private HBox createARBartenderSetting() {
        DestroyableLabel arBartenderLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ar.bartender.toggle")).build();
        DestroyableToggleSwitch arBartenderButton = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.ENABLE_BARTENDER_AR, Boolean.TRUE.equals(newValue));
                    Platform.runLater(ARService::bartenderToggle);
                })
                .withSelected(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_AR, true))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(arBartenderLabel, arBartenderButton)
                .buildHBox();
    }

    private HBox createARCharacterWhitelistSetting() {
        DestroyableLabel arCharacterWhitelistLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ar.character.whitelist")).build();
        this.arCharacterWhitelistTextField = TextFieldBuilder.builder().withStyleClass("setting-textfield-wide").build();
        this.arCharacterWhitelistTextField.setText(PreferencesService.getPreference(PreferenceConstants.AR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZ- "));
        Button arCharacterWhitelistSaveButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.settings.ar.character.whitelist.save")).withOnAction(event -> {
            PreferencesService.setPreference(PreferenceConstants.AR_CHAR_WHITELIST, this.arCharacterWhitelistTextField.getText());
            EventService.publish(new ARWhitelistChangeEvent(this.arCharacterWhitelistTextField.getText()));
        }).build();
        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(arCharacterWhitelistLabel, this.arCharacterWhitelistTextField, arCharacterWhitelistSaveButton)
                .buildHBox();
    }

    private HBox createARLocaleSetting() {
        DestroyableLabel arLocaleLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ar.locale")).build();
        //download
        //                                connection.setRequestProperty("accept", "application/json");
        //
        DestroyableComboBox<ApplicationLocale> arLocaleSelect = ComboBoxBuilder.builder(ApplicationLocale.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(ApplicationLocale.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        final File tessdataPath = new File(TESS4J_DIR, "tessdata");
                        final File targetFile = new File(tessdataPath.getPath(), newValue.getIso6392B() + ".traineddata");
                        if (targetFile.exists() || newValue == ApplicationLocale.ENGLISH) {
                            PreferencesService.setPreference(PreferenceConstants.AR_LOCALE, newValue.name());
                            EventService.publish(new ARLocaleChangeEvent(newValue));
                        } else {//download
                            tessdataPath.mkdirs();
                            try (final OutputStream output = new FileOutputStream(targetFile)) {
                                final String downloadUrl = "https://github.com/tesseract-ocr/tessdata/raw/main/" + newValue.getIso6392B() + ".traineddata";
                                final URL url = new URL(downloadUrl);
                                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                                connection.setRequestProperty("accept", "application/json");
                                final InputStream responseStream = connection.getInputStream();
                                output.write(responseStream.readAllBytes());
                                NotificationService.showInformation(NotificationType.SUCCESS, "Download complete", "Download of OCR languagedata complete");
                            } catch (final IOException e) {
                                log.error("error downloading OCR languagedata", e);
                                NotificationService.showError(NotificationType.ERROR, "Download failed", "Failed downloading OCR languagedata");
                            }
                        }

//
                    }
                })
                .asLocalized()
                .build();
        arLocaleSelect.getSelectionModel().select(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")));


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(arLocaleLabel, arLocaleSelect)
                .buildHBox();

    }

    private HBox createARColorSetting(final String preferenceName, final String localizationKey, final Color defaultColor) {
        this.arOverlayLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding(localizationKey)).build();
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.valueOf(PreferencesService.getPreference(preferenceName, defaultColor.toString())));
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            PreferencesService.setPreference(preferenceName, newValue.toString());
        });
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.arOverlayLabel, colorPicker)
                .buildHBox();
    }


}
