package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DestroyableComponent extends Destroyable {

    HashMap<ObservableValue, List<ChangeListener>> getListenersMap();

    @Override
    default void destroy() {
        getListenersMap().forEach((observableValue, changeListeners) -> changeListeners.forEach(observableValue::removeListener));
        getListenersMap().forEach((observableValue, changeListeners) -> changeListeners.clear());
        getListenersMap().clear();
        destroyInternal();
    }

    default void addChangeListener(final ObservableValue observableValue, final ChangeListener listener) {
        final HashMap<ObservableValue, List<ChangeListener>> listenersMap = getListenersMap();
        final List<ChangeListener> listeners = listenersMap.getOrDefault(observableValue, new ArrayList<>());
        listeners.add(listener);
        listenersMap.put(observableValue, listeners);
        observableValue.addListener(listener);
    }

}
