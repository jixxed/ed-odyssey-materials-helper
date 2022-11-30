package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class MaterialCollectedMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        try {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(journalMessage.get("Name").asText());
            if (!horizonsMaterial.isUnknown()) {
                if (horizonsMaterial instanceof Commodity commodity) {
                    StorageService.addCommodity(commodity, StoragePool.SHIP, journalMessage.get("Count").asInt());
                } else {
                    StorageService.addMaterial(horizonsMaterial, journalMessage.get("Count").asInt());
                }
                if (WishlistService.isMaterialOnWishlist(horizonsMaterial)) {
                    NotificationService.showInformation(NotificationType.WISHLIST_PICKUP, LocaleService.getLocalizedStringForCurrentLocale("notification.collected.wishlist.material.title"),
                            LocaleService.getLocalizedStringForCurrentLocale("notification.collected.wishlist.material.notification.horizons",
                                    LocaleService.LocalizationKey.of(horizonsMaterial.getLocalizationKey()),
                                    journalMessage.get("Count").asInt(),
                                    StorageService.getMaterialCount(horizonsMaterial),
                                    WishlistService.getAllWishlistsCount(horizonsMaterial)));
                }

                EventService.publish(new StorageEvent(StoragePool.SHIP));
            }
        } catch (final IllegalArgumentException e) {
            final String name = journalMessage.get("Name").asText();
            final String category = journalMessage.get("Category").asText();
            NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Collected", category + " - " + name + "\nPlease report!");
            ReportService.reportMaterial(journalMessage);
        }
    }
}