package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.FavouriteService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

@Slf4j
class OdysseyMaterialCard extends VBox implements DestroyableTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MATERIAL_IRRELEVANT_CLASS = "material-irrelevant";
    private static final String MATERIAL_POWERPLAY_CLASS = "material-powerplay";
    private static final String MATERIAL_RELEVANT_CLASS = "material-relevant";
    private static final String MATERIAL_FAVOURITE_CLASS = "material-favourite";
    private static final String MATERIAL_SPECIFIC_CLASS_PREFIX = "material-";

    private final OdysseyMaterial odysseyMaterial;
    private final Storage amounts;
    private DestroyableResizableImageView image;
    private Label name;
    private Label fleetCarrierAmount;
    private Label wishlistAmount;
    private Label backpackAmount;
    private Label shiplockerAmount;
    private Label totalAmount;
    private OdysseyMaterialShow materialShow;
    private DestroyableResizableImageView fleetCarrierImage;
    private DestroyableResizableImageView wishlistImage;
    private DestroyableResizableImageView backpackImage;
    private DestroyableResizableImageView shipImage;
//    private DestroyableResizableImageView totalImage;


    @Getter
    private final BooleanProperty showFC = new SimpleBooleanProperty(false);
    OdysseyMaterialCard(final OdysseyMaterial odysseyMaterial) {
        this.odysseyMaterial = odysseyMaterial;
        this.amounts = StorageService.getMaterialStorage(odysseyMaterial);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("material");
        this.name = LabelBuilder.builder()
                .withStyleClass("materialcard-name")
                .withText(LocaleService.getStringBinding(this.odysseyMaterial))
                .build();
        this.fleetCarrierAmount = LabelBuilder.builder()
                .withStyleClass("materialcard-fcamount")
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(this.amounts.getFleetCarrierValue()))
                .build();
        this.wishlistAmount = LabelBuilder.builder()
                .withStyleClass("materialcard-amount")
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.odysseyMaterial)).mapToInt(Integer::intValue).sum()))
                .build();
        this.backpackAmount = LabelBuilder.builder()
                .withStyleClass("materialcard-amount")
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(this.amounts.getBackPackValue()))
                .build();
        this.shiplockerAmount = LabelBuilder.builder()
                .withStyleClass("materialcard-amount")
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(this.amounts.getShipLockerValue()))
                .build();
        this.totalAmount = LabelBuilder.builder()
                .withStyleClass("materialcard-total")
                .withNodeOrientation(NodeOrientation.RIGHT_TO_LEFT)
                .withNonLocalizedText(String.valueOf(this.amounts.getTotalValue()))
                .build();

        this.image = createMaterialImage(this.odysseyMaterial);

        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        MaterialService.addMaterialInfoPopOver(this, this.odysseyMaterial, false);

        this.setFavourite(this.odysseyMaterial, FavouriteService.isFavourite(this.odysseyMaterial));
        this.setOnMouseClicked(event -> setFavourite(this.odysseyMaterial, FavouriteService.toggleFavourite(this.odysseyMaterial)));

        this.fleetCarrierImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image", "materialcard-fcamount-image").withImage("/images/material/fleetcarrier.png").build();
        this.wishlistImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image", "materialcard-amount-image").withImage("/images/material/wishlist.png").build();
        this.backpackImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image", "materialcard-amount-image").withImage("/images/material/backpack.png").build();
        this.shipImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image", "materialcard-amount-image").withImage("/images/material/ship.png").build();

        this.fleetCarrierImage.visibleProperty().bind(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())));
        this.fleetCarrierAmount.visibleProperty().bind(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())));
        final HBox nameLine = BoxBuilder.builder().withStyleClass("material-name-line").withNodes(this.image, this.name, region, this.wishlistImage, this.wishlistAmount, this.backpackImage, this.backpackAmount, this.shipImage, this.shiplockerAmount, this.fleetCarrierImage, this.fleetCarrierAmount, this.totalAmount).buildHBox();
        this.getChildren().addAll(nameLine);
        updateStyle();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, event -> {
            this.fleetCarrierAmount.setText(String.valueOf(0));
            this.backpackAmount.setText(String.valueOf(0));
            this.shiplockerAmount.setText(String.valueOf(0));
            this.wishlistAmount.setText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.odysseyMaterial)).mapToInt(Integer::intValue).sum()));
            this.totalAmount.setText(String.valueOf(0));
        }));
        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (StoragePool.FLEETCARRIER.equals(storageEvent.getStoragePool())) {
                this.fleetCarrierAmount.setText(String.valueOf(this.amounts.getFleetCarrierValue()));
            } else if (StoragePool.BACKPACK.equals(storageEvent.getStoragePool())) {
                this.backpackAmount.setText(String.valueOf(this.amounts.getBackPackValue()));
            } else if (StoragePool.SHIPLOCKER.equals(storageEvent.getStoragePool())) {
                this.shiplockerAmount.setText(String.valueOf(this.amounts.getShipLockerValue()));
            }
            this.totalAmount.setText(String.valueOf(this.amounts.getTotalValue()));
            Platform.runLater(this::updateStyle);
        }));

        register(EventService.addListener(true, this, SearchEvent.class, searchEvent -> {
            this.materialShow = searchEvent.getSearch().getMaterialShow();
            Platform.runLater(this::updateStyle);
        }));

        register(EventService.addListener(true, this, IrrelevantMaterialOverrideEvent.class, event ->
                Platform.runLater(this::updateMaterialCardStyle)
        ));
        register(EventService.addListener(true, this, 9, WishlistBlueprintEvent.class, event -> {
            Platform.runLater(() -> this.wishlistAmount.setText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.odysseyMaterial)).mapToInt(Integer::intValue).sum())));
            Platform.runLater(this::updateStyle);
        }));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent ->
                Platform.runLater(this::updateMaterialCardStyle)
        ));
    }

    private void updateStyle() {
        updateMaterialCardStyle();
        this.backpackImage.getStyleClass().removeAll("materialcard-amount-image-nonzero");
        this.backpackAmount.getStyleClass().removeAll("materialcard-amount-nonzero");
        this.fleetCarrierImage.getStyleClass().removeAll("materialcard-fcamount-image-nonzero");
        this.fleetCarrierAmount.getStyleClass().removeAll("materialcard-fcamount-nonzero");
        this.showFC.set(false);
        this.wishlistImage.getStyleClass().removeAll("materialcard-amount-image-nonzero");
        this.wishlistAmount.getStyleClass().removeAll("materialcard-amount-nonzero");
        if (OdysseyMaterialShow.FLEETCARRIER.equals(this.materialShow)) {
            this.showFC.set(true);
            this.fleetCarrierImage.getStyleClass().add("materialcard-fcamount-image-nonzero");
            this.fleetCarrierAmount.getStyleClass().add("materialcard-fcamount-nonzero");
        }
        if (OdysseyMaterialShow.NOT_ON_WISHLIST.equals(this.materialShow)) {
            this.wishlistImage.getStyleClass().add("materialcard-amount-image-nonzero");
            this.wishlistAmount.getStyleClass().add("materialcard-amount-nonzero");
        }
        if (this.amounts.getBackPackValue() > 0 || OdysseyMaterialShow.BACKPACK.equals(this.materialShow)) {
            this.backpackImage.getStyleClass().add("materialcard-amount-image-nonzero");
            this.backpackAmount.getStyleClass().add("materialcard-amount-nonzero");
        }
    }

    private void updateMaterialCardStyle() {
        this.image = createMaterialImage(this.odysseyMaterial);
        this.getStyleClass().removeAll(MATERIAL_IRRELEVANT_CLASS, MATERIAL_RELEVANT_CLASS, MATERIAL_POWERPLAY_CLASS);
        this.setFavourite(this.odysseyMaterial, FavouriteService.isFavourite(this.odysseyMaterial));
        if (this.odysseyMaterial.isUnknown()) {
            this.getStyleClass().addAll(MATERIAL_IRRELEVANT_CLASS);
        } else if (this.odysseyMaterial instanceof Asset) {
            this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS);
        } else if (OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS);
        } else if (APPLICATION_STATE.getSoloMode() && OdysseyBlueprintConstants.isEngineeringOnlyIngredient(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_IRRELEVANT_CLASS);
        } else if (OdysseyBlueprintConstants.isEngineeringIngredient(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS);
        } else if (OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS);
        } else if (this.odysseyMaterial.isPowerplay()) {
            this.getStyleClass().addAll(MATERIAL_POWERPLAY_CLASS);
        } else {
            this.getStyleClass().addAll(MATERIAL_IRRELEVANT_CLASS);
        }
    }

    private DestroyableResizableImageView createMaterialImage(final OdysseyMaterial odysseyMaterial) {

        final boolean isEngineerUnlockMaterial = (APPLICATION_STATE.getSoloMode()) ? OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(odysseyMaterial) : OdysseyBlueprintConstants.isEngineeringIngredient(odysseyMaterial);
        ResizableImageViewBuilder imageViewBuilder = ResizableImageViewBuilder.builder().withStyleClass("materialcard-image");
        if (odysseyMaterial.isUnknown()) {
            imageViewBuilder.withImage("/images/material/unknown.png");
        } else if (isEngineerUnlockMaterial) {
            imageViewBuilder = imageViewBuilder.withImage("/images/material/engineer.png");
        } else if (odysseyMaterial instanceof Data) {
            imageViewBuilder = imageViewBuilder.withImage("/images/material/data.png");
        } else if (odysseyMaterial instanceof Good) {
            imageViewBuilder = imageViewBuilder.withImage("/images/material/good.png");
        } else if (odysseyMaterial instanceof Asset asset) {
            imageViewBuilder = switch (asset.getType()) {
                case TECH -> imageViewBuilder.withImage("/images/material/tech.png");
                case CIRCUIT -> imageViewBuilder.withImage("/images/material/circuit.png");
                case CHEMICAL -> imageViewBuilder.withImage("/images/material/chemical.png");
            };
        }
        return imageViewBuilder.build();
    }

    private void setFavourite(final OdysseyMaterial odysseyMaterial, final boolean isFavourite) {
        if (isFavourite) {
            if (!this.getStyleClass().contains(MATERIAL_FAVOURITE_CLASS)) {
                this.getStyleClass().add(MATERIAL_FAVOURITE_CLASS);
            }
        } else {
            this.getStyleClass().remove(MATERIAL_FAVOURITE_CLASS);
        }
        this.name.textProperty().bind(LocaleService.getStringBinding(odysseyMaterial));
    }
}
