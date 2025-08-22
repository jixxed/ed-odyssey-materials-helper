package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;

public class EdAwesomeIconViewPaneBuilder extends AbstractPaneBuilder<EdAwesomeIconViewPaneBuilder> {
    private EdAwesomeIconView[] edAwesomeIconViews;

    public EdAwesomeIconViewPaneBuilder withIcons(EdAwesomeIconView... edAwesomeIconViews){
        this.edAwesomeIconViews = edAwesomeIconViews;
        return this;
    }

    public static EdAwesomeIconViewPaneBuilder builder() {
        return new EdAwesomeIconViewPaneBuilder();
    }

    @SuppressWarnings("unchecked")
    @Override
    public EdAwesomeIconViewPane build() {
        EdAwesomeIconViewPane edAwesomeIconViewPane = new EdAwesomeIconViewPane(edAwesomeIconViews);
        super.build(edAwesomeIconViewPane);
        return edAwesomeIconViewPane;
    }
}
