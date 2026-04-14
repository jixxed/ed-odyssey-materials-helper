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

import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTitledPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TitledPaneBuilder extends AbstractLabeledBuilder<TitledPaneBuilder> {
    private Node content;

    public static TitledPaneBuilder builder() {
        return new TitledPaneBuilder();
    }

    public <E extends Node & Destroyable> TitledPaneBuilder withContent(final E content) {
        this.content = content;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableTitledPane build() {
        final DestroyableTitledPane titledPane = new DestroyableTitledPane();
        super.build(titledPane);
        if (this.content != null) {
            titledPane.register((Destroyable)this.content);
            titledPane.setContent(this.content);
        }
        return titledPane;
    }

}
