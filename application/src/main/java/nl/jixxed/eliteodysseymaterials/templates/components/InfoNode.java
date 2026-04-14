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

import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public class InfoNode extends DestroyableVBox implements DestroyableTemplate {
    private EdAwesomeIcon[] icons;
    private StringBinding valueText;
    private StringBinding titleText;
    private boolean enabled;
    public InfoNode(StringBinding title, StringBinding text, boolean enabled, EdAwesomeIcon ... icons) {
        this.titleText = title;
        this.icons = icons;
        this.valueText = text;
        this.enabled = enabled;
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("info-node");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("unavailable"), !enabled);
        var title = LabelBuilder.builder()
                .withStyleClass("info-node-title")
                .withText(titleText)
                .build();
        var iconPane = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("info-node-icon")
                .withIcons(icons)
                .build();
        DestroyableStackPane iconAndTitle = StackPaneBuilder.builder()
                .withStyleClass("info-node-icon-title")
                .withNodes(title, iconPane)
                .build();
        title.addBinding(title.visibleProperty(), iconAndTitle.hoverProperty());
        iconPane.addBinding(iconPane.visibleProperty(), iconAndTitle.hoverProperty().not());
        var value = LabelBuilder.builder()
                .withStyleClass("info-node-value")
                .withText(valueText)
                .build();
        this.getNodes().addAll(iconAndTitle, new GrowingRegion(), value);
    }
}
