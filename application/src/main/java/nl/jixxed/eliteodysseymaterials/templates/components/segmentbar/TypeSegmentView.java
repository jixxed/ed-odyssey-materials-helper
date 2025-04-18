package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;

import java.util.Map;

public class TypeSegmentView extends DestroyableStackPane {

    public TypeSegmentView(final TypeSegment segment, final Map<SegmentType, Color> colorMap, final boolean withLabel) {
        this.getStyleClass().add(getStyleClassName(segment));
        final DestroyableLabel label;
        final Color color = colorMap.get(segment.getSegmentType());
        this.setStyle("-fx-background-color: " + toHexString(color) + ";");
        this.setPadding(new Insets(5.0));
        this.setPrefHeight(30.0);
        if (withLabel) {
            label = LabelBuilder.builder()
                    .withStyleClass("horizons-materialcard-amount")//horizons-materialcard-amount
                    .withVisibilityProperty(segment.textProperty().isNotEqualTo("0"))
                    .build();
            label.addBinding(label.textProperty(), segment.textProperty());
            this.getNodes().add(label);
        }
    }

    public TypeSegmentView(final TypeSegment segment, final boolean withLabel) {
        this.getStyleClass().add(getStyleClassName(segment));
        final DestroyableLabel label;
        if (withLabel) {
            label = LabelBuilder.builder()
                    .withStyleClass("horizons-materialcard-amount")//horizons-materialcard-amount
                    .withVisibilityProperty(segment.textProperty().isNotEqualTo("0"))
                    .build();
            label.addBinding(label.textProperty(), segment.textProperty());
            this.getNodes().add(label);
        }
    }

    private static String getStyleClassName(TypeSegment segment) {
        return segment.segmentType.name()
                .toLowerCase()
                .replace("_", "-");
    }

    private String format(final double val) {
        final String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    private String toHexString(final Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }
}
