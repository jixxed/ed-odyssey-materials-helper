package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MarketSell.MarketSell;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

@Slf4j
public class MarketSellMessageProcessor implements MessageProcessor<MarketSell> {
    @Override
    public void process(final MarketSell event) {
        try {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(event.getType());
            if (!horizonsMaterial.isUnknown()) {
                StorageService.removeCommodity((Commodity) horizonsMaterial, StoragePool.SHIP, event.getCount().intValue());
                EventService.publish(new StorageEvent(StoragePool.SHIP));
            }
        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage());
            //NotificationService.showWarning(NotificationType.ERROR, "Unknown Commodity Detected", event.getType() + "\nPlease report!");
        }
    }

    @Override
    public Class<MarketSell> getMessageClass() {
        return MarketSell.class;
    }
}
