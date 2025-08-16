package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.IntegerRange;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialCollected.MaterialCollected;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class MaterialCollectedMessageProcessor implements MessageProcessor<MaterialCollected> {

    @Override
    public void process(final MaterialCollected event) {
        HorizonsMaterial horizonsMaterial = Raw.UNKNOWN;
        try {
            horizonsMaterial = HorizonsMaterial.subtypeForName(event.getName());
        } catch (final IllegalArgumentException e) {
            ReportService.reportMaterial(event);
        }
        if (!horizonsMaterial.isUnknown()) {
            if (horizonsMaterial instanceof Commodity commodity) {
                StorageService.addCommodity(commodity, StoragePool.SHIP, event.getCount().intValue());
            } else {
                StorageService.addMaterial(horizonsMaterial, event.getCount().intValue());
            }
            if (WishlistService.isMaterialOnWishlist(horizonsMaterial)) {
                final IntegerRange allWishlistsCount = WishlistService.getAllWishlistsCount(horizonsMaterial);
                NotificationService.showInformation(NotificationType.SHIP_WISHLIST_PICKUP, LocaleService.LocaleString.of("notification.collected.wishlist.material.title"),
                        LocaleService.LocaleString.of("notification.collected.wishlist.material.notification.horizons",
                                LocaleService.LocalizationKey.of(horizonsMaterial.getLocalizationKey()),
                                event.getCount().intValue(),
                                StorageService.getMaterialCount(horizonsMaterial),
                                allWishlistsCount.min(),
                                allWishlistsCount.max()));
            }
            EventService.publish(new StorageEvent(StoragePool.SHIP));
        }
    }

    @Override
    public Class<MaterialCollected> getMessageClass() {
        return MaterialCollected.class;
    }
}