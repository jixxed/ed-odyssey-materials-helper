package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
@Slf4j
public class MaterialDiscardedMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        try {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(journalMessage.get("Name").asText());
            if (!horizonsMaterial.isUnknown()) {
                if (horizonsMaterial instanceof Commodity commodity && !horizonsMaterial.isUnknown()) {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, journalMessage.get("Count").asInt());
                }
                if (!horizonsMaterial.isUnknown()) {
                    StorageService.removeMaterial(horizonsMaterial, journalMessage.get("Count").asInt());
                }
                EventService.publish(new StorageEvent(StoragePool.SHIP));
            }
        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }
}