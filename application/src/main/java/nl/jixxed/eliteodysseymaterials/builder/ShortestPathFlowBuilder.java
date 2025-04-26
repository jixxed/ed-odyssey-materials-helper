package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathFlow;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortestPathFlowBuilder<T extends BlueprintName<T>> extends AbstractFlowPaneBuilder<FlowPaneBuilder> {

    private Expansion expansion;
    private List<PathItem<T>> pathItems;

    public static <T extends BlueprintName<T>> ShortestPathFlowBuilder<T> builder() {
        return new ShortestPathFlowBuilder();
    }

    public ShortestPathFlowBuilder<T> withExpansion(final Expansion expansion) {
        this.expansion = expansion;
        return this;
    }

    public ShortestPathFlowBuilder<T> withPathItems(final List<PathItem<T>> pathItems) {
        this.pathItems = pathItems;
        return this;
    }

    @SuppressWarnings("unchecked")
    public ShortestPathFlow<T> build() {
        if (expansion == null) {
            throw new IllegalStateException("Expansion must be set");
        }
        final ShortestPathFlow<T> shortestPathFlow = new ShortestPathFlow<>(expansion);
        super.build(shortestPathFlow);
        shortestPathFlow.setItems(pathItems);

        return shortestPathFlow;
    }

}
