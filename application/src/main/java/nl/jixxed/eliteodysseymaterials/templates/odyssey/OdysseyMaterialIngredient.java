package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;

import java.util.ArrayList;
import java.util.List;

//@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class OdysseyMaterialIngredient extends Ingredient implements DestroyableEventTemplate {
    public static final String FILLED = "filled";
    //    @EqualsAndHashCode.Include
    private final OdysseyStorageType storageType;
    @Getter
//    @EqualsAndHashCode.Include
    private final OdysseyMaterial odysseyMaterial;
    @Getter
    private final Integer leftAmount;
    private Integer rightAmount;

    private DestroyableLabel nameLabel;
    private EdAwesomeIconViewPane image;
    private DestroyableLabel leftAmountLabel;
    private DestroyableLabel rightAmountLabel;
    private DestroyableLabel rightDescriptionLabel;


    public OdysseyMaterialIngredient(final OdysseyStorageType storageType, final OdysseyMaterial odysseyMaterial, final Integer leftAmount, final Integer rightAmount) {
        if (storageType.equals(OdysseyStorageType.OTHER)) {
            throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.storageType = storageType;
        this.leftAmount = leftAmount;
        this.odysseyMaterial = odysseyMaterial;
        this.rightAmount = rightAmount;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("ingredient");

        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.odysseyMaterial.getLocalizationKey())
                .build();
        initImage();

        this.leftAmountLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .withNonLocalizedText("0")
                .build();
        this.rightAmountLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .withNonLocalizedText("0")
                .build();
        DestroyableLabel leftDescriptionLabel = LabelBuilder.builder()
                .withStyleClass("required")
                .withText("blueprint.header.required")
                .build();
        this.rightDescriptionLabel = LabelBuilder.builder()
                .withStyleClass("available")
                .withText("blueprint.header.available")
                .build();

        DestroyableHBox leftHBox = BoxBuilder.builder()
                .withNodes(leftDescriptionLabel, this.leftAmountLabel)
                .withStyleClass("quantity-section")
                .buildHBox();
        DestroyableHBox rightHBox = BoxBuilder.builder()
                .withNodes(this.rightAmountLabel, this.rightDescriptionLabel)
                .withStyleClass("quantity-section")
                .buildHBox();
        this.leftAmountLabel.setText(this.leftAmount.toString());
        HBox.setHgrow(this.leftAmountLabel, Priority.ALWAYS);
        this.rightAmountLabel.setText(this.rightAmount.toString());

        DestroyableHBox firstLine = BoxBuilder.builder()
                .withNodes(this.image, this.nameLabel)
                .buildHBox();
        DestroyableHBox secondLine = BoxBuilder.builder()
                .withNodes(leftHBox, new GrowingRegion(), rightHBox)
                .buildHBox();
        this.getNodes().addAll(firstLine, new GrowingRegion(), secondLine);

        MaterialService.addMaterialInfoPopOver(this, this.odysseyMaterial, false);

        update();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, JournalLineProcessedEvent.class, _ -> this.update()));
    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        this.image =  EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("ingredient-image")
                .withIcons(getMaterialIcons(this.odysseyMaterial))
                .build();
    }

    private EdAwesomeIcon[] getMaterialIcons(OdysseyMaterial odysseyMaterial) {
        List<EdAwesomeIcon> edAwesomeIcons = new ArrayList<>();
        if (odysseyMaterial.isUnknown()) {
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_DATA);
        } else if (odysseyMaterial instanceof Data) {
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_DATA);
        } else if (odysseyMaterial instanceof Good) {
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_GOOD_1);
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_GOOD_2);
        } else if (odysseyMaterial instanceof Asset asset) {
            switch (asset.getType()) {
                case TECH -> {
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_TECH_1);
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_TECH_2);
                }
                case CIRCUIT -> {
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CIRCUIT_1);
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CIRCUIT_2);
                }
                case CHEMICAL -> {
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CHEMICAL_1);
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CHEMICAL_2);
                }
            }
        }
        return edAwesomeIcons.toArray(EdAwesomeIcon[]::new);
    }


    private void setRightAmount(final Integer rightAmount) {
        this.rightAmount = rightAmount;
    }


    protected void update() {

        setRightAmount(StorageService.getMaterialStorage(this.odysseyMaterial).getTotalValue());
        if (this.rightAmount >= this.leftAmount) {
            this.rightAmountLabel.setText(this.rightAmount.toString());
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), true);
        } else {
            this.rightAmountLabel.setText(this.rightAmount.toString());
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), false);
        }
    }

    @Override
    public StorageType getType() {
        return this.storageType;
    }

    protected Label getLeftAmountLabel() {
        return this.leftAmountLabel;
    }

    protected Integer getRightAmount() {
        return this.rightAmount;
    }

    protected Label getRightAmountLabel() {
        return this.rightAmountLabel;
    }

    protected void setRightDescriptionLabel(final StringBinding rightDescriptionLabel) {
        this.rightDescriptionLabel.addBinding(this.rightDescriptionLabel.textProperty(), rightDescriptionLabel);
    }

}
