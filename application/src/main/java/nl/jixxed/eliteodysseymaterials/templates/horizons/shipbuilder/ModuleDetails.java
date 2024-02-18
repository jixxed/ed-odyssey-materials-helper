package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleHighlightEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class ModuleDetails extends VBox implements Template {
    private static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
    }

    private VBox attributes;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public ModuleDetails() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("stats-values");
        this.getChildren().add(LabelBuilder.builder().withStyleClass("module-details-title").withNonLocalizedText("Module specs").build());
        attributes = BoxBuilder.builder().buildVBox();
        this.getChildren().add(attributes);
        this.getChildren().add(new GrowingRegion());
    }

    @Override
    public void initEventHandling() {
        EVENT_LISTENERS.add(EventService.addListener(this, ModuleHighlightEvent.class, (event) -> {
            ShipModule shipModule = event.getShipModule();
            attributes.getChildren().clear();
            attributes.getChildren().add(new Separator(Orientation.HORIZONTAL));
            if (shipModule != null) {
                shipModule.getAttibutes().stream().filter(Predicate.not(shipModule::isHiddenStat)).sorted(Comparator.comparing(HorizonsModifier::getOrder)).forEach(horizonsModifier -> {
                    addAttribute(horizonsModifier, shipModule);
                });
            }
        }));
    }

    private void addAttribute(HorizonsModifier horizonsModifier, ShipModule shipModule) {
        final Object originalAttributeValue = shipModule.getOriginalAttributeValue(horizonsModifier);
        final Object minValue = shipModule.getAttributeValue(horizonsModifier, 0.0);
        final Object maxValue = shipModule.getAttributeValue(horizonsModifier, 1.0);
        final Object estimatedValue = shipModule.getAttributeValue(horizonsModifier);
        String actualValueLine = "";
        final HBox valuesLine = BoxBuilder.builder().withNodes().buildHBox();
        final VBox attribute = BoxBuilder.builder().withNodes().buildVBox();

        if (originalAttributeValue instanceof Boolean) {
            booleanLine(horizonsModifier, valuesLine, estimatedValue);
        } else {
            final DestroyableLabel title = LabelBuilder.builder().withStyleClass("module-details-label-title").withText(LocaleService.getStringBinding(horizonsModifier.getLocalizationKey())).build();
            if (isModifiedAttribute(originalAttributeValue, maxValue)) {
                if (shipModule.getModifiers().containsKey(horizonsModifier)) {
                    final Object currentValue = shipModule.getModifiers().get(horizonsModifier);
                    doubleLineWithActualValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue);
                    actualValueLine = "ACT: " + horizonsModifier.format(currentValue) + " @ " + NUMBER_FORMAT_2.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply(BigDecimal.valueOf(100))) + "%\n";
                } else {
                    doubleLIneWithEstimatedValue(horizonsModifier, shipModule, valuesLine, (Double) originalAttributeValue, (Double) estimatedValue);
                }
                final FlowPane flowPane = FlowPaneBuilder.builder()
                        .withStyleClass("fit-to-width")
                        .withNodes(
                                title,
                                valuesLine
                        )
                        .withOrientation(Orientation.HORIZONTAL)
                        .build();
                this.attributes.visibleProperty().bind(flowPane.needsLayoutProperty().not());
//                flowPane.setVisible(false);
                flowPane.maxHeightProperty().bind(title.heightProperty().add(valuesLine.heightProperty()));
                flowPane.prefWidthProperty().bind(this.widthProperty());
//                final double itemsWidth2 = flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum();
//                final double parentWidth2 = this.getWidth();
//                if(parentWidth2 < itemsWidth2 + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                    flowPane.setOrientation(Orientation.VERTICAL);
//                    title.setPadding(new Insets(0,parentWidth2 - title.getWidth() ,0, ScalingHelper.getPixelDoubleFromEm(0.2)));
//                    valuesLine.setPadding(new Insets(0 , ScalingHelper.getPixelDoubleFromEm(0.2),0,parentWidth2 - valuesLine.getWidth() - ScalingHelper.getPixelDoubleFromEm(0.2)));
//                }else{
//                    flowPane.setHgap((parentWidth2 - itemsWidth2 - ScalingHelper.getPixelDoubleFromEm(0.4)));
//                    title.setPadding(new Insets(0,0,0, ScalingHelper.getPixelDoubleFromEm(0.2)));
//                    valuesLine.setPadding(new Insets(0 , ScalingHelper.getPixelDoubleFromEm(0.2),0, ScalingHelper.getPixelDoubleFromEm(0.2)));
//                }
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
//                flowPane.orientationProperty().bind(flowPane.maxHeightProperty()
//                        .map(parentWidth -> {
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return Orientation.HORIZONTAL;
//                            }else{
//                                return Orientation.VERTICAL;
//                            }
//                        }));
//                flowPane.hgapProperty().bind(flowPane.orientationProperty()
//                        .map(parentWidth -> {
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return this.getWidth() - flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() - ScalingHelper.getPixelDoubleFromEm(0.4);
//                            }else{
//                                return 0D;
//                            }
//                        }));
//                title.paddingProperty().bind(flowPane.orientationProperty()
//                        .map(parentWidth -> {
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return new Insets(0, 0, 0, ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }else{
//                                return new Insets(0, this.widthProperty().doubleValue() - title.getWidth(), 0, ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }
//                        }));
//                valuesLine.paddingProperty().bind(flowPane.orientationProperty()
//                        .map(parentWidth -> {
////                            flowPane.setVisible(true);
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.2), 0, ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }else{
//                                return new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.2), 0, this.widthProperty().doubleValue() - valuesLine.getWidth() - ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }
//                        }));
                attribute.getChildren().add(
                        flowPane
                );
                if (!shipModule.getModifications().isEmpty()) {
                    addTooltip(horizonsModifier, shipModule, minValue, maxValue, estimatedValue, actualValueLine, attribute);
                }
                attributes.getChildren().add(attribute);
                attributes.getChildren().add(new Separator(Orientation.HORIZONTAL));
            } else {
                valuesLine.getChildren().addAll(
                        LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(originalAttributeValue)).build()
                );

                final FlowPane flowPane = FlowPaneBuilder.builder()
                        .withStyleClass("fit-to-width")
                        .withNodes(
                                title,
                                valuesLine
                        )
                        .withOrientation(Orientation.HORIZONTAL)
                        .build();
                this.attributes.visibleProperty().bind(flowPane.needsLayoutProperty().not());
                flowPane.prefWidthProperty().bind(this.widthProperty());
                flowPane.maxHeightProperty().bind(title.heightProperty().add(valuesLine.heightProperty()));
