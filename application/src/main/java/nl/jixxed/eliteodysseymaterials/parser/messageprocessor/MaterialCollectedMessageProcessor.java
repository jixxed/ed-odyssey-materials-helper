/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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