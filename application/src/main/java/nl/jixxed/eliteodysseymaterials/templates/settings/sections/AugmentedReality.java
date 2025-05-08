package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
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
import nl.jixxed.eliteodysseymaterials.service.event.ARDisableEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ARLocaleChangeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ARWhitelistChangeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

@Slf4j
public class AugmentedReality extends DestroyableVBox implements DestroyableEventTemplate {
    private static final String TESS4J_DIR = new File(OsConstants.getTess4j()).getPath();

    private DestroyableLabel arOverlayLabel;
    private DestroyableToggleSwitch arOverlayButton;
    private DestroyableTextField arCharacterWhitelistTextField;

    public AugmentedReality() {
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel arLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.ar")
                .build();
        final DestroyableLabel arExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.ar.explain")
                .build();
        final DestroyableHyperlink vccLink = HyperlinkBuilder.builder()
                .withStyleClass(SETTINGS_LINK_CLASS)
                .withOnAction(_ -> FXApplication.getInstance().getHostServices().showDocument("https://aka.ms/vs/17/release/vc_redist.x64.exe"))
                .withText("tab.settings.ar.link")
                .build();
        final DestroyableHBox arSetting = createARSetting();
        final DestroyableHBox arLocaleSetting = createARLocaleSetting();
        final DestroyableHBox arBartenderSetting = createARBartenderSetting();
        final DestroyableHBox arColorPowerplaySetting = createARColorSetting(PreferenceConstants.AR_POWERPLAY_COLOR, "tab.settings.ar.color.powerplay", Color.PURPLE);
        final DestroyableHBox arColorIrrelevantSetting = createARColorSetting(PreferenceConstants.AR_IRRELEVANT_COLOR, "tab.settings.ar.color.irrelevant", Color.RED);
        final DestroyableHBox arColorWishlistSetting = createARColorSetting(PreferenceConstants.AR_WISHLIST_COLOR, "tab.settings.ar.color.wishlist", Color.LIME);
        final DestroyableHBox arColorBlueprintSetting = createARColorSetting(PreferenceConstants.AR_BLUEPRINT_COLOR, "tab.settings.ar.color.blueprint", Color.BLUE);
        final DestroyableHBox arColorBartenderSetting = createARColorSetting(PreferenceConstants.AR_BARTENDER_COLOR, "tab.settings.ar.color.bartender", Color.WHITE);

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(arLabel, BoxBuilder.builder()
                .withNodes(arExplainLabel, vccLink).buildHBox(), arSetting, arLocaleSetting, arColorBlueprintSetting, arColorWishlistSetting, arColorIrrelevantSetting, arColorPowerplaySetting, arBartenderSetting, arColorBartenderSetting);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ARDisableEvent.class, _ -> this.arOverlayButton.setSelected(false)));
    }

    private DestroyableHBox createARSetting() {
        this.arOverlayLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.ar.toggle")
                .build();
        this.arOverlayButton = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((_, _, newValue) -> {
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

    private DestroyableHBox createARBartenderSetting() {
        DestroyableLabel arBartenderLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.ar.bartender.toggle")
                .build();
        DestroyableLabel arBartenderLabelExplain = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.ar.bartender.toggle.explain")
                .build();
        DestroyableToggleSwitch arBartenderButton = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((_, _, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.ENABLE_BARTENDER_AR, Boolean.TRUE.equals(newValue));
                    Platform.runLater(ARService::bartenderToggle);
                })
                .withSelected(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_AR, true))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(arBartenderLabel, arBartenderButton, arBartenderLabelExplain)
                .buildHBox();
    }

    private DestroyableHBox createARCharacterWhitelistSetting() {
        DestroyableLabel arCharacterWhitelistLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.ar.character.whitelist")
                .build();
        this.arCharacterWhitelistTextField = TextFieldBuilder.builder()
                .withStyleClass("setting-textfield-wide")
                .build();
        this.arCharacterWhitelistTextField.setText(PreferencesService.getPreference(PreferenceConstants.AR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZ- "));
        DestroyableButton arCharacterWhitelistSaveButton = ButtonBuilder.builder()
                .withText("tab.settings.ar.character.whitelist.save")
                .withOnAction(_ -> {
                    PreferencesService.setPreference(PreferenceConstants.AR_CHAR_WHITELIST, this.arCharacterWhitelistTextField.getText());
                    EventService.publish(new ARWhitelistChangeEvent(this.arCharacterWhitelistTextField.getText()));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(arCharacterWhitelistLabel, this.arCharacterWhitelistTextField, arCharacterWhitelistSaveButton)
                .buildHBox();
    }

    private DestroyableHBox createARLocaleSetting() {
        DestroyableLabel arLocaleLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.ar.locale")
                .build();

        DestroyableComboBox<ApplicationLocale> arLocaleSelect = ComboBoxBuilder.builder(ApplicationLocale.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(Arrays.stream(ApplicationLocale.values()).filter(ApplicationLocale::isAr).toArray(ApplicationLocale[]::new)))
                .withSelected(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")))
                .withValueChangeListener((_, _, newValue) -> {
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
                                final URL url = new URI(downloadUrl).toURL();
                                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                final InputStream responseStream = connection.getInputStream();
                                output.write(responseStream.readAllBytes());
                                NotificationService.showInformation(NotificationType.SUCCESS, LocaleService.LocaleString.of("notification.ar.download.complete.title"), LocaleService.LocaleString.of("notification.ar.download.complete.text"));
                            } catch (final IOException | URISyntaxException e) {
                                log.error("error downloading OCR languagedata", e);
                                NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.ar.download.failed.title"), LocaleService.LocaleString.of("notification.ar.download.failed.text"));
                            }
                        }

//
                    }
                })
                .asLocalized()
                .build();


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(arLocaleLabel, arLocaleSelect)
                .buildHBox();

    }

    private DestroyableHBox createARColorSetting(final String preferenceName, final String localizationKey, final Color defaultColor) {
        this.arOverlayLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding(localizationKey))
                .build();
        final DestroyableColorPicker colorPicker = new DestroyableColorPicker();
        colorPicker.setValue(Color.valueOf(PreferencesService.getPreference(preferenceName, defaultColor.toString())));
        colorPicker.addChangeListener(colorPicker.valueProperty(), (_, _, newValue) -> PreferencesService.setPreference(preferenceName, newValue.toString()));
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.arOverlayLabel, colorPicker)
                .buildHBox();
    }


}
