package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.journalevents.Event;
public interface MessageProcessor<T extends Event> {
    void process(final T event);
    Class<T> getMessageClass();
}
