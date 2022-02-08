package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

public class HorizonsMaterialCard extends HBox implements Template {

    private ResizableImageView gradeImage;
    private Label nameLabel;
    private Label amountLabel;
    private final HorizonsMaterial material;

    public HorizonsMaterialCard(final HorizonsMaterial material) {
        this.material = material;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("horizons-materialcard-name")
                .withText(LocaleService.getStringBinding(this.material.getLocalizationKey()))
                .build();
        this.amountLabel = LabelBuilder.builder()
                .withStyleClass("horizons-materialcard-amount")
                .withNonLocalizedText(StorageService.getMaterialCount(this.material).toString())
                .build();
        this.getChildren().add(this.gradeImage);
        this.getChildren().add(this.nameLabel);
        this.getChildren().add(this.amountLabel);
    }

    @Override
    public void initEventHandling() {

    }

}
