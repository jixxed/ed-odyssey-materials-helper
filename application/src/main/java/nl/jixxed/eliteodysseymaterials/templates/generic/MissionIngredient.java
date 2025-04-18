package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class MissionIngredient extends Ingredient {
    private final StorageType storageType;
    private final String localeKey;

    private DestroyableLabel nameLabel;

    public MissionIngredient(final String localeKey, final StorageType storageType) {
        this.storageType = storageType;
        this.localeKey = localeKey;
        initComponents();
    }

    private void initComponents() {
        this.getStyleClass().addAll("ingredient", "mission");
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.localeKey)
                .build();
        DestroyableHBox line = BoxBuilder.builder()
                .withNodes(this.nameLabel)
                .buildHBox();
        this.getNodes().addAll(line, new GrowingRegion());
    }


    @Override
    public StorageType getType() {
        return this.storageType;
    }


    @Override
    public String getName() {
        return this.nameLabel.getText();
    }


}
