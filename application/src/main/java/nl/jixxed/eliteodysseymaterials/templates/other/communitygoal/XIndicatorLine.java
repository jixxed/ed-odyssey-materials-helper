/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other.communitygoal;

import io.fair_acc.chartfx.Chart;
import io.fair_acc.chartfx.XYChart;
import io.fair_acc.chartfx.axes.Axis;
import io.fair_acc.chartfx.plugins.AbstractSingleValueIndicator;
import io.fair_acc.chartfx.plugins.ValueIndicator;
import io.fair_acc.chartfx.renderer.Renderer;
import io.fair_acc.chartfx.renderer.spi.ErrorDataSetRenderer;
import io.fair_acc.dataset.DataSet;
import io.fair_acc.dataset.GridDataSet;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class XIndicatorLine extends AbstractSingleValueIndicator implements ValueIndicator {
    /**
     * Creates a new instance of the indicator.
     *
     * @param axis  the axis this indicator is associated with
     * @param value a X value to be indicated
     */
    public XIndicatorLine(final Chart chart, final Axis axis, final double value) {
        this(chart, axis, value, null);
    }

    /**
     * Creates a new instance of the indicator.
     *
     * @param axis  the axis this indicator is associated with
     * @param value a X value to be indicated
     * @param text  the text to be shown by the label. Value of {@link #textProperty()}.
     */
    public XIndicatorLine(final Chart chart, final Axis axis, final double value, final String text) {
        super(axis, value, text);
        setLabelPosition(0.04);
        chart.getPlotArea().addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseEvent);
        chart.getPlotArea().addEventHandler(MouseEvent.MOUSE_EXITED, this::handleMouseEvent);
        chart.addEventHandler(MouseEvent.MOUSE_EXITED, this::handleMouseEvent);
        setPickingDistance(100);
    }

    protected void handleMouseEvent(final MouseEvent mouseEvent) {
        if (!isEditable()) {
            return;
        }

        final Bounds plotAreaBounds = getChart().getPlotArea().getBoundsInLocal();
        Optional<DataPoint> dataPoints = findDataPoints(mouseEvent, plotAreaBounds);
        dataPoints.ifPresent(dataPoint -> valueProperty().set(dataPoint.x));

        if (mouseOOB(mouseEvent)) {
            getChartChildren().clear();
        } else {
            runPostLayout();
        }
        mouseEvent.consume();
    }

    protected Optional<DataPoint> findDataPoints(final MouseEvent event, final Bounds plotAreaBounds) {
        if (!plotAreaBounds.contains(event.getX(), event.getY())) {
            return Optional.empty();
        }

        final Point2D mouseLocation = getLocationInPlotArea(event);

        return findNearestDataPointWithinPickingDistance(mouseLocation);
    }

    protected Optional<DataPoint> findNearestDataPointWithinPickingDistance(final Point2D mouseLocation) {
        final Chart chart = getChart();
        if (!(chart instanceof XYChart)) {
            return Optional.empty();
        }
        final XYChart xyChart = (XYChart) chart;
        final ObservableList<DataSet> xyChartDatasets = xyChart.getDatasets();
        return xyChart.getRenderers().stream() // for all renderers
                .flatMap(renderer -> Stream.of(renderer.getDatasets(), xyChartDatasets) //
                        .flatMap(List::stream) // combine global and renderer specific Datasets
                        .flatMap(dataset -> getPointsCloseToCursor(dataset, renderer, mouseLocation))) // get points in range of cursor
                .reduce((p1, p2) -> p1.distanceFromMouse <= p2.distanceFromMouse ? p1 : p2); // find closest point, tie-breaking in favor of earlier data sets to match rendering order

    }

    protected Stream<DataPoint> getPointsCloseToCursor(final DataSet dataset, final Renderer renderer, final Point2D mouseLocation) {
        // Get Axes for the Renderer
        final Axis xAxis = findXAxis(renderer);
        final Axis yAxis = findYAxis(renderer);
        if (xAxis == null || yAxis == null) {
            return Stream.empty(); // ignore this renderer because there are no valid axes available
        }

        if (dataset instanceof GridDataSet) {
            return Stream.empty(); // TODO: correct impl for grid data sets
        }

        return dataset.lock().readLockGuard(() -> {
            int minIdx = 0;
            int maxIdx = dataset.getDataCount();

            if (isDataSorted(renderer)) {
                // get the screen x coordinates and dataset indices between which points can be in picking distance
                final double xMin = xAxis.getValueForDisplay(mouseLocation.getX() - getPickingDistance());
                final double xMax = xAxis.getValueForDisplay(mouseLocation.getX() + getPickingDistance());

                minIdx = Math.max(0, dataset.getIndex(DataSet.DIM_X, xMin) - 1);
                maxIdx = Math.min(dataset.getDataCount(), dataset.getIndex(DataSet.DIM_X, xMax) + 1);
            }

            return IntStream.range(minIdx, maxIdx) // loop over all candidate points
                    .mapToObj(i -> getDataPointFromDataSet(renderer, dataset, xAxis, yAxis, mouseLocation, i)) // get points with distance to mouse
                    .filter(p -> p.distanceFromMouse <= getPickingDistance()) // filter out points which are too far away
                    .map(dataPoint -> dataPoint.withFormattedLabel(formatLabel(dataPoint)))
                    .toList() // Realize list so that calculations are done within the data set lock
                    .stream();
        });
    }

    private boolean mouseOOB(MouseEvent mouseEvent) {
        Point2D point2D = getChart().getCanvas().sceneToLocal(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        return !getChart().getCanvas().getLayoutBounds().contains(point2D);
    }

    @Override
    public void runPostLayout() {
        if (getChart() == null) {
            return;
        }

        final Bounds plotAreaBounds = getChart().getCanvas().getBoundsInLocal();
        final double minX = plotAreaBounds.getMinX();
        final double minY = plotAreaBounds.getMinY();
        final double maxY = plotAreaBounds.getMaxY();
        final double xPos = minX + getAxis().getDisplayPosition(getValue());

        layoutLine(xPos, minY, xPos, maxY);
    }

    private boolean isDataSorted(final Renderer renderer) {
        return renderer instanceof ErrorDataSetRenderer && ((ErrorDataSetRenderer) renderer).isAssumeSortedData();
    }

    private Axis findYAxis(final Renderer renderer) {
        return renderer.getAxes().stream().filter(ax -> ax.getSide().isVertical()).findFirst().orElse(null);
    }

    private Axis findXAxis(final Renderer renderer) {
        return renderer.getAxes().stream().filter(ax -> ax.getSide().isHorizontal()).findFirst().orElse(null);
    }

    protected DataPoint getDataPointFromDataSet(final Renderer renderer, final DataSet dataset, final Axis xAxis, final Axis yAxis, final Point2D mouseLocation, final int index) {
        final double xValue = dataset.get(DataSet.DIM_X, index);
        final double yValue = dataset.get(DataSet.DIM_Y, index);

        final double displayPositionX = xAxis.getDisplayPosition(xValue);
        final double distanceFromMouseLocation = new Point2D(displayPositionX, 0).distance(new Point2D(mouseLocation.getX(), 0));

        final String dataLabelSafe = getDataLabelSafe(dataset, index);

        return new DataPoint( //
                renderer, //
                xValue, //
                yValue, //
                dataset.getName(), //
                dataLabelSafe, //
                displayPositionX,
                distanceFromMouseLocation);
    }

    protected String formatDataPoint(final DataPoint dataPoint) {
        return Formatters.NUMBER_FORMAT_0.format(dataPoint.y);
    }

    protected String formatLabel(DataPoint dataPoint) {
        return String.format("%s: %s", dataPoint.datasetName, formatDataPoint(dataPoint));
    }

    protected String getDataLabelSafe(final DataSet dataSet, final int index) {
        String labelString = dataSet.getDataLabel(index);
        if (labelString == null) {
            return String.format("%s [%d]", dataSet.getName(), index);
        }
        return labelString;
    }

    public static class DataPoint {
        public final Renderer renderer;
        public final double x;
        public final double y;
        public final double xPos;
        public final String datasetName;
        public final String label;
        public final String formattedLabel; // may be empty
        public final double distanceFromMouse;

        public DataPoint(Renderer renderer, double x, double y, String datasetName, String label, double xPos, double distanceFromMouse, String formattedLabel) {
            this.renderer = renderer;
            this.x = x;
            this.y = y;
            this.xPos = xPos;
            this.datasetName = datasetName;
            this.label = label;
            this.distanceFromMouse = distanceFromMouse;
            this.formattedLabel = formattedLabel;
        }

        public DataPoint(Renderer renderer, double x, double y, String datasetName, String label, double xPos, double distanceFromMouse) {
            this(renderer, x, y, datasetName, label, xPos, distanceFromMouse, "");
        }

        public DataPoint withFormattedLabel(String formattedLabel) {
            return new DataPoint(renderer, x, y, datasetName, formattedLabel, xPos, distanceFromMouse, formattedLabel);
        }
    }

    @Override
    public void updateStyleClass() {
        setStyleClasses(line, "x-", AbstractSingleValueIndicator.STYLE_CLASS_LINE);
    }
}
