package nl.jixxed.eliteodysseymaterials.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractButtonBaseBuilder<T extends AbstractButtonBaseBuilder<T>> extends AbstractLabeledBuilder<T> {
    private EventHandler<ActionEvent> onAction;

    public T withOnAction(final EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
        return (T) this;
    }

    public <P extends ButtonBase & DestroyableComponent> P build(P button) {
        super.build(button);

        if (this.onAction != null) {
            button.registerEventHandler(ActionEvent.ACTION, this.onAction);
        }
        return button;
    }
}
