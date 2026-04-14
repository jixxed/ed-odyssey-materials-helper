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

import javafx.scene.control.ToggleGroup;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleButton;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToggleButtonBuilder extends AbstractButtonBaseBuilder<ToggleButtonBuilder> {

    private Boolean isSelected;
    private ToggleGroup toggleGroup;

    public static ToggleButtonBuilder builder() {
        return new ToggleButtonBuilder();
    }

    public ToggleButtonBuilder withSelected(boolean selected) {
        this.isSelected = selected;
        return this;
    }

    public ToggleButtonBuilder withToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableToggleButton build() {
        final DestroyableToggleButton button = new DestroyableToggleButton();
        super.build(button);
        if (this.isSelected != null) {
            button.setSelected(this.isSelected);
        }
        if (this.toggleGroup != null) {
            button.setToggleGroup(this.toggleGroup);
        }
        return button;
    }
}
