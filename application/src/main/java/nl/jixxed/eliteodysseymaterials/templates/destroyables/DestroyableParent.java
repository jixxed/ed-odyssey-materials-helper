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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface DestroyableParent extends DestroyableComponent {

    default ObservableListOverride getNodes() {
        return DestroyableManager.getObservableListOverride(this, () -> new ObservableListOverride(DestroyableParent.this, DestroyableParent.this.getChildren()));
    }

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

        this.getNodes().clear();
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

//    @Override
//    default void destroyInternal() {
//        this.getChildren().clear();
//        DestroyableComponent.super.destroyInternal();
//    }
}
