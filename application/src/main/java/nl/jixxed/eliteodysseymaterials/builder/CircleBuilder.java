package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableCircle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CircleBuilder extends AbstractShapeBuilder<CircleBuilder> {

    private Double radius;

    public static CircleBuilder builder() {
        return new CircleBuilder();
    }

    public CircleBuilder withRadius(Double radius) {
        this.radius = radius;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableCircle build() {
        DestroyableCircle circle = new DestroyableCircle();
        super.build(circle);
        if (radius != null) {
            circle.setRadius(radius);
        }
        return circle;
    }
}
