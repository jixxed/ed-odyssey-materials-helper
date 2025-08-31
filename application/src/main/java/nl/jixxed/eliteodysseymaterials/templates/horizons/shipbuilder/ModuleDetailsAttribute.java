package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
public class ModuleDetailsAttribute extends DestroyableVBox implements DestroyableTemplate {

    private final HorizonsModifier horizonsModifier;
    private final ShipModule shipModule;
    private final int index;
    private final int size;
    private DestroyableHBox values;

    public ModuleDetailsAttribute(HorizonsModifier horizonsModifier, ShipModule shipModule, int index, int size) {
        this.horizonsModifier = horizonsModifier;
        this.shipModule = shipModule;
        this.index = index;
        this.size = size;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("attribute");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("first-row"), ModuleDetailsHelper.isFirstRow(index));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("last-row"), ModuleDetailsHelper.isLastRow(index, size));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("left-column"), ModuleDetailsHelper.isLeftColumn(index));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("right-column"), ModuleDetailsHelper.isRightColumn(index));
        DestroyableLabel name = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(horizonsModifier.getLocalizationKey())
                .build();
        this.getNodes().add(name);

        final Object originalAttributeValue = shipModule.getOriginalAttributeValue(horizonsModifier);

        values = BoxBuilder.builder()
                .buildHBox();
        if (originalAttributeValue instanceof Boolean) {
            booleanLine(horizonsModifier, values);
        } else {
            if (isModifiedAttribute() || isSynthesized(horizonsModifier)) {
                addBaseValue(horizonsModifier, shipModule, values);
                if(isModifiedAttribute()) {
                    if (shipModule.isLegacy() && shipModule.getModifiers().containsKey(horizonsModifier)) {
                        final Double currentValue = Double.parseDouble(shipModule.getModifiers().get(horizonsModifier).toString());
                        addValues(horizonsModifier, shipModule, values, currentValue);
                    } else {
                        final Double estimatedValue = (Double) shipModule.getAttributeValue(horizonsModifier, false);
                        addValues(horizonsModifier, shipModule, values, estimatedValue);
                    }
                    if (!shipModule.getModifications().isEmpty()) {
                        addTooltip();
                    }
                }
                if(isSynthesized(horizonsModifier)) {
                    final Double estimatedValue = (Double) shipModule.getAttributeValue(horizonsModifier, true);
                    addValues(horizonsModifier, shipModule, values, estimatedValue);
                }
            } else {
                values.getNodes().add(LabelBuilder.builder()
                        .withStyleClass("value")
                        .withNonLocalizedText(horizonsModifier.format(originalAttributeValue))
                        .build());
            }
        }

        double keyWidth = computeTextWidth(name);
        double valueWidth = computeValuesWidth(values);
        double maxWidth = 21 * name.getFont().getSize();
        var texts = BoxBuilder.builder()
                .withStyleClass("texts")
                .buildVBox();
        if (keyWidth + valueWidth <= maxWidth) {
            // They fit on the same line
            var line = BoxBuilder.builder().withNodes(name, new GrowingRegion(), values).buildHBox();
            texts.getNodes().add(line);
        } else {
            // Key on first line
            var keyline = BoxBuilder.builder().withNodes(name).buildHBox();
            var valueline = BoxBuilder.builder().withNodes(new GrowingRegion(), values).buildHBox();
            texts.getNodes().addAll(keyline, valueline);
        }
        this.getNodes().add(texts);
        if (!shipModule.isLegacy() && originalAttributeValue instanceof Double && isModifiedAttribute()) {
//            if (shipModule.getModifiers().containsKey(horizonsModifier)) {
//                final double currentValue = Double.parseDouble(shipModule.getModifiers().get(horizonsModifier).toString());
//                addProgress(horizonsModifier, shipModule, currentValue);
//            } else {
                final Double estimatedValue = (Double) shipModule.getAttributeValue(horizonsModifier, false);
                addProgress(horizonsModifier, shipModule, estimatedValue);
//            }
        }
    }

    private boolean isSynthesized(HorizonsModifier horizonsModifier) {
        return shipModule.isSynthesized(horizonsModifier);
    }

    private void addProgress(HorizonsModifier horizonsModifier, ShipModule shipModule, double currentValue) {
        double originalAttributeValue = (Double) shipModule.getOriginalAttributeValue(horizonsModifier);
        BigDecimal presentValue = shipModule.getAttributeCompleteness(horizonsModifier, false).multiply(BigDecimal.valueOf(100));
        var present = new TypeSegment(Math.max(0D, presentValue.doubleValue()), SegmentType.PRESENT);

        var notPresent = new TypeSegment(Math.max(0D, (100D - presentValue.doubleValue())), SegmentType.NOT_PRESENT);
        DestroyableSegmentedBar<TypeSegment> segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withStyleClass("progress")
                .withSegments(present, notPresent)
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segment -> {
                    var segmentView = new TypeSegmentView(segment, false);
                    segmentView.pseudoClassStateChanged(PseudoClass.getPseudoClass("positive"), currentValue > originalAttributeValue && horizonsModifier.isHigherBetter() || currentValue < originalAttributeValue && !horizonsModifier.isHigherBetter());
                    return segmentView;
                })
                .build();
        this.getNodes().add(new GrowingRegion());
        this.getNodes().add(segmentedBar);
    }

    private double computeValuesWidth(DestroyableHBox values) {
        double sum = values.getChildren().stream().map(DestroyableLabel.class::cast).mapToDouble(this::computeTextWidth).sum();
        // Add padding to the width
        if (values.getChildren().size() == 1) {
            sum += ScalingHelper.getPixelDoubleFromEm(0.4);
        } else if (values.getChildren().size() == 3) {
            sum += ScalingHelper.getPixelDoubleFromEm(0.8);
        }
        // Add spacing between key and value
        sum += ScalingHelper.getPixelDoubleFromEm(1.0);
        return sum;
    }


    private void booleanLine(HorizonsModifier horizonsModifier, DestroyableHBox valuesLine) {
        final Object estimatedValue = shipModule.getAttributeValue(horizonsModifier, true);
        final DestroyableLabel value = LabelBuilder.builder()
                .withStyleClass("value")
                .withNonLocalizedText(horizonsModifier.format(estimatedValue))
                .build();
        value.pseudoClassStateChanged(PseudoClass.getPseudoClass("positive"), Boolean.TRUE.equals(estimatedValue));

        valuesLine.getNodes().addAll(value);
    }

    private static void addBaseValue(HorizonsModifier horizonsModifier, ShipModule shipModule, DestroyableHBox valuesLine) {
        double originalAttributeValue = (Double) shipModule.getOriginalAttributeValue(horizonsModifier);
        DestroyableLabel original = LabelBuilder.builder()
                .withStyleClass("value")
                .withNonLocalizedText(horizonsModifier.format(originalAttributeValue))
                .build();
        valuesLine.getNodes().addAll(
                original
        );
    }
    private static void addValues(HorizonsModifier horizonsModifier, ShipModule shipModule, DestroyableHBox valuesLine, double currentValue) {
        double originalAttributeValue = (Double) shipModule.getOriginalAttributeValue(horizonsModifier);
        final DestroyableLabel value = LabelBuilder.builder()
                .withStyleClass("value")
                .withNonLocalizedText(horizonsModifier.format(currentValue))
                .build();
        value.pseudoClassStateChanged(PseudoClass.getPseudoClass("positive"), currentValue > originalAttributeValue && horizonsModifier.isHigherBetter() || currentValue < originalAttributeValue && !horizonsModifier.isHigherBetter());
        value.pseudoClassStateChanged(PseudoClass.getPseudoClass("negative"), currentValue < originalAttributeValue && horizonsModifier.isHigherBetter() || currentValue > originalAttributeValue && !horizonsModifier.isHigherBetter());
        DestroyableLabel arrow = LabelBuilder.builder()
                .withStyleClass("arrow")
                .withNonLocalizedText(" → ")
                .build();
        valuesLine.getNodes().addAll(
                arrow,
                value
        );
    }


    private double computeTextWidth(Label label) {
        Text tempText = new Text(label.getText());
        tempText.setFont(label.getFont());
        return tempText.getLayoutBounds().getWidth();
    }

    private void addTooltip() {
        final Double originalAttributeValue = (Double) shipModule.getOriginalAttributeValue(horizonsModifier);
        final Double minValue = (Double) shipModule.getAttributeValue(horizonsModifier, 0.0, false);
        final Double maxValue = (Double) shipModule.getAttributeValue(horizonsModifier, 1.0, false);
        final Double estimatedValue = (Double) shipModule.getAttributeValue(horizonsModifier, false);

        final BigDecimal estimated = !Objects.equals(minValue, maxValue) && (estimatedValue > originalAttributeValue && horizonsModifier.isHigherBetter() || estimatedValue < originalAttributeValue && !horizonsModifier.isHigherBetter())
                ? shipModule.getModifications().getFirst().getModificationCompleteness().orElse(BigDecimal.ZERO).multiply(BigDecimal.valueOf(100))
                : BigDecimal.valueOf(100);

        final boolean hasActualValue = shipModule.getModifiers().containsKey(horizonsModifier);

        String minValueText = horizonsModifier.format(minValue);
        String estValueText = horizonsModifier.format(estimatedValue) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(estimated) + "%");
        String actValueText = hasActualValue
                ? horizonsModifier.format(shipModule.getModifiers().get(horizonsModifier)) + (shipModule.isLegacy() ? "" : " @ " + Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(shipModule.getAttributeCompleteness(horizonsModifier, false).multiply(BigDecimal.valueOf(100))) + "%")
                : "";
        String maxValueText = horizonsModifier.format(maxValue);
        StringBinding text = hasActualValue
                ? LocaleService.getStringBinding("tab.ships.details.tooltip.actual", minValueText, estValueText, actValueText, maxValueText)
                : LocaleService.getStringBinding("tab.ships.details.tooltip.estimated", minValueText, estValueText, maxValueText);
        final DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText(text)
                .build();
        tooltip.install(values);
    }


    private boolean isModifiedAttribute() {
        final Object originalAttributeValue = shipModule.getOriginalAttributeValue(horizonsModifier);
        final Object maxAttributeValue = shipModule.getAttributeValue(horizonsModifier, 1.0, false);
        return !originalAttributeValue.equals(maxAttributeValue);
    }
}
