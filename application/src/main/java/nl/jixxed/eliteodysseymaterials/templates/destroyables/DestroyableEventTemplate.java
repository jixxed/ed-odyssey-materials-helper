package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.util.List;

public interface DestroyableEventTemplate extends DestroyableTemplate {
    default <T extends Event> EventListener<T> register(final EventListener<T> eventListener) {
        getEventListeners().add(eventListener);
        return eventListener;
    }

    @Override
    default void destroyTemplate() {
        //deregister event listeners
        getEventListeners().forEach(EventService::removeListener);
        getEventListeners().clear();
        DestroyableTemplate.super.destroyTemplate();

    }

    @SuppressWarnings("java:S1452")
    default List<EventListener<? extends Event>> getEventListeners() {
        return DestroyableManager.getEventListeners(this);
    }

    void initEventHandling();
}
