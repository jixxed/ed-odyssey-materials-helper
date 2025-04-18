package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.shape.Line;

public class DestroyableLine extends Line implements DestroyableComponent {
    public DestroyableLine() {
        super();
    }

    public DestroyableLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
}
