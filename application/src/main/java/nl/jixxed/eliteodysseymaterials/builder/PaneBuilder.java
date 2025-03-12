package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyablePane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaneBuilder extends AbstractPaneBuilder<PaneBuilder> {

    public static PaneBuilder builder() {
        return new PaneBuilder();
    }

    @SuppressWarnings("unchecked")
    public DestroyablePane build() {
        DestroyablePane pane = new DestroyablePane();
        return build(pane);
    }
}
