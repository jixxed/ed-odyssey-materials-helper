package nl.jixxed.eliteodysseymaterials.builder;

import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractFlowPaneBuilder<T extends AbstractFlowPaneBuilder<T>> extends AbstractPaneBuilder<T> {
    private Orientation orientation;

    public T withOrientation(final Orientation orientation) {
        this.orientation = orientation;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <P extends FlowPane & DestroyableComponent> P build(P flowPane) {
        super.build(flowPane);

        if (this.orientation != null) {
            flowPane.setOrientation(this.orientation);
        }
        return flowPane;
    }
}
