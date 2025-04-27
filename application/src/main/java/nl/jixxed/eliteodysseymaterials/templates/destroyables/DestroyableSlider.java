package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.Slider;
import lombok.Getter;

@Getter
public class DestroyableSlider extends Slider implements DestroyableComponent {
    public DestroyableSlider(double min, double max, double value) {
        super(min, max, value);
    }

    @Override
    public void destroyInternal() {
        DestroyableComponent.super.destroyInternal();
        if (this.getSkin() != null) {
            this.getSkin().dispose();
        }
    }
}
