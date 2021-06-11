package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.ContainerTarget;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.models.Container;

import java.util.Iterator;
import java.util.Map;

public abstract class Parser {
    abstract void parse(final Iterator<JsonNode> items, final ContainerTarget containerTarget, Map<? extends Material, Container> knownMap, Map<String, Container> unknownMap);

    <T> Container getOrCreateContainer(final Map<T, Container> map, final T key) {
        if (!map.containsKey(key)) {
            map.put(key, new Container());
        }
        return map.get(key);
    }
}
