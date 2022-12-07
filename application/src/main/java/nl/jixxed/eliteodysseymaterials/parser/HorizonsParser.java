package nl.jixxed.eliteodysseymaterials.parser;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.journalevents.MaterialTrade.MaterialTrade;

import java.util.Iterator;
import java.util.Map;

public interface HorizonsParser {

    default void parse(final Iterator<MaterialTrade> events, final Map<HorizonsMaterial, Integer> storage) {
        events.forEachRemaining(event -> parse(event, storage));
    }

    void parse(final MaterialTrade event, Map<HorizonsMaterial, Integer> storage);
}
