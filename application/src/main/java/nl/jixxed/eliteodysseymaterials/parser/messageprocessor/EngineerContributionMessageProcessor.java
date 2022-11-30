package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
@Slf4j
public class EngineerContributionMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        if (journalMessage.has("Material")) {
            try {
                final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(journalMessage.get("Material").asText());
                if (!horizonsMaterial.isUnknown()) {
                    StorageService.removeMaterial(horizonsMaterial, journalMessage.get("Quantity").asInt());
                }
                EventService.publish(new StorageEvent(StoragePool.SHIP));
            } catch (final IllegalArgumentException e) {
                log.error(e.getMessage());
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", journalMessage.get("Material").asText() + "\nPlease report!");
            }
        }
    }
}