/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractComboBoxBuilder<T, V extends AbstractComboBoxBuilder<T, V>> extends AbstractControlBuilder<V> {
    private ObservableList<T> items;
    private ChangeListener<T> listener;
    private StringBinding promptTextBinding;
    private boolean localized = false;
    private T selected;

    public V asLocalized() {
        this.localized = true;
        return (V) this;
    }

    public V withItemsProperty(final ObservableList<T> items) {
        this.items = items;
        return (V) this;
    }

    public V withPromptTextProperty(final StringBinding promptTextBinding) {
        this.promptTextBinding = promptTextBinding;
        return (V) this;
    }

    public V withValueChangeListener(final ChangeListener<T> listener) {
        this.listener = listener;
        return (V) this;
    }

    public V withSelected(T selected) {
        this.selected = selected;
        return (V) this;

    }

    public <N extends ComboBox<T> & DestroyableComponent> N build(N comboBox) {
        super.build(comboBox);

        if (this.items != null) {
            comboBox.itemsProperty().set(new SimpleListProperty<>(this.items));
        }
        if (this.selected != null) {
            comboBox.getSelectionModel().select(this.selected);
        }
        if (this.listener != null) {
            comboBox.addChangeListener(comboBox.valueProperty(), this.listener);
        }
        if (this.localized) {
            //this sets the old value back after a locale change, instead of blanking it
            comboBox.addChangeListener(comboBox.valueProperty(), (_, oldValue, newValue) -> {
                if (newValue == null && oldValue != null) {
                    comboBox.selectionModelProperty().getValue().select((T) oldValue);
                }
            });
        }
        if (this.promptTextBinding != null) {
            comboBox.addBinding(comboBox.promptTextProperty(), this.promptTextBinding);
        }
        return comboBox;
    }
}