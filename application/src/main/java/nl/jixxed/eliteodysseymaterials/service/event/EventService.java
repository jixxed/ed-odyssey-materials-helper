package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class EventService {
    private static final List<EventListener<? extends Event>> LISTENERS = new ArrayList<>();

    public static <T extends Event> void publish(final T event) {
        LISTENERS.stream()
                .filter(eventListener -> eventListener.getEventClass().equals(event.getClass()))
                .sorted(Comparator.comparingInt(EventListener::getPriority))
                .forEach(eventListener -> ((EventListener<T>) eventListener).handleEvent(event));
    }

    public static <T extends Event> EventListener<T> addListener(final Object owner, final Class<T> eventClass, final Consumer<T> consumer) {
        return addListener(owner, 5, eventClass, consumer);
    }

    public static <T extends Event> EventListener<T> addListener(final Object owner, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final NonStaticEventListener<T> listener = new NonStaticEventListener<>(owner, priority, eventClass, consumer);
        log.debug("register listener: " + listener.getEventClass() + ", " + listener.hashCode());
        LISTENERS.add(listener);
        log.debug("listener size: " + LISTENERS.size());
        return listener;
    }

    public static <T extends Event> EventListener<T> addStaticListener(final Class<T> eventClass, final Consumer<T> consumer) {
        return addStaticListener(5, eventClass, consumer);
    }

    private static <T extends Event> EventListener<T> addStaticListener(final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final EventListener<T> listener = new EventListener<>(priority, eventClass, consumer);
        log.debug("register static listener: " + listener.getEventClass() + ", " + listener.hashCode());
        LISTENERS.add(listener);
        log.debug("listener size: " + LISTENERS.size());
        return listener;
    }

    public static void removeListener(final Consumer<? extends Event> eventConsumer) {
        LISTENERS.removeIf(listener -> listener.getConsumer().equals(eventConsumer));
    }

    public static void removeListener(final EventListener<? extends Event> eventListener) {
        log.debug("deregister listener: " + eventListener.getEventClass() + ", " + eventListener.hashCode());
        LISTENERS.remove(eventListener);
        log.debug("listener size: " + LISTENERS.size());
    }
}
