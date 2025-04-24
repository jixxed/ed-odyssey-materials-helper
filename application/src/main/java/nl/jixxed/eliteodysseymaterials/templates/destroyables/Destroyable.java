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
