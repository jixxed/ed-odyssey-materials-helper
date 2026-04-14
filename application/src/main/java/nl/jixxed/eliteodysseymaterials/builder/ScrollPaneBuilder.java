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
import javafx.scene.control.ScrollPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollPaneBuilder extends AbstractControlBuilder<ScrollPaneBuilder> {
    private static final double SCROLL_SPEED = 0.01;
    private Node content;
    private ScrollPane.ScrollBarPolicy vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS;
    private ScrollPane.ScrollBarPolicy hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER;

    public static ScrollPaneBuilder builder() {
        return new ScrollPaneBuilder();
    }


    public <E extends Node & Destroyable> ScrollPaneBuilder withContent(final E content) {
        this.content = content;
        return this;
    }

    public ScrollPaneBuilder withVbarPolicy(ScrollPane.ScrollBarPolicy scrollBarPolicy) {
        this.vbarPolicy = scrollBarPolicy;
        return this;
    }

    public ScrollPaneBuilder withHbarPolicy(ScrollPane.ScrollBarPolicy scrollBarPolicy) {
        this.hbarPolicy = scrollBarPolicy;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableScrollPane build() {
        final DestroyableScrollPane scrollPane = new DestroyableScrollPane();
        super.build(scrollPane);
        if (content != null) {
            final DestroyableVBox contentNode = BoxBuilder.builder()
                    .withStyleClass("scroll-pane-content")
                    .withNode((Node & DestroyableComponent) this.content)
                    .buildVBox();
            scrollPane.setContentNode(scrollPane.register(contentNode));
        }
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(vbarPolicy);
        scrollPane.setHbarPolicy(hbarPolicy);
        if (scrollPane.getContent() != null) {
            scrollPane.addEventBinding(scrollPane.onScrollProperty(), scrollEvent -> {
                final double speed = scrollPane.getHeight() / scrollPane.getContent().getLayoutBounds().getHeight();
                final double deltaY = scrollEvent.getDeltaY() * speed * SCROLL_SPEED;
                scrollPane.setVvalue(scrollPane.getVvalue() - deltaY);
            });
        }

        return scrollPane;
    }

}
