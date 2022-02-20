package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class MaterialsMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        StorageService.resetHorizonsMaterialCounts();
        journalMessage.get("Raw").elements().forEachRemaining(jsonNode -> {
            final HorizonsMaterial horizonsMaterial = Raw.forName(jsonNode.get("Name").asText());
            StorageService.addMaterial(horizonsMaterial, jsonNode.get("Count").asInt());
        });
        journalMessage.get("Manufactured").elements().forEachRemaining(jsonNode -> {
            final HorizonsMaterial horizonsMaterial = Manufactured.forName(jsonNode.get("Name").asText());
            StorageService.addMaterial(horizonsMaterial, jsonNode.get("Count").asInt());
        });
        journalMessage.get("Encoded").elements().forEachRemaining(jsonNode -> {
            final HorizonsMaterial horizonsMaterial = Encoded.forName(jsonNode.get("Name").asText());
            StorageService.addMaterial(horizonsMaterial, jsonNode.get("Count").asInt());
        });
        EventService.publish(new StorageEvent(StoragePool.SHIP));
        
    }
}