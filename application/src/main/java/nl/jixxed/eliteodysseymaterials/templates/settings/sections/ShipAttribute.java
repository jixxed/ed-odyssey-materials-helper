package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class ShipAttribute implements Template {
    private final ShipModule shipModule;
    private final HorizonsModifier modifier;
    @Getter
    private DestroyableLabel title;
    @Getter
    private HBox valuesLine;
    @Getter
    private TextField textField;
    @Getter
    private CheckBox checkBox;

    public ShipAttribute(ShipModule shipModule, HorizonsModifier modifier) {
        super();
        this.shipModule = shipModule;
        this.modifier = modifier;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        addAttribute();
    }

    @Override
    public void initEventHandling() {

    }

    private void addAttribute() {
        final Object originalAttributeValue = shipModule.getOriginalAttributeValue(modifier);
        valuesLine = BoxBuilder.builder().withStyleClass("settings-legacy-module-details-label-values").withNodes().buildHBox();

        title = LabelBuilder.builder().withStyleClass("settings-legacy-module-details-label-title").withText(LocaleService.getStringBinding(modifier.getLocalizationKey())).build();
        if (originalAttributeValue instanceof Double originalValue) {
            final Object modified= shipModule.getModifiers().get(modifier);
            final Double currentValue = modified != null ? Double.valueOf(modified.toString()) : originalValue;
            final DestroyableLabel value = LabelBuilder.builder().withStyleClass("settings-legacy-module-details-label").withNonLocalizedText(modifier.format(currentValue)).build();
            if (currentValue > originalValue && modifier.isHigherBetter() || currentValue < originalValue && !modifier.isHigherBetter()) {
                value.getStyleClass().add("settings-legacy-module-details-label-green");
            } else if (currentValue < originalValue && modifier.isHigherBetter() || currentValue > originalValue && !modifier.isHigherBetter()) {
                value.getStyleClass().add("settings-legacy-module-details-label-red");
            }
            valuesLine.getChildren().addAll(
                    LabelBuilder.builder().withStyleClass("settings-legacy-module-details-label").withNonLocalizedText(modifier.format(originalAttributeValue)).build()
            );
            if (isModifiedAttribute(originalValue, currentValue)) {
                valuesLine.getChildren().addAll(
                        LabelBuilder.builder().withNonLocalizedText(" → ").build(),
                        value
                );
            }
            textField = new TextField(currentValue.toString());
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textField.getStyleClass().removeAll("settings-legacy-module-valid", "settings-legacy-module-invalid");
                try {
                    final double parsedValue = Double.parseDouble(newValue);
                    textField.getStyleClass().add("settings-legacy-module-valid");
                    shipModule.getModifiers().put(modifier, parsedValue);
                } catch (NumberFormatException | NullPointerException e) {
                    textField.getStyleClass().add("settings-legacy-module-invalid");
                    shipModule.getModifiers().put(modifier, currentValue);
                }
            });
        }
        if (originalAttributeValue instanceof Boolean originalValue) {
            final Object modified = shipModule.getModifiers().get(modifier);
            final Boolean currentValue = modified != null ? Boolean.TRUE.equals(modified) : originalValue;
            final DestroyableLabel value = LabelBuilder.builder().withStyleClass("settings-legacy-module-details-label").withNonLocalizedText(modifier.format(currentValue)).build();
            if (Boolean.TRUE.equals(currentValue)) {
                value.getStyleClass().add("settings-legacy-module-details-label-green");
            } else if (Boolean.FALSE.equals(currentValue)) {
                value.getStyleClass().add("settings-legacy-module-details-label-red");
            }
            valuesLine.getChildren().addAll(
                    LabelBuilder.builder().withStyleClass("settings-legacy-module-details-label").withNonLocalizedText(modifier.format(originalAttributeValue)).build()
            );
            if (!originalValue.equals(currentValue)) {
                valuesLine.getChildren().addAll(
                        LabelBuilder.builder().withNonLocalizedText(" → ").build(),
                        value
                );
            }
            checkBox = new CheckBox();
            checkBox.setSelected(currentValue);
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> shipModule.getModifiers().put(modifier, newValue));
//            textField = new TextField(currentValue.toString());
//            textField.textProperty().addListener((observable, oldValue, newValue) -> {
//                textField.getStyleClass().removeAll("settings-legacy-module-valid", "settings-legacy-module-invalid");
//                try {
//                    final double parsedValue = Double.parseDouble(newValue);
//                    textField.getStyleClass().add("settings-legacy-module-valid");
//                    shipModule.getModifiers().put(modifier, parsedValue);
//                } catch (NumberFormatException | NullPointerException e) {
//                    textField.getStyleClass().add("settings-legacy-module-invalid");
//                    shipModule.getModifiers().put(modifier, currentValue);
//                }
//            });
        }

    }

    private static boolean isModifiedAttribute(Double originalAttributeValue, Double attributeValue) {
        return !originalAttributeValue.equals(attributeValue);
    }

}
