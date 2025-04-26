package nl.jixxed.eliteodysseymaterials.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonBuilder extends AbstractButtonBaseBuilder<ButtonBuilder> {
    private EventHandler<ActionEvent> onAction;

    public static ButtonBuilder builder() {
        return new ButtonBuilder();
    }

    @Override
    @SuppressWarnings("unchecked")
    public DestroyableButton build() {
        return build(new DestroyableButton());
    }

}
