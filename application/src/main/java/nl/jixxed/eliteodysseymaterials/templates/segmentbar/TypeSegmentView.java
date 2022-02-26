package nl.jixxed.eliteodysseymaterials.templates.segmentbar;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;

import java.util.Map;

public class TypeSegmentView extends StackPane {

    public TypeSegmentView(final TypeSegment segment, final Map<SegmentType, Color> colorMap, final boolean withLabel) {
        final Label label;
        final Color color = colorMap.get(segment.getSegmentType());
        this.setStyle("-fx-background-color: " + toHexString(color) + ";");
        this.setPadding(new Insets(5.0));
        this.setPrefHeight(30.0);
        if (withLabel) {
            label = LabelBuilder.builder().build();
            label.textProperty().bind(segment.textProperty());
            label.getStyleClass().add("horizons-materialcard-amount");
            this.getChildren().add(label);
            label.visibleProperty().bind(segment.textProperty().isNotEqualTo("0"));
        }
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
