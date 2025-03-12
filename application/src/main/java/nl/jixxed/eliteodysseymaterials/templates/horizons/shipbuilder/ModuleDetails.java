package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ModuleDetails extends VBox implements DestroyableTemplate {

    private VBox properties;

    private VBox attributes;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private Button legacySaveButton;
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
        legacySaveButton = ButtonBuilder.builder().withStyleClass("module-details-save-legacy").withText(LocaleService.getStringBinding("module.details.save.legacy")).build();
        legacySaveButton.setMnemonicParsing(false);
        legacySaveButton.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.isAltDown()) {
                        legacySaveButton.getOnAction().handle(new ActionEvent());
                    }
                }
        );
        moduleName = LabelBuilder.builder().withStyleClass("module-details-title").withNonLocalizedText("").build();
        this.getChildren().add(moduleName);
        moduleName.visibleProperty().bind(hasModule);
        properties = BoxBuilder.builder().buildVBox();
        properties.visibleProperty().bind(hasModule);
        this.getChildren().add(properties);
        final DestroyableLabel moduleSpecs = LabelBuilder.builder().withStyleClass("module-details-title").withText(LocaleService.getStringBinding("module.details.module.specs")).build();
        moduleSpecs.visibleProperty().bind(hasModule);
        this.getChildren().add(moduleSpecs);
        attributes = BoxBuilder.builder().buildVBox();
        attributes.visibleProperty().bind(hasModule);
        this.getChildren().add(attributes);
        this.getChildren().add(new GrowingRegion());
        this.maxHeightProperty().bind(this.layer.heightProperty());
    }

    private void addEngineering(String text) {
        properties.getChildren().add(getSeparator());
        moduleEngineering = LabelBuilder.builder().withStyleClass("module-details-value").withNonLocalizedText(text).build();
        final FlowPane flowPane = getFlowPane(
                LabelBuilder.builder().withStyleClass("module-details-label-title").withText(LocaleService.getStringBinding("module.details.engineering")).build(),
                new HBox(moduleEngineering)
        );
        properties.getChildren().add(flowPane);
    }

    private void addPrice(String text) {
        properties.getChildren().add(getSeparator());
        modulePrice = LabelBuilder.builder().withStyleClass("module-details-value").withText(LocaleService.getStringBinding("module.details.price.value", text)).build();
        final FlowPane flowPane = getFlowPane(
                LabelBuilder.builder().withStyleClass("module-details-label-title").withText(LocaleService.getStringBinding("module.details.price")).build(),
                new HBox(modulePrice)
        );
        properties.getChildren().add(flowPane);
    }

    private void addBuyPrice(String text) {
        properties.getChildren().add(getSeparator());
        modulePrice = LabelBuilder.builder().withStyleClass("module-details-value").withText(LocaleService.getStringBinding("module.details.buyprice.value", text)).build();
        final FlowPane flowPane = getFlowPane(
                LabelBuilder.builder().withStyleClass("module-details-label-title").withText(LocaleService.getStringBinding("module.details.buyprice")).build(),
                new HBox(modulePrice)
        );
        properties.getChildren().add(flowPane);
    }

    private void addRebuyPrice(String text) {
        properties.getChildren().add(getSeparator());
        modulePrice = LabelBuilder.builder().withStyleClass("module-details-value").withText(LocaleService.getStringBinding("module.details.rebuyprice.value", text)).build();
        final FlowPane flowPane = getFlowPane(
                LabelBuilder.builder().withStyleClass("module-details-label-title").withText(LocaleService.getStringBinding("module.details.rebuyprice")).build(),
                new HBox(modulePrice)
        );
        properties.getChildren().add(flowPane);
    }
    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void initEventHandling() {

        EVENT_LISTENERS.add(EventService.addListener(true, this, 9, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
        EVENT_LISTENERS.add(EventService.addListener(true, this, TerminateApplicationEvent.class, event -> executorService.shutdown()));
        EVENT_LISTENERS.add(EventService.addListener(true, this, ModuleHighlightEvent.class, (event) -> {
            this.shipModule = event.getShipModule();

            update();
        }));
    }

    private void update() {
        properties.getChildren().clear();
        this.getChildren().remove(legacySaveButton);
        attributes.getChildren().clear();
        final boolean modulePresent = shipModule != null;
        hasModule.set(false);
        if (modulePresent) {
            attributes.getChildren().add(getSeparator());
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
            properties.getChildren().add(getSeparator());
            moduleName.setText(LocaleService.getLocalizedStringForCurrentLocale(shipModule.getLocalizationKey()) + " " + shipModule.getModuleSize().intValue() + shipModule.getModuleClass() + ((shipModule instanceof HardpointModule hardpointModule ? "-" + hardpointModule.getMounting().getShortName() : "")));

            shipModule.getAttibutes().stream().filter(Predicate.not(shipModule::isHiddenStat)).sorted(Comparator.comparing(HorizonsModifier::getOrder)).forEach(horizonsModifier -> {
                addAttribute(horizonsModifier, shipModule);
            });
            if (shipModule.isLegacy()) {
                legacySaveButton.setOnAction(actionEvent -> {
                    LegacyModuleService.saveLegacyModule(shipModule);
                    EventService.publish(new LegacyModuleSavedEvent());
                });
                this.getChildren().addFirst(legacySaveButton);
            }
        }
        executorService.schedule(() -> Platform.runLater(() -> hasModule.set(modulePresent)), 50, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    private static Separator getSeparator() {
        final Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.getStyleClass().add("module-details-line");
        return separator;
    }

    private void addAttribute(HorizonsModifier horizonsModifier, ShipModule shipModule) {
        final Object originalAttributeValue = shipModule.getOriginalAttributeValue(horizonsModifier);
        final Object minValue = shipModule.getAttributeValue(horizonsModifier, 0.0);
        final Object maxValue = shipModule.getAttributeValue(horizonsModifier, 1.0);
        final Object estimatedValue = shipModule.getAttributeValue(horizonsModifier);
        final HBox valuesLine = BoxBuilder.builder().withNodes().buildHBox();
        final VBox attribute = BoxBuilder.builder().withNodes().buildVBox();

        if (originalAttributeValue instanceof Boolean) {
            booleanLine(horizonsModifier, valuesLine, estimatedValue);
        } else {
            final DestroyableLabel title = LabelBuilder.builder().withStyleClass("module-details-label-title").withText(LocaleService.getStringBinding(horizonsModifier.getLocalizationKey())).build();
            if (isModifiedAttribute(originalAttributeValue, maxValue)) {
                if (shipModule.getModifiers().containsKey(horizonsModifier)) {
                    doubleLineWithActualValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue);
                } else {
                    doubleLIneWithEstimatedValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue, (Double) estimatedValue);
                }
                final FlowPane flowPane = getFlowPane(title, valuesLine);
                this.attributes.visibleProperty().bind(flowPane.needsLayoutProperty().not());

                attribute.getChildren().add(
                        flowPane
                );
                if (!shipModule.getModifications().isEmpty()) {
                    addTooltip(horizonsModifier, shipModule, (Double) originalAttributeValue, (Double) minValue, (Double) maxValue, (Double) estimatedValue, attribute);
                }
                attributes.getChildren().add(attribute);
                attributes.getChildren().add(getSeparator());
            } else {
                valuesLine.getChildren().add(LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(originalAttributeValue)).build());

                final FlowPane flowPane = getFlowPane(title, valuesLine);
                this.attributes.visibleProperty().bind(flowPane.needsLayoutProperty().not());

                attribute.getChildren().add(
                        flowPane
                );
                attributes.getChildren().add(attribute);
                attributes.getChildren().add(getSeparator());
            }
        }
    }

    private FlowPane getFlowPane(DestroyableLabel title, Pane valuesLine) {
        final FlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("fit-to-width")
                .withNodes(
                        title,
                        valuesLine
                )
                .withOrientation(Orientation.HORIZONTAL)
                .build();
        flowPane.maxHeightProperty().bind(title.heightProperty().add(valuesLine.heightProperty()));
        flowPane.prefWidthProperty().bind(this.widthProperty());
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
        final Tooltip tooltip = TooltipBuilder.builder().withShowDelay(Duration.ZERO).withText(text).build();
        Tooltip.install(attribute, tooltip);
    }

    private static void doubleLIneWithEstimatedValue(HorizonsModifier horizonsModifier, ShipModule shipModule, HBox valuesLine, double originalAttributeValue, double estimatedValue) {
        final DestroyableLabel value = LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(estimatedValue) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply(BigDecimal.valueOf(100))) + "%")).build();
        if (estimatedValue > originalAttributeValue && horizonsModifier.isHigherBetter() || estimatedValue < originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-green");
        } else if (estimatedValue < originalAttributeValue && horizonsModifier.isHigherBetter() || estimatedValue > originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-red");
        }
        valuesLine.getChildren().addAll(
                LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(originalAttributeValue)).build(),
                LabelBuilder.builder().withNonLocalizedText(" → ").build(),
                value
        );
    }

    private static void doubleLineWithActualValue(HorizonsModifier horizonsModifier, ShipModule shipModule, HBox valuesLine, double originalAttributeValue) {
        final double currentValue = Double.parseDouble(shipModule.getModifiers().get(horizonsModifier).toString());
        final DestroyableLabel value = LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(currentValue) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply(BigDecimal.valueOf(100))) + "%")).build();
        if (currentValue > originalAttributeValue && horizonsModifier.isHigherBetter() || currentValue < originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-green");
        } else if (currentValue < originalAttributeValue && horizonsModifier.isHigherBetter() || currentValue > originalAttributeValue && !horizonsModifier.isHigherBetter()) {
            value.getStyleClass().add("module-details-label-red");
        }
        valuesLine.getChildren().addAll(
                LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(originalAttributeValue)).build(),
                LabelBuilder.builder().withNonLocalizedText(" → ").build(),
                value
        );
    }

    private void booleanLine(HorizonsModifier horizonsModifier, HBox valuesLine, Object estimatedValue) {
        final DestroyableLabel value = LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(estimatedValue)).build();
        if (Boolean.TRUE.equals(estimatedValue)) {
            value.getStyleClass().add("module-details-label-green");
        }
        final DestroyableLabel title = LabelBuilder.builder().withStyleClass("module-details-label-title").withText(LocaleService.getStringBinding(horizonsModifier.getLocalizationKey())).build();
        valuesLine.getChildren().addAll(
                title,
                new GrowingRegion(),
                value
        );
        title.setPadding(new Insets(0, 0, 0, ScalingHelper.getPixelDoubleFromEm(0.2)));
        valuesLine.setPadding(new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.6), 0, 0));
        attributes.getChildren().add(valuesLine);
    }

    private static boolean isModifiedAttribute(Object originalAttributeValue, Object attributeValue) {
        return !originalAttributeValue.equals(attributeValue);
    }
}
