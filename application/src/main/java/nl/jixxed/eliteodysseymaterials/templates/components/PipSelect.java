package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;

public class PipSelect extends HBox {

    private final Rectangle rectangle1;
    private final Rectangle rectangle2;
    private final Rectangle rectangle3;
    private final Rectangle rectangle4;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 8;
    private final SimpleIntegerProperty value;

    public PipSelect(int value) {
        super();
        this.getStyleClass().add("pipselect");
        this.value = new SimpleIntegerProperty(clamp(value, MIN_VALUE, MAX_VALUE));
        rectangle1 = getRectangle();
        rectangle2 = getRectangle();
        rectangle3 = getRectangle();
        rectangle4 = getRectangle();
        this.getChildren().addAll(
                rectangle1,
                rectangle2,
                rectangle3,
                rectangle4
        );
        render();
        eventHandling();
    }

    private static Rectangle getRectangle() {
        final Rectangle rectangle = new Rectangle(ScalingHelper.getPixelDoubleFromEm(1.5), ScalingHelper.getPixelDoubleFromEm(1.5));
        rectangle.widthProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(1.5));
        rectangle.heightProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(1.5));
        rectangle.getStyleClass().add("pipselect-rectangle");
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
    public static int clamp(int value,int min, int max) {
        if (value < min) return min;
        return Math.min(value, max);
    }

    private void eventHandling() {
        rectangle1.setOnMouseClicked(event -> {
            this.decrease();
        });
        rectangle2.setOnMouseClicked(event -> {
            this.decrease();
        });
        rectangle3.setOnMouseClicked(event -> {
            this.increase();
        });
        rectangle4.setOnMouseClicked(event -> {
            this.increase();
        });
        this.value.addListener((observable, oldValue, newValue) -> render());
    }

    private void decrease() {
        this.value.set(clamp(this.value.get() - 1, MIN_VALUE, MAX_VALUE));
    }

    private void increase() {
        this.value.set(clamp(this.value.get() + 1, MIN_VALUE, MAX_VALUE));
    }

    private void render() {
        rectangle1.getStyleClass().removeAll("pipselect-full", "pipselect-half");
        rectangle2.getStyleClass().removeAll("pipselect-full", "pipselect-half");
        rectangle3.getStyleClass().removeAll("pipselect-full", "pipselect-half");
        rectangle4.getStyleClass().removeAll("pipselect-full", "pipselect-half");
        switch (value.get()) {
            case 8:
                rectangle4.getStyleClass().add("pipselect-full");
            case 7:
                rectangle4.getStyleClass().add("pipselect-half");
            case 6:
                rectangle3.getStyleClass().add("pipselect-full");
            case 5:
                rectangle3.getStyleClass().add("pipselect-half");
            case 4:
                rectangle2.getStyleClass().add("pipselect-full");
            case 3:
                rectangle2.getStyleClass().add("pipselect-half");
            case 2:
                rectangle1.getStyleClass().add("pipselect-full");
            case 1:
                rectangle1.getStyleClass().add("pipselect-half");
                break;
            case 0:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
    }
}
