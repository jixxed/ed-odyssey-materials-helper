package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private EventHandler<? super MouseEvent> onMouseClicked;
    private String nonLocalizedText;
    private NodeOrientation nodeOrientation;
    private ChangeListener<? super Boolean> hoverPropertyChangeListener;
    private boolean visibility = true;

    public static LabelBuilder builder() {
        return new LabelBuilder();
    }

    public LabelBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public LabelBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public LabelBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public LabelBuilder withVisibility(final boolean visibility) {
        this.visibility = visibility;
        return this;
    }

    public LabelBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public LabelBuilder withNodeOrientation(final NodeOrientation nodeOrientation) {
        this.nodeOrientation = nodeOrientation;
        return this;
    }

    public LabelBuilder withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return this;
    }

    public LabelBuilder withHoverProperty(final ChangeListener<? super Boolean> hoverPropertyChangeListener) {
        this.hoverPropertyChangeListener = hoverPropertyChangeListener;
        return this;
    }

    public Label build() {
        final Label label = new Label();
        label.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            label.textProperty().bind(this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            label.setText(this.nonLocalizedText);
        }
        if (this.onMouseClicked != null) {
            label.setOnMouseClicked(this.onMouseClicked);
        }
        if (this.nodeOrientation != null) {
            label.setNodeOrientation(this.nodeOrientation);
        }
        if (this.hoverPropertyChangeListener != null) {
            label.hoverProperty().addListener(this.hoverPropertyChangeListener);
        }
        label.setVisible(this.visibility);
        return label;
    }
}
