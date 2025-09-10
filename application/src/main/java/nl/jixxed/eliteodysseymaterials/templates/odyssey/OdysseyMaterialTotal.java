package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

class OdysseyMaterialTotal extends DestroyableVBox implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MATERIAL_TOTAL_VALUE_ROW_STYLE_CLASS = "material-total-value-row";
    private static final String MATERIAL_TOTAL_VALUE_IRRELEVANT_ROW_STYLE_CLASS = "material-total-value-irrelevant-row";
    private static final String MATERIAL_TOTAL_VALUE_POWERPLAY_ROW_STYLE_CLASS = "material-total-value-powerplay-row";
    public static final String MATERIAL_TOTAL_TOTAL_ROW_STYLE_CLASS = "material-total-total-row";

    private final Map<MaterialTotalType, DestroyableLabel> totals = new EnumMap<>(MaterialTotalType.class);
    private final Map<MaterialTotalType, DestroyableLabel> totalValues = new EnumMap<>(MaterialTotalType.class);
    private final OdysseyStorageType storageType;
    private final MaterialTotalType[] totalTypes;
    private DestroyableLabel totalValueLabel;
    private DestroyableLabel subTotalValueLabel;


    OdysseyMaterialTotal(final OdysseyStorageType storageType, final MaterialTotalType... totalTypes) {
        this.storageType = storageType;
        this.totalTypes = totalTypes;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        initTotalHeader();
        initTotals();
        initSubTotal();
        updateTotals();
        this.getStyleClass().add("material-total");
        this.getStyleClass().add("material-total-" + this.storageType.name().toLowerCase());
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, JournalLineProcessedEvent.class, _ -> updateTotals()));
        register(EventService.addListener(true, this, SoloModeEvent.class, _ -> updateTotals()));
        register(EventService.addListener(true, this, PowerplayEvent.class, _ -> updateTotals()));
        register(EventService.addListener(true, this, PowerplayLeaveEvent.class, _ -> updateTotals()));
    }

    private void initSubTotal() {
        final DestroyableLabel subTotal = LabelBuilder.builder()
                .withStyleClass(MATERIAL_TOTAL_TOTAL_ROW_STYLE_CLASS)
                .withText(MaterialTotalType.SUB_TOTAL.getLocalizationKey())
                .build();
        final DestroyableLabel total = LabelBuilder.builder()
                .withStyleClass(MATERIAL_TOTAL_TOTAL_ROW_STYLE_CLASS)
                .withText(MaterialTotalType.TOTAL.getLocalizationKey())
                .build();
        this.totals.put(MaterialTotalType.SUB_TOTAL, subTotal);
        this.totals.put(MaterialTotalType.TOTAL, total);

        this.subTotalValueLabel = LabelBuilder.builder()
                .withStyleClass(MATERIAL_TOTAL_TOTAL_ROW_STYLE_CLASS)
                .withNonLocalizedText("0")
                .build();
        this.totalValueLabel = LabelBuilder.builder()
                .withStyleClass(MATERIAL_TOTAL_TOTAL_ROW_STYLE_CLASS)
                .withNonLocalizedText("0")
                .build();

        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));
        final DestroyableHBox subTotalBox = BoxBuilder.builder()
                .withNodes(subTotal, new GrowingRegion(), this.subTotalValueLabel)
                .buildHBox();
        final DestroyableHBox totalBox = BoxBuilder.builder()
                .withNodes(total, new GrowingRegion(), this.totalValueLabel)
                .buildHBox();
        this.getNodes().addAll(totalBox, subTotalBox);
    }

    private void initTotals() {
        Arrays.stream(this.totalTypes).forEach(totalType -> {
            if (MaterialTotalType.TOTAL.equals(totalType)) {
                throw new IllegalArgumentException("TOTAL not supported");
            }
            final DestroyableLabel totalName = LabelBuilder.builder()
                    .withText(totalType.getLocalizationKey())
                    .withStyleClass(getStyleClass(totalType))
                    .withVisibilityProperty(getVisibility(totalType))
                    .withManagedProperty(getVisibility(totalType))
                    .build();
            this.totals.put(totalType, totalName);
            final DestroyableLabel totalValue = LabelBuilder.builder()
                    .withStyleClass(getStyleClass(totalType))
                    .withVisibilityProperty(getVisibility(totalType))
                    .withManagedProperty(getVisibility(totalType))
                    .withNonLocalizedText("0")
                    .build();
            this.totalValues.put(totalType, totalValue);
        });

        this.totals.keySet().forEach(key ->
                this.getNodes().add(BoxBuilder.builder().withNodes(this.totals.get(key), new GrowingRegion(), this.totalValues.get(key)).buildHBox()));
        this.getNodes().add(new GrowingRegion());
    }

    private ObservableValue<Boolean> getVisibility(MaterialTotalType totalType) {
        return switch (totalType) {
            case POWERPLAY -> APPLICATION_STATE.getPowerplay();
            default -> new SimpleBooleanProperty(true);
        };
    }

    private static String getStyleClass(MaterialTotalType totalType) {
        return switch (totalType) {
            case POWERPLAY -> MATERIAL_TOTAL_VALUE_POWERPLAY_ROW_STYLE_CLASS;
            case IRRELEVANT -> MATERIAL_TOTAL_VALUE_IRRELEVANT_ROW_STYLE_CLASS;
            default -> MATERIAL_TOTAL_VALUE_ROW_STYLE_CLASS;
        };
    }

    private void initTotalHeader() {
        DestroyableLabel name = LabelBuilder.builder()
                .withStyleClass("material-total-name")
                .withText(this.storageType.getLocalizationKey())
                .build();

        this.getNodes().addAll(name, new DestroyableSeparator(Orientation.HORIZONTAL));
    }

    private void updateTotals() {
        boolean powerplay = Power.NONE != ApplicationState.getInstance().getPower();
        if (OdysseyStorageType.DATA.equals(this.storageType) || OdysseyStorageType.GOOD.equals(this.storageType)) {
            updateDataOrGoods(powerplay);
        } else if (OdysseyStorageType.ASSET.equals(this.storageType)) {
            updateAssets();
        }
        updateTotalsLabels();
    }

    private void updateAssets() {
        final Integer chemicalAssets = Arrays.stream(Asset.values())
                .filter(asset -> asset.isType(AssetType.CHEMICAL))
                .map(asset -> StorageService.getMaterialCount(asset, AmountType.TOTAL))
                .reduce(0, Integer::sum);
        final Integer circuitAssets = Arrays.stream(Asset.values())
                .filter(asset -> asset.isType(AssetType.CIRCUIT))
                .map(asset -> StorageService.getMaterialCount(asset, AmountType.TOTAL))
                .reduce(0, Integer::sum);
        final Integer techAssets = Arrays.stream(Asset.values())
                .filter(asset -> asset.isType(AssetType.TECH))
                .map(asset -> StorageService.getMaterialCount(asset, AmountType.TOTAL))
                .reduce(0, Integer::sum);
        update(MaterialTotalType.CHEMICAL, chemicalAssets);
        update(MaterialTotalType.CIRCUIT, circuitAssets);
        update(MaterialTotalType.TECH, techAssets);
    }

    private void updateDataOrGoods(boolean powerplay) {
        final Integer blueprintTotal = StorageService.getMaterials(this.storageType).entrySet().stream()
                .filter(material -> (APPLICATION_STATE.getSoloMode())
                        ? OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) || OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(material.getKey())
                        : OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) || OdysseyBlueprintConstants.isEngineeringIngredient(material.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer powerplayTotal = StorageService.getMaterials(this.storageType).entrySet().stream()
                .filter(material -> powerplay && material.getKey().isPowerplay())
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
        final Integer irrelevantTotal = StorageService.getMaterials(this.storageType).entrySet().stream()
                .filter(material ->
                        (!powerplay || !material.getKey().isPowerplay()) &&
                                (APPLICATION_STATE.getSoloMode()
                                        ? !OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) && !OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(material.getKey())
                                        : !OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()) && !OdysseyBlueprintConstants.isEngineeringIngredient(material.getKey())))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);

        update(MaterialTotalType.BLUEPRINT, blueprintTotal);
        update(MaterialTotalType.POWERPLAY, powerplayTotal);
        update(MaterialTotalType.IRRELEVANT, irrelevantTotal);
    }

    private void update(final MaterialTotalType type, final Integer amount) {

        if (this.totalValues.containsKey(type)) {
            final DestroyableLabel label = this.totalValues.get(type);
            label.setText(amount.toString());
        }
    }

    private void updateTotalsLabels() {
        final Integer subTotal = StorageService.getStorageTotal(this.storageType, StoragePool.BACKPACK, StoragePool.SHIPLOCKER);
        this.subTotalValueLabel.setText(subTotal + "/1000");
        final Integer total = StorageService.getStorageTotal(this.storageType, StoragePool.BACKPACK, StoragePool.SHIPLOCKER, StoragePool.FLEETCARRIER, StoragePool.SQUADRONCARRIER);
        this.totalValueLabel.setText(total.toString());
    }
}
