package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lombok.Setter;

public class IntField extends TextField {
    private final IntegerProperty value;
    @Setter
    private int minValue;
    @Setter
    private int maxValue;

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
        if (maxValue < minValue) {
            throw new IllegalArgumentException(
                    "IntField max value " + minValue + " less than min value " + maxValue
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

        final IntField intField = this;

        // make sure the value property is clamped to the required range
        // and update the field's text to be in sync with the value.
        this.value.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> observableValue, final Number oldValue, final Number newValue) {
                if (newValue == null) {
                    intField.setText("");
                } else {
                    if (newValue.intValue() < intField.minValue) {
                        IntField.this.value.setValue(intField.minValue);
                        return;
                    }

                    if (newValue.intValue() > intField.maxValue) {
                        IntField.this.value.setValue(intField.maxValue);
                        return;
                    }

                    if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
                        // no action required, text property is already blank, we don't need to set it to 0.
                    } else {
                        intField.setText(newValue.toString());
                    }
                }
            }
        });

        // restrict key input to numerals.
        this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                if (intField.minValue < 0) {
                    if (!"-0123456789".contains(keyEvent.getCharacter())) {
                        keyEvent.consume();
                    }
                } else {
                    if (!"0123456789".contains(keyEvent.getCharacter())) {
                        keyEvent.consume();
                    }
                }
            }
        });

        // ensure any entered values lie inside the required range.
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observableValue, final String oldValue, final String newValue) {
                if (newValue == null || "".equals(newValue) || (intField.minValue < 0 && "-".equals(newValue))) {
                    IntField.this.value.setValue(0);
                    return;
                }

                final int intValue = Integer.parseInt(newValue);

                if (intField.minValue > intValue || intValue > intField.maxValue) {
                    textProperty().setValue(oldValue);
                }

                IntField.this.value.set(Integer.parseInt(textProperty().get()));
            }
        });
    }
}