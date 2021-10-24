package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventService {
    private static final List<EventListener<? extends Event>> LISTENERS = new ArrayList<>();

    public static <T extends Event> void publish(final T event) {
        LISTENERS.stream()
                .filter(eventListener -> eventListener.getEventClass().equals(event.getClass()))
                .sorted(Comparator.comparingInt(EventListener::getPriority))
                .forEach(eventListener -> ((EventListener<T>) eventListener).getConsumer().accept(event));
    }

    public static <T extends Event> EventListener<T> addListener(final Class<T> eventClass, final Consumer<T> consumer) {
        final EventListener<T> listener = new EventListener<>(eventClass, consumer);
        LISTENERS.add(listener);
        return listener;
    }

    public static <T extends Event> EventListener<T> addListener(final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final EventListener<T> listener = new EventListener<>(priority, eventClass, consumer);
        LISTENERS.add(listener);
        return listener;
    }

    public static void removeListener(final Consumer<? extends Event> eventConsumer) {
        LISTENERS.removeIf(listener -> listener.getConsumer().equals(eventConsumer));
    }
}
