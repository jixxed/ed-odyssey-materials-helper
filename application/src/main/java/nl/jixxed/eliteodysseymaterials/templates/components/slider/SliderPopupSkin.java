package nl.jixxed.eliteodysseymaterials.templates.components.slider;

import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;

public class SliderPopupSkin implements Skin<SliderPopup> {

    SliderPopup skinnable;
    Label valueText;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    public SliderPopupSkin(PopupControl control) {
        skinnable = (SliderPopup)control;
        valueText = new Label();

        valueText.textProperty().bind(new StringBinding() {
            {
                super.bind(skinnable.valueProperty());
            }

            @Override
            protected String computeValue() {
                return String.format("%.2f", skinnable.getValue());
            }
        });
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

    }
}