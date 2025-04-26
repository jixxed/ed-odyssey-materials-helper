package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.geometry.NodeOrientation;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

@Slf4j
public class OdysseyMaterialCard extends DestroyableVBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MATERIAL_IRRELEVANT_CLASS = "irrelevant";
    private static final String MATERIAL_POWERPLAY_CLASS = "powerplay";
    private static final String MATERIAL_RELEVANT_CLASS = "relevant";
    private static final String MATERIAL_FAVOURITE_CLASS = "favourite";
    @Getter
    private final OdysseyMaterial odysseyMaterial;
    private final Storage amounts;
    private DestroyableResizableImageView image;
    private DestroyableLabel name;
    private DestroyableLabel fleetCarrierAmount;
    private DestroyableLabel wishlistAmount;
    private DestroyableLabel backpackAmount;
    private DestroyableLabel shiplockerAmount;
    private DestroyableLabel totalAmount;
    private OdysseyMaterialShow materialShow;
    private DestroyableResizableImageView fleetCarrierImage;
    private DestroyableResizableImageView wishlistImage;
    private DestroyableResizableImageView backpackImage;
    private DestroyableResizableImageView shipImage;


    @Getter
    private final BooleanProperty showFC = new SimpleBooleanProperty(false);
    private final BooleanProperty showWishlist = new SimpleBooleanProperty(false);
    private final BooleanProperty showBackpack = new SimpleBooleanProperty(false);
    private final BooleanProperty showShip = new SimpleBooleanProperty(false);

    OdysseyMaterialCard(final OdysseyMaterial odysseyMaterial) {
        this.odysseyMaterial = odysseyMaterial;
        this.amounts = StorageService.getMaterialStorage(odysseyMaterial);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("material-card");
        this.name = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.odysseyMaterial.getLocalizationKey())
                .build();
        this.fleetCarrierAmount = LabelBuilder.builder()
                .withStyleClass("fleetcarrier-amount")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())))
                .withManagedProperty(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())))
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(this.amounts.getFleetCarrierValue()))
                .build();
        this.wishlistAmount = LabelBuilder.builder()
                .withStyleClass("wishlist-amount")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showWishlist.or(this.hoverProperty()))
                .withManagedProperty(this.showWishlist.or(this.hoverProperty()))
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.odysseyMaterial)).mapToInt(Integer::intValue).sum()))
                .build();
        this.backpackAmount = LabelBuilder.builder()
                .withStyleClass("backpack-amount")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showBackpack.or(this.hoverProperty()))
                .withManagedProperty(this.showBackpack.or(this.hoverProperty()))
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(this.amounts.getBackPackValue()))
                .build();
        this.shiplockerAmount = LabelBuilder.builder()
                .withStyleClass("ship-amount")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showShip.or(this.hoverProperty()))
                .withManagedProperty(this.showShip.or(this.hoverProperty()))
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(this.amounts.getShipLockerValue()))
                .build();
        this.totalAmount = LabelBuilder.builder()
                .withStyleClass("total-amount")
                .withNodeOrientation(NodeOrientation.RIGHT_TO_LEFT)
                .withNonLocalizedText(String.valueOf(this.amounts.getTotalValue()))
                .build();

        this.image = ResizableImageViewBuilder.builder()
                .withStyleClass("material-image")
                .withImage(getMaterialImage(this.odysseyMaterial))
                .build();


        final DestroyableRegion region = new DestroyableRegion();
        HBox.setHgrow(region, Priority.ALWAYS);
        MaterialService.addMaterialInfoPopOver(this, this.odysseyMaterial, false);

        this.setFavourite(this.odysseyMaterial, FavouriteService.isFavourite(this.odysseyMaterial));
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> setFavourite(this.odysseyMaterial, FavouriteService.toggleFavourite(this.odysseyMaterial)));

        this.fleetCarrierImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("fleetcarrier-image")
                .withImage("/images/material/fleetcarrier.png")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())))
                .withManagedProperty(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())))
                .build();
        this.wishlistImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("wishlist-image")
                .withImage("/images/material/wishlist.png")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showWishlist.or(this.hoverProperty()))
                .withManagedProperty(this.showWishlist.or(this.hoverProperty()))
                .build();
        this.backpackImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("backpack-image")
                .withImage("/images/material/backpack.png")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showBackpack.or(this.hoverProperty()))
                .withManagedProperty(this.showBackpack.or(this.hoverProperty()))
                .build();
        this.shipImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("ship-image")
                .withImage("/images/material/ship.png")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showShip.or(this.hoverProperty()))
                .withManagedProperty(this.showShip.or(this.hoverProperty()))
                .build();

        final DestroyableHBox nameLine = BoxBuilder.builder()
                .withStyleClass("material-name-line")
                .withNodes(this.image, this.name, region, this.wishlistImage, this.wishlistAmount, this.backpackImage, this.backpackAmount, this.shipImage, this.shiplockerAmount, this.fleetCarrierImage, this.fleetCarrierAmount, this.totalAmount).buildHBox();
        this.getNodes().addAll(nameLine);
        updateStyle();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, _ -> {
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

        register(EventService.addListener(true, this, IrrelevantMaterialOverrideEvent.class, _ ->
                Platform.runLater(this::updateMaterialCardStyle)
        ));
        register(EventService.addListener(true, this, 9, OdysseyWishlistBlueprintEvent.class, _ -> {
            Platform.runLater(() -> this.wishlistAmount.setText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.odysseyMaterial)).mapToInt(Integer::intValue).sum())));
            Platform.runLater(this::updateStyle);
        }));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, _ ->
                Platform.runLater(this::updateMaterialCardStyle)
        ));
    }

    private void updateStyle() {
        updateMaterialCardStyle();
        final boolean isFcFilter = OdysseyMaterialShow.FLEETCARRIER.equals(this.materialShow);
        this.showFC.set(isFcFilter);

        final boolean isNotOnWishlistFilter = OdysseyMaterialShow.NOT_ON_WISHLIST.equals(this.materialShow);
        this.showWishlist.set(isNotOnWishlistFilter);

        final boolean isBackpackFilterOrInBackpack = this.amounts.getBackPackValue() > 0 || OdysseyMaterialShow.BACKPACK.equals(this.materialShow);
        this.showBackpack.set(isBackpackFilterOrInBackpack);
    }

    private void updateMaterialCardStyle() {
        this.image.setImage(ImageService.getImage(getMaterialImage(this.odysseyMaterial)));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_IRRELEVANT_CLASS), false);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_RELEVANT_CLASS), false);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_POWERPLAY_CLASS), false);
        this.setFavourite(this.odysseyMaterial, FavouriteService.isFavourite(this.odysseyMaterial));
        if (this.odysseyMaterial.isUnknown()) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_IRRELEVANT_CLASS), true);
        } else if (this.odysseyMaterial instanceof Asset) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_RELEVANT_CLASS), true);
        } else if (OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(this.odysseyMaterial)) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_RELEVANT_CLASS), true);
        } else if (APPLICATION_STATE.getSoloMode() && OdysseyBlueprintConstants.isEngineeringOnlyIngredient(this.odysseyMaterial)) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_IRRELEVANT_CLASS), true);
        } else if (OdysseyBlueprintConstants.isEngineeringIngredient(this.odysseyMaterial)) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_RELEVANT_CLASS), true);
        } else if (OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(this.odysseyMaterial)) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_RELEVANT_CLASS), true);
        } else if (this.odysseyMaterial.isPowerplay()) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_POWERPLAY_CLASS), true);
        } else {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_IRRELEVANT_CLASS), true);
        }
    }

    private String getMaterialImage(final OdysseyMaterial odysseyMaterial) {

        final boolean isEngineerUnlockMaterial = (APPLICATION_STATE.getSoloMode()) ? OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(odysseyMaterial) : OdysseyBlueprintConstants.isEngineeringIngredient(odysseyMaterial);

        if (odysseyMaterial.isUnknown()) {
            return "/images/material/unknown.png";
        } else if (isEngineerUnlockMaterial) {
            return "/images/material/engineer.png";
        } else if (odysseyMaterial instanceof Data) {
            return "/images/material/data.png";
        } else if (odysseyMaterial instanceof Good) {
            return "/images/material/good.png";
        } else if (odysseyMaterial instanceof Asset asset) {
            return switch (asset.getType()) {
                case TECH -> "/images/material/tech.png";
                case CIRCUIT -> "/images/material/circuit.png";
                case CHEMICAL -> "/images/material/chemical.png";
            };
        }
        throw new IllegalArgumentException("Not a valid material");
    }

    private void setFavourite(final OdysseyMaterial odysseyMaterial, final boolean isFavourite) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_FAVOURITE_CLASS), isFavourite);
        this.name.addBinding(this.name.textProperty(), LocaleService.getStringBinding(odysseyMaterial));
    }
}
