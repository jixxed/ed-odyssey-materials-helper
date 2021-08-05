package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextFieldBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private StringBinding promptTextBinding;
    private Boolean isTraversable;

    public static TextFieldBuilder builder() {
        return new TextFieldBuilder();
    }

    public TextFieldBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public TextFieldBuilder withPromptTextProperty(final StringBinding promptTextBinding) {
        this.promptTextBinding = promptTextBinding;
        return this;
    }

    public TextFieldBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public TextFieldBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public TextFieldBuilder withFocusTraversable(final boolean isTraversable) {
        this.isTraversable = isTraversable;
        return this;
    }

    public TextField build() {
        final TextField textField = new TextField();
        textField.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            textField.textProperty().bind(this.stringBinding);
        }
        if (this.promptTextBinding != null) {
            textField.promptTextProperty().bind(this.promptTextBinding);
        }
        if (this.isTraversable != null) {
            textField.setFocusTraversable(this.isTraversable);
        }
        return textField;
    }

}
