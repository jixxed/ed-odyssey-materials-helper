package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.property.IntegerProperty;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class MinMaxIntField extends DestroyableHBox implements DestroyableTemplate {
    private final IntField intField;

    public MinMaxIntField(final Integer min, final Integer max, final Integer initial) {
        this.intField = new IntField(min, max, initial);
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("min-max-int-field");
        DestroyableButton minus = ButtonBuilder.builder()
                .withNonLocalizedText("0")
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getMinValue());
                })
                .build();
        DestroyableButton plus = ButtonBuilder.builder()
                .withNonLocalizedText(UTF8Constants.INFINITY)
                .withOnAction(event -> {
                    this.intField.setValue(this.intField.getMaxValue());
                })
                .build();
        this.getNodes().addAll(minus, this.intField, plus);
    }


    public int getMaxValue() {
        return intField.getMaxValue();
    }

    public void setMaxValue(int maxFuel) {
        intField.setMaxValue(maxFuel);
    }

    public void setValue(int i) {
        intField.setValue(i);
    }

    public IntegerProperty valueProperty() {
        return intField.valueProperty();
    }
}
