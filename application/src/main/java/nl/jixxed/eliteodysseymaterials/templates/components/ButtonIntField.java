package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.function.IntConsumer;

public class ButtonIntField extends HBox implements Template {
    private final IntField intField;
    private Button minus;
    private Button plus;

    public ButtonIntField(final Integer min, final Integer max, final Integer initial) {
        this.intField = new IntField(min, max, initial);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.minus = ButtonBuilder.builder()
                .withNonLocalizedText("-")
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getValue() - 1);
                })
                .build();
        this.plus = ButtonBuilder.builder()
                .withNonLocalizedText("+")
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getValue() + 1);
                })
                .build();
        this.getChildren().addAll(this.minus, this.intField, this.plus);
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }

    public void addHandlerOnValidChange(final IntConsumer function) {
        this.intField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || "".equals(newValue) || (this.intField.getMinValue() < 0 && "-".equals(newValue))) {
                return;
            }

            final int intValue = Integer.parseInt(newValue);
            if (this.intField.getMinValue() <= intValue && intValue <= this.intField.getMaxValue()) {
                function.accept(intValue);
            }
        });
    }
}
