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
