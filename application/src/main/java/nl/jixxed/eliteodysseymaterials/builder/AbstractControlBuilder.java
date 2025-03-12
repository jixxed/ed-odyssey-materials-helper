package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AbstractControlBuilder<T extends AbstractControlBuilder<T>> extends AbstractNodeBuilder<T> {

    private Tooltip tooltip;

    public T withToolTip(final Tooltip tooltip) {
        this.tooltip = tooltip;
        return (T) this;
    }
    public <P extends Control & DestroyableComponent> P build(P control) {
        super.build(control);

        if (this.tooltip != null) {
            control.setTooltip(this.tooltip);
        }
        return control;
    }

}
