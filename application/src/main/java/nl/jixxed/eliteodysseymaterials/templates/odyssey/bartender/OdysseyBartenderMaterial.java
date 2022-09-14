package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderAmountSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderMaterialSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

public class OdysseyBartenderMaterial extends HBox implements Template {
    @Getter
    private final Asset asset;
    private int amountSelected = 0;
    private DestroyableLabel amountSelectedLabel;
    private DestroyableLabel valueLabel;
    private DestroyableLabel nameLabel;
    private DestroyableLabel decreaseLabel;
    private DestroyableLabel amountLabel;
    private DestroyableLabel increaseLabel;
    private DestroyableResizableImageView imageView;
    private EventHandler<MouseEvent> mouseEventEventHandler;

    OdysseyBartenderMaterial(final Asset asset) {
        this.asset = asset;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bartender-material");
        // amount selected
        this.amountSelectedLabel = LabelBuilder.builder().withStyleClass("bartender-material-amount-selected").withNonLocalizedText("").build();
        // image
        this.imageView = createMaterialImage();
        // value
        this.valueLabel = LabelBuilder.builder().withStyleClass("bartender-material-value").withNonLocalizedText(String.valueOf(this.asset.getBuyValue())).build();
        // name
        this.nameLabel = LabelBuilder.builder().withStyleClass("bartender-material-name").withText(LocaleService.getStringBinding(this.asset.getLocalizationKey())).build();
        // decrease
        this.decreaseLabel = LabelBuilder.builder().withStyleClass("bartender-material-button").withNonLocalizedText("-").withOnMouseClicked(event -> {
            if (this.amountSelected > 0) {
                this.amountSelected--;
                EventService.publish(new OdysseyBartenderAmountSelectedEvent(this.asset, this.amountSelected));
                this.amountSelectedLabel.setText(this.amountSelected > 0 ? String.valueOf(this.amountSelected) : "");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
            }
        }).build();
        // amount
        this.amountLabel = LabelBuilder.builder().withStyleClass("bartender-material-amount").withNonLocalizedText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue())).build();
        // increase
        this.increaseLabel = LabelBuilder.builder().withStyleClass("bartender-material-button").withNonLocalizedText("+").withOnMouseClicked(event -> {
            if (this.amountSelected < StorageService.getMaterialStorage(this.asset).getTotalValue()) {
                this.amountSelected++;
                EventService.publish(new OdysseyBartenderAmountSelectedEvent(this.asset, this.amountSelected));
                this.amountSelectedLabel.setText(this.amountSelected > 0 ? String.valueOf(this.amountSelected) : "");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
            }
        }).build();
        this.getChildren().addAll(this.imageView, this.valueLabel, this.nameLabel, this.decreaseLabel, this.amountLabel, this.increaseLabel, this.amountSelectedLabel);
        this.mouseEventEventHandler = event -> {
            EventService.publish(new OdysseyBartenderMaterialSelectedEvent(this));
        };
        this.onMouseClickedProperty().setValue(this.mouseEventEventHandler);
        setLayoutMode(LayoutMode.DEFAULT);
    }

    @Override
    public void initEventHandling() {
        EventService.addListener(this, StorageEvent.class, event -> {
            this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue() - this.amountSelected));
        });
    }

    void setLayoutMode(final LayoutMode mode) {
        switch (mode) {

            case SELECTED -> {
                this.decreaseLabel.setVisible(false);
                this.increaseLabel.setVisible(false);
                this.amountSelectedLabel.setVisible(false);
                this.getStyleClass().remove("bartender-material-hover");
                this.onMouseClickedProperty().setValue(null);
            }
            case TRADE -> {
                this.valueLabel.setText(String.valueOf(this.asset.getSellValue()));
                this.decreaseLabel.setVisible(true);
                this.increaseLabel.setVisible(true);
                this.amountSelectedLabel.setVisible(true);
                this.getStyleClass().remove("bartender-material-hover");
                this.onMouseClickedProperty().setValue(null);
            }
            case DEFAULT -> {
                this.amountSelected = 0;
                this.amountSelectedLabel.setText("");
                this.amountLabel.setText(String.valueOf(StorageService.getMaterialStorage(this.asset).getTotalValue()));
                this.valueLabel.setText(String.valueOf(this.asset.getBuyValue()));
                this.decreaseLabel.setVisible(false);
                this.increaseLabel.setVisible(false);
                this.amountSelectedLabel.setVisible(false);
                if (!this.getStyleClass().contains("bartender-material-hover")) {
                    this.getStyleClass().add("bartender-material-hover");
                }
                this.onMouseClickedProperty().setValue(this.mouseEventEventHandler);
            }
            case HIDDEN -> {
            }
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
