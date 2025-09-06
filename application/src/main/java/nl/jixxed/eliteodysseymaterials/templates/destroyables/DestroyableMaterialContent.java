package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
@Getter
public class DestroyableMaterialContent extends DestroyableVBox {
    private List<Runnable> secondaryActions = new ArrayList<>();

    public void addSecondaryAction(Runnable action) {
        this.secondaryActions.add(action);
    }

    public void triggerSecondaryActions() {
        secondaryActions.forEach(Runnable::run);
    }
}
