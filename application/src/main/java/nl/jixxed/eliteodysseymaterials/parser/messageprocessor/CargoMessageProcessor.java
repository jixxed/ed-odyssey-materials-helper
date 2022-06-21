package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.CargoEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class CargoMessageProcessor implements MessageProcessor {

    @Override
    @SuppressWarnings("java:S1192")
    public void process(final JsonNode journalMessage) {
        if (journalMessage.get("Inventory") == null) {
            EventService.publish(new CargoEvent(journalMessage.get("timestamp").asText()));
            return;
        }
        if ("Ship".equals(journalMessage.get("Vessel").asText())) {
            StorageService.resetHorizonsCommodityCounts();
            journalMessage.get("Inventory").elements().forEachRemaining(jsonNode -> {
                final Commodity commodity = Commodity.forName(jsonNode.get("Name").asText());
                StorageService.addCommodity(commodity, StoragePool.SHIP, jsonNode.get("Count").asInt());
            });
            EventService.publish(new StorageEvent(StoragePool.SHIP));
        }

    }
}