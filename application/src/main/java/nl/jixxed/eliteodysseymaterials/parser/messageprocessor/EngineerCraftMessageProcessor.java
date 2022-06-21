package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.util.Iterator;

public class EngineerCraftMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        final Iterator<JsonNode> materials = journalMessage.get("Ingredients").elements();
        materials.forEachRemaining(jsonNode -> {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(jsonNode.get("Name").asText());
            if (horizonsMaterial instanceof Commodity commodity && !horizonsMaterial.isUnknown()) {
                StorageService.removeCommodity(commodity, StoragePool.SHIP, jsonNode.get("Count").asInt());
            }
            if (!horizonsMaterial.isUnknown()) {
                StorageService.removeMaterial(horizonsMaterial, jsonNode.get("Count").asInt());
            }
        });
        EventService.publish(new StorageEvent(StoragePool.SHIP));
    }
}