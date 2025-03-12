package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderAmountSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderMaterialSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.HashMap;
import java.util.Map;

public class OdysseyBartenderResult extends VBox implements DestroyableTemplate {
    private final Map<Asset, Integer> tradeList = new HashMap<>();
    private Asset selectedAsset;
    private DestroyableLabel costPerItem;
    private DestroyableLabel totalOffered;
    private DestroyableLabel wasted;
    private DestroyableLabel toReceive;


    OdysseyBartenderResult() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bartender-total");
        //cost per item
        final DestroyableLabel costPerItemLabel = LabelBuilder.builder().withStyleClass("bartender-total-header").withText(LocaleService.getStringBinding("tab.bartender.total.cost")).build();
        this.costPerItem = LabelBuilder.builder().withStyleClass("bartender-total-value").withNonLocalizedText("").build();
        //total offered
        final DestroyableLabel totalOfferedLabel = LabelBuilder.builder().withStyleClass("bartender-total-header").withText(LocaleService.getStringBinding("tab.bartender.total.offered")).build();
        this.totalOffered = LabelBuilder.builder().withStyleClass("bartender-total-value").withNonLocalizedText("").build();
        //wasted from non perfect trade
        final DestroyableLabel wastedLabel = LabelBuilder.builder().withStyleClass("bartender-total-header").withText(LocaleService.getStringBinding("tab.bartender.total.wasted")).build();
        this.wasted = LabelBuilder.builder().withStyleClass("bartender-total-value").withNonLocalizedText("").build();
        //items to receive from trade
        final DestroyableLabel toReceiveLabel = LabelBuilder.builder().withStyleClass("bartender-total-header").withText(LocaleService.getStringBinding("tab.bartender.total.receive")).build();
        this.toReceive = LabelBuilder.builder().withStyleClass("bartender-total-value").withNonLocalizedText("").build();
        this.getChildren().addAll(
                BoxBuilder.builder().withStyleClass("bartender-total-line").withNodes(costPerItemLabel, new GrowingRegion(), this.costPerItem).buildHBox(),
                BoxBuilder.builder().withStyleClass("bartender-total-line").withNodes(totalOfferedLabel, new GrowingRegion(), this.totalOffered).buildHBox(),
                BoxBuilder.builder().withStyleClass("bartender-total-line").withNodes(wastedLabel, new GrowingRegion(), this.wasted).buildHBox(),
                BoxBuilder.builder().withStyleClass("bartender-total-line").withNodes(toReceiveLabel, new GrowingRegion(), this.toReceive).buildHBox()
        );
        recalculate();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, OdysseyBartenderMaterialSelectedEvent.class, event -> {
            this.selectedAsset = event.getOdysseyBartenderMaterial().getAsset();
            recalculate();
        }));
        register(EventService.addListener(true, this, OdysseyBartenderAmountSelectedEvent.class, event -> {
            final Asset asset = event.getAsset();
            final Integer amountSelected = event.getAmountSelected();
            this.tradeList.put(asset, amountSelected);
            recalculate();
        }));
    }

    void reset() {
        this.tradeList.clear();
        this.selectedAsset = null;
        recalculate();
    }

    private void recalculate() {
        if (this.selectedAsset == null) {
            this.costPerItem.setText("-");
            this.totalOffered.setText("-");
            this.wasted.setText("-");
            this.toReceive.setText("-");
        } else {
            this.costPerItem.setText(String.valueOf(this.selectedAsset.getBuyValue()));
            final Integer sum = this.tradeList.entrySet().stream().map(entry -> entry.getKey().getSellValue() * entry.getValue()).reduce(Integer::sum).orElse(0);
            this.totalOffered.setText(String.valueOf(sum));
            this.wasted.setText(String.valueOf(sum % this.selectedAsset.getBuyValue()));
            this.toReceive.setText(String.valueOf(sum / this.selectedAsset.getBuyValue()));
        }

    }
}
