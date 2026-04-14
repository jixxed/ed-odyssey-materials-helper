/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
