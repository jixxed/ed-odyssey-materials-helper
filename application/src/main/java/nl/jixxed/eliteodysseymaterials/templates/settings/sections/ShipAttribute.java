package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.CheckBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextFieldBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableCheckBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTextField;

//todo refactor this into a component instead of a container
public class ShipAttribute {
    @Getter
    private DestroyableLabel title;
    @Getter
    private DestroyableHBox valuesLine;
    @Getter
    private DestroyableTextField textField;
    @Getter
    private DestroyableCheckBox checkBox;

    public ShipAttribute(ShipModule shipModule, HorizonsModifier modifier) {
        addAttribute(shipModule, modifier);
    }

    private void addAttribute(ShipModule shipModule, HorizonsModifier modifier) {
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
            textField = TextFieldBuilder.builder().withNonLocalizedText(currentValue.toString())
                    .withTextProperty((observable, oldValue, newValue) -> {
                        textField.getStyleClass().removeAll("settings-legacy-module-valid", "settings-legacy-module-invalid");
                        try {
                            final double parsedValue = Double.parseDouble(newValue);
                            textField.getStyleClass().add("settings-legacy-module-valid");
                            shipModule.getModifiers().put(modifier, parsedValue);
                        } catch (NumberFormatException | NullPointerException e) {
                            textField.getStyleClass().add("settings-legacy-module-invalid");
                            shipModule.getModifiers().put(modifier, currentValue);
                        }
                    })
                    .build();
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

            checkBox = CheckBoxBuilder.builder()
                    .withSelected(currentValue)
                    .withSelectedProperty((_, _, newValue) -> shipModule.getModifiers().put(modifier, newValue))
                    .build();
        }

    }

    private static boolean isModifiedAttribute(Double originalAttributeValue, Double attributeValue) {
        return !originalAttributeValue.equals(attributeValue);
    }

}
