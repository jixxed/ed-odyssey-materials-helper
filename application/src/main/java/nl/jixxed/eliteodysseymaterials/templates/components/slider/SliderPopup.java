package nl.jixxed.eliteodysseymaterials.templates.components.slider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;

class SliderPopup extends PopupControl {
    private static final String DEFAULT_STYLE_CLASS = "slider-popup";


    SliderPopup() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

    }


    private DoubleProperty value = new SimpleDoubleProperty();

    DoubleProperty valueProperty() {
        return value;
    }

    double getValue() {
        return value.get();
    }

    void setValue(double value) {
        this.value.set(value);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SliderPopupSkin(this);
    }


}
