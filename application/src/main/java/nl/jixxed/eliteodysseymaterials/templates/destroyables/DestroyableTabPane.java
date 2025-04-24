package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.TabPane;
import lombok.Getter;

@Getter
public class DestroyableTabPane extends TabPane implements DestroyableComponent {

    @Override
    public void destroyInternal() {
        this.getTabs().clear();
        DestroyableComponent.super.destroyInternal();
    }
}
