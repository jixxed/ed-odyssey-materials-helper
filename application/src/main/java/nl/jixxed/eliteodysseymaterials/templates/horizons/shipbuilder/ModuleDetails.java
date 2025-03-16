package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
public class ModuleDetails extends DestroyableVBox implements DestroyableEventTemplate {

    private DestroyableVBox properties;

    private DestroyableVBox attributes;
    private DestroyableButton legacySaveButton;
    private DestroyableLabel modulePrice;
    private DestroyableLabel moduleEngineering;
    private DestroyableLabel moduleName;
    private BooleanProperty hasModule = new SimpleBooleanProperty(false);
    private ShipModule shipModule;
    DetailsLayer layer;

    public ModuleDetails(DetailsLayer layer) {
        this.layer = layer;
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
                .withOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.isAltDown()) {
                        legacySaveButton.getOnAction().handle(new ActionEvent());
                    }
                })
                .build();

        moduleName = LabelBuilder.builder()
                .withStyleClass("module-details-title")
                .withNonLocalizedText("")
                .build();
        this.getNodes().add(moduleName);
        moduleName.addBinding(moduleName.visibleProperty(), hasModule);
        properties = BoxBuilder.builder()
                .buildVBox();
        properties.addBinding(properties.visibleProperty(), hasModule);
        this.getNodes().add(properties);
        final DestroyableLabel moduleSpecs = LabelBuilder.builder()
                .withStyleClass("module-details-title")
                .withText("module.details.module.specs")
                .build();
        moduleSpecs.addBinding(moduleSpecs.visibleProperty(), hasModule);
        this.getNodes().add(moduleSpecs);
        attributes = BoxBuilder.builder()
                .buildVBox();
        attributes.addBinding(attributes.visibleProperty(), hasModule);
        this.getNodes().add(attributes);
        this.getNodes().add(new GrowingRegion());
        this.addBinding(this.maxHeightProperty(), this.layer.heightProperty());
    }

    private void addEngineering(String text) {
        properties.getNodes().add(getSeparator());
        moduleEngineering = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withNonLocalizedText(text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.engineering")
                        .build(),
                BoxBuilder.builder().withNodes(moduleEngineering).buildHBox()
        );
        properties.getNodes().add(flowPane);
    }

    private void addPrice(String text) {
        properties.getNodes().add(getSeparator());
        modulePrice = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withText("module.details.price.value", text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.price")
                        .build(),
                BoxBuilder.builder().withNodes(modulePrice).buildHBox()
        );
        properties.getNodes().add(flowPane);
    }

    private void addBuyPrice(String text) {
        properties.getNodes().add(getSeparator());
        modulePrice = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withText("module.details.buyprice.value", text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.buyprice")
                        .build(),
                BoxBuilder.builder().withNodes(modulePrice).buildHBox()
        );
        properties.getNodes().add(flowPane);
    }

    private void addRebuyPrice(String text) {
        properties.getNodes().add(getSeparator());
        modulePrice = LabelBuilder.builder()
                .withStyleClass("module-details-value")
                .withText("module.details.rebuyprice.value", text)
                .build();
        final DestroyableFlowPane flowPane = getFlowPane(
                LabelBuilder.builder()
                        .withStyleClass("module-details-label-title")
                        .withText("module.details.rebuyprice")
                        .build(),
                BoxBuilder.builder().withNodes(modulePrice).buildHBox()
        );
        properties.getNodes().add(flowPane);
    }

    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, 9, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
        register(EventService.addListener(true, this, TerminateApplicationEvent.class, event -> executorService.shutdown()));
        register(EventService.addListener(true, this, ModuleHighlightEvent.class, (event) -> {
            this.shipModule = event.getShipModule();

            update();
        }));
    }

    private void update() {
        properties.getNodes().clear();
        this.getNodes().remove(legacySaveButton);
        attributes.getNodes().clear();
        final boolean modulePresent = shipModule != null;
        hasModule.set(false);
        if (modulePresent) {
            attributes.getNodes().add(getSeparator());
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
            properties.getNodes().add(getSeparator());
            moduleName.setText(LocaleService.getLocalizedStringForCurrentLocale(shipModule.getLocalizationKey()) + " " + shipModule.getModuleSize().intValue() + shipModule.getModuleClass() + ((shipModule instanceof HardpointModule hardpointModule ? "-" + hardpointModule.getMounting().getShortName() : "")));

            shipModule.getAttibutes().stream().filter(Predicate.not(shipModule::isHiddenStat)).sorted(Comparator.comparing(HorizonsModifier::getOrder)).forEach(horizonsModifier -> {
                addAttribute(horizonsModifier, shipModule);
            });
            if (shipModule.isLegacy()) {
                legacySaveButton.setOnAction(actionEvent -> {
                    LegacyModuleService.saveLegacyModule(shipModule);
                    EventService.publish(new LegacyModuleSavedEvent());
                });
                this.getNodes().addFirst(legacySaveButton);
            }
        }
        executorService.schedule(() -> Platform.runLater(() -> hasModule.set(modulePresent)), 50, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    private static DestroyableSeparator getSeparator() {
        final DestroyableSeparator separator = new DestroyableSeparator(Orientation.HORIZONTAL);
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
        final DestroyableVBox attribute = BoxBuilder.builder()
                .withNodes().buildVBox();

        if (originalAttributeValue instanceof Boolean) {
            booleanLine(horizonsModifier, valuesLine, estimatedValue);
        } else {
            final DestroyableLabel title = LabelBuilder.builder()
                    .withStyleClass("module-details-label-title")
                    .withText(horizonsModifier.getLocalizationKey())
                    .build();
            if (isModifiedAttribute(originalAttributeValue, maxValue)) {
                if (shipModule.getModifiers().containsKey(horizonsModifier)) {
                    doubleLineWithActualValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue);
                } else {
                    doubleLIneWithEstimatedValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue, (Double) estimatedValue);
                }
                final DestroyableFlowPane flowPane = getFlowPane(title, valuesLine);
                this.attributes.addBinding(this.attributes.visibleProperty(), flowPane.needsLayoutProperty().not());

                attribute.getNodes().add(
                        flowPane
                );
                if (!shipModule.getModifications().isEmpty()) {
                    addTooltip(horizonsModifier, shipModule, (Double) originalAttributeValue, (Double) minValue, (Double) maxValue, (Double) estimatedValue, attribute);
                }
                attributes.getNodes().add(attribute);
                attributes.getNodes().add(getSeparator());
            } else {
                valuesLine.getNodes().add(LabelBuilder.builder()
                        .withStyleClass("module-details-label")
                        .withNonLocalizedText(horizonsModifier.format(originalAttributeValue))
                        .build());

                final DestroyableFlowPane flowPane = getFlowPane(title, valuesLine);
                this.attributes.addBinding(this.attributes.visibleProperty(), flowPane.needsLayoutProperty().not());

                attribute.getNodes().add(
                        flowPane
                );
                attributes.getNodes().add(attribute);
                attributes.getNodes().add(getSeparator());
            }
        }
    }

    private <E extends Pane & DestroyableComponent> DestroyableFlowPane getFlowPane(DestroyableLabel title, E valuesLine) {
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("fit-to-width")
                .withNodes(title, valuesLine)
                .withOrientation(Orientation.HORIZONTAL)
                .build();
        flowPane.addBinding(flowPane.maxHeightProperty(), title.heightProperty().add(valuesLine.heightProperty()));
        flowPane.addBinding(flowPane.prefWidthProperty(), this.widthProperty());
        flowPane.needsLayoutProperty().addListener(((observable, oldValue, newValue) -> {
            final double itemsWidth = flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum();
            final double parentWidth = this.getWidth();
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

    private static void addTooltip(HorizonsModifier horizonsModifier, ShipModule shipModule, Double originalAttributeValue, Double minValue, Double maxValue, Double estimatedValue, VBox attribute) {
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
        final Tooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText(text)
                .build();
        Tooltip.install(attribute, tooltip);
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
        attributes.getNodes().add(valuesLine);
    }

    private static boolean isModifiedAttribute(Object originalAttributeValue, Object attributeValue) {
        return !originalAttributeValue.equals(attributeValue);
    }
}
