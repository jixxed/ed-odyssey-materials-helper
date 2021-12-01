package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.ResizableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private EventHandler<ActionEvent> onAction;
    private ObservableValue<String> observableValue;
    private String nonLocalizedText;
    private ResizableImageView imageView;

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

    public ButtonBuilder withText(final ObservableValue<String> observableValue) {
        this.observableValue = observableValue;
        return this;
    }

    public ButtonBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public ButtonBuilder withGraphic(final ResizableImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public ButtonBuilder withOnAction(final EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
        return this;
    }

    public Button build() {
        final Button button = new Button();
        button.getStyleClass().addAll(this.styleClasses);
        if (this.observableValue != null) {
            button.textProperty().bind(this.observableValue);
        } else if (this.nonLocalizedText != null) {
            button.setText(this.nonLocalizedText);
        }
        if (this.imageView != null) {
            button.setGraphic(this.imageView);
        }
        if (this.onAction != null) {
            button.setOnAction(this.onAction);
        }
        return button;
    }

}
