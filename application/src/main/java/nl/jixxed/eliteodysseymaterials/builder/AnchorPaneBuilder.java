package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnchorPaneBuilder extends AbstractPaneBuilder<AnchorPaneBuilder>{
    public static AnchorPaneBuilder builder() {
        return new AnchorPaneBuilder();
    }



    @SuppressWarnings("unchecked")
    public DestroyableAnchorPane build() {
        final DestroyableAnchorPane anchorPane = new DestroyableAnchorPane();
        super.build(anchorPane);

        return anchorPane;
    }
}
