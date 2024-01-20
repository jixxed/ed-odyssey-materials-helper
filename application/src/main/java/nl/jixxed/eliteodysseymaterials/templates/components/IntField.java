package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lombok.Getter;

public class IntField extends TextField {
    private final IntegerProperty value;
    @Getter
    private int minValue;
    @Getter
    private int maxValue;

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if(this.value.getValue() < minValue) {
            this.value.setValue(minValue);
        }
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if(this.value.getValue() > maxValue) {
            this.value.setValue(maxValue);
        }
    }

    // expose an integer value property for the text field.
    public int getValue() {
        return this.value.getValue();
    }

    public void setValue(final int newValue) {
        this.value.setValue(newValue);
    }

    public IntegerProperty valueProperty() {
        return this.value;
    }

    public IntField(final int minValue, final int maxValue, final int initialValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException(
                    "IntField min value " + minValue + " greater than max value " + maxValue
            );
        }
        if (!((minValue <= initialValue) && (initialValue <= maxValue))) {
            throw new IllegalArgumentException(
                    "IntField initialValue " + initialValue + " not between " + minValue + " and " + maxValue
            );
        }

        // initialize the field values.
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = new SimpleIntegerProperty(initialValue);
        setText(initialValue + "");


        // make sure the value property is clamped to the required range
        // and update the field's text to be in sync with the value.
        addValueListener();

        // restrict key input to numerals.
        restrictInputToNumerals();

        // ensure any entered values lie inside the required range.
        addTextListener();
    }

    private void addTextListener() {
        this.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || "".equals(newValue) || (this.minValue < 0 && "-".equals(newValue))) {
                IntField.this.value.setValue(0);
                return;
            }

            final int intValue = Integer.parseInt(newValue);

            if (this.minValue > intValue || intValue > this.maxValue) {
                textProperty().setValue(oldValue);
            }

            IntField.this.value.set(Integer.parseInt(textProperty().get()));
        });
    }

    private void addValueListener() {
        this.value.addListener((observableValue, oldValue, newValue) -> {
            if (oldValue.equals(newValue)) {
                return;
            }
            if (newValue == null) {
                this.setText("");
            } else {
                if (newValue.intValue() < this.minValue) {
                    IntField.this.value.setValue(this.minValue);
                    return;
                }

                if (newValue.intValue() > this.maxValue) {
                    IntField.this.value.setValue(this.maxValue);
                    return;
                }

                if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
                    // no action required, text property is already blank, we don't need to set it to 0.
                } else {
                    this.setText(newValue.toString());
                }
            }
        });
    }

    private void restrictInputToNumerals() {
        this.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (this.minValue < 0) {
                if (!"-0123456789".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            } else {
                if (!"0123456789".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        });
    }
}