package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollPaneBuilder {
    private static final double SCROLL_SPEED = 0.005;
    private final List<String> styleClasses = new ArrayList<>();
    private Node content;

    public static ScrollPaneBuilder builder() {
        return new ScrollPaneBuilder();
    }

    public ScrollPaneBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ScrollPaneBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ScrollPaneBuilder withContent(final Node content) {
        this.content = content;
        return this;
    }

    public ScrollPane build() {
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().addAll(this.styleClasses);
        scrollPane.setContent(this.content);
        scrollPane.pannableProperty().set(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        if (scrollPane.getContent() != null) {
            scrollPane.getContent().setOnScroll(scrollEvent -> {
                final double deltaY = scrollEvent.getDeltaY() * SCROLL_SPEED;
                scrollPane.setVvalue(scrollPane.getVvalue() - deltaY);
            });
        }

        return scrollPane;
    }
}
