package nl.jixxed.eliteodysseymaterials.builder;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import org.apache.commons.lang3.NotImplementedException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoxBuilder extends AbstractPaneBuilder<BoxBuilder> {
    private EventHandler<? super MouseEvent> onMouseClicked;

    public static BoxBuilder builder() {
        return new BoxBuilder();
    }

    public BoxBuilder withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return this;
    }

    public <B extends Pane & DestroyableComponent> B build(B box){
        super.build(box);
        if (this.onMouseClicked != null) {
            box.registerEventHandler(MouseEvent.MOUSE_CLICKED, this.onMouseClicked);
        }
        return box;
    }
    /**
     * Use buildHBox or buildVBox instead
     */
    @Override
    public <N extends Node> N build() {
        throw new NotImplementedException("Use buildHBox or buildVBox instead");
    }

    public DestroyableHBox buildHBox() {
        final DestroyableHBox hBox = new DestroyableHBox();
        return build(hBox);
    }

    public DestroyableVBox buildVBox() {
        final DestroyableVBox vBox = new DestroyableVBox();
        return build(vBox);
    }

}
