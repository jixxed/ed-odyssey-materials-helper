package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@AllArgsConstructor
@Getter
class EventListener<T extends Event> {
    private final Integer priority;
    private final Class<T> eventClass;
    private final Consumer<T> consumer;

    EventListener(final Class<T> eventClass, final Consumer<T> consumer) {
        this(5, eventClass, consumer);
    }
}
