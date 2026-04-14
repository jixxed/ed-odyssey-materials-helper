/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.components.slider;

import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class SliderPopupSkin implements Skin<SliderPopup>, Destroyable {

    SliderPopup skinnable;
    DestroyableLabel valueText;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    public SliderPopupSkin(PopupControl control) {
        skinnable = (SliderPopup) control;
        valueText = register(LabelBuilder.builder()
                .build());
        valueText.addBinding(valueText.textProperty(), skinnable.valueProperty().map(value -> String.format("%.2f", value.doubleValue())));
    }

    @Override
    public SliderPopup getSkinnable() {
        return skinnable;
    }

    @Override
    public Node getNode() {
        return valueText;
    }

    @Override
    public void dispose() {
        valueText.destroy();
        skinnable = null;
        this.destroy();

    }
}