package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.ShipModuleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShipModuleButtonBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private EventHandler<ActionEvent> onAction;
    private ObservableValue<String> observableValue;
    private String nonLocalizedText;
    private DestroyableResizableImageView imageView;
    private ShipModule shipModule;

    public static ShipModuleButtonBuilder builder() {
        return new ShipModuleButtonBuilder();
    }

    public ShipModuleButtonBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ShipModuleButtonBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ShipModuleButtonBuilder withText(final ObservableValue<String> observableValue) {
        this.observableValue = observableValue;
        return this;
    }

    public ShipModuleButtonBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public ShipModuleButtonBuilder withGraphic(final DestroyableResizableImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public ShipModuleButtonBuilder withOnAction(final EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
        return this;
    }
    public ShipModuleButtonBuilder withShipModule(final ShipModule shipModule) {
        this.shipModule = shipModule;
        return this;
    }

    public ShipModuleButton build() {
        final ShipModuleButton button = new ShipModuleButton(this.shipModule);
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
