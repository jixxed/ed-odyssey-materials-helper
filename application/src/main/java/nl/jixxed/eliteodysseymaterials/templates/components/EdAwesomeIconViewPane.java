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

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;

import java.util.Arrays;

public class EdAwesomeIconViewPane extends DestroyableStackPane {
    public EdAwesomeIconViewPane(EdAwesomeIconView... edAwesomeIconViews) {
        this.getStyleClass().add("icon-container");
        this.getNodes().addAll(edAwesomeIconViews);
        Arrays.stream(edAwesomeIconViews).forEach(edAwesomeIconView -> StackPane.setAlignment(edAwesomeIconView, Pos.CENTER));
    }

    public void setIcons(EdAwesomeIcon... edAwesomeIcons) {
        this.getNodes().clear();
        EdAwesomeIconView[] edAwesomeIconViews = Arrays.stream(edAwesomeIcons).map(EdAwesomeIconView::new).toArray(EdAwesomeIconView[]::new);
        this.getNodes().addAll(edAwesomeIconViews);
        Arrays.stream(edAwesomeIconViews).forEach(edAwesomeIconView -> StackPane.setAlignment(edAwesomeIconView, Pos.CENTER));
    }
}
