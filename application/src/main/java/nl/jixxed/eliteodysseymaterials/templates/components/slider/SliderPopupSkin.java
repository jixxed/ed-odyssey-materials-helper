package nl.jixxed.eliteodysseymaterials.templates.components.slider;

import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class SliderPopupSkin implements Skin<SliderPopup>, Destroyable {

    SliderPopup skinnable;
    DestroyableLabel valueText;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    public SliderPopupSkin(PopupControl control) {
        skinnable = (SliderPopup) control;
        valueText = register(LabelBuilder.builder()
                .build());
        valueText.addBinding(valueText.textProperty(), skinnable.valueProperty().map(value -> String.format("%.2f", value.doubleValue())));
    }

    @Override
    public SliderPopup getSkinnable() {
        return skinnable;
    }

    @Override
    public Node getNode() {
        return valueText;
    }

    @Override
    public void dispose() {
        valueText.destroy();
        skinnable = null;
        this.destroy();

    }
}