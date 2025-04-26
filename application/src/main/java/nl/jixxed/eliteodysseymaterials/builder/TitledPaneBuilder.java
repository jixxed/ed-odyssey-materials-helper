package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTitledPane;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TitledPaneBuilder extends AbstractLabeledBuilder<TitledPaneBuilder> {
    private Node content;

    public static TitledPaneBuilder builder() {
        return new TitledPaneBuilder();
    }

    public <E extends Node & Destroyable> TitledPaneBuilder withContent(final E content) {
        this.content = content;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableTitledPane build() {
        final DestroyableTitledPane titledPane = new DestroyableTitledPane();
        super.build(titledPane);
        if (this.content != null) {
            titledPane.register((Destroyable)this.content);
            titledPane.setContent(this.content);
        }
        return titledPane;
    }

}
