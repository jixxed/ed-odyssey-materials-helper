package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class AssetParser extends Parser {
    @Override
    public void parse(final Iterator<JsonNode> components, final StoragePool storagePool, final Map<? extends Material, Storage> knownMap, final Map<String, Storage> unknownMap) {
        components.forEachRemaining(componentNode ->
        {
            final String name = componentNode.get("Name").asText();
            final Asset asset = Asset.forName(name);
            final int amount = componentNode.get("Count").asInt();
            if (Asset.UNKNOWN.equals(asset)) {
                log.warn("Unknown Component detected: " + componentNode.toPrettyString());
            } else {
                final Storage storage = knownMap.get(asset);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            }
        });
    }
}
