package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.util.Iterator;

@Slf4j
public class MissionCompletedMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        if (journalMessage.has("MaterialsReward")) {
            final Iterator<JsonNode> materials = journalMessage.get("MaterialsReward").elements();
            materials.forEachRemaining(jsonNode -> {
                final String name = jsonNode.get("Name").asText();
                try {
                    final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(name);
                    if (horizonsMaterial instanceof Commodity commodity && !horizonsMaterial.isUnknown()) {
                        StorageService.addCommodity(commodity, StoragePool.SHIP, jsonNode.get("Count").asInt());
                    }
                    if (!horizonsMaterial.isUnknown()) {
                        StorageService.addMaterial(horizonsMaterial, jsonNode.get("Count").asInt());
                    }
                    EventService.publish(new StorageEvent(StoragePool.SHIP));
                } catch (final IllegalArgumentException ex) {
                    //not a horizons material reward
                    log.warn("Material was not a Horizons material: " + name);
                }
                //disabled because shiplocker event precedes this event
//                try {
//                    final OdysseyMaterial odysseyMaterial = OdysseyMaterial.subtypeForName(name);
//                    if (!odysseyMaterial.isUnknown()) {
//                        final Storage materialStorage = StorageService.getMaterialStorage(odysseyMaterial);
//                        materialStorage.setValue(materialStorage.getShipLockerValue() + jsonNode.get("Count").asInt(),StoragePool.SHIPLOCKER);
//                    }
//                    EventService.publish(new StorageEvent(StoragePool.SHIPLOCKER));
//                } catch (final IllegalArgumentException ex) {
//                    //not a horizons material reward
//                    log.warn("Material was not an Odyssey material: " + name);
//                }
            });
        }
    }
}