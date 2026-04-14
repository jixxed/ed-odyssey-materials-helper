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

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DestroyableManager {
    private static final Map<DestroyableParent, ObservableListOverride> OBSERVABLE_LIST_OVERRIDES = new ConcurrentHashMap<>();
    private static final Map<Destroyable, Map<ObservableValue<Object>, List<ChangeListener<Object>>>> CHANGE_LISTENERS = new ConcurrentHashMap<>();
    private static final Map<DestroyableParent, Map<ObservableList<Node>, List<ListChangeListener<? super Node>>>> LIST_CHANGE_LISTENERS = new ConcurrentHashMap<>();
    private static final Map<DestroyableParent, Map<ObservableList<Node>, List<InvalidationListener>>> LIST_INVALIDATION_LISTENERS = new ConcurrentHashMap<>();
    private static final Map<Destroyable, Set<Property<Object>>> BINDINGS = new ConcurrentHashMap<>();
    private static final Map<Destroyable, Map<EventType<? extends Event>, List<EventHandler<? super Event>>>> EVENT_HANDLERS = new ConcurrentHashMap<>();
    private static final Map<Destroyable, Map<EventType<? extends Event>, List<EventHandler<? super Event>>>> EVENT_FILTERS = new ConcurrentHashMap<>();

    private static final Map<Destroyable, Set<Destroyable>> DESTROYABLES = new ConcurrentHashMap<>();
    private static final Map<Destroyable, List<EventListener<? extends nl.jixxed.eliteodysseymaterials.service.event.Event>>> EVENT_LISTENERS = new ConcurrentHashMap<>();

    public static Set<Destroyable> getDestroyables(Destroyable d) {
        return DestroyableManager.DESTROYABLES.computeIfAbsent(d, _ -> new HashSet<>());
    }

    @SuppressWarnings("java:S1452")
    public static List<EventListener<? extends nl.jixxed.eliteodysseymaterials.service.event.Event>> getEventListeners(Destroyable d) {
        return DestroyableManager.EVENT_LISTENERS.computeIfAbsent(d, _ -> new ArrayList<>());
    }

    public static Map<ObservableValue<Object>, List<ChangeListener<Object>>> getListeners(Destroyable d) {
        return DestroyableManager.CHANGE_LISTENERS.computeIfAbsent(d, _ -> new HashMap<>());
    }

    @SuppressWarnings("java:S1452")
    public static Map<ObservableList<Node>, List<ListChangeListener<? super Node>>> getListListeners(DestroyableParent d) {
        return DestroyableManager.LIST_CHANGE_LISTENERS.computeIfAbsent(d, _ -> new HashMap<>());
    }

    public static Set<Property<Object>> getBindings(Destroyable d) {
        return DestroyableManager.BINDINGS.computeIfAbsent(d, _ -> new HashSet<>());
    }

    @SuppressWarnings("java:S1452")
    public static Map<EventType<? extends Event>, List<EventHandler<? super Event>>> getEventHandlers(Destroyable d) {
        return DestroyableManager.EVENT_HANDLERS.computeIfAbsent(d, _ -> new HashMap<>());
    }

    @SuppressWarnings("java:S1452")
    public static Map<EventType<? extends Event>, List<EventHandler<? super Event>>> getEventFilters(Destroyable d) {
        return DestroyableManager.EVENT_FILTERS.computeIfAbsent(d, _ -> new HashMap<>());
    }

    public static Map<ObservableList<Node>, List<InvalidationListener>> getListInvalidationListeners(DestroyableParent d) {
        return DestroyableManager.LIST_INVALIDATION_LISTENERS.computeIfAbsent(d, _ -> new HashMap<>());
    }

    public static void destroy(Destroyable d) {
        CHANGE_LISTENERS.remove(d);
        BINDINGS.remove(d);
        EVENT_HANDLERS.remove(d);
        EVENT_FILTERS.remove(d);
        DESTROYABLES.remove(d);
        EVENT_LISTENERS.remove(d);
        if (d instanceof DestroyableParent p) {
            LIST_CHANGE_LISTENERS.remove(p);
            LIST_INVALIDATION_LISTENERS.remove(p);
            OBSERVABLE_LIST_OVERRIDES.remove(d);
        }
    }

    public static ObservableListOverride getObservableListOverride(DestroyableParent d, Supplier<ObservableListOverride> o) {
        return DestroyableManager.OBSERVABLE_LIST_OVERRIDES.computeIfAbsent(d, _ -> o.get());
    }
}
