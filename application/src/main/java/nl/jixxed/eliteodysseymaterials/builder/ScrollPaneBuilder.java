package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollPaneBuilder extends AbstractControlBuilder<ScrollPaneBuilder>{
    private static final double SCROLL_SPEED = 0.01;
    private Node content;

    public static ScrollPaneBuilder builder() {
        return new ScrollPaneBuilder();
    }


    public ScrollPaneBuilder withContent(final Node content) {
        this.content = content;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableScrollPane build() {
        final DestroyableScrollPane scrollPane = new DestroyableScrollPane();
        super.build(scrollPane);

        scrollPane.setContent(this.content);
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        if (scrollPane.getContent() != null) {
            scrollPane.registerEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
                final double speed = scrollPane.getHeight() / scrollPane.getContent().getLayoutBounds().getHeight();
                final double deltaY = scrollEvent.getDeltaY() * speed * SCROLL_SPEED;
                scrollPane.setVvalue(scrollPane.getVvalue() - deltaY);
            });
        }

        return scrollPane;
    }
}
