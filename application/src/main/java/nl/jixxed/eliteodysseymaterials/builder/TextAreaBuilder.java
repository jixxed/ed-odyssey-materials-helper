package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.TextArea;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextAreaBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private StringBinding promptTextBinding;
    private Boolean isTraversable;

    public static TextAreaBuilder builder() {
        return new TextAreaBuilder();
    }

    public TextAreaBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public TextAreaBuilder withPromptTextProperty(final StringBinding promptTextBinding) {
        this.promptTextBinding = promptTextBinding;
        return this;
    }

    public TextAreaBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public TextAreaBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public TextAreaBuilder withFocusTraversable(final boolean isTraversable) {
        this.isTraversable = isTraversable;
        return this;
    }

    public TextArea build() {
        final TextArea textArea = new TextArea();
        textArea.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            textArea.textProperty().bind(this.stringBinding);
        }
        if (this.promptTextBinding != null) {
            textArea.promptTextProperty().bind(this.promptTextBinding);
        }
        if (this.isTraversable != null) {
            textArea.setFocusTraversable(this.isTraversable);
        }
        return textArea;
    }

}
