package nl.jixxed.eliteodysseymaterials.builder;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoxBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private final List<Node> nodes = new ArrayList<>();
    private EventHandler<? super MouseEvent> onMouseClicked;

    public static BoxBuilder builder() {
        return new BoxBuilder();
    }

    public BoxBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public BoxBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public BoxBuilder withNode(final Node node) {
        this.nodes.add(node);
        return this;
    }

    public BoxBuilder withNodes(final Node... nodes) {
        this.nodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public BoxBuilder withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return this;
    }

    public HBox buildHBox() {
        final HBox hBox = new HBox();
        hBox.getStyleClass().addAll(this.styleClasses);
        hBox.getChildren().addAll(this.nodes);
        if (this.onMouseClicked != null) {
            hBox.setOnMouseClicked(this.onMouseClicked);
        }
        return hBox;
    }

    public VBox buildVBox() {
        final VBox vBox = new VBox();
        vBox.getStyleClass().addAll(this.styleClasses);
        vBox.getChildren().addAll(this.nodes);
        if (this.onMouseClicked != null) {
            vBox.setOnMouseClicked(this.onMouseClicked);
        }
        return vBox;
    }

}
