package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Iterator;
@Slf4j
public class FleetCarrierDataParser {
    public void parse(final Iterator<JsonNode> datas, final StoragePool storagePool) {
        datas.forEachRemaining(dataNode ->
        {
            final String name = dataNode.get(getNameField()).asText();
            final Data data = Data.forName(name);
            final int amount = dataNode.get(getAmountField()).asInt();
            if (data.isUnknown()) {
                log.warn("Unknown Data detected: " + dataNode.toPrettyString());
                //NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", name + "\nPlease report!");
            } else {
                StorageService.addMaterial(data, storagePool, amount);
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
