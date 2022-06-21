package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class EngineerContributionMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        if (journalMessage.has("Material")) {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(journalMessage.get("Material").asText());
            if (!horizonsMaterial.isUnknown()) {
                StorageService.removeMaterial(horizonsMaterial, journalMessage.get("Quantity").asInt());
            }
            EventService.publish(new StorageEvent(StoragePool.SHIP));
        }
    }
}