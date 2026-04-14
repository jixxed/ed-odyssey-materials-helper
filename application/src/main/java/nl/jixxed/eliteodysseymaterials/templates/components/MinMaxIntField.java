/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.property.IntegerProperty;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class MinMaxIntField extends DestroyableHBox implements DestroyableTemplate {
    private final IntField intField;

    public MinMaxIntField(final Integer min, final Integer max, final Integer initial) {
        this.intField = new IntField(min, max, initial);
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("min-max-int-field");
        DestroyableButton minus = ButtonBuilder.builder()
                .withNonLocalizedText("0")
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getMinValue());
                })
                .build();
        DestroyableButton plus = ButtonBuilder.builder()
                .withNonLocalizedText(UTF8Constants.INFINITY)
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getMaxValue());
                })
                .build();
        this.getNodes().addAll(minus, this.intField, plus);
    }


    public int getMaxValue() {
        return intField.getMaxValue();
    }

    public void setMaxValue(int maxFuel) {
        intField.setMaxValue(maxFuel);
    }

    public void setValue(int i) {
        intField.setValue(i);
    }

    public IntegerProperty valueProperty() {
        return intField.valueProperty();
    }
}
