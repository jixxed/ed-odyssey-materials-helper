package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;

import java.util.Iterator;
import java.util.Map;
@Slf4j
public class FleetCarrierGoodParser  {
    public void parse(final Iterator<JsonNode> items, final StoragePool storagePool, final Map<? extends OdysseyMaterial, Storage> knownMap) {
        items.forEachRemaining(itemNode ->
        {
            final String name = itemNode.get(getNameField()).asText();
            final Good good = Good.forName(name);
            final int amount = itemNode.get(getAmountField()).asInt();
            if (good.isUnknown()) {
                log.warn("Unknown Good detected: " + itemNode.toPrettyString());
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", name + "\nPlease report!");
            } else {
                final Storage storage = knownMap.get(good);
                //stack values as items occur multiple times in the json
                storage.setValue(Math.max(0,storage.getValue(storagePool) + amount), storagePool);
            }
        });
    }
    private String getAmountField() {
        return "quantity";
    }

    private String getNameField() {
        return "name";
    }
}
