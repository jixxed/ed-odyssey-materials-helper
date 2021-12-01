package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Getter
@Slf4j
public class EventListener<T extends Event> {
    protected final Integer priority;
    protected final Class<T> eventClass;
    protected final Consumer<T> consumer;

    EventListener(final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        this.priority = priority;
        this.eventClass = eventClass;
        this.consumer = consumer;
    }

    void handleEvent(final T event) {
        this.consumer.accept(event);
    }

}
