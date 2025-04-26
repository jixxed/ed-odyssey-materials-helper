package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;

public interface DestroyableSkinBase extends DestroyableParent {
//    ObservableListOverride override = new ObservableListOverride(DestroyableParent.this, super.getChildren());

    default ObservableListOverride getNodes() {
        return new ObservableListOverride(DestroyableSkinBase.this, DestroyableSkinBase.this.getChildren());
    }

    ObservableList<Node> getChildren();
//    ObservableListOverride getNodes();

    @Override
    default void destroy() {

        //additional optional cleanup if required
        DestroyableParent.super.destroy();
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
