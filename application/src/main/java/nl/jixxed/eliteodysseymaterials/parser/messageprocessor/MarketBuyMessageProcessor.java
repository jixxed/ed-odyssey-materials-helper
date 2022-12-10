package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MarketBuy.MarketBuy;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

@Slf4j
public class MarketBuyMessageProcessor implements MessageProcessor<MarketBuy> {
    @Override
    public void process(final MarketBuy event) {
        try {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(event.getType());
            if (!horizonsMaterial.isUnknown()) {
                StorageService.addCommodity((Commodity) horizonsMaterial, StoragePool.SHIP, event.getCount().intValue());
                EventService.publish(new StorageEvent(StoragePool.SHIP));
            }
        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage());
            NotificationService.showWarning(NotificationType.ERROR, "Unknown Commodity Detected", event.getType() + "\nPlease report!");
        }
    }

    @Override
    public Class<MarketBuy> getMessageClass() {
        return MarketBuy.class;
    }
}
