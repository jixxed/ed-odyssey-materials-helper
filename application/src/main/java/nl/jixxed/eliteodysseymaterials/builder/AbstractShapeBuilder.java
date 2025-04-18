package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.shape.Shape;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractShapeBuilder<T extends AbstractShapeBuilder<T>> extends AbstractNodeBuilder<T> {

    public <P extends Shape & DestroyableComponent> P build(P shape) {
        super.build(shape);
        return shape;
    }

}
