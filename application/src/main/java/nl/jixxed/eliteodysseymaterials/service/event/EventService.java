package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S3740")
@Slf4j
public class EventService {
    private static final Map<Class<? extends Event>, List<EventListener<? extends Event>>> LISTENERS_MAP = new HashMap<>();

    public static <T extends Event> void publish(final T event) {
        LISTENERS_MAP.getOrDefault(event.getClass(), Collections.emptyList()).stream()
                .sorted(Comparator.comparingInt(EventListener::getPriority))
                .forEach(eventListener -> {
                    if (event instanceof TerminateApplicationEvent) {
                        //make sure we call each TerminateApplicationEvent listener and try to close all running threads
                        try {
                            ((EventListener<T>) eventListener).handleEvent(event);
                        } catch (final Exception ex) {
                            log.error(ex.getMessage(), ex);
                        }
                    } else {
                        ((EventListener<T>) eventListener).handleEvent(event);
                    }
                });
    }

    public static <T extends Event> EventListener<T> addListener(final Object owner, final Class<T> eventClass, final Consumer<T> consumer) {
        return addListener(owner, 5, eventClass, consumer);
    }

    public static <T extends Event> EventListener<T> addListener(final Object owner, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final NonStaticEventListener<T> listener = new NonStaticEventListener<>(owner, priority, eventClass, consumer);
        logListener(owner, listener, "register");
        final List<EventListener<? extends Event>> eventListeners = LISTENERS_MAP.getOrDefault(eventClass, new ArrayList<>());
        eventListeners.add(listener);
        LISTENERS_MAP.putIfAbsent(eventClass, eventListeners);
        logListenerSize();
        return listener;
    }

    public static <T extends Event> EventListener<T> addStaticListener(final Class<T> eventClass, final Consumer<T> consumer) {
        return addStaticListener(5, eventClass, consumer);
    }

    private static <T extends Event> EventListener<T> addStaticListener(final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final EventListener<T> listener = new EventListener<>(priority, eventClass, consumer);
        logListener(null, listener, "register static");
        final List<EventListener<? extends Event>> eventListeners = LISTENERS_MAP.getOrDefault(eventClass, new ArrayList<>());
        eventListeners.add(listener);
        LISTENERS_MAP.putIfAbsent(eventClass, eventListeners);
        logListenerSize();
        return listener;
    }

    public static void removeListener(final Consumer<? extends Event> eventConsumer, final Class<? extends Event> eventClass) {
        LISTENERS_MAP.computeIfPresent(eventClass, (aClass, eventListeners) -> {
            eventListeners.removeIf(listener -> {
                final boolean remove = listener.getConsumer().equals(eventConsumer);
                if (remove) {
                    logListener(null, listener, "deregister");
                }
                return remove;
            });
            return eventListeners;
        });
        logListenerSize();
    }

    public static void removeListener(final EventListener<? extends Event> eventListener) {
        logListener(null, eventListener, "deregister");
        LISTENERS_MAP.computeIfPresent(eventListener.getEventClass(), (aClass, eventListeners) -> {
            eventListeners.remove(eventListener);
            return eventListeners;
        });
        logListenerSize();
    }

    public static void removeListener(final Object owner) {
        LISTENERS_MAP.values().forEach(eventListeners -> eventListeners.removeIf(listener -> {
            final boolean remove = listener instanceof NonStaticEventListener nonStaticEventListener && nonStaticEventListener.hasOwner(owner);
            if (remove) {
                logListener(owner, listener, "deregister");
            }
            return remove;
        }));
        logListenerSize();
    }

    @SuppressWarnings({"java:S1172", "java:S125"})
    private static <T extends Event> void logListener(final Object owner, final EventListener<T> listener, final String action) {
        log.debug(action + " listener: " + listener.getEventClass() + ", HASH:" + listener.hashCode() + (owner != null ? ", OWNER:" + owner.getClass().getName() + "(" + owner.hashCode() + ")" : ""));
    }

    @SuppressWarnings("java:S125")
    private static void logListenerSize() {
        log.debug("listener size: " + (Integer) LISTENERS_MAP.values().stream().mapToInt(List::size).sum());
    }

    public static void shutdown() {
        LISTENERS_MAP.clear();
    }
}
