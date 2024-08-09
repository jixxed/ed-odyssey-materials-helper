package nl.jixxed.eliteodysseymaterials.parser;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;

import java.util.Iterator;
import java.util.Map;

public interface HorizonsParser<T extends Event> {

    default void parse(final Iterator<T> events, final Map<HorizonsMaterial, Integer> storage) {
        events.forEachRemaining(event -> parse(event, storage));
    }

     void parse(final T event, Map<HorizonsMaterial, Integer> storage);
}
