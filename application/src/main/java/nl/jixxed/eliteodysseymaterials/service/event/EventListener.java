package nl.jixxed.eliteodysseymaterials.service.event;

import java.util.function.Consumer;

public class EventListener<T extends Event> {
    private final Integer priority;
    private final Class<T> eventClass;
    private final Consumer<T> consumer;

    public EventListener(final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        this.priority = priority;
        this.eventClass = eventClass;
        this.consumer = consumer;
    }

    public EventListener(final Class<T> eventClass, final Consumer<T> consumer) {
        this(5, eventClass, consumer);
    }

    public Class<T> getEventClass() {
        return this.eventClass;
    }

    public Consumer<T> getConsumer() {
        return this.consumer;
    }

    public Integer getPriority() {
        return this.priority;
    }
}