//                final double itemsWidth2 = flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum();
//                final double parentWidth2 = this.getWidth();
//                if(parentWidth2 < itemsWidth2 + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                    flowPane.setOrientation(Orientation.VERTICAL);
//                    title.setPadding(new Insets(0,parentWidth2 - title.getWidth() ,0, ScalingHelper.getPixelDoubleFromEm(0.2)));
//                    valuesLine.setPadding(new Insets(0 , ScalingHelper.getPixelDoubleFromEm(0.2),0,parentWidth2 - valuesLine.getWidth() - ScalingHelper.getPixelDoubleFromEm(0.2)));
//                }else{
//                    flowPane.setHgap((parentWidth2 - itemsWidth2 - ScalingHelper.getPixelDoubleFromEm(0.4)));
//                    title.setPadding(new Insets(0,0,0, ScalingHelper.getPixelDoubleFromEm(0.2)));
//                    valuesLine.setPadding(new Insets(0 , ScalingHelper.getPixelDoubleFromEm(0.2),0, ScalingHelper.getPixelDoubleFromEm(0.2)));
//                }
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
//                flowPane.orientationProperty().bind(flowPane.maxHeightProperty()
//                        .map(parentWidth -> {
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return Orientation.HORIZONTAL;
//                            }else{
//                                return Orientation.VERTICAL;
//                            }
//                        }));
//                flowPane.hgapProperty().bind(flowPane.orientationProperty()
//                        .map(parentWidth -> {
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return this.getWidth() - flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() - ScalingHelper.getPixelDoubleFromEm(0.4);
//                            }else{
//                                return 0D;
//                            }
//                        }));
//                title.paddingProperty().bind(flowPane.orientationProperty()
//                        .map(parentWidth -> {
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return new Insets(0, 0, 0, ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }else{
//                                return new Insets(0, this.widthProperty().doubleValue() - title.getWidth(), 0, ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }
//                        }));
//                valuesLine.paddingProperty().bind(flowPane.orientationProperty()
//                        .map(parentWidth -> {
////                            flowPane.setVisible(true);
//                            if(this.widthProperty().doubleValue() >= flowPane.getChildren().stream().map(Region.class::cast).mapToDouble(Region::getWidth).sum() + ScalingHelper.getPixelDoubleFromEm(0.8)) {
//                                return new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.2), 0, ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }else{
//                                return new Insets(0, ScalingHelper.getPixelDoubleFromEm(0.2), 0, this.widthProperty().doubleValue() - valuesLine.getWidth() - ScalingHelper.getPixelDoubleFromEm(0.2));
//                            }
//                        }));
                attribute.getChildren().add(
                        flowPane
                );
                attributes.getChildren().add(attribute);
                attributes.getChildren().add(new Separator(Orientation.HORIZONTAL));
            }
        }
    }

    private static void addTooltip(HorizonsModifier horizonsModifier, ShipModule shipModule, Object minValue, Object maxValue, Object estimatedValue, String actualValueLine, VBox attribute) {
        final Tooltip tooltip = TooltipBuilder.builder().withShowDelay(Duration.ZERO).withNonLocalizedText("MIN: " + horizonsModifier.format(minValue) + "\n" +
                "EST: " + horizonsModifier.format(estimatedValue) + " @ " + NUMBER_FORMAT_2.format(shipModule.getModifications().getFirst().getModificationCompleteness().multiply( BigDecimal.valueOf(100)) ) + "%\n" +
                actualValueLine +
                "MAX: " + horizonsModifier.format(maxValue)).build();
        Tooltip.install(attribute, tooltip);
    }

    private static void doubleLIneWithEstimatedValue(HorizonsModifier horizonsModifier, ShipModule shipModule, HBox valuesLine, double originalAttributeValue, double estimatedValue) {
        final DestroyableLabel value = LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(estimatedValue) + " @ " + NUMBER_FORMAT_2.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply( BigDecimal.valueOf(100))) + "%").build();
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
        final double currentValue = (double) shipModule.getModifiers().get(horizonsModifier);
        final DestroyableLabel value = LabelBuilder.builder().withStyleClass("module-details-label").withNonLocalizedText(horizonsModifier.format(currentValue) + " @ " + NUMBER_FORMAT_2.format(shipModule.getAttributeCompleteness(horizonsModifier).multiply( BigDecimal.valueOf(100))) + "%").build();
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
