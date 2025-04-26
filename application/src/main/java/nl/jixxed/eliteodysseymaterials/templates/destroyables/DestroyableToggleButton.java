package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.ToggleButton;
import lombok.Getter;

@Getter
public class DestroyableToggleButton extends ToggleButton implements DestroyableComponent {
    @Override
    public void destroyInternal() {
        DestroyableComponent.super.destroyInternal();
        this.toggleGroupProperty().set(null);
    }
}
