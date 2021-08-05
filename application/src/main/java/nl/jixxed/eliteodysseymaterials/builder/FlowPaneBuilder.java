package nl.jixxed.eliteodysseymaterials.builder;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlowPaneBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private final List<Node> nodes = new ArrayList<>();
    private EventHandler<? super MouseEvent> onMouseClicked;
    private Orientation orientation;

    public static FlowPaneBuilder builder() {
        return new FlowPaneBuilder();
    }

    public FlowPaneBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public FlowPaneBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public FlowPaneBuilder withNode(final Node node) {
        this.nodes.add(node);
        return this;
    }

    public FlowPaneBuilder withNodes(final Node... nodes) {
        this.nodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public FlowPaneBuilder withNodes(final List<? extends Node> nodes) {
        this.nodes.addAll(nodes);
        return this;
    }

    public FlowPaneBuilder withOrientation(final Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public FlowPaneBuilder withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return this;
    }

    public FlowPane build() {
        final FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().addAll(this.styleClasses);
        flowPane.getChildren().addAll(this.nodes);
        if (this.onMouseClicked != null) {
            flowPane.setOnMouseClicked(this.onMouseClicked);
        }
        if (this.orientation != null) {
            flowPane.setOrientation(this.orientation);
        }
        return flowPane;
    }

}
