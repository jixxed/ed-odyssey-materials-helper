package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.AssetType;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTotalType;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.SoloModeEvent;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

class MaterialTotal extends VBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MATERIAL_TOTAL_VALUE_ROW_STYLE_CLASS = "material-total-value-row";
    private static final String MATERIAL_TOTAL_VALUE_IRRELEVANT_ROW_STYLE_CLASS = "material-total-value-irrelevant-row";
    private Integer subtotalValue = 0;

    private final Map<MaterialTotalType, Label> totals = new EnumMap<>(MaterialTotalType.class);
    private final Map<MaterialTotalType, Label> totalValues = new EnumMap<>(MaterialTotalType.class);
    private Label name;
    private final StorageType storageType;
    private final MaterialTotalType[] totalTypes;

    MaterialTotal(final StorageType storageType, final MaterialTotalType... totalTypes) {
        this.storageType = storageType;
        this.totalTypes = totalTypes;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        initTotalHeader();
        initTotals();
        initSubTotal();

        this.getStyleClass().add("material-total");
        this.getStyleClass().add("material-total-" + this.storageType.name().toLowerCase());
    }

    private void initSubTotal() {
        final Label total = LabelBuilder.builder().withStyleClass("material-total-total-row").withText(LocaleService.getStringBinding(MaterialTotalType.TOTAL.getLocalizationKey())).build();
        this.totals.put(MaterialTotalType.TOTAL, total);

        final Label totalValueLabel = LabelBuilder.builder().withStyleClass("material-total-total-row").withNonLocalizedText("0").build();
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        this.totalValues.put(MaterialTotalType.TOTAL, totalValueLabel);

        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        final HBox totalBox = BoxBuilder.builder().withNodes(total, region, totalValueLabel).buildHBox();
        this.getChildren().add(totalBox);
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
        EventService.addListener(this, JournalLineProcessedEvent.class, journalProcessedEvent -> updateTotals());
        EventService.addListener(this, SoloModeEvent.class, soloModeEvent -> updateTotals());
    }

    private void updateTotals() {
        if (StorageType.DATA.equals(this.storageType) || StorageType.GOOD.equals(this.storageType)) {
            final Integer blueprintTotal = APPLICATION_STATE.getMaterials(this.storageType).entrySet().stream()
                    .filter(material -> (APPLICATION_STATE.getSoloMode())
                            ? RecipeConstants.isBlueprintIngredientWithOverride(material.getKey()) || RecipeConstants.isEngineeringIngredientAndNotCompleted(material.getKey())
                            : RecipeConstants.isBlueprintIngredientWithOverride(material.getKey()) || RecipeConstants.isEngineeringIngredient(material.getKey()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            final Integer irrelevantTotal = APPLICATION_STATE.getMaterials(this.storageType).entrySet().stream()
                    .filter(material -> (APPLICATION_STATE.getSoloMode())
                            ? !RecipeConstants.isBlueprintIngredientWithOverride(material.getKey()) && !RecipeConstants.isEngineeringIngredientAndNotCompleted(material.getKey())
                            : !RecipeConstants.isBlueprintIngredientWithOverride(material.getKey()) && !RecipeConstants.isEngineeringIngredient(material.getKey()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);

            update(MaterialTotalType.BLUEPRINT, blueprintTotal);
            update(MaterialTotalType.IRRELEVANT, irrelevantTotal);
        } else if (StorageType.ASSET.equals(this.storageType)) {
            final Integer chemicalAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                    .filter(assetEntry -> AssetType.CHEMICAL.equals(assetEntry.getKey().getType()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            final Integer circuitAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                    .filter(assetEntry -> AssetType.CIRCUIT.equals(assetEntry.getKey().getType()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            final Integer techAssets = APPLICATION_STATE.getAssets().entrySet().stream()
                    .filter(assetEntry -> AssetType.TECH.equals(assetEntry.getKey().getType()))
                    .map(entry -> entry.getValue().getTotalValue())
                    .reduce(0, Integer::sum);
            update(MaterialTotalType.CHEMICAL, chemicalAssets);
            update(MaterialTotalType.CIRCUIT, circuitAssets);
            update(MaterialTotalType.TECH, techAssets);
        }
    }

    private void update(final MaterialTotalType type, final Integer amount) {
        if (this.totalValues.containsKey(type)) {
            final Label label = this.totalValues.get(type);
            final Integer currentAmount = Integer.parseInt(label.getText());
            label.setText(amount.toString());
            final Label totalLabel = this.totalValues.get(MaterialTotalType.TOTAL);
            this.subtotalValue += (amount - currentAmount);
            totalLabel.setText(this.subtotalValue + "/1000");
        }
    }

}
