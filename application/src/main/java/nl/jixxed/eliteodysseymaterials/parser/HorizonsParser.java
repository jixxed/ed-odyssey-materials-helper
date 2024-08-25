package nl.jixxed.eliteodysseymaterials.parser;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;

import java.util.Iterator;

public interface HorizonsParser<T extends Event> {

    default void parse(final Iterator<T> events) {
        events.forEachRemaining(event -> parse(event));
    }

     void parse(final T event);

}
