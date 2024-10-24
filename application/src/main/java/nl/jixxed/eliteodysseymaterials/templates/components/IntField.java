package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntField extends TextField {
    private final IntegerProperty value;
    @Getter
    private int minValue;
    @Getter
    private int maxValue;
    private boolean isUpdating = false; // Flag to prevent recursive updates

    public IntField(int minValue, int maxValue, int initialValue) {
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
        this.minValue = minValue;
        this.maxValue = maxValue;

        // Initialize the value property and set the initial text
        value = new SimpleIntegerProperty(initialValue);
        setText(String.valueOf(initialValue));

        // Listen to changes in the text field's text property
        addTextListener(minValue, maxValue);

        // restrict key input to numerals.
        restrictInputToNumerals();

        // Listen to changes in the value property
        addValueListener();
    }

    private void addValueListener() {
        value.addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;

            isUpdating = true;
            setText(newValue.toString());
            isUpdating = false;
        });
    }

    private void addTextListener(int minValue, int maxValue) {
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;

            if (newValue == null || newValue.isEmpty() || !isValidInt(newValue)) {
                setText(oldValue); // Revert to the old value if the input is invalid
            } else {
                int intValue = Integer.parseInt(newValue);
                if (intValue < getMinValue() || intValue > getMaxValue()) {
                    setText(oldValue); // Revert if the value is out of bounds
                } else {
                    isUpdating = true;
                    value.set(intValue); // Update the value property
                    isUpdating = false;
                }
            }
        });
    }

    private void restrictInputToNumerals() {
        this.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (this.getMinValue() < 0) {
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

    // Check if the input string is a valid integer
    private boolean isValidInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public int getValue() {
        return value.get();
    }

    public void setValue(int value) {
        if (value >= minValue && value <= maxValue) {
            this.value.set(value);
        }
    }


    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if (this.value.getValue() < minValue) {
            this.value.setValue(minValue);
        }
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if (this.value.getValue() > maxValue) {
            this.value.setValue(maxValue);
        }
    }
}