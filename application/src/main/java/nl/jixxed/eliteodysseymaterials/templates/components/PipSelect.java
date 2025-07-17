package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.builder.RectangleBuilder;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableRectangle;

public class PipSelect extends DestroyableHBox {

    private final DestroyableRectangle rectangle1;
    private final DestroyableRectangle rectangle2;
    private final DestroyableRectangle rectangle3;
    private final DestroyableRectangle rectangle4;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 8;
    private final SimpleIntegerProperty value;

    public PipSelect(int value) {
        super();
        this.getStyleClass().add("pipselect");
        this.value = new SimpleIntegerProperty(clamp(value, MIN_VALUE, MAX_VALUE));
        rectangle1 = getRectangle(_ -> this.decrease());
        rectangle2 = getRectangle(_ -> this.decrease());
        rectangle3 = getRectangle(_ -> this.increase());
        rectangle4 = getRectangle(_ -> this.increase());
        this.getNodes().addAll(
                rectangle1,
                rectangle2,
                rectangle3,
                rectangle4
        );
        render();
        addChangeListener(this.value, (_, _, _) -> render());
    }

    private DestroyableRectangle getRectangle(EventHandler<MouseEvent> eventHandler) {
        final DestroyableRectangle rectangle = RectangleBuilder.builder()
                .withStyleClass("pipselect-rectangle")
                .withWidth(ScalingHelper.getPixelDoubleFromEm(1.5))
                .withHeight(ScalingHelper.getPixelDoubleFromEm(1.5))
                .withOnMouseClicked(eventHandler)
                .build();
        rectangle.addBinding(rectangle.widthProperty(), ScalingHelper.getPixelDoubleBindingFromEm(1.5));
        rectangle.addBinding(rectangle.heightProperty(), ScalingHelper.getPixelDoubleBindingFromEm(1.5));
        return rectangle;
    }

    public int getValue() {
        return value.get();
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    /**
     * Simple utility function which clamps the given value to be strictly
     * between the min and max values.
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        return Math.min(value, max);
    }

    private void decrease() {
        this.value.set(clamp(this.value.get() - 1, MIN_VALUE, MAX_VALUE));
    }

    private void increase() {
        this.value.set(clamp(this.value.get() + 1, MIN_VALUE, MAX_VALUE));
    }

    private void render() {
        rectangle4.pseudoClassStateChanged(PseudoClass.getPseudoClass("full"), value.get() >= 8);
        rectangle4.pseudoClassStateChanged(PseudoClass.getPseudoClass("half"), value.get() >= 7);
        rectangle3.pseudoClassStateChanged(PseudoClass.getPseudoClass("full"), value.get() >= 6);
        rectangle3.pseudoClassStateChanged(PseudoClass.getPseudoClass("half"), value.get() >= 5);
        rectangle2.pseudoClassStateChanged(PseudoClass.getPseudoClass("full"), value.get() >= 4);
        rectangle2.pseudoClassStateChanged(PseudoClass.getPseudoClass("half"), value.get() >= 3);
        rectangle1.pseudoClassStateChanged(PseudoClass.getPseudoClass("full"), value.get() >= 2);
        rectangle1.pseudoClassStateChanged(PseudoClass.getPseudoClass("half"), value.get() >= 1);
    }
}
