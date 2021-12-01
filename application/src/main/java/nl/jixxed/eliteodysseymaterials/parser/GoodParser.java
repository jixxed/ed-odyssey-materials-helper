package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class GoodParser implements Parser {
    @Override
    public void parse(final Iterator<JsonNode> items, final StoragePool storagePool, final Map<? extends Material, Storage> knownMap, final Map<String, Storage> unknownMap) {
        items.forEachRemaining(itemNode ->
        {
            final String name = itemNode.get("Name").asText();
            final Good good = Good.forName(name);
            final int amount = itemNode.get("Count").asInt();
            if (Good.UNKNOWN.equals(good)) {
                log.warn("Unknown Good detected: " + itemNode.toPrettyString());
                final String nameLocalised = itemNode.get("Name_Localised") != null ? itemNode.get("Name_Localised").asText() : name;
                final Storage storage = getOrCreateContainer(unknownMap, name + ":" + nameLocalised);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            } else {
                final Storage storage = knownMap.get(good);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            }
        });
    }
}
