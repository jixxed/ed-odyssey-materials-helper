package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DestroyableComponent extends Destroyable, EventTarget {

    @SuppressWarnings("java:S1452")
    default Map<EventType<? extends Event>, List<EventHandler<? super Event>>> getEventHandlers() {
        return DestroyableManager.getEventHandlers(this);
    }

    @SuppressWarnings("java:S1452")
    default Map<EventType<? extends Event>, List<EventHandler<? super Event>>> getEventFilters() {
        return DestroyableManager.getEventFilters(this);
    }

    @Override
    default void destroy() {
        //clean eventHandlers
        getEventHandlers().forEach((eventType, eventHandlers) -> eventHandlers.forEach(eventHandler -> removeEventHandler(eventType, eventHandler)));
        getEventHandlers().clear();

        //clean eventFilters
        getEventFilters().forEach((eventType, eventFilters) -> eventFilters.forEach(eventFilter -> removeEventFilter(eventType, eventFilter)));
        getEventFilters().clear();

        //clean up more
        Destroyable.super.destroy();
    }

    @SuppressWarnings("unchecked")
    default <E extends Event> void registerEventHandler(EventType<E> eventType, EventHandler<? super E> handler) {
        final Map<EventType<? extends Event>, List<EventHandler<? super Event>>> handlersMap = getEventHandlers();
        final List<EventHandler<? super Event>> handlers = handlersMap.getOrDefault(eventType, new ArrayList<>());
        handlers.add((EventHandler<? super Event>) handler);
        handlersMap.put(eventType, handlers);
        addEventHandler(eventType, handler);
    }

    @SuppressWarnings("unchecked")
    default <E extends Event> void registerEventFilter(EventType<E> eventType, EventHandler<? super E> filter) {
        final Map<EventType<? extends Event>, List<EventHandler<? super Event>>> filtersMap = getEventFilters();
        final List<EventHandler<? super Event>> filters = filtersMap.getOrDefault(eventType, new ArrayList<>());
        filters.add((EventHandler<? super Event>) filter);
        filtersMap.put(eventType, filters);
        addEventFilter(eventType, filter);
    }
}
