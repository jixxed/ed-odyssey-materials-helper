package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.enums.Equipment;
import nl.jixxed.eliteodysseymaterials.enums.EquipmentLevel;

public class LoadoutItem extends VBox implements Template {
    private final Equipment equipment;

    LoadoutItem(final Equipment equipment) {
        this.equipment = equipment;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {

    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-item");
        this.setSpacing(5);
        //image
        final ResizableImageView image = ResizableImageViewBuilder.builder().withStyleClass("loadout-item-image").withImage(this.equipment.getImage()).build();
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
        final HBox modifications = BoxBuilder.builder().withNodes(new LoadoutModification(this.equipment), createRegion(), new LoadoutModification(this.equipment), createRegion(), new LoadoutModification(this.equipment), createRegion(), new LoadoutModification(this.equipment)).buildHBox();
        //stats
        //button
        final Button addToWishlist = ButtonBuilder.builder().withNonLocalizedText("ADD TO WISHLIST").build();


        this.getChildren().addAll(image, title, currentLevelBox, targetLevelBox, modifications, addToWishlist);
    }

    private Region createRegion() {
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }
}
