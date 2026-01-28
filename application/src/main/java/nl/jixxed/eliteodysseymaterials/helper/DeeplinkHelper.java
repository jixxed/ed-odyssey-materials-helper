package nl.jixxed.eliteodysseymaterials.helper;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.ImportType;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.StyleSheet;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ImportResultEvent;
import nl.jixxed.eliteodysseymaterials.service.exception.*;
import nl.jixxed.eliteodysseymaterials.templates.dialog.ImportDialog;
import nl.jixxed.eliteodysseymaterials.watchdog.FileEvent;
import nl.jixxed.eliteodysseymaterials.watchdog.FileListener;
import nl.jixxed.eliteodysseymaterials.watchdog.FileService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
@Slf4j
public class DeeplinkHelper {
    //    @Setter
//    private static FXApplication fxApplication;
    public static Consumer<String> deeplinkConsumer = deeplink -> {
        if (!deeplink.isEmpty()) {
            final String content = deeplink.substring(8);
            final String[] split = content.split("/\\?");
            final ImportType type = ImportType.forName(split[0]);
            final String data = split[1];
            String json = ImportService.convertBase64CompressedToJson(data);
            showImportDialog(type, json, () -> {
                try {
                    final ImportResult importResult = ImportService.importDeeplink(deeplink);
                    EventService.publish(new ImportResultEvent(importResult));
                    handleImportResult(importResult);
                } catch (final LoadoutDeeplinkException ex) {
                    EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_LOADOUT)));
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.loadout"), ex.getLocaleString());
                } catch (final OdysseyWishlistDeeplinkException ex) {
                    EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_ODYSSEY_WISHLIST)));
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.odyssey.wishlist"), ex.getLocaleString());
                } catch (final HorizonsWishlistDeeplinkException ex) {
                    EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_HORIZONS_WISHLIST)));
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.horizons.wishlist"), ex.getLocaleString());
                } catch (final EdsyDeeplinkException ex) {
                    EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_EDSY_WISHLIST)));
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.edsy"), ex.getLocaleString());
                } catch (final ShipDeeplinkException ex) {
                    EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_HORIZONS_SHIP)));
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.ship"), ex.getLocaleString());
                } catch (final CoriolisDeeplinkException ex) {
                    EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_CORIOLIS_WISHLIST)));
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.coriolis"), ex.getLocaleString());
//                        NotificationService.showError(NotificationType.ERROR, "Failed to import Coriolis wishlist", ex.getMessage());
                } catch (final RuntimeException ex) {
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.generic"), LocaleService.LocaleString.of("notification.imported.failed.generic.text", ex.getMessage()));
                    EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.OTHER_ERROR)));
                }
            });

        }
    };
    public static Consumer<String> slefConsumer = deeplink -> {
        if (!deeplink.isEmpty()) {
            try {
                final ImportResult[] importResult = ImportService.importSlef(deeplink);
                for (ImportResult ir : importResult) {
                    EventService.publish(new ImportResultEvent(ir));
                    handleImportResult(ir);
                }
            } catch (final RuntimeException ex) {
                NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.generic"), LocaleService.LocaleString.of("notification.imported.failed.generic.text", ex.getMessage()));
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.OTHER_ERROR)));
            }
        }
    };
    private static Disposable subscribe;

    private static void handleImportResult(final ImportResult importResult) {
        if (ImportResult.ResultType.SUCCESS_LOADOUT.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, LocaleService.LocaleString.of("notification.imported.success.loadout.title"), importResult.getMessage());
            FXApplication.getInstance().getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST.equals(importResult.getResultType()) || ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, LocaleService.LocaleString.of("notification.imported.success.wishlist.title"), importResult.getMessage());
            FXApplication.getInstance().getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_EDSY_WISHLIST.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, LocaleService.LocaleString.of("notification.imported.success.edsy.title"), importResult.getMessage());
            FXApplication.getInstance().getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_CORIOLIS_WISHLIST.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, LocaleService.LocaleString.of("notification.imported.success.coriolis.title"), importResult.getMessage());
            FXApplication.getInstance().getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_HORIZONS_SHIP.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, LocaleService.LocaleString.of("notification.imported.success.ship.title"), importResult.getMessage());
            FXApplication.getInstance().getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.UNKNOWN_TYPE.equals(importResult.getResultType())) {
            NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.failed.unknown.type.title"), LocaleService.LocaleString.of("notification.imported.failed.unknown.type.text"));
        } else if (ImportResult.ResultType.CAPI_OAUTH_TOKEN.equals(importResult.getResultType())) {
            FXApplication.getInstance().getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_SLEF.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, LocaleService.LocaleString.of("notification.imported.success.slef.title"), importResult.getMessage());
            FXApplication.getInstance().getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.ERROR_SLEF.equals(importResult.getResultType())) {
            NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.imported.error.slef.title"), importResult.getMessage());
            FXApplication.getInstance().getPrimaryStage().toFront();
        }
    }


    private static void showImportDialog(ImportType type, String json, Runnable onImportAccepted) {
        if(shouldSkipDialog(type)){
            onImportAccepted.run();
        }else{

            final Stage importStage = new Stage();
            addIconsToStage(importStage);
            final Scene importScene = new Scene(new ImportDialog(type, json, importStage, onImportAccepted), 640, 640);
            importStage.initModality(Modality.APPLICATION_MODAL);
            final JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(importScene);

            if (VersionService.isDev()) {//dev mode for hotswap css
                try {
                    final String currentWorkingDirectory = new File(".").getCanonicalPath();
                    final File mainCss = new File(currentWorkingDirectory + "/src/main/resources/css/compiled/main.css");
                    final File colorblindCss = new File(currentWorkingDirectory + "/src/main/resources/css/compiled/colorblind.css");

                    switch (StyleSheet.valueOf(PreferencesService.getPreference(PreferenceConstants.STYLESHEET, "DEFAULT"))) {
                        case DEFAULT -> importScene.getStylesheets().add(mainCss.toURI().toURL().toExternalForm());
                        case COLORBLIND -> importScene.getStylesheets().add(colorblindCss.toURI().toURL().toExternalForm());
                    }

                    AtomicReference<FileListener> fileListenerRef = new AtomicReference<>();
                    subscribe = Observable.<String>create(emitter -> {
                                fileListenerRef.set(new FileListener() {
                                    @Override
                                    public void onCreated(FileEvent event) {

                                    }

                                    @Override
                                    public void onModified(FileEvent event) {
                                        var pathToCss = "";
                                        try {
                                            pathToCss = event.getFile().toURI().toURL().toExternalForm();//compiledCss.toURI().toURL().toExternalForm();
                                        } catch (final IOException e) {
                                            log.error("Error loading modified css", e);
                                        }
                                        emitter.onNext(pathToCss);
                                    }

                                    @Override
                                    public void onDeleted(FileEvent event) {

                                    }
                                });
                            })
                            .debounce(100, TimeUnit.MILLISECONDS)
                            .observeOn(Schedulers.io())
                            .subscribe(cssFile -> Platform.runLater(() -> {
                                log.info("reloading stylesheet {}", cssFile);
                                try {
                                    importScene.getStylesheets().remove(mainCss.toURI().toURL().toExternalForm());
                                    importScene.getStylesheets().remove(colorblindCss.toURI().toURL().toExternalForm());
                                } catch (final IOException e) {
                                    log.error("Error loading modified css", e);
                                }
                                log.info("reloaded stylesheet {}", cssFile);
                                importScene.getStylesheets().add(cssFile);
                            }));
                    FileService.subscribe(currentWorkingDirectory + "/src/main/resources/css/compiled", false, fileListenerRef.get());
                } catch (final IOException e) {
                    log.error("Error loading stylesheet", e);
                }
            } else {
                importScene.getStylesheets().add(DeeplinkHelper.class.getResource(StyleSheet.valueOf(PreferencesService.getPreference(PreferenceConstants.STYLESHEET, "DEFAULT")).getStyleSheet()).toExternalForm());
            }
            importStage.setScene(importScene);
            importStage.titleProperty().set("Import");
            importStage.showAndWait();
        }
    }

    private static boolean shouldSkipDialog(ImportType type) {
        return ImportType.CAPI.equals(type) || ImportType.UNKNOWN.equals(type);
    }

    private static void addIconsToStage(Stage stage) {
        for (int res : new int[]{16, 32, 48, 64, 128, 256, 512}) {
            stage.getIcons().add(new Image(FXApplication.class.getResourceAsStream("/images/application/appicon" + res + ".png")));
        }
    }

    public static void destroy() {
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

}
