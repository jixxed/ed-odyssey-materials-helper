package nl.jixxed.eliteodysseymaterials.templates.generic.menu;

import javafx.css.PseudoClass;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class Modifier extends DestroyableHBox implements DestroyableTemplate {

    private final String modifierKey;
    private final String value;
    private final boolean isPositive;

    public Modifier(String modifierKey, String value, boolean isPositive) {
        this.modifierKey = modifierKey;
        this.value = value;
        this.isPositive = isPositive;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("modifier");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("positive"), isPositive);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("negative"), !isPositive);
        final DestroyableLabel modifierLabel = LabelBuilder.builder()
                .withStyleClasses("name")
                .withText(modifierKey)
                .build();
        final DestroyableLabel valueLabel = LabelBuilder.builder()
                .withStyleClasses("value")
                .withNonLocalizedText(value)
                .build();
        HBox.setHgrow(valueLabel, Priority.ALWAYS);

        this.getNodes().addAll(modifierLabel, valueLabel);
    }
}
