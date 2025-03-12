package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import nl.jixxed.eliteodysseymaterials.service.event.Event;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.util.List;

public interface DestroyableTemplate extends Template, Destroyable {
    default <T extends Event> EventListener<T> register(final EventListener<T> eventListener) {
        getEventListeners().add(eventListener);
        return eventListener;
    }

    default void destroyTemplate() {
        //deregister event listeners
        getEventListeners().forEach(EventService::removeListener);

        //clean up more
        if(this instanceof DestroyableComponent destroyableComponent){
            destroyableComponent.destroy();
        }else{
            Destroyable.super.destroy();
        }
    }

    @SuppressWarnings("java:S1452")
    default List<EventListener<? extends Event>> getEventListeners(){
        return DestroyableManager.getEventListeners(this);
    }

}
