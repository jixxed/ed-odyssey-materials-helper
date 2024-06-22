package nl.jixxed.eliteodysseymaterials;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.helper.DeeplinkHelper;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.parser.FileProcessor;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.ApplicationLayout;
import nl.jixxed.eliteodysseymaterials.templates.LoadingScreen;
import nl.jixxed.eliteodysseymaterials.templates.dialog.EDDNDialog;
import nl.jixxed.eliteodysseymaterials.templates.dialog.StartDialog;
import nl.jixxed.eliteodysseymaterials.templates.dialog.URLSchemeDialog;
import nl.jixxed.eliteodysseymaterials.templates.dialog.VersionDialog;
import nl.jixxed.eliteodysseymaterials.watchdog.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static nl.jixxed.eliteodysseymaterials.helper.DeeplinkHelper.deeplinkConsumer;

@Slf4j
public class FXApplication extends Application {

    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MAIN_STYLESHEET = "/nl/jixxed/eliteodysseymaterials/style/style.css";

    private ApplicationLayout applicationLayout;
    private TimeStampedGameStateWatcher timeStampedCargoWatcher;
    private TimeStampedGameStateWatcher timeStampedShipLockerWatcher;
    private TimeStampedGameStateWatcher timeStampedBackPackWatcher;
    private GameStateWatcher fleetCarrierWatcher;
    private StateFileWatcher statusWatcher;
    private final JournalWatcher journalWatcher = new JournalWatcher();
    private final DeeplinkWatcher deeplinkWatcher = new DeeplinkWatcher();
    private Stage primaryStage;
    private TimeStampedGameStateWatcher shipyardWatcher;
    private TimeStampedGameStateWatcher outfittingWatcher;
    private TimeStampedGameStateWatcher marketWatcher;
    private TimeStampedGameStateWatcher navrouteWatcher;
    private TimeStampedGameStateWatcher modulesinfoWatcher;
    private TimeStampedGameStateWatcher fcMaterialsWatcher;
    private final java.util.List<EventListener<?>> eventListeners = new ArrayList<>();

    private boolean initialized = false;
    private final AtomicReference<EventListener<CommanderAllListedEvent>> deeplinkReference = new AtomicReference<>();
    private LoadingScreen loadingScreen;
    private StackPane content;
    private Scene scene;


    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    @Override
    public void start(final Stage primaryStage) {
        DeeplinkHelper.setFxApplication(this);
        NotificationService.init();
        PinnedBlueprintService.init();
        ScalingHelper.init();
        HighGradeEmissionService.initialize();
        EDDNService.init();
        try {
            try {
                final GraphicsEnvironment ge =
                        GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/fonts/eurocaps.ttf")));
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/fonts/832-font.ttf")));
            } catch (final IOException | FontFormatException e) {
                //Handle exception
            }
            PreferencesService.setPreference(PreferenceConstants.APP_SETTINGS_VERSION, System.getProperty("app.version"));
            whatsnewPopup();
            urlSchemePopup();
            eddnPopup();
            versionPopup();
            MaterialTrackingService.initialize();
//            HighGradeEmissionService.initialize();
            CAPIService.getInstance(this);
            this.loadingScreen = new LoadingScreen();
            eventListeners.add(EventService.addListener(this, JournalInitEvent.class, event -> {

                if (event.isInitialised() && this.applicationLayout == null) {
                    log.debug("applicationLayout");

                    Platform.runLater(() -> {
                        this.applicationLayout = new ApplicationLayout(this);
                        this.content.getChildren().add(this.applicationLayout);
                        setupStyling(scene);
                        this.applicationLayout.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
                        Platform.runLater(() -> {
                            EventService.publish(new ApplicationLifeCycleEvent());
                        });
                    });
                    log.debug("setupFleetCarrierWatcher");
                    Platform.runLater(() -> setupFleetCarrierWatcher(this.journalWatcher.getWatchedFolder(), APPLICATION_STATE.getPreferredCommander().orElse(null)));
                    log.debug("loadingScreen");
                    Platform.runLater(() -> this.content.getChildren().remove(this.loadingScreen));

                }
            }));
            this.primaryStage = primaryStage;
            primaryStage.setTitle(AppConstants.APP_TITLE);
            primaryStage.getIcons().add(new Image(FXApplication.class.getResourceAsStream(AppConstants.APP_ICON_PATH)));
            setupDeeplinkWatcher();
            setupWatchers();

            initEventHandling();
            createApplicationScene();
            KeyCombination kc = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
            Runnable rn = () -> {
                Platform.runLater(()->{
                    final String clipboard = Clipboard.getSystemClipboard().getString();
                    if (clipboard.startsWith("edomh://")) {
                        deeplinkConsumer.accept(clipboard);
                    }
                });
            };
            scene.getAccelerators().put(kc, rn);
            setupStyling(scene);
            primaryStage.setScene(scene);
            primaryStage.show();

            if (PreferencesService.getPreference(PreferenceConstants.ENABLE_AR, false)) {
                if (OsCheck.isWindows()) {
                    ARService.toggle();
                }
            }

        } catch (final Exception ex) {
            showAlert(ex);
        }
    }

