package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StackPaneBuilder extends AbstractPaneBuilder<StackPaneBuilder> {
    public static StackPaneBuilder builder() {
        return new StackPaneBuilder();
    }

    @SuppressWarnings("unchecked")
    public DestroyableStackPane build() {
        DestroyableStackPane stackPane = new DestroyableStackPane();
        super.build(stackPane);
        return stackPane;
    }

}
