package nl.jixxed.eliteodysseymaterials.templates.components;

import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.function.IntConsumer;

public class ButtonIntField extends DestroyableHBox implements DestroyableTemplate {
    private final IntField intField;

    public ButtonIntField(final Integer min, final Integer max, final Integer initial) {
        this.intField = new IntField(min, max, initial);
        initComponents();
    }

    @Override
    public void initComponents() {
        DestroyableButton minus = ButtonBuilder.builder()
                .withNonLocalizedText("-")
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getValue() - 1);
                })
                .build();
        DestroyableButton plus = ButtonBuilder.builder()
                .withNonLocalizedText("+")
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getValue() + 1);
                })
                .build();
        this.getNodes().addAll(minus, this.intField, plus);
    }

    public void addHandlerOnValidChange(final IntConsumer function) {
        addChangeListener(this.intField.textProperty(), (_, _, newValue) -> {
            if (newValue == null || newValue.isEmpty() || (this.intField.getMinValue() < 0 && "-".equals(newValue))) {
                return;
            }

            final int intValue = Integer.parseInt(newValue);
            if (this.intField.getMinValue() <= intValue && intValue <= this.intField.getMaxValue()) {
                function.accept(intValue);
            }
        });
    }
}
