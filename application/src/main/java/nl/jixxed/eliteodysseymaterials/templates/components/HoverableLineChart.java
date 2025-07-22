package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.CircleBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LineBuilder;
import nl.jixxed.eliteodysseymaterials.builder.RectangleBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableText;

@Slf4j
public class HoverableLineChart extends LineChart<Number, Number> implements DestroyableTemplate {
    private final String tooltipLocaleKey;
    NumberAxisContainer xAxis;
    NumberAxisContainer yAxis;

    public HoverableLineChart(NumberAxisContainer xAxis, NumberAxisContainer yAxis, String tooltipLocaleKey) {
        super(xAxis.axis(), yAxis.axis());
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.tooltipLocaleKey = tooltipLocaleKey;
        initComponents();
    }

    public HoverableLineChart(NumberAxisContainer xAxis, NumberAxisContainer yAxis, ObservableList<Series<Number, Number>> data, String tooltipLocaleKey) {
        super(xAxis.axis(), yAxis.axis(), data);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.tooltipLocaleKey = tooltipLocaleKey;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("hoverable-line-chart");
        Line verticalLine = LineBuilder.builder()
                .withStyleClass("vertical-line")
                .withMouseTransparent(true)
                .build();
        // Create vertical line and add it to the chart
        verticalLine.setStrokeWidth(1);
        verticalLine.setStroke(javafx.scene.paint.Color.LIGHTGRAY);
        verticalLine.setMouseTransparent(true);
        Circle circle = CircleBuilder.builder()
                .withStyleClass("circle")
                .withRadius(5D)
                .withMouseTransparent(true)
                .build();
        Rectangle rect = RectangleBuilder.builder()
                .withStyleClass("text-background")
                .withMouseTransparent(true)
                .build();
//        circle.setStrokeWidth(1);
//        circle.setRadius(5);
//        circle.setStroke(Color.BLUE);
//        circle.setMouseTransparent(true);
        DestroyableText tooltip = TextBuilder.builder()
                .withStyleClass("tooltip-text")
                .withText("blank")
                .build();
        // Create tooltip and add it to the chart
//        tooltip.setStyle("-fx-font-size: 12px; -fx-fill: black;");
        tooltip.setMouseTransparent(true);

        ((Pane) this.lookup(".chart-content")).getChildren().add(verticalLine);
        ((Pane) this.lookup(".chart-content")).getChildren().add(circle);
        ((Pane) this.lookup(".chart-content")).getChildren().add(rect);
        ((Pane) this.lookup(".chart-content")).getChildren().add(tooltip);

        verticalLine.setVisible(false);
        circle.setVisible(false);
        rect.setVisible(false);
        tooltip.setVisible(false);
        // Add mouse movement handler
        this.setOnMouseMoved(event -> {
            if (this.getData().isEmpty()) {
                return;
            }
            // Get the plot area bounds
            javafx.scene.layout.Region plotBackground = (javafx.scene.layout.Region) this.lookup(".chart-plot-background");
            double plotX = plotBackground.getBoundsInParent().getMinX();
            double plotY = plotBackground.getBoundsInParent().getMinY();
            double plotWidth = plotBackground.getBoundsInParent().getWidth();
            double plotHeight = plotBackground.getBoundsInParent().getHeight();

            // Get X axis range
//            NumberAxis xAxis = (NumberAxis) chart.getXAxis();
            double minX = ((NumberAxis) getXAxis()).getLowerBound();
            double maxX = ((NumberAxis) getXAxis()).getUpperBound();
            double minY = ((NumberAxis) getYAxis()).getLowerBound();
            double maxY = ((NumberAxis) getYAxis()).getUpperBound();

            // Get mouse position relative to plot area
            double mouseX = event.getX() - plotX;
            double mouseY = event.getY() - plotY;

//             debug = "";
            // Find nearest data point
//            XYChart.Series<Number, Number> series = this.getData().getFirst();
            XYChart.Series<Number, Number> nearestSeries = this.getData().getFirst();
            double nearestX = Double.MAX_VALUE;
            double nearestY = Double.MAX_VALUE;
            double minDistance = Double.MAX_VALUE;
            double minDistanceX = Double.MAX_VALUE;
            double minDistanceY = Double.MAX_VALUE;
            double nearestDataX = Double.MAX_VALUE;
            double nearestDataY = Double.MAX_VALUE;
            for (var series : this.getData()) {
                for (XYChart.Data<Number, Number> data : series.getData()) {
                    double dataX = data.getXValue().doubleValue();
                    double dataY = data.getYValue().doubleValue();
                    // Add grid line offset to align with chart grid
                    double gridOffsetX = 0;//-4; // Half-pixel offset for grid alignment
                    double gridOffsetY = -4;
                    double distanceX = Math.abs(dataX - (mouseX + gridOffsetX) * (maxX - minX) / plotWidth + minX);
//                    double distanceY = plotHeight - Math.abs(dataY - (mouseY + gridOffsetY) * (maxY - minY) / plotHeight + minY);
//                    double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                    double distanceY = Math.abs((mouseY + gridOffsetY) - ((maxY - dataY) / maxY * plotHeight));
                    if (distanceX < minDistanceX) {
//                        if (distanceX <= minDistanceX) {
//                            log.debug("double {} = {} - Math.abs({} - ({} + {}) * ({} - {}) / {} + {});", distanceY, plotHeight, dataY, mouseY, gridOffsetY, maxY, minY, plotHeight, minY);
//                        log.debug("series: {}, distanceX: {}, distanceY: {}, minDistanceY: {}", series.getName(), distanceX, distanceY, minDistanceY);
//                        }
                        minDistanceX = distanceX;
                        minDistanceY = distanceY;
                        nearestSeries = series;
                    } else if (distanceX == minDistanceX) {
//                        if (distanceX <= minDistanceX) {
//                            log.debug("double {} = {} - Math.abs({} - ({} + {}) * ({} - {}) / {} + {});", distanceY, plotHeight, dataY, mouseY, gridOffsetY, maxY, minY, plotHeight, minY);
//                        log.debug("series: {}, distanceX: {}, distanceY: {}, minDistanceY: {}", series.getName(), distanceX, distanceY, minDistanceY);
//                        }
//                        minDistanceX = distanceX;
                        if (distanceY < minDistanceY) {
                            minDistanceY = distanceY;
                            nearestSeries = series;
                        }
                    }
                    //                if (distanceY < minDistanceY) {
                    //                    minDistanceY = distanceY;
                    //                    nearestY = (int)dataY;
                    //                    nearestDataY = (int)dataY;
                    //                }
                }
            }
            nearestX = Double.MAX_VALUE;
            nearestY = Double.MAX_VALUE;
            minDistance = Double.MAX_VALUE;
            minDistanceX = Double.MAX_VALUE;
            minDistanceY = Double.MAX_VALUE;
            nearestDataX = Double.MAX_VALUE;
            nearestDataY = Double.MAX_VALUE;
            for (XYChart.Data<Number, Number> data : nearestSeries.getData()) {
                double dataX = data.getXValue().doubleValue();
                double dataY = data.getYValue().doubleValue();
                // Add grid line offset to align with chart grid
                double gridOffsetX = -4; // Half-pixel offset for grid alignment
                double gridOffsetY = -4;
                double distanceX = Math.abs(dataX - (mouseX + gridOffsetX) * (maxX - minX) / plotWidth + minX);
                double distanceY = Math.abs(dataY - (mouseY + gridOffsetY) * (maxY - minY) / plotHeight + minY);

                if (distanceX < minDistanceX) {
                    minDistanceX = distanceX;
                    nearestX = dataX;
                    nearestY = dataY;
                    nearestY = dataY;
                    nearestDataY = dataY;
                    nearestDataX = dataX;
                }
//                if (distanceY < minDistanceY) {
//                    minDistanceY = distanceY;
//                    nearestY = (int)dataY;
//                    nearestDataY = (int)dataY;
//                }
            }

            // Snap to nearest data point if within plot area and we found a valid point
            if (mouseX >= 0 && mouseX <= plotWidth + 8 && mouseY >= 0 && mouseY <= plotHeight + 8 && minDistanceX < Double.MAX_VALUE) {
                // Snap to nearest data point's X value
                double snappedX = (nearestDataX - minX) / (maxX - minX) * plotWidth;
                double snappedY = (nearestDataY - minY) / (maxY - minY) * plotHeight;


                verticalLine.setStartX(snappedX + plotX);
                verticalLine.setEndX(snappedX + plotX);
                verticalLine.setStartY(plotY);
                verticalLine.setEndY(plotY + plotHeight);
                circle.setCenterX(snappedX + plotX);
                circle.setCenterY(plotHeight - snappedY + plotY);

                // Position tooltip next to the vertical line
                double tooltipX = snappedX + plotX + 15;
                double tooltipY = plotHeight - snappedY + plotY; // Center vertically in plot area
                if (mouseX > (plotWidth / 2)) {
                    tooltipX = tooltipX - tooltip.getLayoutBounds().getWidth() - 30;
                }
                tooltip.setLayoutX(tooltipX);
                tooltip.setLayoutY(tooltipY);

                rect.setLayoutX(tooltipX - 5);

                rect.setLayoutY(tooltipY - (tooltip.getLayoutBounds().getHeight() / 4));
                rect.setWidth(tooltip.getLayoutBounds().getWidth() + 10);
                rect.setHeight(tooltip.getLayoutBounds().getHeight() + 10);
                verticalLine.setVisible(true);
                circle.setVisible(true);
                rect.setVisible(true);
                tooltip.setVisible(true);
            } else {
                verticalLine.setVisible(false);
                circle.setVisible(false);
                rect.setVisible(false);
                tooltip.setVisible(false);
//                verticalLine.setStartX(-1); // Hide the line if outside plot area
//                verticalLine.setEndX(-1);
//                circle.setCenterX(-100); // Hide the line if outside plot area
//                circle.setCenterY(-100);
//
//                // Hide tooltip when line is hidden
//                tooltip.setLayoutX(-1000);
//                tooltip.setLayoutY(-1000);
            }

            // Find nearest data point
//            XYChart.Series<Number, Number> series = chart.getData().get(0);
            nearestX = Double.MAX_VALUE;
            nearestY = Double.MAX_VALUE;
            minDistanceX = Double.MAX_VALUE;
            minDistanceY = Double.MAX_VALUE;
            nearestDataX = Double.MAX_VALUE;
            nearestDataY = Double.MAX_VALUE;

            for (XYChart.Data<Number, Number> data : nearestSeries.getData()) {
                double dataX = data.getXValue().doubleValue();
                double dataY = data.getYValue().doubleValue();
                // Add grid line offset to align with chart grid
                double gridOffsetX = -4; // Half-pixel offset for grid alignment
                double gridOffsetY = 4;
                double distanceX = Math.abs(dataX - (mouseX + gridOffsetX) * (maxX - minX) / plotWidth + minX);
                double distanceY = Math.abs(dataY - (mouseY + gridOffsetY) * (maxY - minY) / plotHeight + minY);

                if (distanceX < minDistanceX) {
                    minDistanceX = distanceX;
                    nearestX = dataX;
                    nearestY = dataY;
                    nearestDataX = dataX;
                }
//                if (distanceY < minDistanceY) {
//                    minDistanceY = distanceY;
//                    nearestY = (int)dataY;
//                    nearestDataY = (int)dataY;
//                }
            }

            // Update tooltip with proper X axis scaling
            double mouseXValue = mouseX * (maxX - minX) / plotWidth + minX;
//            tooltip.setText(String.format("Speed: %.1f%%\n%s: %.1f °/s", nearestX, nearestSeries.getName(), nearestY));
            tooltip.textProperty().bind(LocaleService.getStringBinding(tooltipLocaleKey,
                    LocaleService.LocalizationKey.of(xAxis.tooltipLocaleKey()), Formatters.NUMBER_FORMAT_0.format(nearestX),
                    this.getData().get(0).getName(), Formatters.NUMBER_FORMAT_1.format(this.getData().get(0).getData().get((int) nearestX).getYValue()),
                    this.getData().get(1).getName(), Formatters.NUMBER_FORMAT_1.format(this.getData().get(1).getData().get((int) nearestX).getYValue()),
                    this.getData().get(2).getName(), Formatters.NUMBER_FORMAT_1.format(this.getData().get(2).getData().get((int) nearestX).getYValue())
            ));
        });
    }
}
