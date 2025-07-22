package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableRectangle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RectangleBuilder extends AbstractShapeBuilder<RectangleBuilder> {

    private Double width;
    private Double height;

    public static RectangleBuilder builder() {
        return new RectangleBuilder();
    }

    public RectangleBuilder withWidth(Double width) {
        this.width = width;
        return this;
    }

    public RectangleBuilder withHeight(Double height) {
        this.height = height;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableRectangle build() {
        DestroyableRectangle rectangle = new DestroyableRectangle();
        super.build(rectangle);
        if (width != null) {
            rectangle.setWidth(width);
        }
        if (height != null) {
            rectangle.setHeight(height);
        }

        return rectangle;
    }
}
