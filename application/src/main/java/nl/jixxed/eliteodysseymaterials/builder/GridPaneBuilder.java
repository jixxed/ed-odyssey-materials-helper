package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableGridPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GridPaneBuilder extends AbstractPaneBuilder<GridPaneBuilder>{

    public static GridPaneBuilder builder() {
        return new GridPaneBuilder();
    }

    @SuppressWarnings("unchecked")
    public DestroyableGridPane build() {
        final DestroyableGridPane gridPane = new DestroyableGridPane();
        super.build(gridPane);

        return gridPane;
    }
}
