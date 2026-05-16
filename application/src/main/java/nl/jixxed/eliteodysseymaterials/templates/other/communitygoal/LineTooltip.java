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
import io.fair_acc.chartfx.plugins.AbstractDataFormattingPlugin;
import io.fair_acc.chartfx.renderer.Renderer;
import io.fair_acc.chartfx.renderer.spi.ErrorDataSetRenderer;
import io.fair_acc.dataset.DataSet;
import io.fair_acc.dataset.GridDataSet;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A tool tip label appearing next to the mouse cursor when placed over a data point's symbol. If symbols are not
 * created/shown for given plot, the tool tip is shown for the closest data point that is within the
 * {@link #pickingDistanceProperty()} from the mouse cursor.
 * <p>
 * CSS style class name: {@value #STYLE_CLASS_LABEL}
 * <p>
 * TODO: extend so that label = new Label(); is a generic object and can also be overwritten with
 * another implementation (&lt;-&gt; advanced interactor) additional add/remove listener are needed to
 * edit/update the custom object based on DataPoint (for the time being private class)
 *
 * @author Grzegorz Kruk
 */
public class LineTooltip extends AbstractDataFormattingPlugin implements Destroyable {
    /**
     * Name of the CSS class of the tool tip label.
     */
    public static final String STYLE_CLASS_LABEL = "chart-datapoint-tooltip-label";

    /**
     * The default distance between the data point coordinates and mouse cursor that triggers showing the tool tip
     * label.
     */
    public static final int DEFAULT_PICKING_DISTANCE = 10000;

    private static final int LABEL_X_OFFSET = 15;
    private static final int LABEL_Y_OFFSET = 5;

    private final DestroyableLabel xValue = LabelBuilder.builder().withStyleClass("tooltip-x").build();
    private final DestroyableLabel yNames = LabelBuilder.builder().withStyleClass("tooltip-y-name").build();
    private final DestroyableLabel yValues = LabelBuilder.builder().withStyleClass("tooltip-y-value").build();
    private final DestroyableHBox tooltipValueList = BoxBuilder.builder().withStyleClass("tooltip-list").withNodes(yNames, yValues).buildHBox();
    private final DestroyableVBox tooltip = BoxBuilder.builder().withStyleClass("line-tooltip").withNodes(xValue,tooltipValueList).buildVBox();

    private final DoubleProperty pickingDistance = new SimpleDoubleProperty(this, "pickingDistance", DEFAULT_PICKING_DISTANCE) {
        @Override
        protected void invalidated() {
            if (get() <= 0) {
                throw new IllegalArgumentException("The " + getName() + " must be a positive value");
            }
        }
    };

    private final EventHandler<MouseEvent> mouseMoveHandler = this::updateToolTip;

    /**
     * Creates a new instance of DataPointTooltip class with {{@link #pickingDistanceProperty() picking distance}
     * initialized to {@value #DEFAULT_PICKING_DISTANCE}.
     */
    public LineTooltip(final DestroyableChart chart) {
        tooltip.setManaged(false);
        registerInputEventHandler(MouseEvent.MOUSE_MOVED, mouseMoveHandler);
        registerInputEventHandler(MouseEvent.MOUSE_EXITED, mouseMoveHandler);
        chart.registerEventHandler(MouseEvent.MOUSE_EXITED, mouseMoveHandler);
    }

    /**
     * Creates a new instance of DataPointTooltip class.
     *
     * @param pickingDistance the initial value for the {@link #pickingDistanceProperty() pickingDistance} property
     */
    public LineTooltip(final DestroyableChart chart, final double pickingDistance) {
        this(chart);
        setPickingDistance(pickingDistance);
    }

    protected List<DataPoint> findDataPoints(final MouseEvent event, final Bounds plotAreaBounds) {
        if (!plotAreaBounds.contains(event.getX(), event.getY())) {
            return List.of();
        }

        final Point2D mouseLocation = getLocationInPlotArea(event);

        return findNearestDataPointWithinPickingDistance(mouseLocation);
    }

    protected List<DataPoint> findNearestDataPointWithinPickingDistance(final Point2D mouseLocation) {
        final Chart chart = getChart();
        if (!(chart instanceof XYChart)) {
            return List.of();
        }

        final XYChart xyChart = (XYChart) chart;
        final ObservableList<DataSet> xyChartDatasets = xyChart.getDatasets();
        List<DataPoint> closestPoints = xyChart.getRenderers().stream() // for all renderers
                .flatMap(renderer -> Stream.of(renderer.getDatasets(), xyChartDatasets) //
                        .flatMap(List::stream) // combine global and renderer specific Datasets
                        .map(dataset -> getPointsCloseToCursor(dataset, renderer, mouseLocation, -1D)
                                .reduce((p1, p2) -> p1.distanceFromMouse <= p2.distanceFromMouse ? p1 : p2))) // get points in range of cursor
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
//                .reduce((p1, p2) -> p1.distanceFromMouse <= p2.distanceFromMouse ? p1 : p2);
        Double maxX = closestPoints.stream()
                .reduce((p1, p2) -> p1.distanceFromMouse <= p2.distanceFromMouse ? p1 : p2)//closest point to the mouse
                .map(dp -> dp.x)
                .orElse(-1D);
        return xyChart.getRenderers().stream() // for all renderers
                .flatMap(renderer -> Stream.of(renderer.getDatasets(), xyChartDatasets) //
                        .flatMap(List::stream) // combine global and renderer specific Datasets
                        .map(dataset -> getPointsCloseToCursor(dataset, renderer, mouseLocation, maxX)
                                .reduce((p1, p2) -> p1.distanceFromMouse <= p2.distanceFromMouse ? p1 : p2))) // get points in range of cursor
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

    }

    protected Stream<DataPoint> getPointsCloseToCursor(final DataSet dataset, final Renderer renderer, final Point2D mouseLocation, final double maxX) {
        // Get Axes for the Renderer
        final Axis xAxis = findXAxis(renderer);
        if (xAxis == null) {
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
                    .mapToObj(i -> getDataPointFromDataSet(renderer, dataset, xAxis, mouseLocation, i)) // get points with distance to mouse
                    .filter(p -> maxX == -1D || p.x <= maxX) // filter out points which are after maxX
                    .filter(p -> p.distanceFromMouse <= getPickingDistance()) // filter out points which are too far away
                    .map(dataPoint -> dataPoint.withFormattedLabel(formatValueLabel(dataPoint), formatNameLabel(dataPoint)));
//                    .collect(Collectors.toList()) // Realize list so that calculations are done within the data set lock
//                    .stream();
        });
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

    protected DataPoint getDataPointFromDataSet(final Renderer renderer, final DataSet dataset, final Axis xAxis, final Point2D mouseLocation, final int index) {
        final double xValue = dataset.get(DataSet.DIM_X, index);
        final double yMaxValue = dataset.get(DataSet.DIM_Y, index);
        final double yMinValue = dataset.get(DataSet.DIM_Z, index);

        final double displayPositionX = xAxis.getDisplayPosition(xValue);
        final double distanceFromMouseLocation = new Point2D(displayPositionX, 0).distance(new Point2D(mouseLocation.getX(), 0));

        final String dataLabelSafe = getDataLabelSafe(dataset, index);

        return new DataPoint( //
                renderer, //
                xValue, //
                yMinValue, //
                yMaxValue, //
                dataset.getName(), //
                dataLabelSafe, //
                displayPositionX,
                distanceFromMouseLocation);
    }

    protected String formatValueLabel(DataPoint dataPoint) {
        if(dataPoint.yMin == dataPoint.yMax) {
            return String.format("%s",  Formatters.NUMBER_FORMAT_0.format(dataPoint.yMin));
        }else{
            return String.format("%s - %s",  Formatters.NUMBER_FORMAT_0.format(dataPoint.yMin), Formatters.NUMBER_FORMAT_0.format(dataPoint.yMax));
        }
    }
    protected String formatNameLabel(DataPoint dataPoint) {
            return String.format("%s:", dataPoint.datasetName);
    }

    protected String getDataLabelSafe(final DataSet dataSet, final int index) {
        String labelString = dataSet.getDataLabel(index);
        if (labelString == null) {
            return String.format("%s [%d]", dataSet.getName(), index);
        }
        return labelString;
    }

    /**
     * Returns the value of the {@link #pickingDistanceProperty()}.
     *
     * @return the current picking distance
     */
    public final double getPickingDistance() {
        return pickingDistanceProperty().get();
    }

    /**
     * Distance of the mouse cursor from the data point (expressed in display units) that should trigger showing the
     * tool tip. By default initialized to {@value #DEFAULT_PICKING_DISTANCE}.
     *
     * @return the picking distance property
     */
    public final DoubleProperty pickingDistanceProperty() {
        return pickingDistance;
    }

    /**
     * Sets the value of {@link #pickingDistanceProperty()}.
     *
     * @param distance the new picking distance
     */
    public final void setPickingDistance(final double distance) {
        pickingDistanceProperty().set(distance);
    }

    protected void updateLabel(final MouseEvent event, final Bounds plotAreaBounds, Optional<DataPoint> x, final String xText, final String names, final String values) {
        xValue.setText(xText);
        yValues.setText(values);
        yNames.setText(names);
        final double mouseX = x.map(dataPoint -> dataPoint.xPos).orElse(event.getX());
        final double spaceLeft = mouseX - plotAreaBounds.getMinX();
        final double spaceRight = plotAreaBounds.getWidth() - spaceLeft;
        double width = tooltip.prefWidth(-1);
        boolean atSide = true; // set to false if we cannot print the tooltip beside the point

        double xLocation;
        if (spaceRight >= width + LABEL_X_OFFSET) { // place to right if enough space
            xLocation = mouseX + LineTooltip.LABEL_X_OFFSET;
        } else if (spaceLeft >= width + LABEL_X_OFFSET) { // place left if enough space
            xLocation = mouseX - LABEL_X_OFFSET - width;
        } else if (width < plotAreaBounds.getWidth()) {
            xLocation = spaceLeft > spaceRight ? plotAreaBounds.getMaxX() - width : plotAreaBounds.getMinX();
            atSide = false;
        } else {
            width = plotAreaBounds.getWidth();
            xLocation = plotAreaBounds.getMinX();
            atSide = false;
        }

        final double mouseY = event.getY();
        final double spaceTop = mouseY - plotAreaBounds.getMinY();
        final double spaceBottom = plotAreaBounds.getHeight() - spaceTop;
        double height = tooltip.prefHeight(width);

        double yLocation;
        if (height < spaceBottom) {
            yLocation = mouseY + LineTooltip.LABEL_Y_OFFSET;
        } else if (height < spaceTop) {
            yLocation = mouseY - LineTooltip.LABEL_Y_OFFSET - height;
        } else if (atSide && height < plotAreaBounds.getHeight()) {
            yLocation = spaceTop < spaceBottom ? plotAreaBounds.getMaxY() - height : plotAreaBounds.getMinY();
        } else if (atSide) {
            yLocation = plotAreaBounds.getMinY();
            height = plotAreaBounds.getHeight();
        } else if (spaceBottom > spaceTop) {
            yLocation = mouseY + LineTooltip.LABEL_Y_OFFSET;
            height = spaceBottom - LABEL_Y_OFFSET;
        } else {
            yLocation = plotAreaBounds.getMinY();
            height = spaceTop - LABEL_Y_OFFSET;
        }
        tooltip.resizeRelocate(xLocation, yLocation, width, height);
    }

    private void updateToolTip(final MouseEvent event) {
        final Bounds plotAreaBounds = getChart().getPlotArea().getBoundsInLocal();
        final List<DataPoint> dataPoint = findDataPoints(event, plotAreaBounds);

        if (dataPoint.isEmpty() || mouseOOB(event)) {
            getChartChildren().remove(tooltip);
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL d, HH:mm");
        Optional<DataPoint> x = dataPoint.stream().reduce((p1, p2) -> p1.distanceFromMouse <= p2.distanceFromMouse ? p1 : p2); // get points in range of cursor
        String xValue = x.map(dataPoint1 -> formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli((long) dataPoint1.x), ZoneOffset.systemDefault()))).orElse("");
        String names = dataPoint.stream().map(dataPoint1 -> dataPoint1.formattedNamesLabel).collect(Collectors.joining("\n"));
        String values = dataPoint.stream().map(dataPoint1 -> dataPoint1.formattedValuesLabel).collect(Collectors.joining("\n"));
        updateLabel(event, plotAreaBounds, x, xValue, names, values);
        if (!getChartChildren().contains(tooltip)) {
            getChartChildren().add(tooltip);
            tooltip.requestLayout();
        }
    }
    private boolean mouseOOB(MouseEvent mouseEvent) {
        Point2D point2D = getChart().getCanvas().sceneToLocal(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        return !getChart().getCanvas().getLayoutBounds().contains(point2D);
    }

    public static class DataPoint {
        public final Renderer renderer;
        public final double x;
        public final double yMin;
        public final double yMax;
        public final double xPos;
        public final String datasetName;
        public final String label;
        public final String formattedValuesLabel; // may be empty
        public final String formattedNamesLabel; // may be empty
        public final double distanceFromMouse;

        public DataPoint(Renderer renderer, double x, double yMin,double yMax, String datasetName, String label, double xPos, double distanceFromMouse, String formattedValuesLabel, String formattedNamesLabel) {
            this.renderer = renderer;
            this.x = x;
            this.yMin = yMin;
            this.yMax = yMax;
            this.xPos = xPos;
            this.datasetName = datasetName;
            this.label = label;
            this.distanceFromMouse = distanceFromMouse;
            this.formattedValuesLabel = formattedValuesLabel;
            this.formattedNamesLabel = formattedNamesLabel;
        }

        public DataPoint(Renderer renderer, double x, double yMin,double yMax, String datasetName, String label,  double xPos, double distanceFromMouse) {
            this(renderer, x, yMin,yMax, datasetName, label, xPos, distanceFromMouse, "", "");
        }

        public DataPoint withFormattedLabel(String formattedValuesLabel, String formattedNamesLabel) {
            return new DataPoint(renderer, x, yMin,yMax, datasetName,formattedValuesLabel, xPos, distanceFromMouse, formattedValuesLabel, formattedNamesLabel);
        }
    }
}
