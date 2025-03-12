package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class DestroyablePane extends Pane implements DestroyableParent {
    ObservableListOverride<DestroyablePane> override = new ObservableListOverride<>(DestroyablePane.this, super.getChildren());

    public ObservableListOverride<DestroyablePane> getNodes() {
        return override;
    }

}
