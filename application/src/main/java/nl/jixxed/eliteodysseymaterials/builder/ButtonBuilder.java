package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private EventHandler<ActionEvent> onAction;
    private StringBinding stringBinding;
    private String nonLocalizedText;

    public static ButtonBuilder builder() {
        return new ButtonBuilder();
    }

    public ButtonBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ButtonBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ButtonBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public ButtonBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public ButtonBuilder withOnAction(final EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
        return this;
    }

    public Button build() {
        final Button button = new Button();
        button.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            button.textProperty().bind(this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            button.setText(this.nonLocalizedText);
        }
        if (this.onAction != null) {
            button.setOnAction(this.onAction);
        }
        return button;
    }

}
