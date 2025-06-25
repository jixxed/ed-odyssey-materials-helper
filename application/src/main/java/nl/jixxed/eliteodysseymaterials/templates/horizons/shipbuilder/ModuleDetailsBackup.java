package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.HardpointModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.LegacyModuleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ModuleDetailsBackup extends DestroyableVBox implements DestroyableEventTemplate {
    private boolean attributesToggle = true;
    private boolean propertiesToggle = true;
    private DestroyableHBox propertiesX;
    private DestroyableVBox propertiesA;
    private DestroyableVBox propertiesB;

    private DestroyableHBox attributesX;
    private DestroyableVBox attributesA;
    private DestroyableVBox attributesB;
    private DestroyableButton legacySaveButton;
    private DestroyableLabel modulePrice;
    private DestroyableLabel moduleEngineering;
    private DestroyableLabel moduleName;
    private BooleanProperty hasModule = new SimpleBooleanProperty(false);
    private ShipModule shipModule;
    //    DetailsLayer layer;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ModuleDetailsBackup(/*DetailsLayer layer*/) {
//        this.layer = layer;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("stats-values");
        legacySaveButton = ButtonBuilder.builder()
                .withStyleClass("module-details-save-legacy")
                .withText("module.details.save.legacy")
                .withMnemonicParsing(false)
                .withVisibility(false)
                .withManaged(false)
                .withOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.isAltDown()) {
                        legacySaveButton.getOnAction().handle(new ActionEvent());
                    }
                })
                .build();
        this.getNodes().add(legacySaveButton);
        moduleName = LabelBuilder.builder()
                .withStyleClass("module-details-title")
                .withNonLocalizedText("")
                .build();
        moduleName.addBinding(moduleName.visibleProperty(), hasModule);
        propertiesA = BoxBuilder.builder()
                .withStyleClasses("stats-column", "stats-column-left")
//                .withNodes(getSeparator(Orientation.HORIZONTAL))
                .buildVBox();
        propertiesB = BoxBuilder.builder()
                .withStyleClasses("stats-column", "stats-column-right")
//                .withNodes(getSeparator(Orientation.HORIZONTAL))
                .buildVBox();
//        this.getNodes().add(propertiesX);
        final DestroyableLabel moduleSpecs = LabelBuilder.builder()
                .withStyleClass("module-details-title")
                .withText("module.details.module.specs")
                .build();
        moduleSpecs.addBinding(moduleSpecs.visibleProperty(), hasModule);
        attributesA = BoxBuilder.builder()
                .withStyleClasses("stats-column", "stats-column-left")
//                .withNodes(getSeparator(Orientation.HORIZONTAL))
                .buildVBox();
        attributesB = BoxBuilder.builder()
                .withStyleClasses("stats-column", "stats-column-right")
//                .withNodes(getSeparator(Orientation.HORIZONTAL))
                .buildVBox();
        attributesX = BoxBuilder.builder()
                .withNodes(attributesA, attributesB)
                .buildHBox();
        propertiesX = BoxBuilder.builder()
                .withNodes(propertiesA, propertiesB)
                .buildHBox();
        propertiesX.addBinding(propertiesX.visibleProperty(), hasModule);
        attributesX.addBinding(attributesX.visibleProperty(), hasModule);
//        this.getNodes().add(moduleSpecs);
//        this.getNodes().add(moduleName);
        this.getNodes().addAll(moduleName, propertiesX, /*getSeparator(Orientation.HORIZONTAL),*/ moduleSpecs, attributesX);
