package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.Tab;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.TabType;

@Getter
public class DestroyableTab extends Tab implements DestroyableComponent {

    @Setter
    private TabType tabType;

    @Override
    public void destroyInternal() {
        this.setContent(null);
        DestroyableComponent.super.destroyInternal();
    }

    public TabType getTabType() {
        return tabType;
    }

}
