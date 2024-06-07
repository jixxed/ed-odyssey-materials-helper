package nl.jixxed.eliteodysseymaterials.helper;

import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.ImportService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ImportResultEvent;
import nl.jixxed.eliteodysseymaterials.service.exception.*;

import java.util.function.Consumer;

public class DeeplinkHelper {
    @Setter
    private static FXApplication fxApplication;
    public static Consumer<String> deeplinkConsumer = deeplink -> {
        if (!deeplink.isEmpty()) {
            try {
                final ImportResult importResult = ImportService.importDeeplink(deeplink);
                EventService.publish(new ImportResultEvent(importResult));
                handleImportResult(importResult);
            } catch (final LoadoutDeeplinkException ex) {
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_LOADOUT)));
                NotificationService.showError(NotificationType.ERROR, "Failed to import loadout", ex.getMessage());
            } catch (final OdysseyWishlistDeeplinkException ex) {
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_ODYSSEY_WISHLIST)));
                NotificationService.showError(NotificationType.ERROR, "Failed to import Odyssey wishlist", ex.getMessage());
            } catch (final HorizonsWishlistDeeplinkException ex) {
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_HORIZONS_WISHLIST)));
                NotificationService.showError(NotificationType.ERROR, "Failed to import Horizons wishlist", ex.getMessage());
            } catch (final EdsyDeeplinkException ex) {
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_EDSY_WISHLIST)));
                NotificationService.showError(NotificationType.ERROR, "Failed to import EDSY wishlist", ex.getMessage());
            } catch (final ShipDeeplinkException ex) {
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_HORIZONS_SHIP)));
                NotificationService.showError(NotificationType.ERROR, "Failed to import ship", ex.getMessage());
            } catch (final CoriolisDeeplinkException ex) {
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.ERROR_CORIOLIS_WISHLIST)));
                NotificationService.showError(NotificationType.ERROR, "Failed to import Coriolis wishlist", ex.getMessage());
//                        NotificationService.showError(NotificationType.ERROR, "Failed to import Coriolis wishlist", ex.getMessage());
            } catch (final RuntimeException ex) {
                NotificationService.showError(NotificationType.ERROR, "Failed to import", ex.getMessage());
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.OTHER_ERROR)));
            }
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
                NotificationService.showError(NotificationType.ERROR, "Failed to import", ex.getMessage());
                EventService.publish(new ImportResultEvent(new ImportResult(ImportResult.ResultType.OTHER_ERROR)));
            }
        }
    };

    private static void handleImportResult(final ImportResult importResult) {
        if (ImportResult.ResultType.SUCCESS_LOADOUT.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, "Imported loadout", importResult.getMessage());
            fxApplication.getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST.equals(importResult.getResultType()) || ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, "Imported wishlist", importResult.getMessage());
            fxApplication.getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_EDSY_WISHLIST.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, "Imported EDSY wishlist", importResult.getMessage());
            fxApplication.getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.SUCCESS_HORIZONS_SHIP.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, "Imported Ship", importResult.getMessage());
            fxApplication.getPrimaryStage().toFront();
        } else if (ImportResult.ResultType.UNKNOWN_TYPE.equals(importResult.getResultType())) {
            NotificationService.showError(NotificationType.ERROR, "Failed to import", "Unknown type");
        } else if (ImportResult.ResultType.CAPI_OAUTH_TOKEN.equals(importResult.getResultType())) {
            fxApplication.getPrimaryStage().toFront();
        }else if (ImportResult.ResultType.SUCCESS_SLEF.equals(importResult.getResultType())) {
            NotificationService.showInformation(NotificationType.IMPORT, "Imported Ship (SLEF)", importResult.getMessage());
            fxApplication.getPrimaryStage().toFront();
        }else if (ImportResult.ResultType.ERROR_SLEF.equals(importResult.getResultType())) {
            NotificationService.showError(NotificationType.ERROR, "Failed to import ship (SLEF)", importResult.getMessage());
            fxApplication.getPrimaryStage().toFront();
        }
    }

}
