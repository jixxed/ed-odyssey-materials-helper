package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.control.Control;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTooltip;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractControlBuilder<T extends AbstractControlBuilder<T>> extends AbstractNodeBuilder<T> {

    private DestroyableTooltip tooltip;
    private Boolean focusTraversable;

    public T withToolTip(final DestroyableTooltip tooltip) {
        this.tooltip = tooltip;
        return (T) this;
    }

    public T withFocusTraversable(boolean focusTraversable) {
        this.focusTraversable = focusTraversable;
        return (T) this;
    }

    public <P extends Control & DestroyableComponent> P build(P control) {
        super.build(control);

        if (this.tooltip != null) {
            this.tooltip.install(control);
        }
        if (this.focusTraversable != null) {
            control.setFocusTraversable(this.focusTraversable);
        }

        return control;
    }

}