//        this.getNodes().add(new GrowingRegion());
//        this.addBinding(this.maxHeightProperty(), this.layer.heightProperty());
    }

    private void addEngineering(String text) {
        var properties = getPropertiesList();
        properties.getNodes().add(getSeparator(Orientation.HORIZONTAL));
        moduleEngineering = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withNonLocalizedText(text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.engineering")
                        .build(),
                BoxBuilder.builder().withNodes(moduleEngineering).buildHBox(),
                properties);
        properties.getNodes().add(flowPane);
    }

    private void addPrice(String text) {
        var properties = getPropertiesList();
        properties.getNodes().add(getSeparator(Orientation.HORIZONTAL));
        modulePrice = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withText("module.details.price.value", text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.price")
                        .build(),
                BoxBuilder.builder().withNodes(modulePrice).buildHBox(),
                properties);
        properties.getNodes().add(flowPane);
    }

    private void addBuyPrice(String text) {
        var properties = getPropertiesList();
        properties.getNodes().add(getSeparator(Orientation.HORIZONTAL));
        modulePrice = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withText("module.details.buyprice.value", text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.buyprice")
                        .build(),
                BoxBuilder.builder().withNodes(modulePrice).buildHBox(),
                properties);
        properties.getNodes().add(flowPane);
    }

    private void addRebuyPrice(String text) {
        var properties = getPropertiesList();
        properties.getNodes().add(getSeparator(Orientation.HORIZONTAL));
        modulePrice = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withText("module.details.rebuyprice.value", text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.rebuyprice")
                        .build(),
                BoxBuilder.builder().withNodes(modulePrice).buildHBox(),
                properties);
        properties.getNodes().add(flowPane);
    }


    @Override
    public void initEventHandling() {

        register(EventService.addListener(false, this, TerminateApplicationEvent.class, event -> {
            executorService.shutdownNow();
        }));
        register(EventService.addListener(true, this, 9, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
        register(EventService.addListener(true, this, TerminateApplicationEvent.class, event -> executorService.shutdown()));
        register(EventService.addListener(true, this, SlotboxHoverEvent.class, (event) -> {
            this.shipModule = event.getShipModule();

            update();
        }));
    }

    private void update() {
        attributesToggle = true;
        propertiesToggle = true;
        propertiesA.getNodes().clear();
        propertiesB.getNodes().clear();
        legacySaveButton.setVisible(false);
        legacySaveButton.setManaged(false);
        attributesA.getNodes().clear();
        attributesB.getNodes().clear();
        final boolean modulePresent = shipModule != null;
        hasModule.set(false);
        if (modulePresent) {
//            attributesA.getNodes().add(getSeparator(Orientation.HORIZONTAL));
//            attributesB.getNodes().add(getSeparator(Orientation.HORIZONTAL));
            addPrice(Formatters.NUMBER_FORMAT_0.format(shipModule.getBasePrice()));
            if (shipModule.getBuyPrice() != null) {
                addBuyPrice(Formatters.NUMBER_FORMAT_0.format(shipModule.getBuyPrice()));
            }
            addRebuyPrice(Formatters.NUMBER_FORMAT_0.format((shipModule.getBuyPrice() != null ? shipModule.getBuyPrice() : shipModule.getBasePrice()) * 0.05));
            if (!shipModule.getModifications().isEmpty()) {
                addEngineering(Stream.concat(shipModule.getModifications().stream()
                                        .map(modification -> LocaleService.getLocalizedStringForCurrentLocale(modification.getModification().getLocalizationKey())),
                                shipModule.getExperimentalEffects().stream()
                                        .map(modification -> LocaleService.getLocalizedStringForCurrentLocale(modification.getLocalizationKey())))
                        .collect(Collectors.joining(", ")));
            }
//            propertiesA.getNodes().add(getSeparator(Orientation.HORIZONTAL));
//            propertiesB.getNodes().add(getSeparator(Orientation.HORIZONTAL));
            moduleName.setText(LocaleService.getLocalizedStringForCurrentLocale(shipModule.getLocalizationKey()) + " " + shipModule.getModuleSize().intValue() + shipModule.getModuleClass() + ((shipModule instanceof HardpointModule hardpointModule ? "-" + hardpointModule.getMounting().getShortName() : "")));

            shipModule.getAttibutes().stream().filter(Predicate.not(shipModule::isHiddenStat)).sorted(Comparator.comparing(HorizonsModifier::getOrder)).forEach(horizonsModifier -> {
                addAttribute(horizonsModifier, shipModule);
            });
            if (shipModule.isLegacy()) {
                legacySaveButton.setOnAction(actionEvent -> {
                    LegacyModuleService.saveLegacyModule(shipModule);
                    EventService.publish(new LegacyModuleSavedEvent());
                });
                legacySaveButton.setVisible(true);
                legacySaveButton.setManaged(true);
            }
        }
        executorService.schedule(() -> Platform.runLater(() -> hasModule.set(modulePresent)), 50, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    private static DestroyableSeparator getSeparator(Orientation orientation) {
        final DestroyableSeparator separator = new DestroyableSeparator(orientation);
        separator.getStyleClass().add("module-details-line");
        return separator;
    }

    private void addAttribute(HorizonsModifier horizonsModifier, ShipModule shipModule) {
        final Object originalAttributeValue = shipModule.getOriginalAttributeValue(horizonsModifier);
        final Object minValue = shipModule.getAttributeValue(horizonsModifier, 0.0);
        final Object maxValue = shipModule.getAttributeValue(horizonsModifier, 1.0);
        final Object estimatedValue = shipModule.getAttributeValue(horizonsModifier);
        final DestroyableHBox valuesLine = BoxBuilder.builder()
                .withNodes().buildHBox();

        if (originalAttributeValue instanceof Boolean) {
            booleanLine(horizonsModifier, valuesLine, estimatedValue);
        } else {
            var attributes = getAttributesList();
            final DestroyableLabel title = LabelBuilder.builder()
                    .withStyleClass("module-details-label-title")
                    .withText(horizonsModifier.getLocalizationKey())
                    .build();
            final DestroyableFlowPane flowPane = getFlowPane(title, valuesLine, attributes);
            final DestroyableVBox attribute = BoxBuilder.builder()
                    .withNodes(flowPane)
                    .buildVBox();
            if (isModifiedAttribute(originalAttributeValue, maxValue)) {
                if (shipModule.getModifiers().containsKey(horizonsModifier)) {
                    doubleLineWithActualValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue);
                } else {
                    doubleLIneWithEstimatedValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue, (Double) estimatedValue);
                }
                if (!shipModule.getModifications().isEmpty()) {
                    addTooltip(horizonsModifier, shipModule, (Double) originalAttributeValue, (Double) minValue, (Double) maxValue, (Double) estimatedValue, attribute);
                }
            } else {
                valuesLine.getNodes().add(LabelBuilder.builder()
                        .withStyleClass("module-details-label")
                        .withNonLocalizedText(horizonsModifier.format(originalAttributeValue))
                        .build());
            }
            attributes.getNodes().add(attribute);
            attributes.getNodes().add(getSeparator(Orientation.HORIZONTAL));
        }
    }

    private <E extends Pane & DestroyableComponent> DestroyableFlowPane getFlowPane(DestroyableLabel title, E valuesLine, DestroyableVBox container) {
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("fit-to-width")
                .withNodes(title, valuesLine)
                .withOrientation(Orientation.HORIZONTAL)
                .build();
        flowPane.addBinding(flowPane.maxHeightProperty(), title.heightProperty().add(valuesLine.heightProperty()));
        flowPane.addBinding(flowPane.prefWidthProperty(), container.widthProperty());
        flowPane.addBinding(this.attributesX.visibleProperty(), flowPane.needsLayoutProperty().not());
        flowPane.addChangeListener(flowPane.needsLayoutProperty(), ((observable, oldValue, newValue) -> {
            final double itemsWidth = flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum();
            final double parentWidth = container.getWidth();
            if (parentWidth >= itemsWidth + ScalingHelper.getPixelDoubleFromEm(0.8)) {
                flowPane.setHgap((parentWidth - itemsWidth - ScalingHelper.getPixelDoubleFromEm(0.4)));
                title.setPadding(new Insets(0, 0, 0, ScalingHelper.getPixelDoubleFromEm(0.2)));
                valuesLine.setPadding(new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.2), 0, ScalingHelper.getPixelDoubleFromEm(0.2)));
            } else if (Orientation.HORIZONTAL.equals(flowPane.getOrientation())) {
                flowPane.setOrientation(Orientation.VERTICAL);
                title.setPadding(new Insets(0, parentWidth - title.getWidth(), 0, ScalingHelper.getPixelDoubleFromEm(0.2)));
                valuesLine.setPadding(new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.2), 0, parentWidth - valuesLine.getWidth() - ScalingHelper.getPixelDoubleFromEm(0.2)));
            }
        }));
        return flowPane;
    }

    private static void addTooltip(HorizonsModifier horizonsModifier, ShipModule shipModule, Double originalAttributeValue, Double minValue, Double maxValue, Double estimatedValue, DestroyableVBox attribute) {
        final BigDecimal estimated = !Objects.equals(minValue, maxValue) && (estimatedValue > originalAttributeValue && horizonsModifier.isHigherBetter() || estimatedValue < originalAttributeValue && !horizonsModifier.isHigherBetter())
                ? shipModule.getModifications().getFirst().getModificationCompleteness().orElse(BigDecimal.ZERO).multiply(BigDecimal.valueOf(100))
                : BigDecimal.valueOf(100);

        final boolean hasActualValue = shipModule.getModifiers().containsKey(horizonsModifier);

        String minValueText = horizonsModifier.format(minValue);
        String estValueText = horizonsModifier.format(estimatedValue) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(estimated) + "%");
        String actValueText = hasActualValue
                ? horizonsModifier.format(shipModule.getModifiers().get(horizonsModifier)) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply(BigDecimal.valueOf(100))) + "%")
                : "";
        String maxValueText = horizonsModifier.format(maxValue);
        StringBinding text = hasActualValue
                ? LocaleService.getStringBinding("tab.ships.details.tooltip.actual", minValueText, estValueText, actValueText, maxValueText)
                : LocaleService.getStringBinding("tab.ships.details.tooltip.estimated", minValueText, estValueText, maxValueText);
        final DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText(text)
                .build();
        tooltip.install(attribute);
    }

    private static void doubleLIneWithEstimatedValue(HorizonsModifier horizonsModifier, ShipModule shipModule, DestroyableHBox valuesLine, double originalAttributeValue, double estimatedValue) {
        final DestroyableLabel value = LabelBuilder.builder()
                .withStyleClass("module-details-label")
                .withNonLocalizedText(horizonsModifier.format(estimatedValue) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply(BigDecimal.valueOf(100))) + "%"))
                .build();
        if (estimatedValue > originalAttributeValue && horizonsModifier.isHigherBetter() || estimatedValue < originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-green");
        } else if (estimatedValue < originalAttributeValue && horizonsModifier.isHigherBetter() || estimatedValue > originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-red");
        }
        valuesLine.getNodes().addAll(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label")
                        .withNonLocalizedText(horizonsModifier.format(originalAttributeValue))
                        .build(),
                LabelBuilder.builder()
                        .withNonLocalizedText(" → ")
                        .build(),
                value
        );
    }

    private static void doubleLineWithActualValue(HorizonsModifier horizonsModifier, ShipModule shipModule, DestroyableHBox valuesLine, double originalAttributeValue) {
        final double currentValue = Double.parseDouble(shipModule.getModifiers().get(horizonsModifier).toString());
        final DestroyableLabel value = LabelBuilder.builder()
                .withStyleClass("module-details-label")
                .withNonLocalizedText(horizonsModifier.format(currentValue) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply(BigDecimal.valueOf(100))) + "%"))
                .build();
        if (currentValue > originalAttributeValue && horizonsModifier.isHigherBetter() || currentValue < originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-green");
        } else if (currentValue < originalAttributeValue && horizonsModifier.isHigherBetter() || currentValue > originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-red");
        }
        valuesLine.getNodes().addAll(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label")
                        .withNonLocalizedText(horizonsModifier.format(originalAttributeValue))
                        .build(),
                LabelBuilder.builder()
                        .withNonLocalizedText(" → ")
                        .build(),
                value
        );
    }

    private void booleanLine(HorizonsModifier horizonsModifier, DestroyableHBox valuesLine, Object estimatedValue) {
        final DestroyableLabel value = LabelBuilder.builder()
                .withStyleClass("module-details-label")
                .withNonLocalizedText(horizonsModifier.format(estimatedValue))
                .build();
        if (Boolean.TRUE.equals(estimatedValue)) {
            value.getStyleClass().add("module-details-label-green");
        }
        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("module-details-label-title")
                .withText(horizonsModifier.getLocalizationKey())
                .build();
        valuesLine.getNodes().addAll(
                title,
                new GrowingRegion(),
                value
        );
        title.setPadding(new Insets(0, 0, 0, ScalingHelper.getPixelDoubleFromEm(0.2)));
        valuesLine.setPadding(new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.6), 0, 0));
        getAttributesList().getNodes().add(valuesLine);
    }

    private DestroyableVBox getAttributesList() {
        if (attributesToggle) {
            attributesToggle = !attributesToggle;
            return attributesA;
        } else {
            attributesToggle = !attributesToggle;
            return attributesB;
        }
    }

    private DestroyableVBox getPropertiesList() {
        if (propertiesToggle) {
            propertiesToggle = !propertiesToggle;
            return propertiesA;
        } else {
            propertiesToggle = !propertiesToggle;
            return propertiesB;
        }
    }

    private static boolean isModifiedAttribute(Object originalAttributeValue, Object attributeValue) {
        return !originalAttributeValue.equals(attributeValue);
    }

    @Override
    public void destroyInternal() {
        log.debug("destroy ModuleDetails");
        super.destroyInternal();
        executorService.shutdownNow();
//        this.layer = null;
    }
}