    private void showAlert(final Exception ex) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(800, 800);
        Platform.runLater(() -> alert.setResizable(false));
        alert.setTitle("Application Error");
        alert.setHeaderText("Please contact the developer with the following information");
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        alert.setContentText(stringWriter.toString());
        alert.showAndWait();
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, WatchedFolderChangedEvent.class, event -> resetWatchedFolder(new File(event.getPath()))));
        this.eventListeners.add(EventService.addListener(this, CommanderSelectedEvent.class, event -> {
            UserPreferencesService.loadUserPreferences(event.getCommander());
            if (this.initialized) {
                reset(this.journalWatcher.getWatchedFolder());
            }
        }));
        this.eventListeners.add(EventService.addListener(this, CommanderAllListedEvent.class, event -> {
            this.initialized = true;
        }));
        this.eventListeners.add(EventService.addListener(this, JournalInitEvent.class, event -> {
//            if (event.isInitialised()) {
//                Platform.runLater(() -> setupFleetCarrierWatcher(this.journalWatcher.getWatchedFolder(), APPLICATION_STATE.getPreferredCommander().orElse(null)));
//                Platform.runLater(() -> this.content.getChildren().remove(this.loadingScreen));
//            }
        }));
        this.eventListeners.add(EventService.addListener(this, FontSizeEvent.class, fontSizeEvent -> {
            if(applicationLayout != null) {
                this.applicationLayout.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
            }
            EventService.publish(new AfterFontSizeSetEvent(fontSizeEvent.getFontSize()));
        }));
        this.primaryStage.setOnCloseRequest(event -> {
            try {
                BackupService.createConfigBackup();
                EventService.publish(new TerminateApplicationEvent());
                EventService.shutdown();
//                NativeLibrary.disposeAll();
                APPLICATION_STATE.releaseLock();
                Platform.exit();
            } catch (final Exception ex) {
                //don't care
            }
        });
        this.eventListeners.add(EventService.addListener(this, SaveWishlistEvent.class, event -> {
            final FileChooser fileChooser = new FileChooser();
            //Set extension filter for text files
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
            //Show save file dialog
            final File file = fileChooser.showSaveDialog(this.primaryStage);
            if (file != null) {
                if (fileChooser.getSelectedExtensionFilter().getExtensions().contains("*.txt")) {
                    saveTextToFile(event.getTextSupplier().get(), file);
                } else if (fileChooser.getSelectedExtensionFilter().getExtensions().contains("*.csv")) {
                    saveTextToFile(event.getCsvSupplier().get(), file);
                } else if (fileChooser.getSelectedExtensionFilter().getExtensions().contains("*.xls")) {
                    saveXlsToFile(event.getXlsSupplier().get(), file);
                }
            }
        }));
        this.eventListeners.add(EventService.addListener(this, SaveInventoryEvent.class, event -> {
            final FileChooser fileChooser = new FileChooser();
            //Set extension filter for text files
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
            //Show save file dialog
            final File file = fileChooser.showSaveDialog(this.primaryStage);
            if (file != null) {
                if (fileChooser.getSelectedExtensionFilter().getExtensions().contains("*.txt")) {
                    saveTextToFile(event.getTextSupplier().get(), file);
                } else if (fileChooser.getSelectedExtensionFilter().getExtensions().contains("*.csv")) {
                    saveTextToFile(event.getCsvSupplier().get(), file);
                } else if (fileChooser.getSelectedExtensionFilter().getExtensions().contains("*.xls")) {
                    saveXlsToFile(event.getXlsSupplier().get(), file);
                }
            }
        }));
    }

    private void setupWatchers() {
        final File watchedFolder = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER));
        setupStorageWatchers(watchedFolder);
    }

    private void setupStorageWatchers(final File watchedFolder) {

        this.timeStampedCargoWatcher = new TimeStampedGameStateWatcher(watchedFolder, file -> FileProcessor.processCargoStateFile(file, JournalEventType.CARGO), AppConstants.CARGO_FILE, true, JournalEventType.CARGO);
        this.timeStampedShipLockerWatcher = new TimeStampedGameStateWatcher(watchedFolder, file -> FileProcessor.processCargoStateFile(file, JournalEventType.SHIPLOCKER), AppConstants.SHIPLOCKER_FILE, true, JournalEventType.SHIPLOCKER);
        this.timeStampedBackPackWatcher = new TimeStampedGameStateWatcher(watchedFolder, file -> FileProcessor.processCargoStateFile(file, JournalEventType.BACKPACK), AppConstants.BACKPACK_FILE, true, JournalEventType.BACKPACK);
        this.journalWatcher.watch(watchedFolder, FileProcessor::processJournal, FileProcessor::resetAndProcessJournal);
        this.statusWatcher = new StateFileWatcher(watchedFolder, FileProcessor::processStatusFile, AppConstants.STATUS_FILE);
        this.shipyardWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processOtherStateFile, AppConstants.SHIPYARD_FILE, true, JournalEventType.SHIPYARD);
        this.outfittingWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processOtherStateFile, AppConstants.OUTFITTING_FILE, true, JournalEventType.OUTFITTING);
        this.marketWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processOtherStateFile, AppConstants.MARKET_FILE, true, JournalEventType.MARKET);
        this.navrouteWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processOtherStateFile, AppConstants.NAVROUTE_FILE, true, JournalEventType.NAVROUTE);
        this.modulesinfoWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processOtherStateFile, AppConstants.MODULESINFO_FILE, true, JournalEventType.MODULEINFO);
        this.fcMaterialsWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processOtherStateFile, AppConstants.FCMATERIALS_FILE, true, JournalEventType.FCMATERIALS);


    }

    private void setupFleetCarrierWatcher(final File watchedFolder, final Commander commander) {

        if (commander != null) {
            final String pathname = OsConstants.CONFIG_DIRECTORY + OsConstants.OS_SLASH + commander.getFid().toLowerCase(Locale.ENGLISH) + (commander.getGameVersion().equals(GameVersion.LEGACY) ? ".legacy" : "");
            final File watchedFolderFleetCarrier = new File(pathname);
            if (!watchedFolderFleetCarrier.exists()) {
                watchedFolderFleetCarrier.mkdirs();
            }
            this.fleetCarrierWatcher = new GameStateWatcher();
            ApplicationState.getInstance().getFcMaterials().set(false);
            this.fleetCarrierWatcher.watch(watchedFolderFleetCarrier, file -> FileProcessor.processCapiFile(file, JournalEventType.CAPIFLEETCARRIER), AppConstants.FLEETCARRIER_FILE, false, JournalEventType.CAPIFLEETCARRIER);
        }

    }

    private void setupDeeplinkWatcher() {

        this.deeplinkReference.set(EventService.addListener(this, CommanderAllListedEvent.class, event -> {

            final File deeplinkWatchedFolder = new File(OsConstants.DEEPLINK_FOLDER);

            this.deeplinkWatcher.watch(deeplinkWatchedFolder, deeplinkConsumer, AppConstants.DEEPLINK_FILE);
            EventService.removeListener(this.deeplinkReference.get());

        }));

    }



    private Scene createApplicationScene() {
        content = new StackPane(/*this.applicationLayout, */this.loadingScreen);
        content.getStyleClass().add("app");
        scene = new Scene(content, PreferencesService.getPreference(PreferenceConstants.APP_WIDTH, 800D), PreferencesService.getPreference(PreferenceConstants.APP_HEIGHT, 600D));

        scene.widthProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_WIDTH, Math.max((Double) newValue, 175.0D)));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_HEIGHT, Math.max((Double) newValue, 175.0D)));
        final Bounds allScreenBounds = computeAllScreenBounds();
        final double minX = allScreenBounds.getMinX() - 8.0D;
        final double minY = allScreenBounds.getMinY() - 8.0D;
        final double maxX = allScreenBounds.getMaxX();
        final double maxY = allScreenBounds.getMaxY();

        this.primaryStage.xProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_X, Math.max((Double) newValue, minX)));
        this.primaryStage.yProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_Y, Math.max((Double) newValue, minY)));
        this.primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.APP_MAXIMIZED, newValue));
        final Double savedX = PreferencesService.getPreference(PreferenceConstants.APP_X, 0D);
        final Double savedY = PreferencesService.getPreference(PreferenceConstants.APP_Y, 0D);
        double x = savedX;
        double y = savedY;
        if (savedX < minX || savedX > maxX) {
            x = 0D;
        }
        if (savedY < minY || savedY > maxY) {
            y = 0D;
        }
        this.primaryStage.setX(x);
        this.primaryStage.setY(y);
        this.primaryStage.setMaximized(PreferencesService.getPreference(PreferenceConstants.APP_MAXIMIZED, Boolean.FALSE));
        return scene;
    }

    private Bounds computeAllScreenBounds() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (final Screen screen : Screen.getScreens()) {
            final Rectangle2D screenBounds = screen.getBounds();
            if (screenBounds.getMinX() < minX) {
                minX = screenBounds.getMinX();
            }
            if (screenBounds.getMinY() < minY) {
                minY = screenBounds.getMinY();
            }
            if (screenBounds.getMaxX() > maxX) {
                maxX = screenBounds.getMaxX();
            }
            if (screenBounds.getMaxY() > maxY) {
                maxY = screenBounds.getMaxY();
            }
        }
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    private void setupStyling(final Scene scene) {
        if(applicationLayout != null) {
            this.applicationLayout.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        }
        final JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getResource(MAIN_STYLESHEET).toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/notificationpopup.css").toExternalForm());
        try {
            addCustomCss(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("java:S899")
    private void addCustomCss(final Scene scene) throws IOException {
        final File customCss = new File(OsConstants.CUSTOM_CSS);
        if (customCss.exists()) {
            importCustomCss(scene, customCss);
        }
    }

    private void urlSchemePopup() {
        final boolean urlSchemeAsked = PreferencesService.getPreference(PreferenceConstants.URL_SCHEME, false).equals(true);
        if (!urlSchemeAsked) {
            final Stage urlSchemeStage = new Stage();

            final Scene urlSchemeScene = new Scene(new URLSchemeDialog(urlSchemeStage), 640, 175);
            urlSchemeStage.initModality(Modality.APPLICATION_MODAL);
            final JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(urlSchemeScene);
            urlSchemeScene.getStylesheets().add(getClass().getResource(MAIN_STYLESHEET).toExternalForm());
            urlSchemeStage.setScene(urlSchemeScene);
            urlSchemeStage.titleProperty().set("Register url scheme");
            urlSchemeStage.showAndWait();
        }
    }

    private void eddnPopup() {
        final boolean urlSchemeAsked = PreferencesService.getPreference(PreferenceConstants.EDDN_ASKED, false).equals(true);
        if (!urlSchemeAsked) {
            final Stage eddnStage = new Stage();

            final Scene eddnScene = new Scene(new EDDNDialog(eddnStage), 640, 175);
            eddnStage.initModality(Modality.APPLICATION_MODAL);
            final JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(eddnScene);
            eddnScene.getStylesheets().add(getClass().getResource(MAIN_STYLESHEET).toExternalForm());
            eddnStage.setScene(eddnScene);
            eddnStage.titleProperty().set("EDDN Support");
            eddnStage.showAndWait();
        }
    }

    private void versionPopup() {
        if (!VersionService.isBeta()) {
            final String buildVersion = VersionService.getBuildVersion();
            String latestVersion = "";
            try {
                latestVersion = VersionService.getLatestVersion();
            } catch (final IOException e) {
                log.error("Error retrieving latest version", e);
            }

            if (VersionService.getBuildVersion() != null && !buildVersion.equals(latestVersion) && !latestVersion.isBlank()) {
                final Stage versionStage = new Stage();

                final Scene versionScene = new Scene(new VersionDialog(versionStage, this), 640, 175);
                versionStage.initModality(Modality.APPLICATION_MODAL);
                final JMetro jMetro = new JMetro(Style.DARK);
                jMetro.setScene(versionScene);
                versionScene.getStylesheets().add(getClass().getResource(MAIN_STYLESHEET).toExternalForm());
                versionStage.setScene(versionScene);
                versionStage.titleProperty().set("New version");
                versionStage.setAlwaysOnTop(true);
                versionStage.showAndWait();
            }
        }
    }

    private void whatsnewPopup() {
        final boolean whatsNewSeen = PreferencesService.getPreference(PreferenceConstants.WHATS_NEW_VERSION, "").equals(PreferencesService.getPreference(PreferenceConstants.APP_SETTINGS_VERSION, "0"));
        if (!whatsNewSeen || !PreferencesService.getPreference(PreferenceConstants.POLICY_ACCEPT_VERSION, "").equals(StartDialog.POLICY_LEVEL_REQUIRED)) {
            final Stage policyStage = new Stage();

            final Scene policyScene = new Scene(new StartDialog(policyStage), 640, 480);
            policyStage.initModality(Modality.APPLICATION_MODAL);
            final JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(policyScene);
            policyScene.getStylesheets().add(getClass().getResource(MAIN_STYLESHEET).toExternalForm());
            policyStage.setScene(policyScene);
            policyStage.titleProperty().set("What's new & privacy policy");
            policyStage.setAlwaysOnTop(true);
            policyStage.showAndWait();
            if (!PreferencesService.getPreference(PreferenceConstants.POLICY_ACCEPT_VERSION, "").equals(StartDialog.POLICY_LEVEL_REQUIRED)) {
                System.exit(0);
            } else {
                PreferencesService.setPreference(PreferenceConstants.POLICY_ACCEPT_VERSION, StartDialog.POLICY_LEVEL_REQUIRED);
            }
        }
    }

    private void importCustomCss(final Scene scene, final File customCss) {
        try {
            scene.getStylesheets().add(customCss.toURI().toURL().toExternalForm());
        } catch (final IOException e) {
            log.error("Error loading stylesheet", e);
        }
    }

    private void resetWatchedFolder(final File watchedFolder) {
        APPLICATION_STATE.resetCommanders();
        EventService.publish(new CommanderResetEvent());
        reset(watchedFolder);
    }

    private void reset(final File watchedFolder) {
        APPLICATION_STATE.resetEngineerStates();
        StorageService.resetShipLockerCounts();
        StorageService.resetBackPackCounts();
        StorageService.resetFleetCarrierCounts();
        StorageService.resetSrvCounts();
        StorageService.resetHorizonsMaterialCounts();
        StorageService.resetHorizonsCommodityCounts();
        if (this.fleetCarrierWatcher != null) {
            this.fleetCarrierWatcher.stop();
        }
        this.timeStampedCargoWatcher.stop();
        this.timeStampedShipLockerWatcher.stop();
        this.timeStampedBackPackWatcher.stop();
        this.journalWatcher.stop();
        this.deeplinkWatcher.stop();
        this.statusWatcher.stop();

        this.shipyardWatcher.stop();
        this.outfittingWatcher.stop();
        this.marketWatcher.stop();
        this.navrouteWatcher.stop();
        this.modulesinfoWatcher.stop();
        this.fcMaterialsWatcher.stop();
        setupDeeplinkWatcher();
        setupStorageWatchers(watchedFolder);
    }

    private void setPreferenceIfNotMaximized(final Stage primaryStage, final String setting, final Double value) {
        // x y are processed before maximized, so excluding setting it if it's -8
        if (!primaryStage.isMaximized() && !Double.valueOf(-8.0D).equals(value)) {
            PreferencesService.setPreference(setting, value);
        }
    }

    private void saveTextToFile(final String content, final File file) {
        try {
            final PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (final IOException ex) {
            log.error("Failed to write to file");
        }
    }

    private void saveXlsToFile(final XSSFWorkbook workbook, final File file) {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
        } catch (final IOException ex) {
            log.error("Failed to write to file");
        }
    }

    static void launchFx(final String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        LocaleService.setCurrentLocale(LocaleService.getCurrentLocale());

        launch(args);
    }
}
