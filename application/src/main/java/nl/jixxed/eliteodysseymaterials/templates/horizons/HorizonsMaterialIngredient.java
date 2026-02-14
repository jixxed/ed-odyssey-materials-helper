package nl.jixxed.eliteodysseymaterials.templates.horizons;

import javafx.css.PseudoClass;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

//@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class HorizonsMaterialIngredient extends Ingredient implements DestroyableComponent, DestroyableEventTemplate {
    public static final String FILLED = "filled";
    public static final String PARTIAL = "partial";
    //    @EqualsAndHashCode.Include
    private final HorizonsStorageType storageType;
    @Getter
//    @EqualsAndHashCode.Include
    private final HorizonsMaterial horizonsMaterial;
    private final HorizonsBlueprint horizonsBlueprint;
    @Getter
    @Setter
    private Integer minRequired;
    private Integer maxRequired;
    private Integer available;

    private DestroyableLabel nameLabel;
    private EdAwesomeIconViewPane image;
    private DestroyableLabel requiredLabel;
    private DestroyableLabel availableLabel;
    private DestroyableLabel availableTitle;

    public HorizonsMaterialIngredient(final HorizonsBlueprint horizonsBlueprint, final HorizonsStorageType storageType, final HorizonsMaterial horizonsMaterial, final Integer required, final Integer available) {
        if (storageType.equals(HorizonsStorageType.OTHER)) {
            throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.horizonsBlueprint = horizonsBlueprint;
        this.storageType = storageType;
        if (horizonsBlueprint instanceof HorizonsModuleBlueprint) {
            this.minRequired = horizonsBlueprint.getHorizonsBlueprintGrade().getNumberOfRolls(getBestEngineer(horizonsBlueprint), horizonsBlueprint.getHorizonsBlueprintName(), horizonsBlueprint.getHorizonsBlueprintType());
            this.maxRequired = horizonsBlueprint.getHorizonsBlueprintGrade().getNumberOfRolls(getWorstEngineer(horizonsBlueprint), horizonsBlueprint.getHorizonsBlueprintName(), horizonsBlueprint.getHorizonsBlueprintType());
        } else {
            this.minRequired = required;
            this.maxRequired = required;
        }
        this.horizonsMaterial = horizonsMaterial;
        this.available = available;
        initComponents();
        initEventHandling();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, JournalLineProcessedEvent.class, journalProcessedEvent -> this.update()));
        register(EventService.addListener(true, this, StorageEvent.class, evt -> {
            if (evt.getStoragePool().equals(StoragePool.SHIP)) {
                this.update();
            }
        }));
        register(EventService.addListener(true, this, EngineerEvent.class, _ -> {
            if (horizonsBlueprint instanceof HorizonsModuleBlueprint) {
                this.minRequired = horizonsBlueprint.getHorizonsBlueprintGrade().getNumberOfRolls(getBestEngineer(horizonsBlueprint), horizonsBlueprint.getHorizonsBlueprintName(), horizonsBlueprint.getHorizonsBlueprintType());
                this.maxRequired = horizonsBlueprint.getHorizonsBlueprintGrade().getNumberOfRolls(getWorstEngineer(horizonsBlueprint), horizonsBlueprint.getHorizonsBlueprintName(), horizonsBlueprint.getHorizonsBlueprintType());
            }
            String required = Objects.equals(minRequired, maxRequired) ? minRequired.toString() : minRequired + " - " + maxRequired;
            this.requiredLabel.setText(required);
        }));
        register(EventService.addListener(true, this, EngineerPinEvent.class, _ -> {
            this.update();
        }));
    }

    public void initComponents() {
        this.getStyleClass().add("ingredient");
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.horizonsMaterial.getLocalizationKey())
                .build();
        VBox.setVgrow(this.nameLabel, Priority.ALWAYS);
        HBox.setHgrow(this.nameLabel, Priority.ALWAYS);
        initImage();

        this.requiredLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .build();
        this.availableLabel = LabelBuilder.builder()
                .withStyleClass("quantity")
                .build();
        DestroyableLabel requiredTitle = LabelBuilder.builder()
                .withStyleClass("required")
                .withText("blueprint.header.required")
                .build();
        this.availableTitle = LabelBuilder.builder()
                .withStyleClass("available")
                .withText("blueprint.header.available")
                .build();

        DestroyableHBox leftHBox = BoxBuilder.builder()
                .withNodes(requiredTitle, this.requiredLabel)
                .withStyleClass("quantity-section").buildHBox();
        DestroyableHBox rightHBox = BoxBuilder.builder()
                .withNodes(this.availableLabel, this.availableTitle)
                .withStyleClass("quantity-section").buildHBox();
        String required = Objects.equals(minRequired, maxRequired) ? minRequired.toString() : minRequired + " - " + maxRequired;
        this.requiredLabel.setText(required);
        HBox.setHgrow(this.requiredLabel, Priority.ALWAYS);
        this.availableLabel.setText(this.available.toString());

        DestroyableHBox firstLine = BoxBuilder.builder()
                .withStyleClass("first-line")
                .withNodes(this.image, this.nameLabel)
                .buildHBox();
