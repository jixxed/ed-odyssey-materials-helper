package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class DataParser extends Parser {
    @Override
    public void parse(final Iterator<JsonNode> datas, final StoragePool storagePool, final Map<? extends Material, Storage> knownMap, final Map<String, Storage> unknownMap) {
        datas.forEachRemaining(dataNode ->
        {
            final String name = dataNode.get("Name").asText();
            final Data data = Data.forName(name);
            final int amount = dataNode.get("Count").asInt();
            if (data.isUnknown()) {
                log.warn("Unknown Data detected: " + dataNode.toPrettyString());
                final String nameLocalised = dataNode.get("Name_Localised") != null ? dataNode.get("Name_Localised").asText() : name;
                final Storage storage = getOrCreateContainer(unknownMap, name + ":" + nameLocalised);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            } else {
                final Storage storage = knownMap.get(data);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            }
        });
    }
}
