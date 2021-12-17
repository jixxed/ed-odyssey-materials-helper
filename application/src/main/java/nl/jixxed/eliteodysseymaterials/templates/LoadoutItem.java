package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.EquipmentLevel;

public class LoadoutItem extends VBox implements Template {
    LoadoutItem() {

        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {

    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-item");
        //image
        //title
        final Label title = LabelBuilder.builder().withStyleClass("loadout-item-title").withNonLocalizedText("TITLE").build();
        //current level
        final Label currentLevelLabel = LabelBuilder.builder().withStyleClass("loadout-item-level-label").withNonLocalizedText("CURRENT LEVEL").build();
        final ComboBox<EquipmentLevel> currentLevel = ComboBoxBuilder.builder(EquipmentLevel.class).build();
        final Region regionCurrent = new Region();
        HBox.setHgrow(regionCurrent, Priority.ALWAYS);
        final HBox currentLevelBox = BoxBuilder.builder().withNodes(currentLevelLabel, regionCurrent, currentLevel).buildHBox();
        //target level
        final Label targetLevelLabel = LabelBuilder.builder().withStyleClass("loadout-item-level-label").withNonLocalizedText("TARGET LEVEL").build();
        final ComboBox<EquipmentLevel> targetLevel = ComboBoxBuilder.builder(EquipmentLevel.class).build();
        final Region regionTarget = new Region();
        HBox.setHgrow(regionTarget, Priority.ALWAYS);
        final HBox targetLevelBox = BoxBuilder.builder().withNodes(targetLevelLabel, regionTarget, targetLevel).buildHBox();
        //modifications
        //stats
        //button


        this.getChildren().addAll(title, currentLevelBox, targetLevelBox);
    }
}
