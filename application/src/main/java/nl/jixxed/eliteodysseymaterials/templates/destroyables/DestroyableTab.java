package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.Tab;
import lombok.Getter;

@Getter
public class DestroyableTab extends Tab implements DestroyableComponent {

    @Override
    public void destroyInternal() {
        this.setContent(null);
        DestroyableComponent.super.destroyInternal();
    }
}
