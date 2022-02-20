package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S3740")
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
        logListener(owner, listener, "register");
        LISTENERS.add(listener);
        logListenerSize();
        return listener;
    }

    public static <T extends Event> EventListener<T> addStaticListener(final Class<T> eventClass, final Consumer<T> consumer) {
        return addStaticListener(5, eventClass, consumer);
    }

    private static <T extends Event> EventListener<T> addStaticListener(final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final EventListener<T> listener = new EventListener<>(priority, eventClass, consumer);
        logListener(null, listener, "register static");
        LISTENERS.add(listener);
        logListenerSize();
        return listener;
    }

    public static void removeListener(final Consumer<? extends Event> eventConsumer) {
        LISTENERS.removeIf(listener -> listener.getConsumer().equals(eventConsumer));
    }

    public static void removeListener(final EventListener<? extends Event> eventListener) {
        logListener(null, eventListener, "deregister");
        LISTENERS.remove(eventListener);
        logListenerSize();
    }

    public static void removeListener(final Object owner) {
        LISTENERS.removeIf(listener -> listener instanceof NonStaticEventListener nonStaticEventListener && nonStaticEventListener.hasOwner(owner));
    }

    private static <T extends Event> void logListener(final Object owner, final EventListener<T> listener, final String action) {
        log.debug(action + " listener: " + listener.getEventClass() + ", " + listener.hashCode() + (owner != null ? ", " + owner.getClass().getName() : ""));
    }

    private static void logListenerSize() {
        log.debug("listener size: " + LISTENERS.size());
    }
}
