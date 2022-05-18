package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

import java.util.Iterator;
import java.util.Map;

public interface Parser {
    void parse(final Iterator<JsonNode> items, final StoragePool storagePool, Map<? extends OdysseyMaterial, Storage> knownMap);

    default <T> Storage getOrCreateContainer(final Map<T, Storage> map, final T key) {
        map.putIfAbsent(key, new Storage());
        return map.get(key);
    }
}
