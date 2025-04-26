package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderAmountSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderMaterialSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.HashMap;
import java.util.Map;

public class OdysseyBartenderResult extends DestroyableVBox implements DestroyableEventTemplate {
    public static final String TITLE_STYLE_CLASS = "title";
    public static final String VALUE_STYLE_CLASS = "value";
    public static final String LINE_STYLE_CLASS = "line";
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
        this.getStyleClass().add("bartender-result");
        //cost per item
        final DestroyableLabel costPerItemLabel = LabelBuilder.builder()
                .withStyleClass(TITLE_STYLE_CLASS)
                .withText("tab.bartender.total.cost")
                .build();
        this.costPerItem = LabelBuilder.builder()
                .withStyleClass(VALUE_STYLE_CLASS)
                .withNonLocalizedText("")
                .build();
        //total offered
        final DestroyableLabel totalOfferedLabel = LabelBuilder.builder()
                .withStyleClass(TITLE_STYLE_CLASS)
                .withText("tab.bartender.total.offered")
                .build();
        this.totalOffered = LabelBuilder.builder()
                .withStyleClass(VALUE_STYLE_CLASS)
                .withNonLocalizedText("")
                .build();
        //wasted from non perfect trade
        final DestroyableLabel wastedLabel = LabelBuilder.builder()
                .withStyleClass(TITLE_STYLE_CLASS)
                .withText("tab.bartender.total.wasted")
                .build();
        this.wasted = LabelBuilder.builder()
                .withStyleClass(VALUE_STYLE_CLASS)
                .withNonLocalizedText("")
                .build();
        //items to receive from trade
        final DestroyableLabel toReceiveLabel = LabelBuilder.builder()
                .withStyleClass(TITLE_STYLE_CLASS)
                .withText("tab.bartender.total.receive")
                .build();
        this.toReceive = LabelBuilder.builder()
                .withStyleClass(VALUE_STYLE_CLASS)
                .withNonLocalizedText("")
                .build();
        this.getNodes().addAll(
                BoxBuilder.builder()
                        .withStyleClass(LINE_STYLE_CLASS)
                        .withNodes(costPerItemLabel, new GrowingRegion(), this.costPerItem).buildHBox(),
                BoxBuilder.builder()
                        .withStyleClass(LINE_STYLE_CLASS)
                        .withNodes(totalOfferedLabel, new GrowingRegion(), this.totalOffered).buildHBox(),
                BoxBuilder.builder()
                        .withStyleClass(LINE_STYLE_CLASS)
                        .withNodes(wastedLabel, new GrowingRegion(), this.wasted).buildHBox(),
                BoxBuilder.builder()
                        .withStyleClass(LINE_STYLE_CLASS)
                        .withNodes(toReceiveLabel, new GrowingRegion(), this.toReceive).buildHBox()
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
