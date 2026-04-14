/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

@Slf4j
public class ModuleDetailsProperty extends DestroyableVBox implements DestroyableTemplate {
    private final String propertyNameKey;
    private final String valueKey;
    private final String valueText;
    private final int index;
    private final int size;

    public ModuleDetailsProperty(String propertyNameKey, String valueKey, String valueText, int index, int size) {
        this.propertyNameKey = propertyNameKey;
        this.valueKey = valueKey;
        this.valueText = valueText;
        this.index = index;
        this.size = size;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("property");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("first-row"), ModuleDetailsHelper.isFirstRow(index));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("last-row"), ModuleDetailsHelper.isLastRow(index, size));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("left-column"), ModuleDetailsHelper.isLeftColumn(index));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("right-column"), ModuleDetailsHelper.isRightColumn(index));
        var value = LabelBuilder.builder()
                .withStyleClass("value")
                .withText(valueKey, valueText)
                .build();
        value.setTextAlignment(TextAlignment.RIGHT);

        var name = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(propertyNameKey)
                .build();
        double keyWidth = computeTextWidth(name);
        double valueWidth = computeTextWidth(value) + ScalingHelper.getPixelDoubleFromEm(1D);
        double maxWidth = 21 * name.getFont().getSize();
        if (keyWidth + valueWidth <= maxWidth) {
            // They fit on the same line
            var line = BoxBuilder.builder().withNodes(name, new GrowingRegion(), value).buildHBox();
            this.getNodes().add(line);
        } else {
            // Key on first line
            var keyline = BoxBuilder.builder().withNodes(name).buildHBox();
            var valueline = BoxBuilder.builder().withNodes(new GrowingRegion(), value).buildHBox();
            this.getNodes().addAll(keyline, valueline);
        }
    }


    private double computeTextWidth(Label label) {
        Text tempText = new Text(label.getText());
        tempText.setFont(label.getFont());
        return tempText.getLayoutBounds().getWidth();
    }
}
