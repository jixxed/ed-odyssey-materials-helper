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
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.ArrayList;
import java.util.List;

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
    private EdAwesomeIconViewPane image;
    private DestroyableLabel name;
    private DestroyableLabel squadronCarrierAmount;
    private DestroyableLabel fleetCarrierAmount;
    private DestroyableLabel wishlistAmount;
    private DestroyableLabel backpackAmount;
    private DestroyableLabel shiplockerAmount;
    private DestroyableLabel totalAmount;
    private OdysseyMaterialShow materialShow;
    private EdAwesomeIconViewPane squadronCarrierImage;
    private EdAwesomeIconViewPane fleetCarrierImage;
    private EdAwesomeIconViewPane wishlistImage;
    private EdAwesomeIconViewPane backpackImage;
    private EdAwesomeIconViewPane shipImage;


    @Getter
    private final BooleanProperty showFC = new SimpleBooleanProperty(false);
    private final BooleanProperty showSC = new SimpleBooleanProperty(false);
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
        this.squadronCarrierAmount = LabelBuilder.builder()
                .withStyleClass("fleetcarrier-amount")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(ApplicationState.getInstance().getFcMaterials().and(this.showSC.or(this.hoverProperty())))
                .withManagedProperty(ApplicationState.getInstance().getFcMaterials().and(this.showSC.or(this.hoverProperty())))
                .withNodeOrientation(NodeOrientation.LEFT_TO_RIGHT)
                .withNonLocalizedText(String.valueOf(this.amounts.getSquadronCarrierValue()))
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

        this.image = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("material-image")
                .withIcons(getMaterialIcons(this.odysseyMaterial))
                .build();

        final DestroyableRegion region = new DestroyableRegion();
        HBox.setHgrow(region, Priority.ALWAYS);
        MaterialService.addMaterialInfoPopOver(this, this.odysseyMaterial, false);

        this.setFavourite(this.odysseyMaterial, FavouriteService.isFavourite(this.odysseyMaterial));
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> setFavourite(this.odysseyMaterial, FavouriteService.toggleFavourite(this.odysseyMaterial)));

        this.fleetCarrierImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses("fleetcarrier-image")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())))
                .withManagedProperty(ApplicationState.getInstance().getFcMaterials().and(this.showFC.or(this.hoverProperty())))
                .withIcons(EdAwesomeIcon.OTHER_CARRIER_SIMPLE)
                .build();
        this.squadronCarrierImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses("squadroncarrier-image")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(ApplicationState.getInstance().getFcMaterials().and(this.showSC.or(this.hoverProperty())))
                .withManagedProperty(ApplicationState.getInstance().getFcMaterials().and(this.showSC.or(this.hoverProperty())))
                .withIcons(EdAwesomeIcon.SQUADRON_CARRIER)
                .build();
        this.wishlistImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses("wishlist-image")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showWishlist.or(this.hoverProperty()))
                .withManagedProperty(this.showWishlist.or(this.hoverProperty()))
                .withIcons(EdAwesomeIcon.OTHER_WISHLIST)
                .build();
        this.backpackImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses("backpack-image")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showBackpack.or(this.hoverProperty()))
                .withManagedProperty(this.showBackpack.or(this.hoverProperty()))
                .withIcons(EdAwesomeIcon.ONFOOT_BACKPACK_1, EdAwesomeIcon.ONFOOT_BACKPACK_2)
                .build();
        this.shipImage = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses("ship-image")
                .withVisibility(false)
                .withManaged(false)
                .withVisibilityProperty(this.showShip.or(this.hoverProperty()))
                .withManagedProperty(this.showShip.or(this.hoverProperty()))
                .withIcons(EdAwesomeIcon.SHIPS_SHIP_1, EdAwesomeIcon.SHIPS_SHIP_2)
                .build();

        final DestroyableHBox nameLine = BoxBuilder.builder()
                .withStyleClass("material-name-line")
                .withNodes(this.image, this.name, region, this.wishlistImage, this.wishlistAmount, this.backpackImage, this.backpackAmount, this.shipImage, this.shiplockerAmount, this.fleetCarrierImage, this.fleetCarrierAmount, this.squadronCarrierImage, this.squadronCarrierAmount, this.totalAmount).buildHBox();
        this.getNodes().addAll(nameLine);
        updateStyle();
    }


    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (StoragePool.FLEETCARRIER.equals(storageEvent.getStoragePool())) {
                this.fleetCarrierAmount.setText(String.valueOf(this.amounts.getFleetCarrierValue()));
            } else if (StoragePool.SQUADRONCARRIER.equals(storageEvent.getStoragePool())) {
                this.squadronCarrierAmount.setText(String.valueOf(this.amounts.getSquadronCarrierValue()));
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
    }

    private void updateStyle() {
        updateMaterialCardStyle();
        final boolean isFcFilter = OdysseyMaterialShow.FLEETCARRIER.equals(this.materialShow);
        this.showFC.set(isFcFilter);

        final boolean isScFilter = OdysseyMaterialShow.SQUADRONCARRIER.equals(this.materialShow);
        this.showSC.set(isScFilter);

        final boolean isNotOnWishlistFilter = OdysseyMaterialShow.NOT_ON_WISHLIST.equals(this.materialShow);
        this.showWishlist.set(isNotOnWishlistFilter);

        final boolean isBackpackFilterOrInBackpack = this.amounts.getBackPackValue() > 0 || OdysseyMaterialShow.BACKPACK.equals(this.materialShow);
        this.showBackpack.set(isBackpackFilterOrInBackpack);
    }

    private void updateMaterialCardStyle() {
        this.image.setIcons(getMaterialIcons(this.odysseyMaterial));
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

    private EdAwesomeIcon[] getMaterialIcons(OdysseyMaterial odysseyMaterial) {
        final boolean isEngineerUnlockMaterial = (APPLICATION_STATE.getSoloMode()) ? OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(odysseyMaterial) : OdysseyBlueprintConstants.isEngineeringIngredient(odysseyMaterial);

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
        if (isEngineerUnlockMaterial) {
            edAwesomeIcons.add(EdAwesomeIcon.SHIPS_ENGINEER);
        }else if (odysseyMaterial.isPowerplay()) {
            edAwesomeIcons.add(EdAwesomeIcon.OTHER_POWERPLAY_OPEN);
        }
        return edAwesomeIcons.toArray(EdAwesomeIcon[]::new);
    }

    private void setFavourite(final OdysseyMaterial odysseyMaterial, final boolean isFavourite) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass(MATERIAL_FAVOURITE_CLASS), isFavourite);
        this.name.addBinding(this.name.textProperty(), LocaleService.getStringBinding(odysseyMaterial));
    }
}
