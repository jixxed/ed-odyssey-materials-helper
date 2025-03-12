package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface DestroyableParent extends DestroyableComponent {

    default Map<ObservableList<Node>, List<ListChangeListener<? super Node>>> getListChangeListeners() {
        return DestroyableManager.getListListeners(this);
    }

    default Map<ObservableList<Node>, List<InvalidationListener>> getListInvalidationListeners() {
        return DestroyableManager.getListInvalidationListeners(this);
    }

    ObservableList<Node> getChildren();

    @Override
    default void destroy() {
        //clean changeListeners
        this.getListChangeListeners().forEach((observableList, changeListeners) -> changeListeners.forEach(observableList::removeListener));
        this.getListChangeListeners().forEach((observableList, changeListeners) -> changeListeners.clear());
        this.getListChangeListeners().clear();

        //additional optional cleanup if required
        DestroyableComponent.super.destroy();
    }

    default void addListListener(final ObservableList<Node> children, final ListChangeListener<? super Node> listener) {
        this.getListChangeListeners().merge(children, new ArrayList<>(Collections.singletonList(listener)), (existingListeners, newListeners) -> {
            existingListeners.addAll(newListeners);
            return existingListeners;
        });
    }

    default void removeListListener(ObservableList<Node> children, ListChangeListener<? super Node> listener) {
        this.getListChangeListeners().computeIfPresent(children, (key, value) -> {
            value.remove(listener);
            return value;
        });
    }

    default void addListListener(ObservableList<Node> children, InvalidationListener listener) {
        this.getListInvalidationListeners().merge(children, new ArrayList<>(Collections.singletonList(listener)), (existingListeners, newListeners) -> {
            existingListeners.addAll(newListeners);
            return existingListeners;
        });
    }

    default void removeListListener(ObservableList<Node> children, InvalidationListener listener) {
        this.getListInvalidationListeners().computeIfPresent(children, (key, value) -> {
            value.remove(listener);
            return value;
        });
    }
}
