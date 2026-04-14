/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        final Color color = colorMap.get(segment.getSegmentType());
        this.setStyle("-fx-background-color: " + toHexString(color) + ";");
        this.setPadding(new Insets(5.0));
        this.setPrefHeight(30.0);
        if (withLabel) {
            final DestroyableLabel label = LabelBuilder.builder()
                    .withVisibilityProperty(segment.textProperty().isNotEqualTo("0"))
                    .build();
            label.addBinding(label.textProperty(), segment.textProperty());
            this.getNodes().add(label);
        }
    }

    public TypeSegmentView(final TypeSegment segment, final boolean withLabel) {
        this.getStyleClass().add(getStyleClassName(segment));
        if (withLabel) {
            DestroyableLabel label = LabelBuilder.builder()
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
