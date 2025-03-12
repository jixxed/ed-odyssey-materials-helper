package nl.jixxed.eliteodysseymaterials.builder;

import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlowPaneBuilder extends AbstractPaneBuilder<FlowPaneBuilder> {
    private Orientation orientation;

    public static FlowPaneBuilder builder() {
        return new FlowPaneBuilder();
    }

    public FlowPaneBuilder withOrientation(final Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    @SuppressWarnings("unchecked")
    public FlowPane build() {
        final DestroyableFlowPane flowPane = new DestroyableFlowPane();
        super.build(flowPane);

        if (this.orientation != null) {
            flowPane.setOrientation(this.orientation);
        }

        return flowPane;
    }

}
