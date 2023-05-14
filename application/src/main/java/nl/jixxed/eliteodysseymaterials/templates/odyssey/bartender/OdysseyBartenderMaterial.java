package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.List;

public class OdysseyBartenderMaterial extends HBox implements Template {
    @Getter
    private final Asset asset;
    private int amountSelected = 0;
    @Getter
    ObjectProperty<LayoutMode> layoutMode = new SimpleObjectProperty<>(LayoutMode.DEFAULT);
    private DestroyableLabel amountSelectedLabel;
    private DestroyableLabel amountSelectedValueLabel;
    private DestroyableLabel buyValueLabel;
    private DestroyableLabel sellValueLabel;
    private DestroyableLabel nameLabel;
    private DestroyableLabel decreaseLabel;
    private DestroyableLabel decreaseTenLabel;
    private DestroyableLabel amountLabel;
    private DestroyableLabel increaseLabel;
    private DestroyableLabel increaseTenLabel;
    private DestroyableResizableImageView fleetCarrierImage;
    private DestroyableResizableImageView wishlistImage;
    private DestroyableResizableImageView backpackImage;
    private DestroyableResizableImageView shipImage;
    private DestroyableResizableImageView imageView;
    private DestroyableLabel wishlistLabel;
    private DestroyableLabel backpackLabel;
    private DestroyableLabel shipLabel;
    private DestroyableLabel fleetCarrierLabel;
    private EventHandler<MouseEvent> mouseEventEventHandler;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    OdysseyBartenderMaterial(final Asset asset) {
        this.asset = asset;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bartender-material");
        // amount selected
        this.amountSelectedLabel = LabelBuilder.builder().withStyleClass("bartender-material-amount-selected").withNonLocalizedText("Sell").build();
        this.amountSelectedValueLabel = LabelBuilder.builder().withStyleClass("bartender-material-amount-selected-value").withNonLocalizedText("").build();
        this.amountSelectedLabel.visibleProperty().bind(this.amountSelectedValueLabel.textProperty().isNotEqualTo(""));
        // image
        this.imageView = createMaterialImage();
        // value
        this.buyValueLabel = LabelBuilder.builder().withStyleClass("bartender-material-value").withText(LocaleService.getStringBinding("bartender.buy.value", String.valueOf(this.asset.getBuyValue()))).build();
        this.sellValueLabel = LabelBuilder.builder().withStyleClass("bartender-material-value").withText(LocaleService.getStringBinding("bartender.sell.value", String.valueOf(this.asset.getSellValue()))).build();
        this.buyValueLabel.disableProperty().bind(this.layoutMode.isEqualTo(LayoutMode.TRADE));
        this.sellValueLabel.disableProperty().bind(this.layoutMode.isEqualTo(LayoutMode.SELECTED));
        // name
        this.nameLabel = LabelBuilder.builder().withStyleClass("bartender-material-name").withText(LocaleService.getStringBinding(this.asset.getLocalizationKey())).build();
        // name
        this.fleetCarrierImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image").withImage("/images/material/fleetcarrier.png").build();
        this.wishlistImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image").withImage("/images/material/wishlist.png").build();
        this.backpackImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image").withImage("/images/material/backpack.png").build();
        this.shipImage = ResizableImageViewBuilder.builder().withStyleClasses("materialcard-image").withImage("/images/material/ship.png").build();
        this.wishlistLabel = LabelBuilder.builder().withStyleClass("bartender-material-wishlist").withNonLocalizedText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.asset)).mapToInt(Integer::intValue).sum())).build();
        this.backpackLabel = LabelBuilder.builder().withStyleClass("bartender-material-wishlist").withNonLocalizedText(String.valueOf(StorageService.getMaterialStorage(this.asset).getBackPackValue())).build();
        this.shipLabel = LabelBuilder.builder().withStyleClass("bartender-material-wishlist").withNonLocalizedText(String.valueOf(StorageService.getMaterialStorage(this.asset).getShipLockerValue())).build();
        this.fleetCarrierLabel = LabelBuilder.builder().withStyleClass("bartender-material-wishlist").withNonLocalizedText(String.valueOf(StorageService.getMaterialStorage(this.asset).getFleetCarrierValue())).build();
        // decrease
        this.decreaseLabel = LabelBuilder.builder().withStyleClass("bartender-material-button").withNonLocalizedText("-").withOnMouseClicked(event -> {
            if (this.amountSelected > 0) {
                this.amountSelected--;
                EventService.publish(new OdysseyBartenderAmountSelectedEvent(this.asset, this.amountSelected));
                this.amountSelectedValueLabel.setText(this.amountSelected > 0 ? String.valueOf(this.amountSelected) : "");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
                updateStyle();
            }
        }).build();
        // decrease 10
        this.decreaseTenLabel = LabelBuilder.builder().withStyleClass("bartender-material-button").withNonLocalizedText("--").withOnMouseClicked(event -> {
            if (this.amountSelected > 0) {
                this.amountSelected -= (this.amountSelected >= 10) ? 10 : this.amountSelected;
                EventService.publish(new OdysseyBartenderAmountSelectedEvent(this.asset, this.amountSelected));
                this.amountSelectedValueLabel.setText(this.amountSelected > 0 ? String.valueOf(this.amountSelected) : "");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
                updateStyle();
            }
        }).build();
        // amount
        this.amountLabel = LabelBuilder.builder().withStyleClass("bartender-material-amount").withNonLocalizedText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue())).build();
        // increase
        this.increaseLabel = LabelBuilder.builder().withStyleClass("bartender-material-button").withNonLocalizedText("+").withOnMouseClicked(event -> {
            if (this.amountSelected < StorageService.getMaterialStorage(this.asset).getTotalValue()) {
                this.amountSelected++;
                EventService.publish(new OdysseyBartenderAmountSelectedEvent(this.asset, this.amountSelected));
                this.amountSelectedValueLabel.setText(this.amountSelected > 0 ? String.valueOf(this.amountSelected) : "");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
                updateStyle();
            }
        }).build();
        // increase 10
        this.increaseTenLabel = LabelBuilder.builder().withStyleClass("bartender-material-button").withNonLocalizedText("++").withOnMouseClicked(event -> {
            if (this.amountSelected < StorageService.getMaterialStorage(this.asset).getTotalValue()) {
                this.amountSelected += (this.amountSelected <= StorageService.getMaterialStorage(this.asset).getTotalValue() - 10) ? 10 : StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected;
                EventService.publish(new OdysseyBartenderAmountSelectedEvent(this.asset, this.amountSelected));
                this.amountSelectedValueLabel.setText(this.amountSelected > 0 ? String.valueOf(this.amountSelected) : "");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
                updateStyle();
            }
        }).build();
        final VBox left = BoxBuilder.builder().withNodes(
                BoxBuilder.builder().withStyleClass("bartender-material-line-image").withNodes(
                        this.imageView
                ).buildHBox()

        ).buildVBox();

        final VBox left2 = BoxBuilder.builder().withStyleClass("bartender-material-line").withNodes(
                this.nameLabel,
                BoxBuilder.builder().withStyleClass("bartender-material-line").withNodes(this.buyValueLabel, this.sellValueLabel).buildHBox()
        ).buildVBox();
        final VBox middle = BoxBuilder.builder().withNodes(
                BoxBuilder.builder().withStyleClass("bartender-material-line").withNodes(this.backpackImage, this.backpackLabel, this.shipImage, this.shipLabel).buildHBox(),
                BoxBuilder.builder().withStyleClass("bartender-material-line").withNodes(this.fleetCarrierImage, this.fleetCarrierLabel, this.wishlistImage, this.wishlistLabel).buildHBox()
        ).buildVBox();
        final VBox right = BoxBuilder.builder().withNodes(
                BoxBuilder.builder().withStyleClasses("bartender-material-line").withNodes(this.decreaseTenLabel,this.decreaseLabel, this.amountLabel,this.increaseLabel,this.increaseTenLabel).buildHBox(),
                BoxBuilder.builder().withStyleClass("bartender-material-line").withNodes(this.amountSelectedLabel, new GrowingRegion(), this.amountSelectedValueLabel).buildHBox()
        ).buildVBox();
        this.getChildren().addAll(left, left2, new GrowingRegion(), middle, right);
        this.mouseEventEventHandler = event -> {
            EventService.publish(new OdysseyBartenderMaterialSelectedEvent(this));
        };
        this.onMouseClickedProperty().setValue(this.mouseEventEventHandler);
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            EventService.publish(new OdysseyBartenderMaterialHoverEvent(this.asset));
        });
        setLayoutMode(LayoutMode.DEFAULT);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, StorageEvent.class, event -> {
            this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
            this.backpackLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getBackPackValue()));
            this.shipLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getShipLockerValue()));
            this.fleetCarrierLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getFleetCarrierValue()));
            updateStyle();
        }));
        this.eventListeners.add(EventService.addListener(this, 9, WishlistBlueprintEvent.class, event -> {
            Platform.runLater(() -> {
                this.wishlistLabel.setText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.asset)).mapToInt(Integer::intValue).sum()));
                updateStyle();
            });
        }));
        this.eventListeners.add(EventService.addListener(this, 9, CommanderSelectedEvent.class, event -> {
            this.wishlistLabel.setText(String.valueOf(Wishlist.ALL.getItems().stream().map(bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName()).getRequiredAmount(this.asset)).mapToInt(Integer::intValue).sum()));
            setLayoutMode(LayoutMode.DEFAULT);
            updateStyle();
        }));
    }

    void setLayoutMode(final LayoutMode mode) {
        this.layoutMode.setValue(mode);
        switch (mode) {

            case SELECTED -> {
                this.decreaseLabel.setVisible(false);
                this.increaseLabel.setVisible(false);
                this.decreaseTenLabel.setVisible(false);
                this.increaseTenLabel.setVisible(false);
                this.amountSelectedValueLabel.setVisible(false);
                this.getStyleClass().remove("bartender-material-hover");
                this.onMouseClickedProperty().setValue(null);
            }
            case TRADE -> {
//                this.buyValueLabel.setText(String.valueOf(this.asset.getSellValue()));
                this.decreaseLabel.setVisible(true);
                this.increaseLabel.setVisible(true);
                this.decreaseTenLabel.setVisible(true);
                this.increaseTenLabel.setVisible(true);
                this.amountSelectedValueLabel.setVisible(true);
                this.getStyleClass().remove("bartender-material-hover");
                this.onMouseClickedProperty().setValue(null);
            }
            case DEFAULT -> {
                this.amountSelected = 0;
                this.amountSelectedValueLabel.setText("");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue()));
//                this.buyValueLabel.setText(String.valueOf(this.asset.getBuyValue()));
                this.decreaseLabel.setVisible(false);
                this.increaseLabel.setVisible(false);
                this.decreaseTenLabel.setVisible(false);
                this.increaseTenLabel.setVisible(false);
                this.amountSelectedValueLabel.setVisible(false);
                if (!this.getStyleClass().contains("bartender-material-hover")) {
                    this.getStyleClass().add("bartender-material-hover");
                }
                this.onMouseClickedProperty().setValue(this.mouseEventEventHandler);
            }
            case HIDDEN -> {
            }
        }
        updateStyle();
    }

    private void updateStyle() {
        if (Integer.parseInt(this.wishlistLabel.getText()) > Integer.parseInt(this.amountLabel.getText())) {
            if (!this.nameLabel.getStyleClass().contains("red")) {
                this.nameLabel.getStyleClass().add("red");
            }
        } else {
            this.nameLabel.getStyleClass().remove("red");
        }
    }

    void addAllAssets() {
        if (this.amountSelected < StorageService.getMaterialStorage(this.asset).getTotalValue()) {
            this.amountSelected = StorageService.getMaterialStorage(this.asset).getTotalValue();
            EventService.publish(new OdysseyBartenderAmountSelectedEvent(this.asset, this.amountSelected));
            this.amountSelectedValueLabel.setText(this.amountSelected > 0 ? String.valueOf(this.amountSelected) : "");
            this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
            updateStyle();
        }
    }

    private DestroyableResizableImageView createMaterialImage() {

        ResizableImageViewBuilder imageViewBuilder = ResizableImageViewBuilder.builder().withStyleClass("materialcard-image");
        imageViewBuilder = switch (this.asset.getType()) {
            case TECH -> imageViewBuilder.withImage("/images/material/tech.png");
            case CIRCUIT -> imageViewBuilder.withImage("/images/material/circuit.png");
            case CHEMICAL -> imageViewBuilder.withImage("/images/material/chemical.png");
        };

        return imageViewBuilder.build();
    }
}
