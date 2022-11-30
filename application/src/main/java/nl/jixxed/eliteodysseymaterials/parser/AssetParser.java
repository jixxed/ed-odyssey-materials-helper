package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class AssetParser implements Parser {
    @Override
    public void parse(final Iterator<JsonNode> components, final StoragePool storagePool, final Map<? extends OdysseyMaterial, Storage> knownMap) {
        components.forEachRemaining(componentNode ->
        {
            final String name = componentNode.get(getNameField()).asText();
            final Asset asset = Asset.forName(name);
            final int amount = componentNode.get(getAmountField()).asInt();
            if (asset.isUnknown()) {
                log.warn("Unknown Asset detected: " + componentNode.toPrettyString());
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", name + "\nPlease report!");
            } else {
                final Storage storage = knownMap.get(asset);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            }
        });
    }

    protected String getAmountField() {
        return "Count";
    }

    protected String getNameField() {
        return "Name";
    }
}
