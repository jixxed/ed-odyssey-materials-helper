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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.*;

public interface Destroyable {
    default Map<ObservableValue<Object>, List<ChangeListener<Object>>> getListeners() {
        return DestroyableManager.getListeners(this);
    }

    default Set<Property<Object>> getBindings() {
        return DestroyableManager.getBindings(this);
    }

    default Set<Destroyable> getDestroyables() {
        return DestroyableManager.getDestroyables(this);
    }

    default <T extends Destroyable> T register(final T destroyable) {
        getDestroyables().add(destroyable);
        return destroyable;
    }

    default <T extends Destroyable> void registerAll(final T... destroyables) {
        getDestroyables().addAll(Arrays.stream(destroyables).toList());
    }

    default <T extends Destroyable> void registerAll(final Collection<T> destroyables) {
        getDestroyables().addAll(destroyables);
    }

    default <T extends Destroyable> void deregister(final T destroyable) {
        getDestroyables().remove(destroyable);
    }

    default <T extends Destroyable> void deregisterAll(final Collection<T> destroyables) {
        getDestroyables().removeAll(destroyables);
    }

    default void destroy() {
        //clean changeListeners
        getListeners().forEach((observableValue, changeListeners) -> changeListeners.forEach((observableValue)::removeListener));
        getListeners().forEach((observableValue, changeListeners) -> changeListeners.clear());
        getListeners().clear();

        //clean bindings
        getBindings().forEach(Property::unbind);
        getBindings().clear();

        //clean destroyables
        try {

            getDestroyables().forEach(destroyable -> {
                if (destroyable instanceof DestroyableEventTemplate template) {
                    template.destroyTemplate();
                } else if (destroyable instanceof DestroyableTemplate template) {
                    template.destroyTemplate();
                } else if (destroyable instanceof DestroyableParent p) {
                    p.destroy();
                } else if (destroyable instanceof DestroyableComponent c) {
                    c.destroy();
                } else if (destroyable instanceof Destroyable d) {
                    d.destroy();
                }
            });
        } catch (ConcurrentModificationException e) {
            System.err.println("ConcurrentModificationException while destroying destroyables");
            System.err.println(this.getClass().getName());
        }
        getDestroyables().clear();

        //additional optional cleanup if required
        destroyInternal();
        DestroyableManager.destroy(this);
    }

    @SuppressWarnings("unchecked")
    default <T> void addChangeListener(final ObservableValue<T> observableValue, final ChangeListener<T> listener) {
        final Map<ObservableValue<Object>, List<ChangeListener<Object>>> listenersMap = getListeners();
        final List<ChangeListener<Object>> listeners = listenersMap.computeIfAbsent((ObservableValue<Object>) observableValue, _ -> new ArrayList<>());
        listeners.add((ChangeListener<Object>) listener);
        listenersMap.put((ObservableValue<Object>) observableValue, listeners);
        observableValue.addListener(listener);
    }


    @SuppressWarnings("unchecked")
    default <P> void addBinding(final Property<P> observableValue, final ObservableValue<P> binding) {
        getBindings().add((Property<Object>) observableValue);
        observableValue.bind(binding);
    }

    @SuppressWarnings("unchecked")
    default <P extends Event> void addEventBinding(final ObjectProperty<EventHandler<? super P>> observableValue, final EventHandler<P> handler) {
        getBindings().add((Property<Object>) (Property<?>) observableValue);
        observableValue.set(handler);
    }

    @SuppressWarnings("unchecked")
    default <P extends Event> void addActionEventBinding(final ObjectProperty<EventHandler<P>> observableValue, final EventHandler<P> handler) {
        getBindings().add((Property<Object>) (Property<?>) observableValue);
        observableValue.set(handler);
    }

    default void destroyInternal() {

    }

}
