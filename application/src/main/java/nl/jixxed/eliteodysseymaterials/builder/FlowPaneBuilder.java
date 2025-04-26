package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlowPaneBuilder extends AbstractFlowPaneBuilder<FlowPaneBuilder> {

    public static FlowPaneBuilder builder() {
        return new FlowPaneBuilder();
    }

    @SuppressWarnings("unchecked")
    public DestroyableFlowPane build() {
        final DestroyableFlowPane flowPane = new DestroyableFlowPane();
        super.build(flowPane);
        return flowPane;
    }

}
