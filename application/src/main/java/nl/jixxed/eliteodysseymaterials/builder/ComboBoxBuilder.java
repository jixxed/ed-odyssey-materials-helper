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

import javafx.util.StringConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ComboBoxBuilder<T> extends AbstractComboBoxBuilder<T, ComboBoxBuilder<T>> {
    private final Class<T> clazz;
    private StringConverter<T> stringConverter;

    public static <T> ComboBoxBuilder<T> builder(final Class<T> clazz) {
        return new ComboBoxBuilder<>(clazz);
    }

    public ComboBoxBuilder<T> withConverter(StringConverter<T> stringConverter) {

        this.stringConverter = stringConverter;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableComboBox<T> build() {
        final DestroyableComboBox<T> comboBox = new DestroyableComboBox<>();
        super.build(comboBox);
        if(stringConverter != null) {
            comboBox.converterProperty().set(stringConverter);
        }
        comboBox.addChangeListener(comboBox.itemsProperty(), (_,_,_) -> {
            comboBox.setVisibleRowCount(Math.min(comboBox.getItems().size(), 10));
        });
        return comboBox;
    }
}
