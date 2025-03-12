package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.*;

public interface Destroyable {
    default Map<ObservableValue<Object>, List<ChangeListener<Object>>> getListeners(){
        return DestroyableManager.getListeners(this);
    }

    default List<Property<Object>> getBindings(){
        return DestroyableManager.getBindings(this);
    }

    default List<Destroyable> getDestroyables(){
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

    default void destroy() {
        //clean changeListeners
        getListeners().forEach((observableValue, changeListeners) -> changeListeners.forEach(observableValue::removeListener));
        getListeners().forEach((observableValue, changeListeners) -> changeListeners.clear());
        getListeners().clear();

        //clean bindings
        getBindings().forEach(Property::unbind);
        getBindings().clear();

        //clean destroyables
        getDestroyables().forEach(Destroyable::destroy);
        getDestroyables().clear();

        //additional optional cleanup if required
        destroyInternal();
    }

    @SuppressWarnings("unchecked")
    default <T> void addChangeListener(final ObservableValue<T> observableValue, final ChangeListener<T> listener) {
        final Map<ObservableValue<Object>, List<ChangeListener<Object>>> listenersMap = getListeners();
        final List<ChangeListener<Object>> listeners = listenersMap.getOrDefault(observableValue, new ArrayList<>());
        listeners.add((ChangeListener<Object>) listener);
        listenersMap.put((ObservableValue<Object>) observableValue, listeners);
        observableValue.addListener(listener);
    }

    @SuppressWarnings("unchecked")
    default <P> void addBinding(final Property<P> observableValue, final ObservableValue<P> binding) {
        getBindings().add((Property<Object>) observableValue);
        observableValue.bind(binding);
    }

    default void destroyInternal() {

    }

}
