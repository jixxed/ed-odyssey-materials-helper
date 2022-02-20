package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;

import java.util.Iterator;
import java.util.Map;

public interface HorizonsParser {

    default void parse(final Iterator<JsonNode> items, final Map<HorizonsMaterial, Integer> storage) {
        items.forEachRemaining(item -> parse(item, storage));
    }

    void parse(final JsonNode item, Map<HorizonsMaterial, Integer> storage);
}
