package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Iterator;
@Slf4j
public class FleetCarrierAssetParser {
    public void parse(final Iterator<JsonNode> components, final StoragePool storagePool) {
        components.forEachRemaining(componentNode ->
        {
            final String name = componentNode.get(getNameField()).asText();
            final Asset asset = Asset.forName(name);
            final int amount = componentNode.get(getAmountField()).asInt();
            if (asset.isUnknown()) {
                log.warn("Unknown Asset detected: " + componentNode.toPrettyString());
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", name + "\nPlease report!");
            } else {
                StorageService.addMaterial(asset, storagePool, amount);
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
