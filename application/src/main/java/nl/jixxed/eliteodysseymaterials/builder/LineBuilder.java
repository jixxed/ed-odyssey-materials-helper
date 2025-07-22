package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLine;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LineBuilder extends AbstractShapeBuilder<LineBuilder> {

    private Boolean isMouseTransparent;

    public static LineBuilder builder() {
        return new LineBuilder();
    }

    public LineBuilder withMouseTransparent(Boolean isMouseTransparent) {
        this.isMouseTransparent = isMouseTransparent;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableLine build() {
        DestroyableLine line = new DestroyableLine();
        super.build(line);
        if (this.isMouseTransparent != null) {
            line.setMouseTransparent(this.isMouseTransparent);
        }
        return line;
    }
}
