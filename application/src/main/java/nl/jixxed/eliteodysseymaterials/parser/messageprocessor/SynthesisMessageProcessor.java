package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.util.Iterator;

@Slf4j
public class SynthesisMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        final Iterator<JsonNode> materials = journalMessage.get("Materials").elements();
        materials.forEachRemaining(jsonNode -> {
            try {
                final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(jsonNode.get("Name").asText());
                if (horizonsMaterial instanceof Commodity commodity && !horizonsMaterial.isUnknown()) {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, jsonNode.get("Count").asInt());
                }
                if (!horizonsMaterial.isUnknown()) {
                    StorageService.removeMaterial(horizonsMaterial, jsonNode.get("Count").asInt());
                }

            } catch (final IllegalArgumentException e) {
                log.error(e.getMessage());
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", journalMessage.get("Name").asText() + "\nPlease report!");
            }
        });
        EventService.publish(new StorageEvent(StoragePool.SHIP));
    }
}