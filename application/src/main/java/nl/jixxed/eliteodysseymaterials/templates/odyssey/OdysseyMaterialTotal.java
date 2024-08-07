package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.AssetType;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTotalType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SoloModeEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

import java.util.*;

class OdysseyMaterialTotal extends VBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MATERIAL_TOTAL_VALUE_ROW_STYLE_CLASS = "material-total-value-row";
    private static final String MATERIAL_TOTAL_VALUE_IRRELEVANT_ROW_STYLE_CLASS = "material-total-value-irrelevant-row";
    private final Map<MaterialTotalType, Label> totals = new EnumMap<>(MaterialTotalType.class);
    private final Map<MaterialTotalType, Label> totalValues = new EnumMap<>(MaterialTotalType.class);
    private Label name;
    private final OdysseyStorageType storageType;
    private final MaterialTotalType[] totalTypes;
    private Label totalValueLabel;
    private Label subTotalValueLabel;

    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    OdysseyMaterialTotal(final OdysseyStorageType storageType, final MaterialTotalType... totalTypes) {
        this.storageType = storageType;
        this.totalTypes = totalTypes;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        initTotalHeader();
        initTotals();
        initSubTotal();
        updateTotals();
        this.getStyleClass().add("material-total");
        this.getStyleClass().add("material-total-" + this.storageType.name().toLowerCase());
    }

    private void initSubTotal() {
        final Label subTotal = LabelBuilder.builder().withStyleClass("material-total-total-row").withText(LocaleService.getStringBinding(MaterialTotalType.SUB_TOTAL.getLocalizationKey())).build();
        final Label total = LabelBuilder.builder().withStyleClass("material-total-total-row").withText(LocaleService.getStringBinding(MaterialTotalType.TOTAL.getLocalizationKey())).build();
        this.totals.put(MaterialTotalType.SUB_TOTAL, subTotal);
        this.totals.put(MaterialTotalType.TOTAL, total);

        this.subTotalValueLabel = LabelBuilder.builder().withStyleClass("material-total-total-row").withNonLocalizedText("0").build();
        this.totalValueLabel = LabelBuilder.builder().withStyleClass("material-total-total-row").withNonLocalizedText("0").build();

        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        final HBox subTotalBox = BoxBuilder.builder().withNodes(subTotal, new GrowingRegion(), this.subTotalValueLabel).buildHBox();
        final HBox totalBox = BoxBuilder.builder().withNodes(total, new GrowingRegion(), this.totalValueLabel).buildHBox();
        this.getChildren().addAll(totalBox, subTotalBox);
    }

    private void initTotals() {
        Arrays.stream(this.totalTypes).forEach(totalType -> {
            if (MaterialTotalType.TOTAL.equals(totalType)) {
                throw new IllegalArgumentException("TOTAL not supported");
            }
            final Label totalName = LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding(totalType.getLocalizationKey()))
                    .withStyleClass(MaterialTotalType.IRRELEVANT.equals(totalType) ? MATERIAL_TOTAL_VALUE_IRRELEVANT_ROW_STYLE_CLASS : MATERIAL_TOTAL_VALUE_ROW_STYLE_CLASS)
                    .build();
            this.totals.put(totalType, totalName);
            final Label totalValue = LabelBuilder.builder()
                    .withStyleClass(MATERIAL_TOTAL_VALUE_ROW_STYLE_CLASS)
                    .withStyleClass(MaterialTotalType.IRRELEVANT.equals(totalType) ? MATERIAL_TOTAL_VALUE_IRRELEVANT_ROW_STYLE_CLASS : MATERIAL_TOTAL_VALUE_ROW_STYLE_CLASS)
                    .withNonLocalizedText("0")
                    .build();
            this.totalValues.put(totalType, totalValue);
        });

        this.totals.keySet().forEach(key -> {
            final Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            this.getChildren().add(new HBox(this.totals.get(key), region, this.totalValues.get(key)));
        });
        final Region vRegion = new Region();
        VBox.setVgrow(vRegion, Priority.ALWAYS);
        this.getChildren().add(vRegion);
    }

    private void initTotalHeader() {
        this.name = LabelBuilder.builder()
                .withStyleClass("material-total-name")
                .withText(LocaleService.getStringBinding(this.storageType.getLocalizationKey()))
                .build();

        this.getChildren().addAll(this.name, new Separator(Orientation.HORIZONTAL));
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, JournalLineProcessedEvent.class, journalProcessedEvent -> updateTotals()));
        this.eventListeners.add(EventService.addListener(this, SoloModeEvent.class, soloModeEvent -> updateTotals()));
    }

    private void updateTotals() {
        if (OdysseyStorageType.DATA.equals(this.storageType) || OdysseyStorageType.GOOD.equals(this.storageType)) {
            final Integer blueprintTotal = StorageService.getMaterials(this.storageType).entrySet().stream()
                    .filter(material -> (APPLICATION_STATE.getSoloMode())
                            ? OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) || OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(material.getKey())
                            : OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) || OdysseyBlueprintConstants.isEngineeringIngredient(material.getKey()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            final Integer irrelevantTotal = StorageService.getMaterials(this.storageType).entrySet().stream()
                    .filter(material -> (APPLICATION_STATE.getSoloMode())
                            ? !OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) && !OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(material.getKey())
                            : !OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) && !OdysseyBlueprintConstants.isEngineeringIngredient(material.getKey()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);

            update(MaterialTotalType.BLUEPRINT, blueprintTotal);
            update(MaterialTotalType.IRRELEVANT, irrelevantTotal);
        } else if (OdysseyStorageType.ASSET.equals(this.storageType)) {
            final Integer chemicalAssets = StorageService.getAssets().entrySet().stream()
                    .filter(assetEntry -> AssetType.CHEMICAL.equals(assetEntry.getKey().getType()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            final Integer circuitAssets = StorageService.getAssets().entrySet().stream()
                    .filter(assetEntry -> AssetType.CIRCUIT.equals(assetEntry.getKey().getType()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            final Integer techAssets = StorageService.getAssets().entrySet().stream()
                    .filter(assetEntry -> AssetType.TECH.equals(assetEntry.getKey().getType()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            update(MaterialTotalType.CHEMICAL, chemicalAssets);
            update(MaterialTotalType.CIRCUIT, circuitAssets);
            update(MaterialTotalType.TECH, techAssets);
        }
        updateTotalsLabels();
    }

    private void update(final MaterialTotalType type, final Integer amount) {

        if (this.totalValues.containsKey(type)) {
            final Label label = this.totalValues.get(type);
            label.setText(amount.toString());
        }
    }

    private void updateTotalsLabels() {
        final Integer subTotal = StorageService.getStorageTotal(this.storageType, StoragePool.BACKPACK, StoragePool.SHIPLOCKER);
        this.subTotalValueLabel.setText(subTotal + "/1000");
        final Integer total = StorageService.getStorageTotal(this.storageType, StoragePool.BACKPACK, StoragePool.SHIPLOCKER, StoragePool.FLEETCARRIER);
        this.totalValueLabel.setText(total.toString());
    }
}
