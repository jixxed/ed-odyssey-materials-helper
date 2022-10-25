package nl.jixxed.eliteodysseymaterials.templates.settings;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.export.CsvExporter;
import nl.jixxed.eliteodysseymaterials.export.TextExporter;
import nl.jixxed.eliteodysseymaterials.export.XlsExporter;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.ButtonIntField;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleSwitch;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class SettingsTab extends OdysseyTab {
    private static final String TESS4J_DIR = new File(OsConstants.TESS4J).getPath();

    private static final String SETTINGS_LABEL_CLASS = "settings-label";
    private static final String SETTINGS_LINK_CLASS = "settings-link";
    private static final String SETTINGS_DROPDOWN_CLASS = "settings-dropdown";
    private static final String SETTINGS_SPACING_10_CLASS = "settings-spacing-10";
    private static final String SETTINGS_JOURNAL_LINE_STYLE_CLASS = "settings-journal-line";
    private static final String SETTINGS_BUTTON_STYLE_CLASS = "settings-button";
    private final Application application;
    private ScrollPane scrollPane;
    private Label journalFolderLabel;
    private Label selectedFolderLabel;
    private Button journalSelectButton;
    private Label fontsizeLabel;
    private ComboBox<FontSize> fontsizeSelect;
    private ComboBox<ApplicationLocale> languageSelect;
    private Label languageLabel;
    private Label readingDirectionLabel;
    private ComboBox<MaterialOrientation> readingDirectionSelect;
    private CheckBox soloModeCheckBox;
    private Label soloModeLabel;
    private Label soloModeExplainLabel;
    private CheckBox notificationSoundCheckBox;
    private Label notificationSoundLabel;
    private Label notificationVolumeLabel;
    private Slider notificationVolumeSlider;
    private Button playNotificationButton;
    private Label overrideLabel;
    private ComboBox<OdysseyMaterial> overrideSelect;
    private Button overrideAddButton;
    private Label overrideListLabel;
    private ListView<OdysseyMaterial> overrideListView;
    private Button overrideRemoveButton;
    private Label trackingOptOutLabel;
    private Label trackingOptOutExplainLabel;
    private CheckBox trackingOptOutCheckBox;
    private Label wipLabel;
    private Label wipExplainLabel;
    private CheckBox wipCheckBox;
    private Label urlSchemeLinkingLabel;
    private Button urlSchemeLinkingButton;
    private Label urlSchemeLinkingActiveLabel;
    private Label wishlistHorizonsGradeRollsLabel;
    private HBox wishlistHorizonsGradeRolls;
    private DestroyableLabel capiConnectLabel;
    private DestroyableLabel capiStatusLabel;
    private Button capiConnectButton;

    private final BooleanProperty registered = new SimpleBooleanProperty(RegistryService.isRegistered());
    private Button capiDisconnectButton;
    private DestroyableLabel arOverlayLabel;
    private DestroyableToggleSwitch arOverlayButton;
    private DestroyableLabel tesseractDataPathFolderLabel;
    private DestroyableLabel selectedTesseractDataPathFolderLabel;
    private Button tesseractDataPathSelectButton;
    private DestroyableLabel arLocaleLabel;
    private DestroyableComboBox<ApplicationLocale> arLocaleSelect;
    private DestroyableLabel arCharacterWhitelistLabel;
    private TextField arCharacterWhitelistTextField;
    private Button arCharacterWhitelistSaveButton;
    private Label pollLabel;
    private Label pollExplainLabel;
    private CheckBox pollCheckBox;

    public SettingsTab(final Application application) {
        this.application = application;
        initComponents();
        initEventHandling();
        applyFontSizingHack();
    }

    private void applyFontSizingHack() {
        final String fontSizeStyle = "-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px";
        this.journalSelectButton.styleProperty().set(fontSizeStyle);
        this.fontsizeSelect.styleProperty().set(fontSizeStyle);
        this.languageSelect.styleProperty().set(fontSizeStyle);
        this.readingDirectionSelect.styleProperty().set(fontSizeStyle);

    }

    private void initEventHandling() {
        EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontSizeStyle = "-fx-font-size: " + fontSizeEvent.getFontSize() + "px;";
            this.journalSelectButton.setStyle(fontSizeStyle);
            this.fontsizeSelect.setStyle(fontSizeStyle);
            this.languageSelect.setStyle(fontSizeStyle);
            this.readingDirectionSelect.setStyle(fontSizeStyle);
        });
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.settings"));
        final Label settingsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tabs.settings"))
                .build();
        final VBox settings = BoxBuilder.builder()
                .withStyleClasses("settings-vbox", SETTINGS_SPACING_10_CLASS)
                .withNodes(settingsLabel)
                .buildVBox();
        //general
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
//        final HBox wipSetting = createWIPSetting();
        final VBox general = BoxBuilder.builder().withStyleClasses("settingsblock", SETTINGS_SPACING_10_CLASS).withNodes(generalLabel, langSetting, fontSetting, customJournalFolderSetting, pollSetting, urlSchemeLinkingSetting, exportInventory).buildVBox();
        settings.getChildren().add(general);
        //overview
        final Label overviewLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.odyssey.materials"))
                .build();
        final HBox readingDirectionSetting = createReadingDirectionSetting();
        final HBox soloModeSetting = createSoloModeSetting();
        final HBox irrelevantOverrideSetting = createIrrelevantOverrideSetting();
        final HBox irrelevantOverrideList = createIrrelevantOverrideList();
        final VBox overview = BoxBuilder.builder().withStyleClasses("settingsblock", SETTINGS_SPACING_10_CLASS).withNodes(overviewLabel, readingDirectionSetting, soloModeSetting, irrelevantOverrideSetting, irrelevantOverrideList).buildVBox();
        settings.getChildren().add(overview);
        //horizons wishlist
        final Label wishlistLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.wishlist"))
                .build();
        final HBox wishlistHorizonsGradeRollsSetting = createWishlistHorizonsGradeRollsSetting();
        final VBox wishlist = BoxBuilder.builder().withStyleClasses("settingsblock", SETTINGS_SPACING_10_CLASS).withNodes(wishlistLabel, wishlistHorizonsGradeRollsSetting).buildVBox();
        settings.getChildren().add(wishlist);
        //notification
        final Label notificationLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.notification"))
                .build();
        final HBox notificationSetting = createNotificationSetting();
        final HBox notificationSoundVolumeSetting = createNotificationVolumeSetting();
        final HBox notificationsListHeader = createNotificationListHeader();
        final VBox notification = BoxBuilder.builder().withStyleClasses("settingsblock", SETTINGS_SPACING_10_CLASS).withNodes(notificationLabel, notificationSetting, notificationSoundVolumeSetting, notificationsListHeader).buildVBox();
        Arrays.stream(NotificationType.values()).forEach(notificationType -> notification.getChildren().add(createCustomNotificationSoundSetting(notificationType)));
        settings.getChildren().add(notification);
        //frontier API
        final Label capiLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.capi"))
                .build();
        final Label capiExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.capi.explain"))
                .build();
        final HBox capiConnectSetting = createCapiConnectSetting();
        final VBox capiIntegration = BoxBuilder.builder().withStyleClasses("settingsblock", SETTINGS_SPACING_10_CLASS).withNodes(capiLabel, capiExplainLabel, capiConnectSetting).buildVBox();
        settings.getChildren().add(capiIntegration);
        //AR
        if (OsCheck.isWindows()) {
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
            final HBox arColorIrrelevantSetting = createARColorSetting(PreferenceConstants.AR_IRRELEVANT_COLOR, "tab.settings.ar.color.irrelevant", Color.RED);
            final HBox arColorWishlistSetting = createARColorSetting(PreferenceConstants.AR_WISHLIST_COLOR, "tab.settings.ar.color.wishlist", Color.LIME);
            final HBox arColorBlueprintSetting = createARColorSetting(PreferenceConstants.AR_BLUEPRINT_COLOR, "tab.settings.ar.color.blueprint", Color.BLUE);
            final VBox ar = BoxBuilder.builder().withStyleClasses("settingsblock", SETTINGS_SPACING_10_CLASS).withNodes(arLabel, BoxBuilder.builder().withNodes(arExplainLabel, vccLink).buildHBox(), arSetting, arLocaleSetting, arColorBlueprintSetting, arColorWishlistSetting, arColorIrrelevantSetting).buildVBox();
            settings.getChildren().add(ar);
        }
        //Tracking
        final Label trackingLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.tracking"))
                .build();
        final HBox trackingOptOutSetting = createTrackingOptOutSetting();
        final VBox tracking = BoxBuilder.builder().withStyleClasses("settingsblock", SETTINGS_SPACING_10_CLASS).withNodes(trackingLabel, trackingOptOutSetting).buildVBox();
        settings.getChildren().add(tracking);

        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(settings)
                .build();
        this.setContent(this.scrollPane);
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


    private HBox createNotificationListHeader() {
        final DestroyableLabel headerNotification = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.header.notification")).build();
        final DestroyableLabel headerEnabled = LabelBuilder.builder().withText(LocaleService.getStringBinding("tab.settings.notification.header.enabled")).build();
        return BoxBuilder.builder().withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS).withNodes(headerNotification, headerEnabled).buildHBox();
    }

    private HBox createUrlSchemeLinkingSetting() {
        this.urlSchemeLinkingLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.url.scheme")).build();
        this.urlSchemeLinkingButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding(RegistryService.isRegistered() ? "tab.settings.url.scheme.button.unregister" : "tab.settings.url.scheme.button.register"))
                .withOnAction(event -> {
                    boolean isRegistered = RegistryService.isRegistered();
                    if (isRegistered) {
                        RegistryService.unregisterApplication();
                    } else {
                        RegistryService.registerApplication();
                    }
                    isRegistered = RegistryService.isRegistered();
                    this.registered.set(isRegistered);
                    this.urlSchemeLinkingButton.textProperty().bind(LocaleService.getStringBinding(isRegistered ? "tab.settings.url.scheme.button.unregister" : "tab.settings.url.scheme.button.register"));
                    this.urlSchemeLinkingActiveLabel.textProperty().bind(LocaleService.getStringBinding(isRegistered ? "tab.settings.url.scheme.registered" : "tab.settings.url.scheme.unregistered"));
                })
                .build();
        this.urlSchemeLinkingActiveLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding(RegistryService.isRegistered() ? "tab.settings.url.scheme.registered" : "tab.settings.url.scheme.unregistered")).build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.urlSchemeLinkingLabel, this.urlSchemeLinkingButton, this.urlSchemeLinkingActiveLabel)
                .buildHBox();
    }

    private HBox createWishlistHorizonsGradeRollsSetting() {
        this.wishlistHorizonsGradeRollsLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.wishlist.grade.rolls")).build();
        final VBox[] gradeControls = Arrays.stream(HorizonsBlueprintGrade.values()).filter(Predicate.not(HorizonsBlueprintGrade.NONE::equals)).sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(grade ->
                {
                    final ButtonIntField buttonIntField = new ButtonIntField(0, 15, PreferencesService.getPreference(PreferenceConstants.WISHLIST_GRADE_ROLLS_PREFIX + grade.name(), grade.getDefaultNumberOfRolls()));
                    buttonIntField.addHandlerOnValidChange(rolls -> PreferencesService.setPreference(PreferenceConstants.WISHLIST_GRADE_ROLLS_PREFIX + grade.name(), rolls));
                    buttonIntField.getStyleClass().add("wishlist-rolls-select");
                    final DestroyableLabel label = LabelBuilder.builder().withStyleClass("wishlist-rolls-label").withNonLocalizedText(String.valueOf(grade.getGrade())).build();
                    final AnchorPane anchorPane2 = new AnchorPane(label);
                    AnchorPaneHelper.setAnchor(label, 0D, 0D, 0D, 0D);
                    return BoxBuilder.builder().withNodes(
                            anchorPane2,
                            buttonIntField
                    ).buildVBox();
                })
                .toArray(VBox[]::new);

        this.wishlistHorizonsGradeRolls = BoxBuilder.builder().withStyleClasses("grade-selects").withNodes(gradeControls).buildHBox();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.wishlistHorizonsGradeRollsLabel, this.wishlistHorizonsGradeRolls)
                .buildHBox();
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
        EventService.addListener(this, ARDisableEvent.class, event -> this.arOverlayButton.setSelected(false));
        this.arOverlayButton.setDisable(!OsCheck.isWindows());
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.arOverlayLabel, this.arOverlayButton)
                .buildHBox();
    }

    private HBox createARCharacterWhitelistSetting() {
        this.arCharacterWhitelistLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ar.character.whitelist")).build();
        this.arCharacterWhitelistTextField = TextFieldBuilder.builder().withStyleClass("setting-textfield-wide").build();
        this.arCharacterWhitelistTextField.setText(PreferencesService.getPreference(PreferenceConstants.AR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZ- "));
        this.arCharacterWhitelistSaveButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.settings.ar.character.whitelist.save")).withOnAction(event -> {
            PreferencesService.setPreference(PreferenceConstants.AR_CHAR_WHITELIST, this.arCharacterWhitelistTextField.getText());
            EventService.publish(new ARWhitelistChangeEvent(this.arCharacterWhitelistTextField.getText()));
        }).build();
        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.arCharacterWhitelistLabel, this.arCharacterWhitelistTextField, this.arCharacterWhitelistSaveButton)
                .buildHBox();
    }

    private HBox createARLocaleSetting() {
        this.arLocaleLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.ar.locale")).build();
        this.arLocaleSelect = ComboBoxBuilder.builder(ApplicationLocale.class)
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
        this.arLocaleSelect.getSelectionModel().select(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")));


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.arLocaleLabel, this.arLocaleSelect)
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

    private HBox createCapiConnectSetting() {
        this.capiConnectLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.capi.link.account")).build();
        this.capiConnectButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.settings.capi.connect"))
                .withOnAction(event -> CAPIService.getInstance().authenticate())
                .build();
        this.capiDisconnectButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.settings.capi.disconnect"))
                .withOnAction(event -> CAPIService.getInstance().deauthenticate())
                .build();
        this.capiConnectButton.disableProperty().bind(CAPIService.getInstance().getActive().or(this.registered.not()));
        this.capiDisconnectButton.disableProperty().bind(CAPIService.getInstance().getActive().not());
        if (this.registered.get()) {
            this.capiStatusLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withVisibilityProperty(CAPIService.getInstance().getActive()).withText(LocaleService.getStringBinding("tab.settings.capi.connected")).build();
        } else {
            this.capiStatusLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withVisibilityProperty(this.registered.not().and(CAPIService.getInstance().getActive().not())).withText(LocaleService.getStringBinding("tab.settings.capi.needs.registration")).build();
        }
        this.registered.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.capiStatusLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.capi.connected"));
                this.capiStatusLabel.visibleProperty().bind(CAPIService.getInstance().getActive());
            } else {
                this.capiStatusLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.capi.needs.registration"));
                this.capiStatusLabel.visibleProperty().bind(this.registered.not());
            }
        });
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.capiConnectLabel, this.capiConnectButton, this.capiDisconnectButton, this.capiStatusLabel)
                .buildHBox();
    }

    private HBox createNotificationVolumeSetting() {
        this.notificationVolumeLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.volume")).build();
        this.notificationVolumeSlider = SliderBuilder.builder()
                .withMin(0)
                .withMax(100)
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50))
                .withChangeListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_VOLUME, newValue.intValue()))
                .build();
        this.playNotificationButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.settings.notification.play"))
                .withOnAction(event -> {
                    final double volume = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50);
                    final String customSoundPath = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, "");
                    try {
                        final URI resource;
                        if (Objects.equals(customSoundPath, "")) {
                            resource = NotificationService.class.getResource("/audio/tweet.mp3").toURI();
                        } else {
                            resource = new File(customSoundPath).toURI();     // For example
                        }

                        final Media sound = new Media(resource.toString());
                        final MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.setVolume(volume / 100);
                        mediaPlayer.play();
                    } catch (final URISyntaxException | NullPointerException ex) {
                        log.error("Failed to play notification sound", ex);
                    }
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.notificationVolumeLabel, this.notificationVolumeSlider, this.playNotificationButton)
                .buildHBox();

    }

    private HBox createNotificationSetting() {
        this.notificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification")).build();
        this.notificationSoundCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND, Boolean.TRUE))
                .withChangeListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND, newValue))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.notificationSoundLabel, this.notificationSoundCheckBox)
                .buildHBox();

    }

    private HBox createCustomNotificationSoundSetting(final NotificationType notificationType) {
        final Label customNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.sound.custom", LocaleService.LocalizationKey.of("notification.type." + notificationType.name().toLowerCase()))).build();
        final Label selectedNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), "")).build();

        final FileChooser notificationSoundSelect = new FileChooser();
        //Set extension filter for mp3 files
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        notificationSoundSelect.getExtensionFilters().add(extFilter);
        final CheckBox notificationEnabledCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), notificationType.isDefaultEnabled()))
                .withChangeListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), newValue))
                .build();
        final Button customNotificationSoundSelectButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.notification.sound.select"))
                .withOnAction(e -> {
                    final File selectedFile = notificationSoundSelect.showOpenDialog(((FXApplication) this.application).getPrimaryStage());
                    if (selectedFile != null) {
                        selectedNotificationSoundLabel.setText(selectedFile.getAbsolutePath());
                        PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), selectedFile.getAbsolutePath());
                    }
                })
                .build();
        final Button customNotificationSoundClearButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.notification.sound.clear"))
                .withOnAction(e -> {
                    selectedNotificationSoundLabel.setText("");
                    PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), "");
                })
                .build();

        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(customNotificationSoundLabel, notificationEnabledCheckBox, customNotificationSoundSelectButton, customNotificationSoundClearButton, selectedNotificationSoundLabel)
                .buildHBox();
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

    private HBox createTrackingOptOutSetting() {
        this.trackingOptOutLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.tracking.opt.out")).build();
        this.trackingOptOutExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.tracking.opt.out.explain")).build();
        this.trackingOptOutCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) ->
                        PreferencesService.setPreference(PreferenceConstants.TRACKING_OPT_OUT, newValue)
                )
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.trackingOptOutLabel, this.trackingOptOutCheckBox, this.trackingOptOutExplainLabel)
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
                SettingsTab.this.overrideAddButton.setDisable(SettingsTab.this.overrideListView.getItems().contains(SettingsTab.this.overrideSelect.getSelectionModel().getSelectedItem()))
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

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.SETTINGS;
    }

}
