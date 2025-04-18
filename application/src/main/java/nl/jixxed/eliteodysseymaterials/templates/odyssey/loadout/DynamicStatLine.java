package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.domain.SelectedModification;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicStatLine extends DestroyableHBox implements DestroyableTemplate {
    private final DynamicStat dynamicStat;
    private DestroyableLabel targetValue;
    private DestroyableLabel moddedValue;
    private DestroyableLabel currentValue;
    LoadoutSet loadoutSet;
    Loadout loadout;

    public DynamicStatLine(DynamicStat dynamicStat, LoadoutSet loadoutSet, Loadout loadout) {
        this.dynamicStat = dynamicStat;
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        initComponents();
        update();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("dynamic-stat");
        final DestroyableLabel nameColumn = LabelBuilder.builder()
                .withStyleClasses("name")
                .withText(dynamicStat.getLocalizationKey())
                .build();
        nameColumn.pseudoClassStateChanged(PseudoClass.getPseudoClass("wide"), Suit.FLIGHTSUIT.equals(loadout.getEquipment()));
        currentValue = LabelBuilder.builder()
                .withStyleClasses("value")
                .withNonLocalizedText("0")
                .build();
        targetValue = LabelBuilder.builder()
                .withStyleClasses("value")
                .withNonLocalizedText("0")
                .build();
        moddedValue = LabelBuilder.builder()
                .withStyleClasses("value")//todo make it wider?
                .withNonLocalizedText("0")
                .build();
        this.getNodes().addAll(nameColumn, currentValue, targetValue, moddedValue);
        update();
    }

    public void update() {
        final Object value = loadout.getEquipment().getStats().get(dynamicStat);
        final String currentLevelValue = dynamicStat.formatValue(value, loadout.getEquipment(), loadout.getCurrentLevel());
        final String targetLevelValue = dynamicStat.formatValue(value, loadout.getEquipment(), loadout.getTargetLevel());
        final List<SelectedModification> selectedModifications = new ArrayList<>(Arrays.stream(loadout.getModifications()).filter(mod -> mod.getModification() != null).toList());
        if (loadout.getEquipment() instanceof Weapon && loadoutSet.getLoadouts().stream().filter(loadoutItem -> loadoutItem.getEquipment() instanceof Suit)
                .anyMatch(loadoutItem -> Arrays.stream(loadoutItem.getModifications())
                        .map(SelectedModification::getModification)
                        .anyMatch(SuitModification.EXTRA_AMMO_CAPACITY::equals))) {
            selectedModifications.add(new SelectedModification(SuitModification.EXTRA_AMMO_CAPACITY, false));
        }
        final List<Modification> modifications = selectedModifications.stream().map(SelectedModification::getModification).toList();
        final String moddedLevelValue = dynamicStat.formatValue(value, loadout.getEquipment(), loadout.getTargetLevel(), modifications);
        this.currentValue.setText(currentLevelValue);
        this.targetValue.setText(targetLevelValue);
        this.moddedValue.setText(moddedLevelValue);
        updateStyle();
        this.setVisible(!loadout.isShowChanged() || isChanged());
        this.setManaged(!loadout.isShowChanged() || isChanged());
    }

    private void updateStyle() {
        targetValue.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !this.currentValue.getText().equals(this.targetValue.getText()));
        moddedValue.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !this.currentValue.getText().equals(this.moddedValue.getText()));
        moddedValue.pseudoClassStateChanged(PseudoClass.getPseudoClass("modded"), !this.targetValue.getText().equals(this.moddedValue.getText()));
    }

    public boolean isChanged() {
        return !this.currentValue.getText().equals(this.moddedValue.getText());
    }
}