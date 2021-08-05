package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private EventHandler<? super MouseEvent> onMouseClicked;
    private String nonLocalizedText;
    private NodeOrientation nodeOrientation;
    private Double wrappingWidth;

    public static TextBuilder builder() {
        return new TextBuilder();
    }

    public TextBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public TextBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public TextBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public TextBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public TextBuilder withNodeOrientation(final NodeOrientation nodeOrientation) {
        this.nodeOrientation = nodeOrientation;
        return this;
    }

    public TextBuilder withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return this;
    }

    public TextBuilder withWrappingWidth(final Double wrappingWidth) {
        this.wrappingWidth = wrappingWidth;
        return this;
    }

    public Text build() {
        final Text text = new Text();
        text.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            text.textProperty().bind(this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            text.setText(this.nonLocalizedText);
        }
        if (this.onMouseClicked != null) {
            text.setOnMouseClicked(this.onMouseClicked);
        }
        if (this.nodeOrientation != null) {
            text.setNodeOrientation(this.nodeOrientation);
        }
        if (this.wrappingWidth != null) {
            text.setWrappingWidth(this.wrappingWidth);
        }
        return text;
    }

}
