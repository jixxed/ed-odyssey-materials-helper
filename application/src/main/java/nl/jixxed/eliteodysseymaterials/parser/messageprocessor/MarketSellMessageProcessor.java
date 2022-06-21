package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class MarketSellMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(journalMessage.get("Type").asText());
        if (!horizonsMaterial.isUnknown()) {
            StorageService.removeCommodity((Commodity) horizonsMaterial, StoragePool.SHIP, journalMessage.get("Count").asInt());
            EventService.publish(new StorageEvent(StoragePool.SHIP));
        }
    }
}
