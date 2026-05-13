/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class HoverableCategoryLineChart extends LineChart<String, Number> implements DestroyableTemplate {

    private final Function<Map<Object, Object>, StringBinding> tooltipFunction;

    public HoverableCategoryLineChart(
            CategoryAxis xAxis,
            NumberAxis yAxis,
            Function<Map<Object, Object>, StringBinding> tooltipFunction
    ) {
        super(xAxis, yAxis);
        this.tooltipFunction = tooltipFunction;

        initComponents();
    }

    public HoverableCategoryLineChart(
            CategoryAxis xAxis,
            NumberAxis yAxis,
            ObservableList<Series<String, Number>> data,
            Function<Map<Object, Object>, StringBinding> tooltipFunction
    ) {
        super(xAxis, yAxis, data);
        this.tooltipFunction = tooltipFunction;

        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("hoverable-category-line-chart");

        Line verticalLine = new Line();
        Circle circle = new Circle(5);
        Rectangle rect = new Rectangle();

        javafx.scene.text.Text tooltip =
                new javafx.scene.text.Text();

        verticalLine.getStyleClass().add("vertical-line");
        circle.getStyleClass().add("circle");
        rect.getStyleClass().add("text-background");
        tooltip.getStyleClass().add("tooltip-text");

        verticalLine.setMouseTransparent(true);
        circle.setMouseTransparent(true);
        rect.setMouseTransparent(true);
        tooltip.setMouseTransparent(true);

        Pane chartContent = (Pane) lookup(".chart-content");

        chartContent.getChildren().addAll(
                verticalLine,
                circle,
                rect,
                tooltip
        );

        verticalLine.setVisible(false);
        circle.setVisible(false);
        rect.setVisible(false);
        tooltip.setVisible(false);

        setOnMouseMoved(event -> {

            if (getData().isEmpty()) {
                return;
            }

            Region plotArea =
                    (Region) lookup(".chart-plot-background");

            double plotX = plotArea.getBoundsInParent().getMinX();
            double plotY = plotArea.getBoundsInParent().getMinY();
            double plotWidth = plotArea.getBoundsInParent().getWidth();
            double plotHeight = plotArea.getBoundsInParent().getHeight();

            double mouseX = event.getX();

            XYChart.Data<String, Number> nearest = null;
            XYChart.Series<String, Number> nearestSeries = null;

            double nearestDistance = Double.MAX_VALUE;

            for (var series : getData()) {

                for (var data : series.getData()) {

                    double x =
                            getXAxis().getDisplayPosition(
                                    data.getXValue()
                            );

                    double distance =
                            Math.abs(mouseX - x);

                    if (distance < nearestDistance) {

                        nearestDistance = distance;
                        nearest = data;
                        nearestSeries = series;
                    }
                }
            }

            if (nearest == null) {
                return;
            }

            double x =
                    getXAxis().getDisplayPosition(
                            nearest.getXValue()
                    );

            double y =
                    getYAxis().getDisplayPosition(
                            nearest.getYValue()
                    );

            verticalLine.setStartX(x);
            verticalLine.setEndX(x);
            verticalLine.setStartY(plotY);
            verticalLine.setEndY(plotY + plotHeight);

            circle.setCenterX(x);
            circle.setCenterY(y);

            Map<Object, Object> values =
                    new LinkedHashMap<>();

            for (var series : getData()) {

                int index =
                        nearestSeries.getData().indexOf(nearest);

                if (index < series.getData().size()) {

                    values.put(
                            series,
                            series.getData()
                                    .get(index)
                                    .getYValue()
                    );
                }
            }

            values.put("x", nearest.getXValue());

            tooltip.textProperty().unbind();
            tooltip.textProperty().bind(
                    tooltipFunction.apply(values)
            );

            double tooltipX = x + 15;

            if (x > plotWidth / 2) {
                tooltipX -= tooltip.getLayoutBounds().getWidth() + 30;
            }

            double tooltipY = y;

            tooltip.setLayoutX(tooltipX);
            tooltip.setLayoutY(tooltipY);

            rect.setLayoutX(tooltipX - 5);
            rect.setLayoutY(tooltipY - 15);

            rect.setWidth(
                    tooltip.getLayoutBounds().getWidth() + 10
            );

            rect.setHeight(
                    tooltip.getLayoutBounds().getHeight() + 10
            );

            verticalLine.setVisible(true);
            circle.setVisible(true);
            rect.setVisible(true);
            tooltip.setVisible(true);
        });

        setOnMouseExited(event -> {

            verticalLine.setVisible(false);
            circle.setVisible(false);
            rect.setVisible(false);
            tooltip.setVisible(false);
        });
    }
}