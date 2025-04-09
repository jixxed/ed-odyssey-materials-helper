package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

public class DestroyableSeparator extends Separator implements DestroyableComponent {
    public DestroyableSeparator() {
    }

    public DestroyableSeparator(Orientation orientation) {
        super(orientation);
    }
}
