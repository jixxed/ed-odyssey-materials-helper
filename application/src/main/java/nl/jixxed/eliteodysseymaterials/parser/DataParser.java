package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class DataParser implements Parser {
    @Override
    public void parse(final Iterator<JsonNode> datas, final StoragePool storagePool, final Map<? extends OdysseyMaterial, Storage> knownMap) {
        datas.forEachRemaining(dataNode ->
        {
            final String name = dataNode.get(getNameField()).asText();
            final Data data = Data.forName(name);
            final int amount = dataNode.get(getAmountField()).asInt();
            if (data.isUnknown()) {
                log.warn("Unknown Data detected: " + dataNode.toPrettyString());
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", name + "\nPlease report!");
            } else {
                final Storage storage = knownMap.get(data);
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
