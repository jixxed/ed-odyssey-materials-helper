package nl.jixxed.eliteodysseymaterials;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.helper.FileHelper;
import nl.jixxed.eliteodysseymaterials.parser.FileProcessor;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.exception.LoadoutDeeplinkException;
import nl.jixxed.eliteodysseymaterials.service.exception.WishlistDeeplinkException;
import nl.jixxed.eliteodysseymaterials.templates.ApplicationLayout;
import nl.jixxed.eliteodysseymaterials.templates.StartDialog;
import nl.jixxed.eliteodysseymaterials.templates.URLSchemeDialog;
import nl.jixxed.eliteodysseymaterials.trade.MarketPlaceClient;
import nl.jixxed.eliteodysseymaterials.watchdog.DeeplinkWatcher;
import nl.jixxed.eliteodysseymaterials.watchdog.JournalWatcher;
import nl.jixxed.eliteodysseymaterials.watchdog.TimeStampedGameStateWatcher;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class FXApplication extends Application {

    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MAIN_STYLESHEET = "/nl/jixxed/eliteodysseymaterials/style/style.css";

    private ApplicationLayout applicationLayout;
    private TimeStampedGameStateWatcher timeStampedShipLockerWatcher;
    private TimeStampedGameStateWatcher timeStampedBackPackWatcher;
    private final JournalWatcher journalWatcher = new JournalWatcher();
    private final DeeplinkWatcher deeplinkWatcher = new DeeplinkWatcher();
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    @Override
    public void start(final Stage primaryStage) {
        try {
            PreferencesService.setPreference(PreferenceConstants.APP_SETTINGS_VERSION, System.getProperty("app.version"));
            whatsnewPopup();
            urlSchemePopup();
            MaterialTrackingService.initialize();
            this.applicationLayout = new ApplicationLayout(this);
            this.primaryStage = primaryStage;
            primaryStage.setTitle(AppConstants.APP_TITLE);
            primaryStage.getIcons().add(new Image(FXApplication.class.getResourceAsStream(AppConstants.APP_ICON_PATH)));
            setupWatchers();

            initEventHandling();
            setupDeeplinkWatcher();

            final Scene scene = createApplicationScene();
            setupStyling(scene);
            primaryStage.setScene(scene);
            primaryStage.show();

            EventService.publish(new ApplicationLifeCycleEvent());


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
        EventService.addListener(this, WatchedFolderChangedEvent.class, event -> reset(new File(event.getPath())));
        EventService.addListener(this, CommanderSelectedEvent.class, event -> reset(this.journalWatcher.getWatchedFolder()));
        EventService.addListener(this, FontSizeEvent.class, fontSizeEvent -> {
            this.applicationLayout.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
            EventService.publish(new AfterFontSizeSetEvent(fontSizeEvent.getFontSize()));
        });
        this.primaryStage.setOnCloseRequest(event -> {
            try {
                if (MarketPlaceClient.getInstance() != null) {
                    MarketPlaceClient.getInstance().close();
                }
                MaterialTrackingService.close();
                this.timeStampedShipLockerWatcher.stop();
                this.timeStampedBackPackWatcher.stop();
                this.journalWatcher.stop();
                this.deeplinkWatcher.stop();
            } catch (final Exception ex) {
                //don't care
            }
        });
        EventService.addListener(this, SaveWishlistEvent.class, event -> {
            final FileChooser fileChooser = new FileChooser();
            //Set extension filter for text files
            final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            //Show save file dialog
            final File file = fileChooser.showSaveDialog(this.primaryStage);
            if (file != null) {
                saveTextToFile(event.getText(), file);
            }
        });
    }

    private void setupWatchers() {
        final File watchedFolder = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER));
        this.timeStampedShipLockerWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processShipLockerBackpack, AppConstants.SHIPLOCKER_FILE, StoragePool.SHIPLOCKER);
        this.timeStampedBackPackWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processShipLockerBackpack, AppConstants.BACKPACK_FILE, StoragePool.BACKPACK);
        this.journalWatcher.watch(watchedFolder, FileProcessor::processJournal, FileProcessor::resetAndProcessJournal);
    }

    private void setupDeeplinkWatcher() {
        final AtomicReference<EventListener<CommanderAllListedEvent>> reference = new AtomicReference<>();
        reference.set(EventService.addListener(this, CommanderAllListedEvent.class, event -> {

            final File deeplinkWatchedFolder = new File(OsConstants.DEEPLINK_FOLDER);
            this.deeplinkWatcher.watch(deeplinkWatchedFolder, deeplink -> {
                if (!deeplink.isEmpty()) {
                    try {
                        final ImportResult importResult = ImportService.importDeeplink(deeplink);
                        EventService.publish(new ImportResultEvent(importResult));
                        handleImportResult(importResult);
                    } catch (final LoadoutDeeplinkException ex) {
                        EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_LOADOUT)));
                        NotificationService.showError("Failed to import loadout", ex.getMessage());
                    } catch (final WishlistDeeplinkException ex) {
                        EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_WISHLIST)));
                        NotificationService.showError("Failed to import wishlist", ex.getMessage());
                    } catch (final RuntimeException ex) {
                        NotificationService.showError("Failed to import", ex.getMessage());
                        EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.OTHER_ERROR)));
                    }
                }
            }, AppConstants.DEEPLINK_FILE);
            EventService.removeListener(reference.get());

        }));

    }

    private void handleImportResult(final ImportResult importResult) {
        if (ImportResult.ResultType.SUCCESS_LOADOUT.equals(importResult.getResultType())) {
            NotificationService.showInformation("Imported loadout", importResult.getMessage());
            this.primaryStage.toFront();
        } else if (ImportResult.ResultType.SUCCESS_WISHLIST.equals(importResult.getResultType())) {
            NotificationService.showInformation("Imported wishlist", importResult.getMessage());
            this.primaryStage.toFront();
        } else if (ImportResult.ResultType.UNKNOWN_TYPE.equals(importResult.getResultType())) {
            NotificationService.showError("Failed to import", "Unknown type");
        }
    }


    private Scene createApplicationScene() {
        final Scene scene = new Scene(this.applicationLayout, PreferencesService.getPreference(PreferenceConstants.APP_WIDTH, 800D), PreferencesService.getPreference(PreferenceConstants.APP_HEIGHT, 600D));

        scene.widthProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_WIDTH, Math.max((Double) newValue, 175.0D)));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_HEIGHT, Math.max((Double) newValue, 175.0D)));

        this.primaryStage.xProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_X, Math.max((Double) newValue, -8.0D)));
        this.primaryStage.yProperty().addListener((observable, oldValue, newValue) -> setPreferenceIfNotMaximized(this.primaryStage, PreferenceConstants.APP_Y, Math.max((Double) newValue, -8.0D)));
        this.primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.APP_MAXIMIZED, newValue));

        this.primaryStage.setX(PreferencesService.getPreference(PreferenceConstants.APP_X, 0D));
        this.primaryStage.setY(PreferencesService.getPreference(PreferenceConstants.APP_Y, 0D));
        this.primaryStage.setMaximized(PreferencesService.getPreference(PreferenceConstants.APP_MAXIMIZED, Boolean.FALSE));
        return scene;
    }

    private void setupStyling(final Scene scene) throws IOException {
        this.applicationLayout.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        final JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getResource(MAIN_STYLESHEET).toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/notificationpopup.css").toExternalForm());
        addCustomCss(scene);
    }

    @SuppressWarnings("java:S899")
    private void addCustomCss(final Scene scene) throws IOException {
        final File customCss = new File(OsConstants.CUSTOM_CSS);
        if (OsConstants.OLD_CUSTOM_CSS != null) {
            final File oldCustomCss = new File(OsConstants.OLD_CUSTOM_CSS);
            if (!customCss.exists() && oldCustomCss.exists()) {
                customCss.createNewFile();
                FileHelper.copyFileContents(oldCustomCss, customCss);
            }
        }
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

    private void reset(final File watchedFolder) {
        APPLICATION_STATE.resetEngineerStates();
        StorageService.resetShipLockerCounts();
        StorageService.resetBackPackCounts();
        APPLICATION_STATE.resetCommanders();
        EventService.publish(new CommanderResetEvent());
        this.timeStampedShipLockerWatcher.stop();
        this.timeStampedBackPackWatcher.stop();
        this.journalWatcher.stop();
        this.deeplinkWatcher.stop();
        setupDeeplinkWatcher();
        this.timeStampedShipLockerWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processShipLockerBackpack, AppConstants.SHIPLOCKER_FILE, StoragePool.SHIPLOCKER);
        this.timeStampedBackPackWatcher = new TimeStampedGameStateWatcher(watchedFolder, FileProcessor::processShipLockerBackpack, AppConstants.BACKPACK_FILE, StoragePool.BACKPACK);
        this.journalWatcher.watch(watchedFolder, FileProcessor::processJournal, FileProcessor::resetAndProcessJournal);
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

    static void launchFx(final String[] args) {
        LocaleService.setCurrentLocale(LocaleService.getCurrentLocale());

        launch(args);
    }
}
