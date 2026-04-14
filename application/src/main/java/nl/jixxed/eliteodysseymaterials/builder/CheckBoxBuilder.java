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

import javafx.beans.value.ChangeListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableCheckBox;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckBoxBuilder extends AbstractButtonBaseBuilder<CheckBoxBuilder> {
    private ChangeListener<Boolean> changeListener;
    private boolean selected = false;

    public static CheckBoxBuilder builder() {
        return new CheckBoxBuilder();
    }


    public CheckBoxBuilder withSelected(final boolean selected) {
        this.selected = selected;
        return this;
    }

    public CheckBoxBuilder withSelectedProperty(final ChangeListener<Boolean> changeListener) {
        this.changeListener = changeListener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DestroyableCheckBox build() {
        DestroyableCheckBox checkBox = new DestroyableCheckBox();
        super.build(checkBox);

        checkBox.setSelected(this.selected);

        if (this.changeListener != null) {
            checkBox.addChangeListener(checkBox.selectedProperty(), this.changeListener);
        }

        return checkBox;
    }
}
