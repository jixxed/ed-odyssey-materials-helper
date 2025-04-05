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