//        this.firstLine.addBinding(this.firstLine.prefHeightProperty(), this.nameLabel.heightProperty());
        DestroyableHBox secondLine = BoxBuilder.builder()
                .withStyleClass("second-line")
                .withNodes(leftHBox, new GrowingRegion(), rightHBox)
                .buildHBox();
        this.getNodes().addAll(firstLine, new GrowingRegion(), secondLine);

        installPopOver();
        update();
        this.requestLayout();
    }

    protected void installPopOver() {
        MaterialService.addMaterialInfoPopOver(this, this.horizonsMaterial, false, () -> this.maxRequired);
    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        if (this.horizonsMaterial instanceof Commodity commodity) {
            this.image = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("ingredient-image")
                    .withIcons(Arrays.stream(commodity.getCommodityType().getIcons()).map(EdAwesomeIconView::new).toArray(EdAwesomeIconView[]::new))
                    .build();
        } else {
            this.image = EdAwesomeIconViewPaneBuilder.builder()
                    .withStyleClass("ingredient-image")
                    .withIcons(new EdAwesomeIconView(this.horizonsMaterial.getRarity().getIcon()))
                    .build();
        }
    }

    protected void setAvailable(final Integer available) {
        this.available = available;
    }


    protected void update() {
        final Integer materialCountShip;
        final Integer materialCountBoth;
        if (this.horizonsMaterial instanceof Commodity commodity) {
            materialCountShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            materialCountBoth = materialCountShip + StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        } else {
            materialCountBoth = StorageService.getMaterialCount(this.horizonsMaterial);
            materialCountShip = materialCountBoth;
        }
        setAvailable(materialCountBoth);
        this.availableLabel.setText(this.available.toString());
        if (materialCountBoth >= this.minRequired && materialCountShip < this.minRequired) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), true);
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(PARTIAL), true);
        } else if (materialCountBoth >= this.minRequired) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), true);
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(PARTIAL), false);
        } else {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(FILLED), false);
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(PARTIAL), false);
        }
    }

    @Override
    public StorageType getType() {
        return this.storageType;
    }

    private Engineer getWorstEngineer(Blueprint<HorizonsBlueprintName> recipe) {
        if (!(recipe instanceof HorizonsModuleBlueprint)) {
            return null;
        }
        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream()
                .min(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng)))
                .orElse(null);

    }

    private Engineer getBestEngineer(Blueprint<HorizonsBlueprintName> recipe) {
        if (!(recipe instanceof HorizonsModuleBlueprint)) {
            return null;
        }
        return ((HorizonsModuleBlueprint) recipe).getEngineers().stream()
                .max(Comparator.comparingInt(eng -> ApplicationState.getInstance().getEngineerRank(eng)))
                .orElse(null);

    }
}
