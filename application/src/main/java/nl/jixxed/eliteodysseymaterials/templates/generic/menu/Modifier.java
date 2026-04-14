/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.generic.menu;

import javafx.css.PseudoClass;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class Modifier extends DestroyableHBox implements DestroyableTemplate {

    private final String modifierKey;
    private final String value;
    private final boolean isPositive;

    public Modifier(String modifierKey, String value, boolean isPositive) {
        this.modifierKey = modifierKey;
        this.value = value;
        this.isPositive = isPositive;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("modifier");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("positive"), isPositive);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("negative"), !isPositive);
        final DestroyableLabel modifierLabel = LabelBuilder.builder()
                .withStyleClasses("name")
                .withText(modifierKey)
                .build();
        final DestroyableLabel valueLabel = LabelBuilder.builder()
                .withStyleClasses("value")
                .withNonLocalizedText(value)
                .build();
        HBox.setHgrow(valueLabel, Priority.ALWAYS);

        this.getNodes().addAll(modifierLabel, valueLabel);
    }
}
