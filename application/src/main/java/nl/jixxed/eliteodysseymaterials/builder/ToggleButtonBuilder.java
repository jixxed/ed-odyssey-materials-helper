package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToggleButtonBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private EventHandler<ActionEvent> onAction;
    private ObservableValue<String> observableValue;
    private String nonLocalizedText;
    private DestroyableResizableImageView imageView;

    public static ToggleButtonBuilder builder() {
        return new ToggleButtonBuilder();
    }

    public ToggleButtonBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ToggleButtonBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ToggleButtonBuilder withText(final ObservableValue<String> observableValue) {
        this.observableValue = observableValue;
        return this;
    }

    public ToggleButtonBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public ToggleButtonBuilder withGraphic(final DestroyableResizableImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public ToggleButtonBuilder withOnAction(final EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
        return this;
    }

    public ToggleButton build() {
        final ToggleButton button = new ToggleButton();
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
