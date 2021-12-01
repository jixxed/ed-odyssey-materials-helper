package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.enums.TradeMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

class TradeIngredient extends MaterialIngredient {

    private static final String INGREDIENT_FILLED_CLASS = "trade-ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "trade-ingredient-unfilled";
    private final Boolean isGiven;

    TradeIngredient(final StorageType storageType, final Material material, final Integer amountAvailable, final Integer tradeAmount, final boolean isGiven) {
        super(storageType, material, amountAvailable, tradeAmount);
        this.isGiven = isGiven;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("trade-ingredient");
        setLeftDescriptionLabel(LocaleService.getStringBinding("recipe.header.available"));
        if (this.isGiven) {
            setRightDescriptionLabel(LocaleService.getStringBinding("recipe.header.give"));
        } else {
            setRightDescriptionLabel(LocaleService.getStringBinding("recipe.header.receive"));
        }

        final boolean isNothing = getMaterial().equals(TradeMaterial.NOTHING);
        getLeftAmountLabel().setVisible(!isNothing);
        getRightAmountLabel().setVisible(!isNothing);
        getLeftDescriptionLabel().setVisible(!isNothing);
        getRightDescriptionLabel().setVisible(!isNothing);
        update();
    }

    private void initEventHandling() {
        if (this.isGiven) {
            this.eventListeners.add(EventService.addListener(this, StorageEvent.class, (storageEvent) -> this.update()));
        }
    }

    @Override
    protected void update() {
        if (Boolean.TRUE.equals(this.isGiven)) {
            this.getRightAmountLabel().setText(this.getRightAmount().toString());
            if (this.getLeftAmount() >= Integer.parseInt(this.getRightAmountLabel().getText())) {
                setCanTrade(true);
                this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
                this.getStyleClass().addAll(INGREDIENT_FILLED_CLASS);
            } else {
                setCanTrade(false);
                this.getStyleClass().removeAll(INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
                this.getStyleClass().addAll(INGREDIENT_UNFILLED_CLASS);
            }
        }
    }

    private BooleanProperty canTrade;

    private final void setCanTrade(final boolean value) {
        canTradeProperty().set(value);
    }

    final BooleanProperty canTradeProperty() {
        if (this.canTrade == null) {
            this.canTrade = new SimpleBooleanProperty(true) {

                @Override
                public Object getBean() {
                    return TradeIngredient.this;
                }

                @Override
                public String getName() {
                    return "canTrade";
                }
            };
        }
        return this.canTrade;
    }
}
