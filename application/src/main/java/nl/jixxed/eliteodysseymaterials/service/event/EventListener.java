package nl.jixxed.eliteodysseymaterials.service.event;

import java.util.function.Consumer;

public class EventListener<T extends Event> {
    Class<T> eventClass;
    Consumer<T> consumer;

    public EventListener(final Class<T> eventClass, final Consumer<T> consumer) {
        this.eventClass = eventClass;
        this.consumer = consumer;
    }

    public Class<T> getEventClass() {
        return this.eventClass;
    }

    public Consumer<T> getConsumer() {
        return this.consumer;
    }
}
