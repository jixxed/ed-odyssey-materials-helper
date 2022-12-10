package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialCollected.MaterialCollected;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class MaterialCollectedMessageProcessor implements MessageProcessor<MaterialCollected> {
    @Override
    public void process(final MaterialCollected event) {
        try {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(event.getName());
            if (!horizonsMaterial.isUnknown()) {
                if (horizonsMaterial instanceof Commodity commodity) {
                    StorageService.addCommodity(commodity, StoragePool.SHIP, event.getCount().intValue());
                } else {
                    StorageService.addMaterial(horizonsMaterial, event.getCount().intValue());
                }
                if (WishlistService.isMaterialOnWishlist(horizonsMaterial)) {
                    NotificationService.showInformation(NotificationType.WISHLIST_PICKUP, LocaleService.getLocalizedStringForCurrentLocale("notification.collected.wishlist.material.title"),
                            LocaleService.getLocalizedStringForCurrentLocale("notification.collected.wishlist.material.notification.horizons",
                                    LocaleService.LocalizationKey.of(horizonsMaterial.getLocalizationKey()),
                                    event.getCount().intValue(),
                                    StorageService.getMaterialCount(horizonsMaterial),
                                    WishlistService.getAllWishlistsCount(horizonsMaterial)));
                }

                EventService.publish(new StorageEvent(StoragePool.SHIP));
            }
        } catch (final IllegalArgumentException e) {
            final String name = event.getName();
            final String category = event.getCategory();
            NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Collected", category + " - " + name + "\nPlease report!");
            ReportService.reportMaterial(event);
        }
    }

    @Override
    public Class<MaterialCollected> getMessageClass() {
        return MaterialCollected.class;
    }
}